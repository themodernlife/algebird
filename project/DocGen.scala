package algebird

import sbt._
import Keys._

import com.typesafe.sbt.git.GitRunner
import com.typesafe.sbt.SbtGit.GitKeys
import com.typesafe.sbt.SbtSite.{ site, SiteKeys }
import com.typesafe.sbt.SbtGhPages.{ ghpages, GhPagesKeys => ghkeys }
import com.typesafe.sbt.SbtGit.GitKeys.gitRemoteRepo

object DocGen {
  val docDirectory = "target/site"

  def syncLocal = (ghkeys.updatedRepository, GitKeys.gitRunner, streams) map { (repo, git, s) =>
    cleanSite(repo, git, s) // First, remove 'stale' files.
    val rootPath = file(docDirectory) // Now copy files.
    IO.copyDirectory(rootPath, repo)
    IO.touch(repo / ".nojekyll")
    repo
    }

  private def cleanSite(dir: File, git: GitRunner, s: TaskStreams): Unit = {
    val toClean = IO.listFiles(dir).filterNot(_.getName == ".git").map(_.getAbsolutePath).toList
    if(!toClean.isEmpty)
      git(("rm" :: "-r" :: "-f" :: "--ignore-unmatch" :: toClean) :_*)(dir, s.log)
    ()
  }

  // Allow forked repos to hack on docs by pushing to their own GitHub pages
  def docGenRemoteRepo: String = System.getProperty("git.remote.repo", "git@github.com:twitter/algebird.git")

  def githubBase(v: String): String = {
    val tagOrBranch = if (v.endsWith("-SNAPSHOT")) "develop" else v
    "https://github.com/twitter/algebird/tree/" + tagOrBranch
  }

  lazy val docGenSettings: Seq[sbt.Setting[_]] =
    Seq(
      scalacOptions in doc <++= (version, baseDirectory in LocalProject("algebird")).map { (v, rootBase) =>
        val docSourceUrl = githubBase(v) + "€{FILE_PATH}.scala"
        Seq("-sourcepath", rootBase.getAbsolutePath, "-doc-source-url", docSourceUrl)
      },
      Unidoc.unidocDirectory := file(docDirectory),
      gitRemoteRepo := docGenRemoteRepo,
      ghkeys.synchLocal <<= syncLocal
    )

  lazy val publishSettings = site.settings ++ Unidoc.settings ++ ghpages.settings ++ docGenSettings
}
