package club.iananderson.f3backport.client.gui.components.boxes;

import club.iananderson.f3backport.client.gui.components.debug.entries.Groups;
import club.iananderson.f3backport.client.gui.screens.ColorsScreen;
import java.util.EnumSet;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import org.jspecify.annotations.NonNull;

public class ColorEditBox extends EditBox {
  private static final int PADDING = 4;
  private final Groups boxGroup;
  private final int groupColor;
  private int newGroupColor;

  public ColorEditBox(Font font, int x, int y, int width, int height, Groups group) {
    super(font, x, y, width, height, group.getTranslatedName());
    this.boxGroup = group;
    this.groupColor = boxGroup.getGroupColor();
    this.newGroupColor = groupColor;
    this.setMaxLength(8);
    this.setValue(String.valueOf(groupColor));
    this.setResponder(colorString -> {
      if (validate(colorString)) {
        this.setTextColor(0xffffff);
        int colorInt = Integer.parseInt(colorString);

        if (colorInt != this.newGroupColor) {
          this.newGroupColor = colorInt;
          this.setValue(colorString);
        }

        ColorsScreen.doneButton.active = true;
      } else {
        this.setTextColor(16733525);
        ColorsScreen.doneButton.active = false;
      }
    });
  }

  private static EnumSet<Groups> groupListSet() {
    return Groups.GROUPS_ENUM_LIST.clone();
  }

  private boolean inBounds(int color) {
    int minColor = 0;
    int maxColor = 16777215;

    return color >= minColor && color <= maxColor;
  }

  public boolean validate(String colorString) {
    try {
      int colorInt = Integer.parseInt(colorString);
      return this.inBounds(colorInt);
    } catch (NumberFormatException formatException) {
      return false;
    }
  }

  public int getColor() {
    return this.groupColor;
  }

  public int getNewColor() {
    return this.newGroupColor;
  }

  public Groups getGroup() {
    return this.boxGroup;
  }

  public MutableComponent getMenuText(Groups group, int newRgb) {
    Style groupFormat = Style.EMPTY.withColor(newRgb);

    MutableComponent groupText = group.getTranslatedName();

    return groupText.withStyle(groupFormat);
  }

  @Override
  public void renderWidget(@NonNull GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
    Minecraft mc = Minecraft.getInstance();
    float textScale = 1;
    int scaledWidth = mc.getWindow().getGuiScaledWidth();
    int widgetTotalSize = ((80 + ColorsScreen.BUTTON_PADDING) * groupListSet().size());

    MutableComponent groupName = this.getMenuText(this.boxGroup, this.newGroupColor);

    graphics.pose().pushPose();
    if ((mc.font.width(groupName) > this.getWidth() - PADDING)) {
      textScale = ((float) this.getWidth() - PADDING) / mc.font.width(groupName);
    }
    graphics.pose().scale(textScale, textScale, 1);
    graphics.drawCenteredString(mc.font, groupName, (int) ((getX() + (double) this.getWidth() / 2) / textScale),
                                (int) ((getY() - (mc.font.lineHeight * textScale) - PADDING) / textScale), 0xffffff);
    graphics.pose().popPose();

    super.renderWidget(graphics, mouseX, mouseY, partialTicks);
  }
}