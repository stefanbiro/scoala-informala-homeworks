package ro.stefan.java;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * this class simulates X0 - play between two players they put randomly marks
 * (x,0)- one after another. After each put, take place a cheek for win
 * condition and a result is printed : ---- the winner, if is one ---- or nobody
 * wins ,if nobody wins
 * 
 * @author stefan1
 * 
 */
public class PlayX0 {

	// create a table for play (this will be the monitor)
	private static char[][] table = new char[3][3];

	// create two players
	static Mark player1 = new Mark("Stefan", 'x');
	static Mark player2 = new Mark("Sebi", '0');

	// create an object for referee (for checking win condition)
	static Mark cheek = new Mark();

	public static void main(String[] arg) {

		PlayX0 play = new PlayX0();

		// table initialization
		play.initializeTable();

		System.out.println("START GAME ");

		// printing the empty table for play
		PlayX0.printTable();

		// create three threads -> putX , put0, end referee
		Thread putX = new Thread(new Runnable() {

			@Override
			public void run() {
				player1.put();
			}

		});

		Thread put0 = new Thread(new Runnable() {

			@Override
			public void run() {
				player2.put();
			}

		});

		Thread referee = new Thread(new Runnable() {

			@Override
			public void run() {
				cheek.checkForWin();
			}

		});

		// create a pool thread - 3 threads
		ExecutorService pool = Executors.newFixedThreadPool(3);

		// put condition for threads
		while (!isTableFull()) {

			// -- chiar si prin codul asta sunt cazuri cand un jucator
			// -- pune de doua ori una dupa alta - NU STIU DE CE !
			// am incercat mai multe variante de cod sa le pornesc a.i.
			// sa nu puna unu de doua ori !! rezultatele sunt corecte
			// (ori de cate ori rulez! ) dar cum se ajunge acolo nu prea
			int x = 0;
			while (x < 2) {
				if (x == 0) {
					pool.execute(putX);
				} else if (x == 1) {
					pool.execute(put0);
				}
				x++;
			}

			// after each move the referee cheek win conditions
			pool.execute(referee);

			int win = cheek.checkForWin();

			// loop for stop threads in case of win and
			// print the winner
			while (win == 1 || win == 2) {

				pool.shutdownNow(); // force stop

				String winner = null;

				if (win == 1) {
					winner = player1.getPlayer();

				} else if (win == 2) {
					winner = player2.getPlayer();
				}

				System.out.println("Castigatorul este : " + winner);

				return;
			}

		}

		pool.shutdown();
		System.out.println("Partida indecisa");
	}

	// Set the table with empty values.
	public void initializeTable() {

		// Loop through rows
		for (int row = 0; row < table.length; row++) {

			// Loop through columns
			for (int column = 0; column < table[row].length; column++) {
				table[row][column] = ' ';
			}
		}
	}

	// method for print the table
	public static void printTable() {

		System.out.println("-------------");

		for (int i = 0; i < table.length; i++) {
			System.out.print("| ");
			for (int j = 0; j < table[i].length; j++) {
				System.out.print(table[i][j] + " | ");
			}
			System.out.println();
			System.out.println("-------------");
		}
	}

	// cheek if the table is full
	public static boolean isTableFull() {
		boolean isFull = true;

		for (int i = 0; i < table.length; i++) {
			for (int j = 0; j < table[i].length; j++) {
				if (table[i][j] == ' ') {
					isFull = false;
				}
			}
		}

		return isFull;
	}

	/**
	 * this class provide marking of x or 0 and cheek the win conditions
	 */
	private static class Mark {

		private String player;
		private char mark;

		public Mark(String player, char mark) {
			super();
			this.player = player;
			this.mark = mark;
		}

		public Mark() {

		}

		public void put() {

			synchronized (table) {

				for (int i = 0; i < table.length; i++) {
					for (int j = 0; j < table[i].length; j++) {

						// put mark in random position
						int a = (int) (Math.random() * (i + 1));
						int b = (int) (Math.random() * (j + 1));

						// cheek if the position is available
						if (table[a][b] == ' ') {
							table[a][b] = mark;

							printTable();

							return;
						}

					}

				}

			}

		}

		public String getPlayer() {
			return player;
		}

		/**
		 * this method cheek the winner for all three conditions row , column
		 * and diagonals .This method return 1 for player1 or 2 for player2
		 */
		public synchronized int checkForWin() {
			int key = 0;
			int a = checkRowsForWin();
			int b = checkColumnsForWin();
			int c = checkDiagonalsForWin();
			if (a == 1 || b == 1 || c == 1) {
				key = 1;
			} else if (a == 2 || b == 2 || c == 2) {
				key = 2;
			}
			return key;
		}

		// cheek diagonals
		private int checkDiagonalsForWin() {

			int result = 0;

			if (table[0][0] == 'x' && table[1][1] == 'x' && table[2][2] == 'x') {
				result = 1;
			} else if (table[0][0] == '0' && table[1][1] == '0' && table[2][2] == '0') {
				result = 2;
			} else if (table[2][0] == 'x' && table[1][1] == 'x' && table[0][2] == 'x') {
				result = 1;
			} else if (table[2][0] == '0' && table[1][1] == '0' && table[0][2] == '0') {
				result = 2;
			}

			return result;
		}

		// cheek columns
		private int checkColumnsForWin() {

			int result = 0;
			for (int i = 0; i < 3; i++) {

				if ((table[0][i] == 'x') && (table[1][i] == 'x') && (table[2][i] == 'x')) {
					result = 1;
				} else if ((table[0][i] == '0') && (table[1][i] == '0') && (table[2][i] == '0')) {
					result = 2;
				}
			}
			return result;

		}

		// cheek rows
		private int checkRowsForWin() {

			int result = 0;
			for (int i = 0; i < 3; i++) {

				if ((table[i][0] == 'x') && (table[i][1] == 'x') && (table[i][2] == 'x')) {
					result = 1;
				} else if ((table[i][0] == '0') && (table[i][1] == '0') && (table[i][2] == '0')) {
					result = 2;
				}
			}
			return result;
		}

	}

}
