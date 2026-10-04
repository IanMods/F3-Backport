package club.iananderson.f3backport.client.gui.components.debug;

import club.iananderson.f3backport.Common;
import club.iananderson.f3backport.client.gui.components.debug.entries.DebugEntryBiome;
import club.iananderson.f3backport.client.gui.components.debug.entries.DebugEntryDayCount;
import club.iananderson.f3backport.client.gui.components.debug.entries.DebugEntryDetailedMemory;
import club.iananderson.f3backport.client.gui.components.debug.entries.DebugEntryEntityRenderStats;
import club.iananderson.f3backport.client.gui.components.debug.entries.DebugEntryFps;
import club.iananderson.f3backport.client.gui.components.debug.entries.DebugEntryGameVersion;
import club.iananderson.f3backport.client.gui.components.debug.entries.DebugEntryGpuUtilization;
import club.iananderson.f3backport.client.gui.components.debug.entries.DebugEntryHeightmap;
import club.iananderson.f3backport.client.gui.components.debug.entries.DebugEntryLight;
import club.iananderson.f3backport.client.gui.components.debug.entries.DebugEntryLocalDifficulty;
import club.iananderson.f3backport.client.gui.components.debug.entries.DebugEntryMemory;
import club.iananderson.f3backport.client.gui.components.debug.entries.DebugEntryNoop;
import club.iananderson.f3backport.client.gui.components.debug.entries.DebugEntryPlayerPosition;
import club.iananderson.f3backport.client.gui.components.debug.entries.DebugEntryPlayerSectionPosition;
import club.iananderson.f3backport.client.gui.components.debug.entries.DebugEntryPlayerSpeed;
import club.iananderson.f3backport.client.gui.components.debug.entries.DebugEntrySimplePerformanceImpactors;
import club.iananderson.f3backport.client.gui.components.debug.entries.DebugEntrySoundMood;
import club.iananderson.f3backport.client.gui.components.debug.entries.DebugEntrySpawnCounts;
import club.iananderson.f3backport.client.gui.components.debug.entries.DebugEntrySystemSpecs;
import club.iananderson.f3backport.client.gui.components.debug.entries.DebugEntryTps;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.resources.ResourceLocation;
import org.jspecify.annotations.Nullable;

public class DebugScreenEntries {
  public static final Map<DebugScreenProfile, Map<ResourceLocation, DebugScreenEntryStatus>> PROFILES;
  private static final Map<ResourceLocation, DebugScreenEntry> ENTRIES_BY_ID = new HashMap<>();
  public static final ResourceLocation GAME_VERSION = register("game_version", new DebugEntryGameVersion());
  public static final ResourceLocation FPS = register("fps", new DebugEntryFps());
  public static final ResourceLocation TPS = register("tps", new DebugEntryTps());
  public static final ResourceLocation MEMORY = register("memory", new DebugEntryMemory());
  public static final ResourceLocation DETAILED_MEMORY = register("detailed_memory", new DebugEntryDetailedMemory());
  public static final ResourceLocation SYSTEM_SPECS = register("system_specs", new DebugEntrySystemSpecs());
  // public static final ResourceLocation LOOKING_AT_BLOCK_STATE = register((String)"looking_at_block_state", new DebugEntryLookingAt.BlockStateInfo());
  // public static final ResourceLocation LOOKING_AT_BLOCK_TAGS = register((String)"looking_at_block_tags", new DebugEntryLookingAt.BlockTagInfo());
  // public static final ResourceLocation LOOKING_AT_FLUID_STATE = register((String)"looking_at_fluid_state", new DebugEntryLookingAt.FluidStateInfo());
  // public static final ResourceLocation LOOKING_AT_FLUID_TAGS = register((String)"looking_at_fluid_tags", new DebugEntryLookingAt.FluidTagInfo());
  // public static final ResourceLocation LOOKING_AT_ENTITY = register((String)"looking_at_entity", new DebugEntryLookingAtEntity());
  // public static final ResourceLocation LOOKING_AT_ENTITY_TAGS = register((String)"looking_at_entity_tags", new DebugEntryLookingAtEntityTags());
  // public static final ResourceLocation CHUNK_RENDER_STATS = register((String)"chunk_render_stats", new DebugEntryChunkRenderStats());
  // public static final ResourceLocation CHUNK_GENERATION_STATS = register((String)"chunk_generation_stats", new DebugEntryChunkGeneration());
  public static final ResourceLocation ENTITY_RENDER_STATS = register("entity_render_stats",
                                                                      new DebugEntryEntityRenderStats());
  // public static final ResourceLocation PARTICLE_RENDER_STATS = register((String)"particle_render_stats", new DebugEntryParticleRenderStats());
  // public static final ResourceLocation CHUNK_SOURCE_STATS = register((String)"chunk_source_stats", new DebugEntryChunkSourceStats());
  public static final ResourceLocation PLAYER_POSITION = register("player_position", new DebugEntryPlayerPosition());
  public static final ResourceLocation PLAYER_SECTION_POSITION = register("player_section_position",
                                                                          new DebugEntryPlayerSectionPosition());
  public static final ResourceLocation PLAYER_SPEED = register("player_speed", new DebugEntryPlayerSpeed());
  public static final ResourceLocation LIGHT_LEVELS = register("light_levels", new DebugEntryLight());
  public static final ResourceLocation HEIGHTMAP = register("heightmap", new DebugEntryHeightmap());
  public static final ResourceLocation BIOME = register("biome", new DebugEntryBiome());
  public static final ResourceLocation LOCAL_DIFFICULTY = register("local_difficulty", new DebugEntryLocalDifficulty());
  public static final ResourceLocation DAY_COUNT = register("day_count", new DebugEntryDayCount());
  public static final ResourceLocation ENTITY_SPAWN_COUNTS = register("entity_spawn_counts",
                                                                      new DebugEntrySpawnCounts());
  public static final ResourceLocation SOUND_MOOD = register("sound_mood", new DebugEntrySoundMood());
  // public static final ResourceLocation SOUND_CACHE = register((String)"sound_cache", new DebugEntrySoundCache());
  // public static final ResourceLocation POST_EFFECTS = register((String)"post_effects", new DebugEntryPostEffects());
  public static final ResourceLocation ENTITY_HITBOXES = register("entity_hitboxes", new DebugEntryNoop());
  public static final ResourceLocation CHUNK_BORDERS = register("chunk_borders", new DebugEntryNoop());
  public static final ResourceLocation THREE_DIMENSIONAL_CROSSHAIR = register("3d_crosshair", new DebugEntryNoop());
  public static final ResourceLocation GPU_UTILIZATION = register("gpu_utilization", new DebugEntryGpuUtilization());
  public static final ResourceLocation SIMPLE_PERFORMANCE_IMPACTORS = register("simple_performance_impactors",
                                                                               new DebugEntrySimplePerformanceImpactors());
  public static final ResourceLocation CHUNK_SECTION_OCTREE = register("chunk_section_octree", new DebugEntryNoop());
  public static final ResourceLocation VISUALIZE_WATER_LEVELS = register("visualize_water_levels",
                                                                         new DebugEntryNoop());
  public static final ResourceLocation VISUALIZE_HEIGHTMAP = register("visualize_heightmap", new DebugEntryNoop());
  public static final ResourceLocation VISUALIZE_COLLISION_BOXES = register("visualize_collision_boxes",
                                                                            new DebugEntryNoop());
  public static final ResourceLocation VISUALIZE_ENTITY_SUPPORTING_BLOCKS = register(
      "visualize_entity_supporting_blocks", new DebugEntryNoop());
  public static final ResourceLocation VISUALIZE_BLOCK_LIGHT_LEVELS = register("visualize_block_light_levels",
                                                                               new DebugEntryNoop());
  public static final ResourceLocation VISUALIZE_SKY_LIGHT_LEVELS = register("visualize_sky_light_levels",
                                                                             new DebugEntryNoop());
  public static final ResourceLocation VISUALIZE_SOLID_FACES = register("visualize_solid_faces", new DebugEntryNoop());
  public static final ResourceLocation VISUALIZE_CHUNKS_ON_SERVER = register("visualize_chunks_on_server",
                                                                             new DebugEntryNoop());
  public static final ResourceLocation VISUALIZE_SKY_LIGHT_SECTIONS = register("visualize_sky_light_sections",
                                                                               new DebugEntryNoop());
  public static final ResourceLocation CHUNK_SECTION_VISIBILITY = register("chunk_section_visibility",
                                                                           new DebugEntryNoop());

  static {
    Map<ResourceLocation, DebugScreenEntryStatus> defaultProfile = Map.of(THREE_DIMENSIONAL_CROSSHAIR,
                                                                          DebugScreenEntryStatus.IN_OVERLAY,
                                                                          GAME_VERSION,
                                                                          DebugScreenEntryStatus.IN_OVERLAY, TPS,
                                                                          DebugScreenEntryStatus.IN_OVERLAY, FPS,
                                                                          DebugScreenEntryStatus.IN_OVERLAY, MEMORY,
                                                                          DebugScreenEntryStatus.IN_OVERLAY, BIOME,
                                                                          DebugScreenEntryStatus.IN_OVERLAY,
                                                                          SYSTEM_SPECS,
                                                                          DebugScreenEntryStatus.IN_OVERLAY,
                                                                          PLAYER_POSITION,
                                                                          DebugScreenEntryStatus.IN_OVERLAY,
                                                                          PLAYER_SECTION_POSITION,
                                                                          DebugScreenEntryStatus.IN_OVERLAY,
                                                                          SIMPLE_PERFORMANCE_IMPACTORS,
                                                                          DebugScreenEntryStatus.IN_OVERLAY);
    Map<ResourceLocation, DebugScreenEntryStatus> performance = Map.of(TPS, DebugScreenEntryStatus.IN_OVERLAY, FPS,
                                                                       DebugScreenEntryStatus.ALWAYS_ON,
                                                                       GPU_UTILIZATION,
                                                                       DebugScreenEntryStatus.IN_OVERLAY, MEMORY,
                                                                       DebugScreenEntryStatus.IN_OVERLAY,
                                                                       SIMPLE_PERFORMANCE_IMPACTORS,
                                                                       DebugScreenEntryStatus.IN_OVERLAY);
    PROFILES = Map.of(DebugScreenProfile.DEFAULT, defaultProfile, DebugScreenProfile.PERFORMANCE, performance);
  }

  public DebugScreenEntries() {
  }

  private static ResourceLocation register(final String id, final DebugScreenEntry entry) {
    return register(Common.location(id), entry);
  }

  public static ResourceLocation register(final ResourceLocation resourceLocation, final DebugScreenEntry entry) {
    ENTRIES_BY_ID.put(resourceLocation, entry);
    return resourceLocation;
  }

  public static Map<ResourceLocation, DebugScreenEntry> allEntries() {
    return Map.copyOf(ENTRIES_BY_ID);
  }

  public static @Nullable DebugScreenEntry getEntry(final ResourceLocation id) {
    return ENTRIES_BY_ID.get(id);
  }
}
