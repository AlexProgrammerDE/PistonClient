package net.pistonmaster.pistonclient.structure;

import net.pistonmaster.pistonclient.panel.PistonModule;
import net.pistonmaster.pistonclient.panel.impls.Nameable;
import net.pistonmaster.pistonclient.structure.modules.HUDModule;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ModuleManager {
    public static final List<PistonModule> registry = new ArrayList<>();

    static {
        registry.add(new HUDModule());
    }

    public static PistonModule getByName(Module module) {
        return registry.stream().filter(pistonModule -> pistonModule.getName().equals(module.getName())).findFirst().get();
    }

    public static List<PistonModule> getByCategory(Category category) {
        return registry.stream().filter(pistonModule -> pistonModule.getCategory().equals(category)).collect(Collectors.toList());
    }

    public enum Module implements Nameable {
        HUD("HUD Module", "Settings for the HUD", Category.CLIENT);

        private final String name;
        private final String description;
        private final Category category;

        Module(String name, String description, Category category) {
            this.name = name;
            this.description = description;
            this.category = category;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public String getDescription() {
            return description;
        }

        public Category getCategory() {
            return category;
        }
    }

    public enum Category implements Nameable {
        CLIENT("ClientCategory", "Settings for the client!");

        private final String name;
        private final String description;

        Category(String name, String description) {
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
}
