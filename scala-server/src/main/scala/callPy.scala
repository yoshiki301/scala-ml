package callpy

import scala.sys.process._

class callPy{
  var status = 0
  def callPython(path: String, argv: List[String] = Nil): Unit ={
    var command = "python " + path
    argv.foreach({ value =>
      val addition = " " + value
      command += addition
    })

    val process = Process(command).lineStream.toList
    if(process != Nil){
      process.foreach(println)
      status = 1
    }else{
      println("process has no return")
      status = 2
    }
  }
}

object Main{
  def main(args: Array[String]): Unit = {
    val callpy = new callPy
    callpy.callPython(args(0), args.toList.tail)
    println(callpy.status)
  }
}