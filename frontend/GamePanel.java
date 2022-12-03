package frontend;

import javax.imageio.ImageIO;
import javax.swing.*;

import common.AvailableMoves;
import common.Board;
import common.Pawn;
import common.PieceColor;
import common.PieceData;
import common.PositionData;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class GamePanel extends JPanel {

	private JPanel squares[][] = new JPanel[10][10];
	private JLabel alphaLabel[] = new JLabel[8];
	private String alpha[] = { "A", "B", "C", "D", "E", "F", "G", "H" };
	private String num[] = { "1", "2", "3", "4", "5", "6", "7", "8" };
	private BufferedImage bPawnImages[] = new BufferedImage[8];
	private BufferedImage wPawnImages[] = new BufferedImage[8];
	private JLabel bPawns[] = new JLabel[8];
	private JLabel wPawns[] = new JLabel[8];
	private GameControl gc;
	private JPanel center = new JPanel(new FlowLayout());

	// TODO turn the piece images into static assets

	// Constructor for the game panel.
	public GamePanel(GameControl gc) {
		this.gc = gc;
		System.out.println(gc);
		// Initializing Panels
		JPanel display = new JPanel(new BorderLayout());
		JPanel top = new JPanel(new FlowLayout());
		JPanel bottom = new JPanel(new GridLayout(2, 1));
		JPanel logoutPanel = new JPanel(new FlowLayout());

		// Creating Buttons and labels
		JLabel p1 = new JLabel("Player 1", JLabel.CENTER); // Can get username here
		JLabel p2 = new JLabel("Player 2", JLabel.CENTER); // Can get username here
		JButton logout = new JButton("Log Out");
		logout.addActionListener(gc);

		// Create the logout button.
		top.add(p1);
		Board board = new Board();
		drawBoard(new Board());
		bottom.add(p2);
		logoutPanel.add(logout);
		bottom.add(logoutPanel);
		display.add(top, BorderLayout.NORTH);
		display.add(center, BorderLayout.CENTER);
		display.add(bottom, BorderLayout.SOUTH);

		// FIXME remove test available moves
		ArrayList<PositionData> moves = new ArrayList<PositionData>();
		moves.add(new PositionData(3, 5));
		moves.add(new PositionData(4, 5));
		gc.setAvailableMoves(new AvailableMoves(moves));
		this.drawAvailableMoves(new AvailableMoves(moves));
		
		this.add(display);
	}

	public void drawBoard(Board board) {
		JPanel boardGrid = new JPanel(new GridLayout(10, 10));
		boardGrid.setPreferredSize(new Dimension(750, 750));

		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				// Initializing loop variables
				squares[i][j] = new JPanel(new BorderLayout());

				JPanel checker = squares[i][j];
				if (i > 0 && i < 9 && j > 0 && i < 9) {
					checker.addMouseListener(new CheckerMouseListener(this.gc, j, i));
				}
				AvailableMoves availableMoves = gc.getAvailableMoves();

				int row = j - 1;
				int col = i - 1;
				Color dark = new Color(102, 76, 131);
				Color light = new Color(179, 160, 200);


				// Setting light board panels
				if ((i + j) % 2 == 0 && i > 0 && j > 0 && i < 9 && j < 9) {
					checker.setBackground(light);
				}
				// Creating dark board panels
				else if ((i + j) % 2 == 1 && i > 0 && j > 0 && i < 9 && j < 9) {
					checker.setBackground(dark);
				}
				// setting row identifiers
				else if ((i == 0 && j > 0 && j < 9) || (i == 9 && j > 0 && j < 9)) {
					alphaLabel[row] = new JLabel(alpha[row], JLabel.CENTER);
					alphaLabel[row].setFont(new Font("Monaco", Font.PLAIN, 20));
					if (i == 0)
						checker.add(alphaLabel[row], BorderLayout.SOUTH);
					else
						checker.add(alphaLabel[row], BorderLayout.NORTH);
				}
				// setting col identifiers
				else if ((j == 0 && i > 0 && i < 9) || (j == 9 && i > 0 && i < 9)) {
					alphaLabel[col] = new JLabel(num[col], JLabel.CENTER);
					alphaLabel[col].setFont(new Font("Monaco", Font.PLAIN, 20));
					if (j == 0)
						checker.add(alphaLabel[col], BorderLayout.EAST);
					else
						checker.add(alphaLabel[col], BorderLayout.WEST);
				}
				boardGrid.add(checker);
			}
		}

		for (int x = 0; x < 8; x++) {
			for (int y = 0; y < 8; y++) {
				PieceData piece = board.getPiece(x, y);

				if (piece == null) {
					continue;
				}

				try {
					squares[y + 1][x + 1].add(new JLabel(new ImageIcon(piece.getImage())), BorderLayout.CENTER);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		center.add(boardGrid);
	}
	
	public void drawAvailableMoves(AvailableMoves moves) {
		for (PositionData pos : moves.getMoves()) {
			this.squares[pos.x+1][pos.y+1].setBackground(Color.GREEN);
		}
		
	}
}
