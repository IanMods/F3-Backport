package club.iananderson.f3backport.client;

import club.iananderson.f3backport.Constants;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

@EventBusSubscriber(modid = Constants.MOD_ID, value = Dist.CLIENT)
public class F3BackportClientNeoForge {

  @SubscribeEvent
  public static void onInitializeClient(FMLClientSetupEvent event) {
  }
}