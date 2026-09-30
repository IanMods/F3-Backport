package club.iananderson.f3backport.util.device;

public record DeviceLimits(int maxAnisotropy, int minUniformOffsetAlignment, int maxTextureSize,
                           long maxMemoryAllocationSize, int maxMultiDrawDirectInterleavedDrawCount,
                           int maxColorAttachments, int maxDrawIndirectDrawCount) {
  public int maxTextureSizeForFormat(final GpuFormat format) {
    return Integer.highestOneBit(Math.min(this.maxTextureSize, (int) Math.sqrt(
        (double) this.maxMemoryAllocationSize / (double) format.blockSize())));
  }
}
