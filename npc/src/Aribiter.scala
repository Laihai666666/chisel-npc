
package npc

import chisel3._
import chisel3.util._

import config.Configs._
import utils.OP_TYPES._
import utils._

class Aribiter_io extends Bundle{
    val ifu_ack =Input(Bool())
    val axi_ifu =Flipped(new AXI_IO)
    val lsu_ack =Input(Bool())
    val axi_lsu =Flipped(new AXI_IO)
}
class Aribiter extends Module{
    val io =IO(new Aribiter_io())
    val xbar=Module(new Xbar())
    val s_ifu_busy :: s_lsu_busy ::Nil =Enum(2)
    val state =RegInit(s_ifu_busy)

    state :=MuxLookup(state,s_ifu_busy)(
        List(
            s_ifu_busy->Mux(io.ifu_ack,s_lsu_busy ,s_ifu_busy),
            s_lsu_busy->Mux(io.lsu_ack,s_ifu_busy,s_lsu_busy)
        )
    )
    val  axi_o=Wire(new AXI_O)
    axi_o.araddr:=0.U
    axi_o.arvalid:=false.B
    axi_o.awaddr:=0.U
    axi_o.rready:=false.B
    axi_o.awvalid:=false.B
    axi_o.wdata:=0.U
    axi_o.wstrb:=0.U
    axi_o.wvalid:=false.B
    axi_o.bready:=false.B
    when(state===s_ifu_busy){
        xbar.io.axi.out:=io.axi_ifu.out
    }.elsewhen(state===s_lsu_busy){
        xbar.io.axi.out:=io.axi_lsu.out
    }.otherwise{
        xbar.io.axi.out:=axi_o
    }
    io.axi_ifu.in:=xbar.io.axi.in
    io.axi_lsu.in:=xbar.io.axi.in
}
