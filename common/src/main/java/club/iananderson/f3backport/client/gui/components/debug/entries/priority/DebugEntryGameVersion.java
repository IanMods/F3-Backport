package club.iananderson.f3backport.client.gui.components.debug.entries.priority;

import club.iananderson.f3backport.client.gui.components.debug.DebugScreenDisplayer;
import club.iananderson.f3backport.client.gui.components.debug.entries.DebugScreenEntry;
import club.iananderson.f3backport.config.F3BackportClient;
import net.minecraft.SharedConstants;
import net.minecraft.client.ClientBrandRetriever;
import net.minecraft.client.Minecraft;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;
import org.jspecify.annotations.Nullable;

public class DebugEntryGameVersion implements DebugScreenEntry {
  public DebugEntryGameVersion() {
  }

  public void display(final DebugScreenDisplayer displayer, final @Nullable Level level,
      final @Nullable LevelChunk clientChunk, final @Nullable LevelChunk serverChunk) {
    String currentVersion = SharedConstants.getCurrentVersion().getName();
    displayer.addPriorityLine("Minecraft " + currentVersion + " (" + Minecraft.getInstance().getLaunchedVersion() + "/"
                                  + ClientBrandRetriever.getClientModName() + ")");
  }

  public boolean isAllowed(final boolean reducedDebugInfo) {
    return true;
  }

  @Override
  public boolean enabled() {
    return F3BackportClient.getEnableGameVersion();
  }
}
