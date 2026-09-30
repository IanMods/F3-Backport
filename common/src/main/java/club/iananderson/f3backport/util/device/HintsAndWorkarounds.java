package club.iananderson.f3backport.util.device;

public record HintsAndWorkarounds(boolean writeToBufferIsSlow, boolean anisotropyHasKnownIssues,
                                  boolean isExplicitDepthRequired, boolean multiDrawIndirectHasKnownIssues) {}

