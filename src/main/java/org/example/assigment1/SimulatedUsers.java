package org.example.assigment1;

import java.util.List;
import java.util.Random;

public class SimulatedUsers implements Runnable {

    private String userId;
    private VinylLibrary vinylLibrary;
    private Random random = new Random();
    private boolean running = true;

    public SimulatedUsers(String userId, VinylLibrary vinylLibrary)
    {
        this.userId = userId;
        this.vinylLibrary = vinylLibrary;
    }


    @Override
    public void run() {
           while(running)
           {
               try {
                   //sleep a random amaount 1-4 seconds
                   Thread.sleep(1000 + random.nextInt(3000));

                   List<Vinyl> vinyls = vinylLibrary.getVinyls();
                   if(vinyls.isEmpty()) continue;

                   //pick a random vinyl
                   Vinyl vinyl = vinyls.get(random.nextInt(vinyls.size()));

                   // don't touch vinyls that are meant to be controlled manually
                   if(!vinyl.isSimulated()) continue;

                   // pick a random action (0-reserve, 1-borrow, 2-return, 3-request removal)
                   int action = random.nextInt(4);

                   switch (action)
                   {
                       case 0 -> vinyl.reserve(userId);
                       case 1 -> vinyl.borrow(userId);
                       case 2 -> vinyl.returnVinyl();
                       case 3 -> vinyl.requestRemoval();
                   }
               }catch (InterruptedException e)
               {
                   Thread.currentThread().interrupt();
                   running = false;
               }
           }
    }

    public void stop()
    {
        running = false;
    }
}
