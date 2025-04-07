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
  val perf = Output(Bool()) // 数据加载计数器信号
}

class LSU extends Module {
  val io = IO(new LSUIO())
  val s_idle :: s_r_busy :: s_w_busy :: s_valid:: s_r_wait_rvalid::  s_w_wait_ready :: s_w_wait_bresp:: Nil = Enum(7)
  val state = RegInit(s_idle)
  state := MuxLookup(state, s_idle)(
    List(
      s_idle          -> Mux(io.in.fire  & io.in.bits.ctrlLoad, s_r_busy,Mux(io.in.fire & io.in.bits.ctrlStore, s_w_busy, Mux(io.in.fire,s_valid , s_idle))),
      s_valid         -> Mux(io.out.fire, s_idle, s_valid),
      s_r_busy        -> Mux(io.axi.in.arready,  s_r_wait_rvalid, s_r_busy),
      s_r_wait_rvalid  -> Mux(io.out.fire, s_idle, s_r_wait_rvalid), 
      s_w_busy        -> Mux(io.axi.in.awready, s_w_wait_ready, s_w_busy),
      s_w_wait_ready  -> Mux(io.axi.in.wready, s_w_wait_bresp, s_w_wait_ready),
      s_w_wait_bresp  -> Mux(io.out.fire,s_idle,s_w_wait_bresp)
    )
  )

  val rdata  = WireDefault(0.U(32.W))
  rdata        := io.axi.in.rdata>>((io.axi.out.araddr(1,0))*8.U)
  io.in.ready        := true.B
  io.out.valid:=(state === s_valid)|((state === s_r_wait_rvalid)&(io.axi.in.rvalid))|((state === s_w_wait_bresp)&(io.axi.in.bvalid))
  io.perf := (state === s_r_wait_rvalid) & (io.axi.in.rvalid) // 数据加载完成时触发计数器
  io.axi.out.rready  := true.B
  io.axi.out.arvalid := state===s_r_busy
  io.axi.out.araddr  := io.in.bits.resultAlu
  io.axi.out.arid     :=0.U
  io.axi.out.arlen    :=0.U
  io.axi.out.arburst  :=0.U
  io.axi.out.arsize   :=Mux(io.in.bits.ctrlLSType === LS_W,2.U,Mux(io.in.bits.ctrlLSType === LS_H,1.U,0.U))
  io.axi.out.awaddr  := io.in.bits.resultAlu
  io.axi.out.awvalid := state===s_w_busy
  io.axi.out.awid     :=0.U
  io.axi.out.awlen    :=0.U
  io.axi.out.awsize   :=Mux(io.in.bits.ctrlLSType === LS_W,2.U,Mux(io.in.bits.ctrlLSType === LS_H,1.U,0.U))
  io.axi.out.awburst  :=0.U
  io.axi.out.wstrb   := (io.in.bits.ctrlLSType<<(io.axi.out.araddr(1,0)))
  io.axi.out.wvalid  := state===s_w_wait_ready
  io.axi.out.wdata   := io.in.bits.wdata<<((io.axi.out.araddr(1,0))*8.U)
  io.axi.out.wlast    := state ===s_w_wait_ready
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
  io.out.bits.rs1          := io.in.bits.rs1
  io.out.bits.rs2          := io.in.bits.rs2
  io.out.bits.rd           := io.in.bits.rd
  io.out.bits.pc           := io.in.bits.pc
  io.out.bits.resultBranch := io.in.bits.resultBranch

}
