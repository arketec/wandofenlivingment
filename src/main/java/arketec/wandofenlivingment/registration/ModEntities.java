package arketec.wandofenlivingment.registration;

import arketec.wandofenlivingment.WandOfEnlivingment;
import arketec.wandofenlivingment.entities.EnlivenedBlockEntity;
import net.minecraft.world.entity.*;
import net.neoforged.neoforge.registries.DeferredHolder;

public class ModEntities {

    public static final DeferredHolder<EntityType<?>, EntityType<EnlivenedBlockEntity>> ENLIVENED_BLOCK =
        register("enlivened_block", EnlivenedBlockEntity::new);

    public static final void register() {}

    private static <E extends Entity> DeferredHolder<EntityType<?>, EntityType<E>> register(
        String name,
        EntityType.EntityFactory<E> factory
    ) {
        return RegistrationManager.ENTITIES.register(
            name,
            () ->
                EntityType.Builder
                    .of(factory, MobCategory.CREATURE)
                    .sized(1.0F, 1.5F)
                    .build(WandOfEnlivingment.MODID)
        );
    }
}
