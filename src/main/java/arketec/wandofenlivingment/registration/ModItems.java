package arketec.wandofenlivingment.registration;

import arketec.wandofenlivingment.items.WandOfEnlivingmentItem;
import java.util.function.Supplier;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {

    public static final RegistryObject<Item> WAND_OF_ENLIVINGMENT = register(
        "wand_of_enlivingment",
        WandOfEnlivingmentItem::new
    );

    public static final void register() {}

    private static <T extends Item> RegistryObject<T> register(
        String name,
        Supplier<T> factory
    ) {
        return RegistrationManager.ITEMS.register(name, factory);
    }
}
