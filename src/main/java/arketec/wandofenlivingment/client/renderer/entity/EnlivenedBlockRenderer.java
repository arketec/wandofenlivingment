package arketec.wandofenlivingment.client.renderer.entity;

import arketec.wandofenlivingment.client.model.entity.EnlivenedBlockModel;
import arketec.wandofenlivingment.entities.EnlivenedBlockEntity;
import arketec.wandofenlivingment.util.EnlivenedBlockHelpers;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class EnlivenedBlockRenderer
    extends MobRenderer<EnlivenedBlockEntity, EnlivenedBlockModel<EnlivenedBlockEntity>> {

    private static final ConcurrentHashMap<String, ResourceLocation> resourceCache =
        new ConcurrentHashMap<>();

    public EnlivenedBlockRenderer(EntityRendererProvider.Context context) {
        super(
            context,
            new EnlivenedBlockModel<>(
                context.bakeLayer(EnlivenedBlockModel.LAYER_LOCATION)
            ),
            0.5F
        );
    }

    @Override
    @NotNull
    public ResourceLocation getTextureLocation(
        EnlivenedBlockEntity enlivenedBlockEntity
    ) {
        boolean noCache = false;
        String cacheKey = enlivenedBlockEntity.blockEnlivened
            .getName()
            .getString();
        if (enlivenedBlockEntity.blockEnlivened == null) {
            enlivenedBlockEntity.setBlockEnlivened(
                enlivenedBlockEntity.getDefaultBlockEnlivened()
            );
            noCache = true;
        } else {
            ResourceLocation cached = resourceCache.getOrDefault(
                cacheKey,
                null
            );
            if (cached != null) {
                return cached;
            }
        }

        ResourceLocation rtn = EnlivenedBlockHelpers.getTextureForBlock(
            Minecraft.getInstance(),
            enlivenedBlockEntity
        );
        if (!noCache) resourceCache.put(cacheKey, rtn);
        return rtn;
    }
}
