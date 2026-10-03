package club.iananderson.f3backport.client.gui.components.debug.entries;

import club.iananderson.f3backport.client.gui.components.debug.DebugGroups;
import club.iananderson.f3backport.client.gui.components.debug.DebugScreenDisplayer;
import club.iananderson.f3backport.client.gui.components.debug.DebugScreenEntry;
import club.iananderson.f3backport.util.device.DeviceType;
import com.mojang.blaze3d.platform.GlUtil;
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
  private static @Nullable String gpuInfo;

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
    displayer.addFactToGroup(DebugGroups.SYSTEM_SPECS, "Java",
                             (fact) -> fact.value(System.getProperty("java.version")));
    displayer.addFactToGroup(DebugGroups.SYSTEM_SPECS, "CPU", (fact) -> fact.value(getCpuInfo()));
    displayer.addFactToGroup(DebugGroups.SYSTEM_SPECS, "Display",
                             (fact) -> fact.value(window.getWidth()).text("x").value(window.getHeight()));
    displayer.addFactToGroup(DebugGroups.SYSTEM_SPECS, "Window",
                             (fact) -> fact.value(window.getScreenWidth()).text("x").value(window.getScreenHeight()));
    displayer.addToGroup(DebugGroups.SYSTEM_SPECS, List.of(GlUtil.getRenderer(), GlUtil.getOpenGLVersion()));
  }

  private String firstLine(final String value) {
    return (String) value.lines().findFirst().orElse(value);
  }

  private String typeName(final DeviceType type) {
    String var10000;
    switch (type) {
      case OTHER -> var10000 = "";
      case INTEGRATED -> var10000 = " (iGPU)";
      case DISCRETE -> var10000 = " (dGPU)";
      case VIRTUAL -> var10000 = " (vGPU)";
      case CPU -> var10000 = " (software)";
      default -> throw new MatchException((String) null, (Throwable) null);
    }

    return var10000;
  }

  public boolean isAllowed(final boolean reducedDebugInfo) {
    return true;
  }
}