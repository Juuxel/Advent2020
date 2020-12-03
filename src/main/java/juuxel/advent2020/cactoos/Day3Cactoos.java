package juuxel.advent2020.cactoos;

import org.cactoos.Scalar;
import org.cactoos.io.TeeInput;
import org.cactoos.iterable.IterableOfChars;
import org.cactoos.iterable.Mapped;
import org.cactoos.iterable.RangeOf;
import org.cactoos.list.ListOf;
import org.cactoos.scalar.Equals;
import org.cactoos.scalar.Folded;
import org.cactoos.scalar.ItemAt;
import org.cactoos.scalar.LengthOf;
import org.cactoos.scalar.SumOf;
import org.cactoos.scalar.Ternary;
import org.cactoos.text.TextOf;

import java.util.List;

public final class Day3Cactoos {
    public static void main(String[] args) {
        List<String> grid = new ListOf<>(args);
        // Part 1
        new LengthOf(
            new TeeInput(
                new TextOf(
                    new org.cactoos.scalar.Mapped<>(
                        x -> x + "\n",
                        new Trees(grid, 3, 1)
                    )
                ),
                new NonClosing(System.out)
            )
        ).intValue();
        // Part 2
        List<Pair<Integer, Integer>> slopes = new ListOf<>(
            new Pair<>(1, 1),
            new Pair<>(3, 1),
            new Pair<>(5, 1),
            new Pair<>(7, 1),
            new Pair<>(1, 2)
        );
        new LengthOf(
            new TeeInput(
                new TextOf(
                    new org.cactoos.scalar.Mapped<>(
                        x -> x + "\n",
                        new Folded<>(
                            1,
                            (a, b) -> a * b,
                            new Mapped<>(
                                slope -> new Trees(grid, slope.first(), slope.second()).value(),
                                slopes
                            )
                        )
                    )
                ),
                new NonClosing(System.out)
            )
        ).intValue();
    }

    private static final class Trees implements Scalar<Integer> {
        private final List<String> grid;
        private final int dx;
        private final int dy;

        Trees(List<String> grid, int dx, int dy) {
            this.grid = grid;
            this.dx = dx;
            this.dy = dy;
        }

        @Override
        public Integer value() {
            return new SumOf(
                new Mapped<>(
                    (Integer y) -> {
                        String row = new ItemAt<>(y, grid).value();
                        int x = (dx * y / dy) % new LengthOf(new TextOf(row)).intValue();
                        return new Ternary<>(
                            new Equals<>(() -> '#', new ItemAt<>(x, new IterableOfChars(row))),
                            1,
                            0
                        ).value();
                    },
                    new RangeOf<>(0, grid.size() - 1, i -> i + dy)
                )
            ).intValue();
        }
    }
}
