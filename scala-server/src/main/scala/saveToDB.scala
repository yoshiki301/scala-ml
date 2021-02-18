import scala.sys.process._

import java.time.{ZonedDateTime, ZoneId}
import java.util.TimeZone
import java.sql.Timestamp



class callPy{
  def callPython(inputPath: String, args: List[String] = Nil): List[String] = {
    var res = List("process has no return")
    var cmd = "python " + inputPath
    var ret = Map.empty[String, Float]
    args.foreach({ tmp =>
      val add = " " + tmp
      cmd += add
      val colon = tmp.indexOf(":")
      val key = tmp.take(colon)
      val value = tmp.drop(colon+1).toFloat
      ret += (key->value)
    })
    println(ret)

    val tz = TimeZone.getTimeZone("Asia/Tokyo").toZoneId

    val start = Timestamp.valueOf(ZonedDateTime.now(tz).toLocalDateTime)
    val process = Process(cmd).lineStream_!.toList
    val stop = Timestamp.valueOf(ZonedDateTime.now(tz).toLocalDateTime)

    println(start)
    println(stop)

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

object saveToDB{
  def main(args: Array[String]): Unit = {
    val callpy = new callPy
    val res = callpy.callPython(args(0), args.toList.tail)
    println(res)

  }
}