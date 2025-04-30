import java.util.*;
import java.io.*;

public class SudokuBoard2 {
    private int[][] board;

    public SudokuBoard2(String filename) {
        board = new int[9][9];
        try {
            Scanner file = new Scanner(new File(filename));

            for (int r = 0; r < 9; r++) {
                String line = file.nextLine();
                for (int c = 0; c < 9; c++) {
                    Scanner lineScan = new Scanner(line);
                    char num = lineScan.next().charAt(c);
                    if (num == '.') {
                        board[r][c] = 0;
                    } else {
                        board[r][c] = num - 48;
                    }
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println("Cannot load: " + filename);
        } catch (InputMismatchException e) {
            System.out.println(filename + " does not meet format expectations.");
        }
    }

    public String toString() {
        String build = " -----------------\n";
        for (int r = 0; r < board.length; r++) {
            build += "|\t";
            for (int c = 0; c < board[0].length; c++) {
                if (board[r][c] == 0) {
                    build += ".|\t";
                } else {
                    build += board[r][c] + "|\t";
                }
            }
            build += "|\n";
        }
        build += " -----------------\n";
        return build;
    }

    public boolean isValid() {
        return isValidData() && rowsValid() && columnsValid() && miniSquaresValid();
    }

    private boolean isValidData() {
        for (int[] row : board) {
            for (int val : row) {
                if (val != 0 && (val < 1 || val > 9)) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean rowsValid() {
        for (int r = 0; r < 9; r++) {
            Set<Integer> seen = new HashSet<>();
            for (int c = 0; c < 9; c++) {
                int val = board[r][c];
                if (val != 0) {
                    if (seen.contains(val)) {
                        return false;
                    }
                    seen.add(val);
                }
            }
        }
        return true;
    }

    private boolean columnsValid() {
        for (int c = 0; c < 9; c++) {
            Set<Integer> seen = new HashSet<>();
            for (int r = 0; r < 9; r++) {
                int val = board[r][c];
                if (val != 0) {
                    if (seen.contains(val)) {
                        return false;
                    }
                    seen.add(val);
                }
            }
        }
        return true;
    }

    private boolean miniSquaresValid() {
        for (int i = 1; i <= 9; i++) {
            int[][] mini = miniSquare(i);
            Set<Integer> seen = new HashSet<>();
            for (int[] row : mini) {
                for (int val : row) {
                    if (val != 0) {
                        if (seen.contains(val)) {
                            return false;
                        }
                        seen.add(val);
                    }
                }
            }
        }
        return true;
    }

    private int[][] miniSquare(int spot) {
        int[][] mini = new int[3][3];
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                mini[r][c] = board[(spot - 1) / 3 * 3 + r][(spot - 1) % 3 * 3 + c];
            }
        }
        return mini;
    }

    public boolean isSolved() {
      if (!isValid()) {
         return false;
      }

      Map<Character, Integer> counts = new HashMap<>();
      for (char c = '1'; c <= '9'; c++) {
         counts.put(c, 0);
      }

      for (char[] row : board) {
         for (char val : row) {
            if (val != '0') {
               if (val < '1' || val > '9') {
                  return false;
               }
               counts.put(val, counts.get(val) + 1);
            }
         }
      }

      for (int count : counts.values()) {
         if (count != 9) {
            return false;
         }
      }

      return true;
   }
}



/*
Checking empty board...passed.
 Checking incomplete, valid board...passed.
 Checking complete, valid board...passed.
 Checking dirty data board...passed.
 Checking row violating board...passed.
 Checking col violating board...passed.
 Checking row&col violating board...passed.
 Checking mini-square violating board...passed.
 **** HORRAY: ALL TESTS PASSED ****
*/