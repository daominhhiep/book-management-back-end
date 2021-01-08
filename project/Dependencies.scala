import sbt._

object Dependencies {

  //Db dependencies
  lazy val scalikejdbcVersion = "3.3.5"
  val scalikejdbc = "org.scalikejdbc" %% "scalikejdbc" % scalikejdbcVersion
  val scalikejdbcTest = "org.scalikejdbc" %% "scalikejdbc-test" % scalikejdbcVersion % Test
  val scalikejdbcConfig = "org.scalikejdbc" %% "scalikejdbc-config" % scalikejdbcVersion // read config from application.conf
  val scalikejdbcDbapiAdapter = "org.scalikejdbc" %% "scalikejdbc-play-dbapi-adapter" % "2.7.1-scalikejdbc-3.3"

  val skinnyOrm = "org.skinny-framework" %% "skinny-orm" % "3.0.3"

  val mysqlConnectorJava = "mysql" % "mysql-connector-java" % "8.0.11"
  val playFlyway = "org.flywaydb" %% "flyway-play" % "5.3.3"

  //Akka
  lazy val akkaVersion = "2.5.24"
  val akkaActor = "com.typesafe.akka" %% "akka-actor" % akkaVersion
  val akkaTestkit = "com.typesafe.akka" %% "akka-testkit" % akkaVersion % Test
  val akkaSl4j = "com.typesafe.akka" %% "akka-slf4j" % akkaVersion //logging
  val akkaQuartz = "com.enragedginger" %% "akka-quartz-scheduler" % "1.8.1-akka-2.5.x"

  val jodaTime = "joda-time" % "joda-time" % "2.10.3"
  val logger = "org.slf4j" % "slf4j-api" % "1.7.25"

  val playJson4s = "org.json4s" %% "json4s-jackson" % "3.6.7"
  val scalaGuice = "net.codingwell" %% "scala-guice" % "4.2.6"

  val portDatabaseDependencies = Seq(
    scalikejdbc, scalikejdbcConfig,
    scalikejdbcDbapiAdapter,
    scalikejdbcTest,
    mysqlConnectorJava,
    playFlyway,
    skinnyOrm
  )

  val portBatchDependencies = Seq(akkaQuartz, akkaActor, akkaSl4j, akkaTestkit)

  val portUtilityDependencies = Seq(jodaTime, logger, playJson4s)
}
