import callpy.callPy
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

class callPyTest extends AnyFunSuite with Matchers{
  test("no argument"){
    val callpy = new callPy
    callpy.callPython("src/test/resources/argCatch.py")
    callpy.status should be (2)
  }

  test("no return option"){
    val callpy = new callPy
    callpy.callPython("src/test/resources/argCatch.py", List("--echo", "abc"))
    callpy.status should be (2)
  }

  test("has return option"){
    val callpy = new callPy
    callpy.callPython("src/test/resources/argCatch.py", List("--echo", "abc", "-r"))
    callpy.status should be (1)
  }
}
