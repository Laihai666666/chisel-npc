package npc
import _root_.circt.stage.ChiselStage

import chisel3._
import chisel3.util._
import config.Configs._
import utils._
class IFU extends Module {
  val io = IO(new Bundle {
    val ifu_req = Input(Bool())
    val ifu_ack  =Output(Bool())
    val pc       = Input(UInt(DATA_WIDTH.W))
    val out      = Decoupled(new Message())
    val axi      = new AXI_IO()
  })
  val s_idle :: s_r_busy :: s_wait_ready :: Nil = Enum(3)
  val state                                     = RegInit(s_idle)
  val inst                                      = RegInit(0.U(DATA_WIDTH.W))
  val rresp                                     = WireDefault(0.U(2.W))
  val read_i                                    = RegInit(true.B)
  read_i := io.ifu_req
  state := MuxLookup(state, s_idle)(
    List(
      s_idle       -> Mux(io.axi.out.arvalid, s_r_busy, s_idle),
      s_r_busy     -> Mux(io.axi.in.arready, s_wait_ready, s_r_busy),
      s_wait_ready -> Mux(io.out.fire, s_idle, s_wait_ready)
    )
  )

  when(state === s_wait_ready) {
    inst := io.axi.in.rdata
    when(rresp === 0.U) {
     io.out.valid := true.B
    }.otherwise {
     io.out.valid := false.B
    }
  }.otherwise {
    io.out.valid := false.B
  }
  io.ifu_ack:=io.out.valid
  rresp              := io.axi.in.rresp
  io.axi.out.rready  := true.B
  io.axi.out.arvalid := read_i|(state===s_r_busy)
  io.axi.out.araddr  := io.pc
  io.axi.out.awaddr  := 0.U
  io.axi.out.awvalid := false.B
  io.axi.out.wstrb   := 0.U
  io.axi.out.wvalid  := false.B
  io.axi.out.wdata   := 0.U
  io.axi.out.bready  := false.B
  io.out.bits.inst   := inst
  io.out.bits.pc     := io.pc
}
