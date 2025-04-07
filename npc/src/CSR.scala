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
  val mvendorid =RegInit(UInt(ADDR_WIDTH.W), 0x79737978.U)
  val marchid =RegInit(UInt(ADDR_WIDTH.W), 0x23060351.U)
  when(io.ctrlcsr === "h341".U) {
    io.csr := mepc
  }.elsewhen(io.ctrlcsr === "h342".U) {
    io.csr := mcause
  }.elsewhen(io.ctrlcsr === "h300".U) {
    io.csr := mstatus
  }.elsewhen(io.ctrlcsr === "h305".U) {
    io.csr := mtvec
  }.elsewhen(io.ctrlcsr === "h400".U) {
    io.csr := mvendorid
  }.elsewhen(io.ctrlcsr === "h401".U) {
    io.csr := marchid
  }.otherwise {
    io.csr := 0.U
  }
  mepc:=Mux(io.ctrlecall,io.pc,Mux(io.ctrlcsrWrite&(io.ctrlcsr === "h341".U),io.data,mepc))
  mcause:=Mux(io.ctrlecall,io.reg17,Mux(io.ctrlcsrWrite&(io.ctrlcsr === "h342".U),io.data,mcause))
  mstatus:=Mux(io.ctrlcsrWrite&(io.ctrlcsr === "h300".U),io.data,mstatus)
  mtvec:=Mux(io.ctrlcsrWrite&(io.ctrlcsr === "h305".U),io.data,mtvec)
 
}
