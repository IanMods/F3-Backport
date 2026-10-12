package club.iananderson.f3backport.client;

import club.iananderson.f3backport.Constants;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@EventBusSubscriber(modid = Constants.MOD_ID, value = Dist.CLIENT)
public class F3BackportClientNeoForge {

  @SubscribeEvent
  public static void onInitializeClient(FMLClientSetupEvent event) {
  }
}