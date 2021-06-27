package net.pistonmaster.pistonclient.gui.modules.combat;

import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemStack;
import net.pistonmaster.pistonclient.gui.modules.Module;

public class AutoTotem extends Module {
    protected AutoTotem(String name, String description) {
        super(name, description);
    }

    public void test() {
        if (MinecraftClient.getInstance().player != null) {
            for (ItemStack item : MinecraftClient.getInstance().player.getInventory().offHand) {

            }
        }
    }
}
