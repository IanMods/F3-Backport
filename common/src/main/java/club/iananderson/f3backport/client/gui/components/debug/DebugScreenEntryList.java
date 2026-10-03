package club.iananderson.f3backport.client.gui.components.debug;

import club.iananderson.f3backport.util.DataFixTypes;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import org.jspecify.annotations.Nullable;
import org.slf4j.Logger;

public class DebugScreenEntryList {
  private static final Logger LOGGER = LogUtils.getLogger();
  private static final int DEFAULT_DEBUG_PROFILE_VERSION = 4649;
  private final Map<ResourceLocation, DebugScreenEntryStatus> allStatuses = new HashMap<>();
  private final List<ResourceLocation> currentlyEnabled = new ArrayList<>();
  private @Nullable DebugScreenProfile profile;
  private boolean isOverlayVisible = false;
  private long currentlyEnabledVersion;

  public DebugScreenEntryList(final Minecraft minecraft) {
    Codec<SerializedOptions> codec = DataFixTypes.DEBUG_PROFILE.wrapCodec(SerializedOptions.CODEC,
                                                                          minecraft.getFixerUpper(), 4649);
    this.load();
  }

  public void load() {
    this.resetToProfile(DebugScreenProfile.DEFAULT);
    this.rebuildCurrentList();
  }

  private void resetStatuses(final Map<ResourceLocation, DebugScreenEntryStatus> newEntries) {
    this.allStatuses.clear();
    this.allStatuses.putAll(newEntries);
  }

  private void resetToProfile(final DebugScreenProfile profile) {
    this.profile = profile;
    this.resetStatuses(DebugScreenEntries.PROFILES.get(profile));
  }

  public void loadProfile(final DebugScreenProfile profile) {
    this.resetToProfile(profile);
    this.rebuildCurrentList();
  }

  public DebugScreenEntryStatus getStatus(final ResourceLocation location) {
    return this.allStatuses.getOrDefault(location, DebugScreenEntryStatus.NEVER);
  }

  public boolean isCurrentlyEnabled(final ResourceLocation location) {
    return this.currentlyEnabled.contains(location);
  }

  public void setStatus(final ResourceLocation location, final DebugScreenEntryStatus status) {
    this.profile = null;
    this.allStatuses.put(location, status);
    this.rebuildCurrentList();
    this.save();
  }

  public boolean toggleStatus(final ResourceLocation location) {
    switch (this.allStatuses.get(location)) {
      case ALWAYS_ON:
        this.setStatus(location, DebugScreenEntryStatus.NEVER);
        return false;
      case IN_OVERLAY:
        if (this.isOverlayVisible) {
          this.setStatus(location, DebugScreenEntryStatus.NEVER);
          return false;
        }

        this.setStatus(location, DebugScreenEntryStatus.ALWAYS_ON);
        return true;
      case NEVER:
        if (this.isOverlayVisible) {
          this.setStatus(location, DebugScreenEntryStatus.IN_OVERLAY);
        } else {
          this.setStatus(location, DebugScreenEntryStatus.ALWAYS_ON);
        }

        return true;
      case null:
      default:
        this.setStatus(location, DebugScreenEntryStatus.ALWAYS_ON);
        return true;
    }
  }

  public Collection<ResourceLocation> getCurrentlyEnabled() {
    return List.copyOf(this.currentlyEnabled);
  }

  public boolean isOverlayVisible() {
    return this.isOverlayVisible;
  }

  public void setOverlayVisible(final boolean visible) {
    if (this.isOverlayVisible != visible) {
      this.isOverlayVisible = visible;
      this.rebuildCurrentList();
    }

  }

  public void toggleDebugOverlay() {
    this.setOverlayVisible(!this.isOverlayVisible);
  }

  public void rebuildCurrentList() {
    this.currentlyEnabled.clear();
    Minecraft minecraft = Minecraft.getInstance();
    boolean isReducedDebugInfo = minecraft.showOnlyReducedInfo();
    this.allStatuses.forEach((key, value) -> {
      if (value == DebugScreenEntryStatus.ALWAYS_ON
          || this.isOverlayVisible && value == DebugScreenEntryStatus.IN_OVERLAY) {
        DebugScreenEntry debug = DebugScreenEntries.getEntry(key);
        if (debug != null && debug.isAllowed(isReducedDebugInfo)) {
          this.currentlyEnabled.add(key);
        }
      }

    });
    this.currentlyEnabled.sort(Comparator.naturalOrder());
    ++this.currentlyEnabledVersion;
  }

  public long getCurrentlyEnabledVersion() {
    return this.currentlyEnabledVersion;
  }

  public boolean isUsingProfile(final DebugScreenProfile profile) {
    return this.profile == profile;
  }

  public void save() {
    SerializedOptions serializedOptions = new SerializedOptions(Optional.ofNullable(this.profile), this.profile == null
                                                                                                   ? Optional.of(
        this.allStatuses)
                                                                                                   : Optional.empty());

    // try {
    //   FileUtils.writeStringToFile(this.debugProfileFile,
    //                               ((JsonElement) this.codec.encodeStart(JsonOps.INSTANCE, serializedOptions)
    //                                   .getOrThrow()).toString(), StandardCharsets.UTF_8);
    // } catch (IOException e) {
    //   LOGGER.error("Failed to save debug profile file {}", this.debugProfileFile, e);
    // }

  }

  private record SerializedOptions(Optional<DebugScreenProfile> profile,
                                   Optional<Map<ResourceLocation, DebugScreenEntryStatus>> custom) {
    public static final Codec<SerializedOptions> CODEC;
    private static final Codec<Map<ResourceLocation, DebugScreenEntryStatus>> CUSTOM_ENTRIES_CODEC;

    static {
      CUSTOM_ENTRIES_CODEC = Codec.unboundedMap(ResourceLocation.CODEC, DebugScreenEntryStatus.CODEC);
      CODEC = RecordCodecBuilder.create(
          (i) -> i.group(DebugScreenProfile.CODEC.optionalFieldOf("profile").forGetter(SerializedOptions::profile),
                         CUSTOM_ENTRIES_CODEC.optionalFieldOf("custom").forGetter(SerializedOptions::custom))
              .apply(i, SerializedOptions::new));
    }
  }
}
