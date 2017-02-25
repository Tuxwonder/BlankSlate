/**
Copyright 2013 Luciano Zu project Ardulink http://www.ardulink.org/

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

@author Luciano Zu
*/

package test_package;

import java.util.List;
import java.util.Set;

import org.zu.ardulink.Link;
import org.zu.ardulink.connection.usb.DigisparkUSBConnection;
import org.zu.ardulink.protocol.IProtocol;
import org.zu.ardulink.protocol.ProtocolHandler;
import org.zu.ardulink.protocol.SimpleBinaryProtocol;

public class BlinkLED {

	public static void main(String[] args) {
		try {
			Link link = Link.getDefaultInstance();
			link = getDigisparkConnection(); // Comment this row if you use just the default connection

			List<String> portList = link.getPortList();
			if(portList != null && portList.size() > 0) {
				String port = portList.get(0);
				System.out.println("Connecting on port: " + port);
				boolean connected = link.connect(port);
				System.out.println("Connected:" + connected);
				Thread.sleep(2000);
				int power = IProtocol.HIGH;
				while(true) {
					System.out.println("Send power:" + power);
					link.sendPowerPinSwitch(2, power);
					if(power == IProtocol.HIGH) {
						power = IProtocol.LOW;
					} else {
						power = IProtocol.HIGH;
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

	private static Link getDigisparkConnection() {
		Set<String> protocolNames = ProtocolHandler.getInstalledProtocolImplementationNames();
		SimpleBinaryProtocol protocol = new SimpleBinaryProtocol();
		if(!protocolNames.contains(SimpleBinaryProtocol.NAME)) {
			ProtocolHandler.installProtocolImplementation(protocol);
		}
		return Link.createInstance("digisparkConnection", SimpleBinaryProtocol.NAME, new DigisparkUSBConnection("digisparkConnection", protocol.getIncomingMessageDivider()));
	}

}