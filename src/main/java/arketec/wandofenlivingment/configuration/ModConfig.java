package arketec.wandofenlivingment.configuration;
import net.neoforged.neoforge.common.ModConfigSpec;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;


public class ModConfig {
    private ModConfig(ModConfigSpec.Builder builder) {
        builder.comment("Config for Wand Of Enlivingment").push("General");

        allowBlockEntities =
                builder
                        .comment(
                                "allow blocks with block entities to be enlivened\nWARNING: Use at your own risk"
                        )
                        .define("allowBlockEntities", false);
        blockDenylist =
                builder
                        .comment("blocks not allowed to enliven (mod:block_name)")
                        .defineList(
                                "blockDenylist",
                                Arrays.asList(
                                        "minecraft:bedrock",
                                        "minecraft:end_portal_frame"
                                ),
                                e ->
                                        Pattern
                                                .compile("[a-z]+:[a-z_]+")
                                                .matcher(e.toString())
                                                .matches()
                        );
        blockAllowlist =
                builder
                        .comment(
                                "blocks with block entities that should be allowed to enliven (mod:block_name)\nNote: This overrides allowBlockEntities on the specified blocks"
                        )
                        .defineList(
                                "blockAllowlist",
                                Arrays.asList(),
                                e ->
                                        Pattern
                                                .compile("[a-z]+:[a-z_]+")
                                                .matcher(e.toString())
                                                .matches()
                        );

        wandDurability =
                builder
                        .comment("number of uses per wand")
                        .define("wandDurability", 64);

        allowMending =
                builder
                        .comment(
                                "allows the mending enchantment to be applied to the wand"
                        )
                        .define("allowMending", false);

        builder.pop();
    }
    public static final ModConfig CONFIG;
    public static final ModConfigSpec CONFIG_SPEC;

    public static ModConfigSpec.ConfigValue<List<? extends String>> blockDenylist;
    public static ModConfigSpec.ConfigValue<List<? extends String>> blockAllowlist;
    public static ModConfigSpec.BooleanValue allowBlockEntities;
    public static ModConfigSpec.ConfigValue<Integer> wandDurability;
    public static ModConfigSpec.BooleanValue allowMending;

    static {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();
        var pair = builder.configure(ModConfig::new);
        CONFIG_SPEC = pair.getRight();
        CONFIG = pair.getLeft();
    }
}
