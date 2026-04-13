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

    public Class<? extends T> getType(int index) {
        return getEntries().get(index).type;
    }
}


