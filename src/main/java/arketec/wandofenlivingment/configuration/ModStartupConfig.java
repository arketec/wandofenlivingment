package arketec.wandofenlivingment.configuration;

import net.neoforged.neoforge.common.ModConfigSpec;

public class ModStartupConfig {
    private ModStartupConfig(ModConfigSpec.Builder builder) {
        builder.comment("Config for Wand Of Enlivingment Startup").push("General");

        fragileWandDurability =
                builder.comment("number of uses per fragile wand").define("fragileWandDurability", 4);
        wandDurability = builder.comment("number of uses per wand").define("wandDurability", 24);
        greaterWandDurability =
                builder.comment("number of uses per greater wand").define("greaterWandDurability", 64);
    }

    public static final ModStartupConfig CONFIG;
    public static final ModConfigSpec CONFIG_SPEC;

    public static ModConfigSpec.ConfigValue<Integer> fragileWandDurability;
    public static ModConfigSpec.ConfigValue<Integer> wandDurability;
    public static ModConfigSpec.ConfigValue<Integer> greaterWandDurability;
    public static ModConfigSpec.ConfigValue<Integer> creativeWandDurability;

    static {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();
        var pair = builder.configure(ModStartupConfig::new);
        CONFIG_SPEC = pair.getRight();
        CONFIG = pair.getLeft();
    }
}
