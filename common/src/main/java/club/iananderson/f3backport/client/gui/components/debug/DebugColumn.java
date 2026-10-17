package club.iananderson.f3backport.client.gui.components.debug;

import club.iananderson.f3backport.client.gui.components.debug.entries.DebugGroupContents;
import club.iananderson.f3backport.client.gui.components.debug.entries.DebugGroups;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.Rect2i;

public class DebugColumn {
  private static final int MARGIN_TOP = 2;
  private final Side side;
  private List<DebugGroups> previousGroups = new ArrayList<>();
  private List<DebugGroups> currentGroups = new ArrayList<>();
  private int heightSoFar;

  public DebugColumn(final Side side) {
    this.side = side;
  }

  public void clear() {
    this.previousGroups = new ArrayList<>();
    this.currentGroups = new ArrayList<>();
  }

  public void newFrame() {
    this.previousGroups = this.currentGroups;
    this.currentGroups = new ArrayList<>();
    this.heightSoFar = 2;
  }

  public boolean containsGroup(final DebugGroups group) {
    return this.previousGroups.contains(group) || this.currentGroups.contains(group);
  }

  public boolean isFull(final int maxHeight) {
    return this.heightSoFar >= maxHeight;
  }

  public void add(final DebugGroupContents contents, final GuiGraphics graphics, final Font font,
      final int scaledScreenWidth) {
    Rect2i rect = contents.extract(graphics, this.heightSoFar, font, this.side, scaledScreenWidth);
    int currentHeight = this.heightSoFar;
    int height = rect.getHeight();
    Objects.requireNonNull(font);
    this.heightSoFar = currentHeight + height + 9;
    this.currentGroups.add(contents.group());
  }

  public List<DebugGroups> getPreviousGroups() {
    return this.previousGroups;
  }

  public int getHeightSoFar() {
    return this.heightSoFar;
  }

  public enum Side {
    LEFT,
    RIGHT;

    Side() {
    }
  }
}
