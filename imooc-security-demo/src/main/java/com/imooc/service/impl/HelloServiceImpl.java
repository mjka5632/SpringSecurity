package com.imooc.service.impl;

import com.imooc.service.HelloService;

public class HelloServiceImpl implements HelloService {


    private String name;

    @Override
    public String greeting(String name) {
        FromData(name);

        validate(name);

        System.out.println("greeting--------------");
        System.out.println("username" + name);


        System.out.println("greeting--------------");
        System.out.println("username" + name);
        return "greeting" + name;
    }

    private void validate(String name) {
        System.out.println("greeting--------------");
        System.out.println("username" + name);
    }

    private void FromData(String name) {
        System.out.println("greeting--------------");
        System.out.println("username" + name);
    }

    public String greeting1(String age1, String name) {
//        System.out.println("greeting--------------");
//        System.out.println("username" + name);
String full = name + age1;
        return "greeting" + full;
    }

    public static void main(String[] args) {
        HelloServiceImpl service = new HelloServiceImpl();
        String age = "2";
        String name = "aa";
        service.greeting1(age, service.name);

        System.out.println("title");
        System.out.println("title");
        System.out.println("title");
        System.out.println("title");
        System.out.println("title");

    }
}
