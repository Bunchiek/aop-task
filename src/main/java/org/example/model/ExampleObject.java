package org.example.model;


public class ExampleObject {

    private String name;

    public ExampleObject(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ExampleObject{" +
                "name='" + name + '\'' +
                '}';
    }
}
