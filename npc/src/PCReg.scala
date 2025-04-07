package npc

import chisel3._
import chisel3.util._

import config.Configs._

class PCRegIO extends Bundle {
  val pc           = Output(UInt(ADDR_WIDTH.W))
  val enable       = Input(Bool())
  val ctrlJump     = Input(Bool())
  val ctrlBranch   = Input(Bool())
  val resultBranch = Input(Bool())
  val addrTarget   = Input(UInt(ADDR_WIDTH.W))
}

class PCReg extends Module {
  val io    = IO(new PCRegIO())
  val regPC = RegInit("h30000000".U(32.W))
  when(io.enable) {
    when(io.ctrlJump || (io.ctrlBranch && io.resultBranch)) {
      regPC := io.addrTarget
    }.otherwise {
      regPC := regPC + 4.U
    }
  }
  io.pc := regPC
}
