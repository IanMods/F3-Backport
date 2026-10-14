package club.iananderson.f3backport.util;

import club.iananderson.f3backport.client.gui.components.debug.entries.Groups;
import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

public class Rgb {
  private Rgb() {
  }

  public static int rgbInt(int r, int g, int b) {
    return new Color(r, g, b).getRGB();
  }

  public static int rgbaInt(int r, int g, int b, int a) {
    return new Color(r, g, b, a).getRGB();
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

  public static Map<String, Integer> groupMap(int rgb) {
    Map<String, Integer> rgbMap = new HashMap<>();

    rgbMap.put("r", rgbColor(rgb).getRed());
    rgbMap.put("g", rgbColor(rgb).getGreen());
    rgbMap.put("b", rgbColor(rgb).getBlue());
    rgbMap.put("rgb", rgb);

    return rgbMap;
  }

  public static Map<String, Integer> defaultGroupMap(Groups group) {
    Map<String, Integer> defaultRgbMap = new HashMap<>();

    int rgb = group.getDefaultColor();

    defaultRgbMap.put("r", rgbColor(rgb).getRed());
    defaultRgbMap.put("g", rgbColor(rgb).getGreen());
    defaultRgbMap.put("b", rgbColor(rgb).getBlue());
    defaultRgbMap.put("rgb", rgb);

    return defaultRgbMap;
  }

  public static void setRgb(Groups group, int rgb) {
    group.getRgbMap().put("r", red(rgb));
    group.getRgbMap().put("g", green(rgb));
    group.getRgbMap().put("b", blue(rgb));
    group.getRgbMap().put("rgb", rgb);
  }

  public static int getRgb(Groups groups) {
    return groups.getRgbMap().get("rgb");
  }

  public static int getRed(Groups groups) {
    return groups.getRgbMap().get("r");
  }

  public static int getGreen(Groups groups) {
    return groups.getRgbMap().get("g");
  }

  public static int getBlue(Groups groups) {
    return groups.getRgbMap().get("b");
  }
}
