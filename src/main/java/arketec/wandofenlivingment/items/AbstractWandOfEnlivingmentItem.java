package arketec.wandofenlivingment.items;

import arketec.wandofenlivingment.configuration.ModCommonConfig;
import arketec.wandofenlivingment.entities.EnlivenedBlockEntity;
import arketec.wandofenlivingment.registration.ModEntities;
import arketec.wandofenlivingment.util.BlockDenylist;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.state.BlockState;

public abstract class AbstractWandOfEnlivingmentItem extends Item {

    public AbstractWandOfEnlivingmentItem(Properties properties) {
        super(properties);
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

            if (!level.isClientSide() && block != null && isAllowedToEnliven(state, level, pos)) {
                EnlivenedBlockEntity enlivenedBlockEntity =
                        ModEntities.ENLIVENED_BLOCK.get().create(level);
                level.removeBlock(pos, false);

                enlivenedBlockEntity.setBlockEnlivened(block);
                enlivenedBlockEntity.setPos(pos.getX(), pos.getY(), pos.getZ());

                level.addFreshEntity(enlivenedBlockEntity);
                level.playSound(null, pos, SoundEvents.AMETHYST_BLOCK_CHIME, SoundSource.HOSTILE, 0.2F, 1.0F);

                if (stack.getMaxDamage() > 0 && stack.getDamageValue() >= stack.getMaxDamage()) {
                    stack.setCount(0);
                    level.playSound(null, pos, SoundEvents.ITEM_BREAK, SoundSource.NEUTRAL, 0.2F, 1.0F);
                    stack.hurtAndBreak(1, context.getPlayer(), LivingEntity.getSlotForHand(context.getHand()));
                    return InteractionResult.PASS;
                }
                if (!context.getPlayer().isCreative()) stack.setDamageValue(stack.getDamageValue() + 1);
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.FAIL;
    }

    public boolean canApplyMending() {
        return isAllowedMending();
    }

    public boolean canRepair() {
        return isAllowedRepair();
    }

    protected boolean isNotInDenyList(Block block) {
        var blockName = BuiltInRegistries.BLOCK.getKey(block).toString();
        var denylist = BlockDenylist.fromConfig(ModCommonConfig.blockDenylist.get());
        return !denylist.matches(blockName);
    }

    protected boolean isAllowedBlockEntity(Block block) {
        var blockName = BuiltInRegistries.BLOCK.getKey(block).toString();
        return (ModCommonConfig.allowBlockEntities.get()
                || ModCommonConfig.blockEntityAllowlist.get().stream().toList().contains(blockName)
                || !(block instanceof EntityBlock));
    }

    protected boolean isExplicitlyDenied(Block block) {
        return (block instanceof DoublePlantBlock);
    }

    protected boolean isFullBlock(BlockState state, Level level, BlockPos pos) {
        return (state.isSolid() && state.isCollisionShapeFullBlock(level, pos));
    }

    @Override
    public abstract boolean isEnchantable(ItemStack stack);

    @Override
    public abstract boolean isBookEnchantable(ItemStack stack, ItemStack book);

    @Override
    public abstract boolean supportsEnchantment(ItemStack stack, Holder<Enchantment> enchantment);

    protected boolean isAllowedToEnliven(BlockState state, Level level, BlockPos pos) {
        return (isNotInDenyList(state.getBlock())
                && isAllowedBlockEntity(state.getBlock())
                && isFullBlock(state, level, pos)
                && !isExplicitlyDenied(state.getBlock()));
    }

    protected boolean isAllowedMending() {
        return ModCommonConfig.allowMending.get();
    }

    protected boolean isAllowedRepair() {
        return ModCommonConfig.allowRepair.get();
    }
}
