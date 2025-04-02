// name: Jay Vedamony
// date: 03/06/2025
// class contains methods to set up chess board and move pieces

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.swing.*;

//You will be implmenting a part of a function and a whole function in this document. Please follow the directions for the 
//suggested order of completion that should make testing easier.
@SuppressWarnings("serial")
public class Board extends JPanel implements MouseListener, MouseMotionListener {
	// Resource location constants for piece images
    private static final String RESOURCES_WBISHOP_PNG = "wbishop.png";
	private static final String RESOURCES_BBISHOP_PNG = "bbishop.png";
	private static final String RESOURCES_WKNIGHT_PNG = "wknight.png";
	private static final String RESOURCES_BKNIGHT_PNG = "bknight.png";
	private static final String RESOURCES_WROOK_PNG = "wrook.png";
	private static final String RESOURCES_BROOK_PNG = "brook.png";
	private static final String RESOURCES_WKING_PNG = "wking.png";
	private static final String RESOURCES_BKING_PNG = "bking.png";
	private static final String RESOURCES_BQUEEN_PNG = "bqueen.png";
	private static final String RESOURCES_WQUEEN_PNG = "wqueen.png";
	private static final String RESOURCES_WPAWN_PNG = "wpawn.png";
	private static final String RESOURCES_BPAWN_PNG = "bpawn.png";
	
	// Logical and graphical representations of board
	private final Square[][] board;
    private final GameWindow g;
 
    //contains true if it's white's turn.
    private boolean whiteTurn;

    //if the player is currently dragging a piece this variable contains it.
    private Piece currPiece;
    private Square fromMoveSquare;
    private Square sq;
    
    //used to keep track of the x/y coordinates of the mouse.
    private int currX;
    private int currY;
    

    
    public Board(GameWindow g) {
        this.g = g;
        board = new Square[8][8];
        setLayout(new GridLayout(8, 8, 0, 0));

        this.addMouseListener(this);
        this.addMouseMotionListener(this);

        //TO BE IMPLEMENTED FIRST
     
      for (int i = 0; i < 8; i++){
        for(int j = 0; j < 8; j++){
            if((i % 2 == 0 && j % 2 == 0) || (i%2 != 0 && j%2 != 0)){
                board[i][j]= new Square(this, true, i,j);
            } else{
                board[i][j]= new Square(this, false, i,j);
            }
            this.add(board[i][j]);
        }
      }  
//        	populate the board with squares here. Note that the board is composed of 64 squares alternating from 
//        	white to black.

        initializePieces();

        this.setPreferredSize(new Dimension(400, 400));
        this.setMaximumSize(new Dimension(400, 400));
        this.setMinimumSize(this.getPreferredSize());
        this.setSize(new Dimension(400, 400));

        whiteTurn = true;

    }

    
	//set up the board such that the black pieces are on one side and the white pieces are on the other.
	//since we only have one kind of piece for now you need only set the same number of pieces on either side.
	//it's up to you how you wish to arrange your pieces.
    private void initializePieces() {
        // white pieces
    	board[7][0].put(new Rook(true, RESOURCES_WROOK_PNG));
        board[7][1].put(new Knight(true, RESOURCES_WKNIGHT_PNG));
        board[7][2].put(new Bishop(true, RESOURCES_WBISHOP_PNG));
        board[7][3].put(new Queen(true, RESOURCES_WQUEEN_PNG));
    	board[7][4].put(new King(true, RESOURCES_WKING_PNG));
        board[7][5].put(new Bishop(true, RESOURCES_WBISHOP_PNG));
        board[7][6].put(new Knight(true, RESOURCES_WKNIGHT_PNG));
        board[7][7].put(new Rook(true, RESOURCES_WROOK_PNG));

        for(int i = 0; i < 8; i++){
            board[6][i].put(new Pawn(true, RESOURCES_WPAWN_PNG));
        }
        // black pieces
        board[0][0].put(new Rook(false, RESOURCES_BROOK_PNG));
        board[0][1].put(new Knight(false, RESOURCES_BKNIGHT_PNG));
        board[0][2].put(new Bishop(false, RESOURCES_BBISHOP_PNG));
        board[0][3].put(new Queen(false, RESOURCES_BQUEEN_PNG));
    	board[0][4].put(new King(false, RESOURCES_BKING_PNG));
        board[0][5].put(new Bishop(false, RESOURCES_BBISHOP_PNG));
        board[0][6].put(new Knight(false, RESOURCES_BKNIGHT_PNG));
        board[0][7].put(new Rook(false, RESOURCES_BROOK_PNG));
        for(int i = 0; i < 8; i++){
            board[1][i].put(new Pawn(false, RESOURCES_BPAWN_PNG));
        }
        
    }

    public Square[][] getSquareArray() {
        return this.board;
    }

    public boolean getTurn() {
        return whiteTurn;
    }

    public void setCurrPiece(Piece p) {
        this.currPiece = p;
    }

    public Piece getCurrPiece() {
        return this.currPiece;
    }

    //precondition - the board is initialized and contains a king of either color. The boolean kingColor corresponds to the color of the king we wish to know the status of.
    //postcondition - returns true of the king is in check and false otherwise.
	public boolean isInCheck(boolean kingColor){
        ArrayList<Square> squares = new ArrayList<Square>();
		for(Square [] row: board) {
            for(Square s: row) {
                if((s.getOccupyingPiece() != null) && (s.getOccupyingPiece().getColor() != kingColor)){
                    squares = s.getOccupyingPiece().getControlledSquares(board, s);
                    for(int i = 0; i < squares.size(); i ++){
                        if(squares.get(i).getOccupyingPiece() instanceof King && squares.get(i).getOccupyingPiece().getColor() == kingColor){
                            return true;
                        }
                    }
                }
            
            }
        }
        return false;
    }
    

    @Override
    public void paintComponent(Graphics g) {
     
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                Square sq = board[x][y];
                if(sq == fromMoveSquare)
                	 sq.setBorder(BorderFactory.createLineBorder(Color.blue));
                sq.paintComponent(g);
                
            }
        }
    	if (currPiece != null) {
            if ((currPiece.getColor() && whiteTurn)
                    || (!currPiece.getColor()&& !whiteTurn)) {
                final Image img = currPiece.getImage();
                g.drawImage(img, currX, currY, null);
            }
        }
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
        currX = e.getX();
        currY = e.getY();

        sq = (Square) this.getComponentAt(new Point(e.getX(), e.getY()));

        if (sq.isOccupied()) {
            
            currPiece = sq.getOccupyingPiece();
           // System.out.println(currPiece);
            fromMoveSquare = sq;
            if (!currPiece.getColor() && whiteTurn)
                return;
            if (currPiece.getColor() && !whiteTurn)
                return;
            sq.setDisplay(false);
        }
        repaint();
    }

    //TO BE IMPLEMENTED!
    //should move the piece to the desired location only if this is a legal move.
    //use the pieces "legal move" function to determine if this move is legal, then complete it by
    //moving the new piece to it's new board location. 
    @Override
    //pre-condition: currPiece is not null
    // post-condition: if the move is valid currPiece moves to endSquare, otherwise it doesn't move
    public void mouseReleased(MouseEvent e) {
        Piece capturedPiece = null;
        boolean moved = false;
        Square endSquare = (Square) this.getComponentAt(new Point(e.getX(), e.getY()));
        //using currPiece
        if(currPiece != null && whiteTurn == currPiece.getColor()){
            ArrayList<Square> legalMoves = currPiece.getLegalMoves(this, fromMoveSquare);
            if(legalMoves.contains(endSquare)){
                capturedPiece = endSquare.getOccupyingPiece();
                endSquare.put(currPiece);
                moved = true;
                fromMoveSquare.removePiece(); 
                // undo move if piece puts its own king in check
                if(isInCheck(whiteTurn)){
                    fromMoveSquare.put(currPiece);
                    endSquare.removePiece();
                    if(capturedPiece != null){
                        endSquare.put(capturedPiece);
                    }
                    if(moved){
                        whiteTurn = !whiteTurn;
                    }
            }
                whiteTurn = !whiteTurn;
            }
        }
    
    for(Square [] row: board) {
        for(Square s: row) {
            s.setBorder(null);
        
        }
    }
    fromMoveSquare.setDisplay(true);
    currPiece = null;
    repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        currX = e.getX() - 24;
        currY = e.getY() - 24;
    // highlight all the squares that are legal to move to
    if(currPiece!= null) {
        for(Square s: currPiece.getLegalMoves(this, fromMoveSquare)) {
        s.setBorder(BorderFactory.createLineBorder(Color.MAGENTA));
        }
    
    }
        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

}