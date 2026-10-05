package club.iananderson.f3backport.client.gui.components.debug.entries.performanceImpactors;

import club.iananderson.f3backport.client.gui.components.debug.DebugGroups;
import club.iananderson.f3backport.client.gui.components.debug.DebugScreenDisplayer;
import club.iananderson.f3backport.client.gui.components.debug.entries.DebugScreenEntry;
import club.iananderson.f3backport.config.F3BackportClient;
import net.minecraft.client.CloudStatus;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;
import org.jspecify.annotations.Nullable;

public class DebugEntrySimplePerformanceImpactors implements DebugScreenEntry {
  public DebugEntrySimplePerformanceImpactors() {
  }

  public void display(final DebugScreenDisplayer displayer, final @Nullable Level serverOrClientLevel,
      final @Nullable LevelChunk clientChunk, final @Nullable LevelChunk serverChunk) {
    Minecraft minecraft = Minecraft.getInstance();
    Options options = minecraft.options;
    // displayer.addFactToGroup(DebugGroups.PERFORMANCE_IMPACTORS, "OIT", (fact) -> fact.value((Boolean)options.improvedTransparency().get() ? "On" : "Off"));
    displayer.addFactToGroup(DebugGroups.PERFORMANCE_IMPACTORS, "Clouds", (fact) -> fact.value(
        options.cloudStatus().get() == CloudStatus.OFF
        ? "Off"
        : (options.cloudStatus().get() == CloudStatus.FAST
           ? "Fast"
           : "Fancy")));
    displayer.addFactToGroup(DebugGroups.PERFORMANCE_IMPACTORS, "Biome Blend",
                             (fact) -> fact.value(options.biomeBlendRadius().get()));
    // TextureFilteringMethod filteringMethod = (TextureFilteringMethod)options.textureFiltering().get();
    // if (filteringMethod == TextureFilteringMethod.ANISOTROPIC) {
    //   displayer.addFactToGroup(DebugGroups.PERFORMANCE_IMPACTORS, "Filtering", (fact) -> fact.value(filteringMethod.caption().getString()).text(" ").value(options.maxAnisotropyValue()).text("x"));
    // } else {
    //   displayer.addFactToGroup(DebugGroups.PERFORMANCE_IMPACTORS, "Filtering", (fact) -> fact.value(filteringMethod.caption().getString()));
    // }

    // boolean isMultiDrawIndirect = minecraft.levelRenderer.isChunkRenderingUsingMultiDrawIndirect();
    // displayer.addFactToGroup(DebugGroups.PERFORMANCE_IMPACTORS, "Multi-draw", (fact) -> fact.value(isMultiDrawIndirect ? "On" : "Off"));
  }

  public boolean isAllowed(final boolean reducedDebugInfo) {
    return true;
  }

  @Override
  public boolean enabled() {
    return F3BackportClient.getEnableSimplePerformanceImpactors();
  }
}
