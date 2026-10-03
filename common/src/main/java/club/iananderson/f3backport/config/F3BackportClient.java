package club.iananderson.f3backport.config;

import club.iananderson.f3backport.config.DefaultValues.Client;
import club.iananderson.f3backport.util.StringLine;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.common.ModConfigSpec.ConfigValue;

public class F3BackportClient {
  public static final ModConfigSpec CLIENT_SPEC;
  private static ConfigValue<Boolean> enableMod;

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
    builder.pop();

  }

  private static <T> T getOrDefault(ConfigValue<T> config) {
    if (CLIENT_SPEC.isLoaded()) {
      return config.get();
    } else {
      return config.getDefault();
    }
  }

  // SeasonHUD
  public static boolean getEnableMod() {
    return getOrDefault(enableMod);
  }

  public static void setEnableMod(boolean enable) {
    F3BackportClient.enableMod.set(enable);
  }
}
