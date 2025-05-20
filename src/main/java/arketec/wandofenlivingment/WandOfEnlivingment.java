package arketec.wandofenlivingment;


import arketec.wandofenlivingment.registration.RegistrationManager;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;

@Mod(WandOfEnlivingment.MODID)
public class WandOfEnlivingment {

    public static final String MODID = "wandofenlivingment";

    public WandOfEnlivingment(ModContainer container) {
        RegistrationManager.register();
        container.registerConfig(ModConfig.Type.STARTUP, arketec.wandofenlivingment.configuration.ModConfig.CONFIG_SPEC);
    }
}
