package dev.arketec.wandofenlivingment.items;

import dev.arketec.wandofenlivingment.configuration.ModConfig;
import dev.arketec.wandofenlivingment.entities.EnlivenedBlockEntity;
import dev.arketec.wandofenlivingment.registration.ModEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoublePlantBlock;
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
                ModConfig.blockDenylist
                    .get()
                    .contains(block.getRegistryName().toString())
            ) {
                return InteractionResult.FAIL;
            }
            if (
                !level.isClientSide() &&
                block != null &&
                canAnimate(state, level, pos)
            ) {
                EnlivenedBlockEntity enlivenedBlockEntity =
                    ModEntities.ENLIVENED_BLOCK.get().create(level);
                level.removeBlock(pos, false);

                enlivenedBlockEntity.setBlock(block);

                enlivenedBlockEntity.setPos(pos.getX(), pos.getY(), pos.getZ());
                level.addFreshEntity(enlivenedBlockEntity);
                level.playSound(
                    null,
                    pos,
                    SoundEvents.AMETHYST_BLOCK_CHIME,
                    SoundSource.AMBIENT,
                    0.2F,
                    1.0F
                );
                stack.setDamageValue(stack.getDamageValue() - 1);
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.FAIL;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(
        Level level,
        Player player,
        InteractionHand hand
    ) {
        return super.use(level, player, hand);
    }

    private Boolean isBlacklisted(Block block) {
        return false;
    }

    private boolean canAnimate(BlockState state, Level world, BlockPos pos) {
        return (
            !isBlacklisted(state.getBlock()) &&
            !(state.getBlock() instanceof DoublePlantBlock) &&
            !(state.getBlock() instanceof Container) &&
            state.getMaterial().isSolid() &&
            state.isCollisionShapeFullBlock(world, pos)
        );
    }
}
