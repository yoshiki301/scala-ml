package controllers

import scala.sys.process.Process

class pythonController{
  def callPython(executePath: String, exportPath: String, paramPath: String): List[String] = {
    var res = List("process has no return")
    val cmd = "python " + executePath + " " + paramPath

    val process = Process(cmd).lineStream_!.toList

    if(process != Nil && process != List("None")){
      res = Nil
      process.foreach({ str =>
        val tmp = List(str)
        res = res ::: tmp
      })
    }
    res
  }
}
