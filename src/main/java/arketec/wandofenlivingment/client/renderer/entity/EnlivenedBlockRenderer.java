package arketec.wandofenlivingment.client.renderer.entity;

import arketec.wandofenlivingment.client.model.entity.EnlivenedBlockModel;
import arketec.wandofenlivingment.entities.EnlivenedBlockEntity;
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
        boolean noCache = false;
        String cacheKey = animatedBlock.blockEnlivened.getName().getString();
        if (animatedBlock.blockEnlivened == null) {
            animatedBlock.setBlockEnlivened(
                animatedBlock.getDefaultBlockEnlivened()
            );
            noCache = true;
        } else {
            ResourceLocation cached =
                (ResourceLocation) resourceCache.getOrDefault(cacheKey, null);
            if (cached != null) {
                return cached;
            }
        }

        ResourceLocation enlivenedBlockResourceLocation = Minecraft
            .getInstance()
            .getBlockRenderer()
            .getBlockModelShaper()
            .getTexture(
                animatedBlock.blockEnlivened.defaultBlockState(),
                animatedBlock.level(),
                animatedBlock.blockPosition()
            )
            .contents()
            .name();

        ResourceLocation rtn = new ResourceLocation(
            enlivenedBlockResourceLocation.getNamespace(),
            "textures/" + enlivenedBlockResourceLocation.getPath() + ".png"
        );
        if (!noCache) resourceCache.put(cacheKey, rtn);
        return rtn;
    }
}
