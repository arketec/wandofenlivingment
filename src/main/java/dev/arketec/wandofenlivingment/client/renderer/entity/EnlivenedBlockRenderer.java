package dev.arketec.wandofenlivingment.client.renderer.entity;

import dev.arketec.wandofenlivingment.client.model.entity.EnlivenedBlockModel;
import dev.arketec.wandofenlivingment.entities.EnlivenedBlockEntity;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class EnlivenedBlockRenderer
    extends MobRenderer<EnlivenedBlockEntity, EnlivenedBlockModel<EnlivenedBlockEntity>> {

    private static ConcurrentHashMap resourceCache = new ConcurrentHashMap();

    public EnlivenedBlockRenderer(EntityRendererProvider.Context context) {
        super(
            context,
            new EnlivenedBlockModel(
                context.bakeLayer(EnlivenedBlockModel.LAYER_LOCATION)
            ),
            0.5F
        );
    }

    @Override
    public ResourceLocation getTextureLocation(
        EnlivenedBlockEntity animatedBlock
    ) {
        if (animatedBlock.blockEnlivened == null) {
            animatedBlock.setBlockEnlivened(
                animatedBlock.getDefaultBlockEnlivened()
            );
        }
        String cacheKey = animatedBlock.blockEnlivened
            .getRegistryName()
            .toString();
        ResourceLocation cached = (ResourceLocation) resourceCache.getOrDefault(
            cacheKey,
            null
        );
        if (cached != null) {
            return cached;
        }

        ResourceLocation enlivenedBlockResourceLocation = Minecraft
            .getInstance()
            .getBlockRenderer()
            .getBlockModelShaper()
            .getTexture(
                animatedBlock.blockEnlivened.defaultBlockState(),
                Minecraft.getInstance().level,
                animatedBlock.blockPosition()
            )
            .getName();

        ResourceLocation rtn = new ResourceLocation(
            enlivenedBlockResourceLocation.getNamespace(),
            "textures/" + enlivenedBlockResourceLocation.getPath() + ".png"
        );
        resourceCache.put(cacheKey, rtn);
        return rtn;
    }
}
