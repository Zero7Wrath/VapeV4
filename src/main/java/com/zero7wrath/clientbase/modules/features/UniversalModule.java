package com.zero7wrath.clientbase.modules.features;

import com.zero7wrath.clientbase.Category;
import com.zero7wrath.clientbase.modules.Module;
import com.zero7wrath.clientbase.settings.Setting;

public class UniversalModule extends Module {
    public UniversalModule(String name, Category category) {
        super(name, "Vape V4 " + name + " module.", category);
    }

    public UniversalModule(String name, Category category, Setting<?>... settings) {
        this(name, category);
        if (settings != null) for (Setting<?> setting : settings) this.settings.add(setting);
    }
}
