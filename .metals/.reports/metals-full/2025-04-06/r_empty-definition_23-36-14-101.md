error id: utils/OP_TYPES.OP_NOP.
file://<WORKSPACE>/npc/src/IDU.scala
empty definition using pc, found symbol in pc: utils/OP_TYPES.OP_NOP.
empty definition using semanticdb
empty definition using fallback
non-local guesses:
	 -chisel3/OP_NOP.
	 -chisel3/OP_NOP#
	 -chisel3/OP_NOP().
	 -chisel3/util/OP_NOP.
	 -chisel3/util/OP_NOP#
	 -chisel3/util/OP_NOP().
	 -config/Configs.OP_NOP.
	 -config/Configs.OP_NOP#
	 -config/Configs.OP_NOP().
	 -utils/OP_TYPES.OP_NOP.
	 -utils/OP_TYPES.OP_NOP#
	 -utils/OP_TYPES.OP_NOP().
	 -utils/OP_NOP.
	 -utils/OP_NOP#
	 -utils/OP_NOP().
	 -OP_NOP.
	 -OP_NOP#
	 -OP_NOP().
	 -scala/Predef.OP_NOP.
	 -scala/Predef.OP_NOP#
	 -scala/Predef.OP_NOP().
offset: 2771
uri: file://<WORKSPACE>/npc/src/IDU.scala
text:
```scala
package npc

import chisel3._
import chisel3.util._

import config.Configs._
import utils.OP_TYPES._

import utils._

class IDUIO extends Bundle {
  val in  = Flipped(Decoupled(new Message()))
  val out = Decoupled(new IDU_O())
  val perf_alu = Output(Bool()) // ALU指令计数器信号
  val perf_mem = Output(Bool()) // 访存指令计数器信号
  val perf_csr = Output(Bool()) // CSR指令计数器信号
}

class IDU extends Module {
  val io = IO(new IDUIO())
  val s_idle :: s_decoding :: Nil = Enum(2)
    val state = RegInit(s_idle)

    state := MuxLookup(state, s_idle)(List(
        s_idle -> Mux(io.in.fire, s_decoding, s_idle),
        s_decoding -> Mux(io.out.fire, s_idle, s_decoding)
    ))

    io.out.valid:=state===s_decoding

    // 指令分类计数器信号
    val is_alu = io.in.bits.inst(6,2) === "b01100".U ||  // R-type
                io.in.bits.inst(6,2) === "b00100".U ||  // I-type ALU
                io.in.bits.inst(6,2) === "b00101".U ||  // AUIPC
                io.in.bits.inst(6,2) === "b01101".U ||    // LUI
                io.in.bits.inst(6,2) === "b11011".U ||   //   JAL
                io.in.bits.inst(6,2) === "b11001".U ||   // JALR
                io.in.bits.inst(6,2) === "b11000".U    // B-type
    val is_mem = io.in.bits.inst(6,2) === "b00000".U || // LOAD
                io.in.bits.inst(6,2) === "b01000".U    // STORE
    val is_csr = io.in.bits.inst(6,2) === "b11100".U    // CSR
    
    io.perf_alu := state === s_decoding && is_alu
    io.perf_mem := state === s_decoding && is_mem
    io.perf_csr := state === s_decoding && is_csr && !(io.out.bits.ctrlbreak)
  io.in.ready     := 1.U
  io.out.bits.rs1 := io.in.bits.inst(19, 15)
  io.out.bits.rs2 := io.in.bits.inst(24, 20)
  io.out.bits.rd  := io.in.bits.inst(11, 7)

  // 五种立即数
  val imm_i = Cat(Fill(20, io.in.bits.inst(31)), io.in.bits.inst(31, 20))
  val imm_u = Cat(io.in.bits.inst(31, 12), Fill(12, 0.U))
  val imm_j = Cat(
    Fill(12, io.in.bits.inst(31)),
    io.in.bits.inst(31),
    io.in.bits.inst(19, 12),
    io.in.bits.inst(20),
    io.in.bits.inst(30, 21),
    Fill(1, 0.U)
  )

  val imm_b =
    Cat(Fill(20, io.in.bits.inst(31)), io.in.bits.inst(7), io.in.bits.inst(30, 25), io.in.bits.inst(11, 8), 0.U(1.W))
  val imm_s = Cat(Fill(20, io.in.bits.inst(31)), io.in.bits.inst(31, 25), io.in.bits.inst(11, 7));

  val imm_shamt = Cat(Fill(27, 0.U), io.in.bits.inst(24, 20))
  // 用于立即数输出
  val imm = WireDefault(0.U(32.W))

  // 用于控制信号
  val ctrlJump     = WireDefault(false.B)
  val ctrlRegWrite = WireDefault(true.B)
  val ctrlLoad     = WireDefault(false.B)
  val ctrlStore    = WireDefault(false.B)
  val ctrlLSType   = WireDefault(0.U(4.W))
  val ctrlALUSrc   = WireDefault(false.B)
  val ctrlJAL      = WireDefault(false.B)
  val ctrlBranch   = WireDefault(false.B)
  val ctrlOP       = WireDefault(OP_@@NOP)
  val ctrlSigned   = WireDefault(true.B)
  val ctrlcs       = WireDefault(false.B)
  val ctrlecall    = WireDefault(false.B)
  val ctrlcsr      = WireDefault(0.U(12.W))
  val ctrlcsrWrite = WireDefault(false.B)
  val ctrloneop    = WireDefault(false.B)
  val ctrlbreak    = WireDefault(false.B)
  // 根据opcode对控制信号赋值
  switch(io.in.bits.inst(6, 2)) {
    // U: lui
    is("b01101".U) {
      ctrlALUSrc := true.B
      ctrlOP     := OP_ADD
      imm        := imm_u
      ctrloneop  := true.B
    }
    // U:auipc
    is("b00101".U) {
      ctrlALUSrc := true.B
      ctrlJAL    := true.B
      ctrlOP     := OP_ADD
      imm        := imm_u
      ctrloneop  := true.B
    }
    // J: jal
    is("b11011".U) {
      ctrlALUSrc := true.B
      ctrlJump   := true.B
      ctrlOP     := OP_ADD
      ctrlJAL    := true.B
      imm        := imm_j
    }
    // I: JALR,
    // I: LB, LH, LW, LBU, LHU
    // I: ADDI, SLTI, SLTIU, XORI, ORI, ANDI, SLLI, SRLI, SRAI
    is("b11001".U, "b00000".U, "b00100".U) {
      ctrlALUSrc := true.B
      // JALR
      when(io.in.bits.inst(6, 2) === "b11001".U) {
        ctrlJump := true.B
        ctrlOP   := OP_ADD
        imm      := imm_i
      }
        // LOAD
        .elsewhen(io.in.bits.inst(6, 2) === "b00000".U) {
          ctrlLoad := true.B
          ctrlOP   := OP_ADD
          imm      := imm_i
          switch(io.in.bits.inst(14, 12)) {
            is("b000".U) {
              ctrlLSType := LS_B
            }
            is("b100".U) {
              ctrlLSType := LS_B
              ctrlSigned := false.B
            }
            is("b001".U) {
              ctrlLSType := LS_H
            }
            is("b101".U) {
              ctrlLSType := LS_H
              ctrlSigned := false.B
            }
            is("b010".U) {
              ctrlLSType := LS_W
            }
            is("b110".U) {
              ctrlLSType := LS_W
              ctrlSigned := false.B
            }
          }
        }
        // AL
        .elsewhen(
          io.in.bits.inst(6, 2) === "b00100".U && (io.in.bits.inst(14, 12) === "b001".U || io.in.bits
            .inst(14, 12) === "b101".U)
        ) {
          imm := imm_shamt
          switch(Cat(io.in.bits.inst(30), io.in.bits.inst(14, 12))) {
            // SLLI
            is("b0001".U) {
              ctrlOP := OP_SLL
            }
            // SRLI
            is("b0101".U) {
              ctrlOP := OP_SRL
            }
            // SRAI
            is("b1101".U) {
              ctrlOP := OP_SRA
            }
          }
        }
        .otherwise {
          imm := imm_i
          switch(io.in.bits.inst(14, 12)) {
            // ADDI
            is("b000".U) {
              ctrlOP := OP_ADD
            }
            // SLTI
            is("b010".U) {
              ctrlOP := OP_LT
            }
            // SLTIU
            is("b011".U) {
              ctrlOP     := OP_LT
              ctrlSigned := false.B
            }
            // XORI
            is("b100".U) {
              ctrlOP := OP_XOR
            }
            // ORI
            is("b110".U) {
              ctrlOP := OP_OR
            }
            // ANDI
            is("b111".U) {
              ctrlOP := OP_AND
            }
          }
        }
    }
    // B:beq,bne.blt.bge,bltu,bgeu
    is("b11000".U) {
      ctrlALUSrc   := false.B
      ctrlBranch   := true.B
      ctrlRegWrite := false.B
      imm          := imm_b
      switch(io.in.bits.inst(14, 12)) {
        is("b000".U) {
          ctrlOP := OP_EQ
        }
        is("b001".U) {
          ctrlOP := OP_NEQ
        }
        is("b100".U) {
          ctrlOP := OP_LT
        }
        is("b101".U) {
          ctrlOP := OP_GE
        }
        is("b110".U) {
          ctrlOP     := OP_LT
          ctrlSigned := false.B
        }
        is("b111".U) {
          ctrlOP     := OP_GE
          ctrlSigned := false.B
        }
      }
    }
    // S: sb,sh,sw
    is("b01000".U) {
      ctrlALUSrc   := true.B
      ctrlStore    := true.B
      ctrlRegWrite := false.B
      ctrlOP       := OP_ADD
      imm          := imm_s
      switch(io.in.bits.inst(14, 12)) {
        is("b000".U) {
          ctrlLSType := LS_B
        }
        is("b001".U) {
          ctrlLSType := LS_H
        }
        is("b010".U) {
          ctrlLSType := LS_W
        }
      }
    }
    // R
    is("b01100".U) {
      switch(io.in.bits.inst(14, 12)) {
        // ADD, SUB
        is("b000".U) {
          when(io.in.bits.inst(30)) {
            ctrlOP := OP_SUB
          }.otherwise {
            ctrlOP := OP_ADD
          }
        }
        // SLL
        is("b001".U) {
          ctrlOP := OP_SLL
        }
        // SLT
        is("b010".U) {
          ctrlOP := OP_LT
        }
        // SLTU
        is("b011".U) {
          ctrlOP     := OP_LT
          ctrlSigned := false.B
        }
        // XOR
        is("b100".U) {
          ctrlOP := OP_XOR
        }
        // SRL, SRA
        is("b101".U) {
          when(io.in.bits.inst(30)) {
            ctrlOP := OP_SRA
          }.otherwise {
            ctrlOP := OP_SRL
          }
        }
        // OR
        is("b110".U) {
          ctrlOP := OP_OR
        }
        // AND
        is("b111".U) {
          ctrlOP := OP_AND
        }
      }
    }
    // ecall,csrrw,csrrs,mret
    // break
    is("b11100".U) {
      switch(io.in.bits.inst(14, 12)) {
        is("b001".U) {
          ctrlcs       := true.B
          ctrlOP       := OP_NOP
          ctrlcsr      := imm_i
          ctrlcsrWrite := true.B
        }
        is("b010".U) {
          ctrlcs       := true.B
          ctrlOP       := OP_OR
          ctrlcsr      := imm_i
          ctrlcsrWrite := true.B
        }
        is("b000".U) {
          switch(io.in.bits.inst(21, 20)) {
            is("b00".U) {
              ctrlcsr      := "h305".U
              ctrlcs       := true.B
              ctrlecall    := true.B
              ctrlJump     := true.B
              ctrlRegWrite := false.B
              ctrlOP       := OP_ADD
            }
            is("b10".U) {
              ctrlcs       := true.B
              ctrlcsr      := "h341".U
              ctrlJump     := true.B
              ctrlOP       := OP_ADD
              ctrlRegWrite := false.B
            }
            is("b01".U) {
              ctrlbreak := true.B
            }
          }
        }
      }
    }
  }

  // 连接控制信号和立即数
  io.out.bits.ctrlALUSrc   := ctrlALUSrc
  io.out.bits.ctrlBranch   := ctrlBranch
  io.out.bits.ctrlJAL      := ctrlJAL
  io.out.bits.ctrlJump     := ctrlJump
  io.out.bits.ctrlLoad     := ctrlLoad
  io.out.bits.ctrlOP       := ctrlOP
  io.out.bits.ctrlRegWrite := ctrlRegWrite
  io.out.bits.ctrlSigned   := ctrlSigned
  io.out.bits.ctrlStore    := ctrlStore
  io.out.bits.ctrlLSType   := ctrlLSType
  io.out.bits.ctrlbreak    := ctrlbreak
  io.out.bits.ctrlcsr      := ctrlcsr
  io.out.bits.ctrlcs       := ctrlcs
  io.out.bits.ctrlcsrWrite := ctrlcsrWrite
  io.out.bits.ctrloneop    := ctrloneop
  io.out.bits.ctrlecall    := ctrlecall
  io.out.bits.imm          := imm
  io.out.bits.pc           := io.in.bits.pc

}

```


#### Short summary: 

empty definition using pc, found symbol in pc: utils/OP_TYPES.OP_NOP.