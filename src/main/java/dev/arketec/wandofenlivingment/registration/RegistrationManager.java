package dev.arketec.wandofenlivingment.registration;

import dev.arketec.wandofenlivingment.WandOfEnlivingment;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class RegistrationManager {

    public static final DeferredRegister<Item> ITEMS = create(
        ForgeRegistries.ITEMS
    );
    public static final DeferredRegister<EntityType<?>> ENTITIES = create(
        ForgeRegistries.ENTITIES
    );

    public static void register() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ITEMS.register(modEventBus);
        ENTITIES.register(modEventBus);

        ModItems.register();
        ModEntities.register();
    }

    private static <
        T extends IForgeRegistryEntry<T>
    > DeferredRegister<T> create(IForgeRegistry<T> registry) {
        return DeferredRegister.create(registry, WandOfEnlivingment.MODID);
    }
}
