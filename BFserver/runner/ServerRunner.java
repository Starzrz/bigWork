package runner;

import NetWorkServer.Server;
import rmi.RemoteHelper;

public class ServerRunner {
	
	public ServerRunner() {
		new RemoteHelper();
	}
	
	public static void main(String[] args) {
		new ServerRunner();
		//Thread netThread = new Thread(new Server());
		//netThread.start();
		
	}
}
