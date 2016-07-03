package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.rmi.RemoteException;
import java.security.Key;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import rmi.RemoteHelper;

public class LogFrame {  //登陆窗口
	private static final int Width = 500;
	private static final int Height = 300;
	JTextField nameField;
	JPasswordField passField;
	JButton button1 ;
	JFrame frame = new JFrame();
	MainFrame mainFrame;
	JLabel bfLabel;
	myPanel mainPanel ;
	LogFrame logFrame;
	public void setIcon(String file, JButton iconButton) {   //设置按钮图片的方法
		ImageIcon icon = new ImageIcon(file); 
		Image temp = icon.getImage().getScaledInstance(iconButton.getWidth(), 
		iconButton.getHeight(), icon.getImage().SCALE_DEFAULT); 
		icon = new ImageIcon(temp); 
		iconButton.setIcon(icon); 
		
		} 
	class myPanel extends JPanel{  //重绘登陆页面背景
		@Override
		protected void paintComponent(Graphics arg0) {
			// TODO Auto-generated method stub
			Image image = new ImageIcon("whiteBack.jpg").getImage();
			arg0.drawImage(image, 0, 0, this.getWidth(),this.getHeight(),this);
		}
	}
	public LogFrame(){
		logFrame = this;
		 mainPanel= new myPanel();
		Font nameFont = new Font("Comic Sans MS",Font.BOLD,20);
		Font logFont = new Font("",Font.PLAIN,30);
		frame.setTitle("Login");
		frame.setSize(Width, Height);
		
		Container container = frame.getContentPane();  
		JLabel nameLabel = new JLabel("Name    ");
		nameField = new JTextField();
		nameField.setPreferredSize(new Dimension(200, 30));
		nameField.setMaximumSize(nameField.getPreferredSize());
		Box nameBox = Box.createHorizontalBox();  //采用box布局，控制组件间距
		nameBox.add(nameLabel);
		nameBox.add(Box.createHorizontalStrut(20));
		nameBox.add(nameField);
		JLabel passLabel = new JLabel("Passward");
		passField =  new JPasswordField();
		passField.setPreferredSize(new Dimension(200, 30));
		
		passField.setMaximumSize(passField.getPreferredSize());
		Box passBox = Box.createHorizontalBox();
		nameField.addKeyListener(new downKeyListener());
		passField.addKeyListener(new logKeyListener());
		passBox.add(passLabel);
		passBox.add(Box.createHorizontalStrut(20));
		passBox.add(passField);
		nameField.setFont(logFont);
		passField.setFont(logFont);
		passLabel.setFont(nameFont);
		nameLabel.setFont(nameFont);
		ImageIcon icon = new ImageIcon("login.jpg");
		Image image = icon.getImage();
		icon = new ImageIcon(image.getScaledInstance(120, 40, image.SCALE_SMOOTH));
		ImageIcon icon2 = new ImageIcon("signUp.jpg");
		Image image2 = icon2.getImage();
		icon2 = new ImageIcon(image2.getScaledInstance(120, 40, image.SCALE_SMOOTH));
		button1 = new JButton(icon);
		button1.setMargin(new Insets(0, 0, 0, 0));  //按钮设置图片全填充
		button1.addActionListener(new clinkListener());
		JButton button2 = new JButton(icon2);
		button2.setMargin(new Insets(0, 0, 0, 0));
	
		button2.addActionListener(new SignListener());
		Box buttonBox = Box.createHorizontalBox();
		buttonBox.add(button1);
		buttonBox.add(Box.createHorizontalStrut(40));
		buttonBox.add(button2);
		Box panelBox = Box.createVerticalBox();
		panelBox.add(nameBox);
		panelBox.add(passBox);
		panelBox.add(Box.createVerticalGlue());
		panelBox.add(buttonBox);
		
		//label绘制
		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(panelBox,BorderLayout.CENTER);
		
		ImageIcon bfImageIcon = new ImageIcon("BFsmall.jpg");
		bfLabel=new JLabel(bfImageIcon);
		mainPanel.add(bfLabel,BorderLayout.WEST);
		
		container.add(mainPanel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		frame.show();
	
		// TODO Auto-generated construc"tor stub
	}
	
	class SignListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			frame.setVisible(false);
			SignUp signUp = new SignUp(logFrame);
		}
		
	}
	class clinkListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			String userName = nameField.getText();
			String user_InfString = userName+"_"+String.valueOf(passField.getPassword());
			String checkString="0+0";
			
				try {                    //读取注册信息列表
					checkString = RemoteHelper.getInstance().getIOService().readFile("Admin", "userlist");
					System.out.println(checkString);
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(checkString.length()<=1){ //若无人注册，报错
					JOptionPane.showMessageDialog(null, "No one has signed up");
				}
				else{
				Boolean isRight = false;
				String[]checkStrings =checkString.substring(1).split(" ");
				for(String s:checkStrings){    //检查账户密码是否正确
					System.out.println(s);
					if(s.equals(user_InfString)){
						if(!isActive(userName)){
						try {
							RemoteHelper.getInstance().getIOService().writeFile(userName+" ","Admin", "activeList",true);
						} catch (RemoteException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						mainFrame = new MainFrame(userName);
						frame.dispose();
						isRight = true;
						}
						else {
							JOptionPane.showMessageDialog(null, "The user is active!");
							return;
						}
					}
				}
				if(!isRight){
					JOptionPane.showMessageDialog(null, "Your password is Wrong or user doesn't exit!");
				}
					// TODO: handle exception
			
		      }
		}
		
	}
	public boolean isActive(String userName){
		try {
			String activeString=RemoteHelper.getInstance().getIOService().readFile("Admin", "activeList");
			if(activeString.trim().length()<1){
				return false;
			}
			String []active = activeString.trim().split(" ");
			for(String s:active){
				if(s.equals(userName)){
					return true;
				}
				
			}
			
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	class logKeyListener implements KeyListener{  //快捷键，回车将光标移到密码处。

		@Override
		public void keyPressed(KeyEvent e) {
			if(e.getKeyChar() == KeyEvent.VK_ENTER){
				button1.doClick();
			}
			// TODO Auto-generated method stub
			
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
	class downKeyListener implements KeyListener{

		@Override
		public void keyPressed(KeyEvent arg0) {
			if(arg0.getKeyChar()==KeyEvent.VK_ENTER){
				passField.requestFocus();
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
}