package club.iananderson.f3backport.config;

import club.iananderson.f3backport.config.DefaultValues.Client;
import club.iananderson.f3backport.util.StringLine;
import java.util.ArrayList;
import java.util.List;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.common.ModConfigSpec.ConfigValue;

public class F3BackportClient {
  public static final ModConfigSpec CLIENT_SPEC;
  public static List<ConfigValue<Boolean>> booleanConfigs = new ArrayList<>();
  public static List<ConfigValue<Integer>> integerConfigs = new ArrayList<>();
  private static ConfigValue<Boolean> enableMod;
  private static ConfigValue<Boolean> enableGameVersion;
  private static ConfigValue<Boolean> enableFps;
  private static ConfigValue<Boolean> enableTps;
  private static ConfigValue<Boolean> enableMemory;
  private static ConfigValue<Boolean> enableSystemSpecs;
  private static ConfigValue<Boolean> enablePlayerPosition;
  private static ConfigValue<Boolean> enableBiome;
  private static ConfigValue<Boolean> enableGpuUtilization;
  private static ConfigValue<Boolean> enableSimplePerformanceImpactors;
  private static ConfigValue<Boolean> enableDayCount;
  private static ConfigValue<Boolean> enableDetailedMemory;
  private static ConfigValue<Boolean> enableEntityRenderStats;
  private static ConfigValue<Boolean> enableHeightmap;
  private static ConfigValue<Boolean> enableLight;
  private static ConfigValue<Boolean> enableLocalDifficulty;
  private static ConfigValue<Boolean> enablePlayerSpeed;
  private static ConfigValue<Boolean> enablePlayerSectionPosition;
  private static ConfigValue<Boolean> enableSoundMood;
  private static ConfigValue<Boolean> enableSpawnCounts;

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

    enableTps = builder.comment(StringLine.builder()
                                    .addLine("Enable the TPS entry?")
                                    .addLine("(true/false)")
                                    .lastLine("Default is " + Client.DEFAULT_ENABLE_TPS + "."))
        .define("enable_tps", Client.DEFAULT_ENABLE_TPS);
    booleanConfigs.add(enableTps);

    enableMemory = builder.comment(StringLine.builder()
                                       .addLine("Enable the Memory entry?")
                                       .addLine("(true/false)")
                                       .lastLine("Default is " + Client.DEFAULT_ENABLE_MEMORY + "."))
        .define("enable_memory", Client.DEFAULT_ENABLE_MEMORY);
    booleanConfigs.add(enableMemory);

    enableSystemSpecs = builder.comment(StringLine.builder()
                                            .addLine("Enable the System Specs entry?")
                                            .addLine("(true/false)")
                                            .lastLine("Default is " + Client.DEFAULT_ENABLE_SYSTEM_SPECS + "."))
        .define("enable_system_specs", Client.DEFAULT_ENABLE_SYSTEM_SPECS);

    enablePlayerPosition = builder.comment(StringLine.builder()
                                               .addLine("Enable the Player Position entry?")
                                               .addLine("(true/false)")
                                               .lastLine("Default is " + Client.DEFAULT_ENABLE_PLAYER_POSITION + "."))
        .define("enable_player_position", Client.DEFAULT_ENABLE_PLAYER_POSITION);
    booleanConfigs.add(enablePlayerPosition);

    enableBiome = builder.comment(StringLine.builder()
                                      .addLine("Enable the Biome entry?")
                                      .addLine("(true/false)")
                                      .lastLine("Default is " + Client.DEFAULT_ENABLE_BIOME + "."))
        .define("enable_biome", Client.DEFAULT_ENABLE_BIOME);
    booleanConfigs.add(enableBiome);

    enableGpuUtilization = builder.comment(StringLine.builder()
                                               .addLine("Enable the Gpu Utilization entry?")
                                               .addLine("(true/false)")
                                               .lastLine("Default is " + Client.DEFAULT_ENABLE_GPU_UTILIZATION + "."))
        .define("enable_gpu_utilization", Client.DEFAULT_ENABLE_GPU_UTILIZATION);
    booleanConfigs.add(enableGpuUtilization);

    enableSimplePerformanceImpactors = builder.comment(StringLine.builder()
                                                           .addLine("Enable the Simple Performance Impactors entry?")
                                                           .addLine("(true/false)")
                                                           .lastLine("Default is "
                                                                         + Client.DEFAULT_ENABLE_SIMPLE_PERF_IMPACTORS
                                                                         + "."))
        .define("enable_simple_performance_impactors", Client.DEFAULT_ENABLE_SIMPLE_PERF_IMPACTORS);
    booleanConfigs.add(enableSimplePerformanceImpactors);

    enableDayCount = builder.comment(StringLine.builder()
                                         .addLine("Enable the Day Count entry?")
                                         .addLine("(true/false)")
                                         .lastLine("Default is " + Client.DEFAULT_ENABLE_DAY_COUNT + "."))
        .define("enable_day_count", Client.DEFAULT_ENABLE_DAY_COUNT);
    booleanConfigs.add(enableDayCount);

    enableDetailedMemory = builder.comment(StringLine.builder()
                                               .addLine("Enable the Detailed Memory entry?")
                                               .addLine("(true/false)")
                                               .lastLine("Default is " + Client.DEFAULT_ENABLE_DETAILED_MEMORY + "."))
        .define("enable_detailed_memory", Client.DEFAULT_ENABLE_DETAILED_MEMORY);
    booleanConfigs.add(enableDetailedMemory);

    enableEntityRenderStats = builder.comment(StringLine.builder()
                                                  .addLine("Enable the Entity Render Stats entry?")
                                                  .addLine("(true/false)")
                                                  .lastLine(
                                                      "Default is " + Client.DEFAULT_ENABLE_ENTITY_RENDER_STATS + "."))
        .define("enable_entity_render_stats", Client.DEFAULT_ENABLE_ENTITY_RENDER_STATS);
    booleanConfigs.add(enableEntityRenderStats);

    enableHeightmap = builder.comment(StringLine.builder()
                                          .addLine("Enable the Heightmap entry?")
                                          .addLine("(true/false)")
                                          .lastLine("Default is " + Client.DEFAULT_ENABLE_HEIGHTMAP + "."))
        .define("enable_heightmap", Client.DEFAULT_ENABLE_HEIGHTMAP);
    booleanConfigs.add(enableHeightmap);

    enableLight = builder.comment(StringLine.builder()
                                      .addLine("Enable the Light entry?")
                                      .addLine("(true/false)")
                                      .lastLine("Default is " + Client.DEFAULT_ENABLE_LIGHT + "."))
        .define("enable_light", Client.DEFAULT_ENABLE_LIGHT);
    booleanConfigs.add(enableLight);

    enableLocalDifficulty = builder.comment(StringLine.builder()
                                                .addLine("Enable the Local Difficulty entry?")
                                                .addLine("(true/false)")
                                                .lastLine("Default is " + Client.DEFAULT_ENABLE_LOCAL_DIFFICULTY + "."))
        .define("enable_local_difficulty", Client.DEFAULT_ENABLE_LOCAL_DIFFICULTY);
    booleanConfigs.add(enableLocalDifficulty);

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

    enableSoundMood = builder.comment(StringLine.builder()
                                          .addLine("Enable the Sound Mood entry?")
                                          .addLine("(true/false)")
                                          .lastLine("Default is " + Client.DEFAULT_ENABLE_SOUND_MOOD + "."))
        .define("enable_sound_mood", Client.DEFAULT_ENABLE_SOUND_MOOD);
    booleanConfigs.add(enableSoundMood);

    enableSpawnCounts = builder.comment(StringLine.builder()
                                            .addLine("Enable the Spawn Counts entry?")
                                            .addLine("(true/false)")
                                            .lastLine("Default is " + Client.DEFAULT_ENABLE_SPAWN_COUNTS + "."))
        .define("enable_spawn_counts", Client.DEFAULT_ENABLE_SPAWN_COUNTS);
    booleanConfigs.add(enableSpawnCounts);

    builder.pop();
  }

  private static <T> T getOrDefault(ConfigValue<T> config) {
    if (CLIENT_SPEC.isLoaded()) {
      return config.get();
    } else {
      return config.getDefault();
    }
  }

  // F3-Backport
  public static boolean getEnableMod() {
    return getOrDefault(enableMod);
  }

  public static void setEnableMod(boolean enable) {
    F3BackportClient.enableMod.set(enable);
  }

  public static boolean getEnableGameVersion() {
    return getOrDefault(enableGameVersion);
  }

  public static void setEnableGameVersion(boolean enable) {
    F3BackportClient.enableGameVersion.set(enable);
  }

  public static boolean getEnableFps() {
    return getOrDefault(enableFps);
  }

  public static void setEnableFps(boolean enable) {
    F3BackportClient.enableFps.set(enable);
  }

  public static boolean getEnableTps() {
    return getOrDefault(enableTps);
  }

  public static void setEnableTps(boolean enable) {
    F3BackportClient.enableTps.set(enable);
  }

  public static boolean getEnableMemory() {
    return getOrDefault(enableMemory);
  }

  public static void setEnableMemory(boolean enable) {
    F3BackportClient.enableMemory.set(enable);
  }

  public static boolean getEnableSystemSpecs() {
    return getOrDefault(enableSystemSpecs);
  }

  public static void setEnableSystemSpecs(boolean enable) {
    F3BackportClient.enableSystemSpecs.set(enable);
  }

  public static boolean getPlayerPosition() {
    return getOrDefault(enablePlayerPosition);
  }

  public static void setEnablePlayerPosition(boolean enable) {
    F3BackportClient.enablePlayerPosition.set(enable);
  }

  public static boolean getEnableBiome() {
    return getOrDefault(enableBiome);
  }

  public static void setEnableBiome(boolean enable) {
    F3BackportClient.enableBiome.set(enable);
  }

  public static boolean getEnableGpuUtilization() {
    return getOrDefault(enableGpuUtilization);
  }

  public static void setEnableGpuUtilization(boolean enable) {
    F3BackportClient.enableGpuUtilization.set(enable);
  }

  public static boolean getEnableSimplePerformanceImpactors() {
    return getOrDefault(enableSimplePerformanceImpactors);
  }

  public static void setEnableSimplePerformanceImpactors(boolean enable) {
    F3BackportClient.enableSimplePerformanceImpactors.set(enable);
  }

  public static boolean getEnableDayCount() {
    return getOrDefault(enableDayCount);
  }

  public static void setEnableDayCount(boolean enable) {
    F3BackportClient.enableDayCount.set(enable);
  }

  public static boolean getEnableDetailedMemory() {
    return getOrDefault(enableDetailedMemory);
  }

  public static void setEnableDetailedMemory(boolean enable) {
    F3BackportClient.enableDetailedMemory.set(enable);
  }

  public static boolean getEnableEntityRenderStats() {
    return getOrDefault(enableEntityRenderStats);
  }

  public static void setEnableEntityRenderStats(boolean enable) {
    F3BackportClient.enableEntityRenderStats.set(enable);
  }

  public static boolean getEnableHeightmap() {
    return getOrDefault(enableHeightmap);
  }

  public static void setEnableHeightmap(boolean enable) {
    F3BackportClient.enableHeightmap.set(enable);
  }

  public static boolean getLight() {
    return getOrDefault(enableLight);
  }

  public static void setLight(boolean enable) {
    F3BackportClient.enableLight.set(enable);
  }

  public static boolean getLocalDifficulty() {
    return getOrDefault(enableLocalDifficulty);
  }

  public static void setLocalDifficulty(boolean enable) {
    F3BackportClient.enableLocalDifficulty.set(enable);
  }

  public static boolean getPlayerSpeed() {
    return getOrDefault(enablePlayerSpeed);
  }

  public static void setPlayerSpeed(boolean enable) {
    F3BackportClient.enablePlayerSpeed.set(enable);
  }

  public static boolean getPlayerSectionPosition() {
    return getOrDefault(enablePlayerSectionPosition);
  }

  public static void setPlayerSectionPosition(boolean enable) {
    F3BackportClient.enablePlayerSectionPosition.set(enable);
  }

  public static boolean getSoundMood() {
    return getOrDefault(enableSoundMood);
  }

  public static void setSoundMood(boolean enable) {
    F3BackportClient.enableSoundMood.set(enable);
  }

  public static boolean getSpawnCounts() {
    return getOrDefault(enableSpawnCounts);
  }

  public static void setSpawnCounts(boolean enable) {
    F3BackportClient.enableSpawnCounts.set(enable);
  }
}
