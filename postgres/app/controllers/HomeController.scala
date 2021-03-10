package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.PostgresProfile
import slick.jdbc.PostgresProfile.api._
import dto.Tables._
import scala.concurrent.ExecutionContext

@Singleton
class HomeController @Inject()(protected val dbConfigProvider: DatabaseConfigProvider, cc: ControllerComponents)(implicit ec: ExecutionContext)
  extends AbstractController(cc) with HasDatabaseConfigProvider[PostgresProfile] {

  def getExecResult(resultId: Int) = Action.async { implicit request =>
    val action = ExecResult.filter(_.id === resultId).result

    db.run(action)
      .map(
        exec_result => Ok(exec_result.toList.mkString("\n"))
      ).recover{
      case exception: Exception => InternalServerError(exception.getMessage)
    }
  }

  def getParams(paramId: Int) = Action.async { implicit request =>
    val action = Params.filter(_.paramId ===paramId).result

    db.run(action)
      .map(
        params => Ok(params.toList.mkString("\n"))
      ).recover{
      case exception: Exception => InternalServerError(exception.getMessage)
    }
  }

}
