package com.nacht.design.pattern.no_02_prototype;

import cn.hutool.core.collection.CollUtil;

import java.util.List;

/**
 * java中的原型模式实现较为简单, 直接调用Object类的clone方法, 实现Cloneable接口即可
 * 需要注意的是直接使用Object的clone方法实现的是浅拷贝, 不是深拷贝
 * 浅拷贝和深拷贝的区别:
 * 浅拷贝: 重新在堆中创建内存, 拷贝前后对象的基本数据类型互不影响, 但是拷贝前后对象的引用类型因共享同一块内存, 会相互影响(如果为基本类型, 拷贝值, 如果为引用类型, 拷贝内存地址)
 * 深拷贝: 从堆内存中开辟一个新的区域存放新对象, 递归的拷贝旧对象到新对象中, 拷贝前后的两个对象互不影响
 * @author Nacht
 * Created on 2020/8/2
 */
public class PersonPrototype implements Cloneable{

    private String name ;
    private int age;
    private List<String> list ;

    public PersonPrototype(){
        System.out.println("新的person");
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "PersonPrototype{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", list=" + list +
                '}';
    }

    public static void main(String[] args) throws CloneNotSupportedException {
        PersonPrototype person = new PersonPrototype();
        person.setAge(15);
        person.setName("fuck");
        person.setList(CollUtil.newArrayList("a","b","c"));
        PersonPrototype person2 = (PersonPrototype) person.clone();
        System.out.println(person);
        System.out.println(person2);
        System.out.println(person.getList() == person2.getList());
    }
}
