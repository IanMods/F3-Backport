package club.iananderson.f3backport.client.gui.components.debug.entries;

import club.iananderson.f3backport.client.gui.components.debug.DebugGroups;
import club.iananderson.f3backport.client.gui.components.debug.DebugScreenDisplayer;
import club.iananderson.f3backport.client.gui.components.debug.DebugScreenEntry;
import club.iananderson.f3backport.config.F3BackportClient;
import com.mojang.blaze3d.platform.GlUtil;
import com.mojang.blaze3d.platform.Monitor;
import com.mojang.blaze3d.platform.VideoMode;
import com.mojang.blaze3d.platform.Window;
import java.util.List;
import java.util.Locale;
import net.minecraft.client.Minecraft;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;
import org.jspecify.annotations.Nullable;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;

public class DebugEntrySystemSpecs implements DebugScreenEntry {
  private static @Nullable String cpuInfo;

  public DebugEntrySystemSpecs() {
  }

  public static String getCpuInfo() {
    if (cpuInfo == null) {
      cpuInfo = "<unknown>";

      try {
        CentralProcessor processor = (new SystemInfo()).getHardware().getProcessor();
        cpuInfo = String.format(Locale.ROOT, "%dx %s", processor.getLogicalProcessorCount(),
                                processor.getProcessorIdentifier().getName()).replaceAll("\\s+", " ");
      } catch (Throwable ignored) {
      }
    }

    return cpuInfo;
  }

  public void display(final DebugScreenDisplayer displayer, final @Nullable Level serverOrClientLevel,
      final @Nullable LevelChunk clientChunk, final @Nullable LevelChunk serverChunk) {
    Window window = Minecraft.getInstance().getWindow();
    Monitor monitor = window.findBestMonitor();
    VideoMode activeMode = monitor.getCurrentMode();

    displayer.addFactToGroup(DebugGroups.SYSTEM_SPECS, "Java",
                             (fact) -> fact.value(System.getProperty("java.version")));
    displayer.addFactToGroup(DebugGroups.SYSTEM_SPECS, "CPU", (fact) -> fact.value(getCpuInfo()));
    displayer.addFactToGroup(DebugGroups.SYSTEM_SPECS, "Display",
                             (fact) -> fact.value(activeMode.getWidth()).text("x").value(activeMode.getHeight()));
    displayer.addFactToGroup(DebugGroups.SYSTEM_SPECS, "Window",
                             (fact) -> fact.value(window.getScreenWidth()).text("x").value(window.getScreenHeight()));
    displayer.addToGroup(DebugGroups.SYSTEM_SPECS,
                         List.of(GlUtil.getRenderer(), "OpenGL " + GlUtil.getOpenGLVersion()));
  }

  public boolean isAllowed(final boolean reducedDebugInfo) {
    return true;
  }

  @Override
  public boolean enabled() {
    return F3BackportClient.getEnableSystemSpecs();
  }
}