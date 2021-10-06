
public class MoveFigure {

	private Gui environment; // Graphical user interface

	/* =========================== */
	public MoveFigure(Gui env) {
		/* =========================== */
		environment = env;
	}

	/* ============================================ */
	public int moveLeft(Icon[] figs, int numFigures, int fignum, int step)
	/* ============================================ */
	/*
	 * Move the graphical object specified by the second argument to the left. Return true
	 * if the graphical object could move.
	 */
	{
		Position curr, next;
		int newx;

		curr = figs[fignum].getOffset();
		if (curr.xCoord() < step)
			return -1; // Graphical object reached left border
		else
			newx = curr.xCoord() - step;
		next = new Position(newx, curr.yCoord());

		// Verify that graphical obejcts do not overlap
		figs[fignum].setOffset(next);
		for (int i = 0; i < numFigures; ++i)
			if ((i != fignum) && (figs[fignum].intersects(figs[i]))) {
				figs[fignum].setOffset(curr);
				return i;
			}

		// Move graphical object to its new Position
		figs[fignum].setOffset(curr);
		environment.eraseFigure(figs[fignum]);
		figs[fignum].setOffset(next);
		environment.drawFigure(figs[fignum]);
		return -2;
	}

	/* ============================================ */
	public int moveRight(Icon[] figs, int numFigures, int fignum, int step)
	/* ============================================ */
	/*
	 * Move the graphical object specified by the second argument to the right. Return true
	 * if the graphical obejct could move.
	 */
	{
		Position curr, next;
		int newx;

		curr = figs[fignum].getOffset();
		if (curr.xCoord() > (environment.displayWidth() - figs[fignum].getWidth() - step))
			return -1; // Graphical object reached right border
		else
			newx = curr.xCoord() + step;
		next = new Position(newx, curr.yCoord());

		// Check that graphical objects do not overlap
		figs[fignum].setOffset(next);
		for (int i = 0; i < numFigures; ++i)
			if ((i != fignum) && (figs[fignum].intersects(figs[i]))) {
				figs[fignum].setOffset(curr);
				return i;
			}

		// Move graphical object to its new Position
		figs[fignum].setOffset(curr);
		environment.eraseFigure(figs[fignum]);
		figs[fignum].setOffset(next);
		environment.drawFigure(figs[fignum]);
		return -2;
	}

	/* ============================================ */
	public int moveDown(Icon[] figs, int numFigures, int fignum, int step)
	/* ============================================ */
	/*
	 * Move the graphical object specified by the second argument t the right. Return true
	 * if the graphical object could move.
	 */
	{
		Position curr, next;
		int newy;

		curr = figs[fignum].getOffset();
		if (curr.yCoord() > (environment.displayHeight() - figs[fignum].getHeight() - step))
			return -1; // Graphical object reached bottom border
		else
			newy = curr.yCoord() + step;
		next = new Position(curr.xCoord(), newy);

		// Check that graphical objects do not overlap
		figs[fignum].setOffset(next);
		for (int i = 0; i < numFigures; ++i)
			if ((i != fignum) && (figs[fignum].intersects(figs[i]))) {
				figs[fignum].setOffset(curr);
				return i;
			}

		// Move graphical object to its new Position
		figs[fignum].setOffset(curr);
		environment.eraseFigure(figs[fignum]);
		figs[fignum].setOffset(next);
		environment.drawFigure(figs[fignum]);
		return -2;
	}

	/* ============================================ */
	public int moveUp(Icon[] figs, int numFigures, int fignum, int step)
	/* ============================================ */
	/*
	 * Move the graphical object specified by the second argument to the right. Return true
	 * if the graphical object could move
	 */
	{
		Position curr, next;
		int newy;

		curr = figs[fignum].getOffset();
		if (curr.yCoord() < step)
			return -1; // Graphical object reached top border
		else
			newy = curr.yCoord() - step;
		next = new Position(curr.xCoord(), newy);

		// Check that graphical objects do not overlap
		figs[fignum].setOffset(next);
		for (int i = 0; i < numFigures; ++i)
			if ((i != fignum) && (figs[fignum].intersects(figs[i]))) {
				figs[fignum].setOffset(curr);
				return i;
			}

		// Draw graphical object in its new Position
		figs[fignum].setOffset(curr);
		environment.eraseFigure(figs[fignum]);
		figs[fignum].setOffset(next);
		environment.drawFigure(figs[fignum]);
		return -2;
	}
}
