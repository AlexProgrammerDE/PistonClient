package net.pistonmaster.pistonclient.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.TitleScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public class TitleScreenMixin {
    @Inject(at = @At("TAIL"), method = "init()V")
    public void setCustomSplash(CallbackInfo info) {
        ((TitleScreenAccessor) MinecraftClient.getInstance().currentScreen).setSplashText("PistonClient!");
    }
}
