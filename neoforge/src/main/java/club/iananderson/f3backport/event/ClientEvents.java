package club.iananderson.f3backport.event;

import club.iananderson.f3backport.Common;
import club.iananderson.f3backport.Constants;
import club.iananderson.f3backport.client.keybinds.DebugKeyBinds;
import club.iananderson.f3backport.client.overlays.NeoForgeNewDebugHudOverlay;
import club.iananderson.f3backport.config.F3BackportClient;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.event.RenderGuiLayerEvent;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;

@EventBusSubscriber(value = Dist.CLIENT, modid = Constants.MOD_ID)
public class ClientEvents {
  @SubscribeEvent
  public static void onKeyInput(InputEvent.Key event) {
    DebugKeyBinds.optionsKeyInput();
  }

  @SubscribeEvent
  public static void onDebugHud(RenderGuiLayerEvent.Pre event) {
    if (F3BackportClient.getEnableMod()) {
      if (event.getName().equals(VanillaGuiLayers.DEBUG_OVERLAY)) {
        event.setCanceled(true);
      }
    }
  }

  @EventBusSubscriber(value = Dist.CLIENT, modid = Constants.MOD_ID)
  public static class ModBus {
    // Overlays
    @SubscribeEvent
    public static void registerGuiOverlays(RegisterGuiLayersEvent event) {
      NeoForgeNewDebugHudOverlay.init();
      event.registerAbove(VanillaGuiLayers.DEBUG_OVERLAY, Common.location("hud"),
                          NeoForgeNewDebugHudOverlay.HUD_INSTANCE);
    }

    // Key Bindings
    @SubscribeEvent
    public static void onKeyRegister(RegisterKeyMappingsEvent event) {
      event.register(DebugKeyBinds.keyDebugOptions);
    }
  }

}
