file://<WORKSPACE>/npc/src/IDU.scala
### scala.ScalaReflectionException: value is_alu is not a method

occurred in the presentation compiler.

presentation compiler configuration:
Scala version: 2.13.12
Classpath:
<WORKSPACE>/.bloop/out/npc/bloop-bsp-clients-classes/classes-Metals-q9BC82RoTQadqYAaoAG6Tg== [exists ], <HOME>/.cache/bloop/semanticdb/com.sourcegraph.semanticdb-javac.0.10.4/semanticdb-javac-0.10.4.jar [exists ], <HOME>/.cache/coursier/v1/http/mirrors.cloud.tencent.com/nexus/repository/maven-public/org/chipsalliance/chisel_2.13/6.5.0/chisel_2.13-6.5.0.jar [exists ], <HOME>/.cache/coursier/v1/http/mirrors.cloud.tencent.com/nexus/repository/maven-public/org/scala-lang/scala-library/2.13.12/scala-library-2.13.12.jar [exists ], <HOME>/.cache/coursier/v1/http/mirrors.cloud.tencent.com/nexus/repository/maven-public/org/scala-lang/scala-reflect/2.13.12/scala-reflect-2.13.12.jar [exists ], <HOME>/.cache/coursier/v1/http/mirrors.cloud.tencent.com/nexus/repository/maven-public/com/github/scopt/scopt_2.13/4.1.0/scopt_2.13-4.1.0.jar [exists ], <HOME>/.cache/coursier/v1/http/mirrors.cloud.tencent.com/nexus/repository/maven-public/net/jcazevedo/moultingyaml_2.13/0.4.2/moultingyaml_2.13-0.4.2.jar [exists ], <HOME>/.cache/coursier/v1/http/mirrors.cloud.tencent.com/nexus/repository/maven-public/org/json4s/json4s-native_2.13/4.0.6/json4s-native_2.13-4.0.6.jar [exists ], <HOME>/.cache/coursier/v1/http/mirrors.cloud.tencent.com/nexus/repository/maven-public/org/apache/commons/commons-text/1.10.0/commons-text-1.10.0.jar [exists ], <HOME>/.cache/coursier/v1/http/mirrors.cloud.tencent.com/nexus/repository/maven-public/io/github/alexarchambault/data-class_2.13/0.2.6/data-class_2.13-0.2.6.jar [exists ], <HOME>/.cache/coursier/v1/http/mirrors.cloud.tencent.com/nexus/repository/maven-public/com/lihaoyi/os-lib_2.13/0.9.2/os-lib_2.13-0.9.2.jar [exists ], <HOME>/.cache/coursier/v1/http/mirrors.cloud.tencent.com/nexus/repository/maven-public/org/scala-lang/modules/scala-parallel-collections_2.13/1.0.4/scala-parallel-collections_2.13-1.0.4.jar [exists ], <HOME>/.cache/coursier/v1/http/mirrors.cloud.tencent.com/nexus/repository/maven-public/com/lihaoyi/upickle_2.13/3.1.0/upickle_2.13-3.1.0.jar [exists ], <HOME>/.cache/coursier/v1/http/mirrors.cloud.tencent.com/nexus/repository/maven-public/org/chipsalliance/firtool-resolver_2.13/1.3.0/firtool-resolver_2.13-1.3.0.jar [exists ], <HOME>/.cache/coursier/v1/http/mirrors.cloud.tencent.com/nexus/repository/maven-public/com/github/nscala-time/nscala-time_2.13/2.22.0/nscala-time_2.13-2.22.0.jar [exists ], <HOME>/.cache/coursier/v1/http/mirrors.cloud.tencent.com/nexus/repository/maven-public/org/yaml/snakeyaml/1.26/snakeyaml-1.26.jar [exists ], <HOME>/.cache/coursier/v1/http/mirrors.cloud.tencent.com/nexus/repository/maven-public/org/json4s/json4s-core_2.13/4.0.6/json4s-core_2.13-4.0.6.jar [exists ], <HOME>/.cache/coursier/v1/http/mirrors.cloud.tencent.com/nexus/repository/maven-public/org/json4s/json4s-native-core_2.13/4.0.6/json4s-native-core_2.13-4.0.6.jar [exists ], <HOME>/.cache/coursier/v1/http/mirrors.cloud.tencent.com/nexus/repository/maven-public/org/apache/commons/commons-lang3/3.12.0/commons-lang3-3.12.0.jar [exists ], <HOME>/.cache/coursier/v1/http/mirrors.cloud.tencent.com/nexus/repository/maven-public/com/lihaoyi/geny_2.13/1.0.0/geny_2.13-1.0.0.jar [exists ], <HOME>/.cache/coursier/v1/http/mirrors.cloud.tencent.com/nexus/repository/maven-public/com/lihaoyi/ujson_2.13/3.1.0/ujson_2.13-3.1.0.jar [exists ], <HOME>/.cache/coursier/v1/http/mirrors.cloud.tencent.com/nexus/repository/maven-public/com/lihaoyi/upack_2.13/3.1.0/upack_2.13-3.1.0.jar [exists ], <HOME>/.cache/coursier/v1/http/mirrors.cloud.tencent.com/nexus/repository/maven-public/com/lihaoyi/upickle-implicits_2.13/3.1.0/upickle-implicits_2.13-3.1.0.jar [exists ], <HOME>/.cache/coursier/v1/http/mirrors.cloud.tencent.com/nexus/repository/maven-public/dev/dirs/directories/26/directories-26.jar [exists ], <HOME>/.cache/coursier/v1/http/mirrors.cloud.tencent.com/nexus/repository/maven-public/com/outr/scribe_2.13/3.13.0/scribe_2.13-3.13.0.jar [exists ], <HOME>/.cache/coursier/v1/http/mirrors.cloud.tencent.com/nexus/repository/maven-public/io/get-coursier/coursier_2.13/2.1.8/coursier_2.13-2.1.8.jar [exists ], <HOME>/.cache/coursier/v1/http/mirrors.cloud.tencent.com/nexus/repository/maven-public/joda-time/joda-time/2.10.1/joda-time-2.10.1.jar [exists ], <HOME>/.cache/coursier/v1/http/mirrors.cloud.tencent.com/nexus/repository/maven-public/org/joda/joda-convert/2.2.0/joda-convert-2.2.0.jar [exists ], <HOME>/.cache/coursier/v1/http/mirrors.cloud.tencent.com/nexus/repository/maven-public/org/json4s/json4s-ast_2.13/4.0.6/json4s-ast_2.13-4.0.6.jar [exists ], <HOME>/.cache/coursier/v1/http/mirrors.cloud.tencent.com/nexus/repository/maven-public/org/json4s/json4s-scalap_2.13/4.0.6/json4s-scalap_2.13-4.0.6.jar [exists ], <HOME>/.cache/coursier/v1/http/mirrors.cloud.tencent.com/nexus/repository/maven-public/com/thoughtworks/paranamer/paranamer/2.8/paranamer-2.8.jar [exists ], <HOME>/.cache/coursier/v1/http/mirrors.cloud.tencent.com/nexus/repository/maven-public/com/lihaoyi/upickle-core_2.13/3.1.0/upickle-core_2.13-3.1.0.jar [exists ], <HOME>/.cache/coursier/v1/http/mirrors.cloud.tencent.com/nexus/repository/maven-public/com/outr/perfolation_2.13/1.2.9/perfolation_2.13-1.2.9.jar [exists ], <HOME>/.cache/coursier/v1/http/mirrors.cloud.tencent.com/nexus/repository/maven-public/com/lihaoyi/sourcecode_2.13/0.3.1/sourcecode_2.13-0.3.1.jar [exists ], <HOME>/.cache/coursier/v1/http/mirrors.cloud.tencent.com/nexus/repository/maven-public/org/scala-lang/modules/scala-collection-compat_2.13/2.11.0/scala-collection-compat_2.13-2.11.0.jar [exists ], <HOME>/.cache/coursier/v1/http/mirrors.cloud.tencent.com/nexus/repository/maven-public/com/outr/moduload_2.13/1.1.7/moduload_2.13-1.1.7.jar [exists ], <HOME>/.cache/coursier/v1/http/mirrors.cloud.tencent.com/nexus/repository/maven-public/com/github/plokhotnyuk/jsoniter-scala/jsoniter-scala-core_2.13/2.13.5.2/jsoniter-scala-core_2.13-2.13.5.2.jar [exists ], <HOME>/.cache/coursier/v1/http/mirrors.cloud.tencent.com/nexus/repository/maven-public/io/get-coursier/coursier-core_2.13/2.1.8/coursier-core_2.13-2.1.8.jar [exists ], <HOME>/.cache/coursier/v1/http/mirrors.cloud.tencent.com/nexus/repository/maven-public/io/get-coursier/coursier-cache_2.13/2.1.8/coursier-cache_2.13-2.1.8.jar [exists ], <HOME>/.cache/coursier/v1/http/mirrors.cloud.tencent.com/nexus/repository/maven-public/io/get-coursier/coursier-proxy-setup/2.1.8/coursier-proxy-setup-2.1.8.jar [exists ], <HOME>/.cache/coursier/v1/http/mirrors.cloud.tencent.com/nexus/repository/maven-public/io/github/alexarchambault/concurrent-reference-hash-map/1.1.0/concurrent-reference-hash-map-1.1.0.jar [exists ], <HOME>/.cache/coursier/v1/http/mirrors.cloud.tencent.com/nexus/repository/maven-public/org/scala-lang/modules/scala-xml_2.13/2.2.0/scala-xml_2.13-2.2.0.jar [exists ], <HOME>/.cache/coursier/v1/http/mirrors.cloud.tencent.com/nexus/repository/maven-public/io/get-coursier/coursier-util_2.13/2.1.8/coursier-util_2.13-2.1.8.jar [exists ], <HOME>/.cache/coursier/v1/http/mirrors.cloud.tencent.com/nexus/repository/maven-public/io/get-coursier/jniutils/windows-jni-utils/0.3.3/windows-jni-utils-0.3.3.jar [exists ], <HOME>/.cache/coursier/v1/http/mirrors.cloud.tencent.com/nexus/repository/maven-public/org/codehaus/plexus/plexus-archiver/4.9.0/plexus-archiver-4.9.0.jar [exists ], <HOME>/.cache/coursier/v1/http/mirrors.cloud.tencent.com/nexus/repository/maven-public/org/codehaus/plexus/plexus-container-default/2.1.1/plexus-container-default-2.1.1.jar [exists ], <HOME>/.cache/coursier/v1/http/mirrors.cloud.tencent.com/nexus/repository/maven-public/org/virtuslab/scala-cli/config_2.13/0.2.1/config_2.13-0.2.1.jar [exists ], <HOME>/.cache/coursier/v1/http/mirrors.cloud.tencent.com/nexus/repository/maven-public/io/github/alexarchambault/windows-ansi/windows-ansi/0.0.5/windows-ansi-0.0.5.jar [exists ], <HOME>/.cache/coursier/v1/http/mirrors.cloud.tencent.com/nexus/repository/maven-public/javax/inject/javax.inject/1/javax.inject-1.jar [exists ], <HOME>/.cache/coursier/v1/http/mirrors.cloud.tencent.com/nexus/repository/maven-public/org/codehaus/plexus/plexus-utils/4.0.0/plexus-utils-4.0.0.jar [exists ], <HOME>/.cache/coursier/v1/http/mirrors.cloud.tencent.com/nexus/repository/maven-public/org/codehaus/plexus/plexus-io/3.4.1/plexus-io-3.4.1.jar [exists ], <HOME>/.cache/coursier/v1/http/mirrors.cloud.tencent.com/nexus/repository/maven-public/commons-io/commons-io/2.15.0/commons-io-2.15.0.jar [exists ], <HOME>/.cache/coursier/v1/http/mirrors.cloud.tencent.com/nexus/repository/maven-public/org/apache/commons/commons-compress/1.24.0/commons-compress-1.24.0.jar [exists ], <HOME>/.cache/coursier/v1/http/mirrors.cloud.tencent.com/nexus/repository/maven-public/org/slf4j/slf4j-api/1.7.36/slf4j-api-1.7.36.jar [exists ], <HOME>/.cache/coursier/v1/http/mirrors.cloud.tencent.com/nexus/repository/maven-public/org/iq80/snappy/snappy/0.4/snappy-0.4.jar [exists ], <HOME>/.cache/coursier/v1/http/mirrors.cloud.tencent.com/nexus/repository/maven-public/org/tukaani/xz/1.9/xz-1.9.jar [exists ], <HOME>/.cache/coursier/v1/http/mirrors.cloud.tencent.com/nexus/repository/maven-public/com/github/luben/zstd-jni/1.5.5-10/zstd-jni-1.5.5-10.jar [exists ], <HOME>/.cache/coursier/v1/http/mirrors.cloud.tencent.com/nexus/repository/maven-public/org/codehaus/plexus/plexus-classworlds/2.6.0/plexus-classworlds-2.6.0.jar [exists ], <HOME>/.cache/coursier/v1/http/mirrors.cloud.tencent.com/nexus/repository/maven-public/org/apache/xbean/xbean-reflect/3.7/xbean-reflect-3.7.jar [exists ], <HOME>/.cache/coursier/v1/http/mirrors.cloud.tencent.com/nexus/repository/maven-public/org/fusesource/jansi/jansi/1.18/jansi-1.18.jar [exists ]
Options:
-language:reflectiveCalls -deprecation -feature -Xcheckinit -Yrangepos -Xplugin-require:semanticdb


action parameters:
offset: 1049
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
                io.in.bits.inst(6,2) === "b11011".U ||   //  /@@/ JAL
                io.in.bits.inst(6,2) === "b11001".U ||   // JALR
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
  val ctrlOP       = WireDefault(0.U(OP_TYPES_WIDTH.W))
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



#### Error stacktrace:

```
scala.reflect.api.Symbols$SymbolApi.asMethod(Symbols.scala:240)
	scala.reflect.api.Symbols$SymbolApi.asMethod$(Symbols.scala:234)
	scala.reflect.internal.Symbols$SymbolContextApiImpl.asMethod(Symbols.scala:99)
	scala.tools.nsc.typechecker.ContextErrors$TyperContextErrors$TyperErrorGen$.MissingArgsForMethodTpeError(ContextErrors.scala:795)
	scala.tools.nsc.typechecker.Typers$Typer.adaptMethodTypeToExpr$1(Typers.scala:984)
	scala.tools.nsc.typechecker.Typers$Typer.adapt(Typers.scala:1305)
	scala.tools.nsc.typechecker.Typers$Typer.typed(Typers.scala:6168)
	scala.tools.nsc.typechecker.Typers$Typer.typedDefDef(Typers.scala:6417)
	scala.tools.nsc.typechecker.Typers$Typer.typed1(Typers.scala:6059)
	scala.tools.nsc.typechecker.Typers$Typer.typed(Typers.scala:6153)
	scala.tools.nsc.typechecker.Typers$Typer.typedStat$1(Typers.scala:6231)
	scala.tools.nsc.typechecker.Typers$Typer.$anonfun$typedStats$4(Typers.scala:3422)
	scala.tools.nsc.typechecker.Typers$Typer.$anonfun$typedStats$4$adapted(Typers.scala:3417)
	scala.reflect.internal.Scopes$Scope.foreach(Scopes.scala:455)
	scala.tools.nsc.typechecker.Typers$Typer.addSynthetics$1(Typers.scala:3417)
	scala.tools.nsc.typechecker.Typers$Typer.typedStats(Typers.scala:3482)
	scala.tools.nsc.typechecker.Typers$Typer.typedTemplate(Typers.scala:2089)
	scala.tools.nsc.typechecker.Typers$Typer.typedClassDef(Typers.scala:1927)
	scala.tools.nsc.typechecker.Typers$Typer.typed1(Typers.scala:6060)
	scala.tools.nsc.typechecker.Typers$Typer.typed(Typers.scala:6153)
	scala.tools.nsc.typechecker.Typers$Typer.typedStat$1(Typers.scala:6231)
	scala.tools.nsc.typechecker.Typers$Typer.$anonfun$typedStats$8(Typers.scala:3470)
	scala.tools.nsc.typechecker.Typers$Typer.typedStats(Typers.scala:3470)
	scala.tools.nsc.typechecker.Typers$Typer.typedPackageDef$1(Typers.scala:5743)
	scala.tools.nsc.typechecker.Typers$Typer.typed1(Typers.scala:6063)
	scala.tools.nsc.typechecker.Typers$Typer.typed(Typers.scala:6153)
	scala.tools.nsc.typechecker.Analyzer$typerFactory$TyperPhase.apply(Analyzer.scala:124)
	scala.tools.nsc.Global$GlobalPhase.applyPhase(Global.scala:480)
	scala.tools.nsc.interactive.Global$TyperRun.applyPhase(Global.scala:1370)
	scala.tools.nsc.interactive.Global$TyperRun.typeCheck(Global.scala:1363)
	scala.tools.nsc.interactive.Global.typeCheck(Global.scala:680)
	scala.meta.internal.pc.HoverProvider.typedHoverTreeAt(HoverProvider.scala:324)
	scala.meta.internal.pc.HoverProvider.hoverOffset(HoverProvider.scala:48)
	scala.meta.internal.pc.HoverProvider.hover(HoverProvider.scala:27)
	scala.meta.internal.pc.ScalaPresentationCompiler.$anonfun$hover$1(ScalaPresentationCompiler.scala:455)
```
#### Short summary: 

scala.ScalaReflectionException: value is_alu is not a method