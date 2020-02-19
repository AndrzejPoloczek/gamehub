package gamehub.gamebind.populator;

public interface Populator<S,T> {

    /**
     * Populate source element into target object.
     *
     * @param source Source object
     * @param target Target object
     */
    void populate(S source, T target);

    /**
     * Populate source element into target object.
     *
     * @param source Source object
     * @return Target object
     */
    T populate(S source);
}
