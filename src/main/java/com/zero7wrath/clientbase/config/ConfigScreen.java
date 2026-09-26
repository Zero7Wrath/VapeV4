package com.zero7wrath.clientbase.config;

import net.lax1dude.eaglercraft.KeyboardConstants;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;

import java.io.IOException;
import java.util.List;

public class ConfigScreen extends GuiScreen {
    private GuiTextField nameField;
    private List<String> configs;
    private String status = "";

    @Override
    public void initGui() {
        nameField = new GuiTextField(1, fontRendererObj, width / 2 - 100, 42, 200, 20);
        nameField.setMaxStringLength(32);
        nameField.setText("default");
        nameField.setFocused(true);
        refresh();
    }

    private void refresh() {
        ConfigManager.ensureDirectory();
        configs = ConfigManager.list();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();

        int left = width / 2 - 125;
        int top = 20;
        int right = width / 2 + 125;

        drawRect(left, top, right, Math.min(height - 20, 330), 0xF51B1A1C);
        drawRect(left + 1, top + 1, right - 1, top + 31, 0xFF171618);

        fontRendererObj.drawString("VAPE V4", left + 12, top + 11, 0xFFFFFFFF);
        fontRendererObj.drawString("PROFILES", left + 83, top + 11, 0xFF8C8C8C);

        fontRendererObj.drawString("CONFIG NAME", left + 12, 34, 0xFF8C8C8C);
        nameField.drawTextBox();

        button(left + 12, 70, left + 86, 92, "SAVE", mouseX, mouseY);
        button(left + 92, 70, left + 166, 92, "LOAD", mouseX, mouseY);
        button(left + 172, 70, right - 12, 92, "DELETE", mouseX, mouseY);

        fontRendererObj.drawString(status, left + 12, 105, 0xFFBDBDBD);
        fontRendererObj.drawString("SAVED CONFIGS", left + 12, 126, 0xFF707070);

        int y = 142;
        if (configs != null) {
            for (String config : configs) {
                if (y > height - 55) break;
                drawRect(left + 12, y, right - 12, y + 22, 0xFF1B1A1C);
                fontRendererObj.drawString(config, left + 20, y + 7, 0xFFC8C8C8);
                button(left + 192, y + 2, right - 12, y + 20, "LOAD", mouseX, mouseY);
                y += 25;
            }
        }

        button(left + 12, height - 48, right - 12, height - 25, "BACK", mouseX, mouseY);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    private void button(int x1, int y1, int x2, int y2, String label, int mouseX, int mouseY) {
        boolean hover = mouseX >= x1 && mouseX <= x2 && mouseY >= y1 && mouseY <= y2;
        drawRect(x1, y1, x2, y2, hover ? 0xFF303033 : 0xFF232124);
        int w = fontRendererObj.getStringWidth(label);
        fontRendererObj.drawString(label, x1 + (x2 - x1 - w) / 2, y1 + 7, 0xFFD0D0D0);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        nameField.mouseClicked(mouseX, mouseY, mouseButton);

        int left = width / 2 - 125;
        int right = width / 2 + 125;

        if (mouseButton == 0) {
            if (inside(mouseX, mouseY, left + 12, 70, left + 86, 92)) {
                ConfigManager.save(nameField.getText());
                refresh();
                status = "Saved " + nameField.getText();
                return;
            }
            if (inside(mouseX, mouseY, left + 92, 70, left + 166, 92)) {
                status = ConfigManager.load(nameField.getText()) ? "Loaded " + nameField.getText() : "Config not found";
                return;
            }
            if (inside(mouseX, mouseY, left + 172, 70, right - 12, 92)) {
                ConfigManager.delete(nameField.getText());
                refresh();
                status = "Deleted " + nameField.getText();
                return;
            }

            int y = 142;
            if (configs != null) {
                for (String config : configs) {
                    if (inside(mouseX, mouseY, left + 12, y, right - 12, y + 22)) {
                        nameField.setText(config);
                        ConfigManager.load(config);
                        status = "Loaded " + config;
                        return;
                    }
                    y += 25;
                }
            }

            if (inside(mouseX, mouseY, left + 12, height - 48, right - 12, height - 25)) {
                mc.displayGuiScreen(new net.minecraft.client.gui.ClickGuiScreen());
                return;
            }
        }

        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    private boolean inside(int mx, int my, int x1, int y1, int x2, int y2) {
        return mx >= x1 && mx <= x2 && my >= y1 && my <= y2;
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (nameField.isFocused()) {
            if (keyCode == KeyboardConstants.KEY_ESCAPE) {
                nameField.setFocused(false);
            } else {
                nameField.textboxKeyTyped(typedChar, keyCode);
            }
            return;
        }

        if (keyCode == KeyboardConstants.KEY_ESCAPE) {
            mc.displayGuiScreen(new net.minecraft.client.gui.ClickGuiScreen());
            return;
        }

        super.keyTyped(typedChar, keyCode);
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}
