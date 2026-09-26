package com.zero7wrath.clientbase;

import com.zero7wrath.clientbase.modules.Manager;
import com.zero7wrath.clientbase.config.ConfigManager;

public class Client {

    public static Client INSTANCE;
    public static Manager manager;

    public void init() {
        INSTANCE = this;
        manager = new Manager();
        manager.init();
        ConfigManager.ensureDirectory();
        ConfigManager.load("default");
    }

    public void onTick() {
        if (manager != null) {
            manager.onTick();
        }
    }
}
