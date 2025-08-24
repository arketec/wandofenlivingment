package arketec.wandofenlivingment.configuration;

import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.List;

public record WandConfig(
        ModConfigSpec.BooleanValue allowBlockEntities,
        ModConfigSpec.ConfigValue<List<? extends String>> blockDenylist,
        ModConfigSpec.ConfigValue<List<? extends String>> blockAllowlist,
        ModConfigSpec.BooleanValue allowMending) {}
