package juuxel.advent2020.cactoos;

/**
 * Ordered pair.
 *
 * @param first  the first value
 * @param second the second value
 * @param <A>    the type of the first value
 * @param <B>    the type of the second value
 */
public record Pair<A, B>(A first, B second) {
}
