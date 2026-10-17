package club.iananderson.f3backport.client.gui.components.debug.entries;

import net.minecraft.network.chat.Component;

public class DebugGroupOld {
  private final Component title;

  protected DebugGroupOld(final Component title) {
    this.title = title;
  }

  public Component title() {
    return this.title;
  }

  public static class Builder {
    private final Component title;

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

    public DebugGroupOld build() {
      return new DebugGroupOld(this.title);
    }
  }
}

