package com.zero7wrath.clientbase.config;

import com.zero7wrath.clientbase.Client;
import com.zero7wrath.clientbase.modules.Module;
import com.zero7wrath.clientbase.settings.Setting;
import net.peyton.eagler.fs.FileUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public final class ConfigManager {
    private ConfigManager() {}

    private static File directory() {
        return new File(FileUtils.dataDir, "VapeV4/configs");
    }

    public static void ensureDirectory() {
        File dir = directory();
        if (!dir.exists()) dir.mkdirs();
    }

    public static List<String> list() {
        ensureDirectory();
        ArrayList<String> result = new ArrayList<String>();
        File[] files = directory().listFiles();
        if (files != null) {
            for (File file : files) {
                String name = file.getName();
                if (name.endsWith(".cfg")) result.add(name.substring(0, name.length() - 4));
            }
        }
        return result;
    }

    public static void save(String name) {
        ensureDirectory();
        File file = fileFor(name);
        PrintWriter out = null;
        try {
            out = new PrintWriter(new BufferedWriter(new FileWriter(file)));
            for (Module module : Client.manager.getModules()) {
                out.println("module|" + module.getName() + "|" + module.isEnabled());
                for (Setting<?> setting : module.getSettings()) {
                    Object value = setting.getValue();
                    out.println("setting|" + module.getName() + "|" + setting.getName() + "|" + String.valueOf(value));
                }
            }
        } catch (IOException ignored) {
        } finally {
            if (out != null) out.close();
        }
    }

    public static boolean load(String name) {
        ensureDirectory();
        File file = fileFor(name);
        if (!file.isFile()) return false;

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|", 4);
                if (parts.length < 3) continue;

                Module module = Client.manager.getModuleByName(parts[1]);
                if (module == null) continue;

                if ("module".equals(parts[0]) && parts.length >= 3) {
                    module.setEnabled(Boolean.parseBoolean(parts[2]));
                } else if ("setting".equals(parts[0]) && parts.length >= 4) {
                    applySetting(module, parts[2], parts[3]);
                }
            }
            return true;
        } catch (IOException ignored) {
            return false;
        } finally {
            if (reader != null) try { reader.close(); } catch (IOException ignored) {}
        }
    }

    public static void delete(String name) {
        File file = fileFor(name);
        if (file.isFile()) file.delete();
    }

    private static void applySetting(Module module, String name, String value) {
        for (Setting<?> setting : module.getSettings()) {
            if (!setting.getName().equalsIgnoreCase(name)) continue;

            if (setting instanceof Setting.BooleanSetting) {
                ((Setting.BooleanSetting) setting).setValue(Boolean.parseBoolean(value));
            } else if (setting instanceof Setting.NumberSetting) {
                try {
                    ((Setting.NumberSetting) setting).setValue(Double.parseDouble(value));
                } catch (NumberFormatException ignored) {}
            } else if (setting instanceof Setting.ModeSetting) {
                ((Setting.ModeSetting) setting).setValue(value);
            }
            return;
        }
    }

    private static File fileFor(String name) {
        if (name == null || name.trim().length() == 0) name = "default";
        name = name.trim().replaceAll("[^a-zA-Z0-9._-]", "_");
        if (name.length() == 0) name = "default";
        return new File(directory(), name + ".cfg");
    }
}
