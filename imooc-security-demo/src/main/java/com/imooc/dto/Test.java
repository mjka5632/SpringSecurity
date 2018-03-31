package com.imooc.dto;

import java.util.ArrayList;

public class Test {

    private final String name;

    public Test(String name){
        this.name = name;
    }

    public static void main(String[] args) {
        ArrayList<String> list = new ArrayList<>();
        f1(list);
    }

    private static void f1(ArrayList<String> list) {
        list.forEach(System.out::println);
    }
}
