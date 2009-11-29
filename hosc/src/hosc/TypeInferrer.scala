package hosc

import EnrichedLambdaCalculus._
import TypeAlgebra._

case class TypeError(s: String) extends Exception(s)

class Subst(val map: Map[TypeVariable, Type]) extends (Type => Type) {
  
  def this() = this(Map())
  def compose(s: Subst) = new Subst(s.map.transform((k, v) => this(v)) ++ this.map)
  def excl(vs: List[TypeVariable]) = new Subst(map -- vs)
    
  def apply(t: Type): Type = t match {
    case tv : TypeVariable => map.getOrElse(tv, tv)
    case Arrow(t1, t2) => Arrow(this(t1), this(t2))
    case TypeConstructor(k, ts) => TypeConstructor(k, ts map this)
  }

  def extend(x: TypeVariable, t: Type) =
    if (tyvars(t) contains x)
      throw TypeError("recursive binding: " + x + " = " + t)
    else if (x == t) this
    else new Subst(Map(x -> t)) compose this
}

case class TypeScheme(genericVars: List[TypeVariable], t: Type) {
  def newInstance = (new Subst(Map(genericVars map {(_, newTyvar)}:_*))) (t)
  def nonGenericVars = tyvars(t) -- genericVars
  def sub(sub: Subst) = TypeScheme(genericVars, (sub excl genericVars) (t))
}

case class TypeEnv(map: Map[TypeVariable, TypeScheme]){
  def value(tv: TypeVariable) = map(tv)
  def install(tv: TypeVariable, ts: TypeScheme) = TypeEnv(map + {(tv, ts)})
  def nonGenericVars = map.values.toList flatMap {_.nonGenericVars} removeDuplicates
  def sub(s: Subst) = TypeEnv(map transform {(k, v) => v.sub(s)})
}

object TypeInferrer {

  private def mgu(t: Type, u: Type, s: Subst): Subst = (t, u) match {
    case (a:TypeVariable, b: TypeVariable) if a == b =>
      s
    case (a:TypeVariable, _) if s(a) == a =>
      s.extend(a, u)
    case (a:TypeVariable, _) =>
      mgu(s(t), s(u), s)
    case (_, a:TypeVariable) =>
      mgu(u, t, s)
    case (Arrow(t1, t2), Arrow(u1, u2)) =>
      mgu(t1, u1, mgu(t2, u2, s))
    case (TypeConstructor(k1, ts), TypeConstructor(k2, us)) if (k1 == k2) =>
      (s /: (ts zip us)) ((s, tu) => mgu(tu._1, tu._2, s))
    case _ =>
      throw new TypeError("cannot unify " + s(t) + " with " + s(u))
  }
  
  private def mgu(ts: List[Pair[Type, Type]], s: Subst): Subst = {
    (s /: ts) {(s, p) => mgu(p._1, p._2, s)}
  }
  
}

import TypeInferrer._
class TypeInferrer(typeDefs: List[TypeConstructorDefinition]) {
  
  private val dataConstructors = Map(typeDefs flatMap {_.cons map {c => c.name -> c}}:_*)
  private val typeConstructorDefs = Map(typeDefs flatMap {td => td.cons map {_.name -> td} }:_*)
  
  def inferType(e: Expression): Type = {
    val v2scheme = {v: Variable => TypeVariable(v.name) -> TypeScheme(Nil, newTyvar)}
    val te = TypeEnv(Map(freeVars(e).toList map v2scheme :_*))
    check(te, e)._2
  }
  
  private def check(te: TypeEnv, expr: Expression): (Subst, Type) = expr match {
    case Variable(name) => 
      (new Subst(), te.value(TypeVariable(name)).newInstance)
    case Application(h, a) => {
      val (sub1, List(type_h, type_a)) = check(te, List(h, a))
      val genVar = newTyvar
      val sub2 = mgu(type_h, Arrow(type_a, genVar), sub1)
      (sub2, sub2(genVar))
    }
    case LambdaAbstraction(v, body) => {
      val genVar = newTyvar
      val extendedEnv = te.install(TypeVariable(v.name), TypeScheme(Nil, genVar)) 
      val (sub, type_body) = check(extendedEnv, body)
      (sub, Arrow(sub(genVar), type_body))
    }
    case Constructor(name, args) => {
      val (sub1, type1s) = check(te, args)
      val conDef = typeConstructorDefs(name)
      val typeParams = conDef.args
      val freshSub = new Subst(Map(typeParams map {(_, newTyvar)}:_*))    
      val freshDataConArgs = dataConstructors(name).args map freshSub
      val sub2 = mgu(freshDataConArgs zip type1s, sub1)
      (sub2, TypeConstructor(conDef.name, typeParams map (sub2 compose freshSub)))
    }
    case LetExpression(bs, expr) => {
      val (fs, bodies) = List.unzip(bs)
      val f_types = fs map {x => TypeVariable(x.name)}
      val (sub1, type1s) = check(te, bodies)
      val te1 = extend(te.sub(sub1), f_types, type1s)
      val (sub2, type2) = check(te1, expr)
      (sub1 compose sub2, type2)
    }
    case LetRecExpression(bs, expr) => {
      val (fs, bodies) = List.unzip(bs) 
      val f_types = fs map {x => TypeVariable(x.name)}
      val schemes = fs map {x => TypeScheme(Nil, newTyvar)}
      val te1 = TypeEnv(te.map ++ (f_types zip schemes))
      val (sub1, type1s) = check(te1, bodies)
      val l_types = schemes map {_.sub(sub1).t}
      val sub2 = mgu(l_types zip type1s, sub1)
      val te2 = extend(te.sub(sub2), f_types, l_types map sub2)
      val (sub3, type3) = check(te2, expr)
      (sub2 compose sub3, type3)
    }
    case c@CaseExpression(selector, branches) => {
      val (sub1, type1s) = tcBranches(te, branches)    
      val tv = newTyvar 
      val sub2 = mgu(type1s map {(tv, _)}, sub1)
      val (sub3, type3) = check(te.sub(sub2), selector)
      val Arrow(selType, branchBodyType) = sub2(tv).asInstanceOf[Arrow]
      val sub = mgu(selType, type3, sub3 compose sub2)
      (sub, sub(branchBodyType))
    }
  }
  
  private def check(environment: TypeEnv, expressions: List[Expression]): (Subst, List[Type]) = expressions match {
    case Nil => (new Subst(), Nil) 
    case e :: es => {
      val (sub1, type1) = check(environment, e)
      val (sub2, types2) = check(environment sub sub1, es)
      (sub2 compose sub1, sub2(type1) :: types2)
    }
  }
  
  // IMPORTANT HERE!
  private def extend(te: TypeEnv, xs: List[TypeVariable], ts: List[Type]): TypeEnv = {
    val schemes = ts map {createTypeScheme(te.nonGenericVars, _)}
    TypeEnv(te.map ++ (xs zip schemes))
  } 
  
  private def createTypeScheme(nonGenericVars: List[TypeVariable], t: Type): TypeScheme = {
    val genericVars = tyvars(t) -- nonGenericVars
    val map = Map(genericVars map {(_, newTyvar)}:_*)
    TypeScheme(map.values.toList, new Subst(map)(t))
  }
  
  private def tcBranch(te: TypeEnv, b: Branch): (Subst, Type) = {
    
    val cd = typeConstructorDefs(b.pattern.name)
    val dc = dataConstructors(b.pattern.name)
    
    val originalTvars = cd.args
    val s = (new Subst() /: originalTvars) ((sub, tv) => sub.extend(tv, newTyvar()))    
    val freshDcArgs: List[Type] = dc.args map s
    
    val tcon = s(TypeConstructor(cd.name, cd.args))
    
    var te1 = te
    for ((patternVar, argType) <- b.pattern.args zip freshDcArgs){
      te1 = te1.install(TypeVariable(patternVar.name), TypeScheme(Nil, argType))
    }
    
    val (sub3, type3) = check(te1, b.term)
    (sub3, Arrow(sub3(tcon), type3))
  }
  
  private def tcBranches(environment: TypeEnv, expressions: List[Branch]): (Subst, List[Type]) = expressions match {
    case Nil => (new Subst(), Nil) 
    case e :: es => {
      val (sub1, type1) = tcBranch(environment, e)
      val (sub2, types2) = tcBranches(environment.sub(sub1), es)
      (sub2 compose sub1, sub2(type1) :: types2)
    }
  }
  
}