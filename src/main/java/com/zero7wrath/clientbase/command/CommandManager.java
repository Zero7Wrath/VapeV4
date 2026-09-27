package com.zero7wrath.clientbase.command;

import com.zero7wrath.clientbase.Client;
import com.zero7wrath.clientbase.modules.Module;
import net.lax1dude.eaglercraft.Keyboard;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TextComponentString;

public final class CommandManager {
    private CommandManager() {}

    public static boolean handle(String message) {
        if (message == null || !message.startsWith(".")) return false;

        String line = message.substring(1).trim();
        if (line.length() == 0) return true;

        String[] parts = line.split("\\s+");
        String command = parts[0].toLowerCase();

        if ("toggle".equals(command)) {
            Module module = module(parts, 1);
            if (module != null) {
                module.toggle();
                say(module.getName() + " " + (module.isEnabled() ? "enabled" : "disabled"));
            }
            return true;
        }

        if ("bind".equals(command)) {
            if (parts.length < 3) {
                say("Usage: .bind <module> <key|none>");
                return true;
            }
            Module module = Client.INSTANCE == null ? null : Client.INSTANCE.getManager().getModuleByName(parts[1]);
            if (module == null) {
                say("Unknown module: " + parts[1]);
                return true;
            }
            int key = keyCode(parts[2]);
            if (key == -2) {
                say("Unknown key: " + parts[2]);
                return true;
            }
            module.setKeyBind(key);
            com.zero7wrath.clientbase.config.ConfigManager.save("default");
            say(module.getName() + " bound to " + parts[2]);
            return true;
        }

        if ("vapegui".equals(command) || "clickgui".equals(command)) {
            Minecraft.getMinecraft().displayGuiScreen(new net.minecraft.client.gui.ClickGuiScreen());
            return true;
        }

        if ("help".equals(command) || "commands".equals(command)) {
            say(".toggle <module>  .bind <module> <key>  .vapegui");
            return true;
        }

        return true;
    }

    private static Module module(String[] parts, int index) {
        if (Client.INSTANCE == null || parts.length <= index) {
            say("Usage: .toggle <module>");
            return null;
        }
        Module module = Client.INSTANCE.getManager().getModuleByName(parts[index]);
        if (module == null) say("Unknown module: " + parts[index]);
        return module;
    }

    private static void say(String text) {
        Minecraft mc = Minecraft.getMinecraft();
        if (mc != null && mc.ingameGUI != null) {
            mc.ingameGUI.getChatGUI().printChatMessage(new TextComponentString("§7[§aVape§7] " + text));
        }
    }

    private static int keyCode(String raw) {
        String key = raw.toLowerCase();
        if ("none".equals(key) || "off".equals(key)) return -1;
        if (key.length() == 1) {
            char c = Character.toUpperCase(key.charAt(0));
            for (int i = 2; i <= 57; i++) {
                if (Keyboard.getKeyName(i) != null && Keyboard.getKeyName(i).equalsIgnoreCase(String.valueOf(c))) return i;
            }
        }
        try {
            return Integer.parseInt(key);
        } catch (NumberFormatException ignored) {}
        String normalized = key.replace("-", "").replace("_", "");
        String[] names = {"space","lshift","rshift","lctrl","rctrl","lalt","ralt","tab","enter","escape","backspace","up","down","left","right","f1","f2","f3","f4","f5","f6","f7","f8","f9","f10","f11","f12"};
        int[] codes = {57,42,54,29,157,56,184,15,28,1,14,200,208,203,205,59,60,61,62,63,64,65,66,67,68,87};
        for (int i = 0; i < names.length; i++) if (names[i].equals(normalized)) return codes[i];
        return -2;
    }
}
