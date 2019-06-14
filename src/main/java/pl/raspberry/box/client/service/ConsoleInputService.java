package pl.raspberry.box.client.service;

import pl.raspberry.box.client.service.console.ConsoleInputProcessor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;

public class ConsoleInputService {

    private static final String EXIT_TEXT = "exit";

    private final List<ConsoleInputProcessor> inputProcessors = new ArrayList<>();

    public void addInputProcessor(ConsoleInputProcessor processor) {
        inputProcessors.add(processor);
    }

    public void readLines(Runnable onExit) {
        readLineUntil(this::invokeProcessor, onExit);
    }

    private void invokeProcessor(String line) {
        inputProcessors.stream()
                .sorted(Comparator.comparing(ConsoleInputProcessor::getPriority))
                .filter(processor -> processor.isApplicable(line))
                .findFirst()
                .ifPresent(processor -> processor.process(line));
    }

    private void readLineUntil(Consumer<String> onLine, Runnable onExit) {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String line;
        try {
            while ((line = in.readLine()) != null) {
                if (line.equals(ConsoleInputService.EXIT_TEXT)) {
                    onExit.run();
                    break;
                }
                onLine.accept(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

}
