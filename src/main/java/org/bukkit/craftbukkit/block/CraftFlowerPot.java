package org.bukkit.craftbukkit.block;

import net.minecraft.server.TileEntityFlowerPot;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.FlowerPot;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

public class CraftFlowerPot extends CraftBlockState implements FlowerPot {
    private final TileEntityFlowerPot flowerPot;
    private ItemStack contents;

    public CraftFlowerPot(Block block) {
        super(block);

        flowerPot = (TileEntityFlowerPot) ((CraftWorld) block.getWorld()).getTileEntityAt(getX(), getY(), getZ());
        contents = CraftItemStack.asNewCraftStack(flowerPot.a());
        contents.setDurability((short) flowerPot.b());
    }

    @Override
    public boolean update(boolean force, boolean applyPhysics) {
        boolean result = super.update(force, applyPhysics);

        if (result) {
            flowerPot.a(contents == null ? null : CraftItemStack.asNMSCopy(contents).getItem(), contents == null ? -1 : contents.getDurability());
            flowerPot.update();
        }

        return result;
    }

    public ItemStack getContents() {
        return contents;
    }

    public void setContents(ItemStack itemStack) {
        if (itemStack == null || itemStack.getType() == Material.AIR) {
            contents = null;
        } else {
            contents = itemStack;
        }
    }
}
