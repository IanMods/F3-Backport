package club.iananderson.f3backport;

import club.iananderson.f3backport.config.F3BackportClient;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Constants.MOD_ID)
public class F3Backport {

  public F3Backport() {
    Common.init();
    //MinecraftForge.EVENT_BUS.register(this);
    var modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

    ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, F3BackportClient.CLIENT_SPEC, "f3-backport-client.toml");
  }
}