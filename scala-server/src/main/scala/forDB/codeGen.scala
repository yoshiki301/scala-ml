package forDB

object codeGen extends App{
  val slickDriver = "slick.jdbc.PostgresProfile"
  val jdbcDriver = "org.postgresql.Driver"
  val url = "jdbc:postgresql://db/postgres"
  val outputDir = "src/main/scala"
  val pkg = "forDB"
  val user = "root"
  val password = "root"

  slick.codegen.SourceCodeGenerator.main(
    Array(slickDriver, jdbcDriver, url, outputDir, pkg, user, password))
}
