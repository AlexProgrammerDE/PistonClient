package net.pistonmaster.pistonclient.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.widget.AbstractButtonWidget;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import net.pistonmaster.pistonclient.screens.ClientSettingsScreen;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(TitleScreen.class)
public abstract class TitleScreenMixin {
    @Shadow
    @Nullable
    private String splashText;

    @Inject(at = @At("TAIL"), method = "init()V")
    public void setCustomSplash(CallbackInfo info) {
        this.splashText = "PistonClient!";
    }

    @Inject(method = "initWidgetsNormal(II)V", at = @At("TAIL"), locals = LocalCapture.CAPTURE_FAILEXCEPTION)
    public void setupPistonClientButton(int y, int spacingY, CallbackInfo ci, boolean bl, ButtonWidget.TooltipSupplier tooltipSupplier) {
        ((ScreenAccessor) this).addButtonInvoker(new ButtonWidget(((Screen) (Object) this).width / 2 - 100, y + spacingY * 3, 200, 20, Text.of("PistonClient Settings"), buttonWidget ->
                MinecraftClient.getInstance().openScreen(new ClientSettingsScreen(MinecraftClient.getInstance().currentScreen)), tooltipSupplier)
        ).active = bl;
    }

    @ModifyArgs(
            method = "init",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/TitleScreen;addButton(Lnet/minecraft/client/gui/widget/AbstractButtonWidget;)Lnet/minecraft/client/gui/widget/AbstractButtonWidget;")
    )
    private void mixin(Args args) {
        ((AbstractButtonWidget) args.get(0)).y = ((AbstractButtonWidget) args.get(0)).y + 20;
    }
}
