package com.zxy.remoteinventory01.mixin;

import com.zxy.remoteinventory01.OpenInventoryPacket;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.BrewingStandBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(BrewingStandBlockEntity.class)
public class MixinBrewingStandBlockEntity extends BlockEntity {
    public MixinBrewingStandBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Overwrite
    public boolean canPlayerUse(PlayerEntity player) {
        for (ServerPlayerEntity player1 : OpenInventoryPacket.playerlist) {
            if(player.equals(player1)) return true;
        }
        if (this.world.getBlockEntity(this.pos) != this) {
            return false;
        } else {
            return !(player.squaredDistanceTo((double)this.pos.getX() + 0.5, (double)this.pos.getY() + 0.5, (double)this.pos.getZ() + 0.5) > 64.0);
        }
    }
}