package svg;

import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import test_package.WhiteboardSim;

public class svgTranslator {

	public static void svgTranslate(File svg, Point scale, Point offset) throws FileNotFoundException {

		WhiteboardSim sim = new WhiteboardSim(2);
		Scanner svgWhole = new Scanner(svg);
		Scanner svgLine;
		String line;
		String type;

		while (svgWhole.hasNextLine()) {

			line = svgWhole.nextLine();
			svgLine = new Scanner(line);

			if (svgLine.hasNext()) {
				type = svgLine.next();

				switch (type) {
				case "<circle":
					drawCircle(sim, line, scale, offset);
				case "<line":
					drawLine(sim, line, scale, offset);
				case "<ellipse":
					drawEllipse(sim, line, scale, offset);
				case "<polyline":
					drawPolyline(sim, line, scale, offset);
				}
			}
			svgLine.close();
		}
		svgWhole.close();
	}

	public static void drawCircle(WhiteboardSim sim, String line, Point scale, Point offset) {
		Scanner scan = new Scanner(line);
		scan.useDelimiter("=");
		
		String cmd;
		String value;
		int cx = 0;
		int cy = 0;
		int r = 0;
		
		while(scan.hasNext())
		{
			cmd = scan.next();
			
			switch (cmd) {
			case "<circle":
				break;
			case "cx":
				value = scan.next();
				cx = Integer.parseInt(value.substring(1, value.length() - 2));
			case "cy":
				value = scan.next();
				cy = Integer.parseInt(value.substring(1, value.length() - 2));
			case "r":
				value = scan.next();
				r = Integer.parseInt(value.substring(1, value.length() - 2));
			}
		}
		
		sim.drawArc(new Point(cx,cy), r, 0, 3.2);
	}

	public static void drawLine(WhiteboardSim sim, String line, Point scale, Point offset) {

	}

	public static void drawEllipse(WhiteboardSim sim, String line, Point scale, Point offset) {

	}

	public static void drawPolyline(WhiteboardSim sim, String line, Point scale, Point offset) {

	}
}
