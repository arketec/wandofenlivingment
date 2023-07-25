package dev.arketec.wandofenlivingment.entities;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
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
import net.minecraftforge.entity.IEntityAdditionalSpawnData;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.registries.ForgeRegistries;

public class EnlivenedBlockEntity
    extends Monster
    implements IEntityAdditionalSpawnData {

    public Block blockID;

    public EnlivenedBlockEntity(
        EntityType<? extends EnlivenedBlockEntity> type,
        Level level
    ) {
        super(type, level);
        blockID =
            ForgeRegistries.BLOCKS.getValue(
                new ResourceLocation("minecraft", "stone")
            );
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

    public void setBlock(Block blockID) {
        this.blockID = blockID;
    }

    @Override
    protected boolean shouldDropLoot() {
        return true;
    }

    @Override
    protected void dropCustomDeathLoot(
        DamageSource p_21018_,
        int p_21019_,
        boolean p_21020_
    ) {
        if (!this.getLevel().isClientSide()) this.getLevel()
            .addFreshEntity(
                new ItemEntity(
                    getLevel(),
                    this.getX(),
                    this.getY(),
                    this.getZ(),
                    new ItemStack(this.blockID.asItem())
                )
            );
    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putString("block_name", blockID.getRegistryName().toString());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        blockID =
            ForgeRegistries.BLOCKS.getValue(
                ResourceLocation
                    .read(tag.getString("block_name"))
                    .getOrThrow(false, s -> {})
            );
    }

    @Override
    public void writeSpawnData(FriendlyByteBuf buffer) {
        buffer.writeUtf(blockID.getRegistryName().toString());
    }

    @Override
    public void readSpawnData(FriendlyByteBuf additionalData) {
        blockID =
            ForgeRegistries.BLOCKS.getValue(
                ResourceLocation
                    .read(additionalData.readUtf())
                    .getOrThrow(false, s -> {})
            );
    }
}
