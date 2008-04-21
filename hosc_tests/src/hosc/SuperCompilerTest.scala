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
    
    SuperCompilerApp.main(Array("-i", "input/z01.hl",
        "-e", "takenm n m", 
        "-t", "output/z01.svg",
        "-p", "output/z01.hl"));
    SuperCompilerApp.main(Array("-i", "input/z02.hl",
        "-e", "app x y", 
        "-t", "output/z02.svg",
        "-p", "output/z02.hl"));
        
   SuperCompilerApp.main(Array("-i", "input/z03.hl",
            "-e", "f1 (f x)", 
            "-t", "output/z03.svg",
            "-p", "output/z03.hl"));   
   
   SuperCompilerApp.main(Array("-i", "input/mapNotNot.hl",
       "-e", "mapNotNot y", 
       "-t", "output/mapNotNot.svg",
       "-p", "output/mapNotNot.hl"));
   
   SuperCompilerApp.main(Array("-i", "input/mapNotNot.hl",
       "-e", "not (not y)", 
       "-t", "output/notNot.svg",
       "-p", "output/notNot.hl"));
   
   SuperCompilerApp.main(Array("-i", "input/mapNotNot.hl",
       "-e", "map not y", 
       "-t", "output/mapNot.svg",
       "-p", "output/mapNot.hl"));
   
   SuperCompilerApp.main(Array("-i", "input/mapNotNot.hl",
       "-e", "map x (Cons True (Cons False Nil)) ", 
       "-t", "output/mapx.svg",
       "-p", "output/mapx.hl"));
   
   SuperCompilerApp.main(Array("-i", "input/mapNotNot.hl",
       "-e", "id y", 
       "-t", "output/id.svg",
       "-p", "output/id.hl"));
   
   SuperCompilerApp.main(Array("-i", "input/eqnum.hl",
       "-e", "eqnum c c", 
       "-t", "output/eqnum.svg",
       "-p", "output/eqnum.hl"));
  }
}
