package club.iananderson.f3backport;

import club.iananderson.f3backport.config.F3BackportClient;
import fuzs.forgeconfigapiport.api.config.v2.ForgeConfigRegistry;
import net.fabricmc.api.ModInitializer;
import net.minecraftforge.fml.config.ModConfig;

public class F3Backport implements ModInitializer {

  @Override
  public void onInitialize() {
    Common.init();

    ForgeConfigRegistry.INSTANCE.register(Constants.MOD_ID, ModConfig.Type.CLIENT, F3BackportClient.CLIENT_SPEC,
                                          "f3-backport-client.toml");
  }
}
