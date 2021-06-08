package net.pistonmaster.pistonclient.core;

import java.awt.*;

public class HUDSettings {
    public static final ColorImpl ACTIVE = new ColorImpl(Color.WHITE);
    public static final ColorImpl INACTIVE = new ColorImpl(Color.GRAY);
    public static final ColorImpl BACKGROUND = new ColorImpl(Color.DARK_GRAY);
    public static final ColorImpl OUTLINE = new ColorImpl(Color.RED);
    public static final ColorImpl FONT = new ColorImpl(Color.CYAN);
    public static final NumberImpl OPACITY = new NumberImpl(10, 100, 0, 5);
    public static final NumberImpl ANIMATION_SPEED = new NumberImpl(10, 100, 0, 5);
}
