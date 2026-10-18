package club.iananderson.f3backport.client.gui.screens;

import club.iananderson.f3backport.Common;
import club.iananderson.f3backport.client.gui.components.buttons.BoolCheckButton;
import club.iananderson.f3backport.client.gui.components.buttons.MenuButton;
import club.iananderson.f3backport.client.gui.components.buttons.MenuButton.MenuButtons;
import club.iananderson.f3backport.client.gui.components.debug.NewDebugScreenOverlay;
import club.iananderson.f3backport.client.gui.components.debug.entries.DebugGroups;
import club.iananderson.f3backport.client.gui.components.sliders.HudScaleSlider;
import club.iananderson.f3backport.config.ConfigEntries;
import club.iananderson.f3backport.config.DefaultValues.Client;
import club.iananderson.f3backport.config.F3BackportClient;
import club.iananderson.f3backport.util.Rgb;
import java.awt.Color;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.ModConfigSpec.ConfigValue;
import org.jspecify.annotations.NonNull;

public class MainOptionsScreen extends F3BackportScreen {
  private static final Component SCREEN_TITLE = Common.translatedText("menu.f3backport.config.title");
  private static final MutableComponent PRIORITY_SETTINGS = Common.translatedText(
      "menu.f3backport.config.priority.name");
  private static final MutableComponent POSITION_SETTINGS = Common.translatedText(
      "menu.f3backport.config.position.name");
  private static final MutableComponent MEMORY_SETTINGS = Common.translatedText("menu.f3backport.config.memory.name");
  private static final MutableComponent SYSTEM_SPECS_SETTINGS = Common.translatedText(
      "menu.f3backport.config.systemSpecs.name");
  private static final MutableComponent PERF_IMPACTORS_SETTINGS = Common.translatedText(
      "menu.f3backport.config.perfImpactors.name");
  private static final MutableComponent LIGHT_SETTINGS = Common.translatedText("menu.f3backport.config.light.name");
  private static final MutableComponent HEIGHT_MAP_SETTINGS = Common.translatedText(
      "menu.f3backport.config.heightMap.name");
  private static final MutableComponent SPAWN_COUNT_SETTINGS = Common.translatedText(
      "menu.f3backport.config.spawnCounts.name");
  private static final MutableComponent MISC_SETTINGS = Common.translatedText("menu.f3backport.config.misc.name");
  private final Rectangle priorityOptionsRect = new Rectangle();
  private final Rectangle positionOptionsRect = new Rectangle();
  private final Rectangle miscOptionsRect = new Rectangle();
  private final Rectangle memoryOptionsRect = new Rectangle();
  private final Rectangle systemSpecsOptionsRect = new Rectangle();
  private final Rectangle perfImpactorsOptionsRect = new Rectangle();
  private final Rectangle spawnCountsOptionsRect = new Rectangle();
  private boolean preview = false;
  private HudScaleSlider debugGuiScaleSlider;
  private MenuButton resetButton;

  public MainOptionsScreen(Screen parentScreen) {
    super(parentScreen, SCREEN_TITLE);
    loadConfig();
    this.buttonWidth = 100;
    this.buttonHeight = 14;
    this.scale = 1.0F;
    this.sectionWidth = (2 * buttonWidth) + BUTTON_PADDING;
    this.offsetY = this.buttonHeight + BUTTON_PADDING;
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

  public void addGroupColorButton(DebugGroups group, Rectangle rect) {
    float colorScale = 0.75F;
    int stringWidth = font.width(MenuButtons.COLOR.getButtonText());
    int colorButtonWidth = (stringWidth + (BUTTON_PADDING));
    int colorButtonHeight = (int) (font.lineHeight + 1);

    int buttonX = ((int) rect.getMinX() + this.sectionWidth - colorButtonWidth);
    int buttonY = ((int) rect.getMinY() - ((colorButtonHeight) + 1));

    MenuButton colorButton = MenuButton.builder(MenuButtons.COLOR,
                                                button -> ColorsScreen.getInstance(this, group).open())
        .withBounds(buttonX, buttonY, colorButtonWidth, colorButtonHeight).withScale(colorScale)
        .build();

    widgets.add(colorButton);
  }

  public void renderBacking(GuiGraphics graphics, Side side, Rectangle rect, DebugGroups group) {
    int borderColor = new Color(0, 0, 0, 0).getRGB();
    int backgroundColor = -1873784752;
    int titleColor = -1875890128;

    int stringWidth = font.width(group.getTranslatedName());
    int stringX = (int) (rect.getCenterX());
    int stringY = (int) (rect.getMinY() - (double) font.lineHeight);

    int boxStartX = (int) rect.getMinX() - 1;
    int boxStartY = (int) rect.getMinY() - 1;
    int boxEndX = (int) rect.getMaxX() + 1;
    int boxEndY = (int) rect.getMaxY() + 1;

    int colorStartX = side == Side.LEFT
                      ? boxStartX - 1
                      : boxEndX;

    int groupRgbaInt = Rgb.rgbaInt(group.getGroupColor(), F3BackportClient.getAlphaColor());

    int tabStartY = stringY - 2;
    int tabEndY = (int) rect.getMinY() - 1;

    graphics.fill(boxStartX, boxStartY, boxEndX, boxEndY, backgroundColor);
    graphics.fill(boxStartX, tabStartY, boxEndX, tabEndY, titleColor);
    graphics.vLine(colorStartX, tabStartY - 1, boxEndY, groupRgbaInt);

    float tabScale = (float) (0.75);
    int tabStartX = (int) (stringX - ((double) stringWidth / 2 * tabScale) - 4);
    int tabEndX = (int) (stringX + ((double) stringWidth / 2 * tabScale) + 4);

    graphics.fill(boxStartX, boxStartY - 1, tabStartX, boxStartY, borderColor);
    graphics.fill(tabEndX, boxStartY - 1, boxEndX, boxStartY, borderColor);

    graphics.fill(tabStartX, tabStartY, tabEndX, tabEndY, borderColor);

    graphics.pose().pushPose();
    graphics.pose().scale(tabScale, tabScale, tabScale);
    graphics.drawCenteredString(font, group.getTranslatedName(), (int) (stringX / tabScale), (int) (stringY / tabScale),
                                new Color(255, 255, 255).getRGB());
    graphics.pose().popPose();
  }

  public void renderLeft(GuiGraphics graphics) {
    renderBacking(graphics, Side.LEFT, priorityOptionsRect, DebugGroups.PRIORITY);
    renderBacking(graphics, Side.LEFT, positionOptionsRect, DebugGroups.POSITION);
    renderBacking(graphics, Side.LEFT, spawnCountsOptionsRect, DebugGroups.SPAWN_COUNTS);
    renderBacking(graphics, Side.LEFT, miscOptionsRect, DebugGroups.MISC);
  }

  public void renderRight(GuiGraphics graphics) {
    renderBacking(graphics, Side.RIGHT, memoryOptionsRect, DebugGroups.MEMORY);
    renderBacking(graphics, Side.RIGHT, systemSpecsOptionsRect, DebugGroups.SYSTEM_SPECS);
    renderBacking(graphics, Side.RIGHT, perfImpactorsOptionsRect, DebugGroups.PERFORMANCE_IMPACTORS);
  }

  @Override
  public void renderBackground(@NonNull GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
    super.renderBackground(graphics, mouseX, mouseY, partialTick);

    renderLeft(graphics);
    renderRight(graphics);
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

  public void modOptionsButtons(Side side, int startRow, int section) {
    switch (side) {
      case LEFT -> {
        addBoolRow(side, section, F3BackportClient.enableMod, null);
        sectionLeft += 1;
      }
      case RIGHT -> {
        ConfigEntries configEntry = ConfigEntries.getByConfigValue(F3BackportClient.debugGuiScale);
        int debugGuiScale = configIntegers.get(F3BackportClient.debugGuiScale);

        debugGuiScaleSlider = HudScaleSlider.builder(configEntry.getNameTranslated())
            .withValueRange(Client.DEFAULT_DEBUG_GUI_SCALE_MIN, Client.DEFAULT_DEBUG_GUI_SCALE_MAX)
            .withTooltip(configEntry.getToolTip()).withScale(scale)
            .withInitialValue(debugGuiScale)
            .withDefaultValue((Integer) configEntry.getDefaultValue())
            .withBounds(centerButtonX, (buttonStartY + (startRow * offsetY) + (section * SECTION_PADDING)), buttonWidth,
                        buttonHeight).withStepSize(1)
            .build();
        widgets.add(debugGuiScaleSlider);

        rowRight = startRow + 1;
        sectionRight += 1;
      }
      case null, default -> {
      }
    }
  }

  public void priorityOptionsButtons(Side side, int startRow, int section) {
    if (side == Side.LEFT) {
      priorityOptionsRect.setLocation(leftButtonX, buttonStartY + (startRow * offsetY) + (section * SECTION_PADDING));
    }

    switch (side) {
      case LEFT -> {
        addBoolRow(side, section, F3BackportClient.enableFps, null);
        sectionLeft += 1;
      }
      case RIGHT -> {
        addBoolRow(side, section, F3BackportClient.enableGameVersion, null);
        sectionRight += 1;
      }
      case null, default -> {
      }
    }

    if (side == Side.LEFT) {
      int endRow = this.rowLeft;
      priorityOptionsRect.setSize(sectionWidth, ((endRow - startRow) * offsetY) - BUTTON_PADDING);
      priorityOptionsRect.setSize((2 * sectionWidth) + (2 * BUTTON_PADDING),
                                  ((endRow - startRow) * offsetY) - BUTTON_PADDING);
    }

  }

  public void positionOptionsButtons(Side side, int startRow, int section) {
    positionOptionsRect.setLocation(leftButtonX, buttonStartY + (startRow * offsetY) + (section * SECTION_PADDING));

    addGroupColorButton(DebugGroups.POSITION, positionOptionsRect);

    addBoolRow(side, section, F3BackportClient.enablePlayerPosition, F3BackportClient.enablePlayerPosition);
    addBoolRow(side, section, F3BackportClient.enablePlayerSectionPosition,
               F3BackportClient.enablePlayerSectionPosition);

    int endRow;

    switch (side) {
      case LEFT -> {
        endRow = this.rowLeft;
        sectionLeft += 1;
      }
      case RIGHT -> {
        endRow = this.rowRight;
        sectionRight += 1;
      }
      case null, default -> endRow = 0;
    }

    positionOptionsRect.setSize(sectionWidth, ((endRow - startRow) * offsetY) - BUTTON_PADDING);
  }

  public void miscOptionsButtons(Side side, int startRow, int section) {
    miscOptionsRect.setLocation(leftButtonX, buttonStartY + (startRow * offsetY) + (section * SECTION_PADDING));

    addGroupColorButton(DebugGroups.MISC, miscOptionsRect);

    addBoolRow(side, section, F3BackportClient.enableLight, F3BackportClient.enableGpuUtilization);
    addBoolRow(side, section, F3BackportClient.enableDayCount, F3BackportClient.enableEntityRenderStats);
    addBoolRow(side, section, F3BackportClient.enableLocalDifficulty, F3BackportClient.enableSoundMood);
    addBoolRow(side, section, F3BackportClient.enableTps, null);

    int endRow;

    switch (side) {
      case LEFT -> {
        endRow = this.rowLeft;
        sectionLeft += 1;
      }
      case RIGHT -> {
        endRow = this.rowRight;
        sectionRight += 1;
      }
      case null, default -> endRow = 0;
    }

    miscOptionsRect.setSize(sectionWidth, ((endRow - startRow) * offsetY) - BUTTON_PADDING);
  }

  public void memoryOptionsButtons(Side side, int startRow, int section) {
    memoryOptionsRect.setLocation(leftButtonX, buttonStartY + (startRow * offsetY) + (section * SECTION_PADDING));

    addGroupColorButton(DebugGroups.MEMORY, memoryOptionsRect);

    addBoolRow(side, section, F3BackportClient.enableMemory, F3BackportClient.enableDetailedMemory);

    int endRow;

    switch (side) {
      case LEFT -> {
        endRow = this.rowLeft;
        sectionLeft += 1;
      }
      case RIGHT -> {
        endRow = this.rowRight;
        sectionRight += 1;
      }
      case null, default -> endRow = 0;
    }

    memoryOptionsRect.setSize(sectionWidth, ((endRow - startRow) * offsetY) - BUTTON_PADDING);
  }

  public void systemSpecOptionsButtons(Side side, int startRow, int section) {
    systemSpecsOptionsRect.setLocation(leftButtonX, buttonStartY + (startRow * offsetY) + (section * SECTION_PADDING));

    addGroupColorButton(DebugGroups.SYSTEM_SPECS, systemSpecsOptionsRect);

    addBoolRow(side, section, F3BackportClient.enableSystemSpecs, null);

    int endRow;

    switch (side) {
      case LEFT -> {
        endRow = this.rowLeft;
        sectionLeft += 1;
      }
      case RIGHT -> {
        endRow = this.rowRight;
        sectionRight += 1;
      }
      case null, default -> endRow = 0;
    }

    systemSpecsOptionsRect.setSize(sectionWidth, ((endRow - startRow) * offsetY) - BUTTON_PADDING);
  }

  public void perfImpactorsOptionsButtons(Side side, int startRow, int section) {
    perfImpactorsOptionsRect.setLocation(leftButtonX,
                                         buttonStartY + (startRow * offsetY) + (section * SECTION_PADDING));

    addGroupColorButton(DebugGroups.PERFORMANCE_IMPACTORS, perfImpactorsOptionsRect);

    addBoolRow(side, section, F3BackportClient.enableSimplePerfImpactors, null);

    int endRow;

    switch (side) {
      case LEFT -> {
        endRow = this.rowLeft;
        sectionLeft += 1;
      }
      case RIGHT -> {
        endRow = this.rowRight;
        sectionRight += 1;
      }
      case null, default -> endRow = 0;
    }

    perfImpactorsOptionsRect.setSize(sectionWidth, ((endRow - startRow) * offsetY) - (BUTTON_PADDING));
  }

  public void spawnCountsOptionsButtons(Side side, int startRow, int section) {
    spawnCountsOptionsRect.setLocation(leftButtonX, buttonStartY + (startRow * offsetY) + (section * SECTION_PADDING));

    addGroupColorButton(DebugGroups.SPAWN_COUNTS, spawnCountsOptionsRect);

    addBoolRow(side, section, F3BackportClient.enableSpawnCounts, null);

    int endRow;

    switch (side) {
      case LEFT -> {
        endRow = this.rowLeft;
        sectionLeft += 1;
      }
      case RIGHT -> {
        endRow = this.rowRight;
        sectionRight += 1;
      }
      case null, default -> endRow = 0;
    }

    spawnCountsOptionsRect.setSize(sectionWidth, ((endRow - startRow) * offsetY) - (BUTTON_PADDING));
  }

  @Override
  public void drawHeader() {
    super.drawHeader();
  }

  @Override
  public void drawFooter() {
    super.drawFooter();

    int resetButtonWidth = font.width(MenuButtons.RESET.getButtonText()) + TITLE_PADDING;
    int previewButtonWidth = font.lineHeight + 2;
    Component previewMessage = Common.translatedText("menu.f3backport.main.preview.button");
    int previewButtonX = width - previewButtonWidth - MARGIN_RIGHT - font.width(previewMessage);
    int resetButtonX = previewButtonX - BUTTON_PADDING - resetButtonWidth;

    resetButton = MenuButton.builder(MenuButtons.RESET, button -> resetConfig()).withWidth(resetButtonWidth)
        .withBounds(resetButtonX, buttonStartY + 4, resetButtonWidth, buttonHeight)
        .build();
    widgets.add(resetButton);

    BoolCheckButton previewButton = BoolCheckButton.builder(this.preview)
        .withTooltip(t -> Common.newTooltip("menu.f3backport.main.preview.desc")).withScale(1.0F)
        .create(previewButtonX, (buttonStartY + buttonHeight / 4) + 2, previewButtonWidth, previewMessage,
                (b, val) -> this.preview = val);
    widgets.add(previewButton);
  }

  @Override
  public void drawLeft() {
    super.drawLeft();

    modOptionsButtons(Side.LEFT, rowLeft, sectionLeft);
    priorityOptionsButtons(Side.LEFT, rowLeft, sectionLeft);
    positionOptionsButtons(Side.LEFT, rowLeft, sectionLeft);
    spawnCountsOptionsButtons(Side.LEFT, rowLeft, sectionLeft);
    miscOptionsButtons(Side.LEFT, rowLeft, sectionLeft);
  }

  @Override
  public void drawRight() {
    super.drawRight();

    modOptionsButtons(Side.RIGHT, rowRight, sectionRight);
    priorityOptionsButtons(Side.RIGHT, rowRight, sectionRight);
    memoryOptionsButtons(Side.RIGHT, rowRight, sectionRight);
    systemSpecOptionsButtons(Side.RIGHT, rowRight, sectionRight);
    perfImpactorsOptionsButtons(Side.RIGHT, rowRight, sectionRight);
  }
}