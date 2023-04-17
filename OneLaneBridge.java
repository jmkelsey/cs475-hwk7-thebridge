import java.util.ArrayList;

public class OneLaneBridge extends Bridge {
    //set up other vars
    private Object canArrive;
    private Object canLeave;
    private int maxCars;
    //constructor
    public OneLaneBridge(int maxCars) {
        canArrive = new Object();
        canLeave = new Object();
        currentTime = 0;
        bridge = new ArrayList<>();
        direction = true;
        this.maxCars = maxCars;
      }
    //when cars want to arrive, call this
    public void arrive(Car car) throws InterruptedException{
        synchronized(canArrive) {
            //wait for lock
            while (this.direction != car.getDirection() || bridge.size() >= maxCars) {
                canArrive.wait();
            }
            //when unlocked add car
            car.setEntryTime(currentTime);
            bridge.add(car);
            printBridge();
            currentTime++;
        }

    }

    public void exit(Car car) throws InterruptedException{
        synchronized(canLeave) {
            //check if its the first car
            while (bridge.indexOf(car) != 0) {
                canLeave.wait();
            }
            synchronized(canArrive) {
                bridge.remove(car);
                printBridge();
                canLeave.notifyAll();
                if (this.bridge.size() == 0) {
                    this.direction = !this.direction;
                }
                canArrive.notifyAll();
            }
        }
        
    }

    private void printBridge(){
        System.out.println("Bridge (dir=" + direction + "): " + bridge);
    }
}
