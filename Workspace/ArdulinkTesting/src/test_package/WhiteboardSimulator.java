package test_package;

import java.awt.Point;
import plotter.Plotter;
import plotter.Polyline;

/*
 * NOTES
 *
 * Motor has 200 steps in 1 revolution
 * Which translates to 1.8 degrees per step
 * If using a 3" wheel, circumference is 9.425"
 * Therefore, each step will theoretically move JJ 0.047"
 * That's about 3/64ths of an inch
 * Let's pretend a dot of a marker is 3/32"
 * So that's two steps of the motor
 *
 * Our white board is advertised as 3'x 2'
 * That's probably an exterior dimension
 * Not of the actual writable board
 * Let's take a half inch off of each side for the frame
 * So our white board space is now 35" x 23"
 * We'll consider the dot of a marker as 3/32"
 * If we consider the size of our white board space 
 * and our step-length which is 3/64"
 * Our white board is 746.666 steps x 490.666 steps
 * Which we'll round down to 746 x 490 steps
 * This is the grid for the resting locations of JJ
 * Meaning pen_x and pen_y will remain in the bounds 
 * of [0,746] and [0,490] respectively
 *
 * Onto the image
 * Our resolution will be 2 pixels/step
 * Which means each dot will be a "circle"
 * that's 4 pixels in diameter
 * (Looks like a square with edges cut off)
 *
*/

public class WhiteboardSimulator {
	
	private static final int WB_WIDTH = 749; //Change to 749 when ready
	private static final int WB_HEIGHT = 490; //Change to 490 when ready
	private static final int WB_OFFSET_X = 10;
	private static final int WB_OFFSET_Y = 10;	
	private static final double PLOTTER_SCALE = 0.6;
	
	private float pen_x;
	private float pen_y;	
	private boolean pen_down;
	private Polyline line;
	private Plotter plot = new Plotter();
	
	public WhiteboardSimulator()
	{
		pen_x = 0;
		pen_y = 0;
		pen_down = false;
		
		line = new Polyline("black", 10);
		line.addPoint(new Point(5,5));
		line.addPoint(new Point((int) (WB_WIDTH*PLOTTER_SCALE + WB_OFFSET_X),5));
		line.addPoint(new Point((int) (WB_WIDTH*PLOTTER_SCALE + WB_OFFSET_X),(int) (WB_HEIGHT*PLOTTER_SCALE + WB_OFFSET_Y)));
		line.addPoint(new Point(5,(int) (WB_HEIGHT*PLOTTER_SCALE + WB_OFFSET_Y)));
		line.addPoint(new Point(5,5));
		plot.plot(line);
	}
	
	public void returnToZero()
	{
		pen_x = 0;
		pen_y = 0;
		if(pen_down)
			addPoint();
	}
	
	public void translateBy(int x, int y)
		{translateBy((double) x,(double) y);}
		
	public void translateBy(double x, double y)
	{
		pen_x += x;
		pen_y += y;
		hitLimit();
		if(pen_down)
			addPoint();
	}
	
	public void penUp()
	{
		pen_down = false;
		plot.plot(line);
	}
	
	public void penDown()
	{
		pen_down = true;
		line = new Polyline("black",2);
		addPoint();
	}
	
	private void addPoint()
	{
		line.addPoint(new Point(
				(int) (pen_x*PLOTTER_SCALE + WB_OFFSET_X),
				(int) (pen_y*PLOTTER_SCALE + WB_OFFSET_Y)));
	}

	private void hitLimit()
	{
		if(pen_x >= WB_WIDTH)
			pen_x = WB_WIDTH;
		
		if(pen_x <= 0)
			pen_x = 0;
			
		if(pen_y >= WB_HEIGHT)
			pen_y = WB_HEIGHT;
		
		if(pen_y <= 0)
			pen_y = 0;
	}
}
