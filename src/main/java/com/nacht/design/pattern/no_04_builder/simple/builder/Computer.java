package com.nacht.design.pattern.no_04_builder.simple.builder;

/**
 * 当一个实体类的属性比较多时, 无论是采用构造方法的方式还是set方法的方式都会过于繁琐
 * 此时可以通过在computer内部建一个内部builder类的方式来进行初始化, 并提供链式调用, 这样就算需要按顺序进行参数设置的场景也可以让调用过程清晰明了
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


    private Computer(Builder builder) {
        this.cpu = builder.cpu;
        this.ram = builder.ram;
        this.usbCount = builder.usbCount;
        this.keyboard = builder.keyboard;
        this.display = builder.display;
    }

    @Override
    public String toString() {
        return "Computer{" +
                "cpu='" + cpu + '\'' +
                ", ram='" + ram + '\'' +
                ", usbCount=" + usbCount +
                ", keyboard='" + keyboard + '\'' +
                ", display='" + display + '\'' +
                '}';
    }

    public static class Builder {
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

        public Builder(String cpu, String ram) {
            this.cpu = cpu;
            this.ram = ram;
        }

        public Builder setUsbCount(int usbCount) {
            this.usbCount = usbCount;
            return this;
        }

        public Builder setKeyboard(String keyboard) {
            this.keyboard = keyboard;
            return this;
        }

        public Builder setDisplay(String display) {
            this.display = display;
            return this;
        }

        public Computer build() {
            return new Computer(this);
        }
    }
}
