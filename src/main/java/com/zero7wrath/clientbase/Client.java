package com.zero7wrath.clientbase;

import com.zero7wrath.clientbase.modules.Manager;
import com.zero7wrath.clientbase.config.ConfigManager;
import net.lax1dude.eaglercraft.Keyboard;
import net.lax1dude.eaglercraft.KeyboardConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ClickGuiScreen;

public class Client {

    public static Client INSTANCE;
    public static Manager manager;
    private boolean guiKeyDown;
    private static int guiKey = KeyboardConstants.KEY_F4;
    private static boolean bindingGuiKey;

    public static int getGuiKey() { return guiKey; }
    public static void setGuiKey(int key) { guiKey = key; }
    public static boolean isBindingGuiKey() { return bindingGuiKey; }
    public static void setBindingGuiKey(boolean binding) { bindingGuiKey = binding; }

    public void init() {
        INSTANCE = this;
        manager = new Manager();
        manager.init();
        ConfigManager.ensureDirectory();
        ConfigManager.load("default");
    }

    public void onTick() {
        Minecraft mc = Minecraft.getMinecraft();
        boolean down = Keyboard.isKeyDown(guiKey);

        if (down && !guiKeyDown && mc.currentScreen == null) {
            mc.displayGuiScreen(new ClickGuiScreen());
        }

        guiKeyDown = down;
    }

    public void onPostTick() {
        if (manager != null) {
            manager.onTick();
        }
    }
}
