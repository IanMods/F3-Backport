package club.iananderson.f3backport.client.gui.components.debug.entries;

import club.iananderson.f3backport.client.gui.components.debug.DebugGroups;
import club.iananderson.f3backport.client.gui.components.debug.DebugScreenDisplayer;
import club.iananderson.f3backport.client.gui.components.debug.DebugScreenEntry;
import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import org.jspecify.annotations.Nullable;

public class DebugEntryHeightmap implements DebugScreenEntry {
  private static final Map<Types, String> HEIGHTMAP_NAMES;

  static {
    HEIGHTMAP_NAMES = Maps.newEnumMap(
        Map.of(Types.WORLD_SURFACE_WG, "(WG) Surface", Types.WORLD_SURFACE, "Surface", Types.OCEAN_FLOOR_WG,
               "(WG) Ocean Floor", Types.OCEAN_FLOOR, "Ocean Floor", Types.MOTION_BLOCKING, "Motion",
               Types.MOTION_BLOCKING_NO_LEAVES, "Motion (w/o Leaves)"));
  }

  public DebugEntryHeightmap() {
  }

  public void display(final DebugScreenDisplayer displayer, final @Nullable Level serverOrClientLevel,
      final @Nullable LevelChunk clientChunk, final @Nullable LevelChunk serverChunk) {
    Minecraft minecraft = Minecraft.getInstance();
    Entity entity = minecraft.getCameraEntity();
    if (entity != null && minecraft.level != null && clientChunk != null) {
      BlockPos feetPos = entity.blockPosition();

      for (Heightmap.Types type : Types.values()) {
        displayer.addFactToGroup(DebugGroups.HEIGHTMAP, HEIGHTMAP_NAMES.get(type), (fact) -> {
          int clientHeight = clientChunk.getHeight(type, feetPos.getX(), feetPos.getZ());
          int serverHeight = serverChunk == null
                             ? -1
                             : serverChunk.getHeight(type, feetPos.getX(), feetPos.getZ());
          boolean verbose = type.sendToClient() && serverChunk != null && clientHeight != serverHeight;
          if (verbose) {
            fact.value(clientHeight).text(" (client), ").value(serverHeight).text(" (server)");
          } else if (type.sendToClient()) {
            fact.value(clientHeight);
          } else if (serverChunk != null) {
            fact.value(serverHeight);
          }

        });
      }

    }
  }
}
