package arketec.wandofenlivingment.registration;

import arketec.wandofenlivingment.items.AbstractWandOfEnlivingmentItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.AnvilUpdateEvent;

import java.util.Objects;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.GAME)
public class ForgeEvents {

    @SubscribeEvent
    public static void onAnvilUpdate(AnvilUpdateEvent event) {
        ItemStack left = event.getLeft();
        ItemStack right = event.getRight();
        if (left.getItem() instanceof AbstractWandOfEnlivingmentItem wandOfEnlivingmentItem) {
            if (!wandOfEnlivingmentItem.canRepair()) {
                event.setCanceled(true);
            }
        }
        if (right.getItem() == Items.ENCHANTED_BOOK) {
            var enchantments = EnchantmentHelper.getEnchantmentsForCrafting(right);
            var enchantmentKeySet = enchantments.keySet().stream()
                    .map(x -> x.unwrapKey().isPresent() ? x.unwrapKey().get() : null)
                    .filter(Objects::nonNull)
                    .toList();
            if (enchantmentKeySet.contains(Enchantments.MENDING)
                    && left.getItem() instanceof AbstractWandOfEnlivingmentItem wandOfEnlivingmentItem
                    && !wandOfEnlivingmentItem.canApplyMending()) {
                event.setCanceled(true);
            }
        }
    }
}
