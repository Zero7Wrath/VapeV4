package com.zero7wrath.clientbase.gui;

public final class GuiTheme {
    private static String mode = "RAINBOW";
    private static float speed = 0.35F;

    private GuiTheme() {}

    public static String getMode() { return mode; }

    public static void setMode(String value) {
        if (value == null) return;
        if ("RGB".equalsIgnoreCase(value)) mode = "RGB";
        else if ("RAINBOW".equalsIgnoreCase(value)) mode = "RAINBOW";
        else if ("GREEN".equalsIgnoreCase(value)) mode = "GREEN";
    }

    public static float getSpeed() { return speed; }

    public static void setSpeed(float value) {
        speed = Math.max(0.05F, Math.min(2.0F, value));
    }

    public static int accent() {
        if ("GREEN".equals(mode)) return 0xFF43E06D;
        float t = (System.currentTimeMillis() % 6000L) / 6000.0F * speed;
        if ("RGB".equals(mode)) t = (System.currentTimeMillis() % 3000L) / 3000.0F;
        float h = (t % 1.0F) * 6.0F;
        int segment = (int) h;
        float f = h - segment;
        int r = 0, g = 0, b = 0;
        switch (segment) {
            case 0: r=255; g=(int)(255*f); break;
            case 1: r=(int)(255*(1-f)); g=255; break;
            case 2: g=255; b=(int)(255*f); break;
            case 3: g=(int)(255*(1-f)); b=255; break;
            case 4: r=(int)(255*f); b=255; break;
            default: r=255; b=(int)(255*(1-f)); break;
        }
        return 0xFF000000 | (r << 16) | (g << 8) | b;
    }
}
