
package npc

import chisel3._
import chisel3.util._

import config.Configs._
import utils.OP_TYPES._
import utils._

class UART extends Module{
    val io=IO(new Bundle {
        val axi = Flipped(new AXI_IO)
    })
    val rg1=RegInit(UInt(8.W),0.U)
    val mtime = RegInit(0.U(64.W))
    mtime    :=  mtime + 1.U
    val ar=RegInit(UInt(32.W),0.U)
    
    val arvalid_i=WireDefault(false.B)
    val rdata_i=WireDefault(UInt(32.W),0.U)
    val araddr_i=WireDefault(UInt(32.W),0.U)
    val arready_i=WireDefault(true.B)
    val rresp_i=WireDefault(UInt(2.W),0.U)
    val rvalid_i=WireDefault(false.B)
    val rready_i=WireDefault(false.B)
    val awvalid_i=WireDefault(false.B)
    val wdata_i=WireDefault(UInt(32.W),0.U)
    val awaddr_i=WireDefault(UInt(32.W),0.U)
    val awready_i=WireDefault(true.B)
    val wstrb_i=WireDefault(UInt(8.W),0.U)
    val wvalid_i=WireDefault(false.B)
    val wready_i=WireDefault(true.B)
    val bresp_i=WireDefault(UInt(2.W),0.U)
    val bvalid_i=WireDefault(false.B)
    val bready_i=WireDefault(false.B)
    araddr_i:=io.axi.out.araddr
    arvalid_i:=io.axi.out.arvalid
    rready_i:=io.axi.out.rready
    awaddr_i:=io.axi.out.awaddr
    awvalid_i:=io.axi.out.awvalid
    wdata_i:=io.axi.out.wdata
    wstrb_i:=io.axi.out.wstrb
    wvalid_i:=io.axi.out.wvalid
    bready_i:=io.axi.out.bready

    io.axi.in.rdata:= rdata_i
    io.axi.in.arready:=arready_i
    io.axi.in.rresp:=rresp_i
    io.axi.in.rvalid:=rvalid_i
    io.axi.in.awready:=awready_i
    io.axi.in.wready:=wready_i
    io.axi.in.bresp:=bresp_i
    io.axi.in.bvalid:=bvalid_i
    when(arvalid_i&arready_i){
        ar:=araddr_i
        arready_i<=true.B
    }
    when(arvalid_i&arready_i){
        when(ar==="ha000004c".U){
            rdata_i:=mtime(63,32)
        }
        when(ar==="ha0000048".U){
            rdata_i:=mtime(31,0)
        }
    }
    when(awvalid_i&awready_i){
        ar:=awaddr_i
    }
    when(awready_i&&awvalid_i&&wvalid_i&&wready_i){
        when(ar==="ha00003f8".U){
            rg1:=wdata_i(7,0)
            printf("%c",rg1)
        }
    }
}