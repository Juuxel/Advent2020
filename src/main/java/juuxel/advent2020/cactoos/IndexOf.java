package juuxel.advent2020.cactoos;

import org.cactoos.Text;
import org.cactoos.scalar.ScalarEnvelope;

import java.util.List;

public final class IndexOf extends ScalarEnvelope<Integer> {
    public IndexOf(String text, char c) {
        super(() -> text.indexOf(c));
    }

    public IndexOf(Text text, char c) {
        super(() -> text.asString().indexOf(c));
    }

    public <T> IndexOf(List<? extends T> list, T element) {
        super(() -> list.indexOf(element));
    }
}
