package club.iananderson.f3backport.client.keybinds;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.platform.InputConstants.Type;
import net.minecraft.client.KeyMapping;

public class DebugKeyBinds {
  public final KeyMapping keyDebugOverlay;
  public final KeyMapping keyDebugFpsCharts;
  public final KeyMapping keyDebugNetworkCharts;
  public final KeyMapping keyDebugProfilingChart;

  public DebugKeyBinds() {
    this.keyDebugOverlay = new KeyMapping("key.f3backport.debug.overlay", Type.KEYSYM, InputConstants.KEY_F3,
                                          "key.category.f3backport.debug");
    this.keyDebugProfilingChart = new KeyMapping("key.f3backport.debug.profilingChart", Type.KEYSYM,
                                                 InputConstants.KEY_1, "key.category.f3backport.debug");
    this.keyDebugFpsCharts = new KeyMapping("key.f3backport.debug.fpsCharts", Type.KEYSYM, InputConstants.KEY_2,
                                            "key.category.f3backport.debug");
    this.keyDebugNetworkCharts = new KeyMapping("key.f3backport.debug.networkCharts", Type.KEYSYM, InputConstants.KEY_3,
                                                "key.category.f3backport.debug");
  }
}
