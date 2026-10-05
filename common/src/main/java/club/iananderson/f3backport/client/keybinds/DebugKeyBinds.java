package club.iananderson.f3backport.client.keybinds;

import club.iananderson.f3backport.client.gui.screens.MainOptionsScreen;
import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.platform.InputConstants.Type;
import net.minecraft.client.KeyMapping;

public class DebugKeyBinds {
  public static KeyMapping keyDebugOverlay = new KeyMapping("key.f3backport.debug.overlay", Type.KEYSYM,
                                                            InputConstants.KEY_F3, "key.category.f3backport.debug");
  public static KeyMapping keyDebugFpsCharts = new KeyMapping("key.f3backport.debug.fpsCharts", Type.KEYSYM,
                                                              InputConstants.KEY_2, "key.category.f3backport.debug");
  public static KeyMapping keyDebugNetworkCharts = new KeyMapping("key.f3backport.debug.networkCharts", Type.KEYSYM,
                                                                  InputConstants.KEY_3,
                                                                  "key.category.f3backport.debug");
  public static KeyMapping keyDebugProfilingChart = new KeyMapping("key.f3backport.debug.profilingChart", Type.KEYSYM,
                                                                   InputConstants.KEY_1,
                                                                   "key.category.f3backport.debug");
  public static KeyMapping keyDebugOptions = new KeyMapping("key.f3backport.debug.debugOptions", Type.KEYSYM,
                                                            InputConstants.KEY_DELETE, "key.category.f3backport.debug");

  public DebugKeyBinds() {
  }

  public static void optionsKeyInput() {
    if (keyDebugOptions.consumeClick()) {
      MainOptionsScreen.getInstance(null).open();
    }
  }
}
