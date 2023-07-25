package dev.arketec.wandofenlivingment.client.model.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.arketec.wandofenlivingment.WandOfEnlivingment;
import dev.arketec.wandofenlivingment.entities.EnlivenedBlockEntity;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class EnlivenedBlockModel<T extends EnlivenedBlockEntity>
    extends EntityModel<T> {

    public static final ModelLayerLocation LAYER_LOCATION =
        new ModelLayerLocation(
            new ResourceLocation(WandOfEnlivingment.MODID, "enlivened_block"),
            "main"
        );
    private final ModelPart body;

    public EnlivenedBlockModel(ModelPart root) {
        this.body = root.getChild("body");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition body = partdefinition.addOrReplaceChild(
            "body",
            CubeListBuilder
                .create()
                .texOffs(-4, -4)
                .addBox(
                    -4.0F,
                    -14.0F,
                    -8.0F,
                    12.0F,
                    10.0F,
                    12.0F,
                    new CubeDeformation(0.0F)
                ),
            PartPose.offset(0.0F, 24.0F, 0.0F)
        );

        PartDefinition legs = body.addOrReplaceChild(
            "legs",
            CubeListBuilder
                .create()
                .texOffs(1, 1)
                .addBox(
                    -8.0F,
                    -6.0F,
                    8.0F,
                    2.0F,
                    6.0F,
                    2.0F,
                    new CubeDeformation(0.0F)
                )
                .texOffs(1, 1)
                .addBox(
                    8.0F,
                    -6.0F,
                    8.0F,
                    2.0F,
                    6.0F,
                    2.0F,
                    new CubeDeformation(0.0F)
                )
                .texOffs(1, 1)
                .addBox(
                    -8.0F,
                    -6.0F,
                    -12.0F,
                    2.0F,
                    6.0F,
                    2.0F,
                    new CubeDeformation(0.0F)
                )
                .texOffs(1, 1)
                .addBox(
                    8.0F,
                    -6.0F,
                    -12.0F,
                    2.0F,
                    6.0F,
                    2.0F,
                    new CubeDeformation(0.0F)
                ),
            PartPose.offset(0.0F, 0.0F, 0.0F)
        );

        PartDefinition upperleg3_r1 = legs.addOrReplaceChild(
            "upperleg3_r1",
            CubeListBuilder
                .create()
                .texOffs(-1, -1)
                .addBox(
                    -2.0F,
                    -6.0F,
                    -14.0F,
                    2.0F,
                    2.0F,
                    6.0F,
                    new CubeDeformation(0.0F)
                ),
            PartPose.offsetAndRotation(
                0.0F,
                0.0F,
                0.0F,
                0.0112F,
                0.5855F,
                0.2483F
            )
        );

        PartDefinition upperleg2_r1 = legs.addOrReplaceChild(
            "upperleg2_r1",
            CubeListBuilder
                .create()
                .texOffs(-1, -1)
                .addBox(
                    0.0F,
                    -6.0F,
                    8.0F,
                    2.0F,
                    2.0F,
                    6.0F,
                    new CubeDeformation(0.0F)
                ),
            PartPose.offsetAndRotation(
                0.0F,
                0.0F,
                0.0F,
                0.3007F,
                0.5895F,
                0.2214F
            )
        );

        PartDefinition upperleg4_r1 = legs.addOrReplaceChild(
            "upperleg4_r1",
            CubeListBuilder
                .create()
                .texOffs(-1, -1)
                .addBox(
                    0.0F,
                    -6.0F,
                    -16.0F,
                    2.0F,
                    2.0F,
                    6.0F,
                    new CubeDeformation(0.0F)
                ),
            PartPose.offsetAndRotation(
                0.0F,
                0.0F,
                0.0F,
                -0.1158F,
                -0.6104F,
                -0.0398F
            )
        );

        PartDefinition upperleg1_r1 = legs.addOrReplaceChild(
            "upperleg1_r1",
            CubeListBuilder
                .create()
                .texOffs(-1, -1)
                .addBox(
                    -2.0F,
                    -6.0F,
                    6.0F,
                    2.0F,
                    2.0F,
                    6.0F,
                    new CubeDeformation(0.0F)
                ),
            PartPose.offsetAndRotation(
                0.0F,
                0.0F,
                0.0F,
                0.1724F,
                -0.5687F,
                -0.0099F
            )
        );

        return LayerDefinition.create(meshdefinition, 16, 16);
    }

    @Override
    public void setupAnim(
        T entity,
        float limbSwing,
        float limbSwingAmount,
        float ageInTicks,
        float netHeadYaw,
        float headPitch
    ) {}

    @Override
    public void renderToBuffer(
        PoseStack poseStack,
        VertexConsumer vertexConsumer,
        int packedLight,
        int packedOverlay,
        float red,
        float green,
        float blue,
        float alpha
    ) {
        body.render(
            poseStack,
            vertexConsumer,
            packedLight,
            packedOverlay,
            red,
            green,
            blue,
            alpha
        );
    }
}
