package club.iananderson.f3backport.client.gui.components.debug;

import club.iananderson.f3backport.client.gui.components.debug.DebugColumn.Side;
import club.iananderson.f3backport.client.keybinds.DebugKeyBinds;
import com.mojang.blaze3d.platform.Window;
import com.mojang.datafixers.DataFixUtils;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.debugchart.BandwidthDebugChart;
import net.minecraft.client.gui.components.debugchart.FpsDebugChart;
import net.minecraft.client.gui.components.debugchart.PingDebugChart;
import net.minecraft.client.gui.components.debugchart.TpsDebugChart;
import net.minecraft.client.server.IntegratedServer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.debugchart.LocalSampleLogger;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.status.ChunkStatus;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public class NewDebugScreenOverlay {
  public final DebugScreenEntryList debugEntries;
  private final Font font;
  private final DebugColumn leftColumn;
  private final DebugColumn rightColumn;
  private final Minecraft minecraft;
  private @Nullable ChunkPos lastPos;
  private @Nullable LevelChunk clientChunk;
  private @Nullable CompletableFuture<LevelChunk> serverChunk;
  private long lastDebugEntriesVersion;

  public NewDebugScreenOverlay(final Minecraft minecraft) {
    this.minecraft = minecraft;
    this.leftColumn = new DebugColumn(Side.LEFT);
    this.rightColumn = new DebugColumn(Side.RIGHT);
    this.font = minecraft.font;
    this.debugEntries = new DebugScreenEntryList(minecraft);
  }

  private static String formatChart(final KeyMapping keyDebugModifier, final KeyMapping keybind, final String name,
      final boolean status) {
    return formatKeybind(keyDebugModifier, keybind) + " " + name + " " + (status ? "visible" : "hidden");
  }

  private static String formatKeybind(final KeyMapping keyDebugModifier, final KeyMapping keybind) {
    String debugModifier =
        keyDebugModifier.isUnbound() ? "" : keyDebugModifier.getTranslatedKeyMessage().getString() + "+";
    return "[" + debugModifier + keybind.getTranslatedKeyMessage().getString() + "]";
  }

  public void clearChunkCache() {
    this.serverChunk = null;
    this.clientChunk = null;
  }

  public void render(final @NonNull GuiGraphics graphics, boolean renderProfilerChart, boolean renderFpsCharts,
      boolean renderNetworkCharts, LocalSampleLogger frameTimeLogger, LocalSampleLogger bandwidthLogger,
      LocalSampleLogger pingLogger) {
    if (this.minecraft.isGameLoadFinished() && (!this.minecraft.options.hideGui || this.minecraft.screen != null)) {
      Collection<ResourceLocation> visibleEntries = this.debugEntries.getCurrentlyEnabled();
      if (visibleEntries.isEmpty()) {
        this.clearColumnCache();
      }
      else {
        if (this.lastDebugEntriesVersion != this.debugEntries.getCurrentlyEnabledVersion()) {
          this.lastDebugEntriesVersion = this.debugEntries.getCurrentlyEnabledVersion();
          this.clearColumnCache();
        }

        ProfilerFiller profiler = this.minecraft.getProfiler();
        profiler.push("debug");
        ChunkPos chunkPos;
        if (this.minecraft.getCameraEntity() != null && this.minecraft.level != null) {
          BlockPos feetPos = this.minecraft.getCameraEntity().blockPosition();
          chunkPos = new ChunkPos(feetPos);
        }
        else {
          chunkPos = null;
        }

        if (!Objects.equals(this.lastPos, chunkPos)) {
          this.lastPos = chunkPos;
          this.clearChunkCache();
        }

        DebugScreenDisplayer displayer = new DebugScreenDisplayer();
        Map<DebugGroup, DebugGroupContents> groups = displayer.groups;
        DebugGroupContents leftPriority = displayer.leftPriority;
        DebugGroupContents rightPriority = displayer.rightPriority;
        Level level = this.getLevel();

        for (ResourceLocation id : visibleEntries) {
          DebugScreenEntry entry = DebugScreenEntries.getEntry(id);
          if (entry != null) {
            entry.display(displayer, level, this.getClientChunk(), this.getServerChunk());
          }
        }

        DebugGroupContents miscContents = groups.get(DebugGroups.MISC);
        if (miscContents != null) {
          groups.remove(DebugGroups.MISC);
          groups.put(DebugGroups.MISC, miscContents);
        }

        if (this.debugEntries.isOverlayVisible()) {
          boolean hasServer = this.minecraft.getSingleplayerServer() != null;
          DebugKeyBinds debugKeyBinds = new DebugKeyBinds();
          KeyMapping keyDebugModifier = debugKeyBinds.keyDebugOverlay;
          DebugGroup debugHelp = DebugGroups.HELP;
          String charts = formatChart(keyDebugModifier, debugKeyBinds.keyDebugProfilingChart, "Profiler",
                                      renderProfilerChart);
          charts = "Debug charts: " + charts + "; " + formatChart(keyDebugModifier, debugKeyBinds.keyDebugFpsCharts,
                                                                  hasServer ? "fps + tps" : "fps", renderFpsCharts)
              + ";";
          String networkChartText = formatChart(keyDebugModifier, debugKeyBinds.keyDebugNetworkCharts,
                                                !this.minecraft.isLocalServer() ? "Bandwidth + Ping" : "Ping",
                                                renderNetworkCharts);
          String helpText = "For help: press F3 + Q";
          displayer.addToGroup(debugHelp, List.of(charts, networkChartText, helpText));
        }

        Window window = this.minecraft.getWindow();
        int standardGuiScale = (int) window.getGuiScale();
        int newScale = standardGuiScale;
        if (newScale == -1) {
          newScale = standardGuiScale;
        }
        else if (newScale == 0) {
          int maxGuiScale = this.minecraft.getWindow().calculateScale(0, this.minecraft.isEnforceUnicode());
          newScale = maxGuiScale / 2;
        }
        else {
          newScale = window.calculateScale(newScale, this.minecraft.isEnforceUnicode());
        }

        graphics.pose().pushPose();
        int scaledScreenHeight;
        int scaledScreenWidth;
        if (newScale < standardGuiScale && newScale > 0) {
          graphics.pose()
              .scale((float) newScale / (float) standardGuiScale, (float) newScale / (float) standardGuiScale, 1); //
          scaledScreenWidth = window.getWidth() / newScale;
          scaledScreenHeight = window.getHeight() / newScale;
        }
        else {
          scaledScreenWidth = graphics.guiWidth();
          scaledScreenHeight = graphics.guiHeight();
        }

        this.leftColumn.newFrame();
        this.rightColumn.newFrame();
        if (!leftPriority.lines().isEmpty()) {
          this.leftColumn.add(leftPriority, graphics, this.font, scaledScreenWidth);
        }

        if (!rightPriority.lines().isEmpty()) {
          this.rightColumn.add(rightPriority, graphics, this.font, scaledScreenWidth);
        }

        groups.values().removeIf(
            (contentsx) -> contentsx.lines().isEmpty() && contentsx.facts().isEmpty() && contentsx.customRenderers()
                .isEmpty());

        for (DebugGroup group : this.leftColumn.getPreviousGroups()) {
          if (!this.leftColumn.isFull(scaledScreenHeight)) {
            DebugGroupContents contents = groups.remove(group);
            if (contents != null) {
              this.leftColumn.add(contents, graphics, this.font, scaledScreenWidth);
            }
          }
        }

        for (DebugGroup group : this.rightColumn.getPreviousGroups()) {
          if (!this.rightColumn.isFull(scaledScreenHeight)) {
            DebugGroupContents contents = groups.remove(group);
            if (contents != null) {
              this.rightColumn.add(contents, graphics, this.font, scaledScreenWidth);
            }
          }
        }

        Iterator<DebugGroupContents> iterator = groups.values().iterator();

        while (iterator.hasNext()) {
          DebugGroupContents contents = iterator.next();
          Optional<DebugColumn.Side> preferredSide = contents.group().preferredColumn();
          if (preferredSide.isPresent()) {
            if (preferredSide.get() == Side.LEFT && !this.leftColumn.isFull(scaledScreenHeight)) {
              this.leftColumn.add(contents, graphics, this.font, scaledScreenWidth);
              iterator.remove();
            }
            else if (preferredSide.get() == Side.RIGHT && !this.rightColumn.isFull(scaledScreenHeight)) {
              this.rightColumn.add(contents, graphics, this.font, scaledScreenWidth);
              iterator.remove();
            }
          }
        }

        for (DebugGroupContents contents : groups.values()) {
          if (this.leftColumn.getHeightSoFar() < this.rightColumn.getHeightSoFar() && !this.leftColumn.isFull(
              scaledScreenHeight)) {
            this.leftColumn.add(contents, graphics, this.font, scaledScreenWidth);
          }
          else if (!this.rightColumn.isFull(scaledScreenHeight)) {
            this.rightColumn.add(contents, graphics, this.font, scaledScreenWidth);
          }
        }

        if (renderFpsCharts) {
          LocalSampleLogger tickTimeLogger = this.minecraft.gui.getDebugOverlay().getTickTimeLogger();

          FpsDebugChart fpsChart = new FpsDebugChart(this.font, frameTimeLogger);

          int maxWidth = scaledScreenWidth / 2;
          fpsChart.drawChart(graphics, 0, fpsChart.getWidth(maxWidth));
          if (tickTimeLogger.size() > 0) {
            TpsDebugChart tpsChart = new TpsDebugChart(this.font, tickTimeLogger, () -> minecraft.level == null ? 0.0F
                                                                                                                : minecraft.level.tickRateManager()
                                                                                            .millisecondsPerTick());

            int width = tpsChart.getWidth(maxWidth);
            tpsChart.drawChart(graphics, scaledScreenWidth - width, width);
          }

        }

        if (renderNetworkCharts) {
          PingDebugChart pingChart = new PingDebugChart(this.font, pingLogger);

          int maxWidth = scaledScreenWidth / 2;
          if (!this.minecraft.isLocalServer()) {
            BandwidthDebugChart bandwidthChart = new BandwidthDebugChart(this.font, bandwidthLogger);
            bandwidthChart.drawChart(graphics, 0, bandwidthChart.getWidth(maxWidth));
          }

          int width = pingChart.getWidth(maxWidth);
          pingChart.drawChart(graphics, scaledScreenWidth - width, width);
        }

        graphics.pose().popPose();
        profiler.pop();
      }
    }
    else {
      this.clearColumnCache();
    }
  }

  public void clearColumnCache() {
    this.leftColumn.clear();
    this.rightColumn.clear();
  }

  private @Nullable ServerLevel getServerLevel() {
    if (this.minecraft.level == null) {
      return null;
    }
    else {
      IntegratedServer server = this.minecraft.getSingleplayerServer();
      return server != null ? server.getLevel(this.minecraft.level.dimension()) : null;
    }
  }

  private @Nullable Level getLevel() {
    return this.minecraft.level == null ? null : DataFixUtils.orElse(
        Optional.ofNullable(this.minecraft.getSingleplayerServer())
            .flatMap((s) -> Optional.ofNullable(s.getLevel(this.minecraft.level.dimension()))), this.minecraft.level);
  }

  private @Nullable LevelChunk getServerChunk() {
    if (this.minecraft.level != null && this.lastPos != null) {
      if (this.serverChunk == null) {
        ServerLevel level = this.getServerLevel();
        if (level == null) {
          return null;
        }

        this.serverChunk = level.getChunkSource()
            .getChunkFuture(this.lastPos.x, this.lastPos.z, ChunkStatus.FULL, false)
            .thenApply((chunkResult) -> (LevelChunk) chunkResult.orElse(null));
      }

      return this.serverChunk.getNow(null);
    }
    else {
      return null;
    }
  }

  private @Nullable LevelChunk getClientChunk() {
    if (this.minecraft.level != null && this.lastPos != null) {
      if (this.clientChunk == null) {
        this.clientChunk = this.minecraft.level.getChunk(this.lastPos.x, this.lastPos.z);
      }

      return this.clientChunk;
    }
    else {
      return null;
    }
  }
}
