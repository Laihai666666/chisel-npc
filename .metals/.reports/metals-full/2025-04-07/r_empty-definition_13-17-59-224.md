error id: `<error>`#`<error>`.
file://<WORKSPACE>/npc/src/EXU.scala
empty definition using pc, found symbol in pc: 
empty definition using semanticdb
empty definition using fallback
non-local guesses:
	 -chisel3/io/out.
	 -chisel3/util/io/out.
	 -config/Configs.io.out.
	 -utils/OP_TYPES.io.out.
	 -utils/io/out.
	 -io/out.
	 -scala/Predef.io.out.
offset: 1077
uri: file://<WORKSPACE>/npc/src/EXU.scala
text:
```scala
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
  val perf = Output(Bool()) // 计算指令完成计数器信号
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
  io.perf := io.out.valid && !io.in.bits.ctrlLoad && !io.in.bits.ctrlStore // 计算指令完成时触发计数器(排除访存指令)
  val resultAlu    = WireDefault(0.U(DATA_WIDTH.W))
  val resultBranch = WireDefault(false.B)
  val oprand1      = WireDefault(0.U(DATA_WIDTH.W))
  val oprand2      = WireDefault(0.U(DATA_WIDTH.W))
  io.out@@.bits.ctrlRegWrite := io.in.bits.ctrlRegWrite
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

  resultAlu := MuxCase(0.U, Seq(
    (io.in.bits.ctrlOP === OP_NOP) -> Mux(io.in.bits.ctrlcs, oprand1, 0.U),
    (io.in.bits.ctrlOP === OP_ADD) -> (oprand1 +& oprand2),
    (io.in.bits.ctrlOP === OP_SUB) -> (oprand1 -& oprand2),
    (io.in.bits.ctrlOP === OP_AND) -> (oprand1 & oprand2),
    (io.in.bits.ctrlOP === OP_OR) -> (oprand1 | oprand2),
    (io.in.bits.ctrlOP === OP_XOR) -> (oprand1 ^ oprand2),
    (io.in.bits.ctrlOP === OP_SLL) -> (oprand1 << oprand2(4, 0)),
    (io.in.bits.ctrlOP === OP_SRL) -> (oprand1 >> oprand2(4, 0)),
    (io.in.bits.ctrlOP === OP_SRA) -> (oprand1.asSInt >> oprand2(4, 0)).asUInt,
    (io.in.bits.ctrlOP === OP_EQ) -> Mux(io.in.bits.ctrlBranch,
      (io.in.bits.pc +& io.in.bits.imm),
      (oprand1.asSInt === oprand2.asSInt).asUInt
    ),
    (io.in.bits.ctrlOP === OP_NEQ) -> Mux(io.in.bits.ctrlBranch,
      (io.in.bits.pc +& io.in.bits.imm),
      (oprand1.asSInt =/= oprand2.asSInt).asUInt
    ),
    (io.in.bits.ctrlOP === OP_LT) -> Mux(io.in.bits.ctrlBranch,
      (io.in.bits.pc +& io.in.bits.imm),
      Mux(io.in.bits.ctrlSigned,
        (oprand1.asSInt < oprand2.asSInt).asUInt,
        (oprand1 < oprand2).asUInt
      )
    ),
    (io.in.bits.ctrlOP === OP_GE) -> Mux(io.in.bits.ctrlBranch,
      (io.in.bits.pc +& io.in.bits.imm),
      Mux(io.in.bits.ctrlSigned,
        (oprand1.asSInt >= oprand2.asSInt).asUInt,
        (oprand1 >= oprand2).asUInt
      )
    )
  ))

  
  resultBranch := MuxCase(false.B, Seq(
    (io.in.bits.ctrlOP === OP_EQ) -> (oprand1.asSInt === oprand2.asSInt),
    (io.in.bits.ctrlOP === OP_NEQ) -> (oprand1.asSInt =/= oprand2.asSInt),
    (io.in.bits.ctrlOP === OP_LT) -> Mux(io.in.bits.ctrlSigned,
      (oprand1.asSInt < oprand2.asSInt),
      (oprand1 < oprand2)
    ),
    (io.in.bits.ctrlOP === OP_GE) -> Mux(io.in.bits.ctrlSigned,
      (oprand1.asSInt >= oprand2.asSInt),
      (oprand1 >= oprand2)
    )
  ))
  io.out.bits.resultBranch := resultBranch
  io.out.bits.resultAlu    := resultAlu

}

```


#### Short summary: 

empty definition using pc, found symbol in pc: 