package net.pistonmaster.pistonclient.screens;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.MultilineText;
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

    private final Screen parent;
    private final Text message;
    private final Text proceedText;
    private final String serverName;
    private MultilineText lines;

    public JoinWarningScreen(Screen parent) {
        this(parent, "openanarchy.org");
    }

    public JoinWarningScreen(Screen parent, String serverName) {
        super(NarratorManager.EMPTY);
        this.serverName = serverName;
        this.lines = MultilineText.EMPTY;
        this.parent = parent;
        message = Text.of("Are you REALLY sure you want to join " + serverName + "? We are not responsible if this is a IP logger!");
        proceedText = HEADER.shallowCopy().append("\n").append(message);
    }

    @Override
    protected void init() {
        super.init();
        this.lines = MultilineText.create(this.textRenderer, message, this.width - 50);
        int var10000 = this.lines.count() + 1;
        this.textRenderer.getClass();
        int i = var10000 * 9 * 2;

        this.addButton(new ButtonWidget(this.width / 2 - 155, 100 + i, 150, 20, ScreenTexts.PROCEED, buttonWidget -> this.client.openScreen(new ConnectScreen(this.parent, MinecraftClient.getInstance(), new ServerInfo("server", serverName, false)))));
        this.addButton(new ButtonWidget(this.width / 2 - 155 + 160, 100 + i, 150, 20, ScreenTexts.BACK, buttonWidget -> this.client.openScreen(this.parent)));
    }

    @Override
    public String getNarrationMessage() {
        return proceedText.getString();
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackgroundTexture(0);
        drawTextWithShadow(matrices, this.textRenderer, HEADER, 25, 30, 16777215);
        MultilineText var10000 = this.lines;
        this.textRenderer.getClass();
        var10000.drawWithShadow(matrices, 25, 70, 9 * 2, 16777215);
        super.render(matrices, mouseX, mouseY, delta);
    }
}
