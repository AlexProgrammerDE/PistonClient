package net.pistonmaster.pistonclient.screens;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ScreenTexts;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

public class ClientSettingsScreen extends Screen {
    private final Screen parent;

    public ClientSettingsScreen(Screen parent) {
        super(Text.of("PistonClient Settings"));
        this.parent = parent;
        this.height = this.height * 2;
    }

    @Override
    protected void init() {
        this.addButton(new ButtonWidget(this.width / 2 - 155, this.height / 6 + 48 - 6, 150, 20, new TranslatableText("options.skinCustomisation"), button ->
                this.client.openScreen(new ClientSettingsScreen(this))));
        this.addButton(new ButtonWidget(this.width / 2 + 5, this.height / 6 + 48 - 6, 150, 20, new TranslatableText("options.sounds"), button ->
                this.client.openScreen(new ClientSettingsScreen(this))));
        this.addButton(new ButtonWidget(this.width / 2 - 155, this.height / 6 + 72 - 6, 150, 20, new TranslatableText("options.video"), button ->
                this.client.openScreen(new ClientSettingsScreen(this))));
        this.addButton(new ButtonWidget(this.width / 2 + 5, this.height / 6 + 72 - 6, 150, 20, new TranslatableText("options.controls"), button ->
                this.client.openScreen(new ClientSettingsScreen(this))));
        this.addButton(new ButtonWidget(this.width / 2 - 155, this.height / 6 + 96 - 6, 150, 20, new TranslatableText("options.language"), button ->
                this.client.openScreen(new ClientSettingsScreen(this))));
        this.addButton(new ButtonWidget(this.width / 2 + 5, this.height / 6 + 96 - 6, 150, 20, new TranslatableText("options.chat.title"), button ->
                this.client.openScreen(new ClientSettingsScreen(this))));
        this.addButton(new ButtonWidget(this.width / 2 + 5, this.height / 6 + 120 - 6, 150, 20, new TranslatableText("options.accessibility.title"), button ->
                this.client.openScreen(new ClientSettingsScreen(this))));
        this.addButton(new ButtonWidget(this.width / 2 - 100, this.height / 6 + 168, 200, 20, ScreenTexts.DONE, (button) ->
                this.client.openScreen(this.parent)));
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        drawCenteredText(matrices, this.textRenderer, this.title, this.width / 2, 15, 16777215);
        super.render(matrices, mouseX, mouseY, delta);
    }
}
