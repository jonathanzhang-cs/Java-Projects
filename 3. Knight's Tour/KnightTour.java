import java.util.Stack;

public class KnightTour {
    public static KnightBoard tour(int n){
        Stack<KnightBoard> candidates = new Stack<KnightBoard>();

        KnightBoard kb = new KnightBoard(n); 
        candidates.push(kb);
        
        KnightBoard currentBoard = new KnightBoard(n), newMove;
        
        // Arrays that dictate the sequence in which the knight will find possible move locations.
        int [] xMoves = {2, 1, -1, -2, -2, -1, 1, 2};
        int [] yMoves = {1, 2, 2, 1, -1, -2, -2, -1};
        
        while (candidates.size() != 0){
            currentBoard = candidates.pop();

            if (currentBoard.getMoveCount() == Math.pow(currentBoard.board.length, 2)){
                return currentBoard;
            }
            else{
                int oriX = currentBoard.getCurrentX();
                int oriY = currentBoard.getCurrentY();
                
                for (int i = 0; i < xMoves.length; i++){
                    if (currentBoard.move(oriX + xMoves[i], oriY + yMoves[i])){
                        // move() checked if a move is valid. Now, return the knight's position after move()
                        currentBoard.board[oriX + xMoves[i]][oriY + yMoves[i]] = -1;
                        currentBoard.setCurrentX(oriX);
                        currentBoard.setCurrentY(oriY);
                        currentBoard.setMoveCount(currentBoard.getMoveCount() - 1);
                        
                        newMove = currentBoard.copyBoard();
                        newMove.move(oriX + xMoves[i], oriY + yMoves[i]);
                        candidates.push(newMove);
                    }
                }
            }
        }
        return currentBoard;
    }

    public static void main(String[] args) {
        int n = 8;
        if (args.length == 1) {
            n = Integer.parseInt(args[0].trim());
            if (n < 3 || n > 8) {
                System.out.println("Incorrect parameter (n must be >= 3 and <= 8)");
                System.exit(-1);
            }
        }
        long startTime = System.nanoTime();
        KnightBoard winner = KnightTour.tour(n);
        long endTime = System.nanoTime();
        double delta = (endTime - startTime) / 1e6;
        System.out.println("\nPossible Knight Tour with max #squares visited in this ("+n+"x" +n+") board:");
        winner.printChessBoard();
        System.out.println("\n(Time to find this solution = " + delta + " milliseconds)");
    }
}
