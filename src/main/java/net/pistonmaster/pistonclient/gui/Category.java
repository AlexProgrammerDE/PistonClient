package net.pistonmaster.pistonclient.gui;

import lombok.Getter;
import net.pistonmaster.pistonclient.gui.modules.Module;
import net.pistonmaster.pistonclient.gui.modules.test.TestModule;

import java.util.Arrays;
import java.util.List;

@Getter
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
}
