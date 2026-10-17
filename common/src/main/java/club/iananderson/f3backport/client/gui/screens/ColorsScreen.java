package club.iananderson.f3backport.client.gui.screens;

import club.iananderson.f3backport.Common;
import club.iananderson.f3backport.client.gui.components.boxes.ColorEditBox;
import club.iananderson.f3backport.client.gui.components.debug.entries.DebugGroups;
import club.iananderson.f3backport.client.gui.components.sliders.DefaultColorButton;
import club.iananderson.f3backport.client.gui.components.sliders.rgb.BlueSlider;
import club.iananderson.f3backport.client.gui.components.sliders.rgb.GreenSlider;
import club.iananderson.f3backport.client.gui.components.sliders.rgb.RedSlider;
import club.iananderson.f3backport.client.gui.components.sliders.rgb.RgbSlider;
import club.iananderson.f3backport.config.F3BackportClient;
import club.iananderson.f3backport.util.Rgb;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class ColorsScreen extends F3BackportScreen {
  private static final Component SCREEN_TITLE = Common.translatedText("menu.f3backport.color.title");
  private static final int BOX_WIDTH = 80;
  private final DebugGroups group;
  private ColorEditBox colorBox;
  private int defaultColor;
  private int initialColor;

  public ColorsScreen(Screen parentScreen, DebugGroups group) {
    super(parentScreen, SCREEN_TITLE);
    this.buttonWidth = 170;
    this.group = group;
    this.loadConfig(group);
  }

  public static ColorsScreen getInstance(Screen parentScreen, DebugGroups group) {
    return new ColorsScreen(parentScreen, group);
  }

  private static EnumSet<DebugGroups> groupListSet() {
    return DebugGroups.DEBUG_GROUPS_ENUM_LIST.clone();
  }

  public void loadConfig(DebugGroups group) {
    this.defaultColor = group.getDefaultColor();
    this.initialColor = group.getGroupColor();
  }

  @Override
  public void saveConfig() {
    int newColorInt = Integer.parseInt(colorBox.getValue());

    if (newColorInt != this.initialColor) {
      this.group.setGroupColor(newColorInt);
    }

    F3BackportClient.CLIENT_SPEC.save();
  }

  @Override
  public void onDone() {
    this.saveConfig();
    super.onDone();
  }

  @Override
  public void onClose() {
    super.onClose();
  }

  private List<AbstractWidget> groupWidget(int x, int y, DebugGroups debugGroups) {
    ColorEditBox colorBox = new ColorEditBox(this.font, x, y, BOX_WIDTH, buttonHeight, debugGroups);
    y += colorBox.getHeight() + BUTTON_PADDING;
    x -= 1;
    y += buttonHeight + RgbSlider.SLIDER_PADDING;

    int initialR = Rgb.red(this.initialColor);
    RedSlider redSlider = new RedSlider(x, y, initialR, colorBox);
    y += redSlider.getHeight() + RgbSlider.SLIDER_PADDING;

    int initialG = Rgb.green(this.initialColor);
    GreenSlider greenSlider = new GreenSlider(x, y, initialG, colorBox);
    y += greenSlider.getHeight() + RgbSlider.SLIDER_PADDING;

    int initialB = Rgb.blue(this.initialColor);
    BlueSlider blueSlider = new BlueSlider(x, y, initialB, colorBox);
    y -= (greenSlider.getHeight() + redSlider.getHeight() + RgbSlider.SLIDER_PADDING + buttonHeight
        + RgbSlider.SLIDER_PADDING);

    @SuppressWarnings("checkstyle:Indentation")
    DefaultColorButton defaultButton = DefaultColorButton.builder(colorBox, press -> {
          if (colorBox.getNewColor() != this.defaultColor) {
            int r = Rgb.red(this.defaultColor);
            int g = Rgb.green(this.defaultColor);
            int b = Rgb.blue(this.defaultColor);

            redSlider.setValue(r);
            greenSlider.setValue(g);
            blueSlider.setValue(b);
            colorBox.setValue(String.valueOf(this.defaultColor));
          }
        })
        .withPos(x, y)
        .build();

    this.colorBox = colorBox;

    return new ArrayList<>(Arrays.asList(colorBox, defaultButton, redSlider, greenSlider, blueSlider));
  }

  @Override
  public void init() {
    super.init();

    int widgetWidth = BOX_WIDTH + BUTTON_PADDING;

    int posX = (this.width / 2) - (widgetWidth / 2);
    int posY = MENU_PADDING + buttonHeight + BUTTON_PADDING + buttonHeight;

    this.widgets.addAll(groupWidget(posX, posY, this.group));

    this.widgets.forEach(this::addRenderableWidget);
  }

  @Override
  public void tick() {
    super.tick();
  }

}