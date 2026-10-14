package club.iananderson.f3backport.client.gui.components.sliders.rgb;

import club.iananderson.f3backport.Common;
import club.iananderson.f3backport.client.gui.components.boxes.ColorEditBox;
import club.iananderson.f3backport.client.gui.components.debug.entries.Groups;
import club.iananderson.f3backport.client.gui.components.sliders.BasicSlider;
import club.iananderson.f3backport.util.Rgb;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import org.jspecify.annotations.NonNull;

public class RgbSlider extends BasicSlider {
  public static final int SLIDER_PADDING = 2;
  protected final Groups group;
  protected ColorEditBox groupBox;
  protected int red;
  protected int green;
  protected int blue;
  protected int rgb;

  public RgbSlider(int x, int y, int initial, ColorEditBox groupBox, ChatFormatting textColor) {
    super(x, y, groupBox.getWidth() + 2, groupBox.getHeight() - 6, true, initial, 0, 255,
          groupBox.getGroup().getDefaultColor(), textColor, 1.0F);
    this.groupBox = groupBox;
    this.group = groupBox.getGroup();
    this.rgb = Integer.parseInt(groupBox.getValue());
    this.red = Rgb.red(rgb);
    this.green = Rgb.green(rgb);
    this.blue = Rgb.blue(rgb);
    this.updateMessage();
  }

  @Override
  public void onClick(double x, double y) {
    super.onClick(x, y);
  }

  @Override
  protected void onDrag(double mouseX, double mouseY, double dragX, double dragY) {
    super.onDrag(mouseX, mouseY, dragX, dragY);
  }

  public void setValue(int newValue) {
    double oldValue = this.value;
    this.value = this.snapToNearest((newValue - this.minValue) / (this.maxValue - this.minValue));
    if (!Mth.equal(oldValue, this.value)) {
      this.applyValue();
    }

    this.updateMessage();
  }

  @Override
  public void renderWidget(@NonNull GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
    super.renderWidget(graphics, mouseX, mouseY, partialTick);
  }

  @Override
  protected void updateMessage() {
    Component colorString = Common.literalText(this.getValueString());

    this.setMessage(colorString.copy().withStyle(this.textColor));
  }
}