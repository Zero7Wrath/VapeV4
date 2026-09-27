package com.zero7wrath.clientbase.modules.features.client;

import com.zero7wrath.clientbase.Category;
import com.zero7wrath.clientbase.modules.Module;
import com.zero7wrath.clientbase.modules.features.core.BulkModuleLogic;
import net.minecraft.client.Minecraft;

public final class ClientModules {
    public static class GuiToggleBasic extends Module {
        private final Minecraft mc = Minecraft.getMinecraft();
        public GuiToggleBasic() { super("GuiToggleBasic", "Client module: GuiToggleBasic.", Category.Client); }
        @Override public void onUpdate() { BulkModuleLogic.tick(mc, Category.Client, 0); }
    }

    public static class GuiToggleFast extends Module {
        private final Minecraft mc = Minecraft.getMinecraft();
        public GuiToggleFast() { super("GuiToggleFast", "Client module: GuiToggleFast.", Category.Client); }
        @Override public void onUpdate() { BulkModuleLogic.tick(mc, Category.Client, 1); }
    }

    public static class GuiToggleSlow extends Module {
        private final Minecraft mc = Minecraft.getMinecraft();
        public GuiToggleSlow() { super("GuiToggleSlow", "Client module: GuiToggleSlow.", Category.Client); }
        @Override public void onUpdate() { BulkModuleLogic.tick(mc, Category.Client, 2); }
    }

    public static class GuiToggleSmooth extends Module {
        private final Minecraft mc = Minecraft.getMinecraft();
        public GuiToggleSmooth() { super("GuiToggleSmooth", "Client module: GuiToggleSmooth.", Category.Client); }
        @Override public void onUpdate() { BulkModuleLogic.tick(mc, Category.Client, 3); }
    }

    public static class GuiToggleSmart extends Module {
        private final Minecraft mc = Minecraft.getMinecraft();
        public GuiToggleSmart() { super("GuiToggleSmart", "Client module: GuiToggleSmart.", Category.Client); }
        @Override public void onUpdate() { BulkModuleLogic.tick(mc, Category.Client, 4); }
    }

    public static class GuiToggleAdaptive extends Module {
        private final Minecraft mc = Minecraft.getMinecraft();
        public GuiToggleAdaptive() { super("GuiToggleAdaptive", "Client module: GuiToggleAdaptive.", Category.Client); }
        @Override public void onUpdate() { BulkModuleLogic.tick(mc, Category.Client, 5); }
    }

    public static class GuiTogglePlus extends Module {
        private final Minecraft mc = Minecraft.getMinecraft();
        public GuiTogglePlus() { super("GuiTogglePlus", "Client module: GuiTogglePlus.", Category.Client); }
        @Override public void onUpdate() { BulkModuleLogic.tick(mc, Category.Client, 6); }
    }

    public static class ThemeRGBBasic extends Module {
        private final Minecraft mc = Minecraft.getMinecraft();
        public ThemeRGBBasic() { super("ThemeRGBBasic", "Client module: ThemeRGBBasic.", Category.Client); }
        @Override public void onUpdate() { BulkModuleLogic.tick(mc, Category.Client, 7); }
    }

    public static class ThemeRGBFast extends Module {
        private final Minecraft mc = Minecraft.getMinecraft();
        public ThemeRGBFast() { super("ThemeRGBFast", "Client module: ThemeRGBFast.", Category.Client); }
        @Override public void onUpdate() { BulkModuleLogic.tick(mc, Category.Client, 8); }
    }

    public static class ThemeRGBSlow extends Module {
        private final Minecraft mc = Minecraft.getMinecraft();
        public ThemeRGBSlow() { super("ThemeRGBSlow", "Client module: ThemeRGBSlow.", Category.Client); }
        @Override public void onUpdate() { BulkModuleLogic.tick(mc, Category.Client, 9); }
    }

    public static class ThemeRGBSmooth extends Module {
        private final Minecraft mc = Minecraft.getMinecraft();
        public ThemeRGBSmooth() { super("ThemeRGBSmooth", "Client module: ThemeRGBSmooth.", Category.Client); }
        @Override public void onUpdate() { BulkModuleLogic.tick(mc, Category.Client, 10); }
    }

    public static class ThemeRGBSmart extends Module {
        private final Minecraft mc = Minecraft.getMinecraft();
        public ThemeRGBSmart() { super("ThemeRGBSmart", "Client module: ThemeRGBSmart.", Category.Client); }
        @Override public void onUpdate() { BulkModuleLogic.tick(mc, Category.Client, 11); }
    }

    public static class ThemeRGBAdaptive extends Module {
        private final Minecraft mc = Minecraft.getMinecraft();
        public ThemeRGBAdaptive() { super("ThemeRGBAdaptive", "Client module: ThemeRGBAdaptive.", Category.Client); }
        @Override public void onUpdate() { BulkModuleLogic.tick(mc, Category.Client, 12); }
    }

    public static class ThemeRGBPlus extends Module {
        private final Minecraft mc = Minecraft.getMinecraft();
        public ThemeRGBPlus() { super("ThemeRGBPlus", "Client module: ThemeRGBPlus.", Category.Client); }
        @Override public void onUpdate() { BulkModuleLogic.tick(mc, Category.Client, 13); }
    }

    public static class ThemeRainbowBasic extends Module {
        private final Minecraft mc = Minecraft.getMinecraft();
        public ThemeRainbowBasic() { super("ThemeRainbowBasic", "Client module: ThemeRainbowBasic.", Category.Client); }
        @Override public void onUpdate() { BulkModuleLogic.tick(mc, Category.Client, 14); }
    }

    public static class ThemeRainbowFast extends Module {
        private final Minecraft mc = Minecraft.getMinecraft();
        public ThemeRainbowFast() { super("ThemeRainbowFast", "Client module: ThemeRainbowFast.", Category.Client); }
        @Override public void onUpdate() { BulkModuleLogic.tick(mc, Category.Client, 15); }
    }

    public static class ThemeRainbowSlow extends Module {
        private final Minecraft mc = Minecraft.getMinecraft();
        public ThemeRainbowSlow() { super("ThemeRainbowSlow", "Client module: ThemeRainbowSlow.", Category.Client); }
        @Override public void onUpdate() { BulkModuleLogic.tick(mc, Category.Client, 16); }
    }

    public static class ThemeRainbowSmooth extends Module {
        private final Minecraft mc = Minecraft.getMinecraft();
        public ThemeRainbowSmooth() { super("ThemeRainbowSmooth", "Client module: ThemeRainbowSmooth.", Category.Client); }
        @Override public void onUpdate() { BulkModuleLogic.tick(mc, Category.Client, 17); }
    }

    public static class ThemeRainbowSmart extends Module {
        private final Minecraft mc = Minecraft.getMinecraft();
        public ThemeRainbowSmart() { super("ThemeRainbowSmart", "Client module: ThemeRainbowSmart.", Category.Client); }
        @Override public void onUpdate() { BulkModuleLogic.tick(mc, Category.Client, 18); }
    }

    public static class ThemeRainbowAdaptive extends Module {
        private final Minecraft mc = Minecraft.getMinecraft();
        public ThemeRainbowAdaptive() { super("ThemeRainbowAdaptive", "Client module: ThemeRainbowAdaptive.", Category.Client); }
        @Override public void onUpdate() { BulkModuleLogic.tick(mc, Category.Client, 19); }
    }

    public static class ThemeRainbowPlus extends Module {
        private final Minecraft mc = Minecraft.getMinecraft();
        public ThemeRainbowPlus() { super("ThemeRainbowPlus", "Client module: ThemeRainbowPlus.", Category.Client); }
        @Override public void onUpdate() { BulkModuleLogic.tick(mc, Category.Client, 20); }
    }

    public static class ThemeGreenBasic extends Module {
        private final Minecraft mc = Minecraft.getMinecraft();
        public ThemeGreenBasic() { super("ThemeGreenBasic", "Client module: ThemeGreenBasic.", Category.Client); }
        @Override public void onUpdate() { BulkModuleLogic.tick(mc, Category.Client, 21); }
    }

    public static class ThemeGreenFast extends Module {
        private final Minecraft mc = Minecraft.getMinecraft();
        public ThemeGreenFast() { super("ThemeGreenFast", "Client module: ThemeGreenFast.", Category.Client); }
        @Override public void onUpdate() { BulkModuleLogic.tick(mc, Category.Client, 22); }
    }

    public static class ThemeGreenSlow extends Module {
        private final Minecraft mc = Minecraft.getMinecraft();
        public ThemeGreenSlow() { super("ThemeGreenSlow", "Client module: ThemeGreenSlow.", Category.Client); }
        @Override public void onUpdate() { BulkModuleLogic.tick(mc, Category.Client, 23); }
    }

    public static class ThemeGreenSmooth extends Module {
        private final Minecraft mc = Minecraft.getMinecraft();
        public ThemeGreenSmooth() { super("ThemeGreenSmooth", "Client module: ThemeGreenSmooth.", Category.Client); }
        @Override public void onUpdate() { BulkModuleLogic.tick(mc, Category.Client, 24); }
    }

    public static class ThemeGreenSmart extends Module {
        private final Minecraft mc = Minecraft.getMinecraft();
        public ThemeGreenSmart() { super("ThemeGreenSmart", "Client module: ThemeGreenSmart.", Category.Client); }
        @Override public void onUpdate() { BulkModuleLogic.tick(mc, Category.Client, 25); }
    }

    public static class ThemeGreenAdaptive extends Module {
        private final Minecraft mc = Minecraft.getMinecraft();
        public ThemeGreenAdaptive() { super("ThemeGreenAdaptive", "Client module: ThemeGreenAdaptive.", Category.Client); }
        @Override public void onUpdate() { BulkModuleLogic.tick(mc, Category.Client, 26); }
    }

    public static class ThemeGreenPlus extends Module {
        private final Minecraft mc = Minecraft.getMinecraft();
        public ThemeGreenPlus() { super("ThemeGreenPlus", "Client module: ThemeGreenPlus.", Category.Client); }
        @Override public void onUpdate() { BulkModuleLogic.tick(mc, Category.Client, 27); }
    }

    public static class ThemeFastBasic extends Module {
        private final Minecraft mc = Minecraft.getMinecraft();
        public ThemeFastBasic() { super("ThemeFastBasic", "Client module: ThemeFastBasic.", Category.Client); }
        @Override public void onUpdate() { BulkModuleLogic.tick(mc, Category.Client, 28); }
    }

    public static class ThemeFastFast extends Module {
        private final Minecraft mc = Minecraft.getMinecraft();
        public ThemeFastFast() { super("ThemeFastFast", "Client module: ThemeFastFast.", Category.Client); }
        @Override public void onUpdate() { BulkModuleLogic.tick(mc, Category.Client, 29); }
    }

    public static class ThemeFastSlow extends Module {
        private final Minecraft mc = Minecraft.getMinecraft();
        public ThemeFastSlow() { super("ThemeFastSlow", "Client module: ThemeFastSlow.", Category.Client); }
        @Override public void onUpdate() { BulkModuleLogic.tick(mc, Category.Client, 30); }
    }

    public static class ThemeFastSmooth extends Module {
        private final Minecraft mc = Minecraft.getMinecraft();
        public ThemeFastSmooth() { super("ThemeFastSmooth", "Client module: ThemeFastSmooth.", Category.Client); }
        @Override public void onUpdate() { BulkModuleLogic.tick(mc, Category.Client, 31); }
    }

    public static class ThemeFastSmart extends Module {
        private final Minecraft mc = Minecraft.getMinecraft();
        public ThemeFastSmart() { super("ThemeFastSmart", "Client module: ThemeFastSmart.", Category.Client); }
        @Override public void onUpdate() { BulkModuleLogic.tick(mc, Category.Client, 32); }
    }

    public static class ThemeFastAdaptive extends Module {
        private final Minecraft mc = Minecraft.getMinecraft();
        public ThemeFastAdaptive() { super("ThemeFastAdaptive", "Client module: ThemeFastAdaptive.", Category.Client); }
        @Override public void onUpdate() { BulkModuleLogic.tick(mc, Category.Client, 33); }
    }

    public static class ThemeFastPlus extends Module {
        private final Minecraft mc = Minecraft.getMinecraft();
        public ThemeFastPlus() { super("ThemeFastPlus", "Client module: ThemeFastPlus.", Category.Client); }
        @Override public void onUpdate() { BulkModuleLogic.tick(mc, Category.Client, 34); }
    }

    public static class ThemeSlowBasic extends Module {
        private final Minecraft mc = Minecraft.getMinecraft();
        public ThemeSlowBasic() { super("ThemeSlowBasic", "Client module: ThemeSlowBasic.", Category.Client); }
        @Override public void onUpdate() { BulkModuleLogic.tick(mc, Category.Client, 35); }
    }

    public static class ThemeSlowFast extends Module {
        private final Minecraft mc = Minecraft.getMinecraft();
        public ThemeSlowFast() { super("ThemeSlowFast", "Client module: ThemeSlowFast.", Category.Client); }
        @Override public void onUpdate() { BulkModuleLogic.tick(mc, Category.Client, 36); }
    }

    public static class ThemeSlowSlow extends Module {
        private final Minecraft mc = Minecraft.getMinecraft();
        public ThemeSlowSlow() { super("ThemeSlowSlow", "Client module: ThemeSlowSlow.", Category.Client); }
        @Override public void onUpdate() { BulkModuleLogic.tick(mc, Category.Client, 37); }
    }

    public static class ThemeSlowSmooth extends Module {
        private final Minecraft mc = Minecraft.getMinecraft();
        public ThemeSlowSmooth() { super("ThemeSlowSmooth", "Client module: ThemeSlowSmooth.", Category.Client); }
        @Override public void onUpdate() { BulkModuleLogic.tick(mc, Category.Client, 38); }
    }

    public static class ThemeSlowSmart extends Module {
        private final Minecraft mc = Minecraft.getMinecraft();
        public ThemeSlowSmart() { super("ThemeSlowSmart", "Client module: ThemeSlowSmart.", Category.Client); }
        @Override public void onUpdate() { BulkModuleLogic.tick(mc, Category.Client, 39); }
    }

    public static class ThemeSlowAdaptive extends Module {
        private final Minecraft mc = Minecraft.getMinecraft();
        public ThemeSlowAdaptive() { super("ThemeSlowAdaptive", "Client module: ThemeSlowAdaptive.", Category.Client); }
        @Override public void onUpdate() { BulkModuleLogic.tick(mc, Category.Client, 40); }
    }

    public static class ThemeSlowPlus extends Module {
        private final Minecraft mc = Minecraft.getMinecraft();
        public ThemeSlowPlus() { super("ThemeSlowPlus", "Client module: ThemeSlowPlus.", Category.Client); }
        @Override public void onUpdate() { BulkModuleLogic.tick(mc, Category.Client, 41); }
    }

    public static class GuiCompactBasic extends Module {
        private final Minecraft mc = Minecraft.getMinecraft();
        public GuiCompactBasic() { super("GuiCompactBasic", "Client module: GuiCompactBasic.", Category.Client); }
        @Override public void onUpdate() { BulkModuleLogic.tick(mc, Category.Client, 42); }
    }

    public static class GuiCompactFast extends Module {
        private final Minecraft mc = Minecraft.getMinecraft();
        public GuiCompactFast() { super("GuiCompactFast", "Client module: GuiCompactFast.", Category.Client); }
        @Override public void onUpdate() { BulkModuleLogic.tick(mc, Category.Client, 43); }
    }

    public static class GuiCompactSlow extends Module {
        private final Minecraft mc = Minecraft.getMinecraft();
        public GuiCompactSlow() { super("GuiCompactSlow", "Client module: GuiCompactSlow.", Category.Client); }
        @Override public void onUpdate() { BulkModuleLogic.tick(mc, Category.Client, 44); }
    }

    public static class GuiCompactSmooth extends Module {
        private final Minecraft mc = Minecraft.getMinecraft();
        public GuiCompactSmooth() { super("GuiCompactSmooth", "Client module: GuiCompactSmooth.", Category.Client); }
        @Override public void onUpdate() { BulkModuleLogic.tick(mc, Category.Client, 45); }
    }

    public static class GuiCompactSmart extends Module {
        private final Minecraft mc = Minecraft.getMinecraft();
        public GuiCompactSmart() { super("GuiCompactSmart", "Client module: GuiCompactSmart.", Category.Client); }
        @Override public void onUpdate() { BulkModuleLogic.tick(mc, Category.Client, 46); }
    }

    public static class GuiCompactAdaptive extends Module {
        private final Minecraft mc = Minecraft.getMinecraft();
        public GuiCompactAdaptive() { super("GuiCompactAdaptive", "Client module: GuiCompactAdaptive.", Category.Client); }
        @Override public void onUpdate() { BulkModuleLogic.tick(mc, Category.Client, 47); }
    }

    public static class GuiCompactPlus extends Module {
        private final Minecraft mc = Minecraft.getMinecraft();
        public GuiCompactPlus() { super("GuiCompactPlus", "Client module: GuiCompactPlus.", Category.Client); }
        @Override public void onUpdate() { BulkModuleLogic.tick(mc, Category.Client, 48); }
    }

    public static class GuiSearchBasic extends Module {
        private final Minecraft mc = Minecraft.getMinecraft();
        public GuiSearchBasic() { super("GuiSearchBasic", "Client module: GuiSearchBasic.", Category.Client); }
        @Override public void onUpdate() { BulkModuleLogic.tick(mc, Category.Client, 49); }
    }

    public static class GuiSearchFast extends Module {
        private final Minecraft mc = Minecraft.getMinecraft();
        public GuiSearchFast() { super("GuiSearchFast", "Client module: GuiSearchFast.", Category.Client); }
        @Override public void onUpdate() { BulkModuleLogic.tick(mc, Category.Client, 50); }
    }

    public static class GuiSearchSlow extends Module {
        private final Minecraft mc = Minecraft.getMinecraft();
        public GuiSearchSlow() { super("GuiSearchSlow", "Client module: GuiSearchSlow.", Category.Client); }
        @Override public void onUpdate() { BulkModuleLogic.tick(mc, Category.Client, 51); }
    }

    public static class GuiSearchSmooth extends Module {
        private final Minecraft mc = Minecraft.getMinecraft();
        public GuiSearchSmooth() { super("GuiSearchSmooth", "Client module: GuiSearchSmooth.", Category.Client); }
        @Override public void onUpdate() { BulkModuleLogic.tick(mc, Category.Client, 52); }
    }

    public static class GuiSearchSmart extends Module {
        private final Minecraft mc = Minecraft.getMinecraft();
        public GuiSearchSmart() { super("GuiSearchSmart", "Client module: GuiSearchSmart.", Category.Client); }
        @Override public void onUpdate() { BulkModuleLogic.tick(mc, Category.Client, 53); }
    }

    public static class GuiSearchAdaptive extends Module {
        private final Minecraft mc = Minecraft.getMinecraft();
        public GuiSearchAdaptive() { super("GuiSearchAdaptive", "Client module: GuiSearchAdaptive.", Category.Client); }
        @Override public void onUpdate() { BulkModuleLogic.tick(mc, Category.Client, 54); }
    }

    public static class GuiSearchPlus extends Module {
        private final Minecraft mc = Minecraft.getMinecraft();
        public GuiSearchPlus() { super("GuiSearchPlus", "Client module: GuiSearchPlus.", Category.Client); }
        @Override public void onUpdate() { BulkModuleLogic.tick(mc, Category.Client, 55); }
    }

    public static class GuiProfilesBasic extends Module {
        private final Minecraft mc = Minecraft.getMinecraft();
        public GuiProfilesBasic() { super("GuiProfilesBasic", "Client module: GuiProfilesBasic.", Category.Client); }
        @Override public void onUpdate() { BulkModuleLogic.tick(mc, Category.Client, 56); }
    }

    public static class GuiProfilesFast extends Module {
        private final Minecraft mc = Minecraft.getMinecraft();
        public GuiProfilesFast() { super("GuiProfilesFast", "Client module: GuiProfilesFast.", Category.Client); }
        @Override public void onUpdate() { BulkModuleLogic.tick(mc, Category.Client, 57); }
    }

    public static class GuiProfilesSlow extends Module {
        private final Minecraft mc = Minecraft.getMinecraft();
        public GuiProfilesSlow() { super("GuiProfilesSlow", "Client module: GuiProfilesSlow.", Category.Client); }
        @Override public void onUpdate() { BulkModuleLogic.tick(mc, Category.Client, 58); }
    }

    public static class GuiProfilesSmooth extends Module {
        private final Minecraft mc = Minecraft.getMinecraft();
        public GuiProfilesSmooth() { super("GuiProfilesSmooth", "Client module: GuiProfilesSmooth.", Category.Client); }
        @Override public void onUpdate() { BulkModuleLogic.tick(mc, Category.Client, 59); }
    }

    public static class GuiProfilesSmart extends Module {
        private final Minecraft mc = Minecraft.getMinecraft();
        public GuiProfilesSmart() { super("GuiProfilesSmart", "Client module: GuiProfilesSmart.", Category.Client); }
        @Override public void onUpdate() { BulkModuleLogic.tick(mc, Category.Client, 60); }
    }

    public static class GuiProfilesAdaptive extends Module {
        private final Minecraft mc = Minecraft.getMinecraft();
        public GuiProfilesAdaptive() { super("GuiProfilesAdaptive", "Client module: GuiProfilesAdaptive.", Category.Client); }
        @Override public void onUpdate() { BulkModuleLogic.tick(mc, Category.Client, 61); }
    }

    public static class GuiProfilesPlus extends Module {
        private final Minecraft mc = Minecraft.getMinecraft();
        public GuiProfilesPlus() { super("GuiProfilesPlus", "Client module: GuiProfilesPlus.", Category.Client); }
        @Override public void onUpdate() { BulkModuleLogic.tick(mc, Category.Client, 62); }
    }

    public static class GuiAnimationsBasic extends Module {
        private final Minecraft mc = Minecraft.getMinecraft();
        public GuiAnimationsBasic() { super("GuiAnimationsBasic", "Client module: GuiAnimationsBasic.", Category.Client); }
        @Override public void onUpdate() { BulkModuleLogic.tick(mc, Category.Client, 63); }
    }

    public static class GuiAnimationsFast extends Module {
        private final Minecraft mc = Minecraft.getMinecraft();
        public GuiAnimationsFast() { super("GuiAnimationsFast", "Client module: GuiAnimationsFast.", Category.Client); }
        @Override public void onUpdate() { BulkModuleLogic.tick(mc, Category.Client, 64); }
    }

    public static class GuiAnimationsSlow extends Module {
        private final Minecraft mc = Minecraft.getMinecraft();
        public GuiAnimationsSlow() { super("GuiAnimationsSlow", "Client module: GuiAnimationsSlow.", Category.Client); }
        @Override public void onUpdate() { BulkModuleLogic.tick(mc, Category.Client, 65); }
    }

    public static class GuiAnimationsSmooth extends Module {
        private final Minecraft mc = Minecraft.getMinecraft();
        public GuiAnimationsSmooth() { super("GuiAnimationsSmooth", "Client module: GuiAnimationsSmooth.", Category.Client); }
        @Override public void onUpdate() { BulkModuleLogic.tick(mc, Category.Client, 66); }
    }

    public static class GuiAnimationsSmart extends Module {
        private final Minecraft mc = Minecraft.getMinecraft();
        public GuiAnimationsSmart() { super("GuiAnimationsSmart", "Client module: GuiAnimationsSmart.", Category.Client); }
        @Override public void onUpdate() { BulkModuleLogic.tick(mc, Category.Client, 67); }
    }

    public static class GuiAnimationsAdaptive extends Module {
        private final Minecraft mc = Minecraft.getMinecraft();
        public GuiAnimationsAdaptive() { super("GuiAnimationsAdaptive", "Client module: GuiAnimationsAdaptive.", Category.Client); }
        @Override public void onUpdate() { BulkModuleLogic.tick(mc, Category.Client, 68); }
    }

    public static class GuiAnimationsPlus extends Module {
        private final Minecraft mc = Minecraft.getMinecraft();
        public GuiAnimationsPlus() { super("GuiAnimationsPlus", "Client module: GuiAnimationsPlus.", Category.Client); }
        @Override public void onUpdate() { BulkModuleLogic.tick(mc, Category.Client, 69); }
    }


    public static class GuiThemeV2 extends Module {
        private final Minecraft mc = Minecraft.getMinecraft();
        public GuiThemeV2() { super("GuiThemeV2", "Client module: GuiThemeV2.", Category.Client); }
        @Override public void onUpdate() { BulkModuleLogic.tick(mc, Category.Client, 70); }
    }

    public static class GuiScaleV2 extends Module {
        private final Minecraft mc = Minecraft.getMinecraft();
        public GuiScaleV2() { super("GuiScaleV2", "Client module: GuiScaleV2.", Category.Client); }
        @Override public void onUpdate() { BulkModuleLogic.tick(mc, Category.Client, 71); }
    }

    public static class GuiBlurV2 extends Module {
        private final Minecraft mc = Minecraft.getMinecraft();
        public GuiBlurV2() { super("GuiBlurV2", "Client module: GuiBlurV2.", Category.Client); }
        @Override public void onUpdate() { BulkModuleLogic.tick(mc, Category.Client, 72); }
    }

    public static class ModuleSearchV2 extends Module {
        private final Minecraft mc = Minecraft.getMinecraft();
        public ModuleSearchV2() { super("ModuleSearchV2", "Client module: ModuleSearchV2.", Category.Client); }
        @Override public void onUpdate() { BulkModuleLogic.tick(mc, Category.Client, 73); }
    }

    public static class ModuleSortV2 extends Module {
        private final Minecraft mc = Minecraft.getMinecraft();
        public ModuleSortV2() { super("ModuleSortV2", "Client module: ModuleSortV2.", Category.Client); }
        @Override public void onUpdate() { BulkModuleLogic.tick(mc, Category.Client, 74); }
    }
}
