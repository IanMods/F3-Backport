package club.iananderson.f3backport.client.gui.screens;

import club.iananderson.f3backport.Common;
import club.iananderson.f3backport.client.gui.components.buttons.BoolButton;
import club.iananderson.f3backport.client.gui.components.buttons.sliders.HudScaleSlider;
import club.iananderson.f3backport.client.gui.components.debug.NewDebugScreenOverlay;
import club.iananderson.f3backport.config.DefaultValues.Client;
import club.iananderson.f3backport.config.F3BackportClient;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.ModConfigSpec.ConfigValue;
import org.jspecify.annotations.NonNull;

public class MainOptionsScreen extends F3BackportScreen {
  private static final Component SCREEN_TITLE = Common.translatedText("menu.f3backport.main.title");
  private static final Component PRIORITY_SETTINGS = Common.translatedText("menu.f3backport.main.priority.options");
  private static final Component POSITION_SETTINGS = Common.translatedText("menu.f3backport.main.position.options");
  private static final Component MEMORY_SETTINGS = Common.translatedText("menu.f3backport.main.memory.options");
  private static final Component SYSTEM_SPECS_SETTINGS = Common.translatedText(
      "menu.f3backport.main.systemSpecs.options");
  private static final Component PERF_IMPACTORS_SETTINGS = Common.translatedText(
      "menu.f3backport.main.perfImpactors.options");
  private static final Component LIGHT_SETTINGS = Common.translatedText("menu.f3backport.main.light.options");
  private static final Component HEIGHT_MAP_SETTINGS = Common.translatedText("menu.f3backport.main.heightMap.options");
  private static final Component SPAWN_COUNT_SETTINGS = Common.translatedText(
      "menu.f3backport.main.spawnCount.options");
  private static final Component MISC_SETTINGS = Common.translatedText("menu.f3backport.main.misc.options");
  private static final Map<ConfigValue<Boolean>, Boolean> configBooleans = new HashMap<>();
  private static final Map<ConfigValue<Integer>, Integer> configIntegers = new HashMap<>();
  private int priorityRow;
  private int positionRow;
  private int memoryRow;
  private int systemSpecsRow;
  private int perfImpactorsRow;
  private int lightRow;
  private int heightMapRow;
  private int spawnCountsRow;
  private int miscRow;
  private HudScaleSlider debugGuiScaleSlider;

  public MainOptionsScreen(Screen parentScreen) {
    super(parentScreen, SCREEN_TITLE);
    loadConfig();
    this.buttonWidth = 100;
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
    drawHeading(graphics, POSITION_SETTINGS, positionRow);
    drawHeading(graphics, MEMORY_SETTINGS, memoryRow);
    drawHeading(graphics, SYSTEM_SPECS_SETTINGS, systemSpecsRow);
    drawHeading(graphics, PERF_IMPACTORS_SETTINGS, perfImpactorsRow);
    drawHeading(graphics, LIGHT_SETTINGS, lightRow);

    if (this.minecraft == null) {
      return;
    }

    NewDebugScreenOverlay debugScreenOverlay = new NewDebugScreenOverlay(this.minecraft);
    Map<ConfigValue<?>, ResourceLocation> configValueMap = F3BackportClient.getConfigValueMap();
    List<ConfigValue<Boolean>> enabledConfigs = new ArrayList<>();

    configBooleans.forEach((booleanConfigValue, bool) -> {
      if (bool) {
        enabledConfigs.add(booleanConfigValue);
      }
    });

    int debugGuiScale = debugGuiScaleSlider.getValueInt();

    List<ResourceLocation> enabledResources = new ArrayList<>();

    enabledConfigs.forEach(configValue -> enabledResources.add(configValueMap.get(configValue)));

    debugScreenOverlay.renderMenu(graphics, enabledResources, debugGuiScale);
  }

  public void priorityOptionsButtons() {
    row += 1;
    priorityRow = row;

    boolean enableFps = configBooleans.get(F3BackportClient.enableFps);
    boolean enableGameVersion = configBooleans.get(F3BackportClient.enableGameVersion);

    BoolButton enableFpsButton = BoolButton.builder(enableFps)
        .withTooltip(t -> Common.newTooltip("menu.f3backport.main.priority.enableFps.tooltip"))
        .create(leftButtonX, (buttonStartY + (row * offsetY)), buttonWidth, buttonHeight,
                Common.translatedText("menu.f3backport.main.priority.enableFps.button"),
                (b, val) -> configBooleans.replace(F3BackportClient.enableFps, val));

    BoolButton enableGameVersionButton = BoolButton.builder(enableGameVersion)
        .withTooltip(t -> Common.newTooltip("menu.f3backport.main.priority.enableGameVersion.tooltip"))
        .create(rightButtonX, (buttonStartY + (row * offsetY)), buttonWidth, buttonHeight,
                Common.translatedText("menu.f3backport.main.priority.enableGameVersion.button"),
                (b, val) -> configBooleans.replace(F3BackportClient.enableGameVersion, val));

    widgets.addAll(Arrays.asList(enableFpsButton, enableGameVersionButton));
  }

  public void positionOptionsButtons() {
    row += 2;
    positionRow = row;

    boolean enablePlayerPosition = configBooleans.get(F3BackportClient.enablePlayerPosition);
    boolean enablePlayerSpeed = configBooleans.get(F3BackportClient.enablePlayerSpeed);
    boolean enablePlayerSectionPosition = configBooleans.get(F3BackportClient.enablePlayerSectionPosition);
    boolean enableBiome = configBooleans.get(F3BackportClient.enableBiome);

    BoolButton enablePlayerPositionButton = BoolButton.builder(enablePlayerPosition)
        .withTooltip(t -> Common.newTooltip("menu.f3backport.main.position.enablePlayerPosition.tooltip"))
        .create(leftButtonX, (buttonStartY + (row * offsetY)), buttonWidth, buttonHeight,
                Common.translatedText("menu.f3backport.main.position.enablePlayerPosition.button"),
                (b, val) -> configBooleans.replace(F3BackportClient.enablePlayerPosition, val));

    BoolButton enablePlayerSpeedButton = BoolButton.builder(enablePlayerSpeed)
        .withTooltip(t -> Common.newTooltip("menu.f3backport.main.position.enablePlayerSpeed.tooltip"))
        .create(rightButtonX, (buttonStartY + (row * offsetY)), buttonWidth, buttonHeight,
                Common.translatedText("menu.f3backport.main.position.enablePlayerSpeed.button"),
                (b, val) -> configBooleans.replace(F3BackportClient.enablePlayerSpeed, val));

    row += 1;

    BoolButton enablePlayerSectionPositionButton = BoolButton.builder(enablePlayerSectionPosition)
        .withTooltip(t -> Common.newTooltip("menu.f3backport.main.position.enablePlayerSectionPosition.tooltip"))
        .create(leftButtonX, (buttonStartY + (row * offsetY)), buttonWidth, buttonHeight,
                Common.translatedText("menu.f3backport.main.position.enablePlayerSectionPosition.button"),
                (b, val) -> configBooleans.replace(F3BackportClient.enablePlayerSectionPosition, val));

    BoolButton enableBiomeButton = BoolButton.builder(enableBiome)
        .withTooltip(t -> Common.newTooltip("menu.f3backport.main.position.enableBiome.tooltip"))
        .create(rightButtonX, (buttonStartY + (row * offsetY)), buttonWidth, buttonHeight,
                Common.translatedText("menu.f3backport.main.position.enableBiome.button"),
                (b, val) -> configBooleans.replace(F3BackportClient.enableBiome, val));

    widgets.addAll(Arrays.asList(enablePlayerPositionButton, enablePlayerSpeedButton, enablePlayerSectionPositionButton,
                                 enableBiomeButton));
  }

  public void memoryOptionsButtons() {
    row += 2;
    memoryRow = row;

    boolean enableMemory = configBooleans.get(F3BackportClient.enableMemory);
    boolean enableDetailedMemory =  configBooleans.get(F3BackportClient.enableDetailedMemory);

    BoolButton enableMemoryButton = BoolButton.builder(enableMemory)
        .withTooltip(t -> Common.newTooltip("menu.f3backport.main.memory.enableMemory.tooltip"))
        .create(leftButtonX, (buttonStartY + (row * offsetY)), buttonWidth, buttonHeight,
                Common.translatedText("menu.f3backport.main.memory.enableMemory.button"),
                (b, val) -> configBooleans.replace(F3BackportClient.enableMemory, val));

    BoolButton enableDetailedMemoryButton = BoolButton.builder(enableDetailedMemory)
        .withTooltip(t -> Common.newTooltip("menu.f3backport.main.memory.enableDetailedMemory.tooltip"))
        .create(rightButtonX, (buttonStartY + (row * offsetY)), buttonWidth, buttonHeight,
                Common.translatedText("menu.f3backport.main.memory.enableDetailedMemory.button"),
                (b, val) -> configBooleans.replace(F3BackportClient.enableDetailedMemory, val));

    widgets.addAll(Arrays.asList(enableMemoryButton, enableDetailedMemoryButton));
  }

  public void systemSpecOptionsButtons() {
    row += 2;
    systemSpecsRow = row;

    boolean enableSystemSpecs = configBooleans.get(F3BackportClient.enableSystemSpecs);

    BoolButton enableSystemSpecsButton = BoolButton.builder(enableSystemSpecs)
        .withTooltip(t -> Common.newTooltip("menu.f3backport.main.systemSpecs.enableSystemSpecs.tooltip"))
        .create(leftButtonX, (buttonStartY + (row * offsetY)), buttonWidth, buttonHeight,
                Common.translatedText("menu.f3backport.main.systemSpecs.enableSystemSpecs.button"),
                (b, val) -> configBooleans.replace(F3BackportClient.enableSystemSpecs, val));
    widgets.add(enableSystemSpecsButton);
  }

  public void perfImpactorsOptionsButtons() {
    row += 2;
    perfImpactorsRow = row;

    boolean enableSimplePerfImpactors = configBooleans.get(F3BackportClient.enableSimplePerfImpactors);

    BoolButton enableSimplePerfImpactorsButton = BoolButton.builder(enableSimplePerfImpactors)
        .withTooltip(t -> Common.newTooltip("menu.f3backport.main.perfImpactors.enableSimplePerfImpactors.tooltip"))
        .create(leftButtonX, (buttonStartY + (row * offsetY)), buttonWidth, buttonHeight,
                Common.translatedText("menu.f3backport.main.perfImpactors.enableSimplePerfImpactors.button"),
                (b, val) -> configBooleans.replace(F3BackportClient.enableSimplePerfImpactors, val));
    widgets.add(enableSimplePerfImpactorsButton);
  }

  public void lightOptionsButtons() {
    row += 2;
    lightRow = row;

    boolean enableLight = configBooleans.get(F3BackportClient.enableLight);

    BoolButton enableLightButton = BoolButton.builder(enableLight)
        .withTooltip(t -> Common.newTooltip("menu.f3backport.main.light.enableLight.tooltip"))
        .create(leftButtonX, (buttonStartY + (row * offsetY)), buttonWidth, buttonHeight,
                Common.translatedText("menu.f3backport.main.light.enableLight.button"),
                (b, val) -> configBooleans.replace(F3BackportClient.enableLight, val));
    widgets.add(enableLightButton);
  }

  @Override
  public void init() {
    super.init();

    int enableModWidth = font.width(Common.translatedText("menu.f3backport.main.enableMod.button").append(": OFF")) + 8;
    int debugGuiScaleWidth =
        font.width(Common.translatedText("menu.f3backport.main.debugGuiScale.slider").append("-1")) + 8;

    boolean enableMod = configBooleans.get(F3BackportClient.enableMod);
    int debugGuiScale = configIntegers.get(F3BackportClient.debugGuiScale);

    row = -1;

    BoolButton enableModButton = BoolButton.builder(enableMod)
        .withTooltip(t -> Common.newTooltip("menu.f3backport.main.enableMod.tooltip"))
        .create(leftButtonX, (buttonStartY + (row * offsetY)), buttonWidth, buttonHeight,
                Common.translatedText("menu.f3backport.main.enableMod.button"),
                (b, val) -> configBooleans.replace(F3BackportClient.enableMod, val));
    widgets.add(enableModButton);

    debugGuiScaleSlider = HudScaleSlider.builder(Common.translatedText("menu.f3backport.main.debugGuiScale.slider"))
        .withValueRange(Client.DEFAULT_DEBUG_GUI_SCALE_MIN, Client.DEFAULT_DEBUG_GUI_SCALE_MAX)
        .withTooltip(Common.newTooltip("menu.f3backport.main.debugGuiScale.tooltip"))
        .withInitialValue(debugGuiScale)
        .withDefaultValue(Client.DEFAULT_DEBUG_GUI_SCALE)
        .withBounds(rightButtonX, (buttonStartY + (row * offsetY)),
                    buttonWidth, buttonHeight).withStepSize(1)
        .build();
    widgets.add(debugGuiScaleSlider);

    row += 1;
    priorityOptionsButtons();
    positionOptionsButtons();
    memoryOptionsButtons();
    systemSpecOptionsButtons();
    perfImpactorsOptionsButtons();
    lightOptionsButtons();

    widgets.forEach(this::addRenderableWidget);
  }
}