package club.iananderson.f3backport.util;

import java.awt.Color;

public class Rgb {
  private Rgb() {
  }

  public static int rgbInt(int r, int g, int b) {
    return (256 * 256 * r) + (256 * g) + b;
  }

  public static Color rgbColor(int rgb) {
    return new Color(rgb);
  }

  public static int red(int rgb) {
    return new Color(rgb).getRed();
  }

  public static int green(int rgb) {
    return new Color(rgb).getGreen();
  }

  public static int blue(int rgb) {
    return new Color(rgb).getBlue();
  }
}
