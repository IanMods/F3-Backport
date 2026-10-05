package club.iananderson.f3backport.client.gui.components.debug.entries.misc;

import club.iananderson.f3backport.client.gui.components.debug.DebugScreenDisplayer;
import club.iananderson.f3backport.client.gui.components.debug.entries.DebugGroups;
import club.iananderson.f3backport.client.gui.components.debug.entries.DebugScreenEntry;
import club.iananderson.f3backport.config.F3BackportClient;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;
import org.jspecify.annotations.Nullable;

public class DebugEntryLocalDifficulty implements DebugScreenEntry {
  public DebugEntryLocalDifficulty() {
  }

  public void display(final DebugScreenDisplayer displayer, final @Nullable Level serverOrClientLevel,
      final @Nullable LevelChunk clientChunk, final @Nullable LevelChunk serverChunk) {
    Minecraft minecraft = Minecraft.getInstance();
    Entity entity = minecraft.getCameraEntity();
    if (entity != null && serverChunk != null && serverOrClientLevel instanceof ServerLevel serverLevel) {
      BlockPos feetPos = entity.blockPosition();
      if (!serverLevel.isOutsideBuildHeight(feetPos.getY())) {
        float moonBrightness = serverLevel.getMoonBrightness();
        long localTime = serverChunk.getInhabitedTime();
        DifficultyInstance localDifficulty = new DifficultyInstance(serverLevel.getDifficulty(),
                                                                    serverLevel.getDayTime(), localTime,
                                                                    moonBrightness);
        displayer.addFactToGroup(DebugGroups.MISC, "Local Difficulty",
                                 (fact) -> fact.formattedValue("%.2f", localDifficulty.getEffectiveDifficulty())
                                     .text(" // ")
                                     .formattedValue("%.2f", new Object[]{localDifficulty.getSpecialMultiplier()}));
      }

    }
  }

  @Override
  public boolean enabled() {
    return F3BackportClient.getLocalDifficulty();
  }
}
