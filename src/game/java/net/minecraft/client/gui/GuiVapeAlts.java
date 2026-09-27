package net.minecraft.client.gui;

import java.io.IOException;

import net.lax1dude.eaglercraft.KeyboardConstants;
import net.minecraft.client.Minecraft;

public class GuiVapeAlts extends GuiScreen {
    private final GuiScreen parent;
    private final String[] names = new String[] {"", "", "", "", ""};
    private GuiTextField[] fields;

    public GuiVapeAlts(GuiScreen parent) {
        this.parent = parent;
    }

    @Override
    public void initGui() {
        fields = new GuiTextField[5];
        int left = this.width / 2 - 110;
        for (int i = 0; i < 5; i++) {
            fields[i] = new GuiTextField(20 + i, this.fontRendererObj, left, 62 + i * 35, 220, 22);
            fields[i].setMaxStringLength(16);
            fields[i].setText(names[i]);
        }
        this.buttonList.clear();
        this.buttonList.add(new GuiButton(10, this.width / 2 - 110, 242, 106, 22, "USE SELECTED"));
        this.buttonList.add(new GuiButton(11, this.width / 2 + 4, 242, 106, 22, "CLEAR"));
        this.buttonList.add(new GuiButton(12, this.width / 2 - 110, 271, 220, 22, "BACK"));
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if (button.id == 10) {
            int selected = getFocusedSlot();
            if (selected >= 0) {
                names[selected] = fields[selected].getText().trim();
                if (!names[selected].isEmpty()) {
                    Minecraft.getMinecraft().getSession().setUsername(names[selected]);
                }
            }
        } else if (button.id == 11) {
            int selected = getFocusedSlot();
            if (selected >= 0) {
                fields[selected].setText("");
                names[selected] = "";
            }
        } else if (button.id == 12) {
            saveFields();
            Minecraft.getMinecraft().displayGuiScreen(parent);
        }
    }

    private int getFocusedSlot() {
        if (fields == null) return -1;
        for (int i = 0; i < fields.length; i++) {
            if (fields[i].isFocused()) return i;
        }
        return -1;
    }

    private void saveFields() {
        if (fields == null) return;
        for (int i = 0; i < fields.length; i++) names[i] = fields[i].getText().trim();
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        for (GuiTextField field : fields) field.mouseClicked(mouseX, mouseY, mouseButton);
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        for (GuiTextField field : fields) {
            field.textboxKeyTyped(typedChar, keyCode);
        }
        if (keyCode == KeyboardConstants.KEY_ESCAPE) {
            saveFields();
            Minecraft.getMinecraft().displayGuiScreen(parent);
            return;
        }
        super.keyTyped(typedChar, keyCode);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();

        int left = this.width / 2 - 145;
        int right = this.width / 2 + 145;
        drawRect(left, 18, right, 305, 0xEE101214);
        drawRect(left, 18, right, 20, 0xFF43E06D);

        this.drawCenteredString(this.fontRendererObj, "VAPE V4 ALTS", this.width / 2, 28, 0xFFFFFFFF);
        this.drawCenteredString(this.fontRendererObj, "Local offline account names", this.width / 2, 42, 0xFF858A8F);

        for (int i = 0; i < fields.length; i++) {
            this.fontRendererObj.drawString("ALT " + (i + 1), this.width / 2 - 138, 69 + i * 35, 0xFF43E06D);
            fields[i].drawTextBox();
        }

        this.drawCenteredString(this.fontRendererObj,
                "No passwords or authentication tokens are stored.",
                this.width / 2, 290, 0xFF666B70);

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean doesGuiPauseGame() {
        return true;
    }
}
