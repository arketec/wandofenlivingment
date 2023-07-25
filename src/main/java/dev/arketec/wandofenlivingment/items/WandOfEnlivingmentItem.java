package dev.arketec.wandofenlivingment.items;

import dev.arketec.wandofenlivingment.configuration.ModConfig;
import dev.arketec.wandofenlivingment.entities.EnlivenedBlockEntity;
import dev.arketec.wandofenlivingment.registration.ModEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.state.BlockState;

public class WandOfEnlivingmentItem extends Item {

    public WandOfEnlivingmentItem() {
        super(
            new Properties()
                .tab(CreativeModeTab.TAB_TOOLS)
                .durability(64)
                .setNoRepair()
                .rarity(Rarity.EPIC)
        );
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        ItemStack stack = context.getPlayer().getItemInHand(context.getHand());
        BlockPos pos = context.getClickedPos();
        Direction direction = context.getClickedFace();
        if (!context.getPlayer().mayUseItemAt(pos, direction, stack)) {
            return InteractionResult.FAIL;
        } else {
            Level level = context.getLevel();
            BlockState state = level.getBlockState(pos);
            Block block = state.getBlock();

            if (
                !level.isClientSide() &&
                block != null &&
                isAllowedToEnliven(state, level, pos)
            ) {
                EnlivenedBlockEntity enlivenedBlockEntity =
                    ModEntities.ENLIVENED_BLOCK.get().create(level);
                level.removeBlock(pos, false);

                enlivenedBlockEntity.setBlockEnlivened(block);
                enlivenedBlockEntity.setPos(pos.getX(), pos.getY(), pos.getZ());

                level.addFreshEntity(enlivenedBlockEntity);
                level.playSound(
                    null,
                    pos,
                    SoundEvents.AMETHYST_BLOCK_CHIME,
                    SoundSource.HOSTILE,
                    0.2F,
                    1.0F
                );

                if (!context.getPlayer().isCreative()) stack.setDamageValue(
                    stack.getDamageValue() + 1
                );
                if (stack.getDamageValue() > stack.getMaxDamage()) {
                    stack.setCount(0);
                    level.playSound(
                        null,
                        pos,
                        SoundEvents.ITEM_BREAK,
                        SoundSource.NEUTRAL,
                        0.2F,
                        1.0F
                    );
                }
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.FAIL;
    }

    private boolean isInDenyList(Block block) {
        return ModConfig.blockDenylist
            .get()
            .contains(block.getRegistryName().toString());
    }

    private boolean isAllowedBlockEntity(Block block) {
        return (
            ModConfig.allowBlockEntities.get() ||
            ModConfig.blockAllowlist
                .get()
                .contains(block.getRegistryName().toString()) ||
            !(block instanceof EntityBlock)
        );
    }

    private boolean isExplicitlyDenied(Block block) {
        return (block instanceof DoublePlantBlock);
    }

    private boolean isFullBlock(BlockState state, Level level, BlockPos pos) {
        return (
            state.getMaterial().isSolid() &&
            state.isCollisionShapeFullBlock(level, pos)
        );
    }

    private boolean isAllowedToEnliven(
        BlockState state,
        Level level,
        BlockPos pos
    ) {
        return (
            !isInDenyList(state.getBlock()) &&
            isAllowedBlockEntity(state.getBlock()) &&
            isFullBlock(state, level, pos) &&
            !isExplicitlyDenied(state.getBlock())
        );
    }
}
