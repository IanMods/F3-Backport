package club.iananderson.f3backport.config;

import club.iananderson.f3backport.Common;
import java.util.EnumSet;
import java.util.Locale;
import net.minecraft.network.chat.Component;
import net.neoforged.neoforge.common.ModConfigSpec.ConfigValue;

public enum ConfigEntries {
  ENABLE_MOD("menu.f3backport.config.enableMod.name", "menu.f3backport.config.enableMod.desc",
             F3BackportClient.enableMod),
  DEBUG_GUI_SCALE("menu.f3backport.config.debugGuiScale.name", "menu.f3backport.config.debugGuiScale.desc",
                  F3BackportClient.debugGuiScale),
  ENABLE_GAME_VERSION("menu.f3backport.config.priority.enableGameVersion.name",
                      "menu.f3backport.config.priority.enableGameVersion.desc", F3BackportClient.enableGameVersion),
  ENABLE_FPS("menu.f3backport.config.priority.enableFps.name", "menu.f3backport.config.priority.enableFps.desc",
             F3BackportClient.enableFps),
  ENABLE_PLAYER_POSITION("menu.f3backport.config.position.enablePlayerPosition.name",
                         "menu.f3backport.config.position.enablePlayerPosition.desc",
                         F3BackportClient.enablePlayerPosition),
  ENABLE_PLAYER_SPEED("menu.f3backport.config.position.enablePlayerSpeed.name",
                      "menu.f3backport.config.position.enablePlayerSpeed.desc", F3BackportClient.enablePlayerSpeed),
  ENABLE_PLAYER_SECTION_POSITION("menu.f3backport.config.position.enablePlayerSectionPosition.name",
                                 "menu.f3backport.config.position.enablePlayerSectionPosition.desc",
                                 F3BackportClient.enablePlayerSectionPosition),
  ENABLE_BIOME("menu.f3backport.config.position.enableBiome.name", "menu.f3backport.config.position.enableBiome.desc",
               F3BackportClient.enableBiome),
  ENABLE_MEMORY("menu.f3backport.config.memory.name", "menu.f3backport.config.memory.enableMemory.desc",
                F3BackportClient.enableMemory),
  ENABLE_DETAILED_MEMORY("menu.f3backport.config.memory.enableDetailedMemory.name",
                         "menu.f3backport.config.memory.enableDetailedMemory.desc",
                         F3BackportClient.enableDetailedMemory),
  ENABLE_SYSTEM_SPECS("menu.f3backport.config.systemSpecs.enableSystemSpecs.name",
                      "menu.f3backport.config.systemSpecs.enableSystemSpecs.desc", F3BackportClient.enableSystemSpecs),
  ENABLE_SIMPLE_PERF_IMPACTORS("menu.f3backport.config.perfImpactors.enablePerfImpactors.name",
                               "menu.f3backport.config.perfImpactors.enablePerfImpactors.desc",
                               F3BackportClient.enableSimplePerfImpactors),
  ENABLE_LIGHT("menu.f3backport.config.light.enableLight.name", "menu.f3backport.config.light.enableLight.desc",
               F3BackportClient.enableLight),
  ENABLE_HEIGHTMAP("menu.f3backport.config.heightMap.name", "menu.f3backport.config.heightMap.heightmap.desc",
                   F3BackportClient.enableHeightmap),
  ENABLE_SPAWN_COUNTS("menu.f3backport.config.spawnCounts.enableSpawnCounts.name",
                      "menu.f3backport.config.spawnCounts.enableSpawnCounts.desc", F3BackportClient.enableSystemSpecs),
  ENABLE_GPU_UTILIZATION("menu.f3backport.config.misc.enableGpuUtilization.name",
                         "menu.f3backport.config.misc.enableGpuUtilization.desc",
                         F3BackportClient.enableGpuUtilization),
  ENABLE_DAY_COUNT("menu.f3backport.config.misc.enableDayCount.name", "menu.f3backport.config.misc.enableDayCount.desc",
                   F3BackportClient.enableDayCount),
  ENABLE_ENTITY_RENDER_STATS("menu.f3backport.config.misc.enableEntityRenderStats.name",
                             "menu.f3backport.config.misc.enableEntityRenderStats.desc",
                             F3BackportClient.enableEntityRenderStats),
  ENABLE_LOCAL_DIFFICULTY("menu.f3backport.config.misc.enableLocalDifficulty.name",
                          "menu.f3backport.config.misc.enableLocalDifficulty.desc",
                          F3BackportClient.enableLocalDifficulty),
  ENABLE_SOUND_MOOD("menu.f3backport.config.misc.enableSoundMood.name",
                    "menu.f3backport.config.misc.enableSoundMood.desc", F3BackportClient.enableSoundMood),
  ENABLE_TPS("menu.f3backport.config.misc.enableTps.name", "menu.f3backport.config.misc.enableTps.desc",
             F3BackportClient.enableTps);

  public static final EnumSet<ConfigEntries> CONFIG_ENTRIES_ENUM_LIST = EnumSet.allOf(ConfigEntries.class);
  private final String nameSerialized;
  private final Component nameTranslated;
  private final ConfigValue<?> configValue;

  ConfigEntries(String nameKey, String descKey, ConfigValue<?> configValue) {
    this.nameSerialized = this.toString().toLowerCase(Locale.ROOT);
    this.nameTranslated = Common.translatedText(nameKey);
    this.configValue = configValue;
  }

  public String getNameSerialized() {
    return this.nameSerialized;
  }

  public Component getNameTranslated() {
    return this.nameTranslated;
  }

  public ConfigValue<?> getConfigValue() {
    return this.configValue;
  }
}
