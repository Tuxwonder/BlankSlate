package test_package;

import java.io.IOException;
import java.awt.Point;

public class JJTest {
	public static void main(String[] args) throws IOException
	{
		WhiteboardSim JJ = new WhiteboardSim(4);
		
		
		JJ.drawLine(new Point(30,70), new Point(350,270));
		
		
		JJ.drawArc(new Point(500,220), 200, 0, Math.PI*5/4);
		JJ.drawArc(new Point(200,300), 40, 3, 5);
		
		
		for(int i = 0; i < 300; i += 10)
			JJ.drawPoint(new Point(700 - i, 50 + i));
	}
}
