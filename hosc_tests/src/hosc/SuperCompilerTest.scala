package hosc

import org.junit.Test
import org.junit.Ignore
import org.junit.Assert._
import HLanguage._
import Util._

class SuperCompilerTest {
  
  @Ignore
  @Test def simple(): Unit = {
    testSC("input/revInt.hl", "app (app x y) z")
    testSC("input/revInt.hl", "app (app x y) x")
    testSC("input/revInt.hl", "app x x")
  }  
  
  def testSC(fileName: String, input: String): Unit = {
    val p = programFromFile(fileName)
    val sc = new SuperCompiler(p)
    val term = termFromString(input, p)
    val tree = sc.buildProcessTree(term)
    println(tree)
  }
  
  @Test def processSamples(): Unit =
  {
    
    SuperCompilerApp.main(Array("-i", "input/revInt.hl",
                                "-e", "app (app x y) z", 
                                "-t", "output/app1_1.svg",
                                "-p", "output/app1.hl"));
    SuperCompilerApp.main(Array("-i", "input/revInt.hl",
        "-e", "app (app x y) x", 
        "-t", "output/app2_1.svg",
        "-p", "output/app2.hl"));
    SuperCompilerApp.main(Array("-i", "input/revInt.hl",
        "-e", "app x x", 
        "-t", "output/app3_1.svg",
        "-p", "output/app3.hl"));
    SuperCompilerApp.main(Array("-i", "input/revInt.hl",
        "-e", "rev x", 
        "-t", "output/rev_1.svg",
        "-p", "output/rev.hl"));

  }
}