package NetWorkServer;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JTextArea;

import serviceImpl.ExecuteServiceImpl;
import serviceImpl.IOServiceImpl;


public class Server implements Runnable{
	Socket Socket;
	PrintWriter writer;
	BufferedReader reader;
	static File file;
	static BufferedWriter fileWriter;
	JFrame frame;
	JTextArea textArea;
	public void gui(){
		frame = new JFrame();
		textArea = new JTextArea(20,20);
		frame.getContentPane().add(BorderLayout.CENTER,textArea);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(600, 600);
		frame.setVisible(true);
	}
	/*public static void main(String[] args) {
		Server server = new Server();
		try {
			file =new File("save.txt");
			server.fileWrite("asfasf");
			server.fileWrite("asfagasg");
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}*/
	public void fileWrite(String string){
		try {
			//textArea.append(string);
			//textArea.append("\n");
			frame.repaint();
			fileWriter = new BufferedWriter(new FileWriter(file,true));
			fileWriter.write(string);
			
			fileWriter.newLine();
			fileWriter.flush();
		
			;
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
	}
	public void run() {
		file =new File("save.txt");
		gui();
		try {
			ServerSocket serverSocket = new ServerSocket(6540);
			while(true){
				Socket = serverSocket.accept();
				InputStreamReader inputStreamReader = new InputStreamReader(Socket.getInputStream());
				reader = new BufferedReader(inputStreamReader);
				writer = new PrintWriter(Socket.getOutputStream());
				String messageString = "";
				while((messageString=reader.readLine()).length()>0){
					
					messageString = messageString.substring(0,messageString.length()-1);
					System.out.println(messageString + "message");
					IOServiceImpl impl = new IOServiceImpl();
					String code = impl.readFile("admin", "code");
					System.out.println(code+"code");
					ExecuteServiceImpl executeServiceImpl = new ExecuteServiceImpl();
					String resultString = executeServiceImpl.execute(code, messageString);
					writer.write(resultString);
					textArea.setText(resultString);
					frame.repaint();
					System.out.println(resultString);
					fileWrite(messageString);
					System.out.println("Write in");
				}
				fileWriter.flush();
				fileWriter.close();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
