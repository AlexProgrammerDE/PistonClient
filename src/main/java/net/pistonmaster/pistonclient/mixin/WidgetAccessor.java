package net.pistonmaster.pistonclient.mixin;

import net.minecraft.client.gui.screen.multiplayer.MultiplayerServerListWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(MultiplayerServerListWidget.class)
public interface WidgetAccessor {
    @Accessor("servers")
    public List<MultiplayerServerListWidget.ServerEntry> getServers();
}
