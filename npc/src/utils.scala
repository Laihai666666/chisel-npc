package utils

import chisel3._
import config.Configs._
object OP_TYPES {
  val OP_NOP = "b0000".U
  val OP_ADD = "b0001".U
  val OP_SUB = "b0010".U
  val OP_AND = "b0100".U
  val OP_OR  = "b0101".U
  val OP_XOR = "b0111".U
  val OP_SLL = "b1000".U
  val OP_SRL = "b1001".U
  val OP_SRA = "b1011".U
  val OP_EQ  = "b1100".U
  val OP_NEQ = "b1101".U
  val OP_LT  = "b1110".U
  val OP_GE  = "b1111".U
}
class Message extends Bundle {
  val inst = Output(UInt(DATA_WIDTH.W))
  val pc   = Output(UInt(32.W))
}
// 用于连接控制模块的Bundle
class IDU_O extends Bundle {
  val ctrlJump     = Output(Bool())
  val ctrlRegWrite = Output(Bool())
  val ctrlLoad     = Output(Bool())
  val ctrlStore    = Output(Bool())
  val ctrlLSType   = Output(UInt(8.W))
  val ctrlALUSrc   = Output(Bool())
  val ctrlJAL      = Output(Bool())
  val ctrlBranch   = Output(Bool())
  val ctrlOP       = Output(UInt(OP_TYPES_WIDTH.W))
  val ctrlSigned   = Output(Bool())
  val ctrlbreak    = Output(Bool())
  val rs1          = Output(UInt(REG_NUMS_LOG.W))
  val rs2          = Output(UInt(REG_NUMS_LOG.W))
  val rd           = Output(UInt(REG_NUMS_LOG.W))
  val imm          = Output(UInt(DATA_WIDTH.W))
  val ctrlcs       = Output(Bool())
  val ctrlcsr      = Output(UInt(12.W))
  val ctrlcsrWrite = Output(Bool())
  val ctrlecall    = Output(Bool())
  val ctrloneop    = Output(Bool())
  val pc           = Output(UInt(DATA_WIDTH.W))
}

class EXU_O extends Bundle {
  val resultAlu    = Output(UInt(DATA_WIDTH.W))
  val resultBranch = Output(Bool())
  val ctrlRegWrite = Output(Bool())
  val ctrlLoad     = Output(Bool())
  val ctrlStore    = Output(Bool())
  val ctrlJump     = Output(Bool())
  val ctrlcs       = Output(Bool())
  val ctrlBranch   = Output(Bool())
  val ctrlcsr      = Output(UInt(12.W))
  val ctrlcsrWrite = Output(Bool())
  val ctrlLSType   = Output(UInt(8.W))
  val ctrlSigned   = Output(Bool())
  val ctrlecall    = Output(Bool())
  val ctrlbreak    = Output(Bool())
  val rs1          = Output(UInt(REG_NUMS_LOG.W))
  val rs2          = Output(UInt(REG_NUMS_LOG.W))
  val rd           = Output(UInt(REG_NUMS_LOG.W))
  val wdata        = Output(UInt(DATA_WIDTH.W))
  val pc           = Output(UInt(DATA_WIDTH.W))
}
class LSU_O extends Bundle {
  val resultdata   = Output(UInt(DATA_WIDTH.W))
  val resultBranch = Output(Bool())
  val ctrlRegWrite = Output(Bool())
  val ctrlJump     = Output(Bool())
  val ctrlcs       = Output(Bool())
  val ctrlBranch   = Output(Bool())
  val ctrlcsr      = Output(UInt(12.W))
  val ctrlcsrWrite = Output(Bool())
  val ctrlecall    = Output(Bool())
  val ctrlbreak    = Output(Bool())
  val rs1          = Output(UInt(REG_NUMS_LOG.W))
  val rs2          = Output(UInt(REG_NUMS_LOG.W))
  val rd           = Output(UInt(REG_NUMS_LOG.W))
  val pc           = Output(UInt(DATA_WIDTH.W))
}

class WBU_O extends Bundle {
  val dataRead1 = Output(UInt(DATA_WIDTH.W))
  val dataRead2 = Output(UInt(DATA_WIDTH.W))
  val csr       = Output(UInt(DATA_WIDTH.W))
}

class AXI_IO extends Bundle {
  val in =new AXI_I()
  val out=new AXI_O()
}
class AXI_I extends Bundle {
  val arready = Input(Bool())
  val rdata   = Input(UInt(DATA_WIDTH.W))
  val rresp   = Input(UInt(2.W))
  val rvalid  = Input(Bool())
  val awready = Input(Bool())
  val wready  = Input(Bool())
  val bresp   = Input(UInt(2.W))
  val bvalid  = Input(Bool())
}
class AXI_O extends Bundle {
  val araddr  = Output(UInt(DATA_WIDTH.W))
  val arvalid = Output(Bool())
  val rready  = Output(Bool())
  val awaddr  = Output(UInt(DATA_WIDTH.W))
  val awvalid = Output(Bool())
  val wdata   = Output(UInt(DATA_WIDTH.W))
  val wstrb   = Output(UInt(8.W))
  val wvalid  = Output(Bool())
  val bready  = Output(Bool())
}