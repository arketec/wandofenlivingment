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
        String cacheKey = animatedBlock.blockID.getRegistryName().toString();
        ResourceLocation cached = (ResourceLocation) resourceCache.getOrDefault(
            cacheKey,
            null
        );
        if (cached != null) {
            return cached;
        }
        // this is way too slow
        String blockPath = Minecraft
            .getInstance()
            .getBlockRenderer()
            .getBlockModelShaper()
            .getTexture(
                animatedBlock.blockID.defaultBlockState(),
                Minecraft.getInstance().level,
                animatedBlock.blockPosition()
            )
            .getName()
            .toString();
        String modName = "minecraft";
        if (blockPath.contains(":")) {
            modName = blockPath.split(":")[0];
            blockPath = blockPath.split(":")[1];
        }

        ResourceLocation rtn = new ResourceLocation(
            modName,
            "textures/" + blockPath + ".png"
        );
        resourceCache.put(cacheKey, rtn);
        return rtn;
    }
}
