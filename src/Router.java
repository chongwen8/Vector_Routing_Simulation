package vector_routing;

import java.util.ArrayList;

/**
 *
 * @author Allen Wen
 */

public class Router {
	private int routerID;
	private Router_table[][] distanceVectorTable; // Network topology from routers A to H
	private Router_table[][] routingTable; // Routing table from A to H
	ArrayList<ArrayList<Integer>> neighborList = new ArrayList<ArrayList<Integer>>(Main.MAX_NODES);
	int changes = 0;

//	Router copy;
	public void initialTable(int[][] nodeInfo) {
		distanceVectorTable = new Router_table[Main.MAX_NODES][Main.MAX_NODES];
		routingTable = new Router_table[Main.MAX_NODES][Main.MAX_NODES];
		for (int i = 0; i < distanceVectorTable.length; i++) {
			ArrayList<Integer> temp = new ArrayList<Integer>();
			for (int j = 0; j < distanceVectorTable[0].length; j++) {
				if (i == j) {
					distanceVectorTable[i][j] = new Router_table(0, j, i);
				} else if (nodeInfo[i][j] != 0) {
					distanceVectorTable[i][j] = new Router_table(nodeInfo[i][j], j, j);
					temp.add(j);
				} else {
					distanceVectorTable[i][j] = new Router_table(16, j, i);

				}
			}
			neighborList.add(temp);
		}
		deepCopy();

	}

	public void getNeighbor(int i) {
		for (int neighbor : neighborList.get(i)) {
			for (Router_table temp : routingTable[neighbor]) {
				if (temp.getExpenses() != 16 && temp.getExpenses() != 0) {
					if (distanceVectorTable[i][temp.getDestination()].getExpenses() >= 16) {
						distanceVectorTable[i][temp.getDestination()].setTable(
								temp.getExpenses() + routingTable[i][neighbor].getExpenses(), temp.getDestination(),
								neighbor);
					} else if (distanceVectorTable[i][temp.getDestination()].getNextNum() == neighbor
							&& distanceVectorTable[i][temp.getDestination()]
									.getExpenses() != (temp.getExpenses() + routingTable[i][neighbor].getExpenses())) {
						distanceVectorTable[i][temp.getDestination()].setTable(
								temp.getExpenses() + routingTable[i][neighbor].getExpenses(), temp.getDestination(),
								neighbor);

					} else if ((temp.getExpenses()
							+ routingTable[i][neighbor].getExpenses()) < distanceVectorTable[i][temp.getDestination()]
									.getExpenses()) {
						distanceVectorTable[i][temp.getDestination()].setTable(
								temp.getExpenses() + routingTable[i][neighbor].getExpenses(), temp.getDestination(),
								neighbor);

					}
				}
			}
		}
	}

	public void printTable(int x) {
			for (Router_table router_table : distanceVectorTable[x]) {
				router_table.printTable();
			}
	}

	// gets the distance vector table from other node
	public boolean updateDistanceVectorTable(int loc) {
		this.changes = 0;
		// loop though the two arrays and update if you find a better connection
		for (int j = 0; j < distanceVectorTable[loc].length; j++) {
			if (distanceVectorTable[loc][j].getExpenses() != routingTable[loc][j].getExpenses()) {
				// change will keep track of if the tables were updated
				this.changes++;
			}
		}
		// if the tables changed, tell the main function so it can detect stablity
		if (this.changes > 0) {
			return true;
		} else {
			return false;
		}
	}

	// get a deep copy of array
	public void deepCopy() {
		for (int i = 0; i < routingTable.length; i++) {
			for (int j = 0; j < routingTable[0].length; j++) {
				routingTable[i][j] = new Router_table(distanceVectorTable[i][j]);
			}
		}
	}

}
