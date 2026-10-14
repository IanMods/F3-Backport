package club.iananderson.f3backport.client.overlays;

import club.iananderson.f3backport.client.gui.components.debug.NewDebugScreenOverlay;
import javax.annotation.Nonnull;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.client.gui.components.DebugScreenOverlay;

public class NeoForgeNewDebugHudOverlay implements LayeredDraw.Layer {
  public static NeoForgeNewDebugHudOverlay HUD_INSTANCE;

  public static void init() {
    HUD_INSTANCE = new NeoForgeNewDebugHudOverlay();
  }

  public void render(@Nonnull GuiGraphics graphics, @Nonnull DeltaTracker tickCounter) {
    Minecraft mc = Minecraft.getInstance();
    DebugScreenOverlay debug = mc.getDebugOverlay();

    NewDebugScreenOverlay newDebugScreenOverlay = new NewDebugScreenOverlay(mc);
    newDebugScreenOverlay.debugEntries.setOverlayVisible(debug.showDebugScreen());
    newDebugScreenOverlay.render(graphics);
  }
}