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
    private static final int SIDEBAR_W = 122;
    private static final int PANEL_W = 454;
    private static final int HEADER_H = 48;
    private static final int CARD_W = 210;
    private static final int CARD_H = 62;
    private static final int CARD_GAP = 8;

    private static final int BG = 0xF90E0F11;
    private static final int PANEL = 0xF917181A;
    private static final int CARD = 0xFF1D1F21;
    private static final int CARD_HOVER = 0xFF26292C;
    private static final int CARD_ON = 0xFF43E06D;
    private static final int TEXT = 0xFFE8E8E8;
    private static final int DIM = 0xFF92969A;
    private static final int LINE = 0xFF303337;

    private final Category[] categories = new Category[] {
            Category.Combat, Category.Blatant, Category.Render, Category.Utility,
            Category.World, Category.Inventory, Category.Legit, Category.Client
    };

    private int selectedCategory;
    private int scroll;
    private boolean minimized;
    private GuiTextField search;
    private Module expanded;
    private boolean dragging;
    private int dragOffsetX;
    private int dragOffsetY;
    private int guiX = -1;
    private int guiY = -1;

    private int left() {
        if (guiX >= 0) return Math.max(4, Math.min(width - SIDEBAR_W - PANEL_W - 4, guiX));
        return Math.max(6, width / 2 - (SIDEBAR_W + PANEL_W) / 2);
    }

    private int top() {
        if (guiY >= 0) return Math.max(4, Math.min(height - 342, guiY));
        return Math.max(6, height / 2 - 166);
    }

    private boolean inside(int mx, int my, int x1, int y1, int x2, int y2) {
        return mx >= x1 && mx <= x2 && my >= y1 && my <= y2;
    }

    @Override
    public void initGui() {
        search = new GuiTextField(91, fontRendererObj,
                left() + SIDEBAR_W + 265, top() + 13, 176, 20);
        search.setMaxStringLength(48);
        search.setFocused(false);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        int x = left();
        int y = top();

        if (minimized) {
            drawRect(x, y, x + SIDEBAR_W, y + 34, BG);
            fontRendererObj.drawString("VAPE", x + 12, y + 11, TEXT);
            fontRendererObj.drawString("V4", x + 46, y + 11, CARD_ON);
            fontRendererObj.drawString("+", x + SIDEBAR_W - 18, y + 11, DIM);
            super.drawScreen(mouseX, mouseY, partialTicks);
            return;
        }

        int totalW = SIDEBAR_W + PANEL_W;
        int totalH = 342;

        drawRect(x + 4, y + 4, x + totalW + 4, y + totalH + 4, 0x66000000);
        drawRect(x, y, x + SIDEBAR_W, y + totalH, BG);
        drawRect(x + SIDEBAR_W, y, x + totalW, y + totalH, PANEL);

        drawRect(x, y, x + SIDEBAR_W, y + 42, 0xFF111214);
        fontRendererObj.drawString("VAPE", x + 12, y + 13, TEXT);
        fontRendererObj.drawString("V4", x + 46, y + 13, CARD_ON);
        fontRendererObj.drawString("-", x + SIDEBAR_W - 18, y + 13, DIM);

        drawSidebar(x, y, mouseX, mouseY);
        drawModulePanel(x + SIDEBAR_W, y, totalH, mouseX, mouseY);

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    private void drawSidebar(int x, int y, int mouseX, int mouseY) {
        int cy = y + 52;

        for (int i = 0; i < categories.length; ++i) {
            int rowY = cy + i * 28;
            boolean active = i == selectedCategory;
            boolean hover = inside(mouseX, mouseY, x + 7, rowY, x + SIDEBAR_W - 7, rowY + 24);

            if (active) {
                drawRect(x + 7, rowY, x + SIDEBAR_W - 7, rowY + 24, 0xFF292C2F);
                drawRect(x + 7, rowY, x + 10, rowY + 24, CARD_ON);
            } else if (hover) {
                drawRect(x + 7, rowY, x + SIDEBAR_W - 7, rowY + 24, 0xFF202225);
            }

            fontRendererObj.drawString(pretty(categories[i]), x + 16, rowY + 8, active ? TEXT : DIM);
        }

        drawRect(x + 9, y + 286, x + SIDEBAR_W - 9, y + 287, LINE);
        fontRendererObj.drawString("CONFIG", x + 12, y + 295, DIM);

        if (inside(mouseX, mouseY, x + 7, y + 302, x + SIDEBAR_W - 7, y + 326))
            drawRect(x + 7, y + 302, x + SIDEBAR_W - 7, y + 326, CARD_HOVER);
        fontRendererObj.drawString("Profiles", x + 16, y + 310, TEXT);

        if (inside(mouseX, mouseY, x + 7, y + 327, x + SIDEBAR_W - 7, y + 351))
            drawRect(x + 7, y + 327, x + SIDEBAR_W - 7, y + 351, CARD_HOVER);
        fontRendererObj.drawString("Macros", x + 16, y + 335, TEXT);
    }

    private void drawModulePanel(int x, int y, int h, int mouseX, int mouseY) {
        drawRect(x, y, x + PANEL_W, y + HEADER_H, 0xFF151618);

        String categoryName = pretty(categories[selectedCategory]);
        fontRendererObj.drawString(categoryName, x + 14, y + 12, TEXT);
        fontRendererObj.drawString(filteredModules().size() + " modules", x + 14, y + 27, DIM);

        search.drawTextBox();
        if (search.getText().length() == 0) {
            fontRendererObj.drawString("Search modules...", x + 276, y + 18, DIM);
        }

        List<Module> modules = filteredModules();
        int columns = 2;
        int startY = y + HEADER_H + 8;
        int availableH = h - HEADER_H - 12;
        int rowsVisible = Math.max(1, availableH / (CARD_H + CARD_GAP));
        int maxScroll = Math.max(0, (int)Math.ceil(modules.size() / 2.0) - rowsVisible);
        scroll = Math.max(0, Math.min(scroll, maxScroll));

        int first = scroll * columns;
        int row = 0;
        int col = 0;

        for (int i = first; i < modules.size(); ++i) {
            Module module = modules.get(i);
            int cx = x + 10 + col * (CARD_W + CARD_GAP);
            int cy = startY + row * (CARD_H + CARD_GAP);
            if (cy + CARD_H > y + h - 5) break;

            boolean hover = inside(mouseX, mouseY, cx, cy, cx + CARD_W, cy + CARD_H);
            boolean on = module.isEnabled();

            drawRect(cx, cy, cx + CARD_W, cy + CARD_H, on ? CARD_ON : (hover ? CARD_HOVER : CARD));
            drawRect(cx, cy, cx + 3, cy + CARD_H, on ? 0xFF8BFFAA : 0xFF3A3D40);

            fontRendererObj.drawString(module.getName(), cx + 11, cy + 8, on ? 0xFF101311 : TEXT);
            String description = module.getDescription();
            if (description.length() > 31) description = description.substring(0, 31) + "...";
            fontRendererObj.drawString(description, cx + 11, cy + 22, on ? 0xFF1A251D : DIM);

            if (expanded == module && !module.getSettings().isEmpty()) {
                drawRect(cx + 10, cy + 37, cx + CARD_W - 10, cy + 38, on ? 0x55303030 : LINE);
                int sy = cy + 43;
                int shown = 0;
                for (Setting<?> setting : module.getSettings()) {
                    if (shown++ >= 2) break;
                    fontRendererObj.drawString(setting.getName() + ": " + settingValue(setting),
                            cx + 11, sy, on ? 0xFF1A251D : DIM);
                    sy += 9;
                }
            } else if (!module.getSettings().isEmpty()) {
                fontRendererObj.drawString("Settings  +", cx + 11, cy + 43, on ? 0xFF1A251D : DIM);
            }

            if (!module.getSettings().isEmpty()) {
                fontRendererObj.drawString(expanded == module ? "-" : "+",
                        cx + CARD_W - 16, cy + 8, on ? 0xFF101311 : DIM);
            }

            col++;
            if (col >= columns) {
                col = 0;
                row++;
            }
        }

        if (maxScroll > 0) {
            int barX = x + PANEL_W - 5;
            int barTop = startY;
            int barBottom = y + h - 8;
            int barH = Math.max(18, (barBottom - barTop) * rowsVisible / (rowsVisible + maxScroll));
            int barY = barTop + (barBottom - barTop - barH) * scroll / maxScroll;
            drawRect(barX, barTop, barX + 2, barBottom, LINE);
            drawRect(barX, barY, barX + 2, barY + barH, CARD_ON);
        }
    }

    private String settingValue(Setting<?> setting) {
        Object value = setting.getValue();
        if (value instanceof Double) {
            return String.format(java.util.Locale.US, "%.2f", (Double)value);
        }
        return String.valueOf(value);
    }

    private List<Module> filteredModules() {
        ArrayList<Module> result = new ArrayList<Module>();
        String query = search == null ? "" : search.getText().trim().toLowerCase();

        for (Module module : Client.manager.getModulesByCategory(categories[selectedCategory])) {
            if (query.length() == 0
                    || module.getName().toLowerCase().contains(query)
                    || module.getDescription().toLowerCase().contains(query)) {
                result.add(module);
            }
        }
        return result;
    }

    private String pretty(Category category) {
        String value = category.name();
        return value.substring(0, 1).toUpperCase() + value.substring(1);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        int x = left();
        int y = top();

        if (mouseButton == 0 && inside(mouseX, mouseY, x, y, x + SIDEBAR_W, y + 34)
                && net.lax1dude.eaglercraft.Keyboard.isKeyDown(KeyboardConstants.KEY_LSHIFT)) {
            dragging = true;
            dragOffsetX = mouseX - x;
            dragOffsetY = mouseY - y;
            return;
        }

        if (mouseButton == 0 && inside(mouseX, mouseY, x, y, x + SIDEBAR_W, y + 34)) {
            minimized = !minimized;
            return;
        }

        if (minimized) {
            super.mouseClicked(mouseX, mouseY, mouseButton);
            return;
        }

        if (mouseButton == 0 && inside(mouseX, mouseY, x + 7, y + 302, x + SIDEBAR_W - 7, y + 326)) {
            mc.displayGuiScreen(new ConfigScreen());
            return;
        }

        if (mouseButton == 0 && inside(mouseX, mouseY, x + 7, y + 327, x + SIDEBAR_W - 7, y + 351)) {
            mc.displayGuiScreen(new MacroScreen(this));
            return;
        }

        int cy = y + 52;
        for (int i = 0; i < categories.length; ++i) {
            int rowY = cy + i * 28;
            if (mouseButton == 0 && inside(mouseX, mouseY, x + 7, rowY, x + SIDEBAR_W - 7, rowY + 24)) {
                selectedCategory = i;
                scroll = 0;
                expanded = null;
                return;
            }
        }

        int panelX = x + SIDEBAR_W;
        if (mouseButton == 0 && inside(mouseX, mouseY, panelX + 260, y + 8, panelX + PANEL_W - 10, y + 38)) {
            search.mouseClicked(mouseX, mouseY, mouseButton);
            return;
        }

        List<Module> modules = filteredModules();
        int h = 342;
        int startY = y + HEADER_H + 8;
        int columns = 2;
        int first = scroll * columns;

        for (int i = first; i < modules.size(); ++i) {
            int local = i - first;
            int col = local % columns;
            int row = local / columns;
            int cx = panelX + 10 + col * (CARD_W + CARD_GAP);
            int cy2 = startY + row * (CARD_H + CARD_GAP);
            if (cy2 + CARD_H > y + h - 5) break;

            Module module = modules.get(i);
            if (inside(mouseX, mouseY, cx, cy2, cx + CARD_W, cy2 + CARD_H)) {
                if (mouseButton == 0) {
                    if (!module.getSettings().isEmpty() && mouseX >= cx + CARD_W - 34) {
                        expanded = expanded == module ? null : module;
                        module.open = expanded == module;
                    } else {
                        module.toggle();
                    }
                } else if (mouseButton == 1 && !module.getSettings().isEmpty()) {
                    expanded = expanded == module ? null : module;
                    module.open = expanded == module;
                }
                return;
            }
        }

        super.mouseClicked(mouseX, mouseY, mouseButton);
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

        if (Client.isBindingGuiKey()) {
            if (keyCode != KeyboardConstants.KEY_ESCAPE) {
                Client.setGuiKey(keyCode);
                Client.setBindingGuiKey(false);
                com.zero7wrath.clientbase.config.ConfigManager.save("default");
            } else {
                Client.setBindingGuiKey(false);
            }
            return;
        }

        if (keyCode == KeyboardConstants.KEY_ESCAPE) {
            mc.displayGuiScreen(null);
            return;
        }

        super.keyTyped(typedChar, keyCode);
    }

    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        if (minimized) return;

        int wheel = net.lax1dude.eaglercraft.Mouse.getEventDWheel();
        if (wheel > 0) scroll--;
        else if (wheel < 0) scroll++;
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int state) {
        dragging = false;
        super.mouseReleased(mouseX, mouseY, state);
    }

    @Override
    protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
        if (dragging && clickedMouseButton == 0) {
            guiX = mouseX - dragOffsetX;
            guiY = mouseY - dragOffsetY;
        }
        super.mouseClickMove(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}
