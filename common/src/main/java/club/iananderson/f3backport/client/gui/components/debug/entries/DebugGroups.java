package club.iananderson.f3backport.client.gui.components.debug.entries;

import club.iananderson.f3backport.client.gui.components.debug.DebugColumn.Side;
import club.iananderson.f3backport.client.gui.components.debug.entries.DebugGroup.Builder;

public class DebugGroups {
  public static final DebugGroup HELP;
  public static final DebugGroup MISC;
  public static final DebugGroup PRIORITY;
  public static final DebugGroup LIGHT;
  public static final DebugGroup LOOKING_AT_BLOCK;
  public static final DebugGroup LOOKING_AT_FLUID;
  public static final DebugGroup LOOKING_AT_ENTITY;
  public static final DebugGroup MEMORY;
  public static final DebugGroup POSITION;
  public static final DebugGroup CHUNK_RENDERING;
  public static final DebugGroup PERFORMANCE_IMPACTORS;
  public static final DebugGroup SYSTEM_SPECS;
  public static final DebugGroup HEIGHTMAP;
  public static final DebugGroup CHUNK_GENERATION;
  public static final DebugGroup SPAWN_COUNTS;

  static {
    MEMORY = Builder.titled("Memory").withAccentColor(Groups.MEMORY.getGroupColor()).withPreferredColumn(Side.RIGHT)
        .build();
    POSITION = Builder.titled("Position")
        .withAccentColor(Groups.POSITION.getGroupColor())
        .withPreferredColumn(Side.LEFT)
        .build();
    CHUNK_RENDERING = Builder.titled("Chunk Rendering").withAccentColor(Groups.CHUNK_RENDERING.getGroupColor())
        .build();
    PERFORMANCE_IMPACTORS = Builder.titled("Performance Impactors")
        .withAccentColor(Groups.PERFORMANCE_IMPACTORS.getGroupColor())
        .withPreferredColumn(Side.RIGHT)
        .build();
    SYSTEM_SPECS = Builder.titled("System Specs")
        .withAccentColor(Groups.SYSTEM_SPECS.getGroupColor())
        .withPreferredColumn(Side.RIGHT)
        .build();
    HEIGHTMAP = Builder.titled("Heightmap").withAccentColor(Groups.HEIGHTMAP.getGroupColor())
        .build();
    CHUNK_GENERATION = Builder.titled("Chunk Generation").withAccentColor(Groups.CHUNK_GENERATION.getGroupColor())
        .build();
    SPAWN_COUNTS = Builder.titled("Entity Spawn Counts").withAccentColor(Groups.SPAWN_COUNTS.getGroupColor())
        .build();
    MISC = Builder.titleless()
        .build();
    HELP = Builder.titled("Help").withAccentColor(Groups.HELP.getGroupColor())
        .build();
    PRIORITY = Builder.titleless()
        .build();
    LIGHT = Builder.titled("Light").withAccentColor(Groups.LIGHT.getGroupColor())
        .build();
    LOOKING_AT_BLOCK = Builder.titled("Looking At Block").withAccentColor(Groups.LOOKING_AT_BLOCK.getGroupColor())
        .build();
    LOOKING_AT_FLUID = Builder.titled("Looking At Fluid").withAccentColor(Groups.LOOKING_AT_FLUID.getGroupColor())
        .build();
    LOOKING_AT_ENTITY = Builder.titled("Looking At Entity").withAccentColor(Groups.LOOKING_AT_ENTITY.getGroupColor())
        .build();
  }

  public DebugGroups() {
  }
}
