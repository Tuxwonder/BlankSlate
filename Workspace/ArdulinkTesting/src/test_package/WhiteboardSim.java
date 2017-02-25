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

public class WhiteboardSim {
	
	private static final int WB_WIDTH = 749; //Change to 749 when ready
	private static final int WB_HEIGHT = 490; //Change to 490 when ready
	private static final int PADDING = 10;
	private static final double PLOTTER_SCALE = 0.63;

	private Polyline line;
	private Plotter plot = new Plotter();
	private int penWidth;
	
	public WhiteboardSim(int width)
	{	
		penWidth = width;
		int t = 500; //thickness
		line = new Polyline("black", t*2);
		
		line.addPoint(new Point(
				PADDING - t, 
				PADDING - t));
		line.addPoint(new Point(
				PADDING - t, 
				(int) ((WB_HEIGHT + PADDING*2)*PLOTTER_SCALE + t)));
		line.addPoint(new Point(
				(int) ((WB_WIDTH + PADDING*2)*PLOTTER_SCALE + t),
				(int) ((WB_HEIGHT + PADDING*2)*PLOTTER_SCALE + t)));		
		line.addPoint(new Point(
				(int) ((WB_WIDTH + PADDING*2)*PLOTTER_SCALE + t), 
				PADDING - t));	
		line.addPoint(new Point(
				PADDING - t, 
				PADDING - t));		
		
		plot.plot(line);
	}
	
	private void addPoint(Point point)
	{
		line.addPoint(new Point(
				(int) (point.x*PLOTTER_SCALE + PADDING),
				(int) (point.y*PLOTTER_SCALE + PADDING)));
	}
	
	public void drawLine(Point start, Point end)
	{
		line = new Polyline("black", (int) (penWidth*PLOTTER_SCALE));

		addPoint(start);
		addPoint(end);
		
		plot.plot(line);		
	}
	
	public void drawPoint(Point point)
	{
		drawLine(point, point);
	}
	
	public void drawArc(Point center, int radius, double startAngle, double endAngle)
	{
		if(startAngle > endAngle)
			return;

		double i = startAngle; //Incrementor
		double x = center.x + radius*Math.cos(startAngle);
		double y = center.y + radius*Math.sin(startAngle);
		double dTheta = 0.02; //Change in theta
		
		line = new Polyline("black", (int) (penWidth*PLOTTER_SCALE));		
		addPoint(new Point((int) x,(int) y));
		
		do
		{
			i += dTheta;
			x += -dTheta*radius*Math.sin(i);
			y += dTheta*radius*Math.cos(i);
			
			addPoint(new Point((int) x,(int) y));
		} 
		while(i < endAngle);
		
		plot.plot(line);
	}
}
