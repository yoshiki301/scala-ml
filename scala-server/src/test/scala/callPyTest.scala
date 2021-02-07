import callpy.callPy
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

class callPyTest extends AnyFunSuite with Matchers{
  test("no argument no return option" ){
    val callpy = new callPy
    val res = callpy.callPython("src/test/resources/argCatch.py")
    res.isInstanceOf[List[String]] should be (true)
    res should be (List("process has no return"))
  }

  test("no argument has return option" ){
    val callpy = new callPy
    val res = callpy.callPython("src/test/resources/argCatch.py", List("-r"))
    res.isInstanceOf[List[String]] should be (true)
    res should be (List("process has no return"))
  }

  test("has argument no return option"){
    val callpy = new callPy
    val res = callpy.callPython("src/test/resources/argCatch.py", List("--echo", "abc"))
    res.isInstanceOf[List[String]] should be (true)
    res should be (List("process has no return"))
  }

  test("has argument has return option"){
    val callpy = new callPy
    val res = callpy.callPython("src/test/resources/argCatch.py", List("--echo", "abc", "-r"))
    res.isInstanceOf[List[String]] should be (true)
    res should be (List("abc"))
  }
}
