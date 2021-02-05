import sys.process._

object callpy extends App{
  val process = Process("python src/main/resources/hello.py").run
}
