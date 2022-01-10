package util;

public abstract class MyEvent {
    Object source;

    public MyEvent(Object source) {
        this.source = source;
    }

    public Object getSource() {return source;}
}
