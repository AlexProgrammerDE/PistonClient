package net.pistonmaster.pistonclient.gui.modules;

import lombok.Getter;
import lombok.Setter;

@Getter
public abstract class Module {
    private final String name;
    private final String description;
    @Setter
    private boolean activated;

    protected Module(String name, String description) {
        this(name, description, false);
    }

    protected Module(String name, String description, boolean activated) {
        this.name = name;
        this.description = description;
        this.activated = activated;
    }
}
