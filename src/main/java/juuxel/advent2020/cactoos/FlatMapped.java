package juuxel.advent2020.cactoos;

import org.cactoos.Func;
import org.cactoos.iterable.IterableEnvelope;
import org.cactoos.iterable.IterableOf;
import org.cactoos.iterable.Joined;
import org.cactoos.iterable.Mapped;

/**
 * A flat-mapping iterable. FlatMapped takes each {@code X} and converts it to an {@code Iterable<Y>}.
 * All the results are joined to a single {@code Iterable<Y>}.
 *
 * <p>Equivalent to {@code new Joined<>(new Mapped<>(...))}, but a lot cleaner in my opinion.
 *
 * @param <Y>
 */
public final class FlatMapped<Y> extends IterableEnvelope<Y> {
    public <X> FlatMapped(Func<? super X, ? extends Iterable<? extends Y>> transform, Iterable<? extends X> src) {
        super(new Joined<>(new Mapped<>(transform, src)));
    }

    @SafeVarargs
    public <X> FlatMapped(Func<? super X, ? extends Iterable<? extends Y>> transform, X... src) {
        this(transform, new IterableOf<>(src));
    }
}
