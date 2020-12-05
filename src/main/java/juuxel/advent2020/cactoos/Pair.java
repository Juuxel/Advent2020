package juuxel.advent2020.cactoos;

import java.util.Map;

/**
 * Ordered pair.
 *
 * @param first  the first value
 * @param second the second value
 * @param <A>    the type of the first value
 * @param <B>    the type of the second value
 */
public record Pair<A, B>(A first, B second) implements Map.Entry<A, B> {
    @Override
    public A getKey() {
        return first;
    }

    @Override
    public B getValue() {
        return second;
    }

    @Override
    public B setValue(B value) {
        throw new UnsupportedOperationException("no");
    }
}
