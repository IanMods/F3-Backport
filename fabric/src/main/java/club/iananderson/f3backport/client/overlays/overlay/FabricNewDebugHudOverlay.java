package club.iananderson.f3backport.client.overlays.overlay;

import club.iananderson.f3backport.client.gui.components.debug.NewDebugScreenOverlay;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.DebugScreenOverlay;

public class FabricNewDebugHudOverlay implements HudRenderCallback {
  public static FabricNewDebugHudOverlay HUD_INSTANCE;

  public static void init() {
    HUD_INSTANCE = new FabricNewDebugHudOverlay();
  }

  @Override
  public void onHudRender(GuiGraphics graphics, DeltaTracker tickCounter) {
    Minecraft mc = Minecraft.getInstance();
    DebugScreenOverlay debug = mc.getDebugOverlay();

    new NewDebugScreenOverlay(mc).render(graphics);
  }
}