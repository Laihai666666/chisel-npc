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
      | if (ctrlbreak)begin
      |   ebreak();
      | end
      |end
      |endmodule
  """.stripMargin
  )
}



