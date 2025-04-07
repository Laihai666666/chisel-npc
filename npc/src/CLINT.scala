package npc

import chisel3._
import chisel3.util._

import config.Configs._
import utils.OP_TYPES._
import utils._

class CLINT extends Module{
    val io=IO(new Bundle {
        val axi = Flipped(new AXI_IO)
    })

    val s_idle :: s_busy ::Nil=Enum(2)
    val state =RegInit(s_idle)
    state :=MuxLookup(state,s_idle)(
        List(
            s_idle->Mux(io.axi.out.arvalid,s_busy,s_idle),
            s_busy->Mux(io.axi.in.rvalid,s_idle,s_busy)
        )
    )
    val mtime = RegInit(0.U(64.W))
    mtime    :=  mtime + 1.U
    
    val rdata  = WireDefault(0.U(32.W))

    io.axi.in.rdata:= rdata
    io.axi.in.rvalid:=(state===s_busy)
    io.axi.in.arready:=true.B
    io.axi.in.rresp:=false.B
    
    io.axi.in.awready:=false.B
    io.axi.in.wready:=false.B
    io.axi.in.bresp:=0.U
    io.axi.in.bvalid:=false.B
    io.axi.in.bid:=0.U
    io.axi.in.rlast:=false.B
    io.axi.in.rid:=0.U
    
    when(state===s_busy){
        when(io.axi.out.araddr==="h02000004".U){
            rdata:=mtime(63,32)
        }
        when(io.axi.out.araddr==="h02000000".U){
            rdata:=mtime(31,0)
        }
    }

}