package club.iananderson.f3backport.client.gui.components.buttons;

import club.iananderson.f3backport.Common;
import club.iananderson.f3backport.client.gui.screens.F3BackportScreen;
import com.mojang.blaze3d.systems.RenderSystem;
import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public class MenuButton extends Button {
  private static final WidgetSprites SPRITES = new WidgetSprites(ResourceLocation.withDefaultNamespace("widget/button"),
                                                                 ResourceLocation.withDefaultNamespace(
                                                                     "widget/button_disabled"),
                                                                 ResourceLocation.withDefaultNamespace(
                                                                     "widget/button_highlighted"));

  private static final WidgetSprites COLOR_SPRITES = new WidgetSprites(Common.location("widget/color_button"),
                                                                       Common.location(
                                                                           "widget/color_button_highlighted"));
  float scale;
  private final MenuButtons buttonType;

  protected MenuButton(int x, int y, int width, int height, MenuButtons buttonType, OnPress onPress, float scale) {
    super(x, y, width, height, buttonType.getButtonText(), onPress, DEFAULT_NARRATION);
    this.scale = scale;
    this.buttonType = buttonType;
  }

  public static Builder builder(MenuButtons button, OnPress onPress) {
    return new Builder(button, onPress);
  }

  public static Builder builder(MenuButtons button, F3BackportScreen currentScreen, F3BackportScreen newScreen) {
    return new Builder(button, b -> {
      currentScreen.saveConfig();
      newScreen.open();
    });
  }

  @Override
  protected void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
    graphics.pose().pushPose();
    graphics.setColor(1.0F, 1.0F, 1.0F, this.alpha);
    RenderSystem.enableBlend();
    RenderSystem.enableDepthTest();
    if (this.buttonType == MenuButtons.COLOR) {
      graphics.blitSprite(COLOR_SPRITES.get(this.active, this.isHoveredOrFocused()), this.getX(), this.getY(),
                          this.getWidth(), this.getHeight());
    } else {
      graphics.blitSprite(SPRITES.get(this.active, this.isHoveredOrFocused()), this.getX(), this.getY(),
                          this.getWidth(), this.getHeight());
    }

    graphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);

    graphics.pose().scale(this.scale, this.scale, this.scale);
    Minecraft mc = Minecraft.getInstance();
    float minX = this.getX();
    float maxX = this.getX() + this.getWidth();
    float centerX = ((minX + maxX) / 2);

    float minY = this.getY();
    float maxY = this.getY() + this.getHeight();
    float centerY = (((minY + maxY) / 2));

    Color color = this.active
                  ? new Color(255, 255, 255)
                  : new Color(160, 160, 160);

    graphics.pose().translate(0, 1 - ((float) mc.font.lineHeight / 2), 0);
    graphics.drawCenteredString(mc.font, this.getMessage(), (int) (centerX / this.scale), (int) (centerY / this.scale),
                                color.getRGB());
    graphics.pose().popPose();
  }

  @Override
  public void renderString(@NonNull GuiGraphics graphics, @NonNull Font font, int color) {
    float halfStringWidth = (float) font.width(this.getMessage()) / 2;

    int minX = this.getX();
    int maxX = this.getX() + this.getWidth();
    int centerX = (minX + maxX) / 2;

    int minY = this.getY();
    int maxY = this.getY() + this.getHeight();
    int centerY = (int) (((float) (minY + maxY) / 2));

    graphics.pose().pushPose();
    graphics.drawCenteredString(font, this.getMessage(), (int) (centerX / this.scale), (int) (centerY / this.scale),
                                color);
    graphics.pose().popPose();
  }

  public enum MenuButtons {
    DONE(CommonComponents.GUI_DONE),

    CANCEL(CommonComponents.GUI_CANCEL),

    RESET(Common.translatedText("menu.f3backport.main.reset.button")),

    COLOR(Common.translatedText("menu.f3backport.main.color.button"));

    private final Component buttonText;

    MenuButtons(Component buttonText) {
      this.buttonText = buttonText;
    }

    public Component getButtonText() {
      return this.buttonText;
    }
  }

  public static class Builder {
    protected final MenuButtons buttonType;
    protected final OnPress onPress;
    protected int posX;
    protected int posY;
    protected int width = 150;
    protected int height = 20;
    protected Tooltip tooltip;
    private float scale = 1;

    public Builder(MenuButtons buttonType, OnPress onPress) {
      this.buttonType = buttonType;
      this.onPress = onPress;
    }

    /**
     * Uses default width = 150 and height = 20.
     *
     * @param x The horizontal position of the button
     * @param y The vertical position of the button
     */
    public Builder withPos(int x, int y) {
      this.posX = x;
      this.posY = y;
      return this;
    }

    /**
     * Uses default height = 20.
     *
     * @param width The width of the button
     */
    public Builder withWidth(int width) {
      this.width = width;
      return this;
    }

    public Builder withBounds(int x, int y, int width, int height) {
      this.withPos(x, y);
      this.width = width;
      this.height = height;
      return this;
    }

    public Builder withTooltip(@Nullable Tooltip tooltip) {
      this.tooltip = tooltip;
      return this;
    }

    public Builder withScale(float scale) {
      this.scale = scale;
      return this;
    }

    public MenuButton build() {
      MenuButton button = new MenuButton(this.posX, this.posY, this.width, this.height, this.buttonType, this.onPress,
                                         this.scale);
      button.setTooltip(this.tooltip);
      return button;
    }
  }
}