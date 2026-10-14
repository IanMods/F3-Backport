package club.iananderson.f3backport.config;

import club.iananderson.f3backport.client.gui.components.debug.entries.DebugScreenEntries;
import club.iananderson.f3backport.config.DefaultValues.Client;
import club.iananderson.f3backport.config.DefaultValues.Color;
import club.iananderson.f3backport.util.StringLine;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.common.ModConfigSpec.ConfigValue;
import org.apache.commons.lang3.tuple.Pair;

public class F3BackportClient {
  public static final F3BackportClient CONFIG;
  public static final ModConfigSpec CLIENT_SPEC;
  private static final Map<ConfigValue<?>, ResourceLocation> configValueMap = new HashMap<>();
  public static List<ConfigValue<Boolean>> booleanConfigs = new ArrayList<>();
  public static List<ConfigValue<Integer>> integerConfigs = new ArrayList<>();
  public static List<ConfigValue<Integer>> colorConfigs = new ArrayList<>();
  public static ConfigValue<Boolean> enableMod;
  public static ConfigValue<Boolean> enableGameVersion;
  public static ConfigValue<Boolean> enableFps;
  public static ConfigValue<Boolean> enableTps;
  public static ConfigValue<Boolean> enableMemory;
  public static ConfigValue<Boolean> enableSystemSpecs;
  public static ConfigValue<Boolean> enablePlayerPosition;
  public static ConfigValue<Boolean> enableBiome;
  public static ConfigValue<Boolean> enableGpuUtilization;
  public static ConfigValue<Boolean> enableSimplePerfImpactors;
  public static ConfigValue<Boolean> enableDayCount;
  public static ConfigValue<Boolean> enableDetailedMemory;
  public static ConfigValue<Boolean> enableEntityRenderStats;
  public static ConfigValue<Boolean> enableHeightmap;
  public static ConfigValue<Boolean> enableLight;
  public static ConfigValue<Boolean> enableLocalDifficulty;
  public static ConfigValue<Boolean> enablePlayerSpeed;
  public static ConfigValue<Boolean> enablePlayerSectionPosition;
  public static ConfigValue<Boolean> enableSoundMood;
  public static ConfigValue<Boolean> enableSpawnCounts;
  public static ConfigValue<Integer> debugGuiScale;
  public static ConfigValue<Integer> alphaColor;
  public static ConfigValue<Integer> memoryColor;
  public static ConfigValue<Integer> positionColor;
  public static ConfigValue<Integer> chunkRenderingColor;
  public static ConfigValue<Integer> perfImpactorsColor;
  public static ConfigValue<Integer> systemSpecsColor;
  public static ConfigValue<Integer> heightmapColor;
  public static ConfigValue<Integer> chunkGenerationColor;
  public static ConfigValue<Integer> spawnCountsColor;
  public static ConfigValue<Integer> miscColor;
  public static ConfigValue<Integer> helpColor;
  public static ConfigValue<Integer> priorityColor;
  public static ConfigValue<Integer> lightColor;
  public static ConfigValue<Integer> lookingAtBlockColor;
  public static ConfigValue<Integer> lookingAtFluidColor;
  public static ConfigValue<Integer> lookingAtEntityColor;

  static {
    Pair<F3BackportClient, ModConfigSpec> pair = new ModConfigSpec.Builder().configure(F3BackportClient::new);

    // Store the resulting values
    CONFIG = pair.getLeft();
    CLIENT_SPEC = pair.getRight();

    ModConfigSpec.Builder builder = new ModConfigSpec.Builder();
  }

  private F3BackportClient(ModConfigSpec.Builder builder) {
    builder.push("F3-Backport");
    enableMod = builder.comment(StringLine.builder().boolDesc("Enable the mod?", Client.DEFAULT_ENABLE_MOD))
        .define("enable_mod", Client.DEFAULT_ENABLE_MOD);
    booleanConfigs.add(enableMod);

    debugGuiScale = builder.comment(StringLine.builder()
                                        .addLine("The scale of the Debug Overlay (F3)")
                                        .addLine(" Set to -1 to follow the current Minecraft Gui Scale setting")
                                        .lastLine(" Set to 0 for the Minecraft 'auto' setting"))
        .defineInRange("debug_gui_scale", Client.DEFAULT_DEBUG_GUI_SCALE, Client.DEFAULT_DEBUG_GUI_SCALE_MIN,
                       Client.DEFAULT_DEBUG_GUI_SCALE_MAX);
    integerConfigs.add(debugGuiScale);

    // F3-Backport.Priority
    builder.push("Priority");
    enableGameVersion = builder.comment(
            StringLine.builder().boolDesc("Enable the Game Version entry?", Client.DEFAULT_ENABLE_GAME_VERSION))
        .define("enable_game_version", Client.DEFAULT_ENABLE_GAME_VERSION);
    booleanConfigs.add(enableGameVersion);

    enableFps = builder.comment(StringLine.builder().boolDesc("Enable the FPS entry?", Client.DEFAULT_ENABLE_FPS))
        .define("enable_fps", Client.DEFAULT_ENABLE_FPS);
    booleanConfigs.add(enableFps);
    builder.pop();

    // F3-Backport.Position
    builder.push("Position");
    enablePlayerPosition = builder.comment(
            StringLine.builder().boolDesc("Enable the Player Position entry?", Client.DEFAULT_ENABLE_PLAYER_POSITION))
        .define("enable_player_position", Client.DEFAULT_ENABLE_PLAYER_POSITION);
    booleanConfigs.add(enablePlayerPosition);

    enablePlayerSpeed = builder.comment(
            StringLine.builder().boolDesc("Enable the Player Speed entry?", Client.DEFAULT_ENABLE_PLAYER_SPEED))
        .define("enable_player_speed", Client.DEFAULT_ENABLE_PLAYER_SPEED);
    booleanConfigs.add(enablePlayerSpeed);

    enablePlayerSectionPosition = builder.comment(StringLine.builder()
                                                      .boolDesc("Enable the Player Section Position entry?",
                                                                Client.DEFAULT_ENABLE_PLAYER_SECTION_POSITION))
        .define("enable_player_section_position", Client.DEFAULT_ENABLE_PLAYER_SECTION_POSITION);
    booleanConfigs.add(enablePlayerSectionPosition);

    enableBiome = builder.comment(StringLine.builder().boolDesc("Enable the Biome entry?", Client.DEFAULT_ENABLE_BIOME))
        .define("enable_biome", Client.DEFAULT_ENABLE_BIOME);
    booleanConfigs.add(enableBiome);
    builder.pop();

    // F3-Backport.Memory
    builder.push("Memory");
    enableMemory = builder.comment(
            StringLine.builder().boolDesc("Enable the Memory entry?", Client.DEFAULT_ENABLE_MEMORY))
        .define("enable_memory", Client.DEFAULT_ENABLE_MEMORY);
    booleanConfigs.add(enableMemory);

    enableDetailedMemory = builder.comment(
            StringLine.builder().boolDesc("Enable the Detailed Memory entry?", Client.DEFAULT_ENABLE_DETAILED_MEMORY))
        .define("enable_detailed_memory", Client.DEFAULT_ENABLE_DETAILED_MEMORY);
    booleanConfigs.add(enableDetailedMemory);
    builder.pop();

    // F3-Backport.System_Specs
    builder.push("System_Specs");
    enableSystemSpecs = builder.comment(
            StringLine.builder().boolDesc("Enable the System Specs entry?", Client.DEFAULT_ENABLE_SYSTEM_SPECS))
        .define("enable_system_specs", Client.DEFAULT_ENABLE_SYSTEM_SPECS);
    booleanConfigs.add(enableSystemSpecs);
    builder.pop();

    // F3-Backport.Performance_Impactors
    builder.push("Performance_Impactors");
    enableSimplePerfImpactors = builder.comment(StringLine.builder()
                                                    .boolDesc("Enable the Simple Performance Impactors entry?",
                                                              Client.DEFAULT_ENABLE_SIMPLE_PERF_IMPACTORS))
        .define("enable_simple_perf_impactors", Client.DEFAULT_ENABLE_SIMPLE_PERF_IMPACTORS);
    booleanConfigs.add(enableSimplePerfImpactors);
    builder.pop();

    // F3-Backport.Light
    builder.push("Light");
    enableLight = builder.comment(StringLine.builder().boolDesc("Enable the Light entry?", Client.DEFAULT_ENABLE_LIGHT))
        .define("enable_light", Client.DEFAULT_ENABLE_LIGHT);
    booleanConfigs.add(enableLight);
    builder.pop();

    // F3-Backport.Heightmap
    builder.push("Heightmap");
    enableHeightmap = builder.comment(
            StringLine.builder().boolDesc("Enable the Heightmap entry?", Client.DEFAULT_ENABLE_HEIGHTMAP))
        .define("enable_heightmap", Client.DEFAULT_ENABLE_HEIGHTMAP);
    booleanConfigs.add(enableHeightmap);
    builder.pop();

    // F3-Backport.Spawn_Counts
    builder.push("Spawn_Counts");
    enableSpawnCounts = builder.comment(
            StringLine.builder().boolDesc("Enable the Spawn Counts entry?", Client.DEFAULT_ENABLE_SPAWN_COUNTS))
        .define("enable_spawn_counts", Client.DEFAULT_ENABLE_SPAWN_COUNTS);
    booleanConfigs.add(enableSpawnCounts);
    builder.pop();

    // F3-Backport.Misc
    builder.push("Misc");
    enableGpuUtilization = builder.comment(
            StringLine.builder().boolDesc("Enable the Gpu Utilization entry?", Client.DEFAULT_ENABLE_GPU_UTILIZATION))
        .define("enable_gpu_utilization", Client.DEFAULT_ENABLE_GPU_UTILIZATION);
    booleanConfigs.add(enableGpuUtilization);

    enableDayCount = builder.comment(
            StringLine.builder().boolDesc("Enable the Day Count entry?", Client.DEFAULT_ENABLE_DAY_COUNT))
        .define("enable_day_count", Client.DEFAULT_ENABLE_DAY_COUNT);
    booleanConfigs.add(enableDayCount);

    enableEntityRenderStats = builder.comment(StringLine.builder()
                                                  .boolDesc("Enable the Entity Render Stats entry?",
                                                            Client.DEFAULT_ENABLE_ENTITY_RENDER_STATS))
        .define("enable_entity_render_stats", Client.DEFAULT_ENABLE_ENTITY_RENDER_STATS);
    booleanConfigs.add(enableEntityRenderStats);

    enableLocalDifficulty = builder.comment(
            StringLine.builder().boolDesc("Enable the Local Difficulty entry?", Client.DEFAULT_ENABLE_LOCAL_DIFFICULTY))
        .define("enable_local_difficulty", Client.DEFAULT_ENABLE_LOCAL_DIFFICULTY);
    booleanConfigs.add(enableLocalDifficulty);

    enableSoundMood = builder.comment(
            StringLine.builder().boolDesc("Enable the Sound Mood entry?", Client.DEFAULT_ENABLE_SOUND_MOOD))
        .define("enable_sound_mood", Client.DEFAULT_ENABLE_SOUND_MOOD);
    booleanConfigs.add(enableSoundMood);

    enableTps = builder.comment(StringLine.builder().boolDesc("Enable the TPS entry?", Client.DEFAULT_ENABLE_TPS))
        .define("enable_tps", Client.DEFAULT_ENABLE_TPS);
    booleanConfigs.add(enableTps);
    builder.pop();

    // F3-Backport.Chunk_Rendering
    builder.push("Chunk_Rendering");
    builder.pop();

    // F3-Backport.Chunk_Generation
    builder.push("Chunk_Generation");

    builder.pop();

    // F3-Backport.Help
    builder.push("Help");
    builder.pop();

    // F3-Backport.Looking_At
    builder.push("Looking_At");
    builder.pop();

    builder.push("Colors");
    alphaColor = builder.comment(StringLine.builder()
                                     .addLine("The value used for the alpha channel used for all the colors.")
                                     .addLine(" 0 -> Full Transparent")
                                     .lastLine(" 255 -> Full Opaque"))
        .defineInRange("alpha_color", Color.DEFAULT_ALPHA_COLOR, 0, 255);
    priorityColor = builder.comment(StringLine.builder().rgbDesc("Priority", Color.DEFAULT_PRIORITY_COLOR))
        .define("priority_color", Color.DEFAULT_PRIORITY_COLOR);
    colorConfigs.add(priorityColor);

    positionColor = builder.comment(StringLine.builder().rgbDesc("Position", Color.DEFAULT_POSITION_COLOR))
        .define("position_color", Color.DEFAULT_POSITION_COLOR);
    colorConfigs.add(positionColor);

    memoryColor = builder.comment(StringLine.builder().rgbDesc("Memory", Color.DEFAULT_MEMORY_COLOR))
        .define("memory_color", Color.DEFAULT_MEMORY_COLOR);
    colorConfigs.add(memoryColor);

    systemSpecsColor = builder.comment(StringLine.builder().rgbDesc("System Specs", Color.DEFAULT_SYSTEM_SPECS_COLOR))
        .define("system_specs_color", Color.DEFAULT_SYSTEM_SPECS_COLOR);
    colorConfigs.add(systemSpecsColor);

    perfImpactorsColor = builder.comment(
            StringLine.builder().rgbDesc("Performance Impactors", Color.DEFAULT_PERF_IMPACTORS_COLOR))
        .define("performance_impactors_color", Color.DEFAULT_PERF_IMPACTORS_COLOR);
    colorConfigs.add(perfImpactorsColor);

    lightColor = builder.comment(StringLine.builder().rgbDesc("Light", Color.DEFAULT_LIGHT_COLOR))
        .define("light_color", Color.DEFAULT_LIGHT_COLOR);
    colorConfigs.add(lightColor);

    heightmapColor = builder.comment(StringLine.builder().rgbDesc("Heightmap", Color.DEFAULT_HEIGHTMAP_COLOR))
        .define("heightmap_color", Color.DEFAULT_HEIGHTMAP_COLOR);
    colorConfigs.add(heightmapColor);

    spawnCountsColor = builder.comment(StringLine.builder().rgbDesc("Spawn Counts", Color.DEFAULT_SPAWN_COUNTS_COLOR))
        .define("spawn_counts_color", Color.DEFAULT_SPAWN_COUNTS_COLOR);
    colorConfigs.add(spawnCountsColor);

    miscColor = builder.comment(StringLine.builder().rgbDesc("Misc", Color.DEFAULT_MISC_COLOR))
        .define("misc_color", Color.DEFAULT_MISC_COLOR);
    colorConfigs.add(miscColor);

    chunkRenderingColor = builder.comment(
            StringLine.builder().rgbDesc("Chunk Rendering", Color.DEFAULT_CHUNK_RENDERING_COLOR))
        .define("chunk_rendering_color", Color.DEFAULT_CHUNK_RENDERING_COLOR);
    colorConfigs.add(chunkRenderingColor);

    chunkGenerationColor = builder.comment(
            StringLine.builder().rgbDesc("Chunk Generation", Color.DEFAULT_CHUNK_GENERATION_COLOR))
        .define("chunk_generation_color", Color.DEFAULT_CHUNK_GENERATION_COLOR);
    colorConfigs.add(chunkGenerationColor);

    helpColor = builder.comment(StringLine.builder().rgbDesc("Help", Color.DEFAULT_HELP_COLOR))
        .define("help", Color.DEFAULT_HELP_COLOR);
    colorConfigs.add(helpColor);

    lookingAtBlockColor = builder.comment(
            StringLine.builder().rgbDesc("Looking At Block", Color.DEFAULT_LOOKING_AT_BLOCK_COLOR))
        .define("looking_at_block", Color.DEFAULT_LOOKING_AT_BLOCK_COLOR);
    colorConfigs.add(lookingAtBlockColor);

    lookingAtFluidColor = builder.comment(
            StringLine.builder().rgbDesc("Looking At Fluid", Color.DEFAULT_LOOKING_AT_FLUID_COLOR))
        .define("looking_at_fluid", Color.DEFAULT_LOOKING_AT_FLUID_COLOR);
    colorConfigs.add(lookingAtFluidColor);

    lookingAtEntityColor = builder.comment(
            StringLine.builder().rgbDesc("Looking At Entity", Color.DEFAULT_LOOKING_AT_ENTITY_COLOR))
        .define("looking_at_entity", Color.DEFAULT_LOOKING_AT_ENTITY_COLOR);
    colorConfigs.add(lookingAtEntityColor);
    builder.pop();
  }

  private static <T> T getOrDefault(ConfigValue<T> config) {
    if (CLIENT_SPEC.isLoaded()) {
      return config.get();
    } else {
      return config.getDefault();
    }
  }

  public static Map<ConfigValue<?>, ResourceLocation> getConfigValueMap() {
    configValueMap.put(enableGameVersion, DebugScreenEntries.GAME_VERSION);
    configValueMap.put(enableFps, DebugScreenEntries.FPS);
    configValueMap.put(enablePlayerPosition, DebugScreenEntries.PLAYER_POSITION);
    configValueMap.put(enablePlayerSpeed, DebugScreenEntries.PLAYER_SPEED);
    configValueMap.put(enablePlayerSectionPosition, DebugScreenEntries.PLAYER_SECTION_POSITION);
    configValueMap.put(enableBiome, DebugScreenEntries.BIOME);
    configValueMap.put(enableMemory, DebugScreenEntries.MEMORY);
    configValueMap.put(enableDetailedMemory, DebugScreenEntries.DETAILED_MEMORY);
    configValueMap.put(enableSystemSpecs, DebugScreenEntries.SYSTEM_SPECS);
    configValueMap.put(enableSimplePerfImpactors, DebugScreenEntries.SIMPLE_PERFORMANCE_IMPACTORS);
    configValueMap.put(enableLight, DebugScreenEntries.LIGHT_LEVELS);
    configValueMap.put(enableHeightmap, DebugScreenEntries.HEIGHTMAP);
    configValueMap.put(enableSpawnCounts, DebugScreenEntries.ENTITY_SPAWN_COUNTS);
    configValueMap.put(enableGpuUtilization, DebugScreenEntries.GPU_UTILIZATION);
    configValueMap.put(enableDayCount, DebugScreenEntries.DAY_COUNT);
    configValueMap.put(enableEntityRenderStats, DebugScreenEntries.ENTITY_RENDER_STATS);
    configValueMap.put(enableLocalDifficulty, DebugScreenEntries.LOCAL_DIFFICULTY);
    configValueMap.put(enableSoundMood, DebugScreenEntries.SOUND_MOOD);
    configValueMap.put(enableTps, DebugScreenEntries.TPS);

    return configValueMap;
  }

  // F3-Backport
  public static boolean getEnableMod() {
    return getOrDefault(enableMod);
  }

  public static int getDebugGuiScale() {
    return getOrDefault(debugGuiScale);
  }

  public static boolean getEnableGameVersion() {
    return getOrDefault(enableGameVersion);
  }

  public static boolean getEnableFps() {
    return getOrDefault(enableFps);
  }

  public static boolean getEnableTps() {
    return getOrDefault(enableTps);
  }

  public static boolean getEnableMemory() {
    return getOrDefault(enableMemory);
  }

  public static boolean getEnableSystemSpecs() {
    return getOrDefault(enableSystemSpecs);
  }

  public static boolean getPlayerPosition() {
    return getOrDefault(enablePlayerPosition);
  }

  public static boolean getEnableBiome() {
    return getOrDefault(enableBiome);
  }

  public static boolean getEnableGpuUtilization() {
    return getOrDefault(enableGpuUtilization);
  }

  public static boolean getEnableSimplePerfImpactors() {
    return getOrDefault(enableSimplePerfImpactors);
  }

  public static boolean getEnableDayCount() {
    return getOrDefault(enableDayCount);
  }

  public static boolean getEnableDetailedMemory() {
    return getOrDefault(enableDetailedMemory);
  }

  public static boolean getEnableEntityRenderStats() {
    return getOrDefault(enableEntityRenderStats);
  }

  public static boolean getEnableHeightmap() {
    return getOrDefault(enableHeightmap);
  }

  public static boolean getLight() {
    return getOrDefault(enableLight);
  }

  public static boolean getLocalDifficulty() {
    return getOrDefault(enableLocalDifficulty);
  }

  public static boolean getPlayerSpeed() {
    return getOrDefault(enablePlayerSpeed);
  }

  public static boolean getPlayerSectionPosition() {
    return getOrDefault(enablePlayerSectionPosition);
  }

  public static boolean getSoundMood() {
    return getOrDefault(enableSoundMood);
  }

  public static boolean getSpawnCounts() {
    return getOrDefault(enableSpawnCounts);
  }

  public static int getAlphaColor() {
    return getOrDefault(alphaColor);
  }

  public static int getMemoryColor() {
    return getOrDefault(memoryColor);
  }

  public static int getPositionColor() {
    return getOrDefault(positionColor);
  }

  public static int getChunkRenderingColor() {
    return getOrDefault(chunkRenderingColor);
  }

  public static int getPerfImpactorsColor() {
    return getOrDefault(perfImpactorsColor);
  }

  public static int getSystemSpecsColor() {
    return getOrDefault(systemSpecsColor);
  }

  public static int getHeightmapColor() {
    return getOrDefault(heightmapColor);
  }

  public static int getChunkGenerationColor() {
    return getOrDefault(chunkGenerationColor);
  }

  public static int getSpawnCountsColor() {
    return getOrDefault(spawnCountsColor);
  }

  public static int getMiscColor() {
    return getOrDefault(miscColor);
  }

  public static int getHelpColor() {
    return getOrDefault(helpColor);
  }

  public static int getPriorityColor() {
    return getOrDefault(priorityColor);
  }

  public static int getLightColor() {
    return getOrDefault(lightColor);
  }

  public static int getLookingAtBlockColor() {
    return getOrDefault(lookingAtBlockColor);
  }

  public static int getLookingAtFluidColor() {
    return getOrDefault(lookingAtFluidColor);
  }

  public static int getLookingAtEntityColor() {
    return getOrDefault(lookingAtEntityColor);
  }
}
