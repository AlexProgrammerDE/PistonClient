package net.pistonmaster.pistonclient.core;

import com.lukflug.panelstudio.settings.NumberSetting;

public class NumberImpl implements NumberSetting {
    private double value;
    private double max;
    private double min;
    private int precision;

    public NumberImpl(double value, double max, double min, int precision) {
        this.value = value;
        this.max = max;
        this.min = min;
        this.precision = precision;
    }

    @Override
    public double getNumber() {
        return value;
    }

    @Override
    public void setNumber(double value) {
        this.value = value;
    }

    @Override
    public double getMaximumValue() {
        return max;
    }

    @Override
    public double getMinimumValue() {
        return min;
    }

    @Override
    public int getPrecision() {
        return precision;
    }
}
