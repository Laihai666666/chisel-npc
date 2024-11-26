package npc

import chisel3._
import chisel3.util._

import config.Configs._
import utils.OP_TYPES._
import utils._

class LSUIO extends Bundle {
  val in  = Flipped(Decoupled(new EXU_O))
  val out = Decoupled(new LSU_O)
  val axi = new AXI_IO()
}

class LSU extends Module {
  val io = IO(new LSUIO())

  val s_idle :: s_r_busy :: s_w_busy :: s_valid :: s_r_wait_ready ::  s_w_wait_ready :: Nil = Enum(6)
  val state = RegInit(s_idle)
  
  state := MuxLookup(state, s_idle)(
    List(
      s_idle          -> Mux(io.in.fire  & io.axi.out.arvalid, s_r_busy,Mux(io.in.fire & io.axi.out.awvalid, s_w_busy, Mux(io.in.fire,s_valid , s_idle))),
      s_valid         -> Mux(io.out.fire, s_idle, s_valid),
      s_r_busy        -> Mux(io.axi.in.arready, s_r_wait_ready, s_r_busy),
      s_r_wait_ready  -> Mux(io.out.fire, s_idle, s_r_wait_ready),
      s_w_busy        -> Mux(io.axi.out.wvalid, s_w_wait_ready, s_w_busy),
      s_w_wait_ready  -> Mux(io.out.fire, s_idle, s_w_wait_ready)
    )
  )
  val rresp  = WireDefault(0.U(2.W))
  val rdata  = WireDefault(0.U(32.W))
  val bresp  = WireDefault(0.U(2.W))
  when(state === s_valid) {
    io.out.valid := true.B
  }.elsewhen(state === s_r_wait_ready) {
    rdata        := io.axi.in.rdata
    io.out.valid := true.B
  }.elsewhen(state === s_w_busy) {
    io.out.valid := false.B
  }.elsewhen(state === s_w_wait_ready) {
    io.out.valid := (bresp === 0.U)
  }.otherwise {
    io.out.valid := false.B
  }
  io.in.ready        := true.B
  rresp              := io.axi.in.rresp
  bresp              := io.axi.in.bresp
  io.axi.out.rready  := true.B
  io.axi.out.arvalid := io.in.bits.ctrlLoad
  io.axi.out.araddr  := io.in.bits.resultAlu
  io.axi.out.awaddr  := io.in.bits.resultAlu
  io.axi.out.awvalid := io.in.bits.ctrlStore
  io.axi.out.wstrb   := io.in.bits.ctrlLSType
  io.axi.out.wvalid  := (state===s_w_busy)
  io.axi.out.wdata   := io.in.bits.wdata
  io.axi.out.bready  := true.B
  val result = WireDefault(0.U(DATA_WIDTH.W))
  when(io.in.bits.ctrlLoad) {
    when(io.in.bits.ctrlLSType === LS_W) {
            result := rdata
    }.elsewhen(io.in.bits.ctrlLSType === LS_H) {
            when (io.in.bits.ctrlSigned) {
                result := Cat(Fill(16, rdata(15)), rdata(15, 0))
            } .otherwise {
                result := Cat(Fill(16, 0.U),rdata(15, 0))
            }
    }.otherwise {
            when (io.in.bits.ctrlSigned) {
                result := Cat(Fill(24, rdata(7)),rdata(7, 0))
            } .otherwise {
                result := Cat(Fill(24, 0.U), rdata(7, 0))
            }
    } 
  }.otherwise {
    result := io.in.bits.resultAlu
  }
  io.out.bits.resultdata   := result
  io.out.bits.ctrlRegWrite := io.in.bits.ctrlRegWrite
  io.out.bits.ctrlJump     := io.in.bits.ctrlJump
  io.out.bits.ctrlcs       := io.in.bits.ctrlcs
  io.out.bits.ctrlBranch   := io.in.bits.ctrlBranch
  io.out.bits.ctrlcsr      := io.in.bits.ctrlcsr
  io.out.bits.ctrlcsrWrite := io.in.bits.ctrlcsrWrite
  io.out.bits.ctrlecall    := io.in.bits.ctrlecall
  io.out.bits.ctrlbreak    := io.in.bits.ctrlbreak
  io.out.bits.rs1          := io.in.bits.rs1
  io.out.bits.rs2          := io.in.bits.rs2
  io.out.bits.rd           := io.in.bits.rd
  io.out.bits.pc           := io.in.bits.pc
  io.out.bits.resultBranch := io.in.bits.resultBranch

}
