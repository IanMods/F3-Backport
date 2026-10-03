package club.iananderson.f3backport.util;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFixer;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;
import java.util.Objects;
import net.minecraft.SharedConstants;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.util.datafix.fixes.References;

public enum DataFixTypes {
  DEBUG_PROFILE(References.reference("debug_profile"));

  private final DSL.TypeReference type;

  DataFixTypes(final DSL.TypeReference type) {
    this.type = type;
  }

  private static int currentVersion() {
    return SharedConstants.getCurrentVersion().getDataVersion().getVersion();
  }

  public <A> Codec<A> wrapCodec(final Codec<A> codec, final DataFixer dataFixer, final int defaultVersion) {
    return new Codec<>() {
      public <T> DataResult<T> encode(final A input, final DynamicOps<T> ops, final T prefix) {
        return codec.encode(input, ops, prefix)
            .flatMap((data) -> ops.mergeToMap(data, ops.createString("DataVersion"), ops.createInt(currentVersion())));
      }

      public <T> DataResult<Pair<A, T>> decode(final DynamicOps<T> ops, final T input) {
        Objects.requireNonNull(ops);
        int fromVersion = ops.get(input, "DataVersion").flatMap(ops::getNumberValue).map(Number::intValue).result()
            .orElse(defaultVersion);
        Dynamic<T> dataWithoutVersion = new Dynamic<>(ops, ops.remove(input, "DataVersion"));
        Dynamic<T> fixedData = updateToCurrentVersion(dataFixer, dataWithoutVersion, fromVersion);
        return codec.decode(fixedData);
      }
    };
  }

  public <T> Dynamic<T> update(final DataFixer fixerUpper, final Dynamic<T> input, final int fromVersion,
      final int toVersion) {
    return fixerUpper.update(this.type, input, fromVersion, toVersion);
  }

  public <T> Dynamic<T> updateToCurrentVersion(final DataFixer fixerUpper, final Dynamic<T> input,
      final int dataVersion) {
    return this.update(fixerUpper, input, dataVersion, currentVersion());
  }

  public CompoundTag update(final DataFixer fixer, final CompoundTag tag, final int fromVersion, final int toVersion) {
    return (CompoundTag) this.update(fixer, new Dynamic<>(NbtOps.INSTANCE, tag), fromVersion, toVersion).getValue();
  }

  public CompoundTag updateToCurrentVersion(final DataFixer fixer, final CompoundTag tag, final int fromVersion) {
    return this.update(fixer, tag, fromVersion, currentVersion());
  }
}
