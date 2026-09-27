package net.minecraft.client.gui;

import java.io.IOException;

public class GuiVapeRealms extends GuiScreen {
    private final GuiScreen parent;

    public GuiVapeRealms(GuiScreen parent) {
        this.parent = parent;
    }

    @Override
    public void initGui() {
        this.buttonList.clear();
        this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 2 + 45, 200, 20, "MULTIPLAYER"));
        this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 2 + 70, 200, 20, "BACK"));
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if (button.id == 0) {
            this.mc.displayGuiScreen(new GuiMultiplayer(this));
        } else if (button.id == 1) {
            this.mc.displayGuiScreen(this.parent);
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, "VAPE V4 REALMS", this.width / 2, this.height / 2 - 45, 0xFFFFFFFF);
        this.drawCenteredString(this.fontRendererObj, "REALMS", this.width / 2, this.height / 2 - 25, 0xFF55FF55);
        this.drawCenteredString(this.fontRendererObj, "Official Mojang Realms is not available in this Eaglercraft port.", this.width / 2, this.height / 2 - 5, 0xFFAAAAAA);
        this.drawCenteredString(this.fontRendererObj, "Use Multiplayer to connect to supported Eaglercraft servers.", this.width / 2, this.height / 2 + 10, 0xFF888888);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean doesGuiPauseGame() {
        return true;
    }
}
