package club.iananderson.f3backport.client.gui.components.debug.entries;

import club.iananderson.f3backport.client.gui.components.debug.DebugGroups;
import club.iananderson.f3backport.client.gui.components.debug.DebugScreenDisplayer;
import club.iananderson.f3backport.client.gui.components.debug.DebugScreenEntry;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

public class DebugEntryPlayerSpeed implements DebugScreenEntry {
  private Vec3 lastKnownSpeed;
  private @Nullable Vec3 lastKnownPosition;

  public DebugEntryPlayerSpeed() {
    this.lastKnownSpeed = Vec3.ZERO;
  }

  public void display(final DebugScreenDisplayer displayer, final @Nullable Level serverOrClientLevel,
      final @Nullable LevelChunk clientChunk, final @Nullable LevelChunk serverChunk) {
    Entity player = Minecraft.getInstance().getCameraEntity();

    if (player != null) {
      this.computeSpeed(player);
      displayer.addFactToGroup(DebugGroups.POSITION, "Speed",
                               (fact) -> fact.formattedValue("%.3f", new Object[]{this.lastKnownSpeed.length()})
                                   .text(" blocks/tick"));
    }
  }

  protected void computeSpeed(Entity player) {
    if (this.lastKnownPosition == null) {
      this.lastKnownPosition = player.position();
    }

    this.lastKnownSpeed = player.position().subtract(this.lastKnownPosition);
    this.lastKnownPosition = player.position();
  }
}
