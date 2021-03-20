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

import scala.concurrent.{ExecutionContext, Future}

class databaseController @Inject()(protected val dbConfigProvider: DatabaseConfigProvider, cc: ControllerComponents)(implicit ec: ExecutionContext)
  extends AbstractController(cc) with HasDatabaseConfigProvider[PostgresProfile] with play.api.i18n.I18nSupport {

  import databaseController._

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

  def addExecResult() = Action{ implicit request =>
    Ok(views.html.addExecResult(ExecResultForm))
  }

  def postExecResult() = Action.async { implicit request =>

    ExecResultForm.bindFromRequest.fold(
      error => {
        Future {
          Conflict(Json.obj(
            "message" -> "Conflict: already existing the same id"
          ))
        }
      },
      form => {
        val action = ExecResult += ExecResultRow(form.id, form.paramId, form.executeFilepath, form.outputDirpath, form.startTimestamp, form.endTimestamp, form.exitStatus)
        db.run(action)
          .map { _ =>
            Ok(Json.obj(
              "message" -> "Created exec_result"
            ))
        }
      }
    )
  }

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

object databaseController{
  case class createExecResult(id: Int, paramId: Int, executeFilepath: Option[String], outputDirpath: Option[String], startTimestamp: Option[Timestamp], endTimestamp: Option[Timestamp], exitStatus: Option[String])

  implicit val ExecResultForm: Form[createExecResult] = Form(
    mapping(
      "id" -> number,
      "param_id" -> number,
      "execute_filepath" -> optional(text),
      "output_dirpath" -> optional(text),
      "start_timestamp" -> optional(sqlTimestamp), "end_timestamp" -> optional(sqlTimestamp), "exit_status" -> optional(text)
    )(createExecResult.apply)(createExecResult.unapply)
  )
}