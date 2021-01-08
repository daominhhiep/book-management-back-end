import sbt._
import Keys._
import Dependencies._
import Settings._
import com.typesafe.config._

name := "bm_"
version := "0.1"
description := "https://tool.devsep.com/wiki/pages/viewpage.action?pageId=98153563"
organization := "septeni-technology"

lazy val root = (project in file("."))
  .enablePlugins(PlayScala)
  .enablePlugins(FlywayPlugin)
  .disablePlugins(PlayLayoutPlugin)
  .aggregate(application, domain, port, utility)
  .dependsOn(application, domain, port, utility)
  .settings(commonSettings: _*)
  .settings(libraryDependencies ++= Seq(filters, guice, scalaGuice))
  .settings(routesGenerator := InjectedRoutesGenerator)

lazy val application = (project in file("app/application"))
  .enablePlugins(PlayScala)
  .disablePlugins(PlayLayoutPlugin)
  .dependsOn(utility , domain)
  .settings(commonSettings: _*)

lazy val domain = (project in file("app/domain"))
  .enablePlugins(PlayScala)
  .disablePlugins(PlayLayoutPlugin)
  .dependsOn(utility)
  .settings(commonSettings: _*)

lazy val utility = (project in file("app/utility"))
  .settings(commonSettings: _*)
  .settings(libraryDependencies ++= portUtilityDependencies)

lazy val port = (project in file("app/port"))
  .aggregate(portWebService, portDatabase, portHttp)
  .dependsOn(portWebService, portDatabase, portHttp)
  .settings(commonSettings: _*)

lazy val portWebService = (project in file("app/port/primary/webService"))
  .enablePlugins(PlayScala, SbtWeb)
  .disablePlugins(PlayLayoutPlugin)
  .dependsOn(utility, application)
  .settings(commonSettings: _*)

lazy val portDatabase = (project in file("app/port/secondary/databaseMySQL"))
  .enablePlugins(ScalikejdbcPlugin)
  .dependsOn(utility, application)
  .settings(libraryDependencies ++= portDatabaseDependencies)
  .settings(commonSettings: _*)

lazy val portHttp = (project in file("app/port/secondary/http"))
  .enablePlugins(PlayScala)
  .disablePlugins(PlayLayoutPlugin)
  .dependsOn(utility, application)
  .settings(commonSettings: _*)
  .settings(libraryDependencies ++= Seq(ws))

lazy val dbConfig = ConfigFactory.parseFile(file("app/port/secondary/databaseMySQL/src/main/resources/db.application.conf")).resolve()
lazy val dbTestConfig = ConfigFactory.parseFile(file("app/port/secondary/databaseMySQL/src/test/resources/application.conf")).resolve()

flywayLocations := Seq("filesystem:app/port/secondary/databaseMySQL/src/main/resources/db/migration")
flywayUrl := dbConfig.getString("db.default.url")
flywayUser := dbConfig.getString("db.default.username")
flywayPassword := dbConfig.getString("db.default.password")
flywayUrl in Test := dbTestConfig.getString("db.default.url")
flywayUser in Test:= dbTestConfig.getString("db.default.username")
flywayPassword in Test:= dbTestConfig.getString("db.default.password")

initialCommands := """
import scalikejdbc._
implicit val session = AutoSession
"""



