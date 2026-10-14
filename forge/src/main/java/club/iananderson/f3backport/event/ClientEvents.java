package club.iananderson.f3backport.event;

import club.iananderson.f3backport.Constants;
import club.iananderson.f3backport.client.keybinds.DebugKeyBinds;
import club.iananderson.f3backport.client.overlays.ForgeNewDebugHudOverlay;
import club.iananderson.f3backport.config.F3BackportClient;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(value = Dist.CLIENT, modid = Constants.MOD_ID)
public class ClientEvents {
  @SubscribeEvent
  public static void onKeyInput(InputEvent.Key event) {
    DebugKeyBinds.optionsKeyInput();
  }

  @SubscribeEvent
  public static void onDebugHud(RenderGuiOverlayEvent.Pre event) {
    if (F3BackportClient.getEnableMod()) {
      if (event.getOverlay().id().equals(VanillaGuiOverlay.DEBUG_TEXT.id())) {
        event.cancel();
      }
    }
  }

  @EventBusSubscriber(value = Dist.CLIENT, modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
  public static class ModBus {
    // Overlays
    @SubscribeEvent
    public static void registerGuiOverlays(RegisterGuiOverlaysEvent event) {
      ForgeNewDebugHudOverlay.init();
      event.registerAbove(VanillaGuiOverlay.DEBUG_TEXT.id(), "f3backport", ForgeNewDebugHudOverlay.HUD_INSTANCE);
    }

    // Key Bindings
    @SubscribeEvent
    public static void onKeyRegister(RegisterKeyMappingsEvent event) {
      event.register(DebugKeyBinds.keyDebugOptions);
    }
  }

}
