package arketec.wandofenlivingment.client.registration;

import arketec.wandofenlivingment.WandOfEnlivingment;
import arketec.wandofenlivingment.client.model.entity.EnlivenedBlockModel;
import arketec.wandofenlivingment.client.renderer.entity.EnlivenedBlockRenderer;
import arketec.wandofenlivingment.registration.ModEntities;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@EventBusSubscriber(
    modid = WandOfEnlivingment.MODID,
    bus = EventBusSubscriber.Bus.MOD,
    value = Dist.CLIENT
)
public class ModClientEvents {

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void registerLayerDefinitions(
        EntityRenderersEvent.RegisterLayerDefinitions event
    ) {
        event.registerLayerDefinition(
            EnlivenedBlockModel.LAYER_LOCATION,
            EnlivenedBlockModel::createBodyLayer
        );
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void registerEntityRenderer(
        EntityRenderersEvent.RegisterRenderers event
    ) {
        event.registerEntityRenderer(
            ModEntities.ENLIVENED_BLOCK.get(),
                EnlivenedBlockRenderer::new
        );
    }
}
