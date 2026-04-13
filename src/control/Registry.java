package control;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import entity.interfaces.Describable;
import entity.interfaces.Named;

public class Registry<T extends Named & Describable> {
    private final Map<String, Entry<T>> entries = new LinkedHashMap<>();

    public static class Entry<T> {
        public final String description;
        public final Class<? extends T> type;

        public Entry(String description, Class<? extends T> type) {
            this.description = description;
            this.type = type;
        }
    }

    public void register(Class<? extends T> type) {
        try {
            T instance = type.getDeclaredConstructor().newInstance();
            String name = instance.getName();
            String description = instance.getDescription();
            entries.put(name, new Entry<>(description, type));
        } catch (Exception e) {
            throw new RuntimeException("Failed to register " + type.getName(), e);
        }
    }

    public List<String> getNames() {
        return new ArrayList<>(entries.keySet());
    }

    public List<Entry<T>> getEntries() {
        return new ArrayList<>(entries.values());
    }

    public T create(int index) {
        List<Entry<T>> list = getEntries();
        if (index < 0 || index >= list.size()) return null;
        try {
            return list.get(index).type.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Failed to instantiate " + list.get(index).type.getName(), e);
        }
    }

    public <S extends T> S create(Class<S> type) {
        try {
            return type.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Failed to instantiate " + type.getName(), e);
        }
    }

    public Entry<T> getEntry(Class<? extends T> type) {
        for (Entry<T> entry : entries.values()) {
            if (entry.type.equals(type)) return entry;
        }
        return null;
    }
}


