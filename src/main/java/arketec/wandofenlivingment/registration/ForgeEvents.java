package arketec.wandofenlivingment.registration;

import arketec.wandofenlivingment.items.WandOfEnlivingmentItem;
import java.util.Map;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ForgeEvents {

    @SubscribeEvent
    public static void onAnvilUpdate(AnvilUpdateEvent event) {
        ItemStack left = event.getLeft();
        ItemStack right = event.getRight();
        if (left != null && right != null) {
            if (right.getItem() == Items.ENCHANTED_BOOK) {
                Map<Enchantment, Integer> enchantments =
                    EnchantmentHelper.getEnchantments(right);
                if (
                    enchantments.containsKey(Enchantments.MENDING) &&
                    left.getItem() instanceof WandOfEnlivingmentItem wandOfEnlivingmentItem &&
                    !wandOfEnlivingmentItem.canApplyMending()
                ) {
                    event.setCanceled(true);
                }
            }
        }
    }
}
