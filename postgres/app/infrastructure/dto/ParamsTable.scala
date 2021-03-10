package dto
// AUTO-GENERATED Slick data model for table Params
trait ParamsTable {

  self:Tables  =>

  import profile.api._
  import slick.model.ForeignKeyAction
  // NOTE: GetResult mappers for plain SQL are only generated for tables where Slick knows how to map the types of all columns.
  import slick.jdbc.{GetResult => GR}
  /** Entity class storing rows of table Params
   *  @param paramId Database column param_id SqlType(int4)
   *  @param paramLabel Database column param_label SqlType(text), Default(None)
   *  @param paramValue Database column param_value SqlType(text), Default(None) */
  case class ParamsRow(paramId: Int, paramLabel: Option[String] = None, paramValue: Option[String] = None)
  /** GetResult implicit for fetching ParamsRow objects using plain SQL queries */
  implicit def GetResultParamsRow(implicit e0: GR[Int], e1: GR[Option[String]]): GR[ParamsRow] = GR{
    prs => import prs._
    ParamsRow.tupled((<<[Int], <<?[String], <<?[String]))
  }
  /** Table description of table params. Objects of this class serve as prototypes for rows in queries. */
  class Params(_tableTag: Tag) extends profile.api.Table[ParamsRow](_tableTag, "params") {
    def * = (paramId, paramLabel, paramValue) <> (ParamsRow.tupled, ParamsRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(paramId), paramLabel, paramValue)).shaped.<>({r=>import r._; _1.map(_=> ParamsRow.tupled((_1.get, _2, _3)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column param_id SqlType(int4) */
    val paramId: Rep[Int] = column[Int]("param_id")
    /** Database column param_label SqlType(text), Default(None) */
    val paramLabel: Rep[Option[String]] = column[Option[String]]("param_label", O.Default(None))
    /** Database column param_value SqlType(text), Default(None) */
    val paramValue: Rep[Option[String]] = column[Option[String]]("param_value", O.Default(None))

    /** Foreign key referencing ExecResult (database name params_param_id_fkey) */
    lazy val execResultFk = foreignKey("params_param_id_fkey", paramId, ExecResult)(r => r.paramId, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
  }
  /** Collection-like TableQuery object for table Params */
  lazy val Params = new TableQuery(tag => new Params(tag))
}
