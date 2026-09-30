package club.iananderson.f3backport.util.device;

public record DeviceFeatures(boolean wireframeFillMode, boolean shaderDrawParameters,
                             boolean multiDrawDirectInterleaved, boolean multiDrawDirectSeparate,
                             boolean multiDrawIndirect, boolean drawIndirect, boolean nonZeroFirstInstance,
                             boolean persistentMapping) {}
