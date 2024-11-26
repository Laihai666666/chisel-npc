package npc

import chisel3._
import chisel3.util._
import _root_.circt.stage.ChiselStage

import config.Configs._
import utils._

// Top的模块接口，用于测试
class TopIO extends Bundle {
  val clockCounter = Output(UInt(32.W))
}

class Top extends Module {
  val io = IO(new TopIO())
  val clockCounter = RegInit(0.U(32.W))
  clockCounter    := clockCounter + 1.U
  io.clockCounter := clockCounter
  val aribiter=Module(new Aribiter)
  val ifu = Module(new IFU)
  val idu = Module(new IDU)
  val exu = Module(new EXU)
  val lsu = Module(new LSU)
  val wbu = Module(new WBU)
  StageConnect(ifu.io.out, idu.io.in)
  StageConnect(idu.io.out, exu.io.in)
  StageConnect(exu.io.out, lsu.io.in)
  StageConnect(lsu.io.out, wbu.io.in)
  ifu.io.pc       := wbu.io.pc
  ifu.io.ifu_req := wbu.io.finsh
  exu.io.wd       := wbu.io.out
  aribiter.io.ifu_ack:=ifu.io.ifu_ack
  aribiter.io.lsu_ack:=wbu.io.finsh
  ifu.io.axi.in:=aribiter.io.axi_ifu.in
  aribiter.io.axi_ifu.out:=ifu.io.axi.out
  lsu.io.axi.in:=aribiter.io.axi_lsu.in
  aribiter.io.axi_lsu.out:=lsu.io.axi.out
}

object StageConnect {
  def apply[T <: Data](left: DecoupledIO[T], right: DecoupledIO[T]) = {
    val arch = "multi"
    // 为展示抽象的思想, 此处代码省略了若干细节
    if (arch == "single") { right.bits := left.bits }
    else if (arch == "multi") { right <> left }
    else if (arch == "pipeline") { right <> RegEnable(left, left.fire) }
    else if (arch == "ooo") { right <> Queue(left, 16) }
  }
}
object Top extends App {
  ChiselStage.emitSystemVerilogFile(
    new Top,
    args = Array(
      "--target-dir",
      "/home/lh/ysyx/ysyx-workbench/npc/chisel/build",
      "--split-verilog",
      "--throw-on-first-error"
    ),
    firtoolOpts = Array("-disable-all-randomization", "-strip-debug-info")
  )
}
