package club.iananderson.f3backport.mixin;

import club.iananderson.f3backport.client.gui.components.debug.NewDebugScreenOverlay;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.DebugScreenOverlay;
import net.minecraft.client.gui.components.debugchart.BandwidthDebugChart;
import net.minecraft.util.debugchart.LocalSampleLogger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DebugScreenOverlay.class)
public abstract class MixinDebugScreen {
  @Shadow @Final private Minecraft minecraft;

  @Shadow public abstract void toggleFpsCharts();

  @Shadow private boolean renderProfilerChart;
  @Shadow private boolean renderFpsCharts;
  @Shadow private boolean renderNetworkCharts;
  @Shadow @Final private LocalSampleLogger frameTimeLogger;
  @Shadow @Final private BandwidthDebugChart bandwidthChart;
  @Shadow @Final private LocalSampleLogger pingLogger;
  @Shadow @Final private LocalSampleLogger bandwidthLogger;
  @Unique
  NewDebugScreenOverlay f3_Backport$newDebugScreenOverlay;

  @Inject(method = "<init>", at = @At(value = "TAIL"))
  private void init(Minecraft minecraft, CallbackInfo ci) {
    f3_Backport$newDebugScreenOverlay = new NewDebugScreenOverlay(minecraft);
  }

  @Inject(method = "render", at = @At(value = "HEAD"), cancellable = true)
  private void render(GuiGraphics guiGraphics, CallbackInfo ci) {
    f3_Backport$newDebugScreenOverlay.debugEntries.setOverlayVisible(minecraft.gui.getDebugOverlay().showDebugScreen());
    f3_Backport$newDebugScreenOverlay.render(guiGraphics, this.renderProfilerChart, this.renderFpsCharts,
                                             this.renderNetworkCharts, this.frameTimeLogger, this.bandwidthLogger,
                                             this.pingLogger);
    ci.cancel();
  }
}