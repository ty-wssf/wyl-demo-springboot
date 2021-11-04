package cn.demo.boot.spi.demo;

import cn.demo.boot.spi.enine.ISpi;

public interface IPrint extends ISpi<Integer> {

    void print(Integer level, String msg);

}
