package arketec.wandofenlivingment.entities;

import arketec.wandofenlivingment.util.EnlivenedBlockHelpers;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.entity.IEntityWithComplexSpawn;

public class EnlivenedBlockEntity extends Monster implements IEntityWithComplexSpawn {

    public Block blockEnlivened;

    public EnlivenedBlockEntity(
        EntityType<? extends EnlivenedBlockEntity> type,
        Level level
    ) {
        super(type, level);
        blockEnlivened = getDefaultBlockEnlivened();
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(
                2,
                new WaterAvoidingRandomStrollGoal(this, 1.0D)
            );
        this.goalSelector.addGoal(3, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(
                2,
                new NearestAttackableTargetGoal<>(this, Player.class, true)
            );
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster
            .createMonsterAttributes()
            .add(Attributes.MAX_HEALTH, 20.0D)
            .add(Attributes.MOVEMENT_SPEED, 0.38D)
            .add(Attributes.ATTACK_DAMAGE, 1.0D)
            .add(Attributes.FOLLOW_RANGE, 4.0D);
    }

    public void setBlockEnlivened(Block block) {
        this.blockEnlivened = block;
    }

    public Block getDefaultBlockEnlivened() {
        return BuiltInRegistries.BLOCK.get(
            ResourceLocation.tryBuild("minecraft", "stone")
        );
    }

    @Override
    protected void dropCustomDeathLoot(ServerLevel level, DamageSource damageSource, boolean recentlyHit) {
        if (
                !level.isClientSide() && this.blockEnlivened != null
        ) level
                .addFreshEntity(
                        new ItemEntity(
                                level,
                                this.getX(),
                                this.getY(),
                                this.getZ(),
                                new ItemStack(this.blockEnlivened.asItem())
                        )
                );
    }

    //    @Override
//    public Packet<ClientGamePacketListener> getAddEntityPacket() {
//        return NetworkHooks.getEntitySpawningPacket(this);
//    }

//    @Override
//    public void sendPairingData(ServerPlayer serverPlayer, Consumer<CustomPacketPayload> bundleBuilder) {
//        bundleBuilder()
//    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        if (blockEnlivened == null) {
            return;
        }
        tag.putString(
            "block_name",
            EnlivenedBlockHelpers.getBlockNameAsResourceLocationString(
                blockEnlivened
            )
        );
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        var blockName = tag.getString("block_name");
        if (blockName != null) blockEnlivened =
            BuiltInRegistries.BLOCK.get(
                ResourceLocation.read(blockName).getOrThrow()
            ); else blockEnlivened = getDefaultBlockEnlivened();
    }

    @Override
    public void writeSpawnData(RegistryFriendlyByteBuf buffer) {
        if (blockEnlivened != null) buffer.writeUtf(
            EnlivenedBlockHelpers.getBlockNameAsResourceLocationString(
                blockEnlivened
            )
        );
    }

    @Override
    public void readSpawnData(RegistryFriendlyByteBuf additionalData) {
        var blockName = additionalData.readUtf();
        if (blockName != null) blockEnlivened =
            BuiltInRegistries.BLOCK.get(
                ResourceLocation.read(blockName).getOrThrow()
            ); else blockEnlivened = getDefaultBlockEnlivened();
    }

}
