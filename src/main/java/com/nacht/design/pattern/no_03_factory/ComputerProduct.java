package com.nacht.design.pattern.no_03_factory;

/**
 * @author Nacht
 * Created on 2020/8/3
 */
public class ComputerProduct implements Product{
    @Override
    public void showDescription() {
        System.out.println("this is a computer product");
    }
}
