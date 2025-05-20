package arketec.wandofenlivingment.registration;

import arketec.wandofenlivingment.items.WandOfEnlivingmentItem;
import java.util.Map;
import java.util.Objects;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.AnvilUpdateEvent;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.GAME)
public class ForgeEvents {

    @SubscribeEvent
    public static void onAnvilUpdate(AnvilUpdateEvent event) {
        ItemStack left = event.getLeft();
        ItemStack right = event.getRight();
        if (left != null && right != null) {
            if (right.getItem() == Items.ENCHANTED_BOOK) {
                var enchantments = EnchantmentHelper.getEnchantmentsForCrafting(
                    right
                );
                var enchantmentKeySet = enchantments.keySet()
                        .stream()
                        .map(x -> x.unwrapKey().isPresent() ? x.unwrapKey().get(): null)
                        .filter(Objects::nonNull)
                        .toList();
                if (enchantmentKeySet.contains(Enchantments.MENDING) &&
                    left.getItem() instanceof WandOfEnlivingmentItem wandOfEnlivingmentItem &&
                    !wandOfEnlivingmentItem.canApplyMending()
                ) {
                    event.setCanceled(true);
                }
            }
        }
    }
}
