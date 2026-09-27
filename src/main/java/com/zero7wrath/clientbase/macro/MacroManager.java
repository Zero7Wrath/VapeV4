package com.zero7wrath.clientbase.macro;

import java.util.ArrayList;
import java.util.List;

import net.lax1dude.eaglercraft.Keyboard;
import net.minecraft.client.Minecraft;
import com.zero7wrath.clientbase.Client;
import com.zero7wrath.clientbase.modules.Module;

public final class MacroManager {
    public static final class Macro {
        public int key = -1;
        public String module = "";

        public Macro() {}
        public Macro(int key, String module) {
            this.key = key;
            this.module = module == null ? "" : module;
        }
    }

    private static final List<Macro> macros = new ArrayList<Macro>();
    private static final List<Boolean> wasDown = new ArrayList<Boolean>();

    static {
        add();
        add();
        add();
        add();
        add();
        add();
        add();
        add();
    }

    private MacroManager() {}

    public static int size() {
        return macros.size();
    }

    public static void add() {
        macros.add(new Macro());
        wasDown.add(Boolean.FALSE);
    }

    public static int getKey(int slot) {
        return valid(slot) ? macros.get(slot).key : -1;
    }

    public static String getModule(int slot) {
        return valid(slot) ? macros.get(slot).module : "";
    }

    public static void setKey(int slot, int key) {
        if (valid(slot)) macros.get(slot).key = key;
    }

    public static void setModule(int slot, String module) {
        if (valid(slot)) macros.get(slot).module = module == null ? "" : module.trim();
    }

    public static void clear(int slot) {
        if (!valid(slot)) return;
        macros.get(slot).key = -1;
        macros.get(slot).module = "";
        wasDown.set(slot, Boolean.FALSE);
    }

    public static void tick() {
        Minecraft mc = Minecraft.getMinecraft();
        if (mc == null || mc.player == null || mc.currentScreen != null) return;

        for (int i = 0; i < macros.size(); ++i) {
            Macro macro = macros.get(i);
            if (macro.key < 0) continue;

            boolean down = Keyboard.isKeyDown(macro.key);
            boolean previous = wasDown.get(i).booleanValue();

            if (down && !previous && macro.module.length() > 0 && Client.INSTANCE != null) {
                Module module = Client.INSTANCE.getManager().getModuleByName(macro.module);
                if (module != null) module.toggle();
            }
            wasDown.set(i, Boolean.valueOf(down));
        }
    }

    private static boolean valid(int slot) {
        return slot >= 0 && slot < macros.size();
    }
}
