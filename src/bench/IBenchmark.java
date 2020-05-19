package bench;

public interface IBenchmark {
    void initialize(Object...parameters);
    void run();
    void run(Object...parameters);
    void warmUp();
    void clean();
    void cancel();
}
