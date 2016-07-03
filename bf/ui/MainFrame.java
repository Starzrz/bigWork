package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.lang.reflect.Parameter;
import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.text.StringContent;

import org.omg.CORBA.PUBLIC_MEMBER;

import NetWork.NetWork;
import rmi.RemoteHelper;

//import NetWork.NetWork;
//import NetWorkServer.Server;
class filePanel extends JPanel{      //文件面板背景绘制
	@Override
	protected void paintComponent(Graphics arg0) {        
		// TODO Auto-generated method stub
		Image image = new ImageIcon("底纹背景.jpg").getImage();
		arg0.drawImage(image, 0, 0, this.getWidth(),this.getHeight(),this);
	}
}
class myPanel extends JPanel{        //code面板背景绘制
	@Override
	protected void paintComponent(Graphics arg0) {
		// TODO Auto-generated method stub
		Image image = new ImageIcon("水纹背景.jpg").getImage();
		arg0.drawImage(image, 0, 0, this.getWidth(),this.getHeight(),this);
	}
}
class myList<State> extends ArrayList<State>{    //文件list重构，添加删除指定范围元素方法
	public myList() {
		// TODO Auto-generated constructor stub
	super();
	}
	public void removeFrom(int first,int last){
		removeRange(first, last);
	}
}
public class MainFrame {
	//NetWork netWork;
	//Server server;
	String []tStrings ;
	Thread thread ;
	int VerisonNum = 0;     //版本号
	public static JFrame frame;
	filePanel filePanel;
	myPanel codePanel;
	JPanel IOPanel;
	public String userNameString;     //用户名
	String fileNameString;
	JPanel userPanel;
	JTextArea codeArea;
	JTextArea inputArea;
	public static JTextArea outputArea;
	JTextArea fileArea;
	JButton logoutButton;
	JMenuBar menuBar;
	JMenu fileMenu;
	JMenu runMenu;
	JMenu userMenu;
	JPopupMenu filePopupMenu;
	JMenuItem openItem;
	JMenuItem newItem;
	JMenuItem saveItem;
	JMenuItem exitItem;
	JMenuItem loginItem;
	JMenuItem logoutItem;
	JMenuItem excuteItem;
	JMenuItem clearItem;
	JMenuItem deletItem;
	
	JPanel logPanel;
	JList fileList;
	JMenu versionMenu;
	
	myList<State> saveList = new myList<State>();      //文件列表
	int ptr;
	public String getcode(){    //获取代码和输入的方法
		return codeArea.getText();
	}
	public String getInput(){
		return inputArea.getText();
	}
	public void setVersionMenu(){    //创建版本菜单
		menuBar.remove(versionMenu);
		versionMenu.removeAll();
		Font menuFont = new Font("Comic Sans MS", Font.PLAIN, 30);
		for(int i=0;i<tStrings.length;i++){
			System.out.println(tStrings[i]);
			JMenuItem menuItem = new JMenuItem("Version"+i);
			menuItem.setFont(menuFont);
			menuItem.setForeground(Color.red);
			menuItem.addActionListener(new versionListener());
			versionMenu.add(menuItem);
		}
		menuBar.add(versionMenu);
		menuBar.setVisible(false);
		menuBar.setVisible(true);
	}
	public MainFrame(String userName) {   //主界面构造器，传入用户名
		//字体设置、
		Font menuFont = new Font("Comic Sans MS", Font.PLAIN, 30);
		Font codeFont = new Font("Comic Sans MS",Font.ROMAN_BASELINE,30	);
		Font labelFont = new Font("Times New Roman",Font.BOLD,30);
		 Font borderFont = new Font("Script MT Bold",Font.BOLD,25);
		userNameString = userName;
		
		frame = new JFrame("BF IDE Version 1.0");
		userPanel = new JPanel();
		userPanel.setLayout(new BorderLayout());
		//文件面板绘制
		filePanel = new filePanel();
		filePanel.setSize(30, 40);
		fileArea = new JTextArea(40,30);
		//filePanel.add(fileArea);
		filePanel.setBackground(Color.blue);
		fileArea.setBackground(Color.LIGHT_GRAY);
		Font fileFont = new Font("隶书", Font.PLAIN, 20);
	
		fileArea.setFont(fileFont);
		//绘制code区域面板
	    codeArea = new JTextArea(15,15);
	    codeArea.setLineWrap(true);
	    codeArea.setOpaque(false);
	    codeArea.setWrapStyleWord(true);
	    
	    codeArea.setEditable(false);
	    codeArea.addKeyListener(new revokeListener());
	    codePanel = new myPanel();
	    fileArea.setOpaque(false);
	    JScrollPane codePane = new JScrollPane(codeArea);
	    //绘制滚动条
	  	codeArea.setFont(codeFont);
	 
	    codePane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	    codePane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	    codePane.setOpaque(false);
	    codePane.getViewport().setOpaque(false); //设置面板为透明，显示背景
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
	    
	    inputArea.addKeyListener(new revokeListener());
	    JLabel inputLabel = new JLabel("Input",SwingConstants.LEFT);
	    inputLabel.setFont(labelFont);
	    inputLabel.setForeground(Color.BLUE);
	    JLabel outputLabel = new JLabel("Output");
	    outputLabel.setFont(labelFont);
	    outputLabel.setForeground(Color.BLUE);
	   
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
	    
	    menuBar = new JMenuBar();
	    menuBar.setFont(fileFont);
	    versionMenu = new JMenu("Version");
	    versionMenu.setFont(menuFont);
	    versionMenu.setForeground(Color.RED);
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
		openItem.addActionListener(new openActionlistener());
		newItem = new JMenuItem("new");
		newItem.setFont(menuFont);
		newItem.addActionListener(new NewActionlistener());
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
	    clearItem.addActionListener(new clearListener());
	    filePopupMenu = new JPopupMenu();
	    
	    deletItem = new JMenuItem("delet");
	    deletItem.setFont(menuFont);
	    deletItem.setForeground(Color.blue);
	    filePopupMenu.add(deletItem);
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
	    menuBar.add(versionMenu);
	    
	    //边框
	   
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
	    
	    //标签绘制，登出按钮
	   JLabel userLabel = new JLabel("Hello!"+userNameString+"      ");
		userLabel.setFont(codeFont);
		
		userLabel.setForeground(Color.ORANGE);
		ImageIcon icon = new ImageIcon("logout.jpg");
		Image image = icon.getImage();
		icon = new ImageIcon(image.getScaledInstance(100, 30, image.SCALE_SMOOTH));
		
		logoutButton = new JButton(icon);
		logoutButton.setMargin(new Insets(0, 0, 0, 0));
		logoutButton.addActionListener(new logoutActionlistener());
		logPanel = new JPanel();
		logPanel.add(userLabel);
		logPanel.add(logoutButton);
		userPanel.add(logPanel,BorderLayout.NORTH);
		userPanel.add(filePanel,BorderLayout.CENTER);
		setFilepanel();
	
	    //总体布局
	    frame.setJMenuBar(menuBar);
	    frame.addWindowListener(new WindowsActionlistener());
	    frame.add(BorderLayout.SOUTH,IOPanel);
	    frame.add(BorderLayout.WEST,userPanel);
	    frame.add(BorderLayout.CENTER,codePanel);
	    frame.setSize(1500, 1000);
	    frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	    frame.setVisible(true);
	    frame.setLocationRelativeTo(null);
	    //启动线程，监听代码的改变
	    thread = new Thread(new changeCheck(this));
	    thread.start();
		// TODO Auto-generated constructor stub
	}
	public void deletActive(String userName){  //登出时删除活跃账户。
		try {
			String result="";
			String activeString=RemoteHelper.getInstance().getIOService().readFile("Admin", "activeList");
			if(activeString.trim().length()<1){
				
			}
			String []active = activeString.trim().split(" ");
			for(String s:active){
				if(!s.equals(userName)){
					result = result+s+" ";
				}
				
			}
			RemoteHelper.getInstance().getIOService().writeFile(result,"Admin", "activeList",false);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	class clearListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			codeArea.setText("");
			inputArea.setText("");
		}
		
	}
	class versionListener implements ActionListener{    //版本回退菜单管理

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			String numString =e.getActionCommand();
			int num = numString.charAt(numString.length()-1)-'0';
			String codeString =tStrings[num];
			codeString=codeString.substring(0,codeString.length()-1);
			codeArea.setText(codeString);
		}
		
	}
	class revokeListener implements KeyListener{   //撤销与重做监听

		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
			if(e.isControlDown() && e.getKeyCode() == KeyEvent.VK_Z){ //撤销监听
				try{
				ptr--;  
				State lastState = saveList.get(ptr);  
				codeArea.setText(lastState.codeString);
				inputArea.setText(lastState.inputString);
				}
				catch (Exception e2) {
					ptr++;
					// TODO: handle exception
				}
				
			}
			else if(e.isControlDown() && e.getKeyCode() == KeyEvent.VK_Y){//重做监听
				System.out.println("get");
				try {
					ptr++;
					System.out.println(ptr);
					State lastState = saveList.get(ptr);
					System.out.println(lastState.codeString);
					codeArea.setText(lastState.codeString);
					inputArea.setText(lastState.inputString);
				} catch (Exception e2) {
					ptr--;
					// TODO: handle exception
				}
			}
			else {
				saveList.removeFrom(ptr+1, saveList.size());    //重做打断
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			
			
		}
		
	}
	
	public void setFilepanel(){
		try {
			String tempString=RemoteHelper.getInstance().getIOService().readFileList(userNameString); //文件列表是否为空
			System.out.println(tempString+"temp");
			if(tempString.trim().equals("")){
				System.out.println("kong");
				
				return;
			}
			else{
				Font fileFont = new Font("Times New Roman",Font.BOLD,30);   
				System.out.println("SetList"+tempString);
				tempString = tempString.trim();
				String file[] = tempString.split(" ");   //以数组方式存储列表
				for(int i=0;i<file.length;i++){
					file[i] ="   "+ file[i]+".txt";
				}
				System.out.println(file[0]);
				fileList= new JList(file);
				
				//fileList.setCellRenderer(new FontCellRenderer());
				fileList.setFont(fileFont);
				fileList.setLayoutOrientation(JList.VERTICAL);
				fileList.setOpaque(false);
				fileList.setBackground(new Color(0, 0, 0, 0));
				
		        fileList.addMouseListener(new fileClinkListener());
				filePanel.setLayout(new BorderLayout());
				filePanel.add(BorderLayout.WEST,fileList);
				filePanel.removeAll();     //移除旧的列表，添加新的列表，并刷新
			    filePanel.add(fileList);
			    filePanel.setVisible(false);
			   filePanel.setVisible(true);
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//String tempString 
	}
	
	class myTitle implements Border{     //重构自定义边界

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
	
	class fileClinkListener extends MouseAdapter{   //双击文件列表打开文件的监听
		@Override
		public void mouseClicked(MouseEvent e) {
			if(e.getButton()==MouseEvent.BUTTON3){
				
				filePopupMenu.show(e.getComponent(), e.getX(), e.getY());
			}
			// TODO Auto-generated method stub
			if(e.getClickCount()>=2){
				String fileString = ((JList)e.getSource()).getSelectedValue()+"";
				fileString =fileString.trim().substring(0,fileString.length()-7);   //得到文件名
				//System.out.println(fileString);
				fileNameString = fileString;
				String codeString = "";
				try {
					String tempString=RemoteHelper.getInstance().getIOService().readFile(userNameString, fileNameString);
					
					
					tStrings = tempString.trim().split(" "); 
					
					tempString = tStrings[tStrings.length-1];
					VerisonNum = tempString.charAt(tempString.length()-1)-'0';
					System.out.println(VerisonNum);
					tempString = tempString.substring(0,tempString.length()-1); //去掉版本标记
					char [ ]temp = tempString.toCharArray();
					for(char c:temp){
						if(c!=' '){
							codeString = codeString+c;
						}
					}
					codeArea.setText(codeString);
					codeArea.setEditable(true);
					codeArea.requestFocus();
					inputArea.setText("");
					setVersionMenu();     //显示版本菜单
					setinit();          //重置
					
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, "Read ERROR");
				}
				
			}
		}
	}
	class openActionlistener implements ActionListener{   //监听open，与fileClick基本相同

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			String inputString=JOptionPane.showInputDialog(null,"Please enter a file name","OPEN",JOptionPane.OK_CANCEL_OPTION);
			fileNameString = inputString;
			 
			String codeString = "";
			try {
				String tempString=RemoteHelper.getInstance().getIOService().readFile(userNameString, fileNameString);
				
				
				tStrings = tempString.trim().split(" "); 
				
				tempString = tStrings[tStrings.length-1];
				VerisonNum = tempString.charAt(tempString.length()-1)-'0';
				System.out.println(VerisonNum);
				tempString = tempString.substring(0,tempString.length()-1); //去掉版本标记
				char [ ]temp = tempString.toCharArray();
				for(char c:temp){
					if(c!=' '){
						codeString = codeString+c;
					}
				}
				codeArea.setText(codeString);
				codeArea.setEditable(true);
				codeArea.requestFocus();
				inputArea.setText("");
				setVersionMenu();     //显示版本菜单
				setinit();          //重置
				
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(null, "Read ERROR");
			}
			
		}
		
	}
	class NewActionlistener implements ActionListener{    //监听new

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			String inputString=JOptionPane.showInputDialog(null,"Please enter a file name","NEW",JOptionPane.OK_CANCEL_OPTION);
		    fileNameString = inputString;
		   
		    try {
		    	String tempString=RemoteHelper.getInstance().getIOService().readFileList(userNameString);
		    	if(tempString.trim().equals("")){   //文件列表
		    		
		    	}
		    	else{
		    		String[]tStrings = tempString.split(" ");
		    		for(String s:tStrings){
		    			System.out.println(s);
		    			if(s.equals(fileNameString)){ //若存在相同文件，显示错误信息
		    				
		    				JOptionPane.showMessageDialog(null, "File has already existed!");
		    				return;
		    			}
		    		}
		    	}
		    	 RemoteHelper.getInstance().getIOService().writeFile(fileNameString, userNameString, "fileList", true); //写入列表
			System.out.println("Write");
				}catch (Exception e2) {
					// TODO: handle exception
				}
		    	
		    	
		    codeArea.setText("");
		    inputArea.setText("");
		    String code = codeArea.getText();
		    VerisonNum = 0;
		    code =" "+VerisonNum; //添加第一个版本，默认为0，code默认为空
		    
		    setinit();
			try {
				RemoteHelper.getInstance().getIOService().writeFile(code, userNameString, fileNameString,false);
				VerisonNum ++;
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
		    codeArea.setEditable(true);
		    codeArea.requestFocus();  //code区域得到焦点，且设为可编辑
		    setFilepanel();  //设置文件列表
		}
	}
	class logoutActionlistener implements ActionListener{             //登出按钮的监听

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			//询问登出时是否要保存
			 int i =JOptionPane.showConfirmDialog(null, "Do you want to save before logout?","Logout the IDE",JOptionPane.YES_NO_CANCEL_OPTION);
			 if(i==0){
				 saveItem.doClick();
				 deletActive(userNameString);
				 frame.dispose();
				 LogFrame logFrame = new LogFrame();
			 }
			 if(i==1){
				frame.dispose();
				deletActive(userNameString);
				 LogFrame logFrame = new LogFrame();
			 }
			 if (i==2) {
				
			}
		}
		
	}
	class WindowsActionlistener extends WindowAdapter{  //监听关闭按钮
		 public void windowClosing ( WindowEvent e )
        { 
			 //询问是否保存
			 int i =JOptionPane.showConfirmDialog(null, "Do you want to save before exit?","Exit the IDE",JOptionPane.YES_NO_CANCEL_OPTION);
			 if(i==0){
				 saveItem.doClick();
				 deletActive(userNameString);
				 frame.dispose();
			 }
			 if(i==1){
				 deletActive(userNameString);
				frame.dispose();
			 }
			 if (i==2) {
				
			}
        }
	}
	class runListener implements ActionListener{//运行的监听
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			outputArea.setText("");
			saveItem.doClick();  //运行之前先保存
			// TODO Auto-generated method stub
			String param = inputArea.getText();
			String code = codeArea.getText();
			
			try {
				String resultString = RemoteHelper.getInstance().getExecuteService().execute(code, param);
				outputArea.setText(resultString);
			
			} catch (Exception e) {
				e.printStackTrace();
				// TODO: handle exception
			}
			

		}
		
	}
	class saveListener implements ActionListener{  //保存的监听

		@Override
		public void actionPerformed(ActionEvent e) {
			String code = codeArea.getText().replaceAll("\n", "");  //得到代码区的代码，并且去掉所有的换行
			try {
				String checkString = RemoteHelper.getInstance().getIOService().readFile(userNameString, fileNameString);
				String []checkStrings = checkString.trim().split(" ");
				String lastString = checkStrings[checkStrings.length-1];
				lastString = lastString.substring(0,lastString.length()-1);  //检查是否与上一个版本相同，若相同则不变
				if(lastString.equals(code)){
					System.out.println("SaveExist");
					return;
				}
			} catch (RemoteException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			VerisonNum++;   //版本号自增
			//System.out.println(VerisonNum);
			code = code+VerisonNum;   //代码末尾添加版本号
			try {
				RemoteHelper.getInstance().getIOService().writeFile(code, userNameString, fileNameString,true);
				String tempString=RemoteHelper.getInstance().getIOService().readFile(userNameString, fileNameString);
				
				
				tStrings = tempString.trim().split(" ");   //以数组形式临时存储版本
				
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
			// TODO Auto-generated method stub
			System.out.println("Save");
			setVersionMenu();     //设置版本菜单
		}
		
	}
	public void setinit(){    //初始化方法，清空撤销列表
		saveList.clear();
		ptr = 0;
		State firstState = new State(getcode(),getInput());
		saveList.add(firstState);
	}

}