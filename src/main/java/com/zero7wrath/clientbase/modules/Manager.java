package com.zero7wrath.clientbase.modules;

import com.zero7wrath.clientbase.Category;
import com.zero7wrath.clientbase.modules.features.ClickGui;
import com.zero7wrath.clientbase.modules.features.FullBright;
import com.zero7wrath.clientbase.modules.features.Sprint;
import com.zero7wrath.clientbase.modules.features.UniversalModule;
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
        registerUniversal(Category.Blatant, new String[]{"AntiFall","Fly","HighJump","HitBoxes","Invisible","Jesus","Killaura","LongJump","MouseTP","Phase","Speed","Spider","SpinBot","Swim","TargetStrafe","Timer"});
        registerUniversal(Category.Combat, new String[]{"AimAssist","AutoClicker","Reach","SilentAim","TriggerBot"});
        registerUniversal(Category.Legit, new String[]{"Atmosphere","Breadcrumbs","Cape","ChinaHat","Clock","Disguise","FOV","FPS","Keystrokes","Memory","Ping","SongBeats","Speedmeter","TimeChanger"});
        registerUniversal(Category.Render, new String[]{"Arrows","Chams","ESP","Fullbright","GamingChair","Health","NameTags","PlayerModel","Radar","Search","SessionInfo","Spotify","Tracers","Waypoints"});
        registerUniversal(Category.Utility, new String[]{"AnimationPlayer","AntiRagdoll","AutoRejoin","Blink","ChatSpammer","Disabler","HumSpoofer","Panic","Rejoin","ServerHop","StaffDetector"});
        registerUniversal(Category.World, new String[]{"Anti-AFK","FastProxPrompt","Freecam","Gravity","MurderMystery","Parkour","SafeWalk","Wallhop","Xray"});
    }

    private void registerUniversal(Category category, String[] names) {
        for (String name : names) register(new UniversalModule(name, category));
    }

    public void register(Module module) {
        if (module == null || getModuleByName(module.getName()) != null) return;
        modules.add(module);
    }

    public void onTick() {
        for (Module module : modules) if (module.isEnabled()) module.onUpdate();
    }

    public ArrayList<Module> getModules() { return new ArrayList<Module>(modules); }
    public List<Module> getModulesReadOnly() { return Collections.unmodifiableList(modules); }

    public <T extends Module> T getModule(Class<T> type) {
        for (Module module : modules) if (type.isInstance(module)) return type.cast(module);
        return null;
    }

    public Module getModuleByName(String name) {
        if (name == null) return null;
        for (Module module : modules) if (module.getName().equalsIgnoreCase(name)) return module;
        return null;
    }

    public ArrayList<Module> getModulesByCategory(Category category) {
        ArrayList<Module> result = new ArrayList<Module>();
        for (Module module : modules) if (module.getCategory() == category) result.add(module);
        return result;
    }
}
