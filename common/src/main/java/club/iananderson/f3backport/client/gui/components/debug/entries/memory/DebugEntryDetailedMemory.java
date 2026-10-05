package club.iananderson.f3backport.client.gui.components.debug.entries.memory;

import club.iananderson.f3backport.client.gui.components.debug.DebugFact;
import club.iananderson.f3backport.client.gui.components.debug.DebugGroups;
import club.iananderson.f3backport.client.gui.components.debug.DebugScreenDisplayer;
import club.iananderson.f3backport.client.gui.components.debug.entries.DebugScreenEntry;
import club.iananderson.f3backport.config.F3BackportClient;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;
import org.jspecify.annotations.Nullable;

public class DebugEntryDetailedMemory implements DebugScreenEntry {
  private final MemoryMXBean memoryBean = ManagementFactory.getMemoryMXBean();

  public DebugEntryDetailedMemory() {
  }

  private static long bytesToMebibytes(final long used) {
    return used / 1024L / 1024L;
  }

  private static void getMemoryUsage(final DebugFact fact, final MemoryUsage memoryUsage) {
    fact.text("i=")
        .formattedValue("%03d", bytesToMebibytes(memoryUsage.getInit()))
        .text("MiB u=")
        .formattedValue("%03d", bytesToMebibytes(memoryUsage.getUsed()))
        .text("MiB c=")
        .formattedValue("%03d", bytesToMebibytes(memoryUsage.getCommitted()))
        .text("MiB m=")
        .formattedValue("%03d", bytesToMebibytes(memoryUsage.getMax()))
        .text("MiB");
  }

  public void display(final DebugScreenDisplayer displayer, final @Nullable Level serverOrClientLevel,
      final @Nullable LevelChunk clientChunk, final @Nullable LevelChunk serverChunk) {
    displayer.addFactToGroup(DebugGroups.MEMORY, "Heap",
                             (fact) -> getMemoryUsage(fact, this.memoryBean.getHeapMemoryUsage()));
    displayer.addFactToGroup(DebugGroups.MEMORY, "Non-heap",
                             (fact) -> getMemoryUsage(fact, this.memoryBean.getNonHeapMemoryUsage()));
  }

  public boolean isAllowed(final boolean reducedDebugInfo) {
    return true;
  }

  @Override
  public boolean enabled() {
    return F3BackportClient.getEnableDetailedMemory();
  }
}
