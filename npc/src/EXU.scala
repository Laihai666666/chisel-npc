package npc

import chisel3._
import chisel3.util._

import config.Configs._
import utils.OP_TYPES._
import utils._

class EXUIO extends Bundle {
  val in  = Flipped(Decoupled(new IDU_O))
  val out = Decoupled(new EXU_O)
  val wd  = Flipped(new WBU_O)
}

class EXU extends Module {
  val io = IO(new EXUIO())

  /*val s_idle :: s_execuing :: Nil = Enum(2)
    val state = RegInit(s_idle)

    state := MuxLookup(state, s_idle)(List(
        s_idle -> Mux(io.in.fire, s_execuing, s_idle),
        s_execuing -> Mux(io.out.fire, s_idle, s_execuing)
    ))
    io.in.ready:=1.U
    when(state===s_idle){
        io.out.valid:=0.U
    }otherwise{
        io.out.valid:=1.U
    }*/
  io.in.ready  := 1.U
  io.out.valid := io.in.valid
  val resultAlu    = WireDefault(0.U(DATA_WIDTH.W))
  val resultBranch = WireDefault(false.B)
  val oprand1      = WireDefault(0.U(DATA_WIDTH.W))
  val oprand2      = WireDefault(0.U(DATA_WIDTH.W))
  io.out.bits.ctrlRegWrite := io.in.bits.ctrlRegWrite
  io.out.bits.ctrlLoad     := io.in.bits.ctrlLoad
  io.out.bits.ctrlStore    := io.in.bits.ctrlStore
  io.out.bits.ctrlJump     := io.in.bits.ctrlJump
  io.out.bits.ctrlcs       := io.in.bits.ctrlcs
  io.out.bits.ctrlBranch   := io.in.bits.ctrlBranch
  io.out.bits.ctrlcsr      := io.in.bits.ctrlcsr
  io.out.bits.ctrlcsrWrite := io.in.bits.ctrlcsrWrite
  io.out.bits.ctrlLSType   := io.in.bits.ctrlLSType
  io.out.bits.ctrlSigned   := io.in.bits.ctrlSigned
  io.out.bits.ctrlecall    := io.in.bits.ctrlecall
  io.out.bits.ctrlbreak    := io.in.bits.ctrlbreak
  io.out.bits.rs1          := io.in.bits.rs1
  io.out.bits.rs2          := io.in.bits.rs2
  io.out.bits.rd           := io.in.bits.rd
  io.out.bits.wdata        := io.wd.dataRead2
  io.out.bits.pc           := io.in.bits.pc
  when(io.in.bits.ctrlJAL) {
    oprand1 := io.in.bits.pc
  }.elsewhen(io.in.bits.ctrloneop) {
    oprand1 := 0.U
  }.otherwise {
    oprand1 := io.wd.dataRead1
  }
  when(io.in.bits.ctrlALUSrc) {
    oprand2 := io.in.bits.imm
  }.elsewhen(io.in.bits.ctrlcs) {
    oprand2 := io.wd.csr
  }.otherwise {
    oprand2 := io.wd.dataRead2
  }
  switch(io.in.bits.ctrlOP) {
    is(OP_NOP) {
      resultAlu := 0.U
      when(io.in.bits.ctrlcs) {
        resultAlu := oprand1
      }
    }
    is(OP_ADD) {
      resultAlu := oprand1 +& oprand2
    }
    is(OP_SUB) {
      resultAlu := oprand1 -& oprand2
    }
    is(OP_AND) {
      resultAlu := oprand1 & oprand2
    }
    is(OP_OR) {
      resultAlu := oprand1 | oprand2
    }
    is(OP_XOR) {
      resultAlu := oprand1 ^ oprand2
    }
    is(OP_SLL) {
      resultAlu := oprand1 << oprand2(4, 0)
    }
    is(OP_SRL) {
      resultAlu := oprand1 >> oprand2(4, 0)
    }
    is(OP_SRA) { // 需要注意算术右移的写法
      resultAlu := (oprand1.asSInt >> oprand2(4, 0)).asUInt
    }
    is(OP_EQ) {
      resultBranch := oprand1.asSInt === oprand2.asSInt
      resultAlu    := io.in.bits.pc +& io.in.bits.imm
    }
    is(OP_NEQ) {
      resultBranch := oprand1.asSInt =/= oprand2.asSInt
      resultAlu    := io.in.bits.pc +& io.in.bits.imm
    }
    is(OP_LT) { // 区分有符号比较和无符号比较、分支和SLT
      when(io.in.bits.ctrlBranch) {
        when(io.in.bits.ctrlSigned) {
          resultBranch := oprand1.asSInt < oprand2.asSInt
        }.otherwise {
          resultBranch := oprand1 < oprand2
        }
        resultAlu := io.in.bits.pc +& io.in.bits.imm
      }.otherwise {
        when(io.in.bits.ctrlSigned) {
          resultAlu := oprand1.asSInt < oprand2.asSInt
        }.otherwise {
          resultAlu := oprand1 < oprand2
        }
      }
    }
    is(OP_GE) { // 区分有符号比较和无符号比较
      when(io.in.bits.ctrlSigned) {
        resultBranch := oprand1.asSInt >= oprand2.asSInt
      }.otherwise {
        resultBranch := oprand1 >= oprand2
      }
      resultAlu := io.in.bits.pc +& io.in.bits.imm
    }
  }
  io.out.bits.resultBranch := resultBranch
  io.out.bits.resultAlu    := resultAlu

}
