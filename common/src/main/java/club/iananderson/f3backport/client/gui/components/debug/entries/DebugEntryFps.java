package club.iananderson.f3backport.client.gui.components.debug.entries;

import club.iananderson.f3backport.client.gui.components.debug.DebugScreenDisplayer;
import club.iananderson.f3backport.client.gui.components.debug.DebugScreenEntry;
import com.mojang.blaze3d.platform.Monitor;
import com.mojang.blaze3d.platform.VideoMode;
import java.util.Locale;
import net.minecraft.client.Minecraft;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;
import org.jspecify.annotations.Nullable;

public class DebugEntryFps implements DebugScreenEntry {
  public DebugEntryFps() {
  }

  public void display(final DebugScreenDisplayer displayer, final @Nullable Level serverOrClientLevel,
      final @Nullable LevelChunk clientChunk, final @Nullable LevelChunk serverChunk) {
    Minecraft mc = Minecraft.getInstance();

    int framerateLimit = mc.getWindow().getFramerateLimit();

    String frameRateLimitString = (framerateLimit == 260
                                   ? "inf"
                                   : String.valueOf(framerateLimit));

    String vsyncString = mc.options.enableVsync().get()
                         ? " (vsync)"
                         : " ";

    Monitor monitor = mc.getWindow().findBestMonitor();
    VideoMode activeMode = null;
    if (monitor != null) {
      activeMode = monitor.getCurrentMode();
    }

    String refreshRateString = activeMode == null
                               ? "0"
                               : refreshRateLabel(activeMode.getRefreshRate());

    displayer.addPriorityLine(
        String.format(Locale.ROOT, "%d fps T: %s%s @%sHz", mc.getFps(), frameRateLimitString, vsyncString,
                      refreshRateString));

    // displayer.addPriorityLine(mc.fpsString);
  }

  public String refreshRateLabel(float refreshRate) {
    return (double) refreshRate == Math.rint((double) refreshRate)
           ? Integer.toString((int) refreshRate)
           : String.format(Locale.ROOT, "%.2f", refreshRate);
  }

  public boolean isAllowed(final boolean reducedDebugInfo) {
    return true;
  }
}
