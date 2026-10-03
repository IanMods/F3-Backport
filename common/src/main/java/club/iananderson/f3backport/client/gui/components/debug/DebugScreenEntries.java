package club.iananderson.f3backport.client.gui.components.debug;

import club.iananderson.f3backport.client.gui.components.debug.entries.DebugEntryBiome;
import club.iananderson.f3backport.client.gui.components.debug.entries.DebugEntryDayCount;
import club.iananderson.f3backport.client.gui.components.debug.entries.DebugEntryDetailedMemory;
import club.iananderson.f3backport.client.gui.components.debug.entries.DebugEntryEntityRenderStats;
import club.iananderson.f3backport.client.gui.components.debug.entries.DebugEntryFps;
import club.iananderson.f3backport.client.gui.components.debug.entries.DebugEntryGpuUtilization;
import club.iananderson.f3backport.client.gui.components.debug.entries.DebugEntryHeightmap;
import club.iananderson.f3backport.client.gui.components.debug.entries.DebugEntryLight;
import club.iananderson.f3backport.client.gui.components.debug.entries.DebugEntryLocalDifficulty;
import club.iananderson.f3backport.client.gui.components.debug.entries.DebugEntryMemory;
import club.iananderson.f3backport.client.gui.components.debug.entries.DebugEntryNoop;
import club.iananderson.f3backport.client.gui.components.debug.entries.DebugEntryPlayerSpeed;
import club.iananderson.f3backport.client.gui.components.debug.entries.DebugEntryPosition;
import club.iananderson.f3backport.client.gui.components.debug.entries.DebugEntrySectionPosition;
import club.iananderson.f3backport.client.gui.components.debug.entries.DebugEntrySimplePerformanceImpactors;
import club.iananderson.f3backport.client.gui.components.debug.entries.DebugEntrySoundMood;
import club.iananderson.f3backport.client.gui.components.debug.entries.DebugEntrySpawnCounts;
import club.iananderson.f3backport.client.gui.components.debug.entries.DebugEntrySystemSpecs;
import club.iananderson.f3backport.client.gui.components.debug.entries.DebugEntryTps;
import club.iananderson.f3backport.client.gui.components.debug.entries.DebugEntryVersion;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.resources.ResourceLocation;
import org.jspecify.annotations.Nullable;

public class DebugScreenEntries {
  public static final Map<DebugScreenProfile, Map<ResourceLocation, DebugScreenEntryStatus>> PROFILES;
  private static final Map<ResourceLocation, DebugScreenEntry> ENTRIES_BY_ID = new HashMap<>();
  public static final ResourceLocation GAME_VERSION = register((String) "game_version", new DebugEntryVersion());
  public static final ResourceLocation FPS = register((String) "fps", new DebugEntryFps());
  public static final ResourceLocation TPS = register((String) "tps", new DebugEntryTps());
  public static final ResourceLocation MEMORY = register((String) "memory", new DebugEntryMemory());
  public static final ResourceLocation DETAILED_MEMORY = register((String) "detailed_memory",
                                                                  new DebugEntryDetailedMemory());
  public static final ResourceLocation SYSTEM_SPECS = register((String) "system_specs", new DebugEntrySystemSpecs());
  // public static final ResourceLocation LOOKING_AT_BLOCK_STATE = register((String)"looking_at_block_state", new DebugEntryLookingAt.BlockStateInfo());
  // public static final ResourceLocation LOOKING_AT_BLOCK_TAGS = register((String)"looking_at_block_tags", new DebugEntryLookingAt.BlockTagInfo());
  // public static final ResourceLocation LOOKING_AT_FLUID_STATE = register((String)"looking_at_fluid_state", new DebugEntryLookingAt.FluidStateInfo());
  // public static final ResourceLocation LOOKING_AT_FLUID_TAGS = register((String)"looking_at_fluid_tags", new DebugEntryLookingAt.FluidTagInfo());
  // public static final ResourceLocation LOOKING_AT_ENTITY = register((String)"looking_at_entity", new DebugEntryLookingAtEntity());
  // public static final ResourceLocation LOOKING_AT_ENTITY_TAGS = register((String)"looking_at_entity_tags", new DebugEntryLookingAtEntityTags());
  // public static final ResourceLocation CHUNK_RENDER_STATS = register((String)"chunk_render_stats", new DebugEntryChunkRenderStats());
  // public static final ResourceLocation CHUNK_GENERATION_STATS = register((String)"chunk_generation_stats", new DebugEntryChunkGeneration());
  public static final ResourceLocation ENTITY_RENDER_STATS = register((String) "entity_render_stats",
                                                                      new DebugEntryEntityRenderStats());
  // public static final ResourceLocation PARTICLE_RENDER_STATS = register((String)"particle_render_stats", new DebugEntryParticleRenderStats());
  // public static final ResourceLocation CHUNK_SOURCE_STATS = register((String)"chunk_source_stats", new DebugEntryChunkSourceStats());
  public static final ResourceLocation PLAYER_POSITION = register((String) "player_position", new DebugEntryPosition());
  public static final ResourceLocation PLAYER_SECTION_POSITION = register((String) "player_section_position",
                                                                          new DebugEntrySectionPosition());
  public static final ResourceLocation PLAYER_SPEED = register((String) "player_speed", new DebugEntryPlayerSpeed());
  public static final ResourceLocation LIGHT_LEVELS = register((String) "light_levels", new DebugEntryLight());
  public static final ResourceLocation HEIGHTMAP = register((String) "heightmap", new DebugEntryHeightmap());
  public static final ResourceLocation BIOME = register((String) "biome", new DebugEntryBiome());
  public static final ResourceLocation LOCAL_DIFFICULTY = register((String) "local_difficulty",
                                                                   new DebugEntryLocalDifficulty());
  public static final ResourceLocation DAY_COUNT = register((String) "day_count", new DebugEntryDayCount());
  public static final ResourceLocation ENTITY_SPAWN_COUNTS = register((String) "entity_spawn_counts",
                                                                      new DebugEntrySpawnCounts());
  public static final ResourceLocation SOUND_MOOD = register((String) "sound_mood", new DebugEntrySoundMood());
  // public static final ResourceLocation SOUND_CACHE = register((String)"sound_cache", new DebugEntrySoundCache());
  // public static final ResourceLocation POST_EFFECTS = register((String)"post_effects", new DebugEntryPostEffects());
  public static final ResourceLocation ENTITY_HITBOXES = register((String) "entity_hitboxes", new DebugEntryNoop());
  public static final ResourceLocation CHUNK_BORDERS = register((String) "chunk_borders", new DebugEntryNoop());
  public static final ResourceLocation THREE_DIMENSIONAL_CROSSHAIR = register((String) "3d_crosshair",
                                                                              new DebugEntryNoop());
  public static final ResourceLocation CHUNK_SECTION_PATHS = register((String) "chunk_section_paths",
                                                                      new DebugEntryNoop());
  public static final ResourceLocation GPU_UTILIZATION = register((String) "gpu_utilization",
                                                                  new DebugEntryGpuUtilization());
  public static final ResourceLocation SIMPLE_PERFORMANCE_IMPACTORS = register((String) "simple_performance_impactors",
                                                                               new DebugEntrySimplePerformanceImpactors());
  public static final ResourceLocation CHUNK_SECTION_OCTREE = register((String) "chunk_section_octree",
                                                                       new DebugEntryNoop());
  public static final ResourceLocation VISUALIZE_WATER_LEVELS = register((String) "visualize_water_levels",
                                                                         new DebugEntryNoop());
  public static final ResourceLocation VISUALIZE_HEIGHTMAP = register((String) "visualize_heightmap",
                                                                      new DebugEntryNoop());
  public static final ResourceLocation VISUALIZE_COLLISION_BOXES = register((String) "visualize_collision_boxes",
                                                                            new DebugEntryNoop());
  public static final ResourceLocation VISUALIZE_ENTITY_SUPPORTING_BLOCKS = register(
      (String) "visualize_entity_supporting_blocks", new DebugEntryNoop());
  public static final ResourceLocation VISUALIZE_BLOCK_LIGHT_LEVELS = register((String) "visualize_block_light_levels",
                                                                               new DebugEntryNoop());
  public static final ResourceLocation VISUALIZE_SKY_LIGHT_LEVELS = register((String) "visualize_sky_light_levels",
                                                                             new DebugEntryNoop());
  public static final ResourceLocation VISUALIZE_SOLID_FACES = register((String) "visualize_solid_faces",
                                                                        new DebugEntryNoop());
  public static final ResourceLocation VISUALIZE_CHUNKS_ON_SERVER = register((String) "visualize_chunks_on_server",
                                                                             new DebugEntryNoop());
  public static final ResourceLocation VISUALIZE_SKY_LIGHT_SECTIONS = register((String) "visualize_sky_light_sections",
                                                                               new DebugEntryNoop());
  public static final ResourceLocation CHUNK_SECTION_VISIBILITY = register((String) "chunk_section_visibility",
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
    return register(ResourceLocation.withDefaultNamespace(id), entry);
  }

  public static ResourceLocation register(final ResourceLocation ResourceLocation, final DebugScreenEntry entry) {
    ENTRIES_BY_ID.put(ResourceLocation, entry);
    return ResourceLocation;
  }

  public static Map<ResourceLocation, DebugScreenEntry> allEntries() {
    return Map.copyOf(ENTRIES_BY_ID);
  }

  public static @Nullable DebugScreenEntry getEntry(final ResourceLocation id) {
    return (DebugScreenEntry) ENTRIES_BY_ID.get(id);
  }
}
