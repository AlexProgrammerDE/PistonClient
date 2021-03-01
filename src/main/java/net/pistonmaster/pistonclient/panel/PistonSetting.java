package net.pistonmaster.pistonclient.panel;

import net.pistonmaster.pistonclient.panel.impls.Nameable;

public class PistonSetting implements Nameable {
    private final String name;
    private final String description;

    public PistonSetting(String name, String description) {
        this.name = name;
        this.description = description;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }
}
