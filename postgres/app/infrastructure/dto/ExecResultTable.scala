package dto
// AUTO-GENERATED Slick data model for table ExecResult
trait ExecResultTable {

  self:Tables  =>

  import profile.api._
  import slick.model.ForeignKeyAction
  // NOTE: GetResult mappers for plain SQL are only generated for tables where Slick knows how to map the types of all columns.
  import slick.jdbc.{GetResult => GR}
  /** Entity class storing rows of table ExecResult
   *  @param id Database column id SqlType(int4), PrimaryKey
   *  @param paramId Database column param_id SqlType(int4)
   *  @param executeFilepath Database column execute_filepath SqlType(text), Default(None)
   *  @param outputDirpath Database column output_dirpath SqlType(text), Default(None)
   *  @param startTimestamp Database column start_timestamp SqlType(timestamp), Default(None)
   *  @param endTimestamp Database column end_timestamp SqlType(timestamp), Default(None)
   *  @param exitStatus Database column exit_status SqlType(exit_status_text), Default(None) */
  case class ExecResultRow(id: Int, paramId: Int, executeFilepath: Option[String] = None, outputDirpath: Option[String] = None, startTimestamp: Option[java.sql.Timestamp] = None, endTimestamp: Option[java.sql.Timestamp] = None, exitStatus: Option[String] = None)
  /** GetResult implicit for fetching ExecResultRow objects using plain SQL queries */
  implicit def GetResultExecResultRow(implicit e0: GR[Int], e1: GR[Option[String]], e2: GR[Option[java.sql.Timestamp]]): GR[ExecResultRow] = GR{
    prs => import prs._
    ExecResultRow.tupled((<<[Int], <<[Int], <<?[String], <<?[String], <<?[java.sql.Timestamp], <<?[java.sql.Timestamp], <<?[String]))
  }
  /** Table description of table exec_result. Objects of this class serve as prototypes for rows in queries. */
  class ExecResult(_tableTag: Tag) extends profile.api.Table[ExecResultRow](_tableTag, "exec_result") {
    def * = (id, paramId, executeFilepath, outputDirpath, startTimestamp, endTimestamp, exitStatus) <> (ExecResultRow.tupled, ExecResultRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(id), Rep.Some(paramId), executeFilepath, outputDirpath, startTimestamp, endTimestamp, exitStatus)).shaped.<>({r=>import r._; _1.map(_=> ExecResultRow.tupled((_1.get, _2.get, _3, _4, _5, _6, _7)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(int4), PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.PrimaryKey)
    /** Database column param_id SqlType(int4) */
    val paramId: Rep[Int] = column[Int]("param_id")
    /** Database column execute_filepath SqlType(text), Default(None) */
    val executeFilepath: Rep[Option[String]] = column[Option[String]]("execute_filepath", O.Default(None))
    /** Database column output_dirpath SqlType(text), Default(None) */
    val outputDirpath: Rep[Option[String]] = column[Option[String]]("output_dirpath", O.Default(None))
    /** Database column start_timestamp SqlType(timestamp), Default(None) */
    val startTimestamp: Rep[Option[java.sql.Timestamp]] = column[Option[java.sql.Timestamp]]("start_timestamp", O.Default(None))
    /** Database column end_timestamp SqlType(timestamp), Default(None) */
    val endTimestamp: Rep[Option[java.sql.Timestamp]] = column[Option[java.sql.Timestamp]]("end_timestamp", O.Default(None))
    /** Database column exit_status SqlType(exit_status_text), Default(None) */
    val exitStatus: Rep[Option[String]] = column[Option[String]]("exit_status", O.Default(None))

    /** Uniqueness Index over (paramId) (database name exec_result_param_id_key) */
    val index1 = index("exec_result_param_id_key", paramId, unique=true)
  }
  /** Collection-like TableQuery object for table ExecResult */
  lazy val ExecResult = new TableQuery(tag => new ExecResult(tag))
}
