package club.iananderson.f3backport.client.gui.components.sliders;

import club.iananderson.f3backport.Common;
import com.mojang.blaze3d.systems.RenderSystem;
import java.awt.Color;
import java.text.DecimalFormat;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.jspecify.annotations.NonNull;
import org.lwjgl.glfw.GLFW;

public class BasicSlider extends AbstractSliderButton {
  public static final int SLIDER_PADDING = 2;
  private static final ResourceLocation SLIDER_SPRITE = ResourceLocation.withDefaultNamespace("widget/slider");
  private static final ResourceLocation HIGHLIGHTED_SPRITE = ResourceLocation.withDefaultNamespace(
      "widget/slider_highlighted");
  private static final ResourceLocation SLIDER_HANDLE_SPRITE = ResourceLocation.withDefaultNamespace(
      "widget/slider_handle");
  private static final ResourceLocation SLIDER_HANDLE_HIGHLIGHTED_SPRITE = ResourceLocation.withDefaultNamespace(
      "widget/slider_handle_highlighted");
  public float buttonScale;
  protected boolean drawString;
  protected boolean canChangeValue;
  protected double minValue;
  protected double maxValue;
  protected double defaultValue;
  protected double stepSize;
  protected ChatFormatting textColor;
  private DecimalFormat format;

  private BasicSlider(int x, int y, int width, int height, boolean drawString, double initial, float buttonScale) {
    super(x, y, width, height, Component.empty(), 0D);
    this.drawString = drawString;
    this.value = snapToNearest(initial);
    this.buttonScale = buttonScale;
  }

  protected BasicSlider(int x, int y, int width, int height, boolean drawString, double initial, double minValue,
      double maxValue, double defaultValue, double stepSize, int precision, ChatFormatting textColor,
      float buttonScale) {
    this(x, y, width, height, drawString, initial, buttonScale);
    this.minValue = minValue;
    this.maxValue = maxValue;
    this.defaultValue = defaultValue;
    this.value = this.snapToNearest((initial - minValue) / (maxValue - minValue));
    this.stepSize = Math.abs(stepSize);
    this.textColor = textColor;
    this.drawString = drawString;

    if (stepSize == 0D) {
      precision = Math.min(precision, 4);

      StringBuilder builder = new StringBuilder("0");

      if (precision > 0) {
        builder.append('.');
      }

      while (precision-- > 0) {
        builder.append('0');
      }

      this.format = new DecimalFormat(builder.toString());
    } else if (Mth.equal(this.stepSize, Math.floor(this.stepSize))) {
      this.format = new DecimalFormat("0");
    } else {
      this.format = new DecimalFormat(Double.toString(this.stepSize).replaceAll("\\d", "0"));
    }

    this.updateMessage();
  }

  protected BasicSlider(int x, int y, int width, int height, boolean drawString, double initial, double minValue,
      double maxValue, double defaultValue, ChatFormatting textColor, float buttonScale) {
    this(x, y, width, height, drawString, initial, minValue, maxValue, defaultValue, 1D, 0, textColor, buttonScale);
  }

  protected BasicSlider(int x, int y, int width, int height, boolean drawString, double initial, double minValue,
      double maxValue, double defaultValue, double stepSize, int precision, float buttonScale) {
    this(x, y, width, height, drawString, initial, minValue, maxValue, defaultValue, stepSize, precision,
         ChatFormatting.WHITE, buttonScale);
  }

  protected BasicSlider(int x, int y, int width, int height, boolean drawString, double initial, double minValue,
      double maxValue, double defaultValue, float buttonScale) {
    this(x, y, width, height, drawString, initial, minValue, maxValue, defaultValue, 1D, 0, ChatFormatting.WHITE,
         buttonScale);
  }

  public void onRightClick() {
    this.setValue(defaultValue);
  }

  public int getTextureY() {
    int i = this.isFocused() && !this.canChangeValue
            ? 1
            : 0;
    return i * 20;
  }

  public int getHandleTextureY() {
    int i = !this.isHovered && !this.canChangeValue
            ? 2
            : 3;
    return i * 20;
  }

  public int getFgColor() {
    return this.active
           ? 16777215
           : 10526880;
  }

  protected double snapToNearest(double value) {
    if (stepSize <= 0D) {
      return Mth.clamp(value, 0D, 1D);
    }

    value = Mth.lerp(Mth.clamp(value, 0D, 1D), this.minValue, this.maxValue);

    value = (stepSize * Math.round(value / stepSize));

    if (this.minValue > this.maxValue) {
      value = Mth.clamp(value, this.maxValue, this.minValue);
    } else {
      value = Mth.clamp(value, this.minValue, this.maxValue);
    }

    return Mth.map(value, this.minValue, this.maxValue, 0D, 1D);
  }

  public double getValue() {
    return this.value * (this.maxValue - this.minValue) + this.minValue;
  }

  public void setValue(double value) {
    double oldValue = this.value;
    this.value = this.snapToNearest((value - this.minValue) / (this.maxValue - this.minValue));
    if (!Mth.equal(oldValue, this.value)) {
      this.applyValue();
    }

    this.updateMessage();
  }

  public double getValueDouble() {
    return Math.round(this.getValue() * 10.0) / 10.0;
  }

  public long getValueLong() {
    return Math.round(this.getValue());
  }

  public int getValueInt() {
    return (int) this.getValueLong();
  }

  public String getValueString() {
    return this.format.format(this.getValue());
  }

  public void setSliderValue(double value) {
    double oldValue = this.value;
    this.value = this.snapToNearest(value);
    if (!Mth.equal(oldValue, this.value)) {
      this.applyValue();
    }

    this.updateMessage();
  }

  @Override
  protected void applyValue() {
  }

  private void setValueFromMouse(double mouseX) {
    this.setSliderValue((mouseX - (this.getX() + 4)) / (this.width - 8));
  }

  @Override
  public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
    if (this.active && this.visible) {
      if (mouseButton == GLFW.GLFW_MOUSE_BUTTON_2) {
        this.playDownSound(Minecraft.getInstance().getSoundManager());
        this.onRightClick();
      }
    }

    return super.mouseClicked(mouseX, mouseY, mouseButton);
  }

  @Override
  protected void onDrag(double mouseX, double mouseY, double dragX, double dragY) {
    super.onDrag(mouseX, mouseY, dragX, dragY);
    this.setValueFromMouse(mouseX);
  }

  @Override
  public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
    boolean left = keyCode == GLFW.GLFW_KEY_LEFT;
    boolean right = keyCode == GLFW.GLFW_KEY_RIGHT;
    if (left || right) {
      float f = left
                ? -1F
                : 1F;
      if (stepSize <= 0D) {
        this.setSliderValue(this.value + (f / (this.width - 8)));
      } else {
        this.setValue(this.getValue() + f * this.stepSize);
      }
      return true;
    }

    return false;
  }

  @Override
  protected void updateMessage() {
    if (this.drawString) {
      this.setMessage(Common.literalText(this.getValueString()));
    } else {
      this.setMessage(Component.empty());
    }
  }

  public ResourceLocation getSprite() {
    return this.isFocused() && !this.canChangeValue
           ? HIGHLIGHTED_SPRITE
           : SLIDER_SPRITE;
  }

  public ResourceLocation getHandleSprite() {
    return !this.isHovered && !this.canChangeValue
           ? SLIDER_HANDLE_SPRITE
           : SLIDER_HANDLE_HIGHLIGHTED_SPRITE;
  }

  @Override
  public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
    guiGraphics.setColor(1.0F, 1.0F, 1.0F, this.alpha);
    RenderSystem.enableBlend();
    RenderSystem.defaultBlendFunc();
    RenderSystem.enableDepthTest();
    guiGraphics.pose().pushPose();
    guiGraphics.pose().scale(buttonScale, buttonScale, buttonScale);
    guiGraphics.blitSprite(this.getSprite(), this.getX(), this.getY(), (int) (this.getWidth()),
                           (int) (this.getHeight()));
    guiGraphics.blitSprite(this.getHandleSprite(), this.getX() + (int) (this.value * (double) (this.width - 8)),
                           this.getY(), (int) (8 * buttonScale), (int) (this.getHeight()));
    guiGraphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
    Color color = this.active
                  ? new Color(255, 255, 255)
                  : new Color(160, 160, 160);

    Minecraft mc = Minecraft.getInstance();
    this.renderString(guiGraphics, mc.font, color.getRGB() | Mth.ceil(this.alpha * 255.0F) << 24);
    guiGraphics.pose().popPose();
  }

  public void renderString(@NonNull GuiGraphics guiGraphics, @NonNull Font font, int color) {
    int stringWidth = font.width(this.getMessage());

    int minX = this.getX() + width;
    int maxX = this.getX() + this.getWidth() - width;
    int centerX = (minX + maxX) / 2;

    int minY = this.getY();
    int maxY = this.getY() + this.getHeight();
    int centerY = (int) ((float) (minY + maxY - font.lineHeight) / 2 + (1 * buttonScale));

    // int clampedCenterX = Mth.clamp(centerX, minX + stringWidth / 2, maxX - stringWidth / 2);

    if (stringWidth > this.getWidth()) {
      int padding = 4;
      int avgWidth = (int) ((float) (stringWidth + this.getWidth() + padding) / 2);
      float scale = 1 - (float) (stringWidth + padding - this.getWidth()) / avgWidth;

      guiGraphics.pose().pushPose();
      guiGraphics.pose().scale(scale, scale, scale);
      guiGraphics.drawCenteredString(font, this.getMessage(), (int) (centerX / scale), (int) (centerY / scale), color);
      guiGraphics.pose().popPose();
    } else {
      guiGraphics.drawCenteredString(font, this.getMessage(), centerX, centerY, color);
    }
  }
}