package club.iananderson.f3backport.client.gui.components.debug.entries;

import club.iananderson.f3backport.client.gui.components.debug.DebugColumn.Side;
import club.iananderson.f3backport.client.gui.components.debug.entries.DebugGroup.Builder;
import java.awt.Color;

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
    MEMORY = Builder.titled("Memory").withAccentColor((new Color(255, 155, 0))).withPreferredColumn(Side.RIGHT)
        .build();
    POSITION = Builder.titled("Position").withAccentColor(new Color(255, 255, 255)).withPreferredColumn(Side.LEFT)
        .build();
    CHUNK_RENDERING = Builder.titled("Chunk Rendering").withAccentColor(new Color(240, 176, 160))
        .build();
    PERFORMANCE_IMPACTORS = Builder.titled("Performance Impactors")
        .withAccentColor(new Color(0, 255, 0))
        .withPreferredColumn(Side.RIGHT)
        .build();
    SYSTEM_SPECS = Builder.titled("System Specs").withAccentColor(new Color(255, 0, 0)).withPreferredColumn(Side.RIGHT)
        .build();
    HEIGHTMAP = Builder.titled("Heightmap").withAccentColor(43775)
        .build();
    CHUNK_GENERATION = Builder.titled("Chunk Generation").withAccentColor(new Color(153, 255, 170))
        .build();
    SPAWN_COUNTS = Builder.titled("Entity Spawn Counts").withAccentColor(new Color(255, 68, 68))
        .build();
    MISC = Builder.titleless()
        .build();
    HELP = Builder.titled("Help").withAccentColor(new Color(200, 200, 200))
        .build();
    PRIORITY = Builder.titleless()
        .build();
    LIGHT = Builder.titled("Light").withAccentColor(new Color(255, 255, 0))
        .build();
    LOOKING_AT_BLOCK = Builder.titled("Looking At Block").withAccentColor(new Color(204, 0, 255))
        .build();
    LOOKING_AT_FLUID = Builder.titled("Looking At Fluid").withAccentColor(new Color(255, 204, 0))
        .build();
    LOOKING_AT_ENTITY = Builder.titled("Looking At Entity").withAccentColor(new Color(0, 255, 204))
        .build();
  }

  public DebugGroups() {
  }
}
