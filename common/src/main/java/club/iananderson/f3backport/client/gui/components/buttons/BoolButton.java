package club.iananderson.f3backport.client.gui.components.buttons;

import club.iananderson.f3backport.Common;
import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.systems.RenderSystem;
import java.awt.Color;
import java.util.List;
import java.util.function.Function;
import net.minecraft.client.Minecraft;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.gui.Font;
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
  public static final WidgetSprites SPRITES = new WidgetSprites(Common.location("widget/button"),
                                                                Common.location("widget/button_disabled"),
                                                                Common.location("widget/button_highlighted"),
                                                                Common.location("widget/button_highlighted_disabled"));
  public final Component name;
  public final List<Boolean> values;
  public final Function<BoolButton, MutableComponent> narrationProvider;
  public final BoolButton.OnValueChange onValueChange;
  public final OptionInstance.TooltipSupplier<Boolean> tooltipSupplier;
  public int index;
  public Boolean value;
  public float buttonScale;

  BoolButton(int x, int y, int width, int height, Component message, Component name, int index, Boolean value,
      List<Boolean> values, Function<BoolButton, MutableComponent> narrationProvider,
      BoolButton.OnValueChange onValueChange, OptionInstance.TooltipSupplier<Boolean> tooltipSupplier,
      float buttonScale) {
    super(x, y, width, height, message);
    this.name = name;
    this.index = index;
    this.value = value;
    this.values = values;
    this.narrationProvider = narrationProvider;
    this.onValueChange = onValueChange;
    this.tooltipSupplier = tooltipSupplier;
    this.updateTooltip();
    this.buttonScale = buttonScale;
  }

  public static BoolButton.Builder builder(boolean initialValue) {
    return new Builder().withInitialValue(initialValue);
  }

  protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
    guiGraphics.setColor(1.0F, 1.0F, 1.0F, this.alpha);
    RenderSystem.enableBlend();
    RenderSystem.enableDepthTest();
    guiGraphics.pose().pushPose();
    guiGraphics.pose().scale(buttonScale, buttonScale, buttonScale);
    guiGraphics.blitSprite(SPRITES.get(this.value, this.isHoveredOrFocused()), this.getX(), this.getY(),
                           this.getWidth(), this.getHeight());
    guiGraphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);

    Color color = this.active
                  ? new Color(255, 255, 255)
                  : new Color(160, 160, 160);

    Minecraft mc = Minecraft.getInstance();
    this.renderString(guiGraphics, mc.font, color.getRGB() | Mth.ceil(this.alpha * 255.0F) << 24);
    guiGraphics.pose().popPose();
  }

  @Override
  public void renderString(@NonNull GuiGraphics guiGraphics, @NonNull Font font, int color) {
    int stringWidth = font.width(this.getMessage());

    int minX = this.getX();
    int maxX = this.getX() + this.getWidth();
    int centerX = (minX + maxX) / 2;

    int minY = this.getY();
    int maxY = this.getY() + this.getHeight();
    int centerY = (int) ((float) (minY + maxY) / 2);

    // int clampedCenterX = Mth.clamp(centerX, minX + stringWidth / 2, maxX - stringWidth / 2);
    guiGraphics.pose().pushPose();

    if (stringWidth >= this.getWidth() - 8) {
      float scale = (float) (this.getWidth() - 8) / stringWidth;

      guiGraphics.pose().scale(scale, scale, scale);
      guiGraphics.pose().translate(0, 1 - (float) font.lineHeight / 2, 0);
      guiGraphics.drawCenteredString(font, this.getMessage(), (int) (centerX / scale), (int) (centerY / scale), color);
    } else {
      guiGraphics.pose().translate(0, 1 - (float) font.lineHeight / 2, 0);
      guiGraphics.drawCenteredString(font, this.getMessage(), centerX, centerY, color);
    }
    guiGraphics.pose().popPose();
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

  public Boolean getValue() {
    return this.value;
  }

  public void setValue(Boolean value) {
    int i = this.values.indexOf(value);
    if (i != -1) {
      this.index = i;
    }

    this.updateValue(value);
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
                                   Component.translatable("narration.cycle_button.usage.focused", component));
      } else {
        narrationElementOutput.add(NarratedElementType.USAGE,
                                   Component.translatable("narration.cycle_button.usage.hovered", component));
      }
    }

  }

  public MutableComponent createDefaultNarrationMessage() {
    return wrapDefaultNarrationMessage(this.getMessage());
  }

  public boolean isMouseOver(double mouseX, double mouseY) {
    return this.active && this.visible && mouseX >= (double) this.getX() && mouseY >= (double) this.getY()
        && mouseX < (double) (this.getX() + this.width) && mouseY < (double) (this.getY() + this.height);
  }

  public interface OnValueChange {
    void onValueChange(BoolButton var1, Boolean var2);
  }

  public static class Builder {
    private final Function<BoolButton, MutableComponent> narrationProvider = BoolButton::createDefaultNarrationMessage;
    private final List<Boolean> values = ImmutableList.of(Boolean.TRUE, Boolean.FALSE);
    private int initialIndex;
    private Boolean initialValue;
    private OptionInstance.TooltipSupplier<Boolean> tooltipSupplier = (bool) -> null;
    private float buttonScale = 1;

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

    public BoolButton.Builder withScale(float buttonScale) {
      this.buttonScale = buttonScale;
      return this;
    }

    public BoolButton create(int x, int y, int width, int height, Component name,
        BoolButton.OnValueChange onValueChange) {

      Boolean bool = this.initialValue != null
                     ? this.initialValue
                     : this.values.get(this.initialIndex);
      return new BoolButton(x, y, width, height, name, name, this.initialIndex, bool, this.values,
                            this.narrationProvider, onValueChange, this.tooltipSupplier, buttonScale);

    }
  }
}