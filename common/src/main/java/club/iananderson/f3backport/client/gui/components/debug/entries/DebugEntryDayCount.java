package club.iananderson.f3backport.client.gui.components.debug.entries;

import club.iananderson.f3backport.client.gui.components.debug.DebugGroups;
import club.iananderson.f3backport.client.gui.components.debug.DebugScreenDisplayer;
import club.iananderson.f3backport.client.gui.components.debug.DebugScreenEntry;
import club.iananderson.f3backport.config.F3BackportClient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;
import org.jspecify.annotations.Nullable;

public class DebugEntryDayCount implements DebugScreenEntry {
  public DebugEntryDayCount() {
  }

  public void display(final DebugScreenDisplayer displayer, final @Nullable Level serverOrClientLevel,
      final @Nullable LevelChunk clientChunk, final @Nullable LevelChunk serverChunk) {
    if (serverOrClientLevel != null) {
      displayer.addFactToGroup(DebugGroups.MISC, "Day",
                               (fact) -> fact.value(serverOrClientLevel.getDayTime() / 24000L));
    }

  }

  @Override
  public boolean enabled() {
    return F3BackportClient.getEnableDayCount();
  }
}
