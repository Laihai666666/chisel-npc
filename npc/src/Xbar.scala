
package npc

import chisel3._
import chisel3.util._

import config.Configs._
import utils.OP_TYPES._
import utils._


class Xbar extends Module{
    val io=IO(new Bundle{
        val axi=Flipped(new AXI_IO)
        val soc_axi=new AXI_IO
    })
    val clint=Module(new CLINT())
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
    axi_o.awid:=0.U
    axi_o.awlen:=0.U
    axi_o.awsize:=0.U
    axi_o.awburst:=0.U
    axi_o.wlast:=false.B
    axi_o.arid:=0.U
    axi_o.arlen:=0.U
    axi_o.arsize:=0.U
    axi_o.arburst:=0.U
    
    when(((io.axi.out.araddr>="h02000000".U&io.axi.out.araddr<="h0200ffff".U))){
            clint.io.axi.out:=io.axi.out
            io.axi.in:=clint.io.axi.in
            io.soc_axi.out:=axi_o
    }.otherwise{
            io.soc_axi.out:=io.axi.out
            clint.io.axi.out:=axi_o
            io.axi.in:=io.soc_axi.in
    } 

}