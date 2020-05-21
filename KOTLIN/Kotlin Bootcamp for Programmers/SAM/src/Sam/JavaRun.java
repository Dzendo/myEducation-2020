package Sam;

public class JavaRun {
    // пример на Java функции, которая принимает запускаемый интерфейс в качестве параметра
    // и выполняет метод запуска
    public static void runNow(Runnable runnable) {
        runnable.run();
    }
}
