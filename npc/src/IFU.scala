package npc
import _root_.circt.stage.ChiselStage

import chisel3._
import chisel3.util._
import config.Configs._
import utils._
class IFU extends Module {
  val io = IO(new Bundle {
    val ifu_req = Input(Bool())
    val ifu_ack = Output(Bool())
    val pc      = Input(UInt(DATA_WIDTH.W))
    val out     = Decoupled(new Message())
    val axi     = new AXI_IO()
    val perf    = Output(Bool()) // 性能计数器信号
  })
  val s_idle :: s_r_busy ::s_wait_rvalid:: Nil = Enum(3)
  val state                                     = RegInit(s_idle)
  val inst                                      = RegInit(0.U(DATA_WIDTH.W))
  val read_i                                    = RegInit(true.B)

  read_i := io.ifu_req
  state := MuxLookup(state, s_idle)(
    List(
      s_idle       -> Mux(read_i, s_r_busy, s_idle),
      s_r_busy     -> Mux(io.axi.in.arready, s_wait_rvalid, s_r_busy),
      s_wait_rvalid -> Mux(io.out.fire, s_idle, s_wait_rvalid) 
    )
  )
  
  inst := Mux(state === s_wait_rvalid && io.axi.in.rvalid, io.axi.in.rdata, inst)
  io.out.valid := (state === s_wait_rvalid)&(io.axi.in.rvalid)&(io.axi.in.rresp===0.U)
  io.ifu_ack := io.out.valid
  io.perf := io.out.valid // 指令获取完成时触发计数器
  io.axi.out.rready  := true.B
  io.axi.out.arvalid := (state===s_r_busy)
  io.axi.out.araddr  := io.pc
  io.axi.out.arid    := 0.U
  io.axi.out.arlen   := 0.U
  io.axi.out.arsize  := 2.U
  io.axi.out.arburst := 0.U
  io.axi.out.awaddr  := 0.U
  io.axi.out.awvalid := false.B
  io.axi.out.wstrb   := 0.U
  io.axi.out.wvalid  := false.B
  io.axi.out.wdata   := 0.U
  io.axi.out.bready  := false.B
  io.axi.out.awid    := 0.U
  io.axi.out.awlen   := 0.U
  io.axi.out.awsize  := 0.U
  io.axi.out.awburst := 0.U
  io.axi.out.wlast   := false.B
  
  io.out.bits.inst := inst
  io.out.bits.pc   := io.pc
}
