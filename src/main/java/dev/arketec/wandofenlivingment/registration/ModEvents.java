package dev.arketec.wandofenlivingment.registration;

import dev.arketec.wandofenlivingment.WandOfEnlivingment;
import dev.arketec.wandofenlivingment.entities.EnlivenedBlockEntity;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(
    modid = WandOfEnlivingment.MODID,
    bus = Mod.EventBusSubscriber.Bus.MOD
)
public class ModEvents {

    @SubscribeEvent
    public static void registerEntityAttributes(
        EntityAttributeCreationEvent event
    ) {
        event.put(
            ModEntities.ENLIVENED_BLOCK.get(),
            EnlivenedBlockEntity.createAttributes().build()
        );
    }
}
