package club.iananderson.f3backport.mixin;

import net.minecraft.client.gui.components.DebugScreenOverlay;
import net.minecraft.util.debugchart.LocalSampleLogger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(DebugScreenOverlay.class)
public interface DebugAccessor {
  @Accessor("frameTimeLogger")
  LocalSampleLogger getFrameTimeLogger();
}