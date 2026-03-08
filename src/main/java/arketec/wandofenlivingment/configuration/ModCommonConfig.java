package arketec.wandofenlivingment.configuration;

import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import static com.mojang.text2speech.Narrator.LOGGER;

public class ModCommonConfig {
    private ModCommonConfig(ModConfigSpec.Builder builder) {
        builder.comment("Config for Wand Of Enlivingment").push("General");

        allowBlockEntities = builder.comment(
                        "allow blocks with block entities to be enlivened\nWARNING: Use at your own risk")
                .define("allowBlockEntities", false);
        blockDenylist = builder.comment(
                        """
            blocks not allowed to enliven (mod:block_name). Regex supported.
            Examples:
              "^(?!minecraft:).*$"  -> deny all NON-minecraft blocks
              "^minecraft:.*$"      -> deny ALL minecraft blocks
            """)
                .defineList(
                        List.of("blockDenylist"),
                        () -> List.of("minecraft:bedrock", "minecraft:end_portal_frame"),
                        () -> "",
                        ModCommonConfig::isValidRegexEntry);
        blockEntityAllowlist = builder.comment(
                        "blocks with block entities that should be allowed to enliven (mod:block_name). Regex not supported\nNote: This overrides allowBlockEntities on the specified blocks")
                .defineList(List.of("blockEntityAllowlist"), List.of(), () -> "", e -> e instanceof String);

        allowMending = builder.comment("allows the mending enchantment to be applied to the wand")
                .define("allowMending", false);
        allowRepair =
                builder.comment("allows repair items to be applied to the wand").define("allowRepair", false);
        builder.pop();

        // === Fragile Wand Section ===
        fragileWandConfig = buildWandSection("Fragile Wand", builder, List.of(), List.of("^(?!minecraft:).*$"));

        // === Normal Wand Section ===
        wandConfig = buildWandSection("Normal Wand", builder, List.of(), List.of());

        // === Greater Wand Section ===
        greaterWandConfig = buildWandSection("Greater Wand", builder, List.of(), List.of());
    }

    public static final ModCommonConfig CONFIG;
    public static final ModConfigSpec CONFIG_SPEC;

    public static ModConfigSpec.ConfigValue<List<? extends String>> blockDenylist;
    public static ModConfigSpec.ConfigValue<List<? extends String>> blockEntityAllowlist;
    public static ModConfigSpec.BooleanValue allowBlockEntities;
    public static ModConfigSpec.BooleanValue allowMending;
    public static ModConfigSpec.BooleanValue allowRepair;

    public static WandConfig fragileWandConfig;
    public static WandConfig wandConfig;
    public static WandConfig greaterWandConfig;

    static {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();
        var pair = builder.configure(ModCommonConfig::new);
        CONFIG_SPEC = pair.getRight();
        CONFIG = pair.getLeft();
    }

    private static WandConfig buildWandSection(
            String sectionName,
            ModConfigSpec.Builder builder,
            List<String> defaultAllowList,
            List<String> defaultDenyList) {
        builder.push(sectionName);
        var allowBlockEntities = builder.comment(
                        "blocks with block entities that should be allowed to enliven (mod:block_name).\nNote: This overrides allowBlockEntities on the specified blocks")
                .define("allowBlockEntities", false);
        var denylist = builder.comment(
                        """
            blocks not allowed to enliven (mod:block_name). Regex supported.
            Examples:
              "^(?!minecraft:).*$"  -> deny all NON-minecraft blocks
              "^minecraft:.*$"      -> deny ALL minecraft blocks
            """)
                .defineList(
                        List.of("blockDenylist"), () -> defaultDenyList, () -> "", ModCommonConfig::isValidRegexEntry);
        var allowlist = builder.comment(
                        "blocks with block entities that should be allowed to enliven (mod:block_name). Regex not supported\"\nNote: This overrides allowBlockEntities on the specified blocks")
                .defineList(List.of("blockAllowlist"), () -> defaultAllowList, () -> "", e -> e instanceof String);
        ModConfigSpec.BooleanValue allowMending = null;
        ModConfigSpec.BooleanValue allowRepair = null;
        if (!sectionName.equals("Fragile Wand")) {
            allowMending = builder.comment("allows the mending enchantment to be applied to the wand")
                    .define("allowMending", false);
            allowRepair = builder.comment("allows repair items to be applied to the wand")
                    .define("allowRepair", false);
        }
        builder.pop();

        return new WandConfig(allowBlockEntities, denylist, allowlist, allowMending, allowRepair);
    }

    private static boolean isValidRegexEntry(Object e) {
        if (!(e instanceof String s)) return false;
        try {
            // Allow both literals and regex: compiling a plain literal is fine too.
            Pattern.compile(s);
            return true;
        } catch (PatternSyntaxException ex) {
            // Optional: log details so users see what went wrong.
            LOGGER.warn("Invalid regex in blockDenylist: '{}' at index {}: {}", s, ex.getIndex(), ex.getDescription());
            return false;
        }
    }
}
