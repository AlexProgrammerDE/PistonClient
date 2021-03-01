package net.pistonmaster.pistonclient.screens;

import com.lukflug.panelstudio.ClickGUI;
import com.lukflug.panelstudio.CollapsibleContainer;
import com.lukflug.panelstudio.DraggableContainer;
import com.lukflug.panelstudio.SettingsAnimation;
import com.lukflug.panelstudio.mc16.MinecraftGUI;
import com.lukflug.panelstudio.settings.*;
import com.lukflug.panelstudio.theme.SettingsColorScheme;
import com.lukflug.panelstudio.theme.Theme;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.pistonmaster.pistonclient.panel.*;
import net.pistonmaster.pistonclient.panel.impls.PistonColorSetting;
import net.pistonmaster.pistonclient.structure.ModuleManager;
import net.pistonmaster.pistonclient.structure.categories.ClientCategory;
import net.pistonmaster.pistonclient.structure.modules.HUDModule;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MainScreen extends MinecraftGUI {
    private final MinecraftGUI.GUIInterface guiInterface = new GUIInterface(true) {
        private final TextRenderer minecraftRenderer = MinecraftClient.getInstance().textRenderer;

        @Override
        protected String getResourcePrefix() {
            return "pistonclient:gui/";
        }

        @Override
        public void drawString(Point pos, String s, Color c) {
            end();
            minecraftRenderer.drawWithShadow(matrixStack, s, pos.x, pos.y, c.getRGB());
            begin();
        }

        @Override
        public int getFontWidth(String s) {
            return minecraftRenderer.getWidth(s);
        }

        @Override
        public int getFontHeight() {
            return minecraftRenderer.fontHeight;
        }
    };

    private final ClickGUI gui = new ClickGUI(guiInterface, new PistonDescriptionRenderer());

    private final HUDModule hudModule = (HUDModule) ModuleManager.getByName(ModuleManager.Module.HUD);

    public MainScreen() {
        List<PistonCategory> categories = new ArrayList<>();
        categories.add(new ClientCategory());

        Toggleable colorToggle = hudModule.rgbhsbToggle;

        Theme theme = new PistonTheme(new SettingsColorScheme(hudModule.activeColor, hudModule.inactiveColor, hudModule.backgroundColor, hudModule.outlineColor, hudModule.fontColor, hudModule.opacity), height, 2, 5);

        // Populate the ClickGUI with modules and settings
        for (PistonCategory category : categories) {
            DraggableContainer panel = new DraggableContainer(category.getName(), category.getDescription(), theme.getPanelRenderer(), new SimpleToggleable(false), new SettingsAnimation(hudModule.animationSpeed), null, new Point(1, 1), width);
            gui.addComponent(panel);

            for (PistonModule module : category) {
                CollapsibleContainer container = new CollapsibleContainer(module.getName(), module.getDescription(), theme.getContainerRenderer(), new SimpleToggleable(false), new SettingsAnimation(hudModule.animationSpeed), module);
                panel.addComponent(container);

                for (PistonSetting setting : module) {
                    if (setting instanceof Toggleable) {
                        container.addComponent(new BooleanComponent(setting.getName(), setting.getDescription(), theme.getComponentRenderer(), (Toggleable) setting));
                    } else if (setting instanceof NumberSetting) {
                        NumberSetting numberSetting = (NumberSetting) setting;

                        container.addComponent(new NumberComponent(setting.getName(), setting.getDescription(), theme.getComponentRenderer(), (NumberSetting) setting, numberSetting.getMinimumValue(), numberSetting.getMaximumValue()));
                    } else if (setting instanceof EnumSetting) {
                        container.addComponent(new EnumComponent(setting.getName(), setting.getDescription(), theme.getComponentRenderer(), (EnumSetting) setting));
                    } else if (setting instanceof PistonColorSetting) {
                        PistonColorSetting colorSetting = (PistonColorSetting) setting;

                        container.addComponent(new ColorComponent(setting.getName(), setting.getDescription(), theme.getContainerRenderer(), new SettingsAnimation(hudModule.animationSpeed), theme.getComponentRenderer(), (ColorSetting) setting, colorSetting.isAlphaEnabled(), colorSetting.isRainbowEnabled(), colorToggle));
                    }
                }

                if (module.getKeybind() != null) {
                    container.addComponent(new KeybindComponent(theme.getComponentRenderer(), module.getKeybind()));
                }
            }
        }
    }

    @Override
    protected ClickGUI getGUI() {
        return gui;
    }

    @Override
    protected GUIInterface getInterface() {
        return guiInterface;
    }

    @Override
    protected int getScrollSpeed() {
        return (int) hudModule.scrollSpeed.getNumber();
    }
}