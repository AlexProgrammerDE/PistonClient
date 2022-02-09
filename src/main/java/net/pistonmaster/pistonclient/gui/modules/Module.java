package net.pistonmaster.pistonclient.gui.modules;

public abstract class Module {
    private final String name;
    private final String description;
    private boolean activated;

    protected Module(String name, String description) {
        this(name, description, false);
    }

    protected Module(String name, String description, boolean activated) {
        this.name = name;
        this.description = description;
        this.activated = activated;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }
}
