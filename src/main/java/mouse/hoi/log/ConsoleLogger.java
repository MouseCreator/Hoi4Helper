package mouse.hoi.log;

public class ConsoleLogger implements LogProvider {
    @Override
    public void write(String message) {
        System.out.println(message);
    }
}
