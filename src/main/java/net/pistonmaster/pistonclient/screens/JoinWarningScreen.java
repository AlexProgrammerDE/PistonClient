package net.pistonmaster.pistonclient.screens;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.MultilineText;
import net.minecraft.client.gui.screen.ConnectScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ScreenTexts;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.network.ServerAddress;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.client.util.NarratorManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.pistonmaster.pistonclient.data.ServerJoinRequest;

public class JoinWarningScreen extends Screen {
    private static final Text HEADER;

    static {
        HEADER = Text.of("Discord joining warning!").copy().formatted(Formatting.BOLD);
    }

    private final Screen parent;
    private final Text message;
    private final Text proceedText;
    private final ServerJoinRequest request;
    private MultilineText lines;

    public JoinWarningScreen(Screen parent) {
        this(parent, new ServerJoinRequest("openanarchy.org", 25565));
    }

    public JoinWarningScreen(Screen parent, ServerJoinRequest request) {
        super(NarratorManager.EMPTY);
        this.request = request;
        this.lines = MultilineText.EMPTY;
        this.parent = parent;
        message = Text.of("Are you REALLY sure you want to join " + request.getServer() + "? We are not responsible if this is a IP logger!");
        proceedText = HEADER.shallowCopy().append("\n").append(message);
    }

    @Override
    protected void init() {
        super.init();
        this.lines = MultilineText.create(this.textRenderer, message, this.width - 50);
        int var10000 = this.lines.count() + 1;
        int i = var10000 * 9 * 2;

        this.addDrawableChild(new ButtonWidget(this.width / 2 - 155, 100 + i, 150, 20, ScreenTexts.PROCEED, buttonWidget -> ConnectScreen.connect(this.parent, MinecraftClient.getInstance(), ServerAddress.parse(request.format()), new ServerInfo("server", request.format(), false))));
        this.addDrawableChild(new ButtonWidget(this.width / 2 - 155 + 160, 100 + i, 150, 20, ScreenTexts.BACK, buttonWidget -> this.client.setScreen(this.parent)));
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackgroundTexture(0);
        drawTextWithShadow(matrices, this.textRenderer, HEADER, 25, 30, 16777215);
        MultilineText var10000 = this.lines;
        var10000.drawWithShadow(matrices, 25, 70, 9 * 2, 16777215);
        super.render(matrices, mouseX, mouseY, delta);
    }
}
