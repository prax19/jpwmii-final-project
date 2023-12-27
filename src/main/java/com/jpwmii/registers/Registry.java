package com.jpwmii.registers;

import com.jpwmii.Game;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class Registry<T> implements Iterable<T> {

    private final List<T> registry;
    private final Game context;

    public Registry(Game context) {
        this.context = context;
        this.registry = new ArrayList();
    }

    public int size() {
        return registry.size();
    }

    public T get(int index) {
        return registry.get(index);
    }

    public boolean isRegistered(T element) {
        return registry.contains(element);
    }

    public void set(int index, T element) {
        registry.set(index, element);
    }

    public void register(T element) {
        registry.add(element);
    }

    public void unregister(T element) {
        registry.remove(element);
    }

    public void clear() {
        registry.clear();
    }

    public abstract void update();

    @Override
    public Iterator<T> iterator() {
        return registry.iterator();
    }

    public Game getContext() {
        return context;
    }
}
