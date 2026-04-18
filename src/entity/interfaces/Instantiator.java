package entity.interfaces;

public abstract class Instantiator {
    protected static <T> T instantiate(Class<T> type) { 
        try {
            return type.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Failed to instantiate " + type.getName(), e);
        }
    }
}
