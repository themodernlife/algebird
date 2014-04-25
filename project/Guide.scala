package algebird

import sbt._
import sbt.Keys._
import sbt.Project.Initialize
import com.typesafe.sbt.SbtSite.site
import com.typesafe.sbt.site.SphinxSupport
import com.typesafe.sbt.site.SphinxSupport.{ enableOutput, generatePdf, generatedPdf, generateEpub, generatedEpub, sphinxInputs, sphinxPackages, Sphinx }
import com.typesafe.sbt.preprocess.Preprocess.{ preprocess, preprocessExts, preprocessVars, simplePreprocess }

object Guide {

  lazy val settings = site.settings ++ site.sphinxSupport() ++ site.publishSite ++ sphinxPreprocessing ++ Seq(
    sourceDirectory in Sphinx <<= baseDirectory / "rst",
    sphinxPackages in Sphinx <+= baseDirectory { _ / "_sphinx" / "pygments" },
    enableOutput in generatePdf in Sphinx := true,
    enableOutput in generateEpub in Sphinx := true,
    unmanagedSourceDirectories in Test <<= sourceDirectory in Sphinx apply { _ ** "code" get },
    publishArtifact in Compile := false,
    //unmanagedSourceDirectories in ScalariformKeys.format in Test <<= unmanagedSourceDirectories in Test,
    testOptions += Tests.Argument(TestFrameworks.JUnit, "-v", "-a")
    //reportBinaryIssues := () // disable bin comp check
  )

  // preprocessing settings for sphinx
  lazy val sphinxPreprocessing = inConfig(Sphinx)(Seq(
    target in preprocess <<= baseDirectory / "rst_preprocessed",
    preprocessExts := Set("rst", "py"),
    // customization of sphinx @<key>@ replacements, add to all sphinx-using projects
    // add additional replacements here
    preprocessVars <<= (scalaVersion, version) { (s, v) =>
      val BinVer = """(\d+\.\d+)\.\d+""".r
      Map(
        "version" -> v,
        "scalaVersion" -> s,
        "crossString" -> (s match {
            case BinVer(_) => ""
            case _         => "cross CrossVersion.full"
          }),
        "jarName" -> (s match {
            case BinVer(bv) => "akka-actor_" + bv + "-" + v + ".jar"
            case _          => "akka-actor_" + s + "-" + v + ".jar"
          }),
        "binVersion" -> (s match {
            case BinVer(bv) => bv
            case _          => s
          }),
        "sigarVersion" -> Dependencies.Compile.sigar.revision,
        "github" -> githubUrl(v)
      )
    },
    preprocess <<= (sourceDirectory, target in preprocess, cacheDirectory, preprocessExts, preprocessVars, streams) map {
      (src, target, cacheDir, exts, vars, s) => simplePreprocess(src, target, cacheDir / "sphinx" / "preprocessed", exts, vars, s.log)
    },
    sphinxInputs <<= (sphinxInputs, preprocess) map { (inputs, preprocessed) => inputs.copy(src = preprocessed) }
  )) ++ Seq(
    cleanFiles <+= target in preprocess in Sphinx
  )

  object Dependencies {

    object Compile {
      val sigar       = "org.fusesource"                   % "sigar"                        % "1.6.4"            // ApacheV2
    }
  }

  def githubUrl(v: String): String = {
    val branch = if (v.endsWith("SNAPSHOT")) "master" else "v" + v
    "http://github.com/akka/akka/tree/" + branch
  }
}
