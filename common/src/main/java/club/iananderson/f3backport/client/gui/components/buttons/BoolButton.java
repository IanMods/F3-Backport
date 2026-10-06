package club.iananderson.f3backport.client.gui.components.buttons;

import club.iananderson.f3backport.Common;
import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.systems.RenderSystem;
import java.awt.Color;
import java.util.List;
import java.util.function.Function;
import net.minecraft.client.Minecraft;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.util.Mth;
import org.jspecify.annotations.NonNull;

public class BoolButton extends AbstractButton {
  private static final List<Boolean> BOOLEAN_OPTIONS;
  private final Component name;
  private int index;
  private Boolean value;
  private final List<Boolean> values;
  private final Function<BoolButton, MutableComponent> narrationProvider;
  private final BoolButton.OnValueChange onValueChange;
  private final OptionInstance.TooltipSupplier<Boolean> tooltipSupplier;
  private static final WidgetSprites SPRITES = new WidgetSprites(Common.location("widget/button"),
                                                                 Common.location("widget/button_disabled"),
                                                                 Common.location("widget/button_highlighted"),
                                                                 Common.location("widget/button_highlighted_disabled"));

  BoolButton(int x, int y, int width, int height, Component message, Component name, int index, Boolean value,
      List<Boolean> values, Function<BoolButton, MutableComponent> narrationProvider,
      BoolButton.OnValueChange onValueChange, OptionInstance.TooltipSupplier<Boolean> tooltipSupplier) {
    super(x, y, width, height, message);
    this.name = name;
    this.index = index;
    this.value = value;
    this.values = values;
    this.narrationProvider = narrationProvider;
    this.onValueChange = onValueChange;
    this.tooltipSupplier = tooltipSupplier;
    this.updateTooltip();
  }

  protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
    guiGraphics.setColor(1.0F, 1.0F, 1.0F, this.alpha);
    RenderSystem.enableBlend();
    RenderSystem.enableDepthTest();
    guiGraphics.blitSprite(SPRITES.get(this.value, this.isHoveredOrFocused()), this.getX(), this.getY(),
                           this.getWidth(), this.getHeight());
    guiGraphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);

    Color color = this.active
                  ? new Color(255, 255, 255)
                  : new Color(160, 160, 160);

    Minecraft mc = Minecraft.getInstance();
    this.renderString(guiGraphics, mc.font, color.getRGB() | Mth.ceil(this.alpha * 255.0F) << 24);
  }

  private void updateTooltip() {
    this.setTooltip(this.tooltipSupplier.apply(this.value));
  }

  public void onPress() {
    if (Screen.hasShiftDown()) {
      this.cycleValue(-1);
    } else {
      this.cycleValue(1);
    }
  }

  private Component createLabelForValue(Boolean bool) {
    return this.name;
  }

  private void updateValue(Boolean bool) {
    Component component = this.createLabelForValue(bool);
    this.setMessage(component);
    this.value = bool;
    this.updateTooltip();
  }

  private void cycleValue(int delta) {
    ;
    this.index = Mth.positiveModulo(this.index + delta, this.values.size());
    Boolean bool = this.values.get(this.index);
    this.updateValue(bool);
    this.onValueChange.onValueChange(this, bool);
  }

  private Boolean getCycledValue() {
    return this.values.get(Mth.positiveModulo(this.index + 1, this.values.size()));
  }

  public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
    if (scrollY > (double) 0.0F) {
      this.cycleValue(-1);
    } else if (scrollY < (double) 0.0F) {
      this.cycleValue(1);
    }

    return true;
  }

  public void setValue(Boolean value) {
    int i = this.values.indexOf(value);
    if (i != -1) {
      this.index = i;
    }

    this.updateValue(value);
  }

  public Boolean getValue() {
    return this.value;
  }

  protected @NonNull MutableComponent createNarrationMessage() {
    return this.narrationProvider.apply(this);
  }

  public void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {
    narrationElementOutput.add(NarratedElementType.TITLE, this.createNarrationMessage());
    if (this.active) {
      Boolean bool = this.getCycledValue();
      Component component = this.createLabelForValue(bool);
      if (this.isFocused()) {
        narrationElementOutput.add(NarratedElementType.USAGE,
                                   Component.translatable("narration.cycle_button.usage.focused",
                                                          new Object[]{component}));
      } else {
        narrationElementOutput.add(NarratedElementType.USAGE,
                                   Component.translatable("narration.cycle_button.usage.hovered",
                                                          new Object[]{component}));
      }
    }

  }

  public MutableComponent createDefaultNarrationMessage() {
    return wrapDefaultNarrationMessage(this.getMessage());
  }

  public static BoolButton.Builder builder(boolean initialValue) {
    return new Builder().withInitialValue(initialValue);
  }

  static {
    BOOLEAN_OPTIONS = ImmutableList.of(Boolean.TRUE, Boolean.FALSE);
  }

  public static class Builder {
    private int initialIndex;
    private Boolean initialValue;
    private OptionInstance.TooltipSupplier<Boolean> tooltipSupplier = (bool) -> null;
    private final Function<BoolButton, MutableComponent> narrationProvider = BoolButton::createDefaultNarrationMessage;
    private final List<Boolean> values = BOOLEAN_OPTIONS;

    public BoolButton.Builder withTooltip(OptionInstance.TooltipSupplier<Boolean> tooltipSupplier) {
      this.tooltipSupplier = tooltipSupplier;
      return this;
    }

    public BoolButton.Builder withInitialValue(Boolean initialValue) {
      this.initialValue = initialValue;
      int i = this.values.indexOf(initialValue);
      if (i != -1) {
        this.initialIndex = i;
      }

      return this;
    }

    public BoolButton create(int x, int y, int width, int height, Component name,
        BoolButton.OnValueChange onValueChange) {

      Boolean bool = this.initialValue != null
                     ? this.initialValue
                     : this.values.get(this.initialIndex);
      return new BoolButton(x, y, width, height, name, name, this.initialIndex, bool, this.values,
                            this.narrationProvider, onValueChange, this.tooltipSupplier);

    }
  }

  public interface OnValueChange {
    void onValueChange(BoolButton var1, Boolean var2);
  }
}