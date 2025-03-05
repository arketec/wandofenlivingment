package arketec.wandofenlivingment.registration;

import arketec.wandofenlivingment.WandOfEnlivingment;
import arketec.wandofenlivingment.entities.EnlivenedBlockEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.*;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {

    public static final RegistryObject<EntityType<EnlivenedBlockEntity>> ENLIVENED_BLOCK =
        register("enlivened_block", EnlivenedBlockEntity::new);

    public static final void register() {}

    private static <E extends Entity> RegistryObject<EntityType<E>> register(
        String name,
        EntityType.EntityFactory<E> factory
    ) {
        return RegistrationManager.ENTITIES.register(
            name,
            () ->
                EntityType.Builder
                    .of(factory, MobCategory.CREATURE)
                    .sized(1.0F, 1.5F)
                    .build(
                        new ResourceLocation(WandOfEnlivingment.MODID, name)
                            .toString()
                    )
        );
    }
}
