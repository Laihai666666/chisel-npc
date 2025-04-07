error id: file://<WORKSPACE>/npc/src/Top.scala:[3510..3517) in Input.VirtualFile("file://<WORKSPACE>/npc/src/Top.scala", "package npc

import chisel3._
import chisel3.util._
import _root_.circt.stage.ChiselStage

import config.Configs._
import utils._

class TopIO extends Bundle {
  val interrupt = Input(Bool())
  val master =new AXI()
  val slave =Flipped(new AXI())
}

class ysyx_23060351 extends Module {
  val io       = IO(new TopIO())
  val aribiter = Module(new Aribiter)
  val ifu      = Module(new IFU)
  val idu      = Module(new IDU)
  val exu      = Module(new EXU)
  val lsu      = Module(new LSU)
  val wbu      = Module(new WBU)
  StageConnect(ifu.io.out, idu.io.in)
  StageConnect(idu.io.out, exu.io.in)
  StageConnect(exu.io.out, lsu.io.in)
  StageConnect(lsu.io.out, wbu.io.in)

  ifu.io.pc      := wbu.io.pc
  ifu.io.ifu_req := wbu.io.finsh
  exu.io.wd      := wbu.io.out

  aribiter.io.ifu_ack     := ifu.io.ifu_ack
  aribiter.io.lsu_ack     := wbu.io.finsh
  ifu.io.axi.in           := aribiter.io.axi_ifu.in
  lsu.io.axi.in           := aribiter.io.axi_lsu.in
  aribiter.io.axi_ifu.out := ifu.io.axi.out
  aribiter.io.axi_lsu.out := lsu.io.axi.out

  aribiter.io.axi_soc.in.awready := io.master.awready
  aribiter.io.axi_soc.in.wready  := io.master.wready
  aribiter.io.axi_soc.in.bvalid  := io.master.bvalid
  aribiter.io.axi_soc.in.bresp   := io.master.bresp
  aribiter.io.axi_soc.in.bid     := io.master.bid
  aribiter.io.axi_soc.in.arready := io.master.arready
  aribiter.io.axi_soc.in.rvalid  := io.master.rvalid
  aribiter.io.axi_soc.in.rdata   := io.master.rdata
  aribiter.io.axi_soc.in.rresp   := io.master.rresp
  aribiter.io.axi_soc.in.rlast   := io.master.rlast
  aribiter.io.axi_soc.in.rid     := io.master.rid
  io.master.awaddr               := aribiter.io.axi_soc.out.awaddr
  io.master.awvalid              := aribiter.io.axi_soc.out.awvalid
  io.master.awid                 := aribiter.io.axi_soc.out.awid
  io.master.awlen                := aribiter.io.axi_soc.out.awlen
  io.master.awsize               := aribiter.io.axi_soc.out.awsize
  io.master.awburst              := aribiter.io.axi_soc.out.awburst
  io.master.wvalid               := aribiter.io.axi_soc.out.wvalid
  io.master.wdata                := aribiter.io.axi_soc.out.wdata
  io.master.wstrb                := aribiter.io.axi_soc.out.wstrb
  io.master.wlast                := aribiter.io.axi_soc.out.wlast
  io.master.bready               := aribiter.io.axi_soc.out.bready
  io.master.arvalid              := aribiter.io.axi_soc.out.arvalid
  io.master.araddr               := aribiter.io.axi_soc.out.araddr
  io.master.arid                 := aribiter.io.axi_soc.out.arid
  io.master.arlen                := aribiter.io.axi_soc.out.arlen
  io.master.arsize               := aribiter.io.axi_soc.out.arsize
  io.master.arburst              := aribiter.io.axi_soc.out.arburst
  io.master.rready               := aribiter.io.axi_soc.out.rready

  io.slave.awready := 0.U
  io.slave.wready  := 0.U
  io.slave.bvalid  := 0.U
  io.slave.bresp   := 0.U
  io.slave.bid     := 0.U
  io.slave.arready := 0.U
  io.slave.rvalid  := 0.U
  io.slave.rdata   := 0.U
  io.slave.rresp   := 0.U
  io.slave.rlast   := 0.U
  io.slave.rid     := 0.U

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
object  extends App {
  ChiselStage.emitSystemVerilogFile(
    new Top,
    args = Array(
      "--target-dir",
      "<WORKSPACE>/build",
      "--split-verilog",
      "--throw-on-first-error"
    ),
    firtoolOpts = Array("-disable-all-randomization", "-strip-debug-info")
  )
}
")
file://<WORKSPACE>/file:<WORKSPACE>/npc/src/Top.scala
file://<WORKSPACE>/npc/src/Top.scala:94: error: expected identifier; obtained extends
object  extends App {
        ^
#### Short summary: 

expected identifier; obtained extends