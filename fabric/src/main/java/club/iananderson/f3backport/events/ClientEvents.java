package club.iananderson.f3backport.events;

import club.iananderson.f3backport.client.keybinds.DebugKeyBinds;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;

public class ClientEvents {
  private ClientEvents() {
  }

  private static void registerKeyMappings() {
    DebugKeyBinds debugKeyBinds = new DebugKeyBinds();

    KeyBindingHelper.registerKeyBinding(debugKeyBinds.keyDebugOverlay);
  }

  public static void register() {
    registerKeyMappings();
  }
}
