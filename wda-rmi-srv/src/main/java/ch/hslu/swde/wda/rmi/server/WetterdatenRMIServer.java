package ch.hslu.swde.wda.rmi.server;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import ch.hslu.swde.wda.rmi.api.WetterdatenRMI;
import ch.hslu.swde.wda.rmi.impl.WetterdatenRMIImpl;

public class WetterdatenRMIServer {
	public static void main(String[] args) {
		String hostIp = "localhost";
		int rmiPort = 1099;

		System.setProperty("java.rmi.server.hostname", hostIp);

		try {
			WetterdatenRMI remoteObj = new WetterdatenRMIImpl();

			Registry reg = LocateRegistry.createRegistry(rmiPort);

			if(reg != null ) {
				reg.rebind(WetterdatenRMI.RMI_WDA, remoteObj);
				System.out.println("Server is running: " + hostIp + ":" + rmiPort);
				System.out.println("Press Enter to exit");
				new java.util.Scanner(System.in).nextLine();

				reg.unbind(WetterdatenRMI.RMI_WDA);
			} else {
				System.out.println("Fehler passiert!");
			}
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}

		//Parameter 0 sagt --> es wurde ordentlich beendet
		System.exit(0);
	}
	
}
