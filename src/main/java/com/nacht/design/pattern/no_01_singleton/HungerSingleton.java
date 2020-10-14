package com.nacht.design.pattern.no_01_singleton;

/**
 * 饿汉模式
 * 类加载时进行初始化, 没有线程安全问题, 缺点是会影响程序启动时的性能
 * @author Nacht
 * Created on 2020/7/30
 */
public class HungerSingleton {
    private static HungerSingleton instance = new HungerSingleton();
    private HungerSingleton(){}
    public static HungerSingleton getInstance(){
        return instance;
    }
}
