package logging;

public class ConsoleLogger implements ILogger {
    public void write(long l){
        System.out.println(l+" ");
    }

    public void write(String s){
        System.out.println(s+" ");
    }

    public void write(Object...values){

        for(Object obj : values) {
            System.out.print(obj+" ");
        }
        System.out.print('\n');
    }

    public void writeTime(long value,TimeUnit unit){
        double t=TimeUnit.convertTime(TimeUnit.Nano,unit,(double)value);
        write(t,unit);
    }

    public void writeTime(String string,long value,TimeUnit unit){
        double t=TimeUnit.convertTime(TimeUnit.Nano,unit,(double)value);
        write(string,t,unit);
    }

    public void close(){

    }
}
