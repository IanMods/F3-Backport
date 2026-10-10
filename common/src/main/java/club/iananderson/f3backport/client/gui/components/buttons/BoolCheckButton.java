package club.iananderson.f3backport.client.gui.components.buttons;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.systems.RenderSystem;
import java.awt.Color;
import java.util.List;
import java.util.function.Function;
import net.minecraft.client.Minecraft;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.OptionInstance.TooltipSupplier;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.util.Mth;
import org.jspecify.annotations.NonNull;

public class BoolCheckButton extends BoolButton {
  BoolCheckButton(int x, int y, int width, int height, Component message, Component name, int index, Boolean value,
      List<Boolean> values, Function<BoolButton, MutableComponent> narrationProvider, OnValueChange onValueChange,
      TooltipSupplier<Boolean> tooltipSupplier, float buttonScale) {
    super(x, y, width, height, message, name, index, value, values, narrationProvider, onValueChange, tooltipSupplier,
          buttonScale);
  }

  public static BoolCheckButton.Builder builder(boolean initialValue) {
    return new BoolCheckButton.Builder().withInitialValue(initialValue);
  }

  @Override
  protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
    guiGraphics.setColor(1.0F, 1.0F, 1.0F, this.alpha);
    RenderSystem.enableBlend();
    RenderSystem.enableDepthTest();
    guiGraphics.pose().pushPose();
    guiGraphics.pose().scale(buttonScale, buttonScale, buttonScale);
    guiGraphics.blitSprite(SPRITES.get(this.value, this.isHoveredOrFocused()), (int) (this.getX() / buttonScale),
                           (int) (this.getY() / buttonScale), (int) (this.getWidth() / buttonScale),
                           (int) (this.getHeight() * buttonScale));
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
    int minY = this.getY();
    int maxY = this.getY() + this.getHeight();
    int centerY = (int) ((float) (minY + maxY - font.lineHeight) / 2);
    int startY = (centerY) + 1;

    int startX = this.getX() + this.getWidth() + 2;

    guiGraphics.drawString(font, this.getMessage(), (int) (startX), (int) (startY), color);
  }

  public static class Builder extends BoolButton.Builder {
    private final Function<BoolButton, MutableComponent> narrationProvider = BoolButton::createDefaultNarrationMessage;
    private final List<Boolean> values = ImmutableList.of(Boolean.TRUE, Boolean.FALSE);
    private int initialIndex;
    private Boolean initialValue;
    private OptionInstance.TooltipSupplier<Boolean> tooltipSupplier = (bool) -> null;
    private float buttonScale = 1;

    public BoolCheckButton.Builder withTooltip(OptionInstance.TooltipSupplier<Boolean> tooltipSupplier) {
      this.tooltipSupplier = tooltipSupplier;
      return this;
    }

    public BoolCheckButton.Builder withInitialValue(Boolean initialValue) {
      this.initialValue = initialValue;
      int i = this.values.indexOf(initialValue);
      if (i != -1) {
        this.initialIndex = i;
      }

      return this;
    }

    public BoolCheckButton.Builder withScale(float buttonScale) {
      this.buttonScale = buttonScale;
      return this;
    }

    public BoolCheckButton create(int x, int y, int buttonSize, Component name,
        BoolCheckButton.OnValueChange onValueChange) {

      Boolean bool = this.initialValue != null
                     ? this.initialValue
                     : this.values.get(this.initialIndex);
      return new BoolCheckButton(x, y, buttonSize, buttonSize, name, name, this.initialIndex, bool, this.values,
                                 this.narrationProvider, onValueChange, this.tooltipSupplier, this.buttonScale);

    }
  }
}
