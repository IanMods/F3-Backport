package club.iananderson.f3backport.client.gui.components.debug;

import net.minecraft.client.gui.GuiGraphics;

public interface DebugCustomRenderer {
  void extract(final GuiGraphics graphics, final int left, final int top, final DebugColumn.Side side);

  int height();

  int width(int groupWidth);
}
