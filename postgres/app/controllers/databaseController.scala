package controllers

import javax.inject._
import java.text.SimpleDateFormat
import java.sql.Timestamp

import play.api.mvc._
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import play.api.data._
import play.api.data.Forms._
import play.api.libs.json.{JsObject, Json}
import slick.jdbc.PostgresProfile
import slick.jdbc.PostgresProfile.api._
import dto.Tables._

import scala.concurrent.ExecutionContext

class databaseController @Inject()(protected val dbConfigProvider: DatabaseConfigProvider, cc: ControllerComponents)(implicit ec: ExecutionContext)
  extends AbstractController(cc) with HasDatabaseConfigProvider[PostgresProfile] {

  def getExecResult(resultId: Int) = Action.async { implicit request =>
    val action = ExecResult.filter(_.id === resultId).result

    db.run(action)
      .map {
        item => {
          val sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

          Ok(
            Json.obj(
              "id" -> item.head.id,
              "param_id" -> item.head.paramId,
              "execute_filepath" -> item.head.executeFilepath,
              "output_dirpath" -> item.head.outputDirpath,
              "start_timestamp" -> sdf.format(item.head.startTimestamp.get.getTime),
              "end_timestamp" -> sdf.format(item.head.endTimestamp.get.getTime),
              "exit_status" -> item.head.exitStatus
            )
          )
        }
      }.recover{
      case e: NoSuchElementException => NotFound(
        Json.obj(
          "message" -> "Not found the specified result_id result."
        )
      )
    }
  }

  //  def postExecResult() = Action.async { implicit request =>
  //
  //    val action = ExecResult += ExecResultRow(id, paramId, executeFilepath, outputDirpath, startTimestamp, endTimestamp, exitStatus)
  //
  //    db.run(action).map(
  //      tuple => Ok("Created exec_result")
  //    ).recover{
  //      case exception: Exception => InternalServerError(exception.getMessage)
  //    }
  //  }

  def getParams(paramId: Int) = Action.async { implicit request =>
    val action = Params.filter(_.paramId === paramId).result

    db.run(action)
      .map {
        params => {
          var jsonList = List.empty[JsObject]

          for (item <- params) {
            val jsonItem = Json.obj(
              "param_id" -> item.paramId,
              "param_label" -> item.paramLabel,
              "param_value" -> item.paramValue
            )
            jsonList = jsonItem :: jsonList
          }
          if (jsonList.isEmpty){
            NotFound(
              Json.obj(
                "message" -> "Not found the specified param_id result."
              )
            )
          } else {
            Ok(jsonList.reverse.mkString("\n"))
          }
        }
      }
  }
}

//object databaseController{
//
//  case class ExecResultForm(id: Int, paramId: Int, executeFilepath: Option[String], outputDirpath: Option[String], startTimestamp: Option[Timestamp], endTimestamp: Option[Timestamp], exitStatus: Option[String])
//
//  val execResultForm: Form[ExecResultForm] = Form(
//    mapping(
//      "id" -> number,
//      "paramId" -> number,
//      "executeFilepath" -> optional(text),
//      "outputDirpath" -> optional(text),
//      "startTimestamp" -> optional(sqlTimestamp),
//      "endTimestamp" -> optional(sqlTimestamp),
//      "exitStatus" -> optional(text)
//    )(ExecResultForm.apply)(ExecResultForm.unapply)
//  )
//}