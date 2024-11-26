package npc
import utils._
import chisel3._
import chisel3.util._
import config.Configs._
import _root_.circt.stage.ChiselStage
class Ebreak extends BlackBox with HasBlackBoxInline {
  val io = IO(new Bundle {
    val ctrlbreak = Input(Bool())
  })
  setInline(
    "Ebreak.sv",
    """
      |import "DPI-C" function void ebreak();
      |module Ebreak(
      |      input ctrlbreak);  
      |always @(*) begin
      | if (ctrlbreak)
      |   ebreak();
      |end
      |endmodule
  """.stripMargin
  )
}

class SRAM extends BlackBox with HasBlackBoxInline {
  val io = IO(new Bundle {
    val clk = Input(Clock())
    val reset = Input(Bool())
    val axi = Flipped(new AXI_IO)
  })
  setInline(
    "SRAM.sv",
    """
      |import "DPI-C" function int pmem_read(input int raddr);
      |import "DPI-C" function void pmem_write(
      |input int waddr, input int wdata, input byte wmask);
      |module SRAM(
      |     input clk,
      |     input reset,
      |     input [31:0] axi_out_araddr,
      |     input  axi_out_arvalid,
      |     input  axi_out_rready,
      |     input  [31:0]axi_out_awaddr,
      |     input  [31:0] axi_out_wdata,
      |     input [7:0] axi_out_wstrb,
      |     input axi_out_wvalid,
      |     input axi_out_awvalid,
      |     input axi_out_bready,
      |     output axi_in_awready,
      |     output axi_in_arready,
      |     output axi_in_rvalid,
      |     output [1:0] axi_in_rresp,
      |     output [31:0] axi_in_rdata,
      |     output axi_in_wready,
      |     output [1:0] axi_in_bresp,
      |     output axi_in_bvalid
      |);  
      | reg [31:0] rdata_i;
      | reg [1:0] rresp_i;
      | reg [31:0] araddr_i;
      | reg arvalid_i;
      | reg arready_i;
      | reg rvalid_i;
      | reg rready_i;
      | reg  [31:0]awaddr_i;
      |  reg awvalid_i;
      |  reg  awready_i;
      |  reg   [31:0] wdata_i;
      |  reg  [7:0] wstrb_i;
      |  reg  wvalid_i;
      |  reg  wready_i;
      |  reg  [1:0] bresp_i;
      |  reg  bvalid_i;
      |  reg  bready_i;
      | assign arvalid_i=axi_out_arvalid;
      | assign rready_i=axi_out_rready;
      | assign awvalid_i=axi_out_awvalid;
      | assign wdata_i=axi_out_wdata;
      | assign wstrb_i=axi_out_wstrb;
      | assign wvalid_i=axi_out_wvalid;
      |assign bready_i=axi_out_bready;
      |always @(posedge clk) begin
      |     if(reset)begin
      |       rvalid_i<=0;
      |       arready_i<=0;
      |       araddr_i<=0;
      |       rdata_i<=0;
      |       rresp_i<=0;
      |       awready_i<=1;
      |       wready_i<=1;
      |       bresp_i<=0;
      |       awaddr_i<=0;
      |       bvalid_i<=0;
      |     end
      |     else begin
      |     if(arvalid_i)begin
      |        araddr_i<=axi_out_araddr;
      |        arready_i<=1;
      |     end
      |     else begin
      |         arready_i<=0;
      |     end
      |     if(arvalid_i&&arready_i)begin
      |        rdata_i<=pmem_read(araddr_i);
      |        rvalid_i<=1;
      |        rresp_i<=0;
      |     end
      |     if(awready_i&&awvalid_i)begin
      |        awaddr_i<=axi_out_awaddr;
      |     end
      |     if(awready_i&&awvalid_i&&wvalid_i&&wready_i)begin
      |        pmem_write(awaddr_i,wdata_i,wstrb_i);
      |        bresp_i<=0;
      |        bvalid_i<=1;
      |     end
      |     end
      |end
      |assign axi_in_rdata=rdata_i;
      |assign axi_in_rresp=rresp_i;
      |assign axi_in_arready=arready_i;
      |assign axi_in_rvalid=rvalid_i;
      |assign axi_in_awready=awready_i;
      |assign axi_in_wready=wready_i;
      |assign axi_in_bresp=bresp_i;
      |assign axi_in_bvalid=bvalid_i;
      |endmodule
  """.stripMargin
  )
}


