package club.iananderson.f3backport.client.gui.components.sliders.rgb;

import club.iananderson.f3backport.client.gui.components.boxes.ColorEditBox;
import club.iananderson.f3backport.util.Rgb;
import net.minecraft.ChatFormatting;

public class RedSlider extends RgbSlider {
  public RedSlider(int x, int y, int initial, ColorEditBox groupBox) {
    super(x, y, initial, groupBox, ChatFormatting.RED);
    this.groupBox = groupBox;
    this.red = Rgb.red(Integer.parseInt(groupBox.getValue()));
    this.defaultValue = Rgb.red(groupBox.getGroup().getDefaultColor());
    this.updateMessage();
  }

  @Override
  protected void applyValue() {
    this.green = Rgb.getGreen(group);
    this.blue = Rgb.getBlue(group);
    this.rgb = Rgb.rgbInt(this.getValueInt(), this.green, this.blue);

    Rgb.setRgb(group, this.rgb);
    this.groupBox.setValue(String.valueOf(this.rgb));
  }
}