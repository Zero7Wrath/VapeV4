package com.zero7wrath.clientbase.modules;

import com.zero7wrath.clientbase.Category;
import com.zero7wrath.clientbase.modules.features.ClickGui;
import com.zero7wrath.clientbase.modules.features.FullBright;
import com.zero7wrath.clientbase.modules.features.Sprint;
import com.zero7wrath.clientbase.modules.features.UniversalModule;
import com.zero7wrath.clientbase.modules.features.AntiFall;
import com.zero7wrath.clientbase.modules.features.Fly;
import com.zero7wrath.clientbase.modules.features.HighJump;
import com.zero7wrath.clientbase.modules.features.HitBoxes;
import com.zero7wrath.clientbase.modules.features.Invisible;
import com.zero7wrath.clientbase.modules.features.Jesus;
import com.zero7wrath.clientbase.modules.features.Killaura;
import com.zero7wrath.clientbase.modules.features.LongJump;
import com.zero7wrath.clientbase.modules.features.MouseTP;
import com.zero7wrath.clientbase.modules.features.Spider;
import com.zero7wrath.clientbase.modules.features.SpinBot;
import com.zero7wrath.clientbase.modules.features.Swim;
import com.zero7wrath.clientbase.modules.features.TargetStrafe;
import com.zero7wrath.clientbase.modules.features.Wallhop;
import com.zero7wrath.clientbase.modules.features.Gravity;
import com.zero7wrath.clientbase.modules.features.TimeChanger;
import com.zero7wrath.clientbase.modules.features.FOV;
import com.zero7wrath.clientbase.modules.features.TriggerBot;
import com.zero7wrath.clientbase.modules.features.Speed;
import com.zero7wrath.clientbase.modules.features.Phase;
import com.zero7wrath.clientbase.modules.features.NoFall;
import com.zero7wrath.clientbase.modules.features.Velocity;
import com.zero7wrath.clientbase.modules.features.AutoClicker;
import com.zero7wrath.clientbase.modules.features.AimAssist;
import com.zero7wrath.clientbase.modules.features.NoJumpDelay;
import com.zero7wrath.clientbase.modules.features.NoSlowdown;
import com.zero7wrath.clientbase.modules.features.Parkour;
import com.zero7wrath.clientbase.modules.features.SafeWalk;
import com.zero7wrath.clientbase.modules.features.AutoJump;
import com.zero7wrath.clientbase.modules.features.KeepSprint;
import com.zero7wrath.clientbase.modules.features.NoClickDelay;
import com.zero7wrath.clientbase.modules.features.FastPlace;
import com.zero7wrath.clientbase.modules.features.AutoTool;
import com.zero7wrath.clientbase.modules.features.Criticals;
import com.zero7wrath.clientbase.modules.features.AutoWeapon;
import com.zero7wrath.clientbase.modules.features.Reach;
import com.zero7wrath.clientbase.modules.features.NoWeather;
import com.zero7wrath.clientbase.modules.features.AutoRespawn;
import com.zero7wrath.clientbase.modules.features.AirJump;
import com.zero7wrath.clientbase.modules.features.NoPush;
import com.zero7wrath.clientbase.modules.features.NoHurtCam;
import net.lax1dude.eaglercraft.Keyboard;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Manager {
    private final ArrayList<Module> modules = new ArrayList<Module>();
    private final ArrayList<Module> keyDown = new ArrayList<Module>();

    public void init() {
        modules.clear();
        register(new ClickGui());
        register(new FullBright());
        register(new Sprint());
        register(new AntiFall()); register(new Fly()); register(new HighJump()); register(new HitBoxes());
        register(new Invisible()); register(new Jesus()); register(new Killaura()); register(new LongJump());
        register(new MouseTP()); register(new Spider()); register(new SpinBot()); register(new Swim());
        register(new TargetStrafe()); register(new Wallhop()); register(new Gravity()); register(new TimeChanger());
        register(new FOV()); register(new TriggerBot()); register(new Criticals()); register(new AutoWeapon()); register(new Reach());
        register(new Speed()); register(new Phase()); register(new NoFall()); register(new Velocity());
        register(new NoJumpDelay()); register(new NoSlowdown()); register(new AutoJump());
        register(new AutoClicker()); register(new AimAssist()); register(new KeepSprint());
        register(new NoClickDelay()); register(new FastPlace()); register(new AutoTool());
        register(new Parkour()); register(new SafeWalk());
        register(new NoWeather()); register(new AutoRespawn()); register(new AirJump());
        register(new NoPush()); register(new NoHurtCam());
        registerUniversal(Category.Blatant, new String[]{"Timer"});
        
        registerUniversal(Category.Legit, new String[]{"Atmosphere","Breadcrumbs","Cape","ChinaHat","Clock","Disguise","FOV","FPS","Keystrokes","Memory","Ping","SongBeats","Speedmeter","TimeChanger"});
        registerUniversal(Category.Render, new String[]{"Arrows","Chams","ESP","Fullbright","GamingChair","Health","NameTags","PlayerModel","Radar","Search","SessionInfo","Spotify","Tracers","Waypoints"});
        registerUniversal(Category.Utility, new String[]{"AnimationPlayer","AntiRagdoll","AutoRejoin","Panic"});
        registerUniversal(Category.World, new String[]{"Anti-AFK","BedProtector","ChestSteal","Freecam","Gravity","Xray"});
        registerUniversal(Category.Inventory, new String[]{"AutoBuy","AutoConsume","AutoHotbar","FastConsume","FastDrop"});
    }

    private void registerUniversal(Category category, String[] names) {
        for (String name : names) register(new UniversalModule(name, category));
    }

    public void register(Module module) {
        if (module == null || getModuleByName(module.getName()) != null) return;
        modules.add(module);
    }

    public void onTick() {
        for (Module module : modules) {
            if (module.getKeyBind() >= 0 && Keyboard.isKeyDown(module.getKeyBind())) {
                if (!keyDown.contains(module)) {
                    module.toggle();
                    keyDown.add(module);
                }
            } else {
                keyDown.remove(module);
            }
            if (module.isEnabled()) module.onUpdate();
        }
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
