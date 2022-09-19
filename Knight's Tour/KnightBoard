public class KnightBoard {
    private int size;
    private int moveCount;
    private int currentX, currentY;

    public int[][] board;

    public KnightBoard(int n){
        this.size = n;
        board = new int[size][size];
        for (int i=0; i < board.length; i++) {
            for (int j=0; j < board.length; j++)
                board[i][j] = -1; // -1 indicates a non-visited position
        }
        currentX= 0;
        currentY= 0;
        moveCount = 1;
        board[currentX][currentY] = moveCount;
    }

    public void printChessBoard() {
        for ( int i = 0; i < this.size; i++ ){
            for ( int h = 0; h < this.size; h++ )
                System.out.print("+--");
        System.out.println("+");
            for ( int j = 0; j < this.size; j++ ){
                System.out.print("|");
                    if ( board[i][j] == -1 )
                        System.out.print("     ");
                    else if ( board[i][j] < 10 )
                        System.out.print(" " + board[i][j]);
                    else System.out.print(board[i][j]);
            }
            System.out.println("|");
        }
        for ( int h = 0; h < this.size; h++ )
            System.out.print("+--");
        System.out.println("+");
    }

    public KnightBoard copyBoard(){
        KnightBoard copy = new KnightBoard(this.size);
        copy.setMoveCount(this.moveCount);
        copy.setCurrentX(this.currentX);
        copy.setCurrentY(this.currentY);
        for ( int i = 0; i < this.size; i++ )
            for ( int j = 0;     j != this.size; j++ )
                copy.board[i][j] = this.board[i][j];
        return copy;
    }

    public int getMoveCount(){ return moveCount; }
    public int getCurrentX(){ return currentX; }
    public int getCurrentY(){ return currentY; }
    public void setMoveCount(int count){ moveCount = count; }
    public void setCurrentX(int x) { currentX = x;}
    public void setCurrentY(int y){ currentY = y;}

    public boolean move(int newX, int newY) {
        boolean valid;
        // Check if new position is on the board and has never been visited:
        valid = newX >= 0 && newX < size && newY >= 0 && newY < size && board[newX][newY] == -1;
        if (valid){
            this.currentX = newX;
            this.currentY = newY;
            this.moveCount = this.moveCount + 1;
            this.board[currentX][currentY] = this.moveCount;
        }
        return valid;
    }
}
