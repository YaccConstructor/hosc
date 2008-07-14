package hosc

import scala.util.parsing.input.StreamReader
import scala.util.parsing.input.CharArrayReader

import java.io.File
import java.io.FileInputStream
import java.io.InputStreamReader
import java.io.FileReader
import java.io.FileWriter
import java.io.BufferedReader

import HLanguage._
import HLanguage1._
import HParsers._
import Util._
import sc1.ProcessTree1
import sc1.ProcessTree1._
import sc1.ProcessTree1SVG

import sc1.HParsers1
import hosc.util.Canonizer.{canonize1 => can}

object SuperCompiler2App {
  val help = """usage: hosc.SuperCompiler2App -i input_file -t tree_output_file -p program_output_file
  |Where:
  |input_file            path to input file where code is written HL1 language syntax
  |tree_output_file      path to file where process tree will be placed (in SVG format)
  |program_output_file   path to file where residual program will be placed
  |""".stripMargin
  def main(args : Array[String]) : Unit = {
    var fileName: String = null
    var outFileName: String = null
    var outProgramFileName: String = null
    var sugared = false
    args.toList match {
      case "-i" :: input_file :: "-t" :: output_file :: "-p" :: output_file_1 :: Nil =>
        fileName = input_file
        outFileName = output_file
        outProgramFileName = output_file_1
      case "-help" :: Nil => 
        println(help)
        return
      case _ => 
        throw new IllegalArgumentException("run spcs.SuperCompiler2App -help for help")       
    }
    
    val term = termFromFile(fileName)
    val sc = new SuperCompiler2(term, new Vars1Util())
    val (tree, resTerm) = sc.superCompile()    
    val svg = new ProcessTree1SVG(tree).treeToSVG
    
    val svgFile = new java.io.File(outFileName)
    if (!svgFile.exists){
      svgFile.createNewFile()
    } 
    scala.xml.XML.save(outFileName, svg)
    
    val doc = resTerm.toDoc
    val slFile = new java.io.File(outProgramFileName)
    if (!slFile.exists){
      slFile.createNewFile()
    }
    val fw = new FileWriter(slFile);
    fw.write("// generated by scp1 from " + fileName + "\n")
    doc.format(100, fw)
    fw.flush();
    fw.close(); 
  }
  
  def termFromFile(fileName: String) = {
    val file = new File(fileName)
    val sb = new StringBuilder
    val in = new BufferedReader(new FileReader(fileName));
    var str: String = null
    do {
      str = in.readLine
      if (str != null){
        sb.append(str)
        sb.append("\n")
      }
    } while (str != null)
    in.close();
    val pr = HParsers1.parseTerm(new CharArrayReader(sb.toString.toCharArray))
    if (pr.successful) {
      can(pr.get)
    } else { 
      throw new IllegalArgumentException(pr.toString)
    }
  }
}