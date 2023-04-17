/**
 * Runs all threads
 */

public class BridgeRunner {

	public static void main(String[] args) {
		//check command line inputs
		if (args.length != 2) {
			throw new IllegalArgumentException("Usage: javac BridgeRunner <bridge limit> <num cars>");
		} else if (Integer.parseInt(args[0]) < 1 || Integer.parseInt(args[1]) < 1) {
			throw new IllegalArgumentException("Error: bridge limit and/or num cars must be positive.");
		}
		int bridgeLimit = Integer.parseInt(args[0]);
		int numCars = Integer.parseInt(args[1]);
		//instantiate the bridge
		OneLaneBridge bridge = new OneLaneBridge(bridgeLimit);
		//allocate space for threads
		Thread[] carThreads = new Thread[numCars];
		for (int i = 0; i < numCars; i++) {
			carThreads[i] = new Thread(new Car(i, bridge));
			carThreads[i].start();
		}
		//start then join the threads
		for (int i = 0; i < numCars; i++) {
			try {
				carThreads[i].join(); // wait for the thread to finish
			} catch (InterruptedException e) {
				// handle the exception if the join is interrupted
				e.printStackTrace();
			}
		}

		System.out.println("All cars have crossed!!");
	}

}