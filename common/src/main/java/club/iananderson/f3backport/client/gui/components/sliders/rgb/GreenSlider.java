package club.iananderson.f3backport.client.gui.components.sliders.rgb;

import club.iananderson.f3backport.client.gui.components.boxes.ColorEditBox;
import club.iananderson.f3backport.util.Rgb;
import net.minecraft.ChatFormatting;

public class GreenSlider extends RgbSlider {
  public GreenSlider(int x, int y, int initial, ColorEditBox groupBox) {
    super(x, y, initial, groupBox, ChatFormatting.GREEN);
    this.groupBox = groupBox;
    this.green = Rgb.green(Integer.parseInt(groupBox.getValue()));
    this.defaultValue = Rgb.green(groupBox.getGroup().getDefaultColor());
    this.updateMessage();
  }

  @Override
  protected void applyValue() {
    this.red = Rgb.getRed(group);
    this.blue = Rgb.getBlue(group);
    this.rgb = Rgb.rgbInt(this.red, this.getValueInt(), this.blue);

    Rgb.setRgb(group, this.rgb);
    this.groupBox.setValue(String.valueOf(this.rgb));
  }
}