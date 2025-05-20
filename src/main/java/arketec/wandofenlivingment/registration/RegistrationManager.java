package arketec.wandofenlivingment.registration;

import arketec.wandofenlivingment.WandOfEnlivingment;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.neoforge.registries.DeferredRegister;

public class RegistrationManager {

    public static final DeferredRegister.Items ITEMS =
        DeferredRegister.createItems(WandOfEnlivingment.MODID);
    public static final DeferredRegister<EntityType<?>> ENTITIES =
        DeferredRegister.create(
            Registries.ENTITY_TYPE,
            WandOfEnlivingment.MODID
        );

    public static void register() {
        IEventBus modEventBus = ModLoadingContext
            .get()
            .getActiveContainer()
            .getEventBus();
        ITEMS.register(modEventBus);
        ENTITIES.register(modEventBus);

        ModItems.register();
        ModEntities.register();
    }
}
