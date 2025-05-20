package arketec.wandofenlivingment.registration;

import arketec.wandofenlivingment.WandOfEnlivingment;
import arketec.wandofenlivingment.entities.EnlivenedBlockEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;

@EventBusSubscriber(
    modid = WandOfEnlivingment.MODID,
    bus = EventBusSubscriber.Bus.MOD
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
