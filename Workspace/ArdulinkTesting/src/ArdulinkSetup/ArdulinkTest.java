
package ArdulinkSetup;

import java.util.List;

import org.zu.ardulink.Link;
import org.zu.ardulink.protocol.IProtocol;

public class ArdulinkTest {
	public static void main(String[] args) {
		try {
			Link link = Link.getDefaultInstance();

			List<String> portList = link.getPortList();
			if(portList != null && portList.size() > 0) {
				
				
				String port = portList.get(0);
				System.out.println("Connecting on port: " + port);
				boolean connected = link.connect(port);
				System.out.println("Connected:" + connected);
				Thread.sleep(2000);
				
				//link.addDigitalReadChangeListener(DigitalReadChangeListener);
				int power = IProtocol.HIGH;
				while(true) {
					System.out.println("Send power:" + power);
					link.sendPowerPinSwitch(13, power);
					if(power == IProtocol.POWER_HIGH) {
						power = IProtocol.POWER_LOW;
					} else {
						power = IProtocol.POWER_HIGH;
					}
					Thread.sleep(2000);
				}
			} else {
				System.out.println("No port found!");
			}
						
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}
