package Othello;


public class IdleTemps extends Thread{

    private long msec;
    private IdleRep o;

    public IdleTemps(IdleRep o, long msec){
        this.o = o;
        this.msec = msec;
    }

    public void run(){

        try{
            Thread.sleep(msec);
        }
        catch(InterruptedException ie){
            
        }
        o.resume();

    }

}
