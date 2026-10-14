package club.iananderson.f3backport.client.gui.screens;

import club.iananderson.f3backport.Common;
import club.iananderson.f3backport.client.gui.components.boxes.ColorEditBox;
import club.iananderson.f3backport.client.gui.components.debug.entries.Groups;
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
  private final List<ColorEditBox> groupBoxes = new ArrayList<>();
  private int posX;
  private int posY;

  public ColorsScreen(Screen parentScreen) {
    super(parentScreen, SCREEN_TITLE);
    this.buttonWidth = 170;
    loadConfig();
  }

  public static ColorsScreen getInstance(Screen parentScreen) {
    return new ColorsScreen(parentScreen);
  }

  private static EnumSet<Groups> groupListSet() {
    return Groups.GROUPS_ENUM_LIST.clone();
  }

  public void loadConfig() {
  }

  @Override
  public void onDone() {
    groupBoxes.forEach(editBox -> {
      if (Integer.parseInt(editBox.getValue()) != editBox.getColor()) {
        editBox.save();
      }
    });

    F3BackportClient.CLIENT_SPEC.save();
    super.onDone();
  }

  @Override
  public void onClose() {
    super.onClose();
  }

  public int getBoxWidth() {
    int widgetCount = groupListSet().size();
    int widgetTotalSize = ((80 + BUTTON_PADDING) * widgetCount);

    int boxWidth;
    if (this.width < widgetTotalSize) {
      boxWidth = 60;
    } else {
      boxWidth = 80;
    }

    return boxWidth;
  }

  private List<AbstractWidget> seasonWidget(int x, int y, Groups season) {
    ColorEditBox colorBox = new ColorEditBox(this.font, x, y, getBoxWidth(), buttonHeight, season);
    y += colorBox.getHeight() + BUTTON_PADDING;
    x -= 1;
    y += buttonHeight + RgbSlider.SLIDER_PADDING;

    int initialR = Rgb.red(colorBox.getColor());
    RedSlider redSlider = new RedSlider(x, y, initialR, colorBox);
    y += redSlider.getHeight() + RgbSlider.SLIDER_PADDING;

    int initialG = Rgb.green(colorBox.getColor());
    GreenSlider greenSlider = new GreenSlider(x, y, initialG, colorBox);
    y += greenSlider.getHeight() + RgbSlider.SLIDER_PADDING;

    int initialB = Rgb.blue(colorBox.getColor());
    BlueSlider blueSlider = new BlueSlider(x, y, initialB, colorBox);
    y -= (greenSlider.getHeight() + redSlider.getHeight() + RgbSlider.SLIDER_PADDING + buttonHeight
        + RgbSlider.SLIDER_PADDING);

    @SuppressWarnings("checkstyle:Indentation")
    DefaultColorButton defaultButton = DefaultColorButton.builder(colorBox, press -> {
          int defaultColorInt = season.getDefaultColor();

          if (colorBox.getNewColor() != defaultColorInt) {
            int r = Rgb.red(defaultColorInt);
            int g = Rgb.green(defaultColorInt);
            int b = Rgb.blue(defaultColorInt);

            redSlider.setValue(r);
            greenSlider.setValue(g);
            blueSlider.setValue(b);
            colorBox.setValue(String.valueOf(defaultColorInt));
          }
        })
        .withPos(x, y)
        .build();

    groupBoxes.add(colorBox);

    return new ArrayList<>(Arrays.asList(colorBox, defaultButton, redSlider, greenSlider, blueSlider));
  }

  @Override
  public void init() {
    super.init();

    int widgetWidth = getBoxWidth() + BUTTON_PADDING;
    int totalWidgetWidth = (groupListSet().size() * widgetWidth) - BUTTON_PADDING;

    this.posX = (this.width / 2) - (totalWidgetWidth / 2);
    this.posY = MENU_PADDING + buttonHeight + BUTTON_PADDING + buttonHeight;

    groupListSet().forEach(season -> {
      this.widgets.addAll(seasonWidget(this.posX, this.posY, season));
      this.posX += widgetWidth;
    });

    this.widgets.forEach(this::addRenderableWidget);
  }

  @Override
  public void tick() {
    super.tick();
  }

}