package com.nacht.design.pattern.no_03_factory;

/**
 * @author Nacht
 * Created on 2020/8/3
 */
public class MilkFactory implements AbstractFactory{
    @Override
    public Product createProduct() {
        System.out.println("creating product --> milk");
        return new MilkProduct();
    }
}
