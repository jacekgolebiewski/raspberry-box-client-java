package pl.raspberry.box.core.service.console.processor;

public interface ConsoleInputProcessor {

    int getPriority();

    boolean isApplicable(String line);

    void process(String line);

}
