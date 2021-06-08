package net.pistonmaster.pistonclient.core;

import com.lukflug.panelstudio.CollapsibleContainer;
import com.lukflug.panelstudio.DraggableContainer;
import com.lukflug.panelstudio.SettingsAnimation;
import com.lukflug.panelstudio.hud.HUDClickGUI;
import com.lukflug.panelstudio.mc16.MinecraftGUI;
import com.lukflug.panelstudio.mc16.MinecraftHUDGUI;
import com.lukflug.panelstudio.settings.SimpleToggleable;
import com.lukflug.panelstudio.settings.Toggleable;
import com.lukflug.panelstudio.theme.GameSenseTheme;
import com.lukflug.panelstudio.theme.SettingsColorScheme;
import com.lukflug.panelstudio.theme.Theme;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.pistonmaster.pistonclient.core.modules.Module;

import java.awt.*;

public class MainHUD extends MinecraftHUDGUI {
    private final Toggleable colorToggle;
    private final MinecraftGUI.GUIInterface guiInterface;
    private final Theme theme;
    private final HUDClickGUI gui;

    public MainHUD() {
        TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;

        colorToggle = new Toggleable() {
            boolean b = false;

            @Override
            public void toggle() {
                b = !b;
            }

            @Override
            public boolean isOn() {
                return b;
            }
        };

        guiInterface = new MinecraftGUI.GUIInterface(true) {
            @Override
            protected String getResourcePrefix() {
                return "pistonclient:gui/";
            }

            @Override
            public void drawString(Point pos, String s, Color c) {
                end();
                System.out.println(matrixStack);
                System.out.println(s);
                System.out.println(pos);
                System.out.println(c);
                textRenderer.drawWithShadow(matrixStack, s, pos.x, pos.y, parseColor(c));
                begin();
            }

            @Override
            public int getFontWidth(String s) {
                return textRenderer.getWidth(s);
            }

            @Override
            public int getFontHeight() {
                return textRenderer.fontHeight;
            }
        };

        theme = new GameSenseTheme(new SettingsColorScheme(HUDSettings.ACTIVE, HUDSettings.INACTIVE, HUDSettings.BACKGROUND, HUDSettings.OUTLINE, HUDSettings.FONT, HUDSettings.OPACITY), height, 2, 5);
        gui = new HUDClickGUI(guiInterface, null);

        // Populate the ClickGUI with modules and settings
        for (Category category : Category.values()) {
            DraggableContainer panel = new DraggableContainer(category.getName(), category.getDescription(), theme.getPanelRenderer(), new SimpleToggleable(false), new SettingsAnimation(HUDSettings.ANIMATION_SPEED), null, new Point(10, 10), width); // <-- Width and default position of the panels needs to be defined
            gui.addComponent(panel);

            for (Module module : category.getModules()) {
                CollapsibleContainer container = new CollapsibleContainer(module.getName(), module.getDescription(), theme.getContainerRenderer(), new SimpleToggleable(false), new SettingsAnimation(HUDSettings.ANIMATION_SPEED), module.getToggle()); // <-- It is recommended that the module-class implements Toggleable
                panel.addComponent(container);
            }
        }
    }

    @Override
    protected HUDClickGUI getHUDGUI() {
        return gui;
    }

    @Override
    protected MinecraftGUI.GUIInterface getInterface() {
        return guiInterface;
    }

    @Override
    protected int getScrollSpeed() {
        return 10;
    }

    public static int parseColor(final Color color) {
        return 0xFFFFFF & (color.getRGB() >> 8);
    }
}