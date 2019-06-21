package pl.raspberry.box.client.service.console;

import pl.raspberry.box.client.service.console.processor.ConsoleInputProcessor;
import pl.raspberry.box.client.service.console.processor.PropertyRequestConsoleInputProcessor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class ConsoleInputService {

    private static final String EXIT_TEXT = "exit";
    private BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

    private final List<ConsoleInputProcessor> inputProcessors = Arrays.asList(
            new PropertyRequestConsoleInputProcessor()
    );

    public String readLine() {
        try {
            String line;
            while(true) {
                line = bufferedReader.readLine();
                Optional<ConsoleInputProcessor> processor = getInputProcessor(line);
                if (!processor.isPresent()) {
                    break;
                }
                processor.get().process(line);
            }
            return line;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Optional<ConsoleInputProcessor> getInputProcessor(String line) {
        return inputProcessors.stream()
                .sorted(Comparator.comparing(ConsoleInputProcessor::getPriority))
                .filter(processor -> processor.isApplicable(line))
                .findFirst();
    }

}
