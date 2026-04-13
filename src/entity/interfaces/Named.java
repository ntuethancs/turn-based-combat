package entity.interfaces;

public interface Named {
    /**
     * Converts the class name from PascalCase/CamelCase to a space-separated Title Case string.
     * e.g., "ThornArmour" becomes "Thorn Armour".
     * 
     * @return The formatted name derived from the class name.
     */
    default String getName() {
        return this.getClass().getSimpleName().replaceAll("(?<=[a-z])(?=[A-Z])", " ");
    }
}
