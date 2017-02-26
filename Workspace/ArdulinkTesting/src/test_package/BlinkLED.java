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
import java.util.Scanner;
import java.util.Set;

import org.zu.ardulink.Link;
import org.zu.ardulink.protocol.IProtocol;
import org.zu.ardulink.protocol.ProtocolHandler;
import org.zu.ardulink.protocol.SimpleBinaryProtocol;

public class BlinkLED {

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
				
				String message = "";
				Scanner in = new Scanner(System.in);
				while(!message.equals("quit")) {
					message = in.next();
					link.sendCustomMessage(message);
					System.out.println("Message Sent: " + message);
					
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
