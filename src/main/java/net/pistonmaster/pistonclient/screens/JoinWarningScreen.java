package net.pistonmaster.pistonclient.screens;

import net.minecraft.class_5489;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ConnectScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ScreenTexts;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.client.util.NarratorManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class JoinWarningScreen extends Screen {
    private static final Text HEADER;

    static {
        HEADER = Text.of("Discord joining warning!").copy().formatted(Formatting.BOLD);
    }

    public final String servername;
    private final Screen parent;
    private final Text message;
    private class_5489 lines;

    public JoinWarningScreen(Screen parent, String serverName) {
        super(NarratorManager.EMPTY);
        this.parent = parent;
        this.servername = serverName;

        message = Text.of("Are you REALLY sure you want to join " + servername + "? We are not responsible if this is a IP logger!");
    }

    @Override
    public void init() {
        super.init();

        this.lines = class_5489.method_30890(this.textRenderer, message, this.width - 50);
        int var10000 = this.lines.method_30887() + 1;
        this.textRenderer.getClass();
        int i = var10000 * 9 * 2;
        this.addButton(new ButtonWidget(this.width / 2 - 155, 100 + i, 150, 20, ScreenTexts.PROCEED, buttonWidget ->
                this.client.openScreen(new ConnectScreen(this, MinecraftClient.getInstance(), new ServerInfo(servername, servername, false)))));

        this.addButton(new ButtonWidget(this.width / 2 - 155 + 160, 100 + i, 150, 20, ScreenTexts.BACK, buttonWidget -> this.client.openScreen(this.parent)));
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackgroundTexture(0);
        drawTextWithShadow(matrices, this.textRenderer, HEADER, 25, 30, 16777215);
        class_5489 var10000 = this.lines;
        this.textRenderer.getClass();
        var10000.method_30893(matrices, 25, 70, 9 * 2, 16777215);
        super.render(matrices, mouseX, mouseY, delta);
    }
}
