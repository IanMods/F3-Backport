package club.iananderson.f3backport.client.gui.components.debug.entries;

import club.iananderson.f3backport.Common;
import club.iananderson.f3backport.client.gui.components.debug.DebugColumn.Side;
import club.iananderson.f3backport.config.DefaultValues.Color;
import club.iananderson.f3backport.config.F3BackportClient;
import club.iananderson.f3backport.util.Rgb;
import java.util.EnumSet;
import java.util.Map;
import net.minecraft.network.chat.MutableComponent;

public enum Groups {
  MEMORY("menu.f3backport.config.memory.name", Color.DEFAULT_MEMORY_COLOR, F3BackportClient.getMemoryColor(),
         Side.RIGHT),
  POSITION("menu.f3backport.config.position.name", Color.DEFAULT_POSITION_COLOR, F3BackportClient.getPositionColor(),
           Side.LEFT),
  CHUNK_RENDERING("menu.f3backport.config.chunkRendering.name", Color.DEFAULT_CHUNK_RENDERING_COLOR,
                  F3BackportClient.getChunkRenderingColor(), Side.LEFT),
  PERFORMANCE_IMPACTORS("menu.f3backport.config.perfImpactors.name", Color.DEFAULT_PERF_IMPACTORS_COLOR,
                        F3BackportClient.getPerfImpactorsColor(), Side.RIGHT),
  SYSTEM_SPECS("menu.f3backport.config.systemSpecs.name", Color.DEFAULT_SYSTEM_SPECS_COLOR,
               F3BackportClient.getSystemSpecsColor(), Side.RIGHT),
  HEIGHTMAP("menu.f3backport.config.heightMap.name", Color.DEFAULT_HEIGHTMAP_COLOR,
            F3BackportClient.getHeightmapColor(), Side.LEFT),
  CHUNK_GENERATION("menu.f3backport.config.chunkGeneration.name", Color.DEFAULT_CHUNK_GENERATION_COLOR,
                   F3BackportClient.getChunkGenerationColor(), Side.RIGHT),
  SPAWN_COUNTS("menu.f3backport.config.spawnCounts.name", Color.DEFAULT_SPAWN_COUNTS_COLOR,
               F3BackportClient.getSpawnCountsColor(), Side.LEFT),
  MISC("menu.f3backport.config.misc.name", Color.DEFAULT_MISC_COLOR, F3BackportClient.getMiscColor(), Side.LEFT),
  HELP("menu.f3backport.config.help.name", Color.DEFAULT_HELP_COLOR, F3BackportClient.getHelpColor(), Side.LEFT),
  PRIORITY("menu.f3backport.config.priority.name", Color.DEFAULT_PRIORITY_COLOR, F3BackportClient.getPriorityColor(),
           Side.LEFT),
  LIGHT("menu.f3backport.config.light.name", Color.DEFAULT_LIGHT_COLOR, F3BackportClient.getLightColor(), Side.LEFT),
  LOOKING_AT_BLOCK("menu.f3backport.config.lookingAt.block.name", Color.DEFAULT_LOOKING_AT_BLOCK_COLOR,
                   F3BackportClient.getLookingAtBlockColor(), Side.LEFT),
  LOOKING_AT_FLUID("menu.f3backport.config.lookingAt.fluid.name", Color.DEFAULT_LOOKING_AT_FLUID_COLOR,
                   F3BackportClient.getLookingAtFluidColor(), Side.LEFT),
  LOOKING_AT_ENTITY("menu.f3backport.config.lookingAt.entity.name", Color.DEFAULT_LOOKING_AT_ENTITY_COLOR,
                    F3BackportClient.getLookingAtEntityColor(), Side.LEFT);

  // TODO: Change RGBA -> RGB and have a config option for the alpha
  public static final EnumSet<Groups> GROUPS_ENUM_LIST = EnumSet.allOf(Groups.class);
  private final String translationKey;
  private final int defaultColor;
  private final Map<String, Integer> rgbMap;
  private final Side preferredColumn;
  private int groupColor;

  Groups(final String translationKey, int defaultColor, int groupColor, Side preferredColumn) {
    this.translationKey = translationKey;
    this.defaultColor = defaultColor;
    this.groupColor = groupColor;
    this.preferredColumn = preferredColumn;
    this.rgbMap = Rgb.groupMap(this.groupColor);
  }

  public String translationKey() {
    return this.translationKey;
  }

  public MutableComponent getTranslatedName() {
    return Common.translatedText(this.translationKey);
  }

  public int getDefaultColor() {
    return this.defaultColor;
  }

  public Map<String, Integer> getRgbMap() {
    return this.rgbMap;
  }

  public Side getPreferredColumn() {
    return this.preferredColumn;
  }

  public int getGroupColor() {
    return this.groupColor;
  }

  public void setGroupColor(int groupColor) {
    this.groupColor = groupColor;
  }
}