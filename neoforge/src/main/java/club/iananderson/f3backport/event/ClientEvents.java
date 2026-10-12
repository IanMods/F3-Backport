package club.iananderson.f3backport.event;

import club.iananderson.f3backport.Constants;
import club.iananderson.f3backport.client.keybinds.DebugKeyBinds;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(value = Dist.CLIENT, modid = Constants.MOD_ID)
public class ClientEvents {
  @SubscribeEvent
  public static void onKeyInput(InputEvent.Key event) {
    DebugKeyBinds.optionsKeyInput();
  }

  @EventBusSubscriber(value = Dist.CLIENT, modid = Constants.MOD_ID)
  public static class ModBus {
    // Key Bindings
    @SubscribeEvent
    public static void onKeyRegister(RegisterKeyMappingsEvent event) {
      event.register(DebugKeyBinds.keyDebugOptions);
    }
  }

}
