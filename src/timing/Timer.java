package timing;

public class Timer implements ITimer {
    private long start;
    private long end;
    private long elapsedTime;
    private long totalTime;

    /**
     *  Starts a new timer.
     *  If called without a prior stop() , it resets the current time losing all
     *  time information.
     */
    public void start(){
        totalTime=0;
        elapsedTime=0;
        start=System.nanoTime();
    }

    public long stop(){
        end=System.nanoTime();
        totalTime+=end-start;
        return totalTime;
    }

    public void resume(){
        //elapsedTime=0;
        start=System.nanoTime();

    }


    public long pause(){
        end=System.nanoTime();
        elapsedTime=end-start;
        totalTime+=elapsedTime;
        return elapsedTime;
    }
}
