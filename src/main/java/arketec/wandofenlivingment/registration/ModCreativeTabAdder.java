package arketec.wandofenlivingment.registration;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;

import static arketec.wandofenlivingment.WandOfEnlivingment.MODID;

@EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD)
public final class ModCreativeTabAdder {
    @SubscribeEvent
    public static void onBuildTabs(net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent e) {
        if (e.getTabKey() == net.minecraft.world.item.CreativeModeTabs.TOOLS_AND_UTILITIES) {
            e.accept(ModItems.FRAGILE_WAND_OF_ENLIVINGMENT.get());
            e.accept(ModItems.WAND_OF_ENLIVINGMENT.get());
            e.accept(ModItems.GREATER_WAND_OF_ENLIVINGMENT.get());
            e.accept(ModItems.CREATIVE_WAND_OF_ENLIVINGMENT.get());
        }
    }
}
