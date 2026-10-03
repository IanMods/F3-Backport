package club.iananderson.f3backport;

import club.iananderson.f3backport.config.F3BackportClient;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;

@Mod(Constants.MOD_ID)
public class F3Backport {

  public F3Backport(IEventBus modBus, ModContainer modContainer) {
    CommonClass.init();

    modContainer.registerConfig(ModConfig.Type.CLIENT, F3BackportClient.CLIENT_SPEC, "f3-backport-client.toml");
  }
}