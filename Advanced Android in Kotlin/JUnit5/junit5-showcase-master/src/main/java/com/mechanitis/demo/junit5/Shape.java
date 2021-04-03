package com.mechanitis.demo.junit5;
// Это unit для тестирования, который класс для хранения многогранника.
public class Shape {
    // переменная - параметр класса, кол-во сторон многоугольника передается в конструктор
    private final int numberOfSides;
// Это конструктор класса, через него мы создаем конкретный экземпляр класса для многоугольника
    //их может быть много конструкторов, и еще они могут быть перегруженными.
    public Shape(int numberOfSides) {
        if (numberOfSides < 3 || numberOfSides == Integer.MAX_VALUE) {
            throw new IllegalArgumentException();
        }
        this.numberOfSides = numberOfSides;
    }
// метод (функция) numberOfSides класса - она возвращает кол-во сторон конкретного экземпляра
    public int numberOfSides() {
        return numberOfSides;
    }
    // метод (функция) description класса - она возвращает слово названия фигуры по кол-ву сторон
    public String description() {
        return switch (numberOfSides) {
            case 3 -> "Triangle";
            case 4 -> "Square";
            case 5 -> "Pentagon";
            default -> "Shape with " + numberOfSides + " sides";
        };
    }
}
