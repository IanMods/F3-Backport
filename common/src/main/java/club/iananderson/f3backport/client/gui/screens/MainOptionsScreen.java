package club.iananderson.f3backport.client.gui.screens;

import club.iananderson.f3backport.Common;
import club.iananderson.f3backport.client.gui.components.buttons.sliders.HudScaleSlider;
import club.iananderson.f3backport.config.DefaultValues.Client;
import club.iananderson.f3backport.config.F3BackportClient;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.neoforged.neoforge.common.ModConfigSpec.ConfigValue;
import org.jspecify.annotations.NonNull;

public class MainOptionsScreen extends F3BackportScreen {
  private static final Component SCREEN_TITLE = Common.translatedText("menu.f3backport.main.title");
  private static final Component PRIORITY_SETTINGS = Common.translatedText("menu.f3backport.main.priority.options");
  private static final Map<ConfigValue<Boolean>, Boolean> configBooleans = new HashMap<>();
  private static final Map<ConfigValue<Integer>, Integer> configIntegers = new HashMap<>();
  private int priorityRow;
  private HudScaleSlider debugGuiScaleSlider;

  public MainOptionsScreen(Screen parentScreen) {
    super(parentScreen, SCREEN_TITLE);
    loadConfig();
    this.buttonWidth = 170;
  }

  public static MainOptionsScreen getInstance(Screen parentScreen) {
    return new MainOptionsScreen(parentScreen);
  }

  public void loadConfig() {
    F3BackportClient.booleanConfigs.forEach(booleanConfig -> configBooleans.put(booleanConfig, booleanConfig.get()));
    F3BackportClient.integerConfigs.forEach(integerConfig -> configIntegers.put(integerConfig, integerConfig.get()));
  }

  public void saveConfig() {
    configBooleans.forEach(ConfigValue::set);

    configIntegers.replace(F3BackportClient.debugGuiScale, debugGuiScaleSlider.getValueInt());
    configIntegers.forEach(ConfigValue::set);
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

    drawHeading(graphics, PRIORITY_SETTINGS, priorityRow);
  }

  public void priorityOptionsButtons() {
    row += 2;
    priorityRow = row;

    boolean enableFps = configBooleans.get(F3BackportClient.enableFps);
    boolean enableGameVersion = configBooleans.get(F3BackportClient.enableGameVersion);

    CycleButton<Boolean> enableFpsButton = CycleButton.onOffBuilder(enableFps)
        .withTooltip(t -> Common.newTooltip("menu.f3backport.main.enableFps.tooltip"))
        .create(leftButtonX, (buttonStartY + (row * offsetY)), buttonWidth, buttonHeight,
                Common.translatedText("menu.f3backport.main.enableFps.button"),
                (b, val) -> configBooleans.replace(F3BackportClient.enableFps, val));

    CycleButton<Boolean> enableGameVersionButton = CycleButton.onOffBuilder(enableGameVersion)
        .withTooltip(t -> Common.newTooltip("menu.f3backport.main.enableGameVersion.tooltip"))
        .create(rightButtonX, (buttonStartY + (row * offsetY)), buttonWidth, buttonHeight,
                Common.translatedText("menu.f3backport.main.enableGameVersion.button"),
                (b, val) -> configBooleans.replace(F3BackportClient.enableGameVersion, val));

    widgets.addAll(Arrays.asList(enableGameVersionButton, enableFpsButton));
  }

  @Override
  public void init() {
    super.init();

    int enableModWidth = font.width(Common.translatedText("menu.f3backport.main.enableMod.button").append(": OFF")) + 8;
    int debugGuiScaleWidth =
        font.width(Common.translatedText("menu.f3backport.main.debugGuiScale.slider").append("-1")) + 8;

    boolean enableMod = configBooleans.get(F3BackportClient.enableMod);
    int debugGuiScale = configIntegers.get(F3BackportClient.debugGuiScale);

    CycleButton<Boolean> enableModButton = CycleButton.onOffBuilder(enableMod)
        .withTooltip(t -> Common.newTooltip("menu.f3backport.main.enableMod.tooltip"))
        .create(this.width - enableModWidth - TITLE_PADDING / 2, TITLE_PADDING / 2, enableModWidth, buttonHeight,
                Common.translatedText("menu.f3backport.main.enableMod.button"),
                (b, val) -> configBooleans.replace(F3BackportClient.enableMod, val));
    widgets.add(enableModButton);

    debugGuiScaleSlider = HudScaleSlider.builder(Common.translatedText("menu.f3backport.main.debugGuiScale.slider"))
        .withValueRange(Client.DEFAULT_DEBUG_GUI_SCALE_MIN, Client.DEFAULT_DEBUG_GUI_SCALE_MAX)
        .withTooltip(Common.newTooltip("menu.f3backport.main.debugGuiScale.tooltip"))
        .withInitialValue(debugGuiScale)
        .withDefaultValue(Client.DEFAULT_DEBUG_GUI_SCALE)
        .withBounds(this.width - debugGuiScaleWidth - TITLE_PADDING / 2, TITLE_PADDING + buttonHeight,
                    debugGuiScaleWidth, buttonHeight).withStepSize(1)
        .build();
    widgets.add(debugGuiScaleSlider);

    row = -1;
    priorityOptionsButtons();

    widgets.forEach(this::addRenderableWidget);
  }
}