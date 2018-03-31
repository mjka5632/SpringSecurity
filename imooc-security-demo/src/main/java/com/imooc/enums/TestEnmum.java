package com.imooc.enums;

import com.imooc.dto.User;

public enum TestEnmum {
    CONTINUE(100), PROCESSING(200), CHECKPOINT(103);
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    TestEnmum(int id) {
        this.id = id;
    }

    public static final int dd = 11;
    /**
     *
     */
    private String aa;

    private String ee;

    public static void main(String[] args) {
        User user = new User();
        if (user != null) {

        }

    }


}
