package club.iananderson.f3backport.client.gui.screens;

import club.iananderson.f3backport.Common;
import club.iananderson.f3backport.client.gui.components.buttons.BoolButton;
import club.iananderson.f3backport.client.gui.components.buttons.BoolCheckButton;
import club.iananderson.f3backport.client.gui.components.buttons.MenuButton;
import club.iananderson.f3backport.client.gui.components.buttons.MenuButton.MenuButtons;
import club.iananderson.f3backport.client.gui.components.debug.NewDebugScreenOverlay;
import club.iananderson.f3backport.client.gui.components.sliders.HudScaleSlider;
import club.iananderson.f3backport.config.DefaultValues.Client;
import club.iananderson.f3backport.config.F3BackportClient;
import java.awt.Color;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.layouts.HeaderAndFooterLayout;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.ModConfigSpec.ConfigValue;
import org.jspecify.annotations.NonNull;

public class MainOptionsScreen extends F3BackportScreen {
  private static final Component SCREEN_TITLE = Common.translatedText("menu.f3backport.config.title");
  private static final Component PRIORITY_SETTINGS = Common.translatedText("menu.f3backport.config.priority.name");
  private static final Component POSITION_SETTINGS = Common.translatedText("menu.f3backport.config.position.name");
  private static final Component MEMORY_SETTINGS = Common.translatedText("menu.f3backport.config.memory.name");
  private static final Component SYSTEM_SPECS_SETTINGS = Common.translatedText(
      "menu.f3backport.config.systemSpecs.name");
  private static final Component PERF_IMPACTORS_SETTINGS = Common.translatedText(
      "menu.f3backport.config.perfImpactors.name");
  private static final Component LIGHT_SETTINGS = Common.translatedText("menu.f3backport.config.light.name");
  private static final Component HEIGHT_MAP_SETTINGS = Common.translatedText("menu.f3backport.config.heightMap.name");
  private static final Component SPAWN_COUNT_SETTINGS = Common.translatedText(
      "menu.f3backport.config.spawnCounts.name");
  private static final Component MISC_SETTINGS = Common.translatedText("menu.f3backport.config.misc.name");
  private final HeaderAndFooterLayout layout = new HeaderAndFooterLayout(this);
  private final Rectangle positionOptionsRect = new Rectangle();
  private final float scale = 1F;
  private final int sectionWidth;
  private int positionRow;
  private int memoryRow;
  private int systemSpecsRow;
  private int perfImpactorsRow;
  private int micsRow;
  private int heightMapRow;
  private int spawnCountsRow;
  private int miscRow;
  private boolean preview = false;
  private HudScaleSlider debugGuiScaleSlider;
  private MenuButton resetButton;

  public MainOptionsScreen(Screen parentScreen) {
    super(parentScreen, SCREEN_TITLE);
    loadConfig();
    this.buttonWidth = 100;
    this.sectionWidth = (2 * buttonWidth) + BUTTON_PADDING;
  }

  public static MainOptionsScreen getInstance(Screen parentScreen) {
    return new MainOptionsScreen(parentScreen);
  }

  @Override
  public void loadConfig() {
    F3BackportClient.booleanConfigs.forEach(
        booleanConfig -> defaultBooleans.put(booleanConfig, booleanConfig.getDefault()));
    F3BackportClient.booleanConfigs.forEach(booleanConfig -> configBooleans.put(booleanConfig, booleanConfig.get()));

    F3BackportClient.integerConfigs.forEach(
        integerConfig -> defaultIntegers.put(integerConfig, integerConfig.getDefault()));
    F3BackportClient.integerConfigs.forEach(integerConfig -> configIntegers.put(integerConfig, integerConfig.get()));
  }

  @Override
  public void saveConfig() {
    configBooleans.forEach(ConfigValue::set);

    configIntegers.replace(F3BackportClient.debugGuiScale, debugGuiScaleSlider.getValueInt());
    configIntegers.forEach(ConfigValue::set);

    F3BackportClient.CLIENT_SPEC.save();
  }

  @Override
  public void resetConfig() {
    configBooleans.clear();
    configBooleans.putAll(defaultBooleans);
    configIntegers.clear();
    configIntegers.putAll(defaultIntegers);
    this.rebuildWidgets();
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

  public void renderLeft(GuiGraphics graphics, int width, int height) {
    graphics.fill((int) positionOptionsRect.getMinX() - 2, (int) positionOptionsRect.getMinY() - 2,
                  (int) positionOptionsRect.getMaxX() + 2, (int) positionOptionsRect.getMaxY() + 2,
                  new Color(0, 0, 0, 150).getRGB());
    graphics.fill((int) positionOptionsRect.getMinX() - 1, (int) positionOptionsRect.getMinY() - 1,
                  (int) positionOptionsRect.getMaxX() + 1, (int) positionOptionsRect.getMaxY() + 1,
                  new Color(255, 255, 255, 120).getRGB());

  }

  @Override
  public void renderBackground(@NonNull GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
    super.renderBackground(graphics, mouseX, mouseY, partialTick);

    renderLeft(graphics, width, height);
  }

  @Override
  public void render(@NonNull GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
    super.render(graphics, mouseX, mouseY, partialTicks);

    if (this.minecraft == null) {
      return;
    }

    resetButton.active = !configBooleans.equals(defaultBooleans) || !configIntegers.equals(defaultIntegers);

    if (this.preview) {
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
  }

  public void modOptionsButtons(int startRow, Side side) {
    switch (side) {
      case LEFT -> {
        boolean enableMod = configBooleans.get(F3BackportClient.enableMod);
        BoolButton enableModButton = BoolButton.builder(enableMod)
            .withTooltip(t -> Common.newTooltip("menu.f3backport.config.enableMod.desc")).withScale(scale)
            .create(centerButtonX, (buttonStartY + (startRow * offsetY)), buttonWidth, buttonHeight,
                    Common.translatedText("menu.f3backport.config.enableMod.name"),
                    (b, val) -> configBooleans.replace(F3BackportClient.enableMod, val));
        widgets.add(enableModButton);

        rowLeft = startRow + 1;
      }
      case RIGHT -> {
        int debugGuiScale = configIntegers.get(F3BackportClient.debugGuiScale);
        debugGuiScaleSlider = HudScaleSlider.builder(
                Common.translatedText("menu.f3backport.config.debugGuiScale" + ".name").append(": "))
            .withValueRange(Client.DEFAULT_DEBUG_GUI_SCALE_MIN, Client.DEFAULT_DEBUG_GUI_SCALE_MAX)
            .withTooltip(Common.newTooltip("menu.f3backport.config.debugGuiScale.desc")).withScale(scale)
            .withInitialValue(debugGuiScale)
            .withDefaultValue(Client.DEFAULT_DEBUG_GUI_SCALE)
            .withBounds(centerButtonX, (buttonStartY + (startRow * offsetY)), buttonWidth, buttonHeight).withStepSize(1)
            .build();
        widgets.add(debugGuiScaleSlider);

        rowRight = startRow + 1;
      }
      case null, default -> {
      }
    }
  }

  public void priorityOptionsButtons(int startRow, Side side) {
    switch (side) {
      case LEFT -> {
        boolean enableFps = configBooleans.get(F3BackportClient.enableFps);
        BoolButton enableFpsButton = BoolButton.builder(enableFps)
            .withTooltip(t -> Common.newTooltip("menu.f3backport.config.priority.enableFps.desc")).withScale(scale)
            .create(centerButtonX, (buttonStartY + (startRow * offsetY)), buttonWidth, buttonHeight,
                    Common.translatedText("menu.f3backport.config.priority.enableFps.name"),
                    (b, val) -> configBooleans.replace(F3BackportClient.enableFps, val));
        widgets.add(enableFpsButton);

        rowLeft = startRow + 1;
      }
      case RIGHT -> {
        boolean enableGameVersion = configBooleans.get(F3BackportClient.enableGameVersion);
        BoolButton enableGameVersionButton = BoolButton.builder(enableGameVersion)
            .withTooltip(t -> Common.newTooltip("menu.f3backport.config.priority.enableGameVersion.desc"))
            .withScale(scale)
            .create(centerButtonX, (buttonStartY + (startRow * offsetY)), buttonWidth, buttonHeight,
                    Common.translatedText("menu.f3backport.config.priority.enableGameVersion.name"),
                    (b, val) -> configBooleans.replace(F3BackportClient.enableGameVersion, val));
        widgets.add(enableGameVersionButton);

        rowRight = startRow + 1;
      }
      case null, default -> {
      }
    }
  }

  public void positionOptionsButtons(int startRow, Side side) {
    positionRow = startRow;

    boolean enablePlayerPosition = configBooleans.get(F3BackportClient.enablePlayerPosition);
    BoolButton enablePlayerPositionButton = BoolButton.builder(enablePlayerPosition)
        .withTooltip(t -> Common.newTooltip("menu.f3backport.config.position.enablePlayerPosition.desc"))
        .withScale(scale)
        .create(leftButtonX, (buttonStartY + (startRow * offsetY)), buttonWidth, buttonHeight,
                Common.translatedText("menu.f3backport.config.position.enablePlayerPosition.name"),
                (b, val) -> configBooleans.replace(F3BackportClient.enablePlayerPosition, val));

    boolean enablePlayerSpeed = configBooleans.get(F3BackportClient.enablePlayerSpeed);
    BoolButton enablePlayerSpeedButton = BoolButton.builder(enablePlayerSpeed)
        .withTooltip(t -> Common.newTooltip("menu.f3backport.config.position.enablePlayerSpeed.desc")).withScale(scale)
        .create(rightButtonX, (buttonStartY + (startRow * offsetY)), buttonWidth, buttonHeight,
                Common.translatedText("menu.f3backport.config.position.enablePlayerSpeed.name"),
                (b, val) -> configBooleans.replace(F3BackportClient.enablePlayerSpeed, val));

    startRow += 1;
    boolean enablePlayerSectionPosition = configBooleans.get(F3BackportClient.enablePlayerSectionPosition);
    BoolButton enablePlayerSectionPositionButton = BoolButton.builder(enablePlayerSectionPosition)
        .withTooltip(t -> Common.newTooltip("menu.f3backport.config.position.enablePlayerSectionPosition.desc"))
        .withScale(scale)
        .create(leftButtonX, (buttonStartY + (startRow * offsetY)), buttonWidth, buttonHeight,
                Common.translatedText("menu.f3backport.config.position.enablePlayerSectionPosition.name"),
                (b, val) -> configBooleans.replace(F3BackportClient.enablePlayerSectionPosition, val));
    boolean enableBiome = configBooleans.get(F3BackportClient.enableBiome);
    BoolButton enableBiomeButton = BoolButton.builder(enableBiome)
        .withTooltip(t -> Common.newTooltip("menu.f3backport.config.position.enableBiome.desc")).withScale(scale)
        .create(rightButtonX, (buttonStartY + (startRow * offsetY)), buttonWidth, buttonHeight,
                Common.translatedText("menu.f3backport.config.position.enableBiome.name"),
                (b, val) -> configBooleans.replace(F3BackportClient.enableBiome, val));

    widgets.addAll(Arrays.asList(enablePlayerPositionButton, enablePlayerSpeedButton, enablePlayerSectionPositionButton,
                                 enableBiomeButton));

    positionOptionsRect.setBounds(leftButtonX, buttonStartY + (positionRow * offsetY), sectionWidth,
                                  (int) (((startRow - positionRow + 1) * offsetY) - (BUTTON_PADDING)));

    switch (side) {
      case LEFT -> rowLeft = startRow + 1;
      case RIGHT -> rowRight = startRow + 1;
      case null, default -> {
      }
    }
  }

  public void miscOptionsButtons(int startRow, Side side) {
    micsRow = startRow;

    boolean enableLight = configBooleans.get(F3BackportClient.enableLight);
    BoolButton enableLightButton = BoolButton.builder(enableLight)
        .withTooltip(t -> Common.newTooltip("menu.f3backport.config.light.enableLight.desc")).withScale(scale)
        .create(leftButtonX, (buttonStartY + (startRow * offsetY)), buttonWidth, buttonHeight,
                Common.translatedText("menu.f3backport.config.light.enableLight.name"),
                (b, val) -> configBooleans.replace(F3BackportClient.enableLight, val));
    widgets.add(enableLightButton);

    boolean enableGpuUtilization = configBooleans.get(F3BackportClient.enableGpuUtilization);
    BoolButton enableGpuUtilizationButton = BoolButton.builder(enableGpuUtilization)
        .withTooltip(t -> Common.newTooltip("menu.f3backport.config.misc.enableGpuUtilization.desc")).withScale(scale)
        .create(rightButtonX, (buttonStartY + (startRow * offsetY)), buttonWidth, buttonHeight,
                Common.translatedText("menu.f3backport.config.misc.enableGpuUtilization.name"),
                (b, val) -> configBooleans.replace(F3BackportClient.enableGpuUtilization, val));
    widgets.add(enableGpuUtilizationButton);

    startRow += 1;
    boolean enableDayCount = configBooleans.get(F3BackportClient.enableDayCount);
    BoolButton enableDayCountButton = BoolButton.builder(enableDayCount)
        .withTooltip(t -> Common.newTooltip("menu.f3backport.config.misc.enableDayCount.desc")).withScale(scale)
        .create(leftButtonX, (buttonStartY + (startRow * offsetY)), buttonWidth, buttonHeight,
                Common.translatedText("menu.f3backport.config.misc.enableDayCount.name"),
                (b, val) -> configBooleans.replace(F3BackportClient.enableDayCount, val));
    widgets.add(enableDayCountButton);

    boolean enableEntityRenderStats = configBooleans.get(F3BackportClient.enableEntityRenderStats);
    BoolButton enableEntityRenderStatsButton = BoolButton.builder(enableEntityRenderStats)
        .withTooltip(t -> Common.newTooltip("menu.f3backport.config.misc.enableEntityRenderStats.desc"))
        .withScale(scale)
        .create(rightButtonX, (buttonStartY + (startRow * offsetY)), buttonWidth, buttonHeight,
                Common.translatedText("menu.f3backport.config.misc.enableEntityRenderStats.name"),
                (b, val) -> configBooleans.replace(F3BackportClient.enableEntityRenderStats, val));
    widgets.add(enableEntityRenderStatsButton);

    startRow += 1;
    boolean enableLocalDifficulty = configBooleans.get(F3BackportClient.enableLocalDifficulty);
    BoolButton enableLocalDifficultyButton = BoolButton.builder(enableLocalDifficulty)
        .withTooltip(t -> Common.newTooltip("menu.f3backport.config.misc.enableLocalDifficulty.desc")).withScale(scale)
        .create(leftButtonX, (buttonStartY + (startRow * offsetY)), buttonWidth, buttonHeight,
                Common.translatedText("menu.f3backport.config.misc.enableLocalDifficulty.name"),
                (b, val) -> configBooleans.replace(F3BackportClient.enableLocalDifficulty, val));
    widgets.add(enableLocalDifficultyButton);

    boolean enableSoundMood = configBooleans.get(F3BackportClient.enableSoundMood);
    BoolButton enableSoundMoodButton = BoolButton.builder(enableSoundMood)
        .withTooltip(t -> Common.newTooltip("menu.f3backport.config.misc.enableSoundMood.desc")).withScale(scale)
        .create(rightButtonX, (buttonStartY + (startRow * offsetY)), buttonWidth, buttonHeight,
                Common.translatedText("menu.f3backport.config.misc.enableSoundMood.name"),
                (b, val) -> configBooleans.replace(F3BackportClient.enableSoundMood, val));
    widgets.add(enableSoundMoodButton);

    startRow += 1;
    boolean enableTps = configBooleans.get(F3BackportClient.enableTps);
    BoolButton enableTpsButton = BoolButton.builder(enableTps)
        .withTooltip(t -> Common.newTooltip("menu.f3backport.config.misc.enableTps.desc")).withScale(scale)
        .create(centerButtonX, (buttonStartY + (startRow * offsetY)), buttonWidth, buttonHeight,
                Common.translatedText("menu.f3backport.config.misc.enableTps.name"),
                (b, val) -> configBooleans.replace(F3BackportClient.enableTps, val));
    widgets.add(enableTpsButton);

    switch (side) {
      case LEFT -> rowLeft = startRow + 1;
      case RIGHT -> rowRight = startRow + 1;
      case null, default -> {
      }
    }
  }

  public void memoryOptionsButtons(int startRow, Side side) {
    memoryRow = startRow;

    boolean enableMemory = configBooleans.get(F3BackportClient.enableMemory);
    BoolButton enableMemoryButton = BoolButton.builder(enableMemory)
        .withTooltip(t -> Common.newTooltip("menu.f3backport.config.memory.enableMemory.desc")).withScale(scale)
        .create(leftButtonX, (buttonStartY + (startRow * offsetY)), buttonWidth, buttonHeight,
                Common.translatedText("menu.f3backport.config.memory.enableMemory.name"),
                (b, val) -> configBooleans.replace(F3BackportClient.enableMemory, val));

    boolean enableDetailedMemory = configBooleans.get(F3BackportClient.enableDetailedMemory);
    BoolButton enableDetailedMemoryButton = BoolButton.builder(enableDetailedMemory)
        .withTooltip(t -> Common.newTooltip("menu.f3backport.config.memory.enableDetailedMemory.desc")).withScale(scale)
        .create(rightButtonX, (buttonStartY + (startRow * offsetY)), buttonWidth, buttonHeight,
                Common.translatedText("menu.f3backport.config.memory.enableDetailedMemory.name"),
                (b, val) -> configBooleans.replace(F3BackportClient.enableDetailedMemory, val));

    widgets.addAll(Arrays.asList(enableMemoryButton, enableDetailedMemoryButton));

    switch (side) {
      case LEFT -> rowLeft = startRow + 1;
      case RIGHT -> rowRight = startRow + 1;
      case null, default -> {
      }
    }
  }

  public void systemSpecOptionsButtons(int startRow, Side side) {
    systemSpecsRow = startRow;

    boolean enableSystemSpecs = configBooleans.get(F3BackportClient.enableSystemSpecs);
    BoolButton enableSystemSpecsButton = BoolButton.builder(enableSystemSpecs)
        .withTooltip(t -> Common.newTooltip("menu.f3backport.config.systemSpecs.enableSystemSpecs.desc"))
        .withScale(scale)
        .create(centerButtonX, (buttonStartY + (startRow * offsetY)), buttonWidth, buttonHeight,
                Common.translatedText("menu.f3backport.config.systemSpecs.enableSystemSpecs.name"),
                (b, val) -> configBooleans.replace(F3BackportClient.enableSystemSpecs, val));
    widgets.add(enableSystemSpecsButton);

    switch (side) {
      case LEFT -> rowLeft = startRow + 1;
      case RIGHT -> rowRight = startRow + 1;
      case null, default -> {
      }
    }
  }

  public void perfImpactorsOptionsButtons(int startRow, Side side) {
    perfImpactorsRow = startRow;

    boolean enableSimplePerfImpactors = configBooleans.get(F3BackportClient.enableSimplePerfImpactors);
    BoolButton enableSimplePerfImpactorsButton = BoolButton.builder(enableSimplePerfImpactors)
        .withTooltip(t -> Common.newTooltip("menu.f3backport.config.perfImpactors.enablePerfImpactors.desc"))
        .withScale(scale)
        .create(leftButtonX, (buttonStartY + (startRow * offsetY)), buttonWidth, buttonHeight,
                Common.translatedText("menu.f3backport.config.perfImpactors.enablePerfImpactors.name"),
                (b, val) -> configBooleans.replace(F3BackportClient.enableSimplePerfImpactors, val));
    widgets.add(enableSimplePerfImpactorsButton);

    switch (side) {
      case LEFT -> rowLeft = startRow + 1;
      case RIGHT -> rowRight = startRow + 1;
      case null, default -> {
      }
    }
  }

  @Override
  public void drawHeader() {
    super.drawHeader();
  }

  @Override
  public void drawFooter() {
    super.drawFooter();

    resetButton = MenuButton.builder(MenuButtons.RESET, button -> resetConfig())
        .withPos(BUTTON_PADDING, buttonStartY).withWidth(buttonWidth)
        .build();
    widgets.add(resetButton);

    Component previewMessage = Common.translatedText("menu.f3backport.main.preview.button");
    int previewButtonWidth = font.lineHeight + 2;
    int previewButtonX = width - previewButtonWidth - BUTTON_PADDING - font.width(previewMessage);

    BoolCheckButton previewButton = BoolCheckButton.builder(this.preview)
        .withTooltip(t -> Common.newTooltip("menu.f3backport.main.preview.desc")).withScale(1F)
        .create(previewButtonX, buttonStartY + buttonHeight / 4, previewButtonWidth, previewMessage,
                (b, val) -> this.preview = val);
    widgets.add(previewButton);
  }

  @Override
  public void drawLeft() {
    super.drawLeft();

    modOptionsButtons(rowLeft, Side.LEFT);
    priorityOptionsButtons(rowLeft, Side.LEFT);
    positionOptionsButtons(rowLeft, Side.LEFT);
    miscOptionsButtons(rowLeft, Side.LEFT);
  }

  @Override
  public void drawRight() {
    super.drawRight();

    modOptionsButtons(rowRight, Side.RIGHT);
    priorityOptionsButtons(rowRight, Side.RIGHT);
    memoryOptionsButtons(rowRight, Side.RIGHT);
    systemSpecOptionsButtons(rowRight, Side.RIGHT);
    perfImpactorsOptionsButtons(rowRight, Side.RIGHT);
  }

  @Override
  public void init() {
    super.init();
  }
}