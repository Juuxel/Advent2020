package juuxel.advent2020.cactoos;

import org.cactoos.Output;
import org.cactoos.io.OutputTo;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.OutputStream;

/**
 * A non-closing wrapper for outputs. Used to not close {@link System#out} when writing to it.
 */
public final class NonClosing implements Output {
    private final Output output;

    public NonClosing(Output output) {
        this.output = output;
    }

    public NonClosing(OutputStream output) {
        this(new OutputTo(output));
    }

    @Override
    public OutputStream stream() throws Exception {
        return new OutputStream() {
            final OutputStream delegate = output.stream();

            @Override
            public void write(int b) throws IOException {
                delegate.write(b);
            }

            @Override
            public void write(@NotNull byte[] b) throws IOException {
                delegate.write(b);
            }

            @Override
            public void write(@NotNull byte[] b, int off, int len) throws IOException {
                delegate.write(b, off, len);
            }

            @Override
            public void flush() throws IOException {
                delegate.flush();
            }

            @Override
            public void close() {
                // Not here ;)
            }
        };
    }
}
