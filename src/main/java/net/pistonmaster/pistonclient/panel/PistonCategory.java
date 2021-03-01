package net.pistonmaster.pistonclient.panel;

import net.pistonmaster.pistonclient.panel.impls.Nameable;
import net.pistonmaster.pistonclient.structure.ModuleManager;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.List;

public class PistonCategory implements Nameable, Iterable<PistonModule> {
    private final ModuleManager.Category category;
    private final List<PistonModule> modules;

    public PistonCategory(ModuleManager.Category category) {
        this.category = category;
        modules = ModuleManager.getByCategory(category);
    }

    @Override
    public String getName() {
        return category.getName();
    }

    @Override
    public String getDescription() {
        return category.getDescription();
    }

    @NotNull
    @Override
    public Iterator<PistonModule> iterator() {
        return modules.iterator();
    }
}
