package vector_routing;

import java.util.ArrayList;

public class Router_table {
	private int destination;
	private int expenses;
	private int nextNum;
	public int changes = 0;

	public Router_table(int expenses, int destination, int jumpNun) {
		this.expenses = expenses;
		this.destination = destination;
		this.nextNum = jumpNun;
	}
	
	public Router_table(Router_table r) {
		this.expenses = r.getExpenses();
		this.destination = r.getDestination();
		this.nextNum = r.getNextNum();
	}

	public void setTable(int expenses, int destination, int jumpNun) {
		this.expenses = expenses;
		this.destination = destination;
		this.nextNum = jumpNun;
	}

	public void editExpenses() {
		this.expenses++;
	}

	public void printTable() {
		System.out.printf("Destination: %-5s " + "expenses: %-2d " + "nextNum: %-3s ", this.destination + 1, this.expenses,
				this.nextNum + 1);
		System.out.println();
	}

	public int getDestination() {
		return destination;
	}

	public int getExpenses() {
		return expenses;
	}

	public int getNextNum() {
		return nextNum;
	}
}
