package hosc.tests

import org.junit.{ Test, Assert }
import hosc.Eq

class EquivalenceNaiveTransientAware extends Equivalence {
  import hosc.sc.{ HOSC15, NaiveSuperCompilerTransientAware }
  override def testEq(f1: String, f2: String): Unit = {
    val sc = NaiveSuperCompilerTransientAware
    val p1 = sc.superCompileFile(examplesDir + f1)
    val p2 = sc.superCompileFile(examplesDir + f2)
    Assert.assertTrue(Eq.equivalent(p1.goal, p2.goal))
  }
}