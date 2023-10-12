package model;

/**
 * Represents a predicate (boolean-valued function) of two arguments.
 *
 * @param <T> the type of the first argument
 * @param <U> the type of the second argument
 */
@FunctionalInterface
public interface BiPredicate<T, U> {

    /**
     * Evaluates this predicate on the given arguments.
     *
     * @param t the first input argument
     * @param u the second input argument
     * @return true if the input arguments match the predicate, otherwise false
     */
    boolean test(T t, U u);
}
