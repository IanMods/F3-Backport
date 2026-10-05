package club.iananderson.f3backport.client.gui.components.debug.entries.position;

import club.iananderson.f3backport.client.gui.components.debug.DebugScreenDisplayer;
import club.iananderson.f3backport.client.gui.components.debug.entries.DebugGroups;
import club.iananderson.f3backport.client.gui.components.debug.entries.DebugScreenEntry;
import club.iananderson.f3backport.config.F3BackportClient;
import java.util.Locale;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;
import org.jspecify.annotations.Nullable;

public class DebugEntryPlayerSectionPosition implements DebugScreenEntry {
  public DebugEntryPlayerSectionPosition() {
  }

  public void display(final DebugScreenDisplayer displayer, final @Nullable Level serverOrClientLevel,
      final @Nullable LevelChunk clientChunk, final @Nullable LevelChunk serverChunk) {
    Minecraft minecraft = Minecraft.getInstance();
    Entity entity = minecraft.getCameraEntity();
    if (entity != null) {
      BlockPos feetPos = minecraft.getCameraEntity().blockPosition();
      displayer.addFactToGroup(DebugGroups.POSITION, "Section-Relative", (fact) -> fact.value(
          String.format(Locale.ROOT, "%02d %02d %02d", feetPos.getX() & 15, feetPos.getY() & 15, feetPos.getZ() & 15)));
    }
  }

  public boolean isAllowed(final boolean reducedDebugInfo) {
    return true;
  }

  @Override
  public boolean enabled() {
    return F3BackportClient.getPlayerSectionPosition();
  }
}
