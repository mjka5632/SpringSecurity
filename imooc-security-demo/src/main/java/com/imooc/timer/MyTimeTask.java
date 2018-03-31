package com.imooc.timer;

import java.util.TimerTask;

public class MyTimeTask extends TimerTask{
    private String name;

    public MyTimeTask(String inputName){
        name=inputName;
    }

    @Override
    public void run() {
        System.out.println("name"+name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
