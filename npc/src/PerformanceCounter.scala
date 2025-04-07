package npc

import chisel3._
import chisel3.util._
import config.Configs._

class PerformanceCounterIO extends Bundle {
  val ifu_fetch = Input(Bool())
  val lsu_load = Input(Bool())
  val exu_exec = Input(Bool())
  val idu_alu = Input(Bool())
  val idu_mem = Input(Bool())
  val idu_csr = Input(Bool())
  val finish = Input(Bool()) // 仿真结束信号
}

class PerformanceCounter extends Module {
  val io = IO(new PerformanceCounterIO())

  // 定义计数器寄存器
  val ifu_counter = RegInit(0.U(64.W))
  val lsu_counter = RegInit(0.U(64.W))
  val exu_counter = RegInit(0.U(64.W))
  val idu_alu_counter = RegInit(0.U(64.W))
  val idu_mem_counter = RegInit(0.U(64.W))
  val idu_csr_counter = RegInit(0.U(64.W))

  // 计数器递增逻辑
  when(io.ifu_fetch) { ifu_counter := ifu_counter + 1.U }
  when(io.lsu_load) { lsu_counter := lsu_counter + 1.U }
  when(io.exu_exec) { exu_counter := exu_counter + 1.U }
  when(io.idu_alu) { idu_alu_counter := idu_alu_counter + 1.U }
  when(io.idu_mem) { idu_mem_counter := idu_mem_counter + 1.U }
  when(io.idu_csr) { idu_csr_counter := idu_csr_counter + 1.U }

  // 仿真结束时打印计数器值
  when(io.finish) {
    printf(p"Performance Counters (Final):\n")
    printf(p"  IFU Fetches: ${ifu_counter}\n")
    printf(p"  LSU Loads: ${lsu_counter}\n") 
    printf(p"  EXU Executes: ${exu_counter}\n")
    printf(p"  IDU ALU Ops: ${idu_alu_counter}\n")
    printf(p"  IDU Mem Ops: ${idu_mem_counter}\n")
    printf(p"  IDU CSR Ops: ${idu_csr_counter}\n")
  }
  
}
