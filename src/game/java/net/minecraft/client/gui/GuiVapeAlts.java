package net.minecraft.client.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.lax1dude.eaglercraft.KeyboardConstants;
import net.minecraft.client.Minecraft;

public class GuiVapeAlts extends GuiScreen {
    private final GuiScreen parent;
    private final List<String> names = new ArrayList<String>();
    private final Random random = new Random();
    private GuiTextField field;
    private int page = 0;
    private static final int VISIBLE = 7;
    private int selected = -1;
    private String status = "Add as many local offline names as you want.";

    private static final String[] A = {
        "Shadow", "Frost", "Nova", "Pixel", "Ghost", "Vortex", "Solar", "Lunar",
        "Zero", "Wrath", "Turbo", "Echo", "Byte", "Flux", "Blaze"
    };
    private static final String[] B = {
        "Fox", "Wolf", "Byte", "Rush", "Core", "Wing", "Star", "Void",
        "Spark", "Wave", "Drift", "Dash", "Craft", "Cloud", "Pulse"
    };

    public GuiVapeAlts(GuiScreen parent) {
        this.parent = parent;
        names.add("");
    }

    @Override
    public void initGui() {
        buildField();
    }

    private void buildField() {
        field = new GuiTextField(20, fontRendererObj, width / 2 - 65, 70, 210, 22);
        field.setMaxStringLength(16);
        if (selected >= 0 && selected < names.size()) {
            field.setText(names.get(selected));
        }
    }

    private void syncField() {
        if (selected >= 0 && selected < names.size() && field != null) {
            names.set(selected, field.getText().trim());
        }
    }

    private void select(int index) {
        syncField();
        selected = index;
        buildField();
    }

    private void addSlot() {
        syncField();
        names.add("");
        selected = names.size() - 1;
        page = selected / VISIBLE;
        buildField();
        status = "Added ALT " + (selected + 1) + ".";
    }

    private String randomName() {
        return A[random.nextInt(A.length)] + B[random.nextInt(B.length)] + (100 + random.nextInt(900));
    }

    private void randomizeSelected() {
        if (selected < 0 || selected >= names.size()) addSlot();
        names.set(selected, randomName());
        buildField();
        status = "Generated a random local offline name.";
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if (button.id == 10) {
            syncField();
            if (selected >= 0 && selected < names.size() && !names.get(selected).isEmpty()) {
                Minecraft.getMinecraft().getSession().setUsername(names.get(selected));
                status = "Using ALT " + (selected + 1) + " locally.";
            }
        } else if (button.id == 11) {
            syncField();
            if (selected >= 0 && selected < names.size()) {
                names.set(selected, "");
                buildField();
                status = "Cleared ALT " + (selected + 1) + ".";
            }
        } else if (button.id == 12) {
            randomizeSelected();
        } else if (button.id == 13) {
            addSlot();
        } else if (button.id == 14) {
            syncField();
            if (page > 0) {
                page--;
                selected = page * VISIBLE;
                buildField();
            }
        } else if (button.id == 15) {
            syncField();
            if ((page + 1) * VISIBLE < names.size()) {
                page++;
                selected = page * VISIBLE;
                buildField();
            }
        } else if (button.id == 16) {
            syncField();
            Minecraft.getMinecraft().displayGuiScreen(parent);
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        if (mouseButton != 0) {
            super.mouseClicked(mouseX, mouseY, mouseButton);
            return;
        }

        int left = width / 2 - 145;
        int right = width / 2 + 145;

        if (mouseY >= 68 && mouseY <= 94) {
            field.mouseClicked(mouseX, mouseY, mouseButton);
        }

        for (int i = 0; i < VISIBLE; ++i) {
            int index = page * VISIBLE + i;
            int y = 105 + i * 24;
            if (index >= names.size()) break;
            if (inside(mouseX, mouseY, left + 12, y, right - 12, y + 20)) {
                select(index);
                return;
            }
        }

        int by = height - 42;
        if (inside(mouseX, mouseY, left + 12, by, left + 60, by + 22)) {
            syncField();
            if (selected >= 0 && selected < names.size() && !names.get(selected).isEmpty()) {
                Minecraft.getMinecraft().getSession().setUsername(names.get(selected));
                status = "Using ALT " + (selected + 1) + " locally.";
            }
            return;
        }
        if (inside(mouseX, mouseY, left + 66, by, left + 122, by + 22)) {
            syncField();
            if (selected >= 0 && selected < names.size()) {
                names.set(selected, "");
                buildField();
                status = "Cleared ALT " + (selected + 1) + ".";
            }
            return;
        }
        if (inside(mouseX, mouseY, left + 128, by, left + 190, by + 22)) {
            randomizeSelected();
            return;
        }
        if (inside(mouseX, mouseY, left + 196, by, right - 80, by + 22)) {
            addSlot();
            return;
        }
        if (inside(mouseX, mouseY, right - 74, by, right - 12, by + 22)) {
            syncField();
            Minecraft.getMinecraft().displayGuiScreen(parent);
            return;
        }

        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (field != null && field.isFocused()) {
            field.textboxKeyTyped(typedChar, keyCode);
            return;
        }
        if (keyCode == KeyboardConstants.KEY_ESCAPE) {
            syncField();
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
        drawRect(left, 18, right, Math.min(height - 12, 350), 0xEE101214);
        drawRect(left, 18, right, 20, 0xFF43E06D);

        drawCenteredString(fontRendererObj, "VAPE V4 ALTS", width / 2, 28, 0xFFFFFFFF);
        drawCenteredString(fontRendererObj, "Unlimited local offline slots", width / 2, 42, 0xFF858A8F);

        fontRendererObj.drawString("SELECTED ALT", left + 12, 58, 0xFF43E06D);
        if (field != null) field.drawTextBox();

        for (int i = 0; i < VISIBLE; ++i) {
            int index = page * VISIBLE + i;
            if (index >= names.size()) break;
            int y = 105 + i * 24;
            boolean active = index == selected;
            drawRect(left + 12, y, right - 12, y + 20, active ? 0xFF43E06D : 0xFF252629);
            String value = names.get(index).isEmpty() ? "(empty)" : names.get(index);
            fontRendererObj.drawString("ALT " + (index + 1), left + 18, y + 6,
                    active ? 0xFF101311 : 0xFF43E06D);
            fontRendererObj.drawString(value, left + 70, y + 6,
                    active ? 0xFF101311 : 0xFFD0D0D0);
        }

        drawCenteredString(fontRendererObj, status, width / 2, Math.min(height - 62, 285), 0xFF777C80);
        button(left + 12, height - 42, left + 60, height - 20, "USE", mouseX, mouseY);
        button(left + 66, height - 42, left + 122, height - 20, "CLEAR", mouseX, mouseY);
        button(left + 128, height - 42, left + 190, height - 20, "RANDOM", mouseX, mouseY);
        button(left + 196, height - 42, right - 80, height - 20, "ADD", mouseX, mouseY);
        button(right - 74, height - 42, right - 12, height - 20, "BACK", mouseX, mouseY);

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    private void button(int x1, int y1, int x2, int y2, String label, int mouseX, int mouseY) {
        boolean hover = mouseX >= x1 && mouseX <= x2 && mouseY >= y1 && mouseY <= y2;
        drawRect(x1, y1, x2, y2, hover ? 0xFF303033 : 0xFF232124);
        int w = fontRendererObj.getStringWidth(label);
        fontRendererObj.drawString(label, x1 + (x2 - x1 - w) / 2, y1 + 7, 0xFFD0D0D0);
    }

    private boolean inside(int mx, int my, int x1, int y1, int x2, int y2) {
        return mx >= x1 && mx <= x2 && my >= y1 && my <= y2;
    }

    @Override
    public boolean doesGuiPauseGame() {
        return true;
    }
}
