package club.iananderson.f3backport.client.gui.components.debug.entries;

import club.iananderson.f3backport.client.gui.components.debug.DebugGroups;
import club.iananderson.f3backport.client.gui.components.debug.DebugScreenDisplayer;
import club.iananderson.f3backport.client.gui.components.debug.DebugScreenEntry;
import club.iananderson.f3backport.config.F3BackportClient;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;
import org.jspecify.annotations.Nullable;

public class DebugEntryEntityRenderStats implements DebugScreenEntry {
  public DebugEntryEntityRenderStats() {
  }

  public void display(final DebugScreenDisplayer displayer, final @Nullable Level serverOrClientLevel,
      final @Nullable LevelChunk clientChunk, final @Nullable LevelChunk serverChunk) {
    ClientLevel clientLevel = Minecraft.getInstance().level;
    if (clientLevel != null) {
      displayer.addFactToGroup(DebugGroups.MISC, "Entities",
                               (fact) -> fact.value(Minecraft.getInstance().levelRenderer.getEntityStatistics()));
      displayer.addFactToGroup(DebugGroups.MISC, "Simulation Distance",
                               (fact) -> fact.value(clientLevel.getServerSimulationDistance()));
    }
  }

  public boolean isAllowed(final boolean reducedDebugInfo) {
    return true;
  }

  @Override
  public boolean enabled() {
    return F3BackportClient.getEnableEntityRenderStats();
  }
}
