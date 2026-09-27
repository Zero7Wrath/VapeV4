package com.zero7wrath.clientbase.macro;

import net.lax1dude.eaglercraft.KeyboardConstants;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;

import java.io.IOException;

public class MacroScreen extends GuiScreen {
    private final GuiScreen parent;
    private final GuiTextField[] fields = new GuiTextField[MacroManager.MAX_MACROS];
    private int listeningSlot = -1;

    public MacroScreen(GuiScreen parent) {
        this.parent = parent;
    }

    @Override
    public void initGui() {
        for (int i = 0; i < fields.length; ++i) {
            fields[i] = new GuiTextField(100 + i, fontRendererObj, width / 2 - 35, 45 + i * 27, 190, 20);
            fields[i].setMaxStringLength(100);
            fields[i].setText(MacroManager.getCommand(i));
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();

        int left = width / 2 - 150;
        int right = width / 2 + 150;
        drawRect(left, 18, right, Math.min(height - 18, 295), 0xF5191A1C);
        drawRect(left, 18, right, 49, 0xFF141517);

        fontRendererObj.drawString("VAPE V4", left + 12, 29, 0xFFFFFFFF);
        fontRendererObj.drawString("MACROS", left + 78, 29, 0xFF43E06D);

        for (int i = 0; i < fields.length; ++i) {
            int y = 45 + i * 27;
            fontRendererObj.drawString("Macro " + (i + 1), left + 12, y + 6, 0xFFC8C8C8);
            drawRect(left + 105, y, left + 135, y + 20,
                    listeningSlot == i ? 0xFF43E06D : 0xFF25272A);
            String key = MacroManager.getKey(i) < 0 ? "SET" : net.lax1dude.eaglercraft.Keyboard.getKeyName(MacroManager.getKey(i));
            fontRendererObj.drawString(key, left + 112, y + 6,
                    listeningSlot == i ? 0xFF101311 : 0xFFBFC1C3);
            fields[i].drawTextBox();
            if (inside(mouseX, mouseY, left + 105, y, left + 135, y + 20)) {
                drawRect(left + 105, y, left + 135, y + 20, 0xFF303336);
            }
        }

        button(left + 12, height - 47, left + 105, height - 24, "BACK", mouseX, mouseY);
        button(right - 105, height - 47, right - 12, height - 24, "CLEAR", mouseX, mouseY);

        fontRendererObj.drawString("Click SET, then press a key. Commands can start with /.",
                left + 12, Math.min(height - 65, 275), 0xFF7E8083);

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    private void button(int x1, int y1, int x2, int y2, String label, int mouseX, int mouseY) {
        boolean hover = inside(mouseX, mouseY, x1, y1, x2, y2);
        drawRect(x1, y1, x2, y2, hover ? 0xFF303336 : 0xFF232527);
        int w = fontRendererObj.getStringWidth(label);
        fontRendererObj.drawString(label, x1 + (x2 - x1 - w) / 2, y1 + 7, 0xFFD0D1D2);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        int left = width / 2 - 150;
        if (mouseButton == 0) {
            for (int i = 0; i < fields.length; ++i) {
                int y = 45 + i * 27;
                if (inside(mouseX, mouseY, left + 105, y, left + 135, y + 20)) {
                    listeningSlot = i;
                    return;
                }
                if (inside(mouseX, mouseY, width / 2 - 35, y, width / 2 + 155, y + 20)) {
                    fields[i].mouseClicked(mouseX, mouseY, mouseButton);
                    return;
                }
            }

            if (inside(mouseX, mouseY, left + 12, height - 47, left + 105, height - 24)) {
                mc.displayGuiScreen(parent);
                return;
            }

            if (inside(mouseX, mouseY, width / 2 + 45, height - 47, width / 2 + 138, height - 24)) {
                for (int i = 0; i < fields.length; ++i) {
                    MacroManager.clear(i);
                    fields[i].setText("");
                }
                return;
            }
        }

        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (listeningSlot >= 0) {
            if (keyCode == KeyboardConstants.KEY_ESCAPE) {
                listeningSlot = -1;
            } else {
                MacroManager.setKey(listeningSlot, keyCode);
                listeningSlot = -1;
            }
            return;
        }

        for (GuiTextField field : fields) {
            if (field.isFocused()) {
                field.textboxKeyTyped(typedChar, keyCode);
                for (int i = 0; i < fields.length; ++i) {
                    if (fields[i] == field) MacroManager.setCommand(i, field.getText());
                }
                return;
            }
        }

        if (keyCode == KeyboardConstants.KEY_ESCAPE) {
            mc.displayGuiScreen(parent);
            return;
        }

        super.keyTyped(typedChar, keyCode);
    }

    private boolean inside(int mx, int my, int x1, int y1, int x2, int y2) {
        return mx >= x1 && mx <= x2 && my >= y1 && my <= y2;
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}
