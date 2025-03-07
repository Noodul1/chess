// name: Jay Vedamony
// date: 03/06/2025
// class contains methods to get potential moves for chess pieces
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;

//you will need to implement two functions in this file.
public class Piece {
    private final boolean color;
    private BufferedImage img;
    // arraylist for diagonal moves
    public Piece(boolean isWhite, String img_file) {
        this.color = isWhite;
        try {
            if (this.img == null) {
              this.img = ImageIO.read(getClass().getResource(img_file));
            }
          } catch (IOException e) {
            System.out.println("File not found: " + e.getMessage());
          }
    }
    

    
    public boolean getColor() {
        return color;
    }
    
    public Image getImage() {
        return img;
    }
    
    public void draw(Graphics g, Square currentSquare) {
        int x = currentSquare.getX();
        int y = currentSquare.getY();
        
        g.drawImage(this.img, x, y, null);
    }


    
    // TO BE IMPLEMENTED!
    //return a list of every square that is "controlled" by this piece. A square is controlled
    //if the piece capture into it legally.
    //pre-condition: start exists on board
    //post-condition: returns ArrayList with every possible square the pawn could move to
    public ArrayList<Square> getControlledSquares(Square[][] board, Square start) {
      Piece thisPiece = start.getOccupyingPiece();
      int r = start.getRow();
      int c = start.getCol();
      ArrayList<Square> moves = new ArrayList<Square>();
      Square currSquare = board[r][c];
      if(thisPiece.getColor() && r < 7){
        r++;
        currSquare = board[r][c];
        if(!currSquare.isOccupied()){
          moves.add(currSquare);
        }
      }
      if(!thisPiece.getColor() && r > 0){
        r--;
        currSquare = board[r][c];
        if(!currSquare.isOccupied()){
          moves.add(currSquare);
          if(start.getRow() == 6){
            r--;
            currSquare = board[r][c];
            if(!currSquare.isOccupied()){
              moves.add(currSquare);
            }
          }
        }
      }
      // capturing, diagonal left movement
      r = start.getRow();
      c = start.getCol();
      currSquare = board[r][c];
      if(thisPiece.getColor() && r < 7 && c > 0){
        r++;
        c--;
        currSquare = board[r][c];
        if(currSquare.isOccupied()){
          moves.add(currSquare);
        }
      }
      if(!thisPiece.getColor() && r > 0 && c > 0){
        r--;
        c--;
        currSquare = board[r][c];
        if(currSquare.isOccupied()){
          moves.add(currSquare);
        }
      }
      // capturing, diagonal right movement
      r = start.getRow();
      c = start.getCol();
      currSquare = board[r][c];
      if(thisPiece.getColor() && r < 7 && c < 7){
        r++;
        c++;
        currSquare = board[r][c];
        if(currSquare.isOccupied()){
          moves.add(currSquare);
        }
      }
      if(!thisPiece.getColor() && r > 0 && c < 7){
        r--;
        c++;
        currSquare = board[r][c];
        if(currSquare.isOccupied()){
          moves.add(currSquare);
        }
      }
      return moves;
      
    }
    
    //TO BE IMPLEMENTED!
    //implement the move function here
    //it's up to you how the piece moves, but at the very least the rules should be logical and it should never move off the board!
    //returns an arraylist of squares which are legal to move to
    //please note that your piece must have some sort of logic. Just being able to move to every square on the board is not
    //going to score any points.

    // pre-condition: start exists on b
    // post-condition: returns arraylist of potential legal moves for the pawn based on the square it was on

    public ArrayList<Square> getLegalMoves(Board b, Square start){
      ArrayList<Square> moves = new ArrayList<Square>();
   
      Piece thisPiece = start.getOccupyingPiece();
      int r = start.getRow();
      int c = start.getCol();
      Square currSq = b.getSquareArray()[r][c];
      // first move and moving forward, white
      if(thisPiece.getColor() && r < 7){
        r++;
        currSq = b.getSquareArray()[r][c];
        if(!currSq.isOccupied()){
          moves.add(currSq);
          if(start.getRow() == 1){
            r++;
            currSq = b.getSquareArray()[r][c];
            if(!currSq.isOccupied()){
              moves.add(currSq);
            }
          }
        }
      }
      // first move and moving forward, black
      if(!thisPiece.getColor() && r > 0){
        r--;
        currSq = b.getSquareArray()[r][c];
        if(!currSq.isOccupied()){
          moves.add(currSq);
          if(start.getRow() == 6){
            r--;
            currSq = b.getSquareArray()[r][c];
            if(!currSq.isOccupied()){
              moves.add(currSq);
            }
          }
        }
      }
      //capturing, diagonal left movement
      r = start.getRow();
      c = start.getCol();
      currSq = b.getSquareArray()[r][c];
      if(thisPiece.getColor() && r < 7 && c > 0){
        r++;
        c--;
        currSq = b.getSquareArray()[r][c];
        if(currSq.isOccupied() && !currSq.getOccupyingPiece().getColor()){
          moves.add(currSq);

        }
      }
      if(!thisPiece.getColor() && r > 0 && c > 0){
        r--;
        c--;
        currSq = b.getSquareArray()[r][c];
        if(currSq.isOccupied() && currSq.getOccupyingPiece().getColor()){
          moves.add(currSq);

        }
      }
      // capturing, diagonal right movement
      r = start.getRow();
      c = start.getCol();
      currSq = b.getSquareArray()[r][c];
      if(thisPiece.getColor() && r < 7 && c < 7){
        r++;
        c++;
        currSq = b.getSquareArray()[r][c];
        if(currSq.isOccupied() && !currSq.getOccupyingPiece().getColor()){
          moves.add(currSq);

        }
      }
      if(!thisPiece.getColor() && r > 0 && c < 7){
        r--;
        c++;
        currSq = b.getSquareArray()[r][c];
        if(currSq.isOccupied() && currSq.getOccupyingPiece().getColor()){
          moves.add(currSq);

        }
      }

    return moves; 
    }
}