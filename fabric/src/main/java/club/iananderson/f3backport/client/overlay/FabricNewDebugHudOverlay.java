package club.iananderson.f3backport.client.overlay;

import club.iananderson.f3backport.client.gui.components.debug.NewDebugScreenOverlay;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;

public class FabricNewDebugHudOverlay implements HudRenderCallback {
  public static FabricNewDebugHudOverlay HUD_INSTANCE;

  public static void init() {
    HUD_INSTANCE = new FabricNewDebugHudOverlay();
  }

  @Override
  public void onHudRender(GuiGraphics graphics, float tickDelta) {
    Minecraft mc = Minecraft.getInstance();

    new NewDebugScreenOverlay(mc).render(graphics);
  }
}