package npc

import chisel3._
import chisel3.util._
import config.Configs._
import utils._


class CLINT extends Module {
  val io = IO(new Bundle {
    val axi = Flipped(new AXI_IO)
  })


  val MTIME_ADDR_LOW  = "h02000000".U  
  val MTIME_ADDR_HIGH = "h02000004".U  


  val idle :: busy :: Nil = Enum(2)
  val state = RegInit(idle)
  
  
  state :=MuxLookup(state,idle)(
         List(
             idle->Mux(io.axi.out.arvalid,busy,idle),
             busy->Mux(io.axi.in.rvalid,idle,busy)
         )
     )

  val mtime = RegInit(0.U(64.W))
  mtime := mtime + 1.U

  
  val rdata = WireDefault(0.U(32.W))

  when(state === busy) {
    switch(io.axi.out.araddr) {
      is(MTIME_ADDR_LOW)  { rdata := mtime(31, 0) }
      is(MTIME_ADDR_HIGH) { rdata := mtime(63, 32) }
    }
  }

  io.axi.in.rdata   := rdata
  io.axi.in.rvalid  := state === busy
  io.axi.in.arready := true.B  
  io.axi.in.rresp   := 0.U  
  io.axi.in.rlast   := true.B  
  io.axi.in.rid     := 0.U  

  // Unused write signals
  io.axi.in.awready := false.B
  io.axi.in.wready  := false.B
  io.axi.in.bresp   := 0.U
  io.axi.in.bvalid  := false.B
  io.axi.in.bid     := 0.U
}
