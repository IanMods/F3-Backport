package club.iananderson.f3backport.client.gui.components.debug.entries;

import club.iananderson.f3backport.client.gui.components.debug.DebugColumn.Side;
import java.awt.Color;
import java.util.Optional;
import java.util.OptionalInt;
import net.minecraft.network.chat.Component;

public class DebugGroup {
  private final Component title;
  private final OptionalInt accentColor;
  private final Optional<Side> preferredColumn;

  protected DebugGroup(final Component title, final OptionalInt accentColor, final Optional<Side> preferredColumn) {
    this.title = title;
    this.accentColor = accentColor;
    this.preferredColumn = preferredColumn;
  }

  public Component title() {
    return this.title;
  }

  public OptionalInt accentColor() {
    return this.accentColor;
  }

  public Optional<Side> preferredColumn() {
    return this.preferredColumn;
  }

  public static class Builder {
    private final Component title;
    private OptionalInt accentColor = OptionalInt.empty();
    private Optional<Side> preferredColumn = Optional.empty();

    protected Builder(final Component title) {
      this.title = title;
    }

    public static Builder titleless() {
      return titled(Component.empty());
    }

    public static Builder titled(final String title) {
      return titled(Component.literal(title));
    }

    public static Builder titled(final Component title) {
      return new Builder(title);
    }

    public Builder withAccentColor(final int rgb) {
      this.accentColor = OptionalInt.of(rgb);
      return this;
    }

    public Builder withAccentColor(Color color) {
      int rgbInt = color.getRGB();
      this.accentColor = OptionalInt.of(rgbInt);
      return this;
    }

    public Builder withPreferredColumn(final Side column) {
      this.preferredColumn = Optional.of(column);
      return this;
    }

    public DebugGroup build() {
      return new DebugGroup(this.title, this.accentColor, this.preferredColumn);
    }
  }
}

