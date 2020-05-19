package logging;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

public class FileLogger implements ILogger{

    private PrintStream fStream;

    public FileLogger(String stream){
        try {
            fStream = new PrintStream(new FileOutputStream(stream));
        }catch (FileNotFoundException e){
            System.out.println("IOerror"+e.getMessage());
        }
    }

    /**
     * Writes data contained in
     * @param l into the file pointed by fStream
     */
    public void write(long l){
        fStream.print(l+" ");
    }

    public void write(String s){
        fStream.print(s+" ");
    }

    public void write(Object...parameters){
        for(Object obj : parameters){
            fStream.print(obj+" ");
            fStream.print('\n');
        }
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
        fStream.close();
    }
}
