package club.iananderson.f3backport.config;

public class DefaultValues {
  public static class Client {
    public static final boolean DEFAULT_ENABLE_MOD = true;
    public static final boolean DEFAULT_ENABLE_GAME_VERSION = true;
    public static final boolean DEFAULT_ENABLE_FPS = true;
    public static final boolean DEFAULT_ENABLE_TPS = true;
    public static final boolean DEFAULT_ENABLE_MEMORY = true;
    public static final boolean DEFAULT_ENABLE_SYSTEM_SPECS = true;
    public static final boolean DEFAULT_ENABLE_PLAYER_POSITION = true;
    public static final boolean DEFAULT_ENABLE_BIOME = true;
    public static final boolean DEFAULT_ENABLE_GPU_UTILIZATION = true;
    public static final boolean DEFAULT_ENABLE_SIMPLE_PERF_IMPACTORS = true;
    public static final boolean DEFAULT_ENABLE_DAY_COUNT = false;
    public static final boolean DEFAULT_ENABLE_DETAILED_MEMORY = false;
    public static final boolean DEFAULT_ENABLE_ENTITY_RENDER_STATS = false;
    public static final boolean DEFAULT_ENABLE_HEIGHTMAP = false;
    public static final boolean DEFAULT_ENABLE_LIGHT = false;
    public static final boolean DEFAULT_ENABLE_LOCAL_DIFFICULTY = false;
    public static final boolean DEFAULT_ENABLE_PLAYER_SPEED = false;
    public static final boolean DEFAULT_ENABLE_PLAYER_SECTION_POSITION = true;
    public static final boolean DEFAULT_ENABLE_SOUND_MOOD = false;
    public static final boolean DEFAULT_ENABLE_SPAWN_COUNTS = false;
    public static final int DEFAULT_DEBUG_GUI_SCALE = 2;
    public static final int DEFAULT_DEBUG_GUI_SCALE_MIN = -1;
    public static final int DEFAULT_DEBUG_GUI_SCALE_MAX = 5;
  }

  public static class Color {
    public static final int DEFAULT_ALPHA_COLOR = 255;
    public static final int DEFAULT_MEMORY_COLOR = 16751360;
    public static final int DEFAULT_POSITION_COLOR = 16777215;
    public static final int DEFAULT_CHUNK_RENDERING_COLOR = 15773856;
    public static final int DEFAULT_PERF_IMPACTORS_COLOR = 65280;
    public static final int DEFAULT_SYSTEM_SPECS_COLOR = 16711680;
    public static final int DEFAULT_HEIGHTMAP_COLOR = 2371737;
    public static final int DEFAULT_CHUNK_GENERATION_COLOR = 10092458;
    public static final int DEFAULT_SPAWN_COUNTS_COLOR = 16729156;
    public static final int DEFAULT_MISC_COLOR = 0; // Should have alpha of 0
    public static final int DEFAULT_HELP_COLOR = 13158600;
    public static final int DEFAULT_PRIORITY_COLOR = 0; // Should have alpha of 0
    public static final int DEFAULT_LIGHT_COLOR = 16776960;
    public static final int DEFAULT_LOOKING_AT_BLOCK_COLOR = 13369599;
    public static final int DEFAULT_LOOKING_AT_FLUID_COLOR = 16763904;
    public static final int DEFAULT_LOOKING_AT_ENTITY_COLOR = 65484;
  }
}
