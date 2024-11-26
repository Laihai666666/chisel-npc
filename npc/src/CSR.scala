package npc

import chisel3._
import chisel3.util._

import config.Configs._
import utils._

class CSRIO extends Bundle {
  val ctrlcsrWrite = Input(Bool())
  val ctrlcsr      = Input(UInt(12.W))
  val data         = Input(UInt(DATA_WIDTH.W))
  val ctrlecall    = Input(Bool())
  val pc           = Input(UInt(DATA_WIDTH.W))
  val reg17        = Input(UInt(DATA_WIDTH.W))
  val csr          = Output(UInt(DATA_WIDTH.W))
}

class CSR extends Module {
  val io      = IO(new CSRIO())
  val mcause  = RegInit(UInt(ADDR_WIDTH.W), 0.U)
  val mepc    = RegInit(UInt(ADDR_WIDTH.W), 0.U)
  val mstatus = RegInit(UInt(ADDR_WIDTH.W), 0x1800.U)
  val mtvec   = RegInit(UInt(ADDR_WIDTH.W), 0.U)

  when(io.ctrlcsr === "h341".U) {
    io.csr := mepc
  }.elsewhen(io.ctrlcsr === "h342".U) {
    io.csr := mcause
  }.elsewhen(io.ctrlcsr === "h300".U) {
    io.csr := mstatus
  }.elsewhen(io.ctrlcsr === "h305".U) {
    io.csr := mtvec
  }.otherwise {
    io.csr := 0.U
  }
  when(io.ctrlcsrWrite) {
    when(io.ctrlcsr === "h341".U) {
      mepc := io.data
    }.elsewhen(io.ctrlcsr === "h342".U) {
      mcause := io.data
    }.elsewhen(io.ctrlcsr === "h300".U) {
      mstatus := io.data
    }.elsewhen(io.ctrlcsr === "h305".U) {
      mtvec := io.data
    }
  }
  when(io.ctrlecall) {
    mepc   := io.pc
    mcause := io.reg17
  }
}
