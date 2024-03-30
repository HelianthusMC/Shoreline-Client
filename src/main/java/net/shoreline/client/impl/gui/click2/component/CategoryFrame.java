package net.shoreline.client.impl.gui.click2.component;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.math.Vec2f;

import java.awt.*;

/**
 * @author xgraza
 * @since 03/30/24
 */
public class CategoryFrame extends ComponentDraggable {

    public static final int HEADER = new Color(33, 33, 33).getRGB();
    public static final int BACKGROUND = new Color(48, 48, 48).getRGB();
    private static final float PADDING = 1.0f;

    private final String name;
    private final float headerHeight;

    public CategoryFrame(final String name, final float headerHeight) {
        this.name = name;
        this.headerHeight = headerHeight;
    }

    @Override
    public void draw(DrawContext ctx, Vec2f mouse, float tickDelta) {
        updatePosition(mouse);

        //ctx.enableScissor((int) getX(), (int) getY(), (int) (getX() + getWidth()), (int) (getY() + getHeight()));

        ctx.fill((int) getX(), (int) getY(), (int) (getX() + getWidth()), (int) (getY() + getHeight()), HEADER);
        ctx.fill((int) (getX() + PADDING), (int) (getY() + headerHeight + PADDING), (int) (getX() + getWidth() - PADDING), (int) (getY() + getHeight() - PADDING), BACKGROUND);

        ctx.drawTextWithShadow(mc.textRenderer, name, (int) (getX() + (PADDING * 4)), (int) (getY() + 5), -1);

        float componentY = getY() + headerHeight ;
        for (final Component component : getChildren()) {
            if (component instanceof AbstractComponent c) {
                c.setX(getX() + PADDING);
                c.setY(componentY);
                c.setWidth(getWidth() - (PADDING * 2));
                c.setHeight(headerHeight - 1.0f);
                componentY += c.getHeight();
            }

            component.draw(ctx, mouse, tickDelta);
        }

        //ctx.disableScissor();
    }

    @Override
    public float getHeight() {
        float frameHeight = super.getHeight() + (PADDING * 2);
        for (final Component component : getChildren()) {
            if (component instanceof AbstractComponent c) {
                frameHeight += c.getHeight();
            }
        }
        return frameHeight;
    }
}
