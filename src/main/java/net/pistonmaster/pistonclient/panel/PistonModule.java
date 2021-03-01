package net.pistonmaster.pistonclient.panel;

import com.lukflug.panelstudio.settings.KeybindSetting;
import com.lukflug.panelstudio.settings.Toggleable;
import net.pistonmaster.pistonclient.panel.impls.Nameable;
import net.pistonmaster.pistonclient.structure.ModuleManager;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class PistonModule implements Nameable, Iterable<PistonSetting>, Toggleable {
    private final ModuleManager.Module module;
    private final List<PistonSetting> settings = new ArrayList<>();

    public PistonModule(ModuleManager.Module module) {
        this.module = module;
    }

    @Override
    public String getName() {
        return module.getName();
    }

    @Override
    public String getDescription() {
        return module.getDescription();
    }

    public ModuleManager.Category getCategory() {
        return module.getCategory();
    }

    @NotNull
    @Override
    public Iterator<PistonSetting> iterator() {
        return settings.iterator();
    }

    public abstract KeybindSetting getKeybind();
}
