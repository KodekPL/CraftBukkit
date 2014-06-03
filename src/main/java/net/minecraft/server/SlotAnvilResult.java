package net.minecraft.server;

import org.bukkit.block.BlockState;
import org.bukkit.event.block.BlockStateChangeEvent;

public class SlotAnvilResult extends Slot {

    final World a;
    final int b;
    final int c;
    final int d;
    final ContainerAnvil e;

    SlotAnvilResult(ContainerAnvil containeranvil, IInventory iinventory, int i, int j, int k, World world, int l, int i1, int j1) {
        super(iinventory, i, j, k);
        this.e = containeranvil;
        this.a = world;
        this.b = l;
        this.c = i1;
        this.d = j1;
    }

    public boolean isAllowed(ItemStack itemstack) {
        return false;
    }

    public boolean isAllowed(EntityHuman entityhuman) {
        return (entityhuman.abilities.canInstantlyBuild || entityhuman.expLevel >= this.e.a) && this.e.a > 0 && this.hasItem();
    }

    public void a(EntityHuman entityhuman, ItemStack itemstack) {
        if (!entityhuman.abilities.canInstantlyBuild) {
            entityhuman.levelDown(-this.e.a);
        }

        ContainerAnvil.a(this.e).setItem(0, (ItemStack) null);
        if (ContainerAnvil.b(this.e) > 0) {
            ItemStack itemstack1 = ContainerAnvil.a(this.e).getItem(1);

            if (itemstack1 != null && itemstack1.count > ContainerAnvil.b(this.e)) {
                itemstack1.count -= ContainerAnvil.b(this.e);
                ContainerAnvil.a(this.e).setItem(1, itemstack1);
            } else {
                ContainerAnvil.a(this.e).setItem(1, (ItemStack) null);
            }
        } else {
            ContainerAnvil.a(this.e).setItem(1, (ItemStack) null);
        }

        this.e.a = 0;
        if (!entityhuman.abilities.canInstantlyBuild && !this.a.isStatic && this.a.getType(this.b, this.c, this.d) == Blocks.ANVIL && entityhuman.aH().nextFloat() < 0.12F) {
            int i = this.a.getData(this.b, this.c, this.d);
            int j = i & 3;
            int k = i >> 2;

            ++k;
            if (k > 2) {
                // CraftBukkit start
                BlockState blockState = this.a.getWorld().getBlockAt(this.b, this.c, this.d).getState();
                blockState.setType(org.bukkit.Material.AIR);

                BlockStateChangeEvent anvilChangeEvent = new BlockStateChangeEvent(blockState.getBlock(), blockState);
                this.a.getServer().getPluginManager().callEvent(anvilChangeEvent);
                if (!anvilChangeEvent.isCancelled()) {
                    blockState.update(true);
                    this.a.triggerEffect(1020, this.b, this.c, this.d, 0);
                }
                // CraftBukkit end
            } else {
                // CraftBukkit start
                BlockState blockState = this.a.getWorld().getBlockAt(this.b, this.c, this.d).getState();
                blockState.setRawData((byte) (j | k << 2));

                BlockStateChangeEvent anvilChangeEvent = new BlockStateChangeEvent(blockState.getBlock(), blockState);
                this.a.getServer().getPluginManager().callEvent(anvilChangeEvent);
                if (!anvilChangeEvent.isCancelled()) {
                    blockState.update(true);
                    this.a.triggerEffect(1021, this.b, this.c, this.d, 0);
                }
                // CraftBukkit end
            }
        } else if (!this.a.isStatic) {
            this.a.triggerEffect(1021, this.b, this.c, this.d, 0);
        }
    }
}