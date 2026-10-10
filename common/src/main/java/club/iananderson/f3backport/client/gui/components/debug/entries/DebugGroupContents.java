package club.iananderson.f3backport.client.gui.components.debug.entries;

import club.iananderson.f3backport.client.gui.components.debug.DebugColumn.Side;
import club.iananderson.f3backport.client.gui.components.debug.DebugCustomRenderer;
import com.google.common.base.Strings;
import com.mojang.datafixers.util.Pair;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FastColor;

public record DebugGroupContents(DebugGroup group, List<String> lines, List<Pair<String, Component>> facts,
                                 List<DebugCustomRenderer> customRenderers) {
  private static final int MARGIN_RIGHT = 3;
  private static final int MARGIN_LEFT = 3;
  private static final int TITLE_LEFT_PADDING = 3;
  private static final int FACT_NAME_VALUE_PADDING = 5;

  public DebugGroupContents(final DebugGroup group) {
    this(group, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
  }

  public void addFact(final String name, final Component value) {
    this.facts.add(Pair.of(name, value));
  }

  public void addCustomRenderer(final DebugCustomRenderer renderer) {
    this.customRenderers.add(renderer);
  }

  public Rect2i extract(final GuiGraphics graphics, final int top, final Font font, final Side side,
      final int scaledScreenWidth) {
    Objects.requireNonNull(font);
    int fullWidth = this.lines.stream().mapToInt(font::width).max().orElse(0);
    int titleWidth = font.width(this.group.title());
    if (titleWidth + TITLE_LEFT_PADDING > fullWidth) {
      fullWidth = titleWidth + TITLE_LEFT_PADDING;
    }

    int rows = this.lines.size() + this.facts.size();
    Objects.requireNonNull(font);
    int fullHeight = rows * font.lineHeight;
    if (titleWidth > 0) {
      Objects.requireNonNull(font);
      fullHeight += font.lineHeight;
    }

    int factNameWidth = this.facts.stream().mapToInt((f) -> font.width(f.getFirst())).max().orElse(0);

    for (Pair<String, Component> fact : this.facts) {
      int width = factNameWidth + FACT_NAME_VALUE_PADDING + font.width(fact.getSecond());
      if (width > fullWidth) {
        fullWidth = width;
      }
    }

    for (DebugCustomRenderer customRenderer : this.customRenderers) {
      fullWidth = Math.max(fullWidth, customRenderer.width(fullWidth));
      fullHeight += customRenderer.height();
    }

    int left = side == Side.LEFT
               ? MARGIN_LEFT
               : scaledScreenWidth - MARGIN_RIGHT - fullWidth;
    int y = top;
    if (titleWidth > 0) {
      int titleLeft = left - 1;
      int titleTop = top - 1;
      int titleRight = left + fullWidth + 1;
      int titleBottom = top + font.lineHeight - 1;
      Objects.requireNonNull(font);
      graphics.fill(titleLeft, titleTop, titleRight, titleBottom, -1875890128);
      graphics.drawString(font, this.group.title(), left + TITLE_LEFT_PADDING, top, -1, false);
      Objects.requireNonNull(font);
      y = top + font.lineHeight;
    }

    graphics.fill(left - 1, y - 1, left + fullWidth + 1, top + fullHeight + 1, -1873784752);

    for (Pair<String, Component> fact : this.facts) {
      String name = fact.getFirst() + ":";
      graphics.drawString(font, name, left + (factNameWidth - font.width(fact.getFirst())), y, -2039584, false);
      graphics.drawString(font, fact.getSecond(), left + factNameWidth + FACT_NAME_VALUE_PADDING, y, -3092272, false);
      Objects.requireNonNull(font);
      y += font.lineHeight;
    }

    if (!this.facts.isEmpty() && !this.lines.isEmpty()) {
      y += 2;
    }

    for (String line : this.lines) {
      if (!Strings.isNullOrEmpty(line)) {
        graphics.drawString(font, line, left, y, -2039584, false);
      }

      Objects.requireNonNull(font);
      y += font.lineHeight;
    }

    for (DebugCustomRenderer customRenderer : this.customRenderers) {
      customRenderer.extract(graphics, left, y, side);
      y += customRenderer.height();
    }

    if (this.group.accentColor().isPresent()) {
      int accentColor = FastColor.ARGB32.opaque(this.group.accentColor().getAsInt());
      if (side == Side.LEFT) {
        graphics.fill(0, top - 1, 1, top + fullHeight + 1, accentColor);
      } else {
        graphics.fill(scaledScreenWidth - 1, top - 1, scaledScreenWidth, top + fullHeight + 1, accentColor);
      }
    }

    return new Rect2i(left, top, fullWidth, fullHeight);
  }
}
