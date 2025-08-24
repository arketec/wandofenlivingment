package arketec.wandofenlivingment.client.model.entity;

import arketec.wandofenlivingment.WandOfEnlivingment;
import arketec.wandofenlivingment.entities.EnlivenedBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class EnlivenedBlockModel<T extends EnlivenedBlockEntity> extends EntityModel<T> {

    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(
            ResourceLocation.fromNamespaceAndPath(WandOfEnlivingment.MODID, "enlivened_block"), "main");
    private final ModelPart body;
    private final ModelPart legFL, legFR, legBL, legBR;
    private final ModelPart shinFL, shinFR, shinBL, shinBR;
    private final float flX0, flY0, flZ0;
    private final float frX0, frY0, frZ0;
    private final float blX0, blY0, blZ0;
    private final float brX0, brY0, brZ0;
    private final float sflX0, sflY0, sflZ0, sfrX0, sfrY0, sfrZ0, sblX0, sblY0, sblZ0, sbrX0, sbrY0, sbrZ0;

    public ModelPart body() {
        return body;
    }

    public EnlivenedBlockModel(ModelPart root) {
        this.body = root.getChild("body");
        ModelPart legs = this.body.getChild("legs");

        this.legFL = legs.getChild("upperleg1_r1");
        this.legFR = legs.getChild("upperleg2_r1");
        this.legBL = legs.getChild("upperleg3_r1");
        this.legBR = legs.getChild("upperleg4_r1");

        this.shinFL = this.legFL.getChild("lower_fl");
        this.shinFR = this.legFR.getChild("lower_fr");
        this.shinBL = this.legBL.getChild("lower_bl");
        this.shinBR = this.legBR.getChild("lower_br");

        this.flX0 = legFL.xRot;
        this.flY0 = legFL.yRot;
        this.flZ0 = legFL.zRot;
        this.frX0 = legFR.xRot;
        this.frY0 = legFR.yRot;
        this.frZ0 = legFR.zRot;
        this.blX0 = legBL.xRot;
        this.blY0 = legBL.yRot;
        this.blZ0 = legBL.zRot;
        this.brX0 = legBR.xRot;
        this.brY0 = legBR.yRot;
        this.brZ0 = legBR.zRot;

        this.sflX0 = shinFL.xRot;
        this.sflY0 = shinFL.yRot;
        this.sflZ0 = shinFL.zRot;
        this.sfrX0 = shinFR.xRot;
        this.sfrY0 = shinFR.yRot;
        this.sfrZ0 = shinFR.zRot;
        this.sblX0 = shinBL.xRot;
        this.sblY0 = shinBL.yRot;
        this.sblZ0 = shinBL.zRot;
        this.sbrX0 = shinBR.xRot;
        this.sbrY0 = shinBR.yRot;
        this.sbrZ0 = shinBR.zRot;
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition body =
                partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition legs = body.addOrReplaceChild("legs", CubeListBuilder.create(), PartPose.offset(0, 0, 0));

        PartDefinition upperleg3_r1 = legs.addOrReplaceChild(
                "upperleg3_r1",
                CubeListBuilder.create()
                        .texOffs(-1, -1)
                        .addBox(-2.0F, -6.0F, -14.0F, 2.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0112F, 0.5855F, 0.2483F));

        PartDefinition upperleg2_r1 = legs.addOrReplaceChild(
                "upperleg2_r1",
                CubeListBuilder.create()
                        .texOffs(-1, -1)
                        .addBox(0.0F, -6.0F, 8.0F, 2.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.3007F, 0.5895F, 0.2214F));

        PartDefinition upperleg4_r1 = legs.addOrReplaceChild(
                "upperleg4_r1",
                CubeListBuilder.create()
                        .texOffs(-1, -1)
                        .addBox(0.0F, -6.0F, -16.0F, 2.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.1158F, -0.6104F, -0.0398F));

        PartDefinition upperleg1_r1 = legs.addOrReplaceChild(
                "upperleg1_r1",
                CubeListBuilder.create()
                        .texOffs(-1, -1)
                        .addBox(-2.0F, -6.0F, 6.0F, 2.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.1724F, -0.5687F, -0.0099F));

        upperleg1_r1.addOrReplaceChild(
                "lower_fl",
                CubeListBuilder.create().texOffs(1, 1).addBox(-1.0F, 0.0F, -1.0F, 2, 6, 2),
                PartPose.offset(-1.0F, -4.0F, 12.0F));

        upperleg2_r1.addOrReplaceChild(
                "lower_fr",
                CubeListBuilder.create().texOffs(1, 1).addBox(-1.0F, 0.0F, -1.0F, 2, 6, 2),
                PartPose.offset(1.0F, -4.0F, 14.0F));

        upperleg3_r1.addOrReplaceChild(
                "lower_bl",
                CubeListBuilder.create().texOffs(1, 1).addBox(-1.0F, 0.0F, -1.0F, 2, 6, 2),
                PartPose.offset(-1.0F, -4.0F, -14.0F));

        upperleg4_r1.addOrReplaceChild(
                "lower_br",
                CubeListBuilder.create().texOffs(1, 1).addBox(-1.0F, 0.0F, -1.0F, 2, 6, 2),
                PartPose.offset(1.0F, -4.0F, -16.0F));

        return LayerDefinition.create(meshdefinition, 16, 16);
    }

    private static float rad(float deg) {
        return deg * ((float) Math.PI / 180f);
    }

    @Override
    public void setupAnim(T e, float limbSwing, float limbSwingAmount, float age, float yaw, float pitch) {
        // base pose
        legFL.xRot = flX0;
        legFL.yRot = flY0;
        legFL.zRot = flZ0;
        legFR.xRot = frX0;
        legFR.yRot = frY0;
        legFR.zRot = frZ0;
        legBL.xRot = blX0;
        legBL.yRot = blY0;
        legBL.zRot = blZ0;
        legBR.xRot = brX0;
        legBR.yRot = brY0;
        legBR.zRot = brZ0;

        shinFL.xRot = sflX0;
        shinFL.yRot = sflY0;
        shinFL.zRot = sflZ0;
        shinFR.xRot = sfrX0;
        shinFR.yRot = sfrY0;
        shinFR.zRot = sfrZ0;
        shinBL.xRot = sblX0;
        shinBL.yRot = sblY0;
        shinBL.zRot = sblZ0;
        shinBR.xRot = sbrX0;
        shinBR.yRot = sbrY0;
        shinBR.zRot = sbrZ0;

        // --- tuning ---
        float speed = 1.2f; // cycle speed
        float lift = 0.35f; // thigh forward/back amplitude (was 0.7)
        float sway = 0.10f; // lateral sway amplitude  (was 0.25)
        float kneeGain = 0.35f; // how much the shin bends vs thigh
        float a = net.minecraft.util.Mth.clamp(limbSwingAmount, 0f, 0.6f); // soften big steps

        // hard caps (max angle from base)
        float MAX_THIGH = rad(18f);
        float MAX_SWAY = rad(6f);
        float MAX_KNEE = rad(22f);

        float t = limbSwing * speed;
        float PI = (float) Math.PI;

        // helper lambdas
        java.util.function.BiFunction<Float, Float, Float> clampAdd =
                (base, delta) -> base + net.minecraft.util.Mth.clamp(delta, -MAX_THIGH, MAX_THIGH);

        java.util.function.BiFunction<Float, Float, Float> clampSway =
                (base, delta) -> base + net.minecraft.util.Mth.clamp(delta, -MAX_SWAY, MAX_SWAY);

        // thighs (opposite corners in phase)
        legFL.xRot = clampAdd.apply(flX0, net.minecraft.util.Mth.cos(t) * lift * a);
        legBR.xRot = clampAdd.apply(brX0, net.minecraft.util.Mth.cos(t) * lift * a);
        legFR.xRot = clampAdd.apply(frX0, net.minecraft.util.Mth.cos(t + PI) * lift * a);
        legBL.xRot = clampAdd.apply(blX0, net.minecraft.util.Mth.cos(t + PI) * lift * a);

        // small lateral sway
        legFL.yRot = clampSway.apply(flY0, net.minecraft.util.Mth.cos(t + 0.5f) * sway * a);
        legBR.yRot = clampSway.apply(brY0, net.minecraft.util.Mth.cos(t + 0.5f) * sway * a);
        legFR.yRot = clampSway.apply(frY0, net.minecraft.util.Mth.cos(t + PI + 0.5f) * sway * a);
        legBL.yRot = clampSway.apply(blY0, net.minecraft.util.Mth.cos(t + PI + 0.5f) * sway * a);

        // knees: bend opposite the thigh swing, with clamp
        float dFL = -(legFL.xRot - flX0) * kneeGain;
        float dFR = -(legFR.xRot - frX0) * kneeGain;
        float dBL = -(legBL.xRot - blX0) * kneeGain;
        float dBR = -(legBR.xRot - brX0) * kneeGain;

        shinFL.xRot = sflX0 + net.minecraft.util.Mth.clamp(dFL, -MAX_KNEE, MAX_KNEE);
        shinFR.xRot = sfrX0 + net.minecraft.util.Mth.clamp(dFR, -MAX_KNEE, MAX_KNEE);
        shinBL.xRot = sblX0 + net.minecraft.util.Mth.clamp(dBL, -MAX_KNEE, MAX_KNEE);
        shinBR.xRot = sbrX0 + net.minecraft.util.Mth.clamp(dBR, -MAX_KNEE, MAX_KNEE);

        // keep shin sway small and in phase with its parent
        shinFL.yRot = sflY0 + net.minecraft.util.Mth.clamp(legFL.yRot - flY0, -MAX_SWAY, MAX_SWAY);
        shinFR.yRot = sfrY0 + net.minecraft.util.Mth.clamp(legFR.yRot - frY0, -MAX_SWAY, MAX_SWAY);
        shinBL.yRot = sblY0 + net.minecraft.util.Mth.clamp(legBL.yRot - blY0, -MAX_SWAY, MAX_SWAY);
        shinBR.yRot = sbrY0 + net.minecraft.util.Mth.clamp(legBR.yRot - brY0, -MAX_SWAY, MAX_SWAY);
    }

    @Override
    public void renderToBuffer(
            PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
        body.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
    }
}
