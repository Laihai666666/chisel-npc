
package npc

import chisel3._
import chisel3.util._

import config.Configs._
import utils.OP_TYPES._
import utils._


class Xbar extends Module{
    val io=IO(new Bundle{
        val axi=Flipped(new AXI_IO)
    })
    val sram=Module(new SRAM())
    sram.io.clk:=clock
    sram.io.reset:=reset
    val uart=Module(new UART())
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
    val axi_i=Wire(new AXI_I)
    axi_i.arready:=false.B
    axi_i.rdata:=0.U
    axi_i.rresp:=1.U
    axi_i.rvalid:=false.B
    axi_i.awready:=false.B
    axi_i.wready:=false.B
    axi_i.bresp:=0.U
    axi_i.bvalid:=false.B
    when((io.axi.out.arvalid&io.axi.out.araddr>="h80000000".U&io.axi.out.araddr<="h8fffffff".U)
        |(io.axi.out.awvalid&io.axi.out.awaddr>="h80000000".U&io.axi.out.awaddr<="h8fffffff".U)){
            sram.io.axi.out:=io.axi.out
            io.axi.in:=sram.io.axi.in
            uart.io.axi.out:=axi_o
     }.elsewhen((io.axi.out.arvalid&io.axi.out.araddr>="ha0000000".U&io.axi.out.araddr<="ha0000fff".U)
        |(io.axi.out.awvalid&io.axi.out.awaddr>="ha0000000".U&io.axi.out.awaddr<="ha0000fff".U)){
            uart.io.axi.out:=io.axi.out
            io.axi.in:=uart.io.axi.in
            sram.io.axi.out:=axi_o
    }.elsewhen(io.axi.out.arvalid===false.B&io.axi.out.awvalid===false.B){
            uart.io.axi.out:=axi_o
            sram.io.axi.out:=axi_o
            io.axi.in:=sram.io.axi.in
    }.otherwise{
            uart.io.axi.out:=axi_o
            sram.io.axi.out:=axi_o
            io.axi.in:=axi_i
    } 

}