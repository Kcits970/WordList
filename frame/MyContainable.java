package frame;

public interface MyContainable {
    default void build() {
        createComponents();
        addComponents();
        bindActions();
        configureSettings();
    }

    void createComponents();
    void addComponents();
    default void bindActions() {}
    default void configureSettings() {}
}
