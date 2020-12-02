package juuxel.advent2020.cactoos;

import org.cactoos.Func;
import org.cactoos.Scalar;
import org.cactoos.Text;
import org.cactoos.io.TeeInput;
import org.cactoos.iterable.Filtered;
import org.cactoos.iterable.IterableOfChars;
import org.cactoos.iterable.Mapped;
import org.cactoos.scalar.And;
import org.cactoos.scalar.Equals;
import org.cactoos.scalar.Folded;
import org.cactoos.scalar.ItemAt;
import org.cactoos.scalar.LengthOf;
import org.cactoos.scalar.Not;
import org.cactoos.scalar.NumberOf;
import org.cactoos.scalar.Or;
import org.cactoos.scalar.Sticky;
import org.cactoos.scalar.Ternary;
import org.cactoos.text.Sub;
import org.cactoos.text.TextOf;

public final class Day2Cactoos {
    public static void main(String[] args) {
        new LengthOf(
            new TeeInput(
                new TextOf(
                    new org.cactoos.scalar.Mapped<>(
                        pair -> pair.toString() + "\n",
                        new Folded<>(
                            new Pair<>(0, 0),
                            (a, b) -> new Pair<>(a.first() + b.first(), a.second() + b.second()),
                            new Mapped<>(
                                (String line) -> {
                                    int dash = new IndexOf(line, '-').value();
                                    int firstSpace = new IndexOf(line, ' ').value();
                                    int secondSpace = new LastIndexOf(line, ' ').value();
                                    int lower = new NumberOf(new Sub(line, 0, dash)).intValue();
                                    int upper = new NumberOf(new Sub(line, dash + 1, firstSpace)).intValue();
                                    Scalar<Character> targetChar = new Sticky<>(new ItemAt<>(firstSpace + 1, new IterableOfChars(line)));
                                    Text password = new Sub(line, secondSpace + 1);
                                    int count = new LengthOf(new Filtered<>(it -> it == targetChar.value(), new IterableOfChars(password))).intValue();
                                    Scalar<Character> first = new Sticky<>(new ItemAt<>(lower - 1, new IterableOfChars(password)));
                                    Scalar<Character> second = new Sticky<>(new ItemAt<>(upper - 1, new IterableOfChars(password)));
                                    int part1 = new Ternary<>(lower <= count && count <= upper, 1, 0).value();
                                    int part2 = new Ternary<>(new And(new Not(new Equals<>(first, second)), new Or(new Equals<>(first, targetChar), new Equals<>(second, targetChar))), 1, 0).value();
                                    return new Pair<>(part1, part2);
                                },
                                args
                            )
                        )
                    )
                ),
                new NonClosing(System.out)
            )
        ).intValue();
    }
}
