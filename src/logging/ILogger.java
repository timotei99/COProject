package logging;

public interface ILogger {
    void write(long l);
    void write(String s);
    void writeTime(long value,TimeUnit unit);
    void writeTime(String string,long value,TimeUnit unit);
    void write(Object...parameters);
    void close();
}
