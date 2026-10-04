package club.iananderson.f3backport;

import com.demonwav.mcdev.annotations.Translatable;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;

public class Common {

  public Common() {
  }

  public static void init() {
  }

  public static ResourceLocation location(String path) {
    return ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, path);
  }

  // Used to make porting new text to older versions easier
  public static MutableComponent literalText(String text) {
    return Component.literal(text);
  }

  // Used to make porting new text to older versions easier
  public static MutableComponent translatedText(@Translatable(foldMethod = true) String key) {
    return Component.translatable(key);
  }

  // Used to make porting new text to older versions easier
  public static MutableComponent translatedText(@Translatable(foldMethod = true) String key, Object... args) {
    return Component.translatable(key, args);
  }

  // Used to make porting new text to older versions easier
  public static Tooltip newTooltip(@Translatable(foldMethod = true) String key) {
    return Tooltip.create(translatedText(key));
  }

  // Used to make porting new text to older versions easier
  public static Tooltip newTooltip(@Translatable(foldMethod = true) String key, Object... args) {
    return Tooltip.create(translatedText(key, args));
  }

  private void initConfig() {
  }
}