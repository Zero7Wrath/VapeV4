package net.minecraft.client.gui;

import com.isacofff.clientbase.Category;
import com.isacofff.clientbase.Client;
import com.isacofff.clientbase.modules.Module;
import com.isacofff.clientbase.settings.Setting;
import com.isacofff.clientbase.settings.Setting.BooleanSetting;
import com.isacofff.clientbase.settings.Setting.ModeSetting;
import com.isacofff.clientbase.settings.Setting.NumberSetting;
import net.lax1dude.eaglercraft.Keyboard;
import net.lax1dude.eaglercraft.KeyboardConstants;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Vape V4 inspired ClickGUI port for Eaglercraft.
 *
 * The original Roblox GUI is component based. This class translates the
 * important interaction model into Minecraft's GuiScreen system:
 * - 220px dark windows
 * - draggable category windows
 * - expandable module rows
 * - Boolean / Mode / Number settings
 * - search bar
 * - tooltip descriptions
 *
 * Rendering intentionally uses vanilla GuiScreen primitives so it remains
 * compatible with the Eaglercraft WASM-GC target.
 */
public class ClickGuiScreen extends GuiScreen {

    private static final int WINDOW_WIDTH = 220;
    private static final int HEADER_HEIGHT = 41;
    private static final int ROW_HEIGHT = 27;
    private static final int SEARCH_HEIGHT = 37;
    private static final int WINDOW_GAP = 10;

    private static final int MAIN = 0xFF1A191A;
    private static final int MAIN_DARK = 0xFF151415;
    private static final int ROW = 0xFF1A191A;
    private static final int ROW_HOVER = 0xFF252426;
    private static final int BORDER = 0xCC555555;
    private static final int TEXT = 0xFFC8C8C8;
    private static final int TEXT_DIM = 0xFF8C8C8C;
    private static final int ACCENT = 0xFFB8B8B8;
    private static final int ENABLED = 0xFFB8B8B8;
    private static final int ENABLED_TEXT = 0xFF1A191A;
    private static final int TOOLTIP = 0xF0181718;

    private final List<CategoryPanel> panels = new ArrayList<CategoryPanel>();
    private GuiTextField searchField;

    private CategoryPanel draggingPanel;
    private int dragOffsetX;
    private int dragOffsetY;

    private NumberSetting draggingSlider;
    private CategoryPanel sliderPanel;

    private String tooltipText;
    private int tooltipX;
    private int tooltipY;

    public ClickGuiScreen() {
        int x = 12;
        int y = 60;

        for (Category category : Category.values()) {
            CategoryPanel panel = new CategoryPanel(category, x, y);
            panels.add(panel);
            x += WINDOW_WIDTH + WINDOW_GAP;

            if (x + WINDOW_WIDTH > 1920) {
                x = 12;
                y += 90;
            }
        }
    }

    @Override
    public void initGui() {
        super.initGui();

        searchField = new GuiTextField(
                100,
                this.fontRendererObj,
                Math.max(0, this.width / 2 - 110),
                13,
                220,
                SEARCH_HEIGHT
        );
        searchField.setMaxStringLength(64);
        searchField.setText("");
        searchField.setFocused(false);

        updatePanelPositionsForScreen();
    }

    private void updatePanelPositionsForScreen() {
        if (panels.isEmpty()) {
            return;
        }

        int x = 12;
        int y = 60;

        for (CategoryPanel panel : panels) {
            if (!panel.hasBeenMoved) {
                panel.x = x;
                panel.y = y;
            }

            x += WINDOW_WIDTH + WINDOW_GAP;
            if (x + WINDOW_WIDTH > this.width - 12) {
                x = 12;
                y += 90;
            }
        }
    }

    @Override
    public void updateScreen() {
        if (searchField != null) {
            searchField.updateCursorCounter();
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();

        tooltipText = null;

        drawSearchBar(mouseX, mouseY);

        for (CategoryPanel panel : panels) {
            panel.draw(mouseX, mouseY);
        }

        if (tooltipText != null) {
            drawTooltip(tooltipText, tooltipX, tooltipY);
        }

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    private void drawSearchBar(int mouseX, int mouseY) {
        int x = searchField.xPosition;
        int y = searchField.yPosition;

        drawPanel(x, y, x + WINDOW_WIDTH, y + SEARCH_HEIGHT);

        fontRendererObj.drawString("Vape", x + 9, y + 14, TEXT);
        fontRendererObj.drawString("V4", x + 44, y + 14, ACCENT);

        drawRect(x + 57, y + 12, x + 58, y + 25, 0xFF383638);

        if (searchField.getText().length() == 0) {
            fontRendererObj.drawString("Search", x + 67, y + 14, TEXT_DIM);
        } else {
            searchField.drawTextBox();
        }

        fontRendererObj.drawString("⌕", x + WINDOW_WIDTH - 20, y + 12, TEXT_DIM);
    }

    private void drawPanel(int x1, int y1, int x2, int y2) {
        drawRect(x1, y1, x2, y2, MAIN);
        drawRect(x1, y1, x2, y1 + 1, BORDER);
        drawRect(x1, y2 - 1, x2, y2, BORDER);
        drawRect(x1, y1, x1 + 1, y2, BORDER);
        drawRect(x2 - 1, y1, x2, y2, BORDER);
    }

    private void drawTooltip(String text, int mouseX, int mouseY) {
        int width = fontRendererObj.getStringWidth(text) + 12;
        int x = Math.min(mouseX + 8, this.width - width - 2);
        int y = Math.min(mouseY + 8, this.height - 20);

        drawRect(x, y, x + width, y + 18, TOOLTIP);
        fontRendererObj.drawString(text, x + 6, y + 5, TEXT);
    }

    private boolean isHover(int mouseX, int mouseY, int x, int y, int width, int height) {
        return mouseX >= x && mouseX <= x + width
                && mouseY >= y && mouseY <= y + height;
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        tooltipText = null;

        if (searchField != null && isHover(
                mouseX,
                mouseY,
                searchField.xPosition,
                searchField.yPosition,
                searchField.width,
                searchField.height)) {
            searchField.mouseClicked(mouseX, mouseY, mouseButton);
            return;
        }

        for (int i = panels.size() - 1; i >= 0; i--) {
            if (panels.get(i).mouseClicked(mouseX, mouseY, mouseButton)) {
                return;
            }
        }

        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
        if (draggingPanel != null && clickedMouseButton == 0) {
            draggingPanel.x = mouseX - dragOffsetX;
            draggingPanel.y = mouseY - dragOffsetY;
            draggingPanel.hasBeenMoved = true;
        }

        if (draggingSlider != null && clickedMouseButton == 0 && sliderPanel != null) {
            sliderPanel.updateSlider(draggingSlider, mouseX);
        }

        super.mouseClickMove(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        draggingPanel = null;
        draggingSlider = null;
        sliderPanel = null;
        super.mouseReleased(mouseX, mouseY, state);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (searchField != null && searchField.isFocused()) {
            if (keyCode == KeyboardConstants.KEY_ESCAPE) {
                searchField.setFocused(false);
                return;
            }

            searchField.textboxKeyTyped(typedChar, keyCode);
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
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    private class CategoryPanel {

        private final Category category;

        private int x;
        private int y;

        private boolean expanded = true;
        private boolean hasBeenMoved = false;

        private CategoryPanel(Category category, int x, int y) {
            this.category = category;
            this.x = x;
            this.y = y;
        }

        private ArrayList<Module> getVisibleModules() {
            ArrayList<Module> result = new ArrayList<Module>();
            String query = searchField == null ? "" : searchField.getText().trim().toLowerCase();

            for (Module module : Client.manager.getModulesByCategory(category)) {
                if (query.length() == 0 || module.getName().toLowerCase().contains(query)) {
                    result.add(module);
                }
            }

            return result;
        }

        private int getHeight() {
            int height = HEADER_HEIGHT;

            if (!expanded) {
                return height;
            }

            for (Module module : getVisibleModules()) {
                height += ROW_HEIGHT;

                if (module.open) {
                    for (Setting<?> setting : module.getSettings()) {
                        height += ROW_HEIGHT;
                    }
                }
            }

            return Math.min(height, 601);
        }

        private void draw(int mouseX, int mouseY) {
            int height = getHeight();

            drawPanel(x, y, x + WINDOW_WIDTH, y + height);

            fontRendererObj.drawString(category.name(), x + 12, y + 15, TEXT);

            String arrow = expanded ? "v" : ">";
            fontRendererObj.drawString(arrow, x + WINDOW_WIDTH - 19, y + 15, TEXT_DIM);

            if (!expanded) {
                return;
            }

            int rowY = y + HEADER_HEIGHT;
            ArrayList<Module> modules = getVisibleModules();

            for (Module module : modules) {
                boolean hover = isHover(mouseX, mouseY, x, rowY, WINDOW_WIDTH, ROW_HEIGHT);
                int background = module.isEnabled() ? ENABLED : (hover ? ROW_HOVER : ROW);

                drawRect(x + 1, rowY, x + WINDOW_WIDTH - 1, rowY + ROW_HEIGHT, background);

                int textColor = module.isEnabled() ? ENABLED_TEXT : TEXT;
                fontRendererObj.drawString(module.getName(), x + 12, rowY + 9, textColor);

                String dots = module.getSettings().isEmpty() ? "" : "...";
                if (dots.length() > 0) {
                    fontRendererObj.drawString(
                            dots,
                            x + WINDOW_WIDTH - 25,
                            rowY + 9,
                            module.open ? TEXT : TEXT_DIM
                    );
                }

                if (hover && module.getDescription() != null
                        && module.getDescription().length() > 0
                        && !" - - - ".equals(module.getDescription())) {
                    tooltipText = module.getDescription();
                    tooltipX = mouseX;
                    tooltipY = mouseY;
                }

                rowY += ROW_HEIGHT;

                if (module.open) {
                    for (Setting<?> setting : module.getSettings()) {
                        drawSetting(module, setting, rowY, mouseX, mouseY);
                        rowY += ROW_HEIGHT;
                    }
                }
            }
        }

        private void drawSetting(Module module, Setting<?> setting, int rowY, int mouseX, int mouseY) {
            boolean hover = isHover(mouseX, mouseY, x, rowY, WINDOW_WIDTH, ROW_HEIGHT);
            int background = hover ? ROW_HOVER : MAIN_DARK;

            drawRect(x + 1, rowY, x + WINDOW_WIDTH - 1, rowY + ROW_HEIGHT, background);

            String label = setting.getName();
            int textColor = TEXT;

            if (setting instanceof BooleanSetting) {
                BooleanSetting bool = (BooleanSetting) setting;
                String value = bool.getValue() ? "ON" : "OFF";
                fontRendererObj.drawString(label, x + 12, rowY + 8, textColor);

                int valueWidth = fontRendererObj.getStringWidth(value);
                fontRendererObj.drawString(
                        value,
                        x + WINDOW_WIDTH - valueWidth - 12,
                        rowY + 8,
                        bool.getValue() ? TEXT : TEXT_DIM
                );
            } else if (setting instanceof ModeSetting) {
                ModeSetting mode = (ModeSetting) setting;
                String value = String.valueOf(mode.getValue());
                fontRendererObj.drawString(label, x + 12, rowY + 8, textColor);

                int valueWidth = fontRendererObj.getStringWidth(value);
                fontRendererObj.drawString(
                        value,
                        x + WINDOW_WIDTH - valueWidth - 12,
                        rowY + 8,
                        ACCENT
                );
            } else if (setting instanceof NumberSetting) {
                NumberSetting number = (NumberSetting) setting;

                fontRendererObj.drawString(label, x + 12, rowY + 5, textColor);

                String value = formatNumber(number.getValue());
                int valueWidth = fontRendererObj.getStringWidth(value);
                fontRendererObj.drawString(
                        value,
                        x + WINDOW_WIDTH - valueWidth - 12,
                        rowY + 5,
                        ACCENT
                );

                int barX = x + 12;
                int barY = rowY + 19;
                int barWidth = WINDOW_WIDTH - 24;

                drawRect(barX, barY, barX + barWidth, barY + 2, 0xFF454345);

                double range = number.getMax() - number.getMin();
                double percent = range <= 0.0
                        ? 0.0
                        : (number.getValue() - number.getMin()) / range;

                percent = Math.max(0.0, Math.min(1.0, percent));

                int fillWidth = (int) (barWidth * percent);
                drawRect(barX, barY, barX + fillWidth, barY + 2, ACCENT);

                int knobX = barX + fillWidth;
                drawRect(knobX - 2, barY - 2, knobX + 2, barY + 4, TEXT);
            }

            if (hover) {
                tooltipText = setting.getName();
                tooltipX = mouseX;
                tooltipY = mouseY;
            }
        }

        private boolean mouseClicked(int mouseX, int mouseY, int mouseButton) {
            int height = getHeight();

            if (!isHover(mouseX, mouseY, x, y, WINDOW_WIDTH, height)) {
                return false;
            }

            if (isHover(mouseX, mouseY, x, y, WINDOW_WIDTH, HEADER_HEIGHT)) {
                if (mouseButton == 0) {
                    draggingPanel = this;
                    dragOffsetX = mouseX - x;
                    dragOffsetY = mouseY - y;
                } else if (mouseButton == 1) {
                    expanded = !expanded;
                }

                return true;
            }

            if (!expanded) {
                return true;
            }

            int rowY = y + HEADER_HEIGHT;

            for (Module module : getVisibleModules()) {
                if (isHover(mouseX, mouseY, x, rowY, WINDOW_WIDTH, ROW_HEIGHT)) {
                    if (mouseButton == 0) {
                        int dotsX = x + WINDOW_WIDTH - 34;

                        if (!module.getSettings().isEmpty() && mouseX >= dotsX) {
                            module.open = !module.open;
                        } else {
                            module.toggle();
                        }
                    } else if (mouseButton == 1) {
                        module.open = !module.open;
                    }

                    return true;
                }

                rowY += ROW_HEIGHT;

                if (module.open) {
                    for (Setting<?> setting : module.getSettings()) {
                        if (isHover(mouseX, mouseY, x, rowY, WINDOW_WIDTH, ROW_HEIGHT)) {
                            handleSettingClick(setting, mouseX, mouseButton);
                            return true;
                        }

                        rowY += ROW_HEIGHT;
                    }
                }
            }

            return true;
        }

        private void handleSettingClick(Setting<?> setting, int mouseX, int mouseButton) {
            if (setting instanceof BooleanSetting) {
                if (mouseButton == 0) {
                    ((BooleanSetting) setting).toggle();
                }
                return;
            }

            if (setting instanceof ModeSetting) {
                if (mouseButton == 0) {
                    ((ModeSetting) setting).cycle();
                }
                return;
            }

            if (setting instanceof NumberSetting && mouseButton == 0) {
                draggingSlider = (NumberSetting) setting;
                sliderPanel = this;
                updateSlider((NumberSetting) setting, mouseX);
            }
        }

        private void updateSlider(NumberSetting setting, int mouseX) {
            int barX = x + 12;
            int barWidth = WINDOW_WIDTH - 24;

            double percent = (mouseX - barX) / (double) barWidth;
            percent = Math.max(0.0, Math.min(1.0, percent));

            double value = setting.getMin()
                    + percent * (setting.getMax() - setting.getMin());

            double increment = setting.getIncrement();
            if (increment > 0.0) {
                value = setting.getMin()
                        + Math.round((value - setting.getMin()) / increment) * increment;
            }

            value = Math.max(setting.getMin(), Math.min(setting.getMax(), value));
            setting.setValue(value);
        }
    }

    private String formatNumber(double value) {
        if (value == (long) value) {
            return String.valueOf((long) value);
        }

        String result = String.valueOf(value);
        if (result.length() > 7) {
            result = result.substring(0, 7);
        }

        return result;
    }
}
