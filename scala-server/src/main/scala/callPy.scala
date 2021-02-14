package pythonController

import scala.sys.process._

class callPy{
  var res = List("process has no return")
  def callPython(path: String, argv: List[String] = Nil): List[String] = {
    var command = "python " + path
    argv.foreach({ value =>
      val addition = " " + value
      command += addition
    })

    val process = Process(command).lineStream_!.toList
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