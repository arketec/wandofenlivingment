package dev.arketec.wandofenlivingment.client.registration;

import dev.arketec.wandofenlivingment.WandOfEnlivingment;
import dev.arketec.wandofenlivingment.client.model.entity.EnlivenedBlockModel;
import dev.arketec.wandofenlivingment.client.renderer.entity.EnlivenedBlockRenderer;
import dev.arketec.wandofenlivingment.registration.ModEntities;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(
    modid = WandOfEnlivingment.MODID,
    bus = Mod.EventBusSubscriber.Bus.MOD,
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
            x -> new EnlivenedBlockRenderer(x)
        );
    }
}
