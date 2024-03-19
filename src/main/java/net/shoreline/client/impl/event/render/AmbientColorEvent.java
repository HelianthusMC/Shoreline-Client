package net.shoreline.client.impl.event.render;

import net.shoreline.client.api.event.Cancelable;
import net.shoreline.client.api.event.Event;

import java.awt.*;

@Cancelable
public class AmbientColorEvent extends Event {
    private Color color;

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
