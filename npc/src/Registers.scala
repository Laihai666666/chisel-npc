package npc

import chisel3._
import chisel3.util._

import config.Configs._
import utils._

class RegistersIO extends Bundle {
  val ctrlRegWrite = Input(Bool())
  val dataWrite    = Input(UInt(DATA_WIDTH.W))
  val csr          = Input(UInt(DATA_WIDTH.W))
  val rs1          = Input(UInt(REG_NUMS_LOG.W))
  val rs2          = Input(UInt(REG_NUMS_LOG.W))
  val rd           = Input(UInt(REG_NUMS_LOG.W))
  val ctrlJump     = Input(Bool())
  val ctrlcs       = Input(Bool())
  val pc           = Input(UInt(ADDR_WIDTH.W))
  val dataRead1    = Output(UInt(DATA_WIDTH.W))
  val dataRead2    = Output(UInt(DATA_WIDTH.W))
  val reg17        = Output(UInt(DATA_WIDTH.W))
}

class Registers extends Module {
  val io = IO(new RegistersIO())
  when(io.rs1 === 0.U) {
    io.dataRead1 := 0.U
  }
  when(io.rs2 === 0.U) {
    io.dataRead2 := 0.U
  }

  val regs = Reg(Vec(REG_NUMS, UInt(DATA_WIDTH.W)))
  // 给出写信号，且rd不为0时写寄存器

  when(io.ctrlRegWrite && io.rd =/= 0.U) {
    when(io.ctrlcs) {
      regs(io.rd) := io.csr
    }.elsewhen(io.ctrlJump) {
      regs(io.rd) := io.pc + 4.U
    }.otherwise {
      regs(io.rd) := io.dataWrite
    }
  }
  io.dataRead1 := regs(io.rs1)
  io.dataRead2 := regs(io.rs2)
  io.reg17     := regs(17.U)

}
