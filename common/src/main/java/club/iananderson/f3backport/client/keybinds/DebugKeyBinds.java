package club.iananderson.f3backport.client.keybinds;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.platform.InputConstants.Type;
import net.minecraft.client.KeyMapping;

public class DebugKeyBinds {
  public final KeyMapping keyDebugOverlay;
  public final KeyMapping keyDebugFpsCharts;
  public final KeyMapping keyDebugNetworkCharts;
  public final KeyMapping keyDebugProfilingChart;
  public final String debugCategory = "key.category.minecraft.debug";

  public DebugKeyBinds() {
    this.keyDebugOverlay = new KeyMapping("key.debug.overlay", Type.KEYSYM, InputConstants.KEY_F3, debugCategory);
    this.keyDebugProfilingChart = new KeyMapping("key.debug.profilingChart", Type.KEYSYM, InputConstants.KEY_1,
                                                 debugCategory);
    this.keyDebugFpsCharts = new KeyMapping("key.debug.fpsCharts", Type.KEYSYM, InputConstants.KEY_2, debugCategory);
    this.keyDebugNetworkCharts = new KeyMapping("key.debug.networkCharts", Type.KEYSYM, InputConstants.KEY_3,
                                                debugCategory);

  }
}
