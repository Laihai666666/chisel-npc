package config
import chisel3._
import scala.math._

object Configs {
  val ADDR_WIDTH          = 32
  val ADDR_BYTE_WIDTH     = ADDR_WIDTH / 8
  val INST_WIDTH          = 32
  val INST_BYTE_WIDTH     = INST_WIDTH / 8
  val INST_BYTE_WIDTH_LOG = ceil(log(INST_BYTE_WIDTH) / log(2)).toInt
  val MEM_INST_SIZE       = 1024
  val DATA_WIDTH          = 32
  val DATA_WIDTH_H        = 16
  val DATA_WIDTH_B        = 8
  val START_ADDR: UInt = 0x00000000.U
  val REG_NUMS       = 32
  val OP_TYPES_WIDTH = 4
  val REG_NUMS_LOG   = 5
  val LS_B           = 0x1.U(8.W)
  val LS_H           = 0x3.U(8.W)
  val LS_W           = 0xf.U(8.W)
}
