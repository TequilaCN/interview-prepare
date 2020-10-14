package com.nacht.design.pattern.no_01_singleton;

/**
 * 双重检查锁模式
 * 这种方式先进行判断实例是否为null
 * 如果为null再加锁
 * 在同步代码块里面再判断一次, 防止已经有别的线程已经进行了实例化
 * 这种方式仅仅对新建实例的过程进行加锁, 相比线程安全的懒汉式实现性能有提升
 * 但是volatile会稍微对性能有影响
 * 加volatile的原因是jvm对指令运行有指令重排的优化机制
 * 实例化一个对象的过程:
 * 1. 分配内存
 * 2. 初始化对象
 * 3. 将对象指针指向内存地址
 * 实际运行可能会被优化为1->3->2
 * 假设在线程A按上面的顺序去执行instance的初始化操作, 此时另外一个线程B调用getInstance方法, 由于已经分配了内存并且instance已经指向了分配好的内存地址, 线程B就会判断instance不等于null, 从而获取到一个未初始化完成的对象
 * 这就是所谓的DCL失效
 * @author Nacht
 * Created on 2020/7/30
 */
public class DCLSingleton {
    private static volatile DCLSingleton instance ;
    private DCLSingleton(){}
    public static DCLSingleton getInstance(){
        if(instance == null){
            synchronized (DCLSingleton.class){
                if(instance == null){
                    instance = new DCLSingleton();
                }
            }
        }
        return instance;
    }
}
