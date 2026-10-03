package club.iananderson.f3backport.client.gui.components.debug.entries;

import club.iananderson.f3backport.client.gui.components.debug.DebugScreenDisplayer;
import club.iananderson.f3backport.client.gui.components.debug.DebugScreenEntry;
import net.minecraft.client.Minecraft;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;
import org.jspecify.annotations.Nullable;

public class DebugEntryFps implements DebugScreenEntry {
  public DebugEntryFps() {
  }

  public void display(final DebugScreenDisplayer displayer, final @Nullable Level serverOrClientLevel,
      final @Nullable LevelChunk clientChunk, final @Nullable LevelChunk serverChunk) {
    Minecraft minecraft = Minecraft.getInstance();
    // int framerateLimit = minecraft.getWindow().getFramerateLimit();
    displayer.addPriorityLine(minecraft.fpsString);
    // displayer.addPriorityLine(String.format(Locale.ROOT, "%d fps T: %s%s @%sHz", minecraft.getFps(), framerateLimit == 260 ? "inf" : framerateLimit, presentModeName((GpuSurface.PresentMode)surfaceConfiguration.map(GpuSurface.Configuration::presentMode).orElse((Object)null)), activeMode == null ? "0" : activeMode.refreshRateLabel()));
  }

  public boolean isAllowed(final boolean reducedDebugInfo) {
    return true;
  }

  // private static String presentModeName(final GpuSurface.@Nullable PresentMode mode) {
  //   String var10000;
  //   switch (mode) {
  //     case null -> var10000 = "";
  //     case IMMEDIATE -> var10000 = " (immediate)";
  //     case MAILBOX -> var10000 = " (mailbox)";
  //     case FIFO -> var10000 = " (fifo)";
  //     case FIFO_RELAXED -> var10000 = " (fifo relaxed)";
  //     default -> throw new MatchException((String)null, (Throwable)null);
  //   }
  //
  //   return var10000;
  // }
}
