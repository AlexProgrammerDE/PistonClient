package net.pistonmaster.pistonclient.gui;

import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription;
import io.github.cottonmc.cotton.gui.widget.WGridPanel;
import io.github.cottonmc.cotton.gui.widget.WLabel;
import io.github.cottonmc.cotton.gui.widget.WSprite;
import io.github.cottonmc.cotton.gui.widget.WToggleButton;
import lombok.RequiredArgsConstructor;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.pistonmaster.pistonclient.PistonClient;
import net.pistonmaster.pistonclient.discord.MessageTool;

import java.awt.*;

@RequiredArgsConstructor
public class SettingsGUI extends LightweightGuiDescription {
    public SettingsGUI(PistonClient pistonClient) {
        var x = 256;
        var y = 240;
        var root = new WGridPanel();
        setRootPanel(root);
        root.setSize(x, y);

        var icon = new WSprite(new Identifier("minecraft:textures/item/redstone.png"));
        root.add(icon, 5, 1, 1, 1);

        var label = new WLabel(new TranslatableText("gui.pistonclient.settings"), Color.BLACK.getRGB());
        root.add(label, 6, 1, 2, 1);

        var toggleButton = new WToggleButton(new TranslatableText("gui.pistonclient.discordrpc"));
        toggleButton.setToggle(pistonClient.getMessageTool().isActivated());
        toggleButton.setOnToggle(pistonClient.getMessageTool()::setActivated);
        root.add(toggleButton, 8, 3, 4, 1);

        root.validate(this);
    }
}
