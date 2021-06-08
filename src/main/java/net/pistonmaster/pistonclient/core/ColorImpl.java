package net.pistonmaster.pistonclient.core;

import com.lukflug.panelstudio.settings.ColorSetting;

import java.awt.*;

public class ColorImpl implements ColorSetting {
    private Color color;
    private boolean rainbow;

    public ColorImpl(Color color) {
        this(color, false);
    }

    public ColorImpl(Color color, boolean rainbow) {
        this.color = color;
        this.rainbow = rainbow;
    }

    @Override
    public Color getValue() {
        return color;
    }

    @Override
    public void setValue(Color value) {
        this.color = value;
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public boolean getRainbow() {
        return rainbow;
    }

    @Override
    public void setRainbow(boolean rainbow) {
        this.rainbow = rainbow;
    }
}
