package frontend;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GamePanel extends JPanel {

	JPanel squares[][] = new JPanel[10][10];
	JLabel alphaLabel[] = new JLabel[8];
	String alpha[] = { "A", "B", "C", "D", "E", "F", "G", "H" };
	String num[] = { "1", "2", "3", "4", "5", "6", "7", "8" };
	BufferedImage bPawnImages[] = new BufferedImage[8];
	BufferedImage wPawnImages[] = new BufferedImage[8];
	JLabel bPawns[] = new JLabel[8];
	JLabel wPawns[] = new JLabel[8];

	// Constructor for the initial panel.
	public GamePanel(GameControl gc) {
		// Initializing variables
		JPanel display = new JPanel(new BorderLayout());
		JLabel p1 = new JLabel("Player 1", JLabel.CENTER); // Can get username here
		JLabel p2 = new JLabel("Player 2", JLabel.CENTER); // Can get username here
		JPanel board = new JPanel(new GridLayout(10, 10));
		board.setPreferredSize(new Dimension(750, 750));

		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				// Initializing loop varibales
				squares[i][j] = new JPanel(new BorderLayout());
				int row = j - 1;
				int col = i - 1;
				// Setting light board panels
				if ((i + j) % 2 == 0 && i > 0 && j > 0 && i < 9 && j < 9) {
					squares[i][j].setBackground(Color.WHITE);
				}
				// Creating dark board panels
				else if ((i + j) % 2 == 1 && i > 0 && j > 0 && i < 9 && j < 9) {
					squares[i][j].setBackground(Color.GRAY);
				} else if ((i == 0 && j > 0 && j < 9) || (i == 9 && j > 0 && j < 9)) {
					alphaLabel[row] = new JLabel(alpha[row], JLabel.CENTER);
					if (i == 0)
						squares[i][j].add(alphaLabel[row], BorderLayout.SOUTH);
					else
						squares[i][j].add(alphaLabel[row], BorderLayout.NORTH);
				} else if ((j == 0 && i > 0 && i < 9) || (j == 9 && i > 0 && i < 9)) {
					alphaLabel[col] = new JLabel(num[col], JLabel.CENTER);
					if (j == 0)
						squares[i][j].add(alphaLabel[col], BorderLayout.EAST);
					else
						squares[i][j].add(alphaLabel[col], BorderLayout.WEST);
				}
				board.add(squares[i][j]);
			}
		}
		// Creating variables

		BufferedImage blackRook, blackKnight, blackBishop, blackQueen, blackKing, blackPawn;
		BufferedImage whiteRook, whiteKnight, whiteBishop, whiteQueen, whiteKing, whitePawn;
		try {
			// Reading the black piece files
			blackRook = ImageIO.read(new File("assets/bRook.png"));
			blackKnight = ImageIO.read(new File("assets/bKnight.png"));
			blackBishop = ImageIO.read(new File("assets/bBishop.png"));
			blackQueen = ImageIO.read(new File("assets/bQueen.png"));
			blackKing = ImageIO.read(new File("assets/bKing.png"));
			// creating 8 black pawn pieces
			for (int i = 0; i < 8; i++) {
				bPawnImages[i] = ImageIO.read(new File("assets/bPawn.png"));

			}

			// Reading the white piece files
			whiteRook = ImageIO.read(new File("assets/wRook.png"));
			whiteKnight = ImageIO.read(new File("assets/wKnight.png"));
			whiteBishop = ImageIO.read(new File("assets/wBishop.png"));
			whiteQueen = ImageIO.read(new File("assets/wQueen.png"));
			whiteKing = ImageIO.read(new File("assets/wKing.png"));
			// creating 8 white pawn pieces
			for (int i = 0; i < 8; i++) {
				wPawnImages[i] = ImageIO.read(new File("assets/wPawn.png"));

			}
			// creating labels with white pieces in them
			JLabel bRook1 = new JLabel(new ImageIcon(blackRook));
			JLabel bRook2 = new JLabel(new ImageIcon(blackRook));
			JLabel bKnight1 = new JLabel(new ImageIcon(blackKnight));
			JLabel bKnight2 = new JLabel(new ImageIcon(blackKnight));
			JLabel bBishop1 = new JLabel(new ImageIcon(blackBishop));
			JLabel bBishop2 = new JLabel(new ImageIcon(blackBishop));
			JLabel bQueen = new JLabel(new ImageIcon(blackQueen));
			JLabel bKing = new JLabel(new ImageIcon(blackKing));
			// Creating labels with black pawns in them
			for (int i = 0; i < 8; i++) {
				bPawns[i] = new JLabel(new ImageIcon(bPawnImages[i]));

			}
			// creating labels with images in them
			JLabel wRook1 = new JLabel(new ImageIcon(whiteRook));
			JLabel wRook2 = new JLabel(new ImageIcon(whiteRook));
			JLabel wKnight1 = new JLabel(new ImageIcon(whiteKnight));
			JLabel wKnight2 = new JLabel(new ImageIcon(whiteKnight));
			JLabel wBishop1 = new JLabel(new ImageIcon(whiteBishop));
			JLabel wBishop2 = new JLabel(new ImageIcon(whiteBishop));
			JLabel wQueen = new JLabel(new ImageIcon(whiteQueen));
			JLabel wKing = new JLabel(new ImageIcon(whiteKing));
			// Creating labels with white pawns in them
			for (int i = 0; i < 8; i++) {
				wPawns[i] = new JLabel(new ImageIcon(wPawnImages[i]));

			}
			// Adding blacks pieces to board
			// Adding Rooks
			squares[1][1].add(bRook1, BorderLayout.CENTER);
			squares[1][8].add(bRook2, BorderLayout.CENTER);
			// Adding Knights
			squares[1][2].add(bKnight1, BorderLayout.CENTER);
			squares[1][7].add(bKnight2, BorderLayout.CENTER);
			// Adding Bishops
			squares[1][3].add(bBishop1, BorderLayout.CENTER);
			squares[1][6].add(bBishop2, BorderLayout.CENTER);
			// Adding king and queen
			squares[1][4].add(bQueen, BorderLayout.CENTER);
			squares[1][5].add(bKing, BorderLayout.CENTER);
			for (int i = 0; i < 8; i++) {
				squares[2][i + 1].add(bPawns[i], BorderLayout.CENTER);
			}

			// Adding pieces to board
			// Adding Rooks
			squares[8][1].add(wRook1, BorderLayout.CENTER);
			squares[8][8].add(wRook2, BorderLayout.CENTER);
			// Adding Knights
			squares[8][2].add(wKnight1, BorderLayout.CENTER);
			squares[8][7].add(wKnight2, BorderLayout.CENTER);
			// Adding Bishops
			squares[8][3].add(wBishop1, BorderLayout.CENTER);
			squares[8][6].add(wBishop2, BorderLayout.CENTER);
			// Adding king and queen
			squares[8][4].add(wQueen, BorderLayout.CENTER);
			squares[8][5].add(wKing, BorderLayout.CENTER);
			// adding pawns to board panels
			for (int i = 0; i < 8; i++) {
				squares[7][i + 1].add(wPawns[i], BorderLayout.CENTER);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		display.add(p1, BorderLayout.NORTH);
		display.add(board, BorderLayout.CENTER);
		display.add(p2, BorderLayout.SOUTH);
		this.add(display);
	}
}
