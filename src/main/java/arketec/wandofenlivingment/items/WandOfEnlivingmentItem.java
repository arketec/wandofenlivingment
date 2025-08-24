package arketec.wandofenlivingment.items;

import arketec.wandofenlivingment.configuration.ModCommonConfig;
import arketec.wandofenlivingment.configuration.ModStartupConfig;
import arketec.wandofenlivingment.util.BlockDenylist;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;

public class WandOfEnlivingmentItem extends AbstractWandOfEnlivingmentItem {

    public WandOfEnlivingmentItem() {
        super(new Properties()
                .durability(ModStartupConfig.wandDurability.get())
                .setNoRepair()
                .stacksTo(1)
                .rarity(Rarity.RARE));
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return true;
    }

    @Override
    public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
        return true;
    }

    @Override
    public boolean supportsEnchantment(ItemStack stack, Holder<Enchantment> enchantment) {
        return true;
    }

    @Override
    protected boolean isAllowedMending() {
        return super.isAllowedMending()
                || ModCommonConfig.wandConfig.allowMending().get();
    }

    @Override
    protected boolean isAllowedBlockEntity(Block block) {
        return super.isAllowedBlockEntity(block) || isAllowedBlock(block);
    }

    @Override
    protected boolean isNotInDenyList(Block block) {
        var blockName = BuiltInRegistries.BLOCK.getKey(block).toString();
        var denylist = BlockDenylist.fromConfig(
                ModCommonConfig.wandConfig.blockDenylist().get());

        return super.isNotInDenyList(block) && !denylist.matches(blockName);
    }

    private boolean isAllowedBlock(Block block) {
        var blockName = BuiltInRegistries.BLOCK.getKey(block).toString();
        return (ModCommonConfig.wandConfig.allowBlockEntities().get()
                || ModCommonConfig.wandConfig.blockAllowlist().get().stream()
                        .toList()
                        .contains(blockName)
                || !(block instanceof EntityBlock));
    }
}
