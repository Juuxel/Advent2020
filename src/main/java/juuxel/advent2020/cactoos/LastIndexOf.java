package juuxel.advent2020.cactoos;

import org.cactoos.Text;
import org.cactoos.scalar.ScalarEnvelope;

import java.util.List;

public final class LastIndexOf extends ScalarEnvelope<Integer> {
    public LastIndexOf(String text, char c) {
        super(() -> text.lastIndexOf(c));
    }

    public LastIndexOf(Text text, char c) {
        super(() -> text.asString().lastIndexOf(c));
    }

    public <T> LastIndexOf(List<? extends T> list, T element) {
        super(() -> list.lastIndexOf(element));
    }
}
