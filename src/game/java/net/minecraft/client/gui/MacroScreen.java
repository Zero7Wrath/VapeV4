package net.minecraft.client.gui;

import com.zero7wrath.clientbase.macro.MacroManager;
import net.lax1dude.eaglercraft.KeyboardConstants;

import java.io.IOException;

public class MacroScreen extends GuiScreen {
    private final GuiScreen parent;
    private final GuiTextField[] commandFields = new GuiTextField[MacroManager.MAX_MACROS];
    private int selectedSlot = -1;
    private String status = "Click a key box, then press a key.";

    public MacroScreen(GuiScreen parent) {
        this.parent = parent;
    }

    @Override
    public void initGui() {
        for (int i = 0; i < MacroManager.MAX_MACROS; ++i) {
            commandFields[i] = new GuiTextField(
                    20 + i, fontRendererObj,
                    width / 2 - 70, 42 + i * 28,
                    190, 20
            );
            commandFields[i].setMaxStringLength(100);
            commandFields[i].setText(MacroManager.getCommand(i));
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();

        int left = width / 2 - 145;
        int right = width / 2 + 145;

        drawRect(left, 18, right, Math.min(height - 12, 300), 0xF51B1A1C);
        drawRect(left + 1, 19, right - 1, 48, 0xFF171618);

        fontRendererObj.drawString("VAPE V4", left + 12, 29, 0xFFFFFFFF);
        fontRendererObj.drawString("MACROS", left + 78, 29, 0xFF43E06D);
        fontRendererObj.drawString(status, left + 12, 54, 0xFF8C8C8C);

        for (int i = 0; i < MacroManager.MAX_MACROS; ++i) {
            int y = 70 + i * 28;
            String key = MacroManager.getKey(i) < 0 ? "NONE" : "KEY " + MacroManager.getKey(i);

            drawRect(left + 12, y, left + 58, y + 22,
                    selectedSlot == i ? 0xFF43E06D : 0xFF252629);
            fontRendererObj.drawString(key, left + 17, y + 7,
                    selectedSlot == i ? 0xFF101311 : 0xFFD0D0D0);

            commandFields[i].drawTextBox();

            drawRect(right - 52, y, right - 12, y + 22, 0xFF252629);
            fontRendererObj.drawString("CLR", right - 43, y + 7, 0xFFD0D0D0);
        }

        button(left + 12, height - 42, right - 12, height - 20,
                "BACK", mouseX, mouseY);

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
        if (mouseButton != 0) {
            super.mouseClicked(mouseX, mouseY, mouseButton);
            return;
        }

        int left = width / 2 - 145;
        int right = width / 2 + 145;

        for (int i = 0; i < MacroManager.MAX_MACROS; ++i) {
            int y = 70 + i * 28;

            if (inside(mouseX, mouseY, left + 12, y, left + 58, y + 22)) {
                selectedSlot = i;
                status = "Press a key for macro " + (i + 1) + ".";
                commandFields[i].setFocused(false);
                return;
            }

            if (inside(mouseX, mouseY, right - 52, y, right - 12, y + 22)) {
                MacroManager.clear(i);
                commandFields[i].setText("");
                if (selectedSlot == i) selectedSlot = -1;
                status = "Cleared macro " + (i + 1) + ".";
                return;
            }

            commandFields[i].mouseClicked(mouseX, mouseY, mouseButton);
        }

        if (inside(mouseX, mouseY, left + 12, height - 42, right - 12, height - 20)) {
            saveCommands();
            mc.displayGuiScreen(parent == null ? new ClickGuiScreen() : parent);
            return;
        }

        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    private void saveCommands() {
        for (int i = 0; i < MacroManager.MAX_MACROS; ++i) {
            MacroManager.setCommand(i, commandFields[i].getText());
        }
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (selectedSlot >= 0) {
            if (keyCode == KeyboardConstants.KEY_ESCAPE) {
                selectedSlot = -1;
                status = "Key selection cancelled.";
                return;
            }

            MacroManager.setKey(selectedSlot, keyCode);
            selectedSlot = -1;
            status = "Key set. Enter a chat command in the field.";
            return;
        }

        for (GuiTextField field : commandFields) {
            if (field.isFocused()) {
                field.textboxKeyTyped(typedChar, keyCode);
                return;
            }
        }

        if (keyCode == KeyboardConstants.KEY_ESCAPE) {
            saveCommands();
            mc.displayGuiScreen(parent == null ? new ClickGuiScreen() : parent);
            return;
        }

        super.keyTyped(typedChar, keyCode);
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    private boolean inside(int mx, int my, int x1, int y1, int x2, int y2) {
        return mx >= x1 && mx <= x2 && my >= y1 && my <= y2;
    }
}
