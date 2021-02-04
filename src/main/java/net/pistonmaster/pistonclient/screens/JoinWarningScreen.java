package net.pistonmaster.pistonclient.screens;

import net.minecraft.class_5489;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ScreenTexts;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.CheckboxWidget;
import net.minecraft.client.util.NarratorManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;

public class JoinWarningScreen extends Screen {
    private final Screen parent;
    private static final Text HEADER;
    private static final Text MESSAGE;
    private static final Text CHECK_MESSAGE;
    private static final Text PROCEED_TEXT;
    private CheckboxWidget checkbox;
    public final String servername;

    public JoinWarningScreen(Screen parent, String serverName) {
        super(NarratorManager.EMPTY);
        this.parent = parent;
        this.servername = serverName;
    }

    protected void init() {
        super.init();

        int i = (class_5489.method_30890(this.textRenderer, MESSAGE, this.width - 50).method_30887() + 1)  * 9 * 2;
        this.addButton(new ButtonWidget(this.width / 2 - 155, 100 + i, 150, 20, ScreenTexts.PROCEED, buttonWidget -> {
            this.client.openScreen(new MultiplayerScreen(this.parent));
        }));

        this.addButton(new ButtonWidget(this.width / 2 - 155 + 160, 100 + i, 150, 20, ScreenTexts.BACK, buttonWidget -> this.client.openScreen(this.parent)));
        this.checkbox = new CheckboxWidget(this.width / 2 - 155 + 80, 76 + i, 150, 20, CHECK_MESSAGE, false);
        this.addButton(this.checkbox);
    }

    @Override
    public String getNarrationMessage() {
        return PROCEED_TEXT.getString();
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackgroundTexture(0);

        drawTextWithShadow(matrices, this.textRenderer, HEADER, 25, 30, 16777215);
        class_5489.field_26528.method_30893(matrices, 25, 70, 9 * 2, 16777215);

        super.render(matrices, mouseX, mouseY, delta);
    }

    static {
        HEADER = Text.of("Discord joining warning!").copy().formatted(Formatting.BOLD);
        MESSAGE = new TranslatableText("multiplayerWarning.message");
        CHECK_MESSAGE = Text.of("I am aware of the risk!");
        PROCEED_TEXT = HEADER.shallowCopy().append("\n").append(MESSAGE);
    }
}
