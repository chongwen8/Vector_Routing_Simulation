package vector_routing;

import java.io.*;

public class VectorRouting {
	// returns true if the system is stable

	public boolean shareTables() {
		boolean stable;
		int detect = 0; // has the system been updated
		// I will use this to determine if the system is stable.
		for (int i = 0; i < Main.MAX_NODES; i++) {
			stable = Main.routers.updateDistanceVectorTable(i);
			if (stable) {
				detect++;
			}
			System.out.println("Router " + (i + 1) + " updated " + Main.routers.changes);
		}
		// if the system is not stable, then detect will get up from zero
		// it will be used to return to the main function to detect if the system is indeed stable
		if (detect == 0) {
			// system is stable
			return true;
		} else {
			// system is not stable
			return false;
		}
	}

	// This simply reads data from the input file
	// Will return the node information
	public int[][] readInput(String fileName) {
		int i = 0;
		int[][] nodeInfo; // will store the information of the nodes from the file
		nodeInfo = new int[Main.MAX_NODES][Main.MAX_NODES];
		try {
			File file = new File(fileName);
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String line;
			// Read data from the file line by line and store it in the array
			while ((line = bufferedReader.readLine()) != null) {
				// we know how the file will be formatted
				String[] lines = line.split(" ");
				for (int j = 0; j < Main.MAX_NODES; j++) {
					// convert string to int
					nodeInfo[i][j] = Integer.parseInt(lines[j]);
				}
				i++;
			}
			fileReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// return the created array to the main function
		return nodeInfo;
	}

}
