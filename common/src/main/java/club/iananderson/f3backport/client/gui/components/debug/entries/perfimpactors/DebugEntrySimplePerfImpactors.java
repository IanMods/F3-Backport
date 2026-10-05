package club.iananderson.f3backport.client.gui.components.debug.entries.perfimpactors;

import club.iananderson.f3backport.client.gui.components.debug.DebugScreenDisplayer;
import club.iananderson.f3backport.client.gui.components.debug.entries.DebugGroups;
import club.iananderson.f3backport.client.gui.components.debug.entries.DebugScreenEntry;
import club.iananderson.f3backport.config.F3BackportClient;
import net.minecraft.client.CloudStatus;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;
import org.jspecify.annotations.Nullable;

public class DebugEntrySimplePerfImpactors implements DebugScreenEntry {
  public DebugEntrySimplePerfImpactors() {
  }

  public void display(final DebugScreenDisplayer displayer, final @Nullable Level serverOrClientLevel,
      final @Nullable LevelChunk clientChunk, final @Nullable LevelChunk serverChunk) {
    Minecraft minecraft = Minecraft.getInstance();
    Options options = minecraft.options;
    displayer.addFactToGroup(DebugGroups.PERFORMANCE_IMPACTORS, "Clouds", (fact) -> fact.value(
        options.cloudStatus().get() == CloudStatus.OFF
        ? "Off"
        : (options.cloudStatus().get() == CloudStatus.FAST
           ? "Fast"
           : "Fancy")));
    displayer.addFactToGroup(DebugGroups.PERFORMANCE_IMPACTORS, "Biome Blend",
                             (fact) -> fact.value(options.biomeBlendRadius().get()));
  }

  public boolean isAllowed(final boolean reducedDebugInfo) {
    return true;
  }

  @Override
  public boolean enabled() {
    return F3BackportClient.getEnableSimplePerformanceImpactors();
  }
}
