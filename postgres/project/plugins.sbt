addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.8.7")

resolvers += "Typesafe repository" at "https://repo.typesafe.com/typesafe/releases/"

addSbtPlugin("org.scalastyle" %% "scalastyle-sbt-plugin" % "1.0.0")

addSbtPlugin("com.github.tototoshi" % "sbt-slick-codegen" %"1.4.0")
libraryDependencies+=  "org.postgresql" % "postgresql" % "42.2.5"