package juuxel.advent2020.cactoos;

import org.cactoos.Scalar;
import org.cactoos.io.TeeInput;
import org.cactoos.iterable.Filtered;
import org.cactoos.iterable.IterableOf;
import org.cactoos.iterable.Mapped;
import org.cactoos.scalar.Equals;
import org.cactoos.scalar.FirstOf;
import org.cactoos.scalar.LengthOf;
import org.cactoos.scalar.NumberOf;
import org.cactoos.scalar.SumOfInt;
import org.cactoos.scalar.Ternary;
import org.cactoos.text.TextOf;

import java.util.Optional;

public class Day1Cactoos {
    public static void main(String[] args) {
        Iterable<Integer> numbers = new Mapped<>(
            x -> new NumberOf(x).intValue(),
            new IterableOf<>(args)
        );
        // Part 1
        new LengthOf(
            new TeeInput(
                new TextOf(
                    new org.cactoos.scalar.Mapped<>(
                        x -> x.map(Object::toString).orElse("No match found!") + "\n",
                        new FirstOf<Optional<Integer>>(
                            new Filtered<>(Optional::isPresent,
                                new Mapped<>(
                                    (Scalar<Optional<Integer>> x) -> x.value(),
                                    new FlatMapped<>(
                                        b -> new Mapped<>(
                                            c -> new Ternary<>(
                                                new Equals<>(new SumOfInt(() -> b, () -> c), () -> 2020),
                                                () -> Optional.of(b * c),
                                                Optional::empty
                                            ),
                                            numbers
                                        ),
                                        numbers
                                    )
                                )
                            ),
                            Optional::empty
                        )
                    )
                ),
                new NonClosing(System.out)
            )
        ).intValue();
        // Part 2
        new LengthOf(
            new TeeInput(
                new TextOf(
                    new org.cactoos.scalar.Mapped<>(
                        x -> x.map(Object::toString).orElse("No match found!") + "\n",
                        new FirstOf<Optional<Integer>>(
                            new Filtered<>(Optional::isPresent,
                                new Mapped<>(
                                    (Scalar<Optional<Integer>> x) -> x.value(),
                                    new FlatMapped<>(
                                        a -> new FlatMapped<>(
                                            b -> new Mapped<>(
                                                c -> new Ternary<>(
                                                    new Equals<>(new SumOfInt(() -> a, () -> b, () -> c), () -> 2020),
                                                    () -> Optional.of(a * b * c),
                                                    Optional::empty
                                                ),
                                                numbers
                                            ),
                                            numbers
                                        ),
                                        numbers
                                    )
                                )
                            ),
                            Optional::empty
                        )
                    )
                ),
                new NonClosing(System.out)
            )
        ).intValue();
    }
}
