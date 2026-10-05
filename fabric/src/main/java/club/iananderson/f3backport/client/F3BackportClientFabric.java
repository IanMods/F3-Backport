package club.iananderson.f3backport.client;

import club.iananderson.f3backport.event.ClientEvents;
import net.fabricmc.api.ClientModInitializer;

public class F3BackportClientFabric implements ClientModInitializer {

  @Override
  public void onInitializeClient() {
    ClientEvents.register();
  }
}
