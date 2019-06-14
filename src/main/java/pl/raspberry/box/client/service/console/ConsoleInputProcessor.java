package pl.raspberry.box.client.service.console;

public interface ConsoleInputProcessor {

    int getPriority();

    boolean isApplicable(String line);

    void process(String line);

}
