package com.zero7wrath.clientbase;

import com.zero7wrath.clientbase.modules.Manager;

public class Client {

    public static Client INSTANCE;
    public static Manager manager;

    public void init() {
        INSTANCE = this;
        manager = new Manager();
        manager.init();
    }

    public void onTick() {
        if (manager != null) {
            manager.onTick();
        }
    }
}
