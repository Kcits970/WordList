package frame.mainframe;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SortableListModel<E extends Comparable<E>> extends AbstractListModel<E> {
    List<E> elements;

    public SortableListModel() {
        elements = new ArrayList<>();
    }

    public void sort() {
        Collections.sort(elements);
        fireContentsChanged(this, 0, elements.size() - 1);
    }

    public void setElements(List<E> elements) {
        this.elements.clear();
        this.elements.addAll(elements);
        sort();
    }

    public void add(E element) {
        elements.add(element);
        sort();
    }

    public void remove(Object o) {
        elements.remove(o);
        fireContentsChanged(this, 0, elements.size() - 1);
    }

    public int indexOf(Object o) {
        return elements.indexOf(o);
    }

    public boolean contains(Object o) {
        return elements.contains(o);
    }

    public E get(int index) {
        if (index < 0 || index >= elements.size())
            return null;
        else
            return elements.get(index);
    }

    @Override
    public int getSize() {
        return elements.size();
    }

    @Override
    public E getElementAt(int index) {
        return elements.get(index);
    }
}
