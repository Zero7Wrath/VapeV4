package com.zero7wrath.clientbase.modules;

import com.zero7wrath.clientbase.Category;
import com.zero7wrath.clientbase.modules.features.ClickGui;
import com.zero7wrath.clientbase.modules.features.FullBright;
import com.zero7wrath.clientbase.modules.features.Sprint;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Manager {

    private final ArrayList<Module> modules = new ArrayList<Module>();

    public void init() {
        modules.clear();

        register(new ClickGui());
        register(new FullBright());
        register(new Sprint());
    }

    public void register(Module module) {
        if (module == null || getModuleByName(module.getName()) != null) {
            return;
        }
        modules.add(module);
    }

    public void onTick() {
        for (Module module : modules) {
            if (module.isEnabled()) {
                module.onUpdate();
            }
        }
    }

    public ArrayList<Module> getModules() {
        return new ArrayList<Module>(modules);
    }

    public List<Module> getModulesReadOnly() {
        return Collections.unmodifiableList(modules);
    }

    public <T extends Module> T getModule(Class<T> type) {
        for (Module module : modules) {
            if (type.isInstance(module)) {
                return type.cast(module);
            }
        }
        return null;
    }

    public Module getModuleByName(String name) {
        if (name == null) {
            return null;
        }

        for (Module module : modules) {
            if (module.getName().equalsIgnoreCase(name)) {
                return module;
            }
        }
        return null;
    }

    public ArrayList<Module> getModulesByCategory(Category category) {
        ArrayList<Module> result = new ArrayList<Module>();

        for (Module module : modules) {
            if (module.getCategory() == category) {
                result.add(module);
            }
        }

        return result;
    }
}
