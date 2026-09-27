package com.zero7wrath.clientbase.modules;

import com.zero7wrath.clientbase.Category;
import com.zero7wrath.clientbase.settings.Setting;

import java.util.ArrayList;

public abstract class Module {

    private final String name;
    private final String description;
    private final Category category;
    private boolean enabled;
    private int keyBind = -1;

    public boolean open;

    protected final ArrayList<Setting<?>> settings = new ArrayList<Setting<?>>();

    protected Module(String name, Category category) {
        this(name, "-", category);
    }

    protected Module(String name, String description, Category category) {
        this.name = name;
        this.description = description;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Category getCategory() {
        return category;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        if (this.enabled == enabled) {
            return;
        }

        this.enabled = enabled;

        if (enabled) {
            onEnable();
        } else {
            onDisable();
        }
    }

    public void toggle() {
        setEnabled(!enabled);
    }

    public int getKeyBind() {
        return keyBind;
    }

    public void setKeyBind(int keyBind) {
        this.keyBind = keyBind;
    }

    public ArrayList<Setting<?>> getSettings() {
        return settings;
    }

    public void onEnable() {
    }

    public void onDisable() {
    }

    public void onUpdate() {
    }
}
