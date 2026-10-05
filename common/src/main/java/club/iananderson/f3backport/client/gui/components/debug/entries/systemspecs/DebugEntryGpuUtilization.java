package club.iananderson.f3backport.client.gui.components.debug.entries.systemspecs;

import club.iananderson.f3backport.client.gui.components.debug.DebugScreenDisplayer;
import club.iananderson.f3backport.client.gui.components.debug.entries.DebugGroups;
import club.iananderson.f3backport.client.gui.components.debug.entries.DebugScreenEntry;
import club.iananderson.f3backport.config.F3BackportClient;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;
import org.jspecify.annotations.Nullable;

public class DebugEntryGpuUtilization implements DebugScreenEntry {
  public DebugEntryGpuUtilization() {
  }

  public void display(final DebugScreenDisplayer displayer, final @Nullable Level serverOrClientLevel,
      final @Nullable LevelChunk clientChunk, final @Nullable LevelChunk serverChunk) {
    Minecraft minecraft = Minecraft.getInstance();
    displayer.addFactToGroup(DebugGroups.SYSTEM_SPECS, "GPU Utilization", (fact) -> {
      if (minecraft.getGpuUtilization() > (double) 100.0F) {
        fact.text(Component.literal("100%").withColor(-65536));
      } else {
        fact.value((int) Math.round(minecraft.getGpuUtilization())).text("%");
      }

    });
  }

  public boolean isAllowed(final boolean reducedDebugInfo) {
    return true;
  }

  @Override
  public boolean enabled() {
    return F3BackportClient.getEnableGpuUtilization();
  }
}
