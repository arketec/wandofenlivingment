package arketec.wandofenlivingment.client.renderer.entity;

import arketec.wandofenlivingment.client.model.entity.EnlivenedBlockModel;
import arketec.wandofenlivingment.entities.EnlivenedBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;

public class BlockBodyLayer extends RenderLayer<EnlivenedBlockEntity, EnlivenedBlockModel<EnlivenedBlockEntity>> {

    public BlockBodyLayer(RenderLayerParent<EnlivenedBlockEntity, EnlivenedBlockModel<EnlivenedBlockEntity>> parent) {
        super(parent);
    }

    @Override
    public void render(
            PoseStack pose,
            MultiBufferSource buffers,
            int light,
            EnlivenedBlockEntity e,
            float limbSwing,
            float limbSwingAmount,
            float partialTicks,
            float ageInTicks,
            float netHeadYaw,
            float headPitch) {

        var state = e.blockEnlivened.defaultBlockState();
        if (state == null) return;

        pose.pushPose();

        this.getParentModel().body().translateAndRotate(pose);

        pose.scale(-1.0F, -1.0F, 1.0F);

        pose.translate(-0.6D, 0.3D, -0.5D);
        float s = 0.8F;
        pose.translate(0.5D, 0.5D, 0.5D);
        pose.scale(s, s, s);
        pose.translate(-0.5D, -0.5D, -0.5D);

        Minecraft.getInstance()
                .getBlockRenderer()
                .renderSingleBlock(
                        state,
                        pose,
                        buffers,
                        light,
                        OverlayTexture.NO_OVERLAY,
                        net.neoforged.neoforge.client.model.data.ModelData.EMPTY,
                        null);

        pose.popPose();
    }
}
