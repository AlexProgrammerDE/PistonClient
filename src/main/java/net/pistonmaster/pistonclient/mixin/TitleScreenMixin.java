package net.pistonmaster.pistonclient.mixin;

import net.minecraft.client.gui.screen.TitleScreen;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public abstract class TitleScreenMixin {
    @Shadow
    @Nullable
    private String splashText;

    @Inject(at = @At("TAIL"), method = "init()V")
    public void setCustomSplash(CallbackInfo info) {
        this.splashText = "PistonClient!";
    }
}
