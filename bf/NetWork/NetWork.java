package NetWork;

import java.awt.Frame;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.Socket;

import ui.MainFrame;

public class NetWork {
	public NetWork() {
		this (0,0);
		// TODO Auto-generated constructor stub
	}
	public NetWork(int i,int j){
		
	}
	Socket socket;
	static PrintWriter writer;
	public BufferedReader reader;
	
	public static void sendToServer(String sendString){
		try {
			writer.println(sendString);
			writer.flush();
			System.out.println("send");
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
	}
	public void setUp(){
		
		try {
			socket = new Socket("127.0.0.1", 6540);
			writer = new PrintWriter(socket.getOutputStream());
			InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream());
			reader = new BufferedReader(inputStreamReader);
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		
		
	}

}
