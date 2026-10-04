package club.iananderson.f3backport.config;

import club.iananderson.f3backport.config.DefaultValues.Client;
import club.iananderson.f3backport.util.StringLine;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.common.ModConfigSpec.ConfigValue;

public class F3BackportClient {
  public static final ModConfigSpec CLIENT_SPEC;
  private static ConfigValue<Boolean> enableMod;
  private static ConfigValue<Boolean> enableGameVersion;
  private static ConfigValue<Boolean> enableFps;
  private static ConfigValue<Boolean> enableTps;
  private static ConfigValue<Boolean> enableMemory;
  private static ConfigValue<Boolean> enableSystemSpecs;
  private static ConfigValue<Boolean> enablePlayerPosition;
  private static ConfigValue<Boolean> enableBiome;
  private static ConfigValue<Boolean> enable3dCrosshair;
  private static ConfigValue<Boolean> enableGpuUtilization;
  private static ConfigValue<Boolean> enableSimplePerformanceImpactors;

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
    enableGameVersion = builder.comment(StringLine.builder()
                                            .addLine("Enable the Game Version entry?")
                                            .addLine("(true/false)")
                                            .lastLine("Default is " + Client.DEFAULT_ENABLE_GAME_VERSION + "."))
        .define("enable_game_version", Client.DEFAULT_ENABLE_GAME_VERSION);
    enableFps = builder.comment(StringLine.builder()
                                    .addLine("Enable the FPS entry")
                                    .addLine("(true/false)")
                                    .lastLine("Default is " + Client.DEFAULT_ENABLE_FPS + "."))
        .define("enable_fps", Client.DEFAULT_ENABLE_FPS);
    enableTps = builder.comment(StringLine.builder()
                                    .addLine("Enable the TPS entry?")
                                    .addLine("(true/false)")
                                    .lastLine("Default is " + Client.DEFAULT_ENABLE_TPS + "."))
        .define("enable_tps", Client.DEFAULT_ENABLE_TPS);
    enableMemory = builder.comment(StringLine.builder()
                                       .addLine("Enable the Memory entry?")
                                       .addLine("(true/false)")
                                       .lastLine("Default is " + Client.DEFAULT_ENABLE_MEMORY + "."))
        .define("enable_memory", Client.DEFAULT_ENABLE_MEMORY);
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
    enableBiome = builder.comment(StringLine.builder()
                                      .addLine("Enable the Biome entry?")
                                      .addLine("(true/false)")
                                      .lastLine("Default is " + Client.DEFAULT_ENABLE_BIOME + "."))
        .define("enable_biome", Client.DEFAULT_ENABLE_BIOME);
    enable3dCrosshair = builder.comment(StringLine.builder()
                                            .addLine("Enable the 3d Crosshair?")
                                            .addLine("(true/false)")
                                            .lastLine("Default is " + Client.DEFAULT_ENABLE_3D_CROSSHAIR + "."))
        .define("enable_3d_crosshair", Client.DEFAULT_ENABLE_3D_CROSSHAIR);
    enableGpuUtilization = builder.comment(StringLine.builder()
                                               .addLine("Enable the Gpu Utilization entry?")
                                               .addLine("(true/false)")
                                               .lastLine("Default is " + Client.DEFAULT_ENABLE_GPU_UTILIZATION + "."))
        .define("enable_gpu_utilization", Client.DEFAULT_ENABLE_GPU_UTILIZATION);
    enableSimplePerformanceImpactors = builder.comment(StringLine.builder()
                                                           .addLine("Enable the Simple Performance Impactors entry?")
                                                           .addLine("(true/false)")
                                                           .lastLine("Default is "
                                                                         + Client.DEFAULT_ENABLE_SIMPLE_PERF_IMPACTORS
                                                                         + "."))
        .define("enable_simple_performance_impactors", Client.DEFAULT_ENABLE_SIMPLE_PERF_IMPACTORS);
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

  public static boolean getEnable3dCrosshair() {
    return getOrDefault(enable3dCrosshair);
  }

  public static void setEnable3dCrosshair(boolean enable) {
    F3BackportClient.enable3dCrosshair.set(enable);
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
}
