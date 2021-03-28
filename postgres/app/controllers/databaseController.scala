package controllers

import javax.inject._
import java.text.SimpleDateFormat

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
              "is_succeed" -> item.head.isSucceed
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

    ExecResultForm.bindFromRequest.fold (
      error => {
        Future{
          BadRequest(s"${error}")
        }
      },
      form => {
        val action = ExecResult.filter(_.id === form.id).exists.result.flatMap {
          case false => (ExecResult += ExecResultRow(form.id, form.paramId, form.executeFilepath, form.outputDirpath, form.startTimestamp, form.endTimestamp, form.isSucceed)) >>
            DBIO.from(Future{
              Ok(Json.obj(
                "message" -> "Created exec_result"
              ))
            })
          case true => {
            DBIO.from(Future{
              Conflict(Json.obj(
                "message" -> "Conflict: already existing the same id"
              ))
            })
          }
        }
        db.run(action)
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

  def addParams() = Action { implicit request =>
    Ok(views.html.addParams(ParamsForm))
  }

  def postParams() = Action.async { implicit request =>

    ParamsForm.bindFromRequest.fold (
      error => {
        Future{
          BadRequest(s"${error}")
        }
      },
      form => {
        val action = Params.filter(_.paramId === form.paramId).exists.result.flatMap {
          case false => (Params += ParamsRow(form.paramId, form.paramLabel, form.paramValue)) >>
            DBIO.from(Future{
              Ok(Json.obj(
                "message" -> "Created params"
              ))
            })
          case true => {
            DBIO.from(Future{
              Conflict(Json.obj(
                "message" -> "Conflict: already existing the same param_id"
              ))
            })
          }
        }
        db.run(action)
      }
    )
  }

}

object databaseController{

  implicit val ExecResultForm: Form[ExecResultRow] = Form(
    mapping(
      "id" -> number,
      "param_id" -> number,
      "execute_filepath" -> optional(text),
      "output_dirpath" -> optional(text),
      "start_timestamp" -> optional(sqlTimestamp),
      "end_timestamp" -> optional(sqlTimestamp),
      "is_succeed" -> optional(boolean)
    )(ExecResultRow.apply)(ExecResultRow.unapply)
  )

  implicit val ParamsForm: Form[ParamsRow] = Form(
    mapping(
      "param_id" -> number,
      "param_label" -> optional(text),
      "param_value" -> optional(text)
    )(ParamsRow.apply)(ParamsRow.unapply)
  )
}