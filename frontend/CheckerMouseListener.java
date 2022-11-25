package frontend;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import common.PositionData;

public class CheckerMouseListener implements MouseListener {
	private PositionData pos;
	private GameControl gc;

	public CheckerMouseListener(GameControl gc, int x, int y) {
		this.pos = new PositionData(x, y);
		this.gc = gc;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		gc.sendCheckerClickedToServer(pos);
	}

}
