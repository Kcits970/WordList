package frame;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.util.Arrays;

public class MyFrameTools {
    public static Border createMyTitledBorder(String title) {
        Border baseBorder = BorderFactory.createLineBorder(new Color(184, 207, 229));
        return BorderFactory.createTitledBorder(baseBorder, title, TitledBorder.LEFT, TitledBorder.TOP, MyFonts.CONSOLAS_16);
    }

    public static Container addComponentWithEdgeSpacing(Container c, Component comp, int spacing) {
        //adds the given component to the specified container with the specified spacing, and returns it.
        //if the container is null, a new container containing the component will be returned.

        if (c == null) c = new Container();

        c.setLayout(new BorderLayout());
        c.add(Box.createHorizontalStrut(spacing), BorderLayout.EAST);
        c.add(Box.createHorizontalStrut(spacing), BorderLayout.WEST);
        c.add(Box.createVerticalStrut(spacing), BorderLayout.NORTH);
        c.add(Box.createVerticalStrut(spacing), BorderLayout.SOUTH);
        c.add(comp, BorderLayout.CENTER);

        return c;
    }

    public static Container addHorizontally(Container c, boolean alignWidths, Component... components) {
        //adds the given components horizontally to the specified container, and returns it.
        //if the container is null, a new container containing the components will be returned.

        if (c == null) c = new Container();

        c.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 0));

        if (alignWidths) {
            int greatestWidth = Arrays.stream(components).mapToInt(component -> component.getPreferredSize().width).max().orElse(0);
            Arrays.stream(components).forEach(component -> component.setPreferredSize(new Dimension(greatestWidth, component.getPreferredSize().height)));
        }

        for (Component comp : components)
            c.add(comp);

        return c;
    }

    public static Container addVertically(Container c, Component... components) {
        if (c == null) c = new Container();

        c.setLayout(new BoxLayout(c, BoxLayout.Y_AXIS));
        for (int i = 0; i < components.length; i++) {
            c.add(components[i]);

            if (i < components.length - 1)
                c.add(Box.createVerticalStrut(5));
        }

        return c;
    }
}