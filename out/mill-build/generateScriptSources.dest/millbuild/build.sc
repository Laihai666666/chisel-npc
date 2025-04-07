package millbuild

import _root_.mill.runner.MillBuildRootModule

object MiscInfo_build {
  implicit lazy val millBuildRootModuleInfo: _root_.mill.runner.MillBuildRootModule.Info = _root_.mill.runner.MillBuildRootModule.Info(
    Vector("/home/lh/ysyx/ysyx-workbench/chisel/out/mill-launcher/0.11.7.jar").map(_root_.os.Path(_)),
    _root_.os.Path("/home/lh/ysyx/ysyx-workbench/chisel"),
    _root_.os.Path("/home/lh/ysyx/ysyx-workbench/chisel"),
  )
  implicit lazy val millBaseModuleInfo: _root_.mill.main.RootModule.Info = _root_.mill.main.RootModule.Info(
    millBuildRootModuleInfo.projectRoot,
    _root_.mill.define.Discover[build]
  )
}
import MiscInfo_build.{millBuildRootModuleInfo, millBaseModuleInfo}
object build extends build
class build extends _root_.mill.main.RootModule {

//MILL_ORIGINAL_FILE_PATH=/home/lh/ysyx/ysyx-workbench/chisel/build.sc
//MILL_USER_CODE_START_MARKER
import mill._
import mill.scalalib._
import mill.scalalib.scalafmt.ScalafmtModule
import mill.scalalib.TestModule.Utest
// support BSP
import mill.bsp._

object npc extends ScalaModule with ScalafmtModule { m =>
  override def scalaVersion = "2.13.12"
  override def scalacOptions = Seq(
    "-language:reflectiveCalls",
    "-deprecation",
    "-feature",
    "-Xcheckinit",
  )
  
  override def forkArgs = Seq(
    "-Xmx8G",
    "-Xss128M",
    "-Dchisel3.firrtlOptions=--target-dir build --infer-rw --repl-seq-mem -X verilog"
  )
  val chiselVersion = "6.5.0"
  def sources = T.sources {
    super.sources() ++ Seq(
      PathRef(os.pwd ),
    )
  }
  override def ivyDeps = Agg(
    ivy"org.chipsalliance::chisel:$chiselVersion"
  )

  override def scalacPluginIvyDeps = Agg(
    ivy"org.chipsalliance:::chisel-plugin:$chiselVersion"
  )

  object test extends ScalaTests with TestModule.ScalaTest {
    override def ivyDeps = m.ivyDeps() ++ Agg(
      ivy"org.scalatest::scalatest::3.2.19"
    )
  }
  def repositoriesTask = T.task {
      Seq(
        coursier.MavenRepository("http://mirrors.cloud.tencent.com/nexus/repository/maven-public"),
        coursier.MavenRepository(
          "https://repo.scala-sbt.org/scalasbt/maven-releases"
        )
      ) ++ super.repositoriesTask()
  }
}

}