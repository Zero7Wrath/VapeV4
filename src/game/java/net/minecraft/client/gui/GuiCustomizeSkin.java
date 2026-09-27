package net.minecraft.client.gui;

import java.io.IOException;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.player.EnumPlayerModelParts;

public class GuiCustomizeSkin extends GuiScreen {
	private final GuiScreen parentScreen;
	private String title;
	private GuiButton enableFNAWSkinsButton;

	public GuiCustomizeSkin(GuiScreen parentScreenIn) {
		this.parentScreen = parentScreenIn;
	}

	public void initGui() {
		this.title = "SKIN CUSTOMIZATION";

		int i = 0;
		for (EnumPlayerModelParts part : EnumPlayerModelParts.values()) {
			this.buttonList.add(new ButtonPart(part.getPartId(),
					this.width / 2 - 155 + i % 2 * 160,
					this.height / 6 + 24 * (i >> 1), 150, 20, part));
			++i;
		}

		this.buttonList.add(new GuiOptionButton(199, this.width / 2 - 155 + i % 2 * 160,
				this.height / 6 + 24 * (i >> 1), GameSettings.Options.MAIN_HAND,
				this.mc.gameSettings.getKeyBinding(GameSettings.Options.MAIN_HAND)));
		++i;
		if (i % 2 == 1) ++i;

		int y = this.height / 6 + 12 + 24 * (i >> 1);
		this.buttonList.add(enableFNAWSkinsButton = new GuiButton(201, this.width / 2 - 100, y, 200, 20,
				"FNAW SKINS: " + (this.mc.gameSettings.enableFNAWSkins ? "ON" : "OFF")));
		this.buttonList.add(new GuiButton(200, this.width / 2 - 100, y + 27, 200, 20, "DONE"));
	}

	protected void actionPerformed(GuiButton button) throws IOException {
		if (!button.enabled) return;

		if (button.id == 200) {
			this.mc.gameSettings.saveOptions();
			this.mc.displayGuiScreen(this.parentScreen);
		} else if (button.id == 199) {
			this.mc.gameSettings.setOptionValue(GameSettings.Options.MAIN_HAND, 1);
			button.displayString = this.mc.gameSettings.getKeyBinding(GameSettings.Options.MAIN_HAND);
			this.mc.gameSettings.sendSettingsToServer();
		} else if (button.id == 201) {
			this.mc.gameSettings.enableFNAWSkins = !this.mc.gameSettings.enableFNAWSkins;
			this.mc.getRenderManager().setEnableFNAWSkins(this.mc.getEnableFNAWSkins());
			enableFNAWSkinsButton.displayString = "FNAW SKINS: " +
					(this.mc.gameSettings.enableFNAWSkins ? "ON" : "OFF");
		} else if (button instanceof ButtonPart) {
			EnumPlayerModelParts part = ((ButtonPart) button).playerModelParts;
			this.mc.gameSettings.switchModelPartEnabled(part);
			button.displayString = getMessage(part);
		}
	}

	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		drawDefaultBackground();
		int cardW = 360;
		int left = this.width / 2 - cardW / 2;
		drawRect(left, 18, left + cardW, this.height - 18, 0xE8141618);
		drawRect(left, 18, left + cardW, 20, 0xFF43E06D);
		drawCenteredString(this.fontRendererObj, this.title, this.width / 2, 27, 0xFFFFFFFF);
		drawCenteredString(this.fontRendererObj, "Toggle the model parts you want to show.", this.width / 2, 40, 0xFF777B80);
		super.drawScreen(mouseX, mouseY, partialTicks);
	}

	private String getMessage(EnumPlayerModelParts part) {
		String state = this.mc.gameSettings.getModelParts().contains(part) ? "ON" : "OFF";
		return part.getName().getFormattedText() + ": " + state;
	}

	class ButtonPart extends GuiButton {
		private final EnumPlayerModelParts playerModelParts;

		private ButtonPart(int id, int x, int y, int w, int h, EnumPlayerModelParts part) {
			super(id, x, y, w, h, GuiCustomizeSkin.this.getMessage(part));
			this.playerModelParts = part;
		}
	}
}
