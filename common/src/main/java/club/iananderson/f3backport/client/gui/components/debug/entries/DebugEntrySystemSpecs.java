package club.iananderson.f3backport.client.gui.components.debug.entries;

import club.iananderson.f3backport.Constants;
import club.iananderson.f3backport.client.gui.components.debug.DebugGroups;
import club.iananderson.f3backport.client.gui.components.debug.DebugScreenDisplayer;
import club.iananderson.f3backport.client.gui.components.debug.DebugScreenEntry;
import club.iananderson.f3backport.util.PlatformUtil;
import club.iananderson.f3backport.util.device.DeviceFeatures;
import club.iananderson.f3backport.util.device.DeviceInfo;
import club.iananderson.f3backport.util.device.DeviceLimits;
import club.iananderson.f3backport.util.device.DeviceType;
import club.iananderson.f3backport.util.device.HintsAndWorkarounds;
import com.mojang.blaze3d.platform.Window;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;
import org.lwjgl.opengl.ARBClipControl;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL33C;
import org.lwjgl.opengl.GLCapabilities;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;

public class DebugEntrySystemSpecs implements DebugScreenEntry {
  private static @Nullable String cpuInfo;
  private static final List<String> DEVICE_NAMES_THAT_IMPLY_CPU = List.of("mesa offscreen", "llvmpipe");
  private static final List<String> DEVICE_NAMES_THAT_IMPLY_VIRTUAL = List.of("virtgl");

  public DebugEntrySystemSpecs() {
  }

  public static String getCpuInfo() {
    if (cpuInfo == null) {
      cpuInfo = "<unknown>";

      try {
        CentralProcessor processor = (new SystemInfo()).getHardware().getProcessor();
        cpuInfo = String.format(Locale.ROOT, "%dx %s", processor.getLogicalProcessorCount(),
                                processor.getProcessorIdentifier().getName()).replaceAll("\\s+", " ");
      } catch (Throwable var1) {
      }
    }

    return cpuInfo;
  }

  private static int getMaxSupportedTextureSize() {
    int maxReported = GL33C.glGetInteger(3379);

    for (int texSize = Math.max(32768, maxReported); texSize >= 1024; texSize >>= 1) {
      GL33C.glTexImage2D(32868, 0, 6408, texSize, texSize, 0, 6408, 5121, (ByteBuffer) null);
      int width = GL33C.glGetTexLevelParameteri(32868, 0, 4096);
      if (width != 0) {
        return texSize;
      }
    }

    int maxSupportedTextureSize = Math.max(maxReported, 1024);
    Constants.LOG.info("Failed to determine maximum texture size by probing, trying GL_MAX_TEXTURE_SIZE = {}",
                       maxSupportedTextureSize);
    return maxSupportedTextureSize;
  }

  private static boolean isGlOnDx12(final String deviceName) {
    boolean isWindowsArm64 = PlatformUtil.IS_WINDOWS && PlatformUtil.IS_AARCH64;
    return isWindowsArm64 || deviceName.startsWith("D3D12");
  }

  private static boolean isAmd(final String renderer) {
    return renderer.contains("AMD");
  }

  private static boolean isNvidia(final String renderer) {
    return renderer.toLowerCase(Locale.ROOT).contains("nvidia");
  }

  private static DeviceType guessDeviceType(final String renderer, final String vendor) {
    if (vendor.contains("intel")) {
      return renderer.contains("arc") ? DeviceType.DISCRETE : DeviceType.INTEGRATED;
    }
    else {
      for (String string : DEVICE_NAMES_THAT_IMPLY_CPU) {
        if (renderer.contains(string)) {
          return DeviceType.CPU;
        }
      }

      for (String string : DEVICE_NAMES_THAT_IMPLY_VIRTUAL) {
        if (renderer.contains(string)) {
          return DeviceType.VIRTUAL;
        }
      }

      return DeviceType.OTHER;
    }
  }

  public static DeviceInfo createDeviceInfo(final GLCapabilities capabilities, final int maxSupportedAnisotropy,
      final Set<String> enabledExtensions) {
    String renderer = GL33C.glGetString(7937);
    String vendor = GL33C.glGetString(7936);
    String rendererLowerCase = renderer.toLowerCase(Locale.ROOT);
    String vendorLowerCase = vendor.toLowerCase(Locale.ROOT);
    int drawIndirectCount = enabledExtensions.contains("GL_ARB_multi_draw_indirect") ? Integer.MAX_VALUE : (
        enabledExtensions.contains("GL_ARB_draw_indirect") ? 1 : 0);
    return new DeviceInfo(renderer, vendor, GL33C.glGetString(7938), capabilities.GL_ARB_clip_control, "OpenGL", 1.0F,
                          new DeviceLimits(maxSupportedAnisotropy, GL33C.glGetInteger(35380),
                                           getMaxSupportedTextureSize(), Long.MAX_VALUE, 0, GL33C.glGetInteger(34852),
                                           drawIndirectCount),
                          new DeviceFeatures(true, enabledExtensions.contains("GL_ARB_shader_draw_parameters"), false,
                                             true, enabledExtensions.contains("GL_ARB_multi_draw_indirect"),
                                             enabledExtensions.contains("GL_ARB_draw_indirect"),
                                             enabledExtensions.contains("GL_ARB_base_instance"),
                                             enabledExtensions.contains("GL_ARB_buffer_storage")),
                          Collections.unmodifiableSet(enabledExtensions),
                          new HintsAndWorkarounds(isGlOnDx12(renderer), isAmd(renderer),
                                                  PlatformUtil.isAppleSiliconMac(renderer),
                                                  vendorLowerCase.contains("intel") && !rendererLowerCase.contains(
                                                      "arc")), guessDeviceType(rendererLowerCase, vendorLowerCase));
  }

  public void display(final DebugScreenDisplayer displayer, final @Nullable Level serverOrClientLevel,
      final @Nullable LevelChunk clientChunk, final @Nullable LevelChunk serverChunk) {
    GLCapabilities capabilities = GL.createCapabilities();
    Set<String> enabledExtensions = new HashSet<>();
    int maxSupportedAnisotropy;
    if (capabilities.GL_EXT_texture_filter_anisotropic) {
      maxSupportedAnisotropy = Mth.floor(GL33C.glGetFloat(34047));
      enabledExtensions.add("GL_EXT_texture_filter_anisotropic");
    }
    else {
      maxSupportedAnisotropy = 1;
    }

    if (capabilities.GL_ARB_clip_control) {
      ARBClipControl.glClipControl(36001, 37727);
      enabledExtensions.add("GL_ARB_clip_control");
    }

    if (capabilities.GL_ARB_shader_draw_parameters) {
      enabledExtensions.add("GL_ARB_shader_draw_parameters");
    }

    if (capabilities.GL_ARB_draw_indirect) {
      enabledExtensions.add("GL_ARB_draw_indirect");
      if (capabilities.GL_ARB_multi_draw_indirect) {
        enabledExtensions.add("GL_ARB_multi_draw_indirect");
      }
    }

    if (capabilities.GL_ARB_base_instance) {
      enabledExtensions.add("GL_ARB_base_instance");
    }

    DeviceInfo deviceInfo = createDeviceInfo(capabilities, maxSupportedAnisotropy, enabledExtensions);
    Window window = Minecraft.getInstance().getWindow();
    displayer.addFactToGroup(DebugGroups.SYSTEM_SPECS, "Java",
                             (fact) -> fact.value(System.getProperty("java.version")));
    displayer.addFactToGroup(DebugGroups.SYSTEM_SPECS, "CPU", (fact) -> fact.value(getCpuInfo()));
    displayer.addFactToGroup(DebugGroups.SYSTEM_SPECS, "Display",
                             (fact) -> fact.value(window.getWidth()).text("x").value(window.getHeight()).text(" (")
                                 .value(deviceInfo.vendorName()).text(")"));
    displayer.addFactToGroup(DebugGroups.SYSTEM_SPECS, "Window",
                             (fact) -> fact.value(window.getScreenWidth()).text("x").value(window.getScreenHeight()));
    displayer.addToGroup(DebugGroups.SYSTEM_SPECS, List.of(
        String.format(Locale.ROOT, "%s%s", deviceInfo.name(), this.typeName(deviceInfo.type())),
        String.format(Locale.ROOT, "%s %s", deviceInfo.backendName(), this.firstLine(deviceInfo.driverInfo()))));
  }

  private String firstLine(final String value) {
    return (String) value.lines().findFirst().orElse(value);
  }

  private String typeName(final DeviceType type) {
    String typeName;
    switch (type) {
      case OTHER -> typeName = "";
      case INTEGRATED -> typeName = " (iGPU)";
      case DISCRETE -> typeName = " (dGPU)";
      case VIRTUAL -> typeName = " (vGPU)";
      case CPU -> typeName = " (software)";
      default -> throw new MatchException((String) null, (Throwable) null);
    }

    return typeName;
  }

  public boolean isAllowed(final boolean reducedDebugInfo) {
    return true;
  }
}
