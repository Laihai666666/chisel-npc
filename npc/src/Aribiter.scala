
package npc

import chisel3._
import chisel3.util._

import config.Configs._
import utils.OP_TYPES._
import utils._

class Aribiter_io extends Bundle {
    val ifu_ack = Input(Bool())  // IFU transaction complete
    val lsu_ack = Input(Bool())  // LSU transaction complete

    val axi_ifu = Flipped(new AXI_IO)  // IFU AXI interface
    val axi_lsu = Flipped(new AXI_IO)  // LSU AXI interface

    val axi_soc = new AXI_IO  // SOC AXI interface
}

class Aribiter extends Module {
    val io = IO(new Aribiter_io())
    val xbar = Module(new Xbar())

    
    val s_ifu_busy :: s_lsu_busy :: Nil = Enum(2)
    val state = RegInit(s_ifu_busy)

    
    switch(state) {
        is(s_ifu_busy) {
            when(io.ifu_ack) { state := s_lsu_busy }
        }
        is(s_lsu_busy) {
            when(io.lsu_ack) { state := s_ifu_busy }
        }
    }

    
    val axi_o = WireDefault(0.U.asTypeOf(new AXI_O))

    
    xbar.io.axi.out := MuxCase(axi_o, Seq(
        (state === s_ifu_busy) -> io.axi_ifu.out,
        (state === s_lsu_busy) -> io.axi_lsu.out
    ))

    io.axi_ifu.in := xbar.io.axi.in
    io.axi_lsu.in := xbar.io.axi.in

    io.axi_soc.out := xbar.io.soc_axi.out
    xbar.io.soc_axi.in := io.axi_soc.in
}
