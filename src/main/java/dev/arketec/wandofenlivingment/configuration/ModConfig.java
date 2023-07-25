package dev.arketec.wandofenlivingment.configuration;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import net.minecraftforge.common.ForgeConfigSpec;

public class ModConfig {

    public static final ForgeConfigSpec CONFIG_SPEC;

    public static ForgeConfigSpec.ConfigValue<List<? extends String>> blockDenylist;

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        builder.comment("Config for Wand Of Enlivingment").push("General");

        blockDenylist =
            builder
                .comment("blocks not allowed to animate (mod:block_name)")
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

        builder.pop();

        CONFIG_SPEC = builder.build();
    }

    public static void loadConfig(ForgeConfigSpec spec, Path path) {
        final CommentedFileConfig configData = CommentedFileConfig
            .builder(path)
            .sync()
            .autosave()
            .writingMode(WritingMode.REPLACE)
            .build();

        configData.load();

        spec.setConfig(configData);
    }
}
