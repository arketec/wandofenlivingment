package arketec.wandofenlivingment.registration;

import arketec.wandofenlivingment.items.CreativeWandOfEnlivingmentItem;
import arketec.wandofenlivingment.items.FragileWandOfEnlivingment;
import arketec.wandofenlivingment.items.GreaterWandOfEnlivingment;
import arketec.wandofenlivingment.items.WandOfEnlivingmentItem;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;

import java.util.function.Supplier;

public class ModItems {

    public static final DeferredItem<Item> FRAGILE_WAND_OF_ENLIVINGMENT =
            register("fragile_wand_of_enlivingment", FragileWandOfEnlivingment::new);
    public static final DeferredItem<Item> WAND_OF_ENLIVINGMENT =
            register("wand_of_enlivingment", WandOfEnlivingmentItem::new);
    public static final DeferredItem<Item> GREATER_WAND_OF_ENLIVINGMENT =
            register("greater_wand_of_enlivingment", GreaterWandOfEnlivingment::new);
    public static final DeferredItem<Item> CREATIVE_WAND_OF_ENLIVINGMENT =
            register("creative_wand_of_enlivingment", CreativeWandOfEnlivingmentItem::new);

    public static final void register() {}

    private static <T extends Item> DeferredItem<T> register(String name, Supplier<T> factory) {
        return RegistrationManager.ITEMS.register(name, factory);
    }
}
