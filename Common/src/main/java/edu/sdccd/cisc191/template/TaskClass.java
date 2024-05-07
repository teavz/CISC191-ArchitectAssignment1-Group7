package edu.sdccd.cisc191.template;

public class TaskClass extends Thread{

    public TaskClass() {
        // TOdo: something here
    }

    public void run() {
        for (int i = 0; i <= 1000; i++){
            System.out.println(i);

            try{
                Thread.sleep(1000);
            } catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}
