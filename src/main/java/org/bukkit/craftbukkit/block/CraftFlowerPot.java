package org.bukkit.craftbukkit.block;

import net.minecraft.server.TileEntityFlowerPot;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.FlowerPot;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

public class CraftFlowerPot extends CraftBlockState implements FlowerPot {
    private final TileEntityFlowerPot flowerPot;
    private MaterialData contents;

    public CraftFlowerPot(Block block) {
        super(block);

        flowerPot = (TileEntityFlowerPot) ((CraftWorld) block.getWorld()).getTileEntityAt(getX(), getY(), getZ());
        contents = CraftItemStack.asNewCraftStack(flowerPot.a()).getData();
        contents.setData((byte) flowerPot.b());
    }

    @Override
    public boolean update(boolean force, boolean applyPhysics) {
        boolean result = super.update(force, applyPhysics);

        if (result) {
            flowerPot.a(CraftItemStack.asNMSCopy(contents.toItemStack(1)).getItem(), contents.getData());
            flowerPot.update();
        }

        return result;
    }

    public MaterialData getContents() {
        return contents;
    }

    public void setContents(MaterialData materialData) {
        if (materialData == null) {
            contents = new MaterialData(Material.AIR);
        } else {
            contents = materialData;
        }
    }
}
