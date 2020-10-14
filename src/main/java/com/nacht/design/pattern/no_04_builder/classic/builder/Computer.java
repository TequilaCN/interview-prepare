package com.nacht.design.pattern.no_04_builder.classic.builder;

/**
 * @author Nacht
 * Created on 2020/9/7
 */
public class Computer {
    /**
     * cpu, 必填参数
     */
    private String cpu;
    /**
     * 内存. 必填参数
     */
    private String ram;
    /**
     * usb接口数, 可选参数
     */
    private int usbCount;
    /**
     * 键盘, 可选参数
     */
    private String keyboard;
    /**
     * 显示器, 可选参数
     */
    private String display;
}
