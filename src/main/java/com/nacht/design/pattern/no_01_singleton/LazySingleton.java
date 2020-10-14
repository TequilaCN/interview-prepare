package com.nacht.design.pattern.no_01_singleton;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.IntStream;

/**
 * 懒汉模式(线程安全, 如果去掉volatile和synchronized会有线程安全问题)
 * 特点: 类加载的时候没有生成实例, 只有第一次调用的时候才会创建单例
 * 必须添加volatile和synchronized关键字
 * 如果没有加关键字会出现线程同步问题
 * 假设两个线程A,B同时访问getInstance方法
 * A先获得cpu时间, 进入到instance==null代码块里面
 * 此时B获得cpu时间, 由于A还没有创建实例, 也进入instance == null代码块里面
 * 此时A重新获得cpu时间, 创建一个新的实例
 * B获得cpu时间之后也会创建一个新的实例
 * 这样就导致A和B线程获取到的不是同一个实例
 * @author Nacht
 * Created on 2020/7/30
 */
public class LazySingleton {
    private static volatile LazySingleton instance;
    private LazySingleton(){}
    public static  synchronized LazySingleton getInstance() throws InterruptedException {
        if(instance == null){
            Thread.sleep(500L); //假设没有关键字, 在这里sleep一段时间, 再通过多线程去获取, 就会破坏单例
            instance = new LazySingleton();
        }
        return instance;
    }
    public static void main(String[] args) throws InterruptedException {
        Set<LazySingleton> set = new HashSet<>();
        IntStream.rangeClosed(1, 100).forEach(i ->  new Thread(() ->
        {
            try {
                set.add(LazySingleton.getInstance());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        ).start());
        Thread.sleep(1000L);
        System.out.println(set.size());
    }
}
