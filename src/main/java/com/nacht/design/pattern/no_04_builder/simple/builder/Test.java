package com.nacht.design.pattern.no_04_builder.simple.builder;

/**
 * @author Nacht
 * Created on 2020/9/7
 */
public class Test {

    public static void main(String[] args) {
        Computer computer = new Computer.Builder("奔腾2009", "金士顿内存100G").setUsbCount(3).setDisplay("三星4k显示器").setKeyboard("罗技10键键盘").build();
        System.out.println("电脑配置: " + computer);
    }

}
