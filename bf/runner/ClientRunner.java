package runner;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import NetWork.NetWork;
import rmi.RemoteHelper;
import service.IOService;
import ui.MainFrame;

public class ClientRunner {
	private RemoteHelper remoteHelper;
	static MainFrame mainFrame;
	public ClientRunner() {
		linkToServer();
		initGUI();
	}
	
	private void linkToServer() {
		try {
			remoteHelper = RemoteHelper.getInstance();
			remoteHelper.setRemote(Naming.lookup("rmi://localhost:8888/DataRemoteObject"));
			System.out.println("linked");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
	}
	
	private void initGUI() {
		mainFrame = new MainFrame();
		
	}
	
	public void test(){
		try {
			System.out.println(remoteHelper.getUserService().login("admin", "123456a"));
			System.out.println(remoteHelper.getIOService().writeFile("2", "admin", "testFile"));
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args){
		//NetWork netWork = new NetWork();
		//netWork.setUp();
		ClientRunner cr = new ClientRunner();
		//Thread thread = new Thread(new receive(mainFrame, netWork.reader));
		//thread.run();//cr.test();
	}
}
class receive implements Runnable{

	MainFrame Mframe;
	BufferedReader reader;
	public receive(MainFrame frame,BufferedReader r) {
		Mframe = frame;
		reader =r;
		// TODO Auto-generated constructor stub
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		String mString = "";
		try {
			while ((mString=reader.readLine())!=null) {
				MainFrame.outputArea.setText(mString);
				MainFrame.frame.repaint();
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
