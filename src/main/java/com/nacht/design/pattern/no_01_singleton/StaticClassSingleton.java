package com.nacht.design.pattern.no_01_singleton;

/**
 * 静态内部类方式
 * 静态内部类的优点是：外部类加载时并不需要立即加载内部类，内部类不被加载则不去初始化INSTANCE，故而不占内存。
 * 即当StaticClassSingleton第一次被加载时，并不需要去加载StaticClassSingletonHolder，只有当getInstance()方法第一次被调用时，才会去初始化INSTANCE,
 * 第一次调用getInstance()方法会导致虚拟机加载StaticClassSingletonHolder类，这种方法不仅能确保线程安全，也能保证单例的唯一性，同时也延迟了单例的实例化。
 * @author Nacht
 * Created on 2020/7/30
 */
public class StaticClassSingleton {
    private StaticClassSingleton(){}
    private static StaticClassSingleton getInstance(){
        return StaticClassSingletonHolder.sInstance;
    }
    private static class StaticClassSingletonHolder{
        private static final StaticClassSingleton sInstance = new StaticClassSingleton();
    }
}
