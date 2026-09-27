package net.minecraft.client.gui;

import com.zero7wrath.clientbase.Category;
import com.zero7wrath.clientbase.Client;
import com.zero7wrath.clientbase.config.ConfigScreen;
import com.zero7wrath.clientbase.modules.Module;
import com.zero7wrath.clientbase.settings.Setting;
import net.lax1dude.eaglercraft.KeyboardConstants;

import java.io.IOException;

public class ClickGuiScreen extends GuiScreen {
    private static final int SIDEBAR_W = 112;
    private static final int PANEL_W = 292;
    private static final int ROW_H = 25;
    private static final int BG = 0xF9161719;
    private static final int PANEL = 0xF91C1D1F;
    private static final int ROW = 0xF9222325;
    private static final int HOVER = 0xF92B2D2F;
    private static final int GREEN = 0xFF43E06D;
    private static final int GREEN_DARK = 0xFF1F6E39;
    private static final int TEXT = 0xFFE0E0E0;
    private static final int DIM = 0xFF8B8D90;
    private static final int LINE = 0xFF303235;

    private final Category[] categories = new Category[] {
        Category.Combat, Category.Render, Category.Utility, Category.World,
        Category.Player, Category.Movement, Category.Legit, Category.Blatant, Category.Client
    };

    private int selected;
    private GuiTextField search;
    private boolean minimized;

    private int sx() {
        return Math.max(8, width / 2 - 202);
    }

    private int sy() {
        return Math.max(8, height / 2 - 145);
    }

    private boolean inside(int mx, int my, int x1, int y1, int x2, int y2) {
        return mx >= x1 && mx <= x2 && my >= y1 && my <= y2;
    }

    @Override
    public void initGui() {
        search = new GuiTextField(21, fontRendererObj, sx() + SIDEBAR_W + 12, sy() + 38, PANEL_W - 24, 18);
        search.setMaxStringLength(64);
        search.setFocused(false);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        int x = sx();
        int y = sy();
        int h = minimized ? 32 : 292;
        int panelX = x + SIDEBAR_W + 7;

        drawRect(x + 3, y + 2, x + SIDEBAR_W + (minimized ? 0 : PANEL_W) + 8, y + h + 2, 0x66000000);
        drawRect(x, y, x + SIDEBAR_W, y + h, BG);

        drawRect(x, y, x + SIDEBAR_W, y + 32, 0xFF111214);
        fontRendererObj.drawString("VAPE", x + 12, y + 11, 0xFFFFFFFF);
        fontRendererObj.drawString("V4", x + 45, y + 11, GREEN);
        fontRendererObj.drawString(minimized ? "+" : "-", x + SIDEBAR_W - 18, y + 10, DIM);

        if (!minimized) {
            drawRect(panelX, y, panelX + PANEL_W, y + h, PANEL);
            drawRect(panelX, y, panelX + PANEL_W, y + 32, 0xFF17181A);
            fontRendererObj.drawString(categoryLabel(categories[selected]), panelX + 12, y + 11, TEXT);
            fontRendererObj.drawString("x", panelX + PANEL_W - 17, y + 10, DIM);

            drawRect(panelX + 9, y + 32, panelX + PANEL_W - 9, y + 33, LINE);
            search.drawTextBox();
            if (search.getText().length() == 0) {
                fontRendererObj.drawString("Search modules...", panelX + 18, y + 44, DIM);
            }

            int rowY = y + 61;
            String query = search.getText().trim().toLowerCase();

            for (Module module : Client.manager.getModulesByCategory(categories[selected])) {
                if (query.length() > 0 && !module.getName().toLowerCase().contains(query)) continue;

                boolean hover = inside(mouseX, mouseY, panelX + 8, rowY, panelX + PANEL_W - 8, rowY + ROW_H);
                boolean enabled = module.isEnabled();

                drawRect(panelX + 8, rowY, panelX + PANEL_W - 8, rowY + ROW_H,
                        enabled ? GREEN : (hover ? HOVER : ROW));

                fontRendererObj.drawString(module.getName(), panelX + 16, rowY + 8,
                        enabled ? 0xFF101311 : TEXT);

                if (!module.getSettings().isEmpty()) {
                    fontRendererObj.drawString(module.open ? "-" : "+",
                            panelX + PANEL_W - 22, rowY + 8, enabled ? 0xFF101311 : DIM);
                }

                rowY += ROW_H;

                if (module.open) {
                    for (Setting<?> setting : module.getSettings()) {
                        drawSetting(panelX, rowY, setting, mouseX, mouseY);
                        rowY += ROW_H;
                    }
                }

                if (rowY > y + h - 8) break;
            }
        }

        int cy = y + 41;
        for (int i = 0; i < categories.length; ++i) {
            boolean active = i == selected;
            boolean hover = inside(mouseX, mouseY, x + 6, cy - 3, x + SIDEBAR_W - 6, cy + 19);
            if (active) drawRect(x + 6, cy - 3, x + SIDEBAR_W - 6, cy + 19, 0xFF24272A);
            else if (hover) drawRect(x + 6, cy - 3, x + SIDEBAR_W - 6, cy + 19, 0xFF1E2022);

            fontRendererObj.drawString(categoryLabel(categories[i]), x + 14, cy + 4,
                    active ? 0xFFFFFFFF : TEXT);
            cy += 25;
        }

        drawRect(x + 9, y + 268, x + SIDEBAR_W - 9, y + 269, LINE);
        fontRendererObj.drawString("MISC", x + 12, y + 278, DIM);

        if (inside(mouseX, mouseY, x + 6, y + 284, x + SIDEBAR_W - 6, y + 306))
            drawRect(x + 6, y + 284, x + SIDEBAR_W - 6, y + 306, HOVER);
        fontRendererObj.drawString("Profiles", x + 14, y + 291, TEXT);

        if (inside(mouseX, mouseY, x + 6, y + 307, x + SIDEBAR_W - 6, y + 329))
            drawRect(x + 6, y + 307, x + SIDEBAR_W - 6, y + 329, HOVER);
        fontRendererObj.drawString("Macros", x + 14, y + 314, TEXT);

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    private void drawSetting(int panelX, int rowY, Setting<?> setting, int mouseX, int mouseY) {
        boolean hover = inside(mouseX, mouseY, panelX + 8, rowY, panelX + PANEL_W - 8, rowY + ROW_H);
        drawRect(panelX + 8, rowY, panelX + PANEL_W - 8, rowY + ROW_H, hover ? HOVER : 0xFF191A1C);
        fontRendererObj.drawString(setting.getName(), panelX + 16, rowY + 8, TEXT);

        if (setting instanceof Setting.BooleanSetting) {
            boolean value = ((Setting.BooleanSetting) setting).getValue();
            fontRendererObj.drawString(value ? "ON" : "OFF", panelX + PANEL_W - 42, rowY + 8,
                    value ? GREEN : DIM);
        } else if (setting instanceof Setting.ModeSetting) {
            String value = String.valueOf(setting.getValue());
            fontRendererObj.drawString(value, panelX + PANEL_W - fontRendererObj.getStringWidth(value) - 14,
                    rowY + 8, TEXT);
        } else if (setting instanceof Setting.NumberSetting) {
            Setting.NumberSetting n = (Setting.NumberSetting) setting;
            String value = format(n.getValue());
            fontRendererObj.drawString(value, panelX + PANEL_W - fontRendererObj.getStringWidth(value) - 14,
                    rowY + 4, TEXT);
            int bx = panelX + 16;
            int by = rowY + 19;
            int bw = PANEL_W - 32;
            double pct = (n.getValue() - n.getMin()) / (n.getMax() - n.getMin());
            pct = Math.max(0.0, Math.min(1.0, pct));
            drawRect(bx, by, bx + bw, by + 2, 0xFF45474A);
            drawRect(bx, by, bx + (int)(bw * pct), by + 2, GREEN);
        }
    }

    private String format(double value) {
        if (value == (long)value) return String.valueOf((long)value);
        String s = String.valueOf(value);
        return s.length() > 7 ? s.substring(0, 7) : s;
    }

    private String categoryLabel(Category category) {
        return category.name();
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        int x = sx();
        int y = sy();
        int panelX = x + SIDEBAR_W + 7;

        if (mouseButton == 0 && inside(mouseX, mouseY, x, y, x + SIDEBAR_W, y + 32)) {
            minimized = !minimized;
            return;
        }

        if (!minimized && mouseButton == 0 && inside(mouseX, mouseY, panelX + PANEL_W - 30, y, panelX + PANEL_W, y + 32)) {
            mc.displayGuiScreen(null);
            return;
        }

        if (!minimized && mouseButton == 0 && inside(mouseX, mouseY, panelX + 8, y + 32, panelX + PANEL_W - 8, y + 58)) {
            search.mouseClicked(mouseX, mouseY, mouseButton);
            return;
        }

        if (mouseButton == 0 && inside(mouseX, mouseY, x + 6, y + 284, x + SIDEBAR_W - 6, y + 306)) {
            mc.displayGuiScreen(new ConfigScreen());
            return;
        }

        if (mouseButton == 0 && inside(mouseX, mouseY, x + 6, y + 307, x + SIDEBAR_W - 6, y + 329)) {
            mc.displayGuiScreen(new MacroScreen(this));
            return;
        }

        int cy = y + 41;
        for (int i = 0; i < categories.length; ++i) {
            if (mouseButton == 0 && inside(mouseX, mouseY, x + 6, cy - 3, x + SIDEBAR_W - 6, cy + 19)) {
                selected = i;
                return;
            }
            cy += 25;
        }

        if (!minimized) {
            int rowY = y + 61;
            String query = search.getText().trim().toLowerCase();

            for (Module module : Client.manager.getModulesByCategory(categories[selected])) {
                if (query.length() > 0 && !module.getName().toLowerCase().contains(query)) continue;

                if (inside(mouseX, mouseY, panelX + 8, rowY, panelX + PANEL_W - 8, rowY + ROW_H)) {
                    if (mouseButton == 0) {
                        if (!module.getSettings().isEmpty() && mouseX > panelX + PANEL_W - 45)
                            module.open = !module.open;
                        else
                            module.toggle();
                    } else if (mouseButton == 1 && !module.getSettings().isEmpty()) {
                        module.open = !module.open;
                    }
                    return;
                }

                rowY += ROW_H;
                if (module.open) {
                    for (Setting<?> setting : module.getSettings()) {
                        if (inside(mouseX, mouseY, panelX + 8, rowY, panelX + PANEL_W - 8, rowY + ROW_H)) {
                            clickSetting(setting);
                            return;
                        }
                        rowY += ROW_H;
                    }
                }
            }
        }

        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    private void clickSetting(Setting<?> setting) {
        if (setting instanceof Setting.BooleanSetting) {
            ((Setting.BooleanSetting) setting).toggle();
        } else if (setting instanceof Setting.ModeSetting) {
            ((Setting.ModeSetting) setting).cycle();
        }
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (search != null && search.isFocused()) {
            if (keyCode == KeyboardConstants.KEY_ESCAPE) {
                search.setFocused(false);
                return;
            }
            search.textboxKeyTyped(typedChar, keyCode);
            return;
        }

        if (keyCode == KeyboardConstants.KEY_ESCAPE) {
            mc.displayGuiScreen(null);
            return;
        }

        super.keyTyped(typedChar, keyCode);
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}
