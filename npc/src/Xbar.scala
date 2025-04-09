
package npc

import chisel3._
import chisel3.util._

import config.Configs._
import utils.OP_TYPES._
import utils._


class Xbar extends Module {
    val io = IO(new Bundle {
        val axi = Flipped(new AXI_IO)
        val soc_axi = new AXI_IO
    })

    
    val clint = Module(new CLINT())
    /* val sram = Module(new SRAM())
    sram.io.clk := clock
    sram.io.reset := reset */
    
    
    val axi_o = WireDefault(0.U.asTypeOf(new AXI_O))
    
    // Define address range constants
    val CLINT_START = "h02000000".U
    val CLINT_END   = "h0200ffff".U
    /* val SRAM_START  = "h80000000".U 
    val SRAM_END    = "h8fffffff".U */
    
    
    //val isClintAddr = io.axi.out.araddr >= CLINT_START && io.axi.out.araddr <= CLINT_END
    /* val isSramAddr = io.axi.out.araddr >= SRAM_START && io.axi.out.araddr <= SRAM_END */
    
    
    when (io.axi.out.araddr >= CLINT_START && io.axi.out.araddr <= CLINT_END) {
        // Route to CLINT
        clint.io.axi.out := io.axi.out
        io.axi.in := clint.io.axi.in
        //sram.io.axi.out := axi_o
        io.soc_axi.out := axi_o
    /* } .elsewhen (isSramAddr) {
        // Route to SRAM
        sram.io.axi.out := io.axi.out
        io.axi.in := sram.io.axi.in
        clint.io.axi.out := axi_o
        io.soc_axi.out := axi_o */
    } .otherwise {
        // Route to SOC
        io.soc_axi.out := io.axi.out
        clint.io.axi.out := axi_o
        //sram.io.axi.out := axi_o
        io.axi.in := io.soc_axi.in
    }
}
