package com.zero7wrath.clientbase.modules.features.client;

import com.zero7wrath.clientbase.Category;
import com.zero7wrath.clientbase.modules.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ClickGuiScreen;

public class ClickGui extends Module {

    public ClickGui() {
        super("ClickGUI", "Controls the client's GUI.", Category.Client);
    }

    @Override
    public void onEnable() {
        Minecraft.getMinecraft().displayGuiScreen(new ClickGuiScreen());
    }

    @Override
    public void onDisable() {
        if (Minecraft.getMinecraft().currentScreen instanceof ClickGuiScreen) {
            Minecraft.getMinecraft().displayGuiScreen(null);
        }
    }
}
