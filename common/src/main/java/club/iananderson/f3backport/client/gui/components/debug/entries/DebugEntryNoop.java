package club.iananderson.f3backport.client.gui.components.debug.entries;

import club.iananderson.f3backport.client.gui.components.debug.DebugEntryCategory;
import club.iananderson.f3backport.client.gui.components.debug.DebugScreenDisplayer;
import club.iananderson.f3backport.client.gui.components.debug.DebugScreenEntry;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;
import org.jspecify.annotations.Nullable;

public class DebugEntryNoop implements DebugScreenEntry {
  private final boolean isAllowedWithReducedDebugInfo;

  public DebugEntryNoop() {
    this(false);
  }

  public DebugEntryNoop(final boolean isAllowedWithReducedDebugInfo) {
    this.isAllowedWithReducedDebugInfo = isAllowedWithReducedDebugInfo;
  }

  public void display(final DebugScreenDisplayer displayer, final @Nullable Level serverOrClientLevel,
      final @Nullable LevelChunk clientChunk, final @Nullable LevelChunk serverChunk) {
  }

  public boolean isAllowed(final boolean reducedDebugInfo) {
    return this.isAllowedWithReducedDebugInfo || !reducedDebugInfo;
  }

  public DebugEntryCategory category() {
    return DebugEntryCategory.RENDERER;
  }

  @Override
  public boolean enabled() {
    return true;
  }
}
