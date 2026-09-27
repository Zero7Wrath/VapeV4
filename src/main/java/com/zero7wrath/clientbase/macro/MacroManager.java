package com.zero7wrath.clientbase.macro;

import net.lax1dude.eaglercraft.Keyboard;
import net.minecraft.client.Minecraft;

public final class MacroManager {
    public static final int MAX_MACROS = 8;
    private static final int[] keys = new int[MAX_MACROS];
    private static final String[] commands = new String[MAX_MACROS];
    private static final boolean[] wasDown = new boolean[MAX_MACROS];

    static {
        for (int i = 0; i < MAX_MACROS; ++i) {
            keys[i] = -1;
            commands[i] = "";
        }
    }

    private MacroManager() {
    }

    public static int getKey(int slot) {
        return keys[slot];
    }

    public static String getCommand(int slot) {
        return commands[slot];
    }

    public static void setKey(int slot, int key) {
        if (slot >= 0 && slot < MAX_MACROS) keys[slot] = key;
    }

    public static void setCommand(int slot, String command) {
        if (slot >= 0 && slot < MAX_MACROS) commands[slot] = command == null ? "" : command.trim();
    }

    public static void clear(int slot) {
        if (slot < 0 || slot >= MAX_MACROS) return;
        keys[slot] = -1;
        commands[slot] = "";
        wasDown[slot] = false;
    }

    public static void tick() {
        Minecraft mc = Minecraft.getMinecraft();
        if (mc == null || mc.player == null) return;

        for (int i = 0; i < MAX_MACROS; ++i) {
            int key = keys[i];
            if (key < 0) continue;

            boolean down = Keyboard.isKeyDown(key);
            if (down && !wasDown[i] && commands[i].length() > 0) {
                mc.player.sendChatMessage(commands[i]);
            }
            wasDown[i] = down;
        }
    }
}
