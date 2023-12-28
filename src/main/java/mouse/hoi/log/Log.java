package mouse.hoi.log;

public class Log {

    private static LogProvider logProvider;
    public static void write(Object message) {
        if (logProvider == null) {
            logProvider = new ConsoleLogger();
        }
        logProvider.write(message.toString());
    }
}
