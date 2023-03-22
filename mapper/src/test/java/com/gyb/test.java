package com.gyb;

/**
 * @date 2023/3/18 - 13:26
 */
public class test {

    public static void main(String[] args) {
        B b = new B(1, 2);
        System.out.println(b.getB());
    }

}


class A{
    private int b;
    private int c;

    public A() {
    }

    public A(int b, int c) {
        this.b = b;
        this.c = c;
    }

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }

    public int getC() {
        return c;
    }

    public void setC(int c) {
        this.c = c;
    }
}

class B extends A{

    public B() {
    }

    public B(int b, int c) {
        super(b, c);
    }
}
