package npc

import chisel3._
import chisel3.util._
import config.Configs._
import utils._


class SRAM extends BlackBox with HasBlackBoxInline {
  val io = IO(new Bundle {
    val clk = Input(Clock())
    val reset = Input(Bool())
    val axi = Flipped(new AXI_IO)
  })
  setInline(
    "SRAM.sv",
    """
      |import "DPI-C" function int pmem_read(input int addr);
      |import "DPI-C" function void pmem_write(input int addr, input int data,input int wmask);
      |module SRAM(
      |     input clk,
      |     input reset,
      |     // AXI输入信号 (来自主设备)
      |     input [31:0] axi_out_araddr,
      |     input axi_out_arvalid,
      |     input [3:0] axi_out_arid,
      |     input [7:0] axi_out_arlen,
      |     input [2:0] axi_out_arsize,
      |     input [1:0] axi_out_arburst,
      |     input axi_out_rready,
      |     input [31:0] axi_out_awaddr,
      |     input [31:0] axi_out_wdata,
      |     input [3:0] axi_out_wstrb,
      |     input axi_out_wvalid,
      |     input axi_out_wlast,
      |     input axi_out_awvalid,
      |     input [3:0] axi_out_awid,
      |     input [7:0] axi_out_awlen,
      |     input [2:0] axi_out_awsize,
      |     input [1:0] axi_out_awburst,
      |     input axi_out_bready,
      |     // AXI输出信号 (到主设备)
      |     output axi_in_awready,
      |     output axi_in_arready,
      |     output axi_in_rvalid,
      |     output [1:0] axi_in_rresp,
      |     output [31:0] axi_in_rdata,
      |     output [3:0] axi_in_rid,
      |     output axi_in_rlast,
      |     output axi_in_wready,
      |     output [1:0] axi_in_bresp,
      |     output axi_in_bvalid,
      |     output [3:0] axi_in_bid
      |);  
      | reg [31:0] rdata_i;
      | reg [1:0] rresp_i;
      | reg rvalid_i;
      | reg [31:0] awaddr_reg;     // 写地址寄存器
      | reg awvalid_reg;           // 写地址有效标志
      | reg [1:0] bresp_i;
      | reg bvalid_i;
      | wire arready_i = !rvalid_i; // 读准备好当没有有效读数据
      | wire awready_i = !bvalid_i && !awvalid_reg; // 写准备好当没有有效写响应且没有pending的写地址
      | wire wready_i = awvalid_reg;  // 写数据准备好当有有效的写地址
      |always @(posedge clk) begin
      |     if(reset)begin
      |       rvalid_i <= 0;
      |       rdata_i <= 0;
      |       rresp_i <= 0;
      |       bresp_i <= 0;
      |       bvalid_i <= 0;
      |       awaddr_reg <= 0;
      |       awvalid_reg <= 0;
      |     end
      |     else begin
      |     if(axi_out_arvalid && arready_i)begin
      |        if(axi_out_araddr[31:28] != 4'h8) begin // 检查地址是否在0x80000000-0x8FFFFFFF范围内
      |          rresp_i <= 2'b11; // SLVERR响应
      |          rvalid_i <= 1;
      |          rdata_i <= 32'h0;
      |        end else begin
      |          rdata_i <=pmem_read(axi_out_araddr);
      |          rvalid_i <= 1;
      |          rresp_i <= 0;
      |        end
      |     end 
      |     if(axi_out_rready && rvalid_i) begin
      |        rvalid_i <= 0; 
      |     end
      |     // 处理写地址阶段
      |     if(axi_out_awvalid && awready_i) begin
      |        awaddr_reg <= axi_out_awaddr;
      |        awvalid_reg <= 1'b1;
      |     end
      |
      |     // 处理写数据阶段
      |     if(axi_out_wvalid && wready_i ) begin
      |        if(awaddr_reg[31:28] != 4'h8) begin // 检查地址是否在0x80000000-0x8FFFFFFF范围内
      |          bresp_i <= 2'b11; // SLVERR响应
      |          bvalid_i <= 1'b1;
      |        end else begin
      |          pmem_write(awaddr_reg, axi_out_wdata, {{8{axi_out_wstrb[3]}},{8{axi_out_wstrb[2]}},{8{axi_out_wstrb[1]}},{8{axi_out_wstrb[0]}}});
      |          bresp_i <= 2'b00;
      |          bvalid_i <= 1'b1;
      |        end
      |        awvalid_reg <= 1'b0; // 清除地址有效标志
      |     end 
      |     if(axi_out_bready && bvalid_i) begin
      |        bvalid_i <= 0; 
      |     end
      |     end
      |end
      |assign axi_in_rdata = rdata_i;
      |assign axi_in_rresp = rresp_i;
      |assign axi_in_rid = axi_out_arid;
      |assign axi_in_rlast = 1'b1;
      |assign axi_in_arready = arready_i;
      |assign axi_in_rvalid = rvalid_i;
      |assign axi_in_awready = awready_i;
      |assign axi_in_wready = wready_i;
      |assign axi_in_bresp = bresp_i;
      |assign axi_in_bvalid = bvalid_i;
      |assign axi_in_bid = axi_out_awid;
      |endmodule
  """.stripMargin
  )
}
