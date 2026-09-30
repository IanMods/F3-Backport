package club.iananderson.f3backport.client.gui.components.debug;

import club.iananderson.f3backport.client.gui.components.debug.DebugColumn.Side;
import club.iananderson.f3backport.client.gui.components.debug.DebugGroup.Builder;

public class DebugGroups {
  public static final DebugGroup HELP = Builder.titled("Help").build();
  public static final DebugGroup MISC = Builder.titleless().build();
  public static final DebugGroup PRIORITY = Builder.titleless().build();
  public static final DebugGroup LIGHT = Builder.titled("Light").withAccentColor(16776960).build();
  public static final DebugGroup LOOKING_AT_BLOCK = Builder.titled("Looking At Block").withAccentColor(13369599).build();
  public static final DebugGroup LOOKING_AT_FLUID = Builder.titled("Looking At Fluid").withAccentColor(16763904).build();
  public static final DebugGroup LOOKING_AT_ENTITY = Builder.titled("Looking At Entity").withAccentColor(65484).build();
  public static final DebugGroup MEMORY;
  public static final DebugGroup POSITION;
  public static final DebugGroup CHUNK_RENDERING;
  public static final DebugGroup PERFORMANCE_IMPACTORS;
  public static final DebugGroup SYSTEM_SPECS;
  public static final DebugGroup HEIGHTMAP;
  public static final DebugGroup CHUNK_GENERATION;
  public static final DebugGroup SPAWN_COUNTS;

  public DebugGroups() {
  }

  static {
    MEMORY = Builder.titled("Memory").withAccentColor(16751360).withPreferredColumn(Side.RIGHT).build();
    POSITION = Builder.titled("Position").withAccentColor(16777215).withPreferredColumn(Side.LEFT).build();
    CHUNK_RENDERING = Builder.titled("Chunk Rendering").withAccentColor(15773856).build();
    PERFORMANCE_IMPACTORS = Builder.titled("Performance Impactors").withAccentColor(65280).withPreferredColumn(Side.RIGHT).build();
    SYSTEM_SPECS = Builder.titled("System Specs").withAccentColor(16711680).withPreferredColumn(Side.RIGHT).build();
    HEIGHTMAP = Builder.titled("Heightmap").withAccentColor(43775).build();
    CHUNK_GENERATION = Builder.titled("Chunk Generation").withAccentColor(10092458).build();
    SPAWN_COUNTS = Builder.titled("Entity Spawn Counts").withAccentColor(16729156).build();
  }
}
