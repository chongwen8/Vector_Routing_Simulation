package vector_routing;

import java.util.Scanner;

public class Main {

	public static final int MAX_NODES = 8;
	static Router routers; // my router object
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in); // gets user input
		boolean singleStep; // if true then the user wants to use single step, otherwise false
		boolean stable = false; // assume the system is not stable and needs to run
		int cycles = 0; // cycles until stable
		String num; // show numTH table's information
		String choice; // this will tell the program what the user has chosen how to run the program
		VectorRouting vr = new VectorRouting();
		int[][] nodeInfo = vr.readInput("/Users/chong/code/input.txt"); 
		
		System.out.print("Would you like single step mode? (y/n): ");
		// Get what the user wants to do.
		choice = scanner.nextLine();

		// if yes, then the user wants single step
		if (choice.equalsIgnoreCase("y")) {
			singleStep = true;
		} else {
			singleStep = false;
		}

		// Begin main part of functionality
		System.out.println("Initializing routers.");

		// Create a list of routers
		routers = new Router();
		routers.initialTable(nodeInfo);
		System.out.println("Initializing is done.");
		// The program will diverge based on user input
		if (singleStep) {
			// execute the single step process
			while (!stable) {
				// make the user advance by pressing return
				System.out.println("Please press enter to continue.");
				choice = scanner.nextLine();
				for (int i = 0; i < MAX_NODES; i++) {
					routers.getNeighbor(i);
				}
				stable = vr.shareTables();
				routers.deepCopy();
			}
			// We wont be needing to get anymore user input
			System.out.println("The system is now stable.");
			System.out.println("The nodes are not getting any new information");
		} else {
			// auto process
			// the system starts not stable. It will run until it is stable
			while (!stable) {
				//one cycle of the system sharing information
				for (int i = 0; i < MAX_NODES; i++) {
					routers.getNeighbor(i);
				}
				stable = vr.shareTables();
				System.out.println();
				routers.deepCopy();
				cycles++;
			}
			System.out.println("The system is now stable.");
			System.out.println("The nodes are not getting any new information");
			System.out.println("It took the system " + cycles + " cycles");
		}

		System.out.println("which table's infor do u want to get ?");
		num = scanner.nextLine();
		routers.printTable(Integer.parseInt(num) - 1);
		scanner.close();
		
	}

}
