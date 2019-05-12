package code.leetcode.medium;

public class GameOfLife {
    public static void main(String[] args) {

    }

    public void gameOfLife(int[][] board) {
        if (board == null || board.length == 0 || board[0] == null || board[0].length == 0) {
            return;
        }
        int rows = board.length;
        int colums = board[0].length;

        for(int i=0;i<rows;i++){
            for(int j=0;j<=colums;j++){
                int neighbous = getNeighbourValue(board, i, j, rows, colums);
                if(board[i][j] ==0){
                    if(neighbous == 3){
                        board[i][j] = -2;
                    }
                } else {
                    if(neighbous < 2 || neighbous >3){
                        board[i][j] = -1;
                    }
                }
            }
        }

        for(int i=0;i<rows;i++) {
            for (int j = 0; j <= colums; j++) {
                if(board[i][j] ==-2){
                    board[i][j] = 1;
                }
                if(board[i][j] ==-1){
                    board[i][j] = 0;
                }
            }
        }
    }


    private int getNeighbourValue(int[][] board, int i, int j, int rows, int colums) {
        return getValue(board, i - 1, j, rows, colums)
                + getValue(board, i + 1, j, rows, colums)
                + getValue(board, i, j - 1, rows, colums)
                + getValue(board, i, j + 1, rows, colums)
                + getValue(board, i - 1, j - 1, rows, colums)
                + getValue(board, i + 1, j + 1, rows, colums)
                + getValue(board, i - 1, j + 1, rows, colums)
                + getValue(board, i + 1, j - 1, rows, colums);
    }

    //-1 is original 1 but next 0
    //-2 is original 0 but next 1
    private int getValue(int[][] board, int i, int j, int rows, int colums) {
        if (i < 0 || i >= rows || j < 0 || j >= colums) {
            return 0;
        }
        if (board[i][j] == -2) {
            return 0;
        }
        if (board[i][j] == -1) {
            return 1;
        }
        return board[i][j];
    }


}
