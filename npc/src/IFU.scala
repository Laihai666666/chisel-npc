package npc
import _root_.circt.stage.ChiselStage

import chisel3._
import chisel3.util._
import config.Configs._
import utils._

class IFU extends Module {
  val io = IO(new Bundle {
    val ifu_req = Input(Bool())
    val ifu_ack = Output(Bool())
    val pc      = Input(UInt(DATA_WIDTH.W))
    val out     = Decoupled(new Message())
    val perf    = Output(Bool()) // 性能计数器信号
    val axi     = new AXI_IO()
  })
  // Cache参数定义
  val CACHE_SIZE = 64  
  val BLOCK_SIZE = 4    
  val BLOCKS = CACHE_SIZE / BLOCK_SIZE
  val INDEX_BITS = log2Ceil(BLOCKS)
  val OFFSET_BITS = log2Ceil(BLOCK_SIZE)
  val TAG_BITS = DATA_WIDTH - INDEX_BITS - OFFSET_BITS

  // Cache存储结构
  val valid = RegInit(VecInit(Seq.fill(BLOCKS)(false.B)))
  val tags = Reg(Vec(BLOCKS, UInt(TAG_BITS.W)))
  val data = Reg(Vec(BLOCKS, UInt(DATA_WIDTH.W)))
  //dontTouch(data)

  // 状态机
  val s_idle :: s_check :: s_miss :: s_fill :: Nil = Enum(4)
  val state = RegInit(s_idle)

  val ifu_req = RegInit(true.B)
  ifu_req := io.ifu_req
  
  val inst   = RegInit(0.U(DATA_WIDTH.W))
  // 地址解析
  val addr_tag = io.pc(DATA_WIDTH-1, INDEX_BITS+OFFSET_BITS)
  val addr_index = io.pc(INDEX_BITS+OFFSET_BITS-1, OFFSET_BITS)
  val addr_offset = io.pc(OFFSET_BITS-1, 0)

  // 默认输出
  io.ifu_ack := false.B
  io.out.valid := false.B
  io.axi.out := 0.U.asTypeOf(new AXI_O())
  val FLASH_START = "h30000000".U
  val FLASH_END   = "h3fffffff".U
  val PSRAM_START = "h80000000".U
  val PSRAM_END   =  "h9fffffff".U
  val SDRAM_START = "ha0000000".U
  val SDRAM_END   = "hafffffff".U
  val is_cacheable = ((io.pc >= FLASH_START && io.pc <= FLASH_END)    
                    || (io.pc >= PSRAM_START && io.pc <= PSRAM_END) 
                    || (io.pc >= SDRAM_START && io.pc <= SDRAM_END))
  // 状态机逻辑
  switch(state) {
    is(s_idle) {
      when(ifu_req) {
        state :=Mux(is_cacheable, s_check, s_miss)
      }
    }
    is(s_check) {
      // 检查cache命中
      val hit = valid(addr_index) && (tags(addr_index) === addr_tag)
      when(hit) {
        inst := data(addr_index)
        io.out.valid := true.B
        io.ifu_ack := true.B
        state := s_idle
      }.otherwise {
        state := s_miss
      }
    }
    is(s_miss) {
      // 发起AXI读请求
      io.axi.out.arvalid := true.B
      io.axi.out.araddr := io.pc
      io.axi.out.arlen := 0.U
      io.axi.out.arsize := 2.U  
      when(io.axi.in.arready) {
        state := s_fill
      }
    }

    is(s_fill) {
      io.axi.out.rready := true.B
      when(io.axi.in.rvalid) { 
        inst:= io.axi.in.rdata 
        when(io.axi.in.rlast) {
          when(is_cacheable) {
            valid(addr_index) := true.B
            tags(addr_index) := addr_tag
            data(addr_index) := io.axi.in.rdata 
          }
          state := s_idle
          io.out.valid := true.B
          io.ifu_ack := true.B
        }
      }
    }
  }
  io.perf := io.out.valid 
  io.out.bits.inst := inst
  io.out.bits.pc := io.pc
}
