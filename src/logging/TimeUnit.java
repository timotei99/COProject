package logging;

public enum TimeUnit {
    Nano,
    Micro,
    Milli,
    Sec;

    public static double convertTime(TimeUnit from,TimeUnit to,double value){
        double time=0;
        if(from==TimeUnit.Nano){
            switch (to){
                case Micro: time=value/1000.0;
                break;
                case Milli: time=value/1000000.0;
                break;
                case Sec: time=value/1000000000.0;
                break;
                default: time=value;
                break;
            }
        }
        if(from==TimeUnit.Micro){
            switch (to){
                case Nano: time=value*1000.0;
                    break;
                case Milli: time=value/1000.0;
                    break;
                case Sec: time=value/1000000.0;
                    break;
                default: time=value;
                break;
            }
        }
        if(from==TimeUnit.Milli){
            switch (to){
                case Nano: time=value*1000000.0;
                    break;
                case Micro: time=value*1000.0;
                    break;
                case Sec: time=value/1000.0;
                    break;
                default: time=value;
                break;
            }
        }
        if(from==TimeUnit.Sec){
            switch (to){
                case Nano: time=value*1000000000.0;
                    break;
                case Micro: time=value*1000000.0;
                    break;
                case Milli: time=value*1000.0;
                    break;
                default: time=value;
                break;
            }
        }
        return time;
    }
}
