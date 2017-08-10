name := "DataCollection"

version := "1.0"

scalaVersion := "2.11.11"

// https://mvnrepository.com/artifact/joda-time/joda-time
libraryDependencies += "joda-time" % "joda-time" % "2.9.9"

// https://mvnrepository.com/artifact/com.typesafe/config
libraryDependencies += "com.typesafe" % "config" % "1.3.1"

// https://mvnrepository.com/artifact/org.apache.logging.log4j/log4j-core
libraryDependencies += "org.apache.logging.log4j" % "log4j-core" % "2.8.2"

// https://mvnrepository.com/artifact/org.apache.logging.log4j/log4j-api
libraryDependencies += "org.apache.logging.log4j" % "log4j-api" % "2.8.2"

// https://mvnrepository.com/artifact/org.scala-lang.modules/scala-xml_2.11
libraryDependencies += "org.scala-lang.modules" % "scala-xml_2.11" % "1.0.6"

// https://mvnrepository.com/artifact/net.sourceforge.htmlcleaner/htmlcleaner
libraryDependencies += "net.sourceforge.htmlcleaner" % "htmlcleaner" % "2.6.1"

// http://slick.lightbend.com/doc/3.2.0/gettingstarted.html
libraryDependencies ++= Seq(
  "com.typesafe.slick" %% "slick" % "3.2.0",
  //"org.slf4j" % "slf4j-nop" % "1.6.4",
  "com.typesafe.slick" %% "slick-hikaricp" % "3.2.0"
)

// http://slick.lightbend.com/doc/3.2.0/code-generation.html
//libraryDependencies += "com.typesafe.slick" %% "slick-codegen" % "3.2.0"


// https://mvnrepository.com/artifact/com.typesafe.slick/slick-extensions_2.11
libraryDependencies += "com.typesafe.slick" % "slick-extensions_2.11" % "3.1.0"

// https://mvnrepository.com/artifact/com.google.apis/google-api-services-analyticsreporting
libraryDependencies += "com.google.apis" % "google-api-services-analyticsreporting" % "v4-rev114-1.22.0"

// https://mvnrepository.com/artifact/com.google.api-client/google-api-client-gson
libraryDependencies += "com.google.api-client" % "google-api-client-gson" % "1.22.0"

unmanagedJars in Compile += file("lib/ojdbc6-11.2.0.3.jar")







        