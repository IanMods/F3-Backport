package club.iananderson.f3backport.client.gui.components.debug;

import club.iananderson.f3backport.client.gui.components.debug.entries.DebugGroup;
import club.iananderson.f3backport.client.gui.components.debug.entries.DebugGroupContents;
import club.iananderson.f3backport.client.gui.components.debug.entries.DebugGroups;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Consumer;

public class DebugScreenDisplayer {
  public final DebugGroupContents leftPriority = new DebugGroupContents(DebugGroups.PRIORITY);
  public final DebugGroupContents rightPriority = new DebugGroupContents(DebugGroups.PRIORITY);
  public final Map<DebugGroup, DebugGroupContents> groups = new LinkedHashMap<>();

  public void addPriorityLine(final String line) {
    if (leftPriority.lines().size() > rightPriority.lines().size()) {
      rightPriority.lines().add(line);
    } else {
      leftPriority.lines().add(line);
    }

  }

  public void addToGroup(final DebugGroup group, final Collection<String> lines) {
    groups.computeIfAbsent(group, (k) -> new DebugGroupContents(group)).lines().addAll(lines);
  }

  public void addToGroup(final DebugGroup group, final String lines) {
    groups.computeIfAbsent(group, (k) -> new DebugGroupContents(group)).lines().add(lines);
  }

  public void addToGroup(final DebugGroup group, final DebugCustomRenderer customRenderer) {
    groups.computeIfAbsent(group, (k) -> new DebugGroupContents(group)).addCustomRenderer(customRenderer);
  }

  public void addFactToGroup(final DebugGroup group, final String name, final Consumer<DebugFact> builder) {
    DebugFact fact = new DebugFact();
    builder.accept(fact);
    groups.computeIfAbsent(group, (k) -> new DebugGroupContents(group)).addFact(name, fact.result());
  }
}

