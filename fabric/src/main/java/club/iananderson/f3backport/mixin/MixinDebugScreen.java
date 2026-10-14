package club.iananderson.f3backport.mixin;

import club.iananderson.f3backport.client.gui.components.debug.NewDebugScreenOverlay;
import club.iananderson.f3backport.config.F3BackportClient;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.DebugScreenOverlay;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DebugScreenOverlay.class)
public abstract class MixinDebugScreen {
  @SuppressWarnings("checkstyle:MemberName")
  @Unique
  NewDebugScreenOverlay f3_Backport$newDebugScreenOverlay;
  @Shadow
  @Final
  private Minecraft minecraft;

  @Inject(method = "<init>", at = @At(value = "TAIL"))
  private void init(Minecraft minecraft, CallbackInfo ci) {
    if (F3BackportClient.getEnableMod()) {
      f3_Backport$newDebugScreenOverlay = new NewDebugScreenOverlay(minecraft);
    }
  }

  @Inject(method = "render", at = @At(value = "HEAD"), cancellable = true)
  private void render(GuiGraphics graphics, CallbackInfo ci) {
    if (F3BackportClient.getEnableMod()) {
      DebugScreenOverlay debug = minecraft.getDebugOverlay();

      f3_Backport$newDebugScreenOverlay.debugEntries.setOverlayVisible(debug.showDebugScreen());

      f3_Backport$newDebugScreenOverlay.render(graphics);
      ci.cancel();
    }
  }
}