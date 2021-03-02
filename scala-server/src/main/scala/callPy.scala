package callpy

import scala.sys.process._

class callPy{
  def callPython(path: String, argv: List[String] = Nil): List[String] = {
    var res = List("process has no return")
    var command = "python " + path
    argv.foreach({ value =>
      val addition = " " + value
      command += addition
    })

    val process = Process(command).lineStream.toList
    if(process != Nil && process != List("None")){
      res = Nil
      process.foreach({ str =>
        res = str :: res
      })
      res.reverse
    }
    res
  }
}

object Main{
  def main(args: Array[String]): Unit = {
    val callpy = new callPy
    val res = callpy.callPython(args(0), args.toList.tail)
    println(res)
  }
}