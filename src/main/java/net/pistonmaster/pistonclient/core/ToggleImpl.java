package net.pistonmaster.pistonclient.core;

import com.lukflug.panelstudio.settings.Toggleable;

public class ToggleImpl implements Toggleable {
    private boolean value;

    public ToggleImpl() {
        this(false);
    }

    public ToggleImpl(boolean value) {
        this.value = value;
    }

    @Override
    public void toggle() {
        value = !value;
    }

    @Override
    public boolean isOn() {
        return value;
    }
}
