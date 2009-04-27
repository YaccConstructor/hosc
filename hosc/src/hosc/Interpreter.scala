package hosc

import scala.util.parsing.input.{CharArrayReader, Reader}

import HLanguage._
import TermAlgebra._
import Util._

class Interpreter(program: Program) {
  
  def this(fileName: String) = this(Util.programFromFile(fileName))
  
  def eval(t: Expression): Expression = lazyEval(t) match {
    case c: Constructor => Constructor(c.name, c.args.map(eval))
    case l: LambdaAbstraction => l
    case x => throw new Exception("Internal Error: lazy eval returns " + x)
  }
  
  def eval(): Expression = {
    // validate that term is ground
    Validator.valTerm(Set.empty[String] ++ (program.fs map {f => f.name}), program.goal, program)
    eval(program.goal)
  }
  
  private def lazyEval(term: Expression): Expression = {
    var t = term
    do {
      t = baseLazyEval(t);      
    } while (decompose(t).isInstanceOf[Context])
    t
  }
  
  // Constructor or lambda as result
  private def baseLazyEval(t: Expression): Expression = decompose(t) match {
    case o: Observable => o.term
    case context: Context => context.redex match {
      case RedexCall(v) => {
        val lam = program.getFunction(v.name).get.lam
        context.replaceHole(freshBinders(lam)) 
      }
      case RedexLamApp(lam, app) => context.replaceHole(applySubstitution(lam.t, Map(lam.v -> app.arg)))
      case RedexCaseCon(c, ce) => {
        val b = ce.branches.find(_.pattern.name == c.name).get
        val sub = Map[Variable, Expression]() ++ (b.pattern.args zip c.args)
        context.replaceHole(applySubstitution(b.term, sub))
      }
      case _ => throw new Exception("Unexpexted redex is encoutered " + context.redex)
    }
  }
  
}