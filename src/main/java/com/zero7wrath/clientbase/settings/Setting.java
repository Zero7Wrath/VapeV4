package com.zero7wrath.clientbase.settings;

public abstract class Setting<T> {

    protected final String name;
    protected T value;

    protected Setting(String name, T value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public static class BooleanSetting extends Setting<Boolean> {
        public BooleanSetting(String name, boolean value) {
            super(name, value);
        }

        public void toggle() {
            setValue(!getValue());
        }
    }

    public static class NumberSetting extends Setting<Double> {
        private final double min;
        private final double max;
        private final double increment;

        public NumberSetting(String name, double value, double min, double max, double increment) {
            super(name, value);
            this.min = min;
            this.max = max;
            this.increment = increment;
        }

        public double getMin() {
            return min;
        }

        public double getMax() {
            return max;
        }

        public double getIncrement() {
            return increment;
        }

        @Override
        public void setValue(Double value) {
            if (value == null) {
                return;
            }

            double clamped = Math.max(min, Math.min(max, value));

            if (increment > 0.0) {
                clamped = min + Math.round((clamped - min) / increment) * increment;
                clamped = Math.max(min, Math.min(max, clamped));
            }

            super.setValue(clamped);
        }
    }

    public static class ModeSetting extends Setting<String> {
        private final String[] modes;
        private int index;

        public ModeSetting(String name, String defaultMode, String... modes) {
            super(name, defaultMode);
            this.modes = modes == null ? new String[0] : modes;

            for (int i = 0; i < this.modes.length; i++) {
                if (this.modes[i].equals(defaultMode)) {
                    index = i;
                    break;
                }
            }
        }

        public void cycle() {
            if (modes.length == 0) {
                return;
            }

            index = (index + 1) % modes.length;
            setValue(modes[index]);
        }
    }
}
