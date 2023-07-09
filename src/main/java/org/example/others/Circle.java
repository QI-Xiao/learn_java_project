package org.example.others;

public class Circle implements IShape {

    @Override
    public void draw() {
        System.out.println("draw circle");
    }

    public static void main(String[] args) {
        IShape shape = new Circle();
        shape.draw();

        IShape shape2 = new Square();
        shape2.draw();

        IShape shape3 = new Rectangle();
        shape3.draw();
    }
}
