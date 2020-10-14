package com.nacht.design.pattern.no_03_factory;

/**
 * @author Nacht
 * Created on 2020/8/3
 */
public interface AbstractFactory {
    /**
     * create a new product.
     * @return
     */
    Product createProduct();
}
