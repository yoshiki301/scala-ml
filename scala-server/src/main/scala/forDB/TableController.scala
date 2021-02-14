package forDB

import slick.jdbc.JdbcBackend.Database
import scala.concurrent.Await
import scala.concurrent.duration.Duration
import slick.driver.PostgresDriver.api._

object TableController extends App{
  val db = Database.forURL(url = "jdbc:postgresql://db:5432", user = "root", password = "root", driver =
    "org" +
    ".postgresql.Driver")
  val query = sql"SELECT id, param_id FROM scala_server.exec_result".as[(Int, Int)]
  val f = db.run(query)
  Await.result(f, Duration.Inf) foreach println
}
