package club.iananderson.f3backport.config;

import club.iananderson.f3backport.client.gui.components.debug.entries.DebugScreenEntries;
import club.iananderson.f3backport.config.DefaultValues.Client;
import club.iananderson.f3backport.util.StringLine;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.common.ModConfigSpec.ConfigValue;

public class F3BackportClient {
  public static final ModConfigSpec CLIENT_SPEC;
  public static List<ConfigValue<Boolean>> booleanConfigs = new ArrayList<>();
  public static List<ConfigValue<Integer>> integerConfigs = new ArrayList<>();
  private static final Map<ConfigValue<?>, ResourceLocation> configValueMap = new HashMap<>();
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

  static {
    ModConfigSpec.Builder builder = new ModConfigSpec.Builder();
    setupConfig(builder);
    CLIENT_SPEC = builder.build();
  }

  private static void setupConfig(ModConfigSpec.Builder builder) {
    builder.push("F3-Backport");
    enableMod = builder.comment(StringLine.builder()
                                    .addLine("Enable the mod?")
                                    .addLine("(true/false)")
                                    .lastLine("Default is " + Client.DEFAULT_ENABLE_MOD + "."))
        .define("enable_mod", Client.DEFAULT_ENABLE_MOD);
    booleanConfigs.add(enableMod);

    debugGuiScale = builder.comment(StringLine.builder()
                                        .addLine("The scale of the debug (F3) screen")
                                        .addLine("Set to -1 to follow the current Minecraft Gui Scale setting")
                                        .addLine("Set to 0 for the Minecraft 'auto' setting")
                                        .lastLine("Default is " + Client.DEFAULT_DEBUG_GUI_SCALE + "."))
        .defineInRange("debug_gui_scale", Client.DEFAULT_DEBUG_GUI_SCALE, Client.DEFAULT_DEBUG_GUI_SCALE_MIN,
                       Client.DEFAULT_DEBUG_GUI_SCALE_MAX);
    integerConfigs.add(debugGuiScale);

    // F3-Backport.Priority
    builder.push("Priority");
    enableGameVersion = builder.comment(StringLine.builder()
                                            .addLine("Enable the Game Version entry?")
                                            .addLine("(true/false)")
                                            .lastLine("Default is " + Client.DEFAULT_ENABLE_GAME_VERSION + "."))
        .define("enable_game_version", Client.DEFAULT_ENABLE_GAME_VERSION);
    booleanConfigs.add(enableGameVersion);

    enableFps = builder.comment(StringLine.builder()
                                    .addLine("Enable the FPS entry")
                                    .addLine("(true/false)")
                                    .lastLine("Default is " + Client.DEFAULT_ENABLE_FPS + "."))
        .define("enable_fps", Client.DEFAULT_ENABLE_FPS);
    booleanConfigs.add(enableFps);
    builder.pop();

    // F3-Backport.Position
    builder.push("Position");
    enablePlayerPosition = builder.comment(StringLine.builder()
                                               .addLine("Enable the Player Position entry?")
                                               .addLine("(true/false)")
                                               .lastLine("Default is " + Client.DEFAULT_ENABLE_PLAYER_POSITION + "."))
        .define("enable_player_position", Client.DEFAULT_ENABLE_PLAYER_POSITION);
    booleanConfigs.add(enablePlayerPosition);

    enablePlayerSpeed = builder.comment(StringLine.builder()
                                            .addLine("Enable the Player Speed entry?")
                                            .addLine("(true/false)")
                                            .lastLine("Default is " + Client.DEFAULT_ENABLE_PLAYER_SPEED + "."))
        .define("enable_player_speed", Client.DEFAULT_ENABLE_PLAYER_SPEED);
    booleanConfigs.add(enablePlayerSpeed);

    enablePlayerSectionPosition = builder.comment(StringLine.builder()
                                                      .addLine("Enable the Player Section Position entry?")
                                                      .addLine("(true/false)")
                                                      .lastLine(
                                                          "Default is " + Client.DEFAULT_ENABLE_PLAYER_SECTION_POSITION
                                                              + "."))
        .define("enable_player_section_position", Client.DEFAULT_ENABLE_PLAYER_SECTION_POSITION);
    booleanConfigs.add(enablePlayerSectionPosition);

    enableBiome = builder.comment(StringLine.builder()
                                      .addLine("Enable the Biome entry?")
                                      .addLine("(true/false)")
                                      .lastLine("Default is " + Client.DEFAULT_ENABLE_BIOME + "."))
        .define("enable_biome", Client.DEFAULT_ENABLE_BIOME);
    booleanConfigs.add(enableBiome);
    builder.pop();

    // F3-Backport.Memory
    builder.push("Memory");
    enableMemory = builder.comment(StringLine.builder()
                                       .addLine("Enable the Memory entry?")
                                       .addLine("(true/false)")
                                       .lastLine("Default is " + Client.DEFAULT_ENABLE_MEMORY + "."))
        .define("enable_memory", Client.DEFAULT_ENABLE_MEMORY);
    booleanConfigs.add(enableMemory);

    enableDetailedMemory = builder.comment(StringLine.builder()
                                               .addLine("Enable the Detailed Memory entry?")
                                               .addLine("(true/false)")
                                               .lastLine("Default is " + Client.DEFAULT_ENABLE_DETAILED_MEMORY + "."))
        .define("enable_detailed_memory", Client.DEFAULT_ENABLE_DETAILED_MEMORY);
    booleanConfigs.add(enableDetailedMemory);
    builder.pop();

    // F3-Backport.System_Specs
    builder.push("System_Specs");
    enableSystemSpecs = builder.comment(StringLine.builder()
                                            .addLine("Enable the System Specs entry?")
                                            .addLine("(true/false)")
                                            .lastLine("Default is " + Client.DEFAULT_ENABLE_SYSTEM_SPECS + "."))
        .define("enable_system_specs", Client.DEFAULT_ENABLE_SYSTEM_SPECS);
    booleanConfigs.add(enableSystemSpecs);
    builder.pop();

    // F3-Backport.Performance_Impactors
    builder.push("Performance_Impactors");
    enableSimplePerfImpactors = builder.comment(StringLine.builder()
                                                    .addLine("Enable the Simple Performance Impactors entry?")
                                                    .addLine("(true/false)")
                                                    .lastLine(
                                                        "Default is " + Client.DEFAULT_ENABLE_SIMPLE_PERF_IMPACTORS
                                                            + "."))
        .define("enable_simple_performance_impactors", Client.DEFAULT_ENABLE_SIMPLE_PERF_IMPACTORS);
    booleanConfigs.add(enableSimplePerfImpactors);
    builder.pop();

    // F3-Backport.Light
    builder.push("Light");
    enableLight = builder.comment(StringLine.builder()
                                      .addLine("Enable the Light entry?")
                                      .addLine("(true/false)")
                                      .lastLine("Default is " + Client.DEFAULT_ENABLE_LIGHT + "."))
        .define("enable_light", Client.DEFAULT_ENABLE_LIGHT);
    booleanConfigs.add(enableLight);
    builder.pop();

    // F3-Backport.Heightmap
    builder.push("Heightmap");
    enableHeightmap = builder.comment(StringLine.builder()
                                          .addLine("Enable the Heightmap entry?")
                                          .addLine("(true/false)")
                                          .lastLine("Default is " + Client.DEFAULT_ENABLE_HEIGHTMAP + "."))
        .define("enable_heightmap", Client.DEFAULT_ENABLE_HEIGHTMAP);
    booleanConfigs.add(enableHeightmap);
    builder.pop();

    // F3-Backport.Spawn_Counts
    builder.push("Spawn_Counts");
    enableSpawnCounts = builder.comment(StringLine.builder()
                                            .addLine("Enable the Spawn Counts entry?")
                                            .addLine("(true/false)")
                                            .lastLine("Default is " + Client.DEFAULT_ENABLE_SPAWN_COUNTS + "."))
        .define("enable_spawn_counts", Client.DEFAULT_ENABLE_SPAWN_COUNTS);
    booleanConfigs.add(enableSpawnCounts);
    builder.pop();

    // F3-Backport.Misc
    builder.push("Misc");
    enableGpuUtilization = builder.comment(StringLine.builder()
                                               .addLine("Enable the Gpu Utilization entry?")
                                               .addLine("(true/false)")
                                               .lastLine("Default is " + Client.DEFAULT_ENABLE_GPU_UTILIZATION + "."))
        .define("enable_gpu_utilization", Client.DEFAULT_ENABLE_GPU_UTILIZATION);
    booleanConfigs.add(enableGpuUtilization);

    enableDayCount = builder.comment(StringLine.builder()
                                         .addLine("Enable the Day Count entry?")
                                         .addLine("(true/false)")
                                         .lastLine("Default is " + Client.DEFAULT_ENABLE_DAY_COUNT + "."))
        .define("enable_day_count", Client.DEFAULT_ENABLE_DAY_COUNT);
    booleanConfigs.add(enableDayCount);

    enableEntityRenderStats = builder.comment(StringLine.builder()
                                                  .addLine("Enable the Entity Render Stats entry?")
                                                  .addLine("(true/false)")
                                                  .lastLine(
                                                      "Default is " + Client.DEFAULT_ENABLE_ENTITY_RENDER_STATS + "."))
        .define("enable_entity_render_stats", Client.DEFAULT_ENABLE_ENTITY_RENDER_STATS);
    booleanConfigs.add(enableEntityRenderStats);

    enableLocalDifficulty = builder.comment(StringLine.builder()
                                                .addLine("Enable the Local Difficulty entry?")
                                                .addLine("(true/false)")
                                                .lastLine("Default is " + Client.DEFAULT_ENABLE_LOCAL_DIFFICULTY + "."))
        .define("enable_local_difficulty", Client.DEFAULT_ENABLE_LOCAL_DIFFICULTY);
    booleanConfigs.add(enableLocalDifficulty);

    enableSoundMood = builder.comment(StringLine.builder()
                                          .addLine("Enable the Sound Mood entry?")
                                          .addLine("(true/false)")
                                          .lastLine("Default is " + Client.DEFAULT_ENABLE_SOUND_MOOD + "."))
        .define("enable_sound_mood", Client.DEFAULT_ENABLE_SOUND_MOOD);
    booleanConfigs.add(enableSoundMood);

    enableTps = builder.comment(StringLine.builder()
                                    .addLine("Enable the TPS entry?")
                                    .addLine("(true/false)")
                                    .lastLine("Default is " + Client.DEFAULT_ENABLE_TPS + "."))
        .define("enable_tps", Client.DEFAULT_ENABLE_TPS);
    booleanConfigs.add(enableTps);
    builder.pop();
    builder.pop();
  }

  private static <T> T getOrDefault(ConfigValue<T> config) {
    if (CLIENT_SPEC.isLoaded()) {
      return config.get();
    } else {
      return config.getDefault();
    }
  }

  public static Map<ConfigValue<?>, ResourceLocation> getConfigValueMap(){
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
}
