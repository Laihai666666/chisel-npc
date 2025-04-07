package npc

import chisel3._
import chisel3.util._

import config.Configs._
import utils._

class WBUIO extends Bundle {
  val in    = Flipped(Decoupled(new LSU_O()))
  val out   = new WBU_O()
  val pc    = Output(UInt(DATA_WIDTH.W))
  val finsh = Output(Bool())
}

class WBU extends Module {
  val io        = IO(new WBUIO())
  val registers = Module(new Registers())
  val pcreg     = Module(new PCReg())
  val csrs      = Module(new CSR())
  val enable = WireDefault(false.B)
  /*val s_idle :: s_writing :: Nil = Enum(2)
  val state                      = RegInit(s_idle)
  state := MuxLookup(state, s_idle)(
    List(
      s_idle    -> Mux(io.in.fire, s_writing, s_idle),
      s_writing -> Mux(io.finsh, s_idle, s_writing)
    )
  )
  when(state === s_idle) {
    io.finsh := false.B
  }.otherwise {
    io.finsh := true.B
  }
  */
  io.finsh := enable
  io.in.ready := true.B
  when(io.in.valid) {
    enable                    := true.B
    registers.io.ctrlRegWrite := io.in.bits.ctrlRegWrite
    csrs.io.ctrlcsrWrite      := io.in.bits.ctrlcsrWrite
    csrs.io.ctrlecall         := io.in.bits.ctrlecall
  }.otherwise {
    enable                    := false.B
    registers.io.ctrlRegWrite := false.B
    csrs.io.ctrlcsrWrite      := false.B
    csrs.io.ctrlecall         := false.B
  }

  registers.io.dataWrite := io.in.bits.resultdata
  registers.io.csr       := csrs.io.csr
  registers.io.rs1       := io.in.bits.rs1
  registers.io.rs2       := io.in.bits.rs2
  registers.io.rd        := io.in.bits.rd
  registers.io.ctrlJump  := io.in.bits.ctrlJump
  registers.io.ctrlcs    := io.in.bits.ctrlcs
  registers.io.pc        := io.in.bits.pc
  io.out.dataRead1       := registers.io.dataRead1
  io.out.dataRead2       := registers.io.dataRead2

  pcreg.io.ctrlJump     := io.in.bits.ctrlJump
  pcreg.io.enable       := enable
  pcreg.io.ctrlBranch   := io.in.bits.ctrlBranch
  pcreg.io.resultBranch := io.in.bits.resultBranch
  pcreg.io.addrTarget   := io.in.bits.resultdata
  io.pc                 := pcreg.io.pc

  csrs.io.ctrlcsr := io.in.bits.ctrlcsr
  csrs.io.data    := io.in.bits.resultdata
  csrs.io.pc      := pcreg.io.pc
  csrs.io.reg17   := registers.io.reg17
  io.out.csr      := csrs.io.csr
}
