package net.minecraft.client.gui;

import com.zero7wrath.clientbase.Category;
import com.zero7wrath.clientbase.Client;
import com.zero7wrath.clientbase.config.ConfigScreen;
import com.zero7wrath.clientbase.modules.Module;
import com.zero7wrath.clientbase.settings.Setting;
import net.lax1dude.eaglercraft.KeyboardConstants;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ClickGuiScreen extends GuiScreen {
    private static final int SIDEBAR_W = 156;
    private static final int PANEL_W = 286;
    private static final int ROW_H = 29;
    private static final int BG = 0xF7131214;
    private static final int PANEL = 0xF91A191B;
    private static final int ROW = 0xFF1D1C1E;
    private static final int HOVER = 0xFF272528;
    private static final int ACTIVE = 0xFFE6E6E6;
    private static final int TEXT = 0xFFC8C8C8;
    private static final int DIM = 0xFF777477;
    private static final int LINE = 0xFF2E2C2F;

    private final Category[] categories = new Category[] {
        Category.Combat, Category.Render, Category.Utility, Category.World,
        Category.Player, Category.Movement, Category.Legit, Category.Blatant
    };

    private int selected = 0;
    private GuiTextField search;
    private String tooltip;
    private int tooltipX;
    private int tooltipY;

    public ClickGuiScreen() {
    }

    @Override
    public void initGui() {
        search = new GuiTextField(10, fontRendererObj, 182 + 12, 49, PANEL_W - 24, 20);
        search.setMaxStringLength(64);
        search.setFocused(false);
    }

    private int sx() {
        return Math.max(12, width / 2 - 225);
    }

    private int sy() {
        return Math.max(12, height / 2 - 190);
    }

    private void smallCaps(String text, int x, int y, int color) {
        fontRendererObj.drawSmallCapsString(text, x, y, color);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawRect(0, 0, width, height, 0xFF101012);
        tooltip = null;

        int x = sx();
        int y = sy();
        int panelX = x + SIDEBAR_W + 10;
        int panelY = y;
        int panelH = Math.min(height - y - 12, 360);

        drawRoundedPanel(x, y, x + SIDEBAR_W, y + panelH, PANEL);
        drawRoundedPanel(panelX, panelY, panelX + PANEL_W, panelY + panelH, PANEL);

        smallCaps("VAPE", x + 14, y + 15, 0xFFFFFFFF);
        smallCaps("V4", x + 51, y + 15, 0xFF88858A);
        fontRendererObj.drawString("⚙", x + SIDEBAR_W - 21, y + 12, DIM);

        int cy = y + 47;
        for (int i = 0; i < categories.length; i++) {
            boolean active = i == selected;
            boolean hover = inside(mouseX, mouseY, x + 7, cy - 4, x + SIDEBAR_W - 7, cy + 22);

            if (active) drawRect(x + 7, cy - 4, x + SIDEBAR_W - 7, cy + 22, 0xFF29272A);
            else if (hover) drawRect(x + 7, cy - 4, x + SIDEBAR_W - 7, cy + 22, HOVER);

            smallCaps(categoryLabel(categories[i]), x + 18, cy + 4, active ? 0xFFFFFFFF : TEXT);
            fontRendererObj.drawString("›", x + SIDEBAR_W - 19, cy + 4, active ? TEXT : DIM);
            cy += 29;
        }

        drawRect(x + 12, y + 286, x + SIDEBAR_W - 12, y + 287, LINE);
        smallCaps("MISC", x + 14, y + 299, DIM);

        miscRow(x, y + 313, "Friends", mouseX, mouseY);
        miscRow(x, y + 338, "Profiles", mouseX, mouseY);

        smallCaps(categoryLabel(categories[selected]), panelX + 14, panelY + 14, 0xFFE0E0E0);
        fontRendererObj.drawString("...", panelX + PANEL_W - 25, panelY + 12, DIM);

        drawRect(panelX + 10, panelY + 37, panelX + PANEL_W - 10, panelY + 38, LINE);
        search.drawTextBox();
        if (search.getText().length() == 0) {
            smallCaps("search", panelX + 18, panelY + 55, DIM);
        }

        int rowY = panelY + 69;
        String query = search.getText().trim().toLowerCase();

        for (Module module : Client.manager.getModulesByCategory(categories[selected])) {
            if (query.length() > 0 && !module.getName().toLowerCase().contains(query)) continue;

            boolean hover = inside(mouseX, mouseY, panelX + 7, rowY, panelX + PANEL_W - 7, rowY + ROW_H);
            boolean enabled = module.isEnabled();

            drawRect(panelX + 7, rowY, panelX + PANEL_W - 7, rowY + ROW_H,
                    enabled ? ACTIVE : (hover ? HOVER : ROW));

            smallCaps(module.getName(), panelX + 16, rowY + 9, enabled ? 0xFF151416 : TEXT);

            if (!module.getSettings().isEmpty()) {
                fontRendererObj.drawString("⋮", panelX + PANEL_W - 25, rowY + 8,
                        module.open ? 0xFFFFFFFF : DIM);
            }

            if (hover && module.getDescription() != null && !"-".equals(module.getDescription())) {
                tooltip = module.getDescription();
                tooltipX = mouseX;
                tooltipY = mouseY;
            }

            rowY += ROW_H;

            if (module.open) {
                for (Setting<?> setting : module.getSettings()) {
                    drawSetting(panelX, rowY, setting, mouseX, mouseY);
                    rowY += ROW_H;
                }
            }

            if (rowY > panelY + panelH - 25) break;
        }

        if (tooltip != null) {
            int tw = fontRendererObj.getStringWidth(tooltip) + 12;
            int tx = Math.min(tooltipX + 8, width - tw - 4);
            int ty = Math.min(tooltipY + 8, height - 24);
            drawRect(tx, ty, tx + tw, ty + 18, 0xF0181718);
            fontRendererObj.drawString(tooltip, tx + 6, ty + 5, TEXT);
        }

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    private void drawRoundedPanel(int x1, int y1, int x2, int y2, int color) {
        drawRect(x1 + 4, y1, x2 - 4, y2, color);
        drawRect(x1, y1 + 4, x2, y2 - 4, color);
        drawRect(x1 + 5, y1 + 1, x2 - 5, y1 + 2, LINE);
        drawRect(x1 + 5, y2 - 2, x2 - 5, y2 - 1, LINE);
    }

    private void drawSetting(int panelX, int rowY, Setting<?> setting, int mouseX, int mouseY) {
        boolean hover = inside(mouseX, mouseY, panelX + 7, rowY, panelX + PANEL_W - 7, rowY + ROW_H);
        drawRect(panelX + 7, rowY, panelX + PANEL_W - 7, rowY + ROW_H, hover ? HOVER : 0xFF171618);

        smallCaps(setting.getName(), panelX + 16, rowY + 9, TEXT);

        if (setting instanceof Setting.BooleanSetting) {
            String value = ((Setting.BooleanSetting) setting).getValue() ? "on" : "off";
            smallCaps(value, panelX + PANEL_W - 45, rowY + 9,
                    ((Setting.BooleanSetting) setting).getValue() ? 0xFFFFFFFF : DIM);
        } else if (setting instanceof Setting.ModeSetting) {
            String value = String.valueOf(setting.getValue());
            smallCaps(value, panelX + PANEL_W - fontRendererObj.getStringWidth(value) - 18, rowY + 9, 0xFFE0E0E0);
        } else if (setting instanceof Setting.NumberSetting) {
            Setting.NumberSetting n = (Setting.NumberSetting) setting;
            String value = format(n.getValue());
            smallCaps(value, panelX + PANEL_W - fontRendererObj.getStringWidth(value) - 18, rowY + 5, 0xFFE0E0E0);
            int bx = panelX + 16;
            int by = rowY + 20;
            int bw = PANEL_W - 32;
            double pct = (n.getValue() - n.getMin()) / (n.getMax() - n.getMin());
            pct = Math.max(0.0, Math.min(1.0, pct));
            drawRect(bx, by, bx + bw, by + 2, 0xFF444245);
            drawRect(bx, by, bx + (int)(bw * pct), by + 2, 0xFFFFFFFF);
        }
    }

    private void miscRow(int x, int y, String label, int mouseX, int mouseY) {
        if (inside(mouseX, mouseY, x + 7, y - 4, x + SIDEBAR_W - 7, y + 20))
            drawRect(x + 7, y - 4, x + SIDEBAR_W - 7, y + 20, HOVER);
        smallCaps(label, x + 18, y + 4, TEXT);
        fontRendererObj.drawString("›", x + SIDEBAR_W - 19, y + 4, DIM);
    }

    private String categoryLabel(Category category) {
        return category.name();
    }

    private String format(double value) {
        if (value == (long)value) return String.valueOf((long)value);
        String s = String.valueOf(value);
        return s.length() > 7 ? s.substring(0, 7) : s;
    }

    private boolean inside(int mx, int my, int x1, int y1, int x2, int y2) {
        return mx >= x1 && mx <= x2 && my >= y1 && my <= y2;
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        int x = sx();
        int y = sy();
        int panelX = x + SIDEBAR_W + 10;
        int panelY = y;

        if (inside(mouseX, mouseY, x + 7, y + 309, x + SIDEBAR_W - 7, y + 333) && mouseButton == 0) {
            mc.displayGuiScreen(new ConfigScreen());
            return;
        }

        int cy = y + 47;
        for (int i = 0; i < categories.length; i++) {
            if (inside(mouseX, mouseY, x + 7, cy - 4, x + SIDEBAR_W - 7, cy + 22) && mouseButton == 0) {
                selected = i;
                return;
            }
            cy += 29;
        }

        if (inside(mouseX, mouseY, panelX + 10, panelY + 37, panelX + PANEL_W - 10, panelY + 62)) {
            search.mouseClicked(mouseX, mouseY, mouseButton);
            return;
        }

        int rowY = panelY + 69;
        String query = search.getText().trim().toLowerCase();

        for (Module module : Client.manager.getModulesByCategory(categories[selected])) {
            if (query.length() > 0 && !module.getName().toLowerCase().contains(query)) continue;

            if (inside(mouseX, mouseY, panelX + 7, rowY, panelX + PANEL_W - 7, rowY + ROW_H)) {
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
                    if (inside(mouseX, mouseY, panelX + 7, rowY, panelX + PANEL_W - 7, rowY + ROW_H)) {
                        clickSetting(setting);
                        return;
                    }
                    rowY += ROW_H;
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
    protected void mouseClickMove(int mouseX, int mouseY, int button, long timeSinceLastClick) {
        super.mouseClickMove(mouseX, mouseY, button, timeSinceLastClick);
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
