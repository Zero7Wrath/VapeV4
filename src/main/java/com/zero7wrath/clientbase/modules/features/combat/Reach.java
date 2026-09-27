package com.zero7wrath.clientbase.modules.features.combat;

import com.zero7wrath.clientbase.Category;
import com.zero7wrath.clientbase.modules.Module;
import com.zero7wrath.clientbase.settings.Setting;

public class Reach extends Module {
    public final Setting.NumberSetting distance =
            new Setting.NumberSetting("Distance", 6.0D, 3.0D, 10.0D, 0.5D);

    public Reach() {
        super("Reach", "Extends the local ray-trace distance without custom packet code.", Category.Combat);
        settings.add(distance);
    }

    public double getDistance() {
        return distance.getValue();
    }
}
