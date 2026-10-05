package club.iananderson.f3backport.client.gui.screens;

import club.iananderson.f3backport.Common;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.neoforged.neoforge.common.ModConfigSpec.ConfigValue;
import org.jspecify.annotations.NonNull;

public class MainOptionsScreen extends F3BackportScreen {
  private static final Component SCREEN_TITLE = Common.translatedText("menu.f3backport.main.title");
  private static Map<ConfigValue<Boolean>, Boolean> configBooleans = new HashMap<>();

  public MainOptionsScreen(Screen parentScreen) {
    super(parentScreen, SCREEN_TITLE);
    loadConfig();
    this.buttonWidth = 170;
  }

  public static MainOptionsScreen getInstance(Screen parentScreen) {
    return new MainOptionsScreen(parentScreen);
  }

  public void loadConfig() {
    configBooleans
  }

  public void saveConfig() {
  }

  @Override
  public void onDone() {
    saveConfig();
    super.onDone();
  }

  @Override
  public void onClose() {
    super.onClose();
  }

  @Override
  public void render(@NonNull GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
    super.render(graphics, mouseX, mouseY, partialTicks);

    // drawHeading(graphics, MINIMAP_SETTINGS, minimapRow);
  }

  public void minimapOptionsButtons() {
    // row += 2;
    // minimapRow = row;
    //
    // enableMinimapIntegrationButton = CycleButton.onOffBuilder(enableMinimapIntegration)
    //     .withTooltip(t -> Common.newTooltip("menu.seasonhud.main.minimapIntegration.tooltip"))
    //     .create(leftButtonX, (buttonStartY + (row * offsetY)), buttonWidth, buttonHeight,
    //             Common.translatedText("menu.seasonhud.main.enableMinimapIntegration.button"),
    //             (b, val) -> enableMinimapIntegration = val);
    //
    // showMinimapHiddenButton = CycleButton.onOffBuilder(showMinimapHidden)
    //     .withTooltip(t -> Common.newTooltip("menu.seasonhud.main.showMinimapHidden.tooltip"))
    //     .create(rightButtonX, (buttonStartY + (row * offsetY)), buttonWidth, buttonHeight,
    //             Common.translatedText("menu.seasonhud.main.showMinimapHidden.button"),
    //             (b, val) -> showMinimapHidden = val);
    //
    // widgets.addAll(Arrays.asList(enableMinimapIntegrationButton, showMinimapHiddenButton));
  }

  @Override
  public void init() {
    super.init();

    // int enableModWidth = font.width(Common.translatedText("menu.seasonhud.main.enableMod.button").append(": OFF")) + 8;
    //
    // CycleButton<Boolean> enableModButton = CycleButton.onOffBuilder(enableMod)
    //     .withTooltip(t -> Common.newTooltip("menu.seasonhud.main.enableMod.tooltip"))
    //     .create(this.width - enableModWidth - TITLE_PADDING / 2, TITLE_PADDING / 2, enableModWidth, buttonHeight,
    //             Common.translatedText("menu.seasonhud.main.enableMod.button"), (b, val) -> enableMod = val);
    // widgets.add(enableModButton);
    //
    // row = -1;
    // seasonHudOptionsButtons();
    // minimapOptionsButtons();
    //
    // widgets.forEach(this::addRenderableWidget);
  }
}