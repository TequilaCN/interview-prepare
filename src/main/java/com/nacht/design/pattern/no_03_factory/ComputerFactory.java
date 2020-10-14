package com.nacht.design.pattern.no_03_factory;

/**
 * @author Nacht
 * Created on 2020/8/3
 */
public class ComputerFactory implements AbstractFactory{
    @Override
    public Product createProduct() {
        System.out.println("creating product --> computer");
        return new ComputerProduct();
    }
}
