package testbench;

import bench.HDDWriteSpeed;
import bench.IBenchmark;

public class TestHDDSequentialSpeed {

    public static int compute_score(double speed,int reference){
        return (int)(speed*1000)/reference;
    }

    public static void main(String[] args){
        IBenchmark bench=new HDDWriteSpeed();
        final int reference=650;
        int scoreWriteFs=0;
        int scoreReadFs=0;
        int scoreWriteFb=0;
        int scoreReadFb=0;
        int totalScore=0;

        //bench.initialize("r");
        bench.run("fs",true,"w");
        scoreWriteFs=compute_score(((HDDWriteSpeed)bench).getResult(),reference);
        bench.run("fs",true,"r");
        scoreReadFs=compute_score(((HDDWriteSpeed)bench).getResult(),reference);
        bench.run("fb",true,"w");
        scoreWriteFb=compute_score(((HDDWriteSpeed)bench).getResult(),reference);
        bench.run("fb",true,"r");
        scoreReadFb=compute_score(((HDDWriteSpeed)bench).getResult(),reference);
        totalScore=((scoreWriteFb+scoreWriteFs)*4/5+(scoreReadFb+scoreReadFs)*1/5)/4;
        System.out.println("Total score is: "+totalScore);
    }
}
