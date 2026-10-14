package club.iananderson.f3backport.client.gui.components.sliders.rgb;

import club.iananderson.f3backport.client.gui.components.boxes.ColorEditBox;
import club.iananderson.f3backport.util.Rgb;
import net.minecraft.ChatFormatting;

public class BlueSlider extends RgbSlider {
  public BlueSlider(int x, int y, int initial, ColorEditBox groupBox) {
    super(x, y, initial, groupBox, ChatFormatting.BLUE);
    this.groupBox = groupBox;
    this.blue = Rgb.blue(Integer.parseInt(groupBox.getValue()));
    this.defaultValue = Rgb.blue(groupBox.getGroup().getDefaultColor());
    this.updateMessage();
  }

  @Override
  public void applyValue() {
    this.red = Rgb.getRed(group);
    this.green = Rgb.getGreen(group);
    this.rgb = Rgb.rgbInt(this.red, this.green, this.getValueInt());

    Rgb.setRgb(group, this.rgb);
    this.groupBox.setValue(String.valueOf(this.rgb));
  }
}