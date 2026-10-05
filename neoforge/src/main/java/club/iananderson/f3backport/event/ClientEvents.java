package club.iananderson.f3backport.event;

import club.iananderson.f3backport.Constants;
import club.iananderson.f3backport.client.keybinds.DebugKeyBinds;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;

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
