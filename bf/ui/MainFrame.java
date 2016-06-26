package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.MenuBar;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.reflect.Parameter;
import java.rmi.RemoteException;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.text.StringContent;

import NetWork.NetWork;
import rmi.RemoteHelper;

//import NetWork.NetWork;
//import NetWorkServer.Server;
class filePanel extends JPanel{
	@Override
	protected void paintComponent(Graphics arg0) {
		// TODO Auto-generated method stub
		Image image = new ImageIcon("底纹背景.jpg").getImage();
		arg0.drawImage(image, 0, 0, this.getWidth(),this.getHeight(),this);
	}
}
class myPanel extends JPanel{
	@Override
	protected void paintComponent(Graphics arg0) {
		// TODO Auto-generated method stub
		Image image = new ImageIcon("waterback.jpg").getImage();
		arg0.drawImage(image, 0, 0, this.getWidth(),this.getHeight(),this);
	}
}
public class MainFrame {
	//NetWork netWork;
	//Server server;
	public static JFrame frame;
	filePanel filePanel;
	myPanel codePanel;
	JPanel IOPanel;
	
	
	JTextArea codeArea;
	JTextArea inputArea;
	public static JTextArea outputArea;
	JTextArea fileArea;
	
	JMenuBar menuBar;
	JMenu fileMenu;
	JMenu runMenu;
	JMenu userMenu;
	JMenuItem openItem;
	JMenuItem newItem;
	JMenuItem saveItem;
	JMenuItem exitItem;
	JMenuItem loginItem;
	JMenuItem logoutItem;
	JMenuItem excuteItem;
	JMenuItem clearItem;
	
	public MainFrame() {
		frame = new JFrame("BF IDE Version 1.0");
		//文件面板绘制
		filePanel = new filePanel();
		fileArea = new JTextArea(40,30);
		filePanel.add(fileArea);
		filePanel.setBackground(Color.blue);
		fileArea.setBackground(Color.LIGHT_GRAY);
		Font fileFont = new Font("隶书", Font.PLAIN, 20);
	
		fileArea.setFont(fileFont);
		//绘制code区域面板
	    codeArea = new JTextArea(15,15);
	    codeArea.setLineWrap(true);
	    codeArea.setOpaque(false);
	    codeArea.setWrapStyleWord(true);
	    codeArea.addKeyListener(new sendListener());
	    codePanel = new myPanel();
	    fileArea.setOpaque(false);
	    JScrollPane codePane = new JScrollPane(codeArea);
	    //绘制滚动条
	    //codeArea.setBackground(image);
	    Font codeFont = new Font("Comic Sans MS",Font.ROMAN_BASELINE,30	);
	    Font labelFont = new Font("Times New Roman",Font.BOLD,30);
	    codeArea.setFont(codeFont);
	    codePane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	    codePane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	    codePane.setOpaque(false);
	    codePane.getViewport().setOpaque(false);
	    codePanel.setLayout(new BorderLayout());         //设置布局格式
	    codePanel.add(BorderLayout.CENTER,codePane);
	    JLabel codeLabel = new JLabel("Code");//名称标签
	    codeLabel.setFont(labelFont);
	    codeLabel.setForeground(Color.BLUE);
	    //codeLabel.setBackground(Color.white);
	    //codePanel.add(BorderLayout.NORTH,codeLabel);
	    codePanel.setBackground(Color.GRAY);        //设置颜色为灰
	    //绘制IO区域面板
	    IOPanel = new JPanel();
	    inputArea = new JTextArea(5,30);  //输入面板
	    inputArea.setLineWrap(true);
	    inputArea.setWrapStyleWord(true);
	    inputArea.setBackground(Color.LIGHT_GRAY);
	    inputArea.setFont(labelFont);
	    JLabel inputLabel = new JLabel("Input",SwingConstants.LEFT);
	    inputLabel.setFont(labelFont);
	    inputLabel.setForeground(Color.BLUE);
	    JLabel outputLabel = new JLabel("Output");
	    outputLabel.setFont(labelFont);
	    outputLabel.setForeground(Color.BLUE);
	    //inputArea.add(inputLabel);
	    //IOPanel.add(inputLabel);
	    //输出面板绘制
	    outputArea = new JTextArea(5,30);
	    outputArea.setLineWrap(true);
	    outputArea.setWrapStyleWord(true);
	    outputArea.setEditable(false);
	    outputArea.setBackground(Color.gray);
	   outputArea.setFont(labelFont);
	    IOPanel.add(inputArea);
	    IOPanel.add(outputArea);
	    
	    //绘制菜单
	    Font menuFont = new Font("Comic Sans MS", Font.PLAIN, 30);
	    menuBar = new JMenuBar();
	    menuBar.setFont(fileFont);
	    
		fileMenu = new JMenu("file");
		fileMenu.setFont(menuFont);
		fileMenu.setForeground(Color.RED);
		runMenu = new JMenu("run");
		runMenu.setFont(menuFont);
		runMenu.setForeground(Color.RED);
		userMenu = new JMenu("user");
		userMenu.setFont(menuFont);
		userMenu.setForeground(Color.RED);
		openItem = new JMenuItem("open");
		openItem.setFont(menuFont);
		newItem = new JMenuItem("new");
		newItem.setFont(menuFont);
		saveItem = new JMenuItem("save");
		saveItem.setFont(menuFont);
		saveItem.addActionListener(new saveListener());
		exitItem = new JMenuItem("exit");
		exitItem.setFont(menuFont);
	    loginItem = new JMenuItem("login");
	    loginItem.setFont(menuFont);
	    logoutItem = new JMenuItem("logout");
	    logoutItem.setFont(menuFont);
	    excuteItem = new JMenuItem("excute");
	    excuteItem.setFont(menuFont);
	    excuteItem.addActionListener(new runListener());
	    clearItem = new JMenuItem("clear");
	    clearItem.setFont(menuFont);
	    fileMenu.add(newItem);
	    fileMenu.add(openItem);
	    fileMenu.add(saveItem);
	    fileMenu.add(exitItem);
	    runMenu.add(excuteItem);
	    runMenu.add(clearItem);
	    userMenu.add(loginItem);
	    userMenu.add(logoutItem);
	    menuBar.add(fileMenu);
	    menuBar.add(runMenu);
	    menuBar.add(userMenu);
	    //边框
	    Font borderFont = new Font("Script MT Bold",Font.BOLD,25);
	    TitledBorder inpuTitledBorder = new TitledBorder("Input");
	    myTitle myTitleBorder = new myTitle(2,new Color(0,255,255));
	    inpuTitledBorder.setBorder(myTitleBorder);
	    inpuTitledBorder.setTitleFont(borderFont);
	    inpuTitledBorder.setTitleColor(Color.blue);
	    inputArea.setBorder(inpuTitledBorder);
	    TitledBorder outputTitledBorder = new TitledBorder("Output");
	    outputTitledBorder.setBorder(myTitleBorder);
	    outputTitledBorder.setTitleFont(borderFont);
	    outputTitledBorder.setTitleColor(Color.BLUE);
	    outputArea.setBorder(outputTitledBorder);
	    TitledBorder codeTitledBorder = new TitledBorder("Code");
	    codeTitledBorder.setTitleFont(borderFont);
	    codeTitledBorder.setBorder(myTitleBorder);
	    codeTitledBorder.setTitleColor(Color.BLUE);
	    codePanel.setBorder(codeTitledBorder);
	    TitledBorder fileBorder = new TitledBorder("File");
	    fileBorder.setTitleFont(borderFont);
	    fileBorder.setBorder(myTitleBorder);
	    fileBorder.setTitleColor(Color.BLUE);
	    filePanel.setBorder(fileBorder);
	    //总体布局
	    frame.setJMenuBar(menuBar);
	    frame.addWindowListener(new WindowsActionlistener());
	    frame.add(BorderLayout.SOUTH,IOPanel);
	    frame.add(BorderLayout.WEST,filePanel);
	    frame.add(BorderLayout.CENTER,codePanel);
	    frame.setSize(1500, 1000);
	    frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	    frame.setVisible(true);
	    frame.setLocationRelativeTo(null);
	    
	    
		// TODO Auto-generated constructor stub
	}
	class myTitle implements Border{

		private int thickness; //边界线条的厚度
		
		private Color color; //边界的颜色
		
		
		
		public myTitle(int thickness,Color color){
		
		this.thickness=thickness;
		
		this.color=color;
		
		}
		
		
		public void paintBorder(Component c,Graphics g, int x, int y, int width, int height) { 
		g.setColor(this.color); //设定颜色
		g.fill3DRect(x,y,width-thickness,thickness,true); //绘制上边界
		g.fill3DRect(x,y+thickness,thickness,height-thickness,true); //绘制左边界
		g.fill3DRect(x+thickness,y+height-thickness,width-thickness,thickness,true); //绘制下边界
		g.fill3DRect(x+width-thickness,y,thickness,height-thickness,true); //绘制右边界
		}
		
		
		
		public Insets getBorderInsets(Component c){
		
		return new Insets(thickness,thickness,thickness,thickness);
		
		}
		
		
		
		public boolean isBorderOpaque(){
		
		return true;
		
		}

		
	}
	class WindowsActionlistener extends WindowAdapter{
		 public void windowClosing ( WindowEvent e )
        {
			 int i =JOptionPane.showConfirmDialog(null, "Do you want to save before exit?","Exit the IDE",JOptionPane.YES_NO_CANCEL_OPTION);
			 if(i==0){
				 saveItem.doClick();
				 frame.dispose();;
			 }
			 if(i==1){
				frame.dispose();
			 }
			 if (i==2) {
				
			}
        }
	}
	class runListener implements ActionListener{
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			saveItem.doClick();
			// TODO Auto-generated method stub
			String param = inputArea.getText();
			String code = codeArea.getText();
			//System.out.println(param+"r");
			//System.out.println(code+"c");
			try {
				String resultString = RemoteHelper.getInstance().getExecuteService().execute(code, param);
				
				
				//String resultString = RemoteHelper.getInstance().getIOService().readFileList(code, param);
				//System.out.println(resultString+"r");
				outputArea.setText(resultString);
			//	NetWork.sendToServer(param+"0");
				//String code = RemoteHelper.getInstance().getIOService().readFile("admin", "code");
				//String reString = RemoteHelper.getInstance().getE
			} catch (Exception e) {
				e.printStackTrace();
				// TODO: handle exception
			}
			

		}
		
	}
	class saveListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			String code = codeArea.getText();
			try {
				RemoteHelper.getInstance().getIOService().writeFile(code, "admin", "code");
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
			// TODO Auto-generated method stub
			System.out.println("Save");
		}
		
	}
	public class sendListener implements KeyListener{

		@Override
		public void keyPressed(KeyEvent arg0) {
			if(arg0.getKeyChar() == KeyEvent.VK_ENTER){
				//netWork.sendToServer(codeArea.getText());
				codeArea.setText("");
				codeArea.requestFocus();
			}
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyReleased(KeyEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyTyped(KeyEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}

}	/*class MenuItemActionListener implements ActionListener {
		/**
		 * 子菜单响应事件
		 */
	
		/*public void actionPerformed(ActionEvent e) {
			String cmd = e.getActionCommand();
			if (cmd.equals("Open")) {
				textArea.setText("Open");
			} else if (cmd.equals("Save")) {
				textArea.setText("Save");
			} else if (cmd.equals("Run")) {
				resultLabel.setText("Hello, result");
			}
		}
	}

	class SaveActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String code = textArea.getText();
			try {
				RemoteHelper.getInstance().getIOService().writeFile(code, "admin", "code");
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
		}

	}
}*/
