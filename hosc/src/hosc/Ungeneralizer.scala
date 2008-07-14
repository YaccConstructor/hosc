package hosc

import HLanguage1._
import sc1.ProcessTree1
import sc1.ProcessTree1._

class Ungeneralizer(tree: ProcessTree1, constructor: CodeConstructor) {
  def ungeneralize(): Boolean = {
    println("ungeneralized")
    var ungeneralized = false
    var someWorkWasDone = false
    do {
      someWorkWasDone = false
      val letrecs = tree.leafs filter {_.expr match {case l: LetRecExpression1 => true; case _ => false;}}
      for (lr <- letrecs) {
        lr.ancestors() find {p => p.expr match {case l: LetExpression1 => true; case _ => false}} match {
          case Some(alpha) => {
            tree.leafs filter {n => n.ungeneralized && n.ancestors().contains(alpha)} match {
              case Nil => {
                val code = constructor.construct(alpha)
                val ungeneralizedNode = tree.replace(alpha, code)
                ungeneralizedNode.ungeneralized = true
                ungeneralized = true
                someWorkWasDone = true
              }
              case some => 
            }            
          }
          case None =>
        }
      }
    } while (someWorkWasDone)
    ungeneralized
  }
}
