package frame.mygraphics;

import java.util.*;

public abstract class GraphicsGroup extends MyGraphicsObject {
    List<MyGraphicsObject> group;
    int alignment;

    public GraphicsGroup(int alignment, MyGraphicsObject... objs) {
        group = new ArrayList<>();
        Arrays.stream(objs).forEach(obj -> group.add(obj));

        this.alignment = alignment;
    }
}
