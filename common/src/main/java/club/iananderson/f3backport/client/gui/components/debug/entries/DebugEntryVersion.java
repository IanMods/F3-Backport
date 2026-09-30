package club.iananderson.f3backport.client.gui.components.debug.entries;

import club.iananderson.f3backport.client.gui.components.debug.DebugScreenDisplayer;
import club.iananderson.f3backport.client.gui.components.debug.DebugScreenEntry;
import javax.annotation.Nullable;
import net.minecraft.SharedConstants;
import net.minecraft.client.ClientBrandRetriever;
import net.minecraft.client.Minecraft;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;

public class DebugEntryVersion implements DebugScreenEntry {
  public DebugEntryVersion() {
  }

  public void display(final DebugScreenDisplayer displayer, final @Nullable Level level, final @Nullable LevelChunk clientChunk, final @Nullable LevelChunk serverChunk) {
    String var10001 = SharedConstants.getCurrentVersion().getName();
    displayer.addPriorityLine("Minecraft " + var10001 + " (" + Minecraft.getInstance().getLaunchedVersion() + "/" + ClientBrandRetriever.getClientModName() + ")");
  }

  public boolean isAllowed(final boolean reducedDebugInfo) {
    return true;
  }
}
