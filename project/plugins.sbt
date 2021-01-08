// The Play plugin
addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.7.3")
addSbtPlugin("org.scalikejdbc" %% "scalikejdbc-mapper-generator" % "3.3.5") // auto migrate
addSbtPlugin("io.github.davidmweber" % "flyway-sbt" % "5.2.0") // run command migrate
//addSbtPlugin("org.scalariform" % "sbt-scalariform" % "1.8.3") // reformat code
//addSbtPlugin("org.scalastyle" %% "scalastyle-sbt-plugin" % "1.0.0") // check code style
