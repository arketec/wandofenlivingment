package arketec.wandofenlivingment.util;

import arketec.wandofenlivingment.entities.EnlivenedBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

public class EnlivenedBlockHelpers {

    public static ResourceLocation getTextureForBlock(
        Minecraft instance,
        EnlivenedBlockEntity enlivenedBlockEntity
    ) {
        ResourceLocation blockResource = instance
            .getBlockRenderer()
            .getBlockModelShaper()
            .getTexture(
                enlivenedBlockEntity.blockEnlivened.defaultBlockState(),
                enlivenedBlockEntity.level(),
                enlivenedBlockEntity.blockPosition()
            )
            .contents()
            .name();
        var template = "textures/%s.png";
        return new ResourceLocation(
            blockResource.getNamespace(),
            String.format(template, blockResource.getPath())
        );
    }

    public static String getBlockNameAsResourceLocationString(Block block) {
        return block.getDescriptionId().replace("block.", "").replace(".", ":");
    }
}
