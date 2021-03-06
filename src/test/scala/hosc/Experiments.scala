package hosc

import org.junit.Test
import org.junit.Ignore
import org.junit.Assert._
import HLanguage._
import Util._
import hosc.lemmas.LemmaFinder

import hosc.{SuperCompilerApp => SCP0}
import hosc.exp.{SuperCompilerApp1 => SCP1}

class Experiments {
  
 @Ignore 
 @Test def eval1_0 =
    SCP0.main(Array("-si", "examples/exp/eval1.hs", "-t", "examples/exp/out0/eval1.svg","-p", "examples/exp/out0/eval1.hs"))
 
 @Ignore 
 @Test def eval1_1: Unit = {
    //SCP1.main(Array("-si", "examples/exp/eval1.hs", "-t", "examples/exp/out1/eval1.svg","-p", "examples/exp/out1/eval1.hs"))
    null
  }  
  
  @Test def fo_dsl_1_a =
    SCP0.main(Array("-si", "examples/exp/fo_dsl_1_a.hs", "-t", "examples/exp/out0/fo_dsl_1_a.svg","-p", "examples/exp/out0/fo_dsl_1_a.hs"))
  
  @Test def fo_dsl_1_b =
    SCP0.main(Array("-si", "examples/exp/fo_dsl_1_b.hs", "-t", "examples/exp/out0/fo_dsl_1_b.svg","-p", "examples/exp/out0/fo_dsl_1_b.hs"))
  
  @Test def fo_dsl_2_a =
    SCP0.main(Array("-si", "examples/exp/fo_dsl_2_a.hs", "-t", "examples/exp/out0/fo_dsl_2_a.svg","-p", "examples/exp/out0/fo_dsl_2_a.hs"))
  
  @Test def ho_dsl =
    SCP0.main(Array("-si", "examples/exp/ho_dsl.hs", "-t", "examples/exp/out0/ho_dsl.svg","-p", "examples/exp/out0/ho_dsl.hs"))
  
  @Ignore
  @Test def fo_dsl_2_b = {
    SCP0.main(Array("-si", "examples/exp/fo_dsl_2_b.hs", "-t", "examples/exp/out0/fo_dsl_2_b.svg","-p", "examples/exp/out0/fo_dsl_2_b.hs"))
  }
  
  @Test def fo_dsl_3_a =
    SCP0.main(Array("-si", "examples/exp/fo_dsl_3_a.hs", "-t", "examples/exp/out0/fo_dsl_3_a.svg","-p", "examples/exp/out0/fo_dsl_3_a.hs"))
  
  @Test def fo_dsl_3_b =
    SCP0.main(Array("-si", "examples/exp/fo_dsl_3_b.hs", "-t", "examples/exp/out0/fo_dsl_3_b.svg","-p", "examples/exp/out0/fo_dsl_3_b.hs"))
  
  @Test def no_lambda =
    SCP0.main(Array("-si", "examples/exp/inf_data.hs", "-t", "examples/exp/out0/inf_data.svg","-p", "examples/exp/out0/inf_data.hs"))
  @Test def no_lambda2 =
    SCP0.main(Array("-si", "examples/exp/inf_data2.hs", "-t", "examples/exp/out0/inf_data2.svg","-p", "examples/exp/out0/inf_data2.hs"))
  
  @Test def inf_eq =
    SCP0.main(Array("-si", "examples/exp/inf_eq.hs", "-t", "examples/exp/out0/inf_eq.svg","-p", "examples/exp/out0/inf_eq.hs"))
  
  @Test def pred_nat =
    SCP0.main(Array("-si", "examples/exp/predNat.hs", "-t", "examples/exp/out0/predNat.svg","-p", "examples/exp/out0/predNat.hs"))
  
  @Test def join1 =
    SCP0.main(Array("-si", "examples/exp/join1.hs", "-t", "examples/exp/out0/join1.svg","-p", "examples/exp/out0/join1.hs"))
  
  @Test def rev =
    SCP0.main(Array("-si", "examples/exp/rev.hs", "-t", "examples/exp/out0/rev.svg","-p", "examples/exp/out0/rev.hs"))
  
  @Test def church =
    SCP0.main(Array("-si", "examples/exp/church.hs", "-t", "examples/exp/out0/church.svg","-p", "examples/exp/out0/church.hs"))
  
  @Test def or_even_odd_1 =
    SCP0.main(Array("-si", "examples/exp/or_even_odd_1.hs", "-t", "examples/exp/out0/or_even_odd_1.svg","-p", "examples/exp/out0/or_even_odd_1.hs"))
  
  @Test def or_even_odd_2 =
    SCP0.main(Array("-si", "examples/exp/or_even_odd_2.hs", "-t", "examples/exp/out0/or_even_odd_2.svg","-p", "examples/exp/out0/or_even_odd_2.hs"))
  
  @Ignore
  @Test def or_even_odd: Unit = {
    val program = programFromFile("examples/exp/or_even_odd_2.hs")
    val finder = new LemmaFinder(program)
    //finder.findEqExpressions(program.goal)
    null
  }
  
  @Test def even_doubleAcc_1 =
    SCP0.main(Array("-si", "examples/exp/even_doubleAcc_1.hs", "-t", "examples/exp/out0/even_doubleAcc_1.svg","-p", "examples/exp/out0/even_doubleAcc_1.hs"))
  
  @Test def even_doubleAcc_2 =
    SCP0.main(Array("-si", "examples/exp/even_doubleAcc_2.hs", "-t", "examples/exp/out0/even_doubleAcc_2.svg","-p", "examples/exp/out0/even_doubleAcc_2.hs"))
  
  @Ignore
  @Test def even_doubleAcc: Unit = {
    val program = programFromFile("examples/exp/even_doubleAcc_2.hs")
    val finder = new LemmaFinder(program)
    //finder.findEqExpressions(program.goal)
    null
  }
    
}
