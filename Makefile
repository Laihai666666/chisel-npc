BUILD_DIR = ./build

default: verilog

verilog:
	mkdir -p $(BUILD_DIR)
	mill -i npc.runMain npc.ysyx_23060351 

test:
	mill -i __.test


init:
	git submodule update --init --recursive
bump:
	git submodule foreach "git fetch origin&&git checkout master&&git reset --hard origin/master"


bsp:
	mill -i mill.bsp.BSP/install

idea:
	mill -i mill.scalalib.GenIdea/idea

help:
	mill -i npc.runMain npc.ysyx_23060351  --help

clean:
	-rm -rf $(BUILD_DIR)

.PHONY: clean init bump bsp idea help verilog emu