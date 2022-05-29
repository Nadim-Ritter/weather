package ch.hslu.swde.wda.app;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ch.hslu.swde.wda.rmi.api.WetterdatenRMI;
import ch.hslu.swde.wda.ui.UI;

public class WetterdatenApp {

	private static Logger loggerWetterdatenApp = LogManager.getLogger(WetterdatenApp.class);

	public static void main(String[] args) {

		String ipRMIServer = "localhost";
		int rmiPort = 1099;

		if (System.getSecurityManager() == null) {

			System.setProperty("java.security.policy", "checker.policy");
			System.setSecurityManager(new SecurityManager());
		}

		try {

			String url = "rmi://" + ipRMIServer + ":" + rmiPort + "/" + WetterdatenRMI.RMI_WDA;


			/* UI-Komponente */
			UI ui = new UI();

			ui.execute();

		} catch (Exception e) {
			loggerWetterdatenApp.error(">> Fehler: ", e);
			System.out.println("Die Applikation wurde aus einem unbekannten Grund heruntergefahren.");
			e.printStackTrace();
		}
	}

}
