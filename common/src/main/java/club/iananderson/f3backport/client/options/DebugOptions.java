package club.iananderson.f3backport.client.options;

import net.minecraft.client.Minecraft;
import net.minecraft.client.OptionInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

public class DebugOptions {
  private static final Component DEBUG_GUI_SCALE_TOOLTIP = Component.translatable("options.debugGuiScale.tooltip");
  private final OptionInstance<Integer> debugGuiScale;

  public DebugOptions() {
    this.debugGuiScale = new OptionInstance<>("options.guiScale",
                                              OptionInstance.cachedConstantTooltip(DEBUG_GUI_SCALE_TOOLTIP),
                                              (caption, value) -> {
                                                MutableComponent var10000;
                                                switch (value) {
                                                  case -1 -> var10000 = Component.translatable(
                                                      "options.debugGuiScale.unchanged");
                                                  case 0 -> var10000 = Component.translatable("options.guiScale.auto");
                                                  default -> var10000 = Component.literal(Integer.toString(value));
                                                }

                                                return var10000;
                                              }, new OptionInstance.ClampingLazyMaxIntRange(-1, () -> {
      Minecraft minecraft = Minecraft.getInstance();
      return !minecraft.isRunning()
             ? 2147483646
             : minecraft.getWindow().calculateScale(0, minecraft.isEnforceUnicode());
    }, 2147483646), 0, (var0) -> {
    });

  }

  public OptionInstance<Integer> debugGuiScale() {
    return this.debugGuiScale;
  }

}
