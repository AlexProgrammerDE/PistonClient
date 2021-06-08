package net.pistonmaster.pistonclient.core;

import net.pistonmaster.pistonclient.core.modules.Module;
import net.pistonmaster.pistonclient.core.modules.test.TestModule;

import java.util.Arrays;
import java.util.List;

public enum Category {
    TEST("Test", "test", new TestModule());

    private final String name;
    private final String description;
    private final List<Module> modules;

    Category(String name, String description, Module... modules) {
        this.name = name;
        this.description = description;
        this.modules = Arrays.asList(modules);
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<Module> getModules() {
        return modules;
    }
}
