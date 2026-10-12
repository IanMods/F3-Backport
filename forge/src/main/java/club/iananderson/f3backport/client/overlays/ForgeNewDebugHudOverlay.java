package club.iananderson.f3backport.client.overlays;

import club.iananderson.f3backport.client.gui.components.debug.NewDebugScreenOverlay;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class ForgeNewDebugHudOverlay implements IGuiOverlay {
  public static ForgeNewDebugHudOverlay HUD_INSTANCE;

  public static void init() {
    HUD_INSTANCE = new ForgeNewDebugHudOverlay();
  }

  public void render(ForgeGui gui, GuiGraphics graphics, float partialTick, int screenWidth, int screenHeight) {
    Minecraft mc = Minecraft.getInstance();

    new NewDebugScreenOverlay(mc).render(graphics);
  }
}