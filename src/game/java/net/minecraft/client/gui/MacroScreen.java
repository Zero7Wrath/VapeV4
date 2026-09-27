package net.minecraft.client.gui;

import com.zero7wrath.clientbase.macro.MacroManager;
import net.lax1dude.eaglercraft.KeyboardConstants;

import java.io.IOException;

public class MacroScreen extends GuiScreen {
    private final GuiScreen parent;
    private GuiTextField[] moduleFields;
    private int selectedSlot = -1;
    private int page = 0;
    private static final int VISIBLE = 7;
    private String status = "Bind a key, then enter a module name.";

    public MacroScreen(GuiScreen parent) {
        this.parent = parent;
    }

    @Override
    public void initGui() {
        buildFields();
    }

    private void buildFields() {
        moduleFields = new GuiTextField[VISIBLE];
        for (int i = 0; i < VISIBLE; ++i) {
            int slot = page * VISIBLE + i;
            moduleFields[i] = new GuiTextField(20 + i, fontRendererObj,
                    width / 2 - 35, 70 + i * 29, 175, 20);
            moduleFields[i].setMaxStringLength(40);
            if (slot < MacroManager.size()) {
                moduleFields[i].setText(MacroManager.getModule(slot));
            }
        }
    }

    private int slotFor(int row) {
        return page * VISIBLE + row;
    }

    private void saveFields() {
        for (int i = 0; i < VISIBLE; ++i) {
            int slot = slotFor(i);
            if (slot < MacroManager.size()) {
                MacroManager.setModule(slot, moduleFields[i].getText());
            }
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        int left = width / 2 - 145;
        int right = width / 2 + 145;

        drawRect(left, 18, right, height - 12, 0xF51B1A1C);
        drawRect(left + 1, 19, right - 1, 48, 0xFF171618);

        fontRendererObj.drawString("VAPE V4", left + 12, 29, 0xFFFFFFFF);
        fontRendererObj.drawString("MACROS", left + 78, 29, 0xFF43E06D);
        fontRendererObj.drawString(status, left + 12, 54, 0xFF8C8C8C);

        for (int i = 0; i < VISIBLE; ++i) {
            int slot = slotFor(i);
            int y = 70 + i * 29;
            String key = slot < MacroManager.size() && MacroManager.getKey(slot) >= 0
                    ? "KEY " + MacroManager.getKey(slot) : "NONE";

            drawRect(left + 12, y, left + 58, y + 22,
                    selectedSlot == slot ? 0xFF43E06D : 0xFF252629);
            fontRendererObj.drawString(key, left + 16, y + 7,
                    selectedSlot == slot ? 0xFF101311 : 0xFFD0D0D0);

            if (slot < MacroManager.size()) {
                moduleFields[i].drawTextBox();
                drawRect(right - 52, y, right - 12, y + 22, 0xFF252629);
                fontRendererObj.drawString("CLR", right - 43, y + 7, 0xFFD0D0D0);
            }
        }

        button(left + 12, height - 42, left + 78, height - 20, "ADD", mouseX, mouseY);
        button(left + 84, height - 42, left + 145, height - 20, "PREV", mouseX, mouseY);
        button(left + 151, height - 42, left + 212, height - 20, "NEXT", mouseX, mouseY);
        button(left + 218, height - 42, right - 12, height - 20, "BACK", mouseX, mouseY);

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

        for (int i = 0; i < VISIBLE; ++i) {
            int slot = slotFor(i);
            int y = 70 + i * 29;
            if (slot >= MacroManager.size()) continue;

            if (inside(mouseX, mouseY, left + 12, y, left + 58, y + 22)) {
                selectedSlot = slot;
                status = "Press a key for macro " + (slot + 1) + ".";
                moduleFields[i].setFocused(false);
                return;
            }

            if (inside(mouseX, mouseY, right - 52, y, right - 12, y + 22)) {
                MacroManager.clear(slot);
                moduleFields[i].setText("");
                if (selectedSlot == slot) selectedSlot = -1;
                status = "Cleared macro " + (slot + 1) + ".";
                return;
            }

            moduleFields[i].mouseClicked(mouseX, mouseY, mouseButton);
        }

        if (inside(mouseX, mouseY, left + 12, height - 42, left + 78, height - 20)) {
            saveFields();
            MacroManager.add();
            status = "Added a new macro slot.";
            buildFields();
            return;
        }

        if (inside(mouseX, mouseY, left + 84, height - 42, left + 145, height - 20)) {
            saveFields();
            if (page > 0) --page;
            buildFields();
            return;
        }

        if (inside(mouseX, mouseY, left + 151, height - 42, left + 212, height - 20)) {
            saveFields();
            if ((page + 1) * VISIBLE < MacroManager.size()) ++page;
            buildFields();
            return;
        }

        if (inside(mouseX, mouseY, left + 218, height - 42, right - 12, height - 20)) {
            saveFields();
            mc.displayGuiScreen(parent == null ? new ClickGuiScreen() : parent);
            return;
        }

        super.mouseClicked(mouseX, mouseY, mouseButton);
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
            status = "Key set. Type a module name, not a chat command.";
            return;
        }

        for (GuiTextField field : moduleFields) {
            if (field.isFocused()) {
                field.textboxKeyTyped(typedChar, keyCode);
                return;
            }
        }

        if (keyCode == KeyboardConstants.KEY_ESCAPE) {
            saveFields();
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
