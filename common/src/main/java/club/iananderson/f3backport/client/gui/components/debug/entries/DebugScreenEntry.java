package club.iananderson.f3backport.client.gui.components.debug.entries;

import club.iananderson.f3backport.client.gui.components.debug.DebugEntryCategory;
import club.iananderson.f3backport.client.gui.components.debug.DebugScreenDisplayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;
import org.jspecify.annotations.Nullable;

public interface DebugScreenEntry {
  void display(DebugScreenDisplayer displayer, @Nullable Level serverOrClientLevel, @Nullable LevelChunk clientChunk,
      @Nullable LevelChunk serverChunk);

  default boolean isAllowed(final boolean reducedDebugInfo) {
    return !reducedDebugInfo;
  }

  default DebugEntryCategory category() {
    return DebugEntryCategory.SCREEN_TEXT;
  }

  boolean enabled();
}
