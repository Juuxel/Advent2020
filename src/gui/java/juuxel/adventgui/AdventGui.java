package juuxel.adventgui;

import juuxel.advent2020.arrow.Day1ArrowKt;
import juuxel.advent2020.cactoos.Day1Cactoos;
import juuxel.advent2020.cactoos.Day2Cactoos;
import juuxel.advent2020.misc.Day2AltKt;
import juuxel.advent2020.regular.Day1Kt;
import juuxel.advent2020.regular.Day2Kt;
import org.cactoos.io.OutputStreamTo;
import org.cactoos.io.TeeOutputStream;
import org.jetbrains.annotations.NotNull;
import org.pushingpixels.substance.extras.api.skinpack.SubstanceMangoLookAndFeel;

import javax.swing.*;
import java.awt.BorderLayout;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public final class AdventGui {
    private static final Executor EXECUTOR = Executors.newSingleThreadExecutor(r -> new Thread(r, "Advent"));
    private static final NumberFormat TIME_FORMAT = new DecimalFormat("#######.###");
    private static final int CURRENT_DAY;

    static {
        LocalDate today = LocalDate.now();
        int currentDayUnclamped = today.getMonth() == Month.DECEMBER ? today.getDayOfMonth() : 1;
        CURRENT_DAY = Math.min(currentDayUnclamped, 25);
    }

    private static final Entry[] ENTRIES = {
        new Entry("Day 1 (Kotlin)", 1, Day1Kt::main),
        new Entry("Day 1 (Kotlin, Arrow)", 1, Day1ArrowKt::nonSuspend),
        new Entry("Day 1 (Java, Cactoos)", 1, Day1Cactoos::main),
        new Entry("Day 2 (Kotlin)", 2, Day2Kt::main),
        new Entry("Day 2 (Java, Cactoos)", 2, Day2Cactoos::main),
        new Entry("Day 2 (Kotlin + Leaf Through)", 2, Day2AltKt::main),
    };

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(new SubstanceMangoLookAndFeel());
            } catch (UnsupportedLookAndFeelException e) {
                e.printStackTrace();
            }

            JPanel panel = new JPanel(new BorderLayout());
            JList<Entry> entries = new JList<>(ENTRIES);
            JButton run = new JButton("Run");
            JButton load = new JButton("Load data");
            JTextArea input = new JTextArea();
            JTextArea output = new JTextArea();
            JPanel inputArea = new JPanel(new BorderLayout());
            JScrollPane inputScroll = new JScrollPane(input);
            JScrollPane outputScroll = new JScrollPane(output);
            JSplitPane sideSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT, new JScrollPane(entries), inputArea);
            JSplitPane mainSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, sideSplit, outputScroll);
            JFrame frame = new JFrame("Advent of Code 2020");

            entries.setSelectedIndex(0);
            for (Entry entry : ENTRIES) {
                if (entry.day() == CURRENT_DAY) {
                    entries.setSelectedValue(entry, true);
                    break;
                }
            }

            output.setEditable(false);
            sideSplit.setDividerLocation(0.5);

            inputArea.add(inputScroll, BorderLayout.CENTER);
            inputArea.add(load, BorderLayout.SOUTH);
            inputArea.setBorder(BorderFactory.createTitledBorder("Input"));
            outputScroll.setBorder(BorderFactory.createTitledBorder("Output"));

            run.addActionListener(e -> {
                String[] inputLines = input.getText().lines().toArray(String[]::new);
                EXECUTOR.execute(timed(inputLines, entries.getSelectedValue()));
            });

            load.addActionListener(e -> {
                JDialog dialog = new JDialog(frame, "Choose a day", true);
                JPanel dialogPanel = new JPanel(new BorderLayout());
                JLabel currentDayLabel = new JLabel(CURRENT_DAY + " December");
                JSlider slider = new JSlider(1, 25, CURRENT_DAY);
                JButton done = new JButton("Done");
                boolean[] accepted = { false };

                currentDayLabel.setHorizontalAlignment(SwingConstants.CENTER);
                slider.setMajorTickSpacing(7);
                slider.setMinorTickSpacing(1);
                slider.setPaintLabels(true);
                slider.setPaintTicks(true);
                slider.addChangeListener(f -> currentDayLabel.setText(slider.getValue() + " December"));

                dialogPanel.add(currentDayLabel, BorderLayout.NORTH);
                dialogPanel.add(slider, BorderLayout.CENTER);
                dialogPanel.add(done, BorderLayout.SOUTH);

                done.addActionListener(f -> {
                    accepted[0] = true;
                    dialog.dispose();
                });

                dialog.setContentPane(dialogPanel);
                dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                dialog.setSize(200, 130);
                dialog.setVisible(true);

                if (!accepted[0]) return;

                try {
                    int day = slider.getValue();
                    String name = "/day" + day + ".txt";
                    InputStream in = AdventGui.class.getResourceAsStream(name);

                    if (in == null) {
                        JOptionPane.showMessageDialog(load, "It's not " + day + " December yet!", "Could not find data", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    try (in) {
                        input.setText(new String(in.readAllBytes(), StandardCharsets.UTF_8));
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });

            Writer outputWriter = new TextAreaWriter(output);
            // Sorry Yegor, but this is not "OO". Cactoos' IO converters are just too convenient, though ;)
            System.setOut(new PrintStream(new TeeOutputStream(System.out, new OutputStreamTo(outputWriter))));
            System.setErr(new PrintStream(new TeeOutputStream(System.err, new OutputStreamTo(outputWriter))));

            panel.add(mainSplit, BorderLayout.CENTER);
            panel.add(run, BorderLayout.SOUTH);

            frame.setSize(640, 480);
            frame.setContentPane(panel);
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }

    private static Runnable timed(String[] input, Entry entry) {
        return () -> {
            try {
                long start = System.nanoTime();

                entry.task().run(input);

                long end = System.nanoTime();
                long duration = end - start;
                double durationMs = (double) duration / 1_000_000.0;
                String durationMessage = ">>> Completed '" + entry.name() + "' in " + duration + " ns = " + TIME_FORMAT.format(durationMs) + " ms";

                if (durationMs >= 1000) {
                    double durationS = (double) durationMs / 1000.0;
                    durationMessage += " = " + TIME_FORMAT.format(durationS) + " s";
                }

                System.out.println(durationMessage);
                System.out.println();
            } catch (Exception e) {
                System.err.println(">>> '" + entry.name() + "' errored!");
                e.printStackTrace();
                System.err.println();
            }
        };
    }

    private record Entry(String name, int day, ThrowingMain task) {
        Entry(String name, int day, ThrowingRunnable task) {
            this(name, day, args -> task.run());
        }

        @Override
        public String toString() {
            return name;
        }
    }

    @FunctionalInterface
    private interface ThrowingMain {
        void run(String[] args) throws Exception;
    }

    @FunctionalInterface
    private interface ThrowingRunnable {
        void run() throws Exception;
    }

    private static final class TextAreaWriter extends Writer {
        private final JTextArea area;

        TextAreaWriter(JTextArea area) {
            this.area = area;
        }

        @Override
        public void write(@NotNull char[] cbuf, int off, int len) {
            if (SwingUtilities.isEventDispatchThread()) {
                synchronized (area) {
                    area.append(new String(Arrays.copyOfRange(cbuf, off, off + len)));
                }
            } else {
                char[] clone = cbuf.clone();
                SwingUtilities.invokeLater(() -> write(clone, off, len));
            }
        }

        @Override
        public void flush() {}

        @Override
        public void close() {}
    }
}
