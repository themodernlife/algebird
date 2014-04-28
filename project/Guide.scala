package algebird

import sbt._
import sbt.Keys._
import sbt.Project.Initialize
import com.typesafe.sbt.SbtSite.site
import com.typesafe.sbt.site.SphinxSupport
import com.typesafe.sbt.site.SphinxSupport.{ enableOutput, generatePdf, generatedPdf, generateEpub, generatedEpub, sphinxInputs, sphinxPackages, Sphinx }
import com.typesafe.sbt.preprocess.Preprocess.{ preprocess, preprocessExts, preprocessVars, simplePreprocess }

object Guide {
  lazy val settings = site.settings ++ site.sphinxSupport() ++ sphinxPreprocessing ++ Seq(
    sourceDirectory in Sphinx <<= baseDirectory { _ / "rst" },
    sphinxPackages in Sphinx <+= baseDirectory { _ / "_sphinx" / "pygments" },
    unmanagedSourceDirectories in Test <<= sourceDirectory in Sphinx apply { _ ** "code" get },
    publishArtifact := false
  )

  // preprocessing settings for sphinx
  lazy val sphinxPreprocessing = inConfig(Sphinx)(Seq(
    target in preprocess <<= baseDirectory { _ / "rst_preprocessed" },
    preprocessExts := Set("rst", "py"),
    // @<key>@ replacements, add additional replacements here
    preprocessVars <<= (scalaVersion, version) { (s, v) =>
      val BinVer = """(\d+\.\d+)\.\d+""".r
      Map(
        "version" -> v,
        "scalaVersion" -> s,
        "crossString" -> (s match {
            case BinVer(_) => ""
            case _         => "cross CrossVersion.full"
        }),
        "github" -> DocGen.githubBase(v)
      )
    },
    preprocess <<= (sourceDirectory, target in preprocess, cacheDirectory, preprocessExts, preprocessVars, streams) map {
      (src, target, cacheDir, exts, vars, s) => simplePreprocess(src, target, cacheDir / "sphinx" / "preprocessed", exts, vars, s.log)
    },
    sphinxInputs <<= (sphinxInputs, preprocess) map { (inputs, preprocessed) => inputs.copy(src = preprocessed) }
  )) ++ Seq(
    cleanFiles <+= target in preprocess in Sphinx
  )
}
