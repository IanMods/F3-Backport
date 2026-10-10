package club.iananderson.f3backport.client.gui.screens;

import club.iananderson.f3backport.Common;
import club.iananderson.f3backport.client.gui.components.buttons.BoolButton;
import club.iananderson.f3backport.client.gui.components.buttons.MenuButton;
import club.iananderson.f3backport.client.gui.components.buttons.MenuButton.MenuButtons;
import club.iananderson.f3backport.config.F3BackportClient;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.neoforged.neoforge.common.ModConfigSpec.ConfigValue;
import org.jspecify.annotations.NonNull;

public class F3BackportScreen extends Screen {
  public static final int MENU_PADDING = 30;
  public static final int TITLE_PADDING = 10;
  public static final int BUTTON_PADDING = 6;
  private static final int MARGIN_RIGHT = 16;
  private static final int MARGIN_LEFT = 16;
  public static MenuButton doneButton;
  public static MenuButton cancelButton;
  public final List<AbstractWidget> widgets = new ArrayList<>();
  public final Screen parentScreen;
  public final Map<ConfigValue<Boolean>, Boolean> configBooleans = new HashMap<>();
  public final Map<ConfigValue<Boolean>, Boolean> defaultBooleans = new HashMap<>();
  public final Map<ConfigValue<Integer>, Integer> configIntegers = new HashMap<>();
  public final Map<ConfigValue<Integer>, Integer> defaultIntegers = new HashMap<>();
  public int buttonWidth = 150;
  public int buttonHeight = 20;
  public int leftButtonX;
  public int rightButtonX;
  public int centerButtonX;
  public int row;
  public int rowLeft;
  public int rowRight;
  public int buttonStartY = MENU_PADDING;
  public int offsetY = this.buttonHeight + BUTTON_PADDING;
  protected boolean hasPendingChanges;

  public F3BackportScreen(Screen parentScreen, Component title) {
    super(title);
    this.parentScreen = parentScreen;
  }

  public void open() {
    Minecraft.getInstance().setScreen(this);
  }

  @Override
  public boolean isPauseScreen() {
    return true;
  }

  @Override
  public boolean shouldCloseOnEsc() {
    return !this.hasPendingChanges;
  }

  public void loadConfig() {
  }

  public void saveConfig() {
  }

  public void resetConfig() {
  }

  @Override
  public void onClose() {
    Minecraft.getInstance().setScreen(this.parentScreen);
  }

  public void onDone() {
    Minecraft.getInstance().setScreen(this.parentScreen);
  }

  protected void rebuildWidgets() {
    this.clearWidgets();
    this.clearFocus();
    this.init();
  }

  @SuppressWarnings("checkstyle:AbbreviationAsWordInName")
  public void rebuildUI() {
    this.rebuildWidgets();
  }

  public void addBoolRow(Side side, Map<ConfigValue<Boolean>, Boolean> configMap, ConfigValue<Boolean> configValueLeft,
      @Nullable ConfigValue<Boolean> configValueRight, float scale) {
    int row = side == Side.LEFT
              ? this.rowLeft
              : this.rowRight;

    Boolean boolLeft = configMap.get(configValueLeft);
    BoolButton leftButton = BoolButton.builder(boolLeft)
        .withTooltip(t -> Common.newTooltip("menu.f3backport.config.priority.enableFps.desc")).withScale(scale)
        .create(centerButtonX, (buttonStartY + (row * offsetY)), buttonWidth, buttonHeight,
                Common.translatedText("menu.f3backport.config.priority.enableFps.name"),
                (b, val) -> configBooleans.replace(F3BackportClient.enableFps, val));
    widgets.add(leftButton);

    row += 1;

    boolean rightBool = configMap.get(configValueRight);
    BoolButton rightButton = BoolButton.builder(rightBool)
        .withTooltip(t -> Common.newTooltip("menu.f3backport.config.priority.enableGameVersion.desc")).withScale(scale)
        .create(centerButtonX, (buttonStartY + (row * offsetY)), buttonWidth, buttonHeight,
                Common.translatedText("menu.f3backport.config.priority.enableGameVersion.name"),
                (b, val) -> configBooleans.replace(configValueRight, val));
    widgets.add(rightButton);

    row += 1;

    if (side == Side.LEFT) {
      this.rowLeft = row;
    } else {
      this.rowRight = row;
    }
  }

  public void drawLeft() {
    rightButtonX = (width / 2) - BUTTON_PADDING - buttonWidth;
    leftButtonX = rightButtonX - buttonWidth - BUTTON_PADDING;
    centerButtonX = (leftButtonX + rightButtonX) / 2;

    buttonStartY = MENU_PADDING;

    rowLeft = 0;
  }

  public void drawRight() {
    leftButtonX = (width / 2) + BUTTON_PADDING;
    rightButtonX = leftButtonX + buttonWidth + BUTTON_PADDING;
    centerButtonX = (leftButtonX + rightButtonX) / 2;

    buttonStartY = MENU_PADDING;

    rowRight = 0;
  }

  public void drawHeader() {
    int centerX = this.width / 2;
    int halfTitleWidth = font.width(this.getTitle()) / 2;

    rightButtonX = centerX + halfTitleWidth + TITLE_PADDING;
    leftButtonX = centerX - halfTitleWidth - TITLE_PADDING - buttonWidth;
    buttonStartY = TITLE_PADDING + (font.lineHeight / 2) - (buttonHeight / 2);
  }

  public void drawFooter() {
    leftButtonX = (this.width / 2) - (buttonWidth + BUTTON_PADDING);
    rightButtonX = (this.width / 2) + BUTTON_PADDING;
    centerButtonX = (leftButtonX + rightButtonX) / 2;

    buttonStartY = this.height - MenuButton.DEFAULT_HEIGHT - BUTTON_PADDING;

    cancelButton = MenuButton.builder(MenuButtons.CANCEL, press -> this.onClose())
        .withPos(leftButtonX, buttonStartY).withWidth(buttonWidth)
        .build();

    doneButton = MenuButton.builder(MenuButtons.DONE, press -> this.onDone())
        .withPos(rightButtonX, buttonStartY).withWidth(buttonWidth)
        .build();

    this.widgets.addAll(Arrays.asList(cancelButton, doneButton));
  }

  @Override
  public void render(@NonNull GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
    super.render(graphics, mouseX, mouseY, partialTicks);
    graphics.drawCenteredString(font, this.getTitle(), this.width / 2, TITLE_PADDING, 16777215);
  }

  @Override
  public void init() {
    super.init();
    this.widgets.clear();

    this.drawHeader();
    this.drawLeft();
    this.drawRight();
    this.drawFooter();

    this.widgets.forEach(this::addRenderableWidget);
  }

  public enum Side {
    LEFT,
    RIGHT
  }
}
