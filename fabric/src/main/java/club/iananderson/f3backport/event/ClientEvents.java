package club.iananderson.f3backport.event;

import club.iananderson.f3backport.client.keybinds.DebugKeyBinds;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;

public class ClientEvents {
  private ClientEvents() {
  }

  // Key Bindings
  private static void registerKeyMappings() {
    DebugKeyBinds.keyDebugOptions = KeyBindingHelper.registerKeyBinding(
        DebugKeyBinds.keyDebugOptions);
  }

  private static void registerKeyInputs() {
    ClientTickEvents.END_CLIENT_TICK.register(client -> {
      DebugKeyBinds.optionsKeyInput();
    });
  }

  public static void register() {
    registerKeyMappings();
    registerKeyInputs();
  }
}
