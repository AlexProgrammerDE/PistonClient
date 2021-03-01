package net.pistonmaster.pistonclient.mixin;

import net.minecraft.client.gui.screen.multiplayer.MultiplayerServerListWidget;
import net.minecraft.client.network.ServerInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(MultiplayerServerListWidget.ServerEntry.class)
public interface ServerEntryAccessor {
    @Accessor("server")
    ServerInfo getServer();
}
