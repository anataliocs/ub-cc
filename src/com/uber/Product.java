package com.uber;

public class Product {

    private Integer i;
    private Integer j;

    public Product() {}

    public Product(Integer i, Integer j) {
        this.i = i;
        this.j = j;
    }

    public Integer getProduct(Integer i, Integer j) {
        return i * j;
    }


    public Integer getI() {
        return i;
    }

    public void setI(Integer i) {
        this.i = i;
    }

    public Integer getJ() {
        return j;
    }

    public void setJ(Integer j) {
        this.j = j;
    }
}
