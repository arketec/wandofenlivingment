package dev.arketec.wandofenlivingment;

import dev.arketec.wandofenlivingment.configuration.ModConfig;
import dev.arketec.wandofenlivingment.registration.RegistrationManager;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.loading.FMLPaths;

@Mod(WandOfEnlivingment.MODID)
public class WandOfEnlivingment {

    public static final String MODID = "wandofenlivingment";

    public WandOfEnlivingment() {
        ModConfig.loadConfig(
            ModConfig.CONFIG_SPEC,
            FMLPaths.CONFIGDIR.get().resolve("wandofenlivingment.toml")
        );
        RegistrationManager.register();
    }
}
