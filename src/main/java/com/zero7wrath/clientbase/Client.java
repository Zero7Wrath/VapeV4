package com.zero7wrath.clientbase;

import com.zero7wrath.clientbase.modules.Manager;
import com.zero7wrath.clientbase.config.ConfigManager;
import com.zero7wrath.clientbase.macro.MacroManager;

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
        MacroManager.tick();
    }

    public void onPostTick() {
        if (manager != null) {
            manager.onTick();
        }
    }
}
