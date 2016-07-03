package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.rmi.RemoteException;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import rmi.RemoteHelper;


public class SignUp {     //实现注册界面
	JFrame signframe;
	LogFrame logFrame;
	JButton signButton;
	JButton concelButton;
	JTextField signNameField;
	JPasswordField signPasswordField;
	JPasswordField confirmPasswordField;
	public SignUp(LogFrame frame) {  //取得登陆界面的引用
	
		logFrame = frame;
		Font nameFont = new Font("Comic Sans MS",Font.BOLD,20);
		Font logFont = new Font("",Font.PLAIN,30);
		signframe = new JFrame("Sign up");
		signNameField = new JTextField(10);
		signNameField.setMaximumSize(signNameField.getPreferredSize());
		signPasswordField = new JPasswordField(10);
		signPasswordField.setMaximumSize(signPasswordField.getPreferredSize());
		confirmPasswordField = new JPasswordField(10);
		confirmPasswordField.setMaximumSize(confirmPasswordField.getPreferredSize());  //确认密码窗口
		JLabel nameLabel = new JLabel("Name ");
		Box nameBox = Box.createHorizontalBox();
		nameBox.add(nameLabel);
		nameBox.add(Box.createHorizontalStrut(30));
		nameBox.add(signNameField);
		JLabel passLabel = new JLabel("Password");
		Box passBox = Box.createHorizontalBox();
		passBox.add(passLabel);
		passBox.add(Box.createHorizontalStrut(8));
		passBox.add(signPasswordField);
		JLabel confirmLabel = new JLabel("Confirm");
		Box confirmBox = Box.createHorizontalBox();
		confirmBox.add(confirmLabel);
		confirmBox.add(Box.createHorizontalStrut(20));
		confirmBox.add(confirmPasswordField);
		
		signButton = new JButton();
		signButton.addActionListener(new signupListener());
		concelButton = new JButton();
		concelButton.addActionListener(new concelListener());
		Box buttonBox = Box.createHorizontalBox();
		buttonBox.add(signButton);
		buttonBox.add(Box.createHorizontalStrut(40));
		buttonBox.add(concelButton);
		Box panelBox = Box.createVerticalBox();
		panelBox.add(nameBox);
		panelBox.add(passBox);
		panelBox.add(confirmBox);
		panelBox.add(Box.createVerticalGlue());
		panelBox.add(buttonBox);
		ImageIcon icon = new ImageIcon("signUp.jpg");
		Image image = icon.getImage();
		icon = new ImageIcon(image.getScaledInstance(120, 40, image.SCALE_SMOOTH));
		signButton.setIcon(icon);
		signButton.setMargin(new Insets(0, 0, 0, 0));
		ImageIcon icon2 = new ImageIcon("Cancel.jpg");
		Image image2 = icon2.getImage();
		icon2 = new ImageIcon(image2.getScaledInstance(120, 40, image.SCALE_SMOOTH));
		concelButton.setIcon(icon2);
		concelButton.setMargin(new Insets(0, 0, 0, 0));
		//signNameField.setFont(logFont);
		signNameField.setPreferredSize(new Dimension(500, 30));
		signPasswordField.setPreferredSize(new Dimension(500, 30));
		confirmPasswordField.setPreferredSize(new Dimension(500, 30));
		signNameField.setMaximumSize(signNameField.getPreferredSize());
		signPasswordField.setMaximumSize(signPasswordField.getPreferredSize());
		confirmPasswordField.setMaximumSize(confirmPasswordField.getPreferredSize());
		nameLabel.setFont(nameFont);
		passLabel.setFont(nameFont);
		confirmLabel.setFont(nameFont);
		signframe.getContentPane().add(panelBox,BorderLayout.CENTER);
		signframe.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		signframe.setSize(400,300);
		signframe.addWindowListener(new windowCloseListener());
		signframe.setLocationRelativeTo(null);
		signframe.setVisible(true);
		// TODO Auto-generated constructor stub
	}
	class windowCloseListener extends WindowAdapter{  //关闭注册界面，重新回到登陆界面
		@Override
		public void windowClosing(WindowEvent e) {
			// TODO Auto-generated method stub
			concelButton.doClick();
		}
	}
	class concelListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			signframe.dispose();
			logFrame.frame.setVisible(true);
		}
		
	}
	class signupListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			boolean isExist = false;
			String userNameString  = signNameField.getText();
			String passString = String.valueOf(signPasswordField.getPassword());
			String confirmString = String.valueOf(confirmPasswordField.getPassword());
			String userInf = userNameString+"_"+passString;
			try {
				String []uStrings = RemoteHelper.getInstance().getIOService().readFile("Admin","userlist").substring(1).split(" ");
				OK:
				for(String s:uStrings){
					String []tempStrings =s.split("_");
					for(String a:tempStrings){
						if(a.equals(userNameString)){
							isExist = true;       //检查已注册列表，不允许重名
							break OK;
						}
					}
				}
			} catch (RemoteException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			
			
			if((userNameString.length()<1) || (passString.length()<1) || (confirmString.length()<1)){ //不允许为空
				JOptionPane.showMessageDialog(null, "The username or password should not be null!");
			}
			else if(isExist){    //重名提示
				JOptionPane.showMessageDialog(null, "The username has already existed!");
			}
			else if(!passString.equals(confirmString)){  //确认密码错误提示
				JOptionPane.showMessageDialog(null, "Please type the same password in two field!");
			}
			else {
				try {     //注册成功，写入信息
					RemoteHelper.getInstance().getIOService().writeFile(userInf, "Admin","userlist",true);
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				int i = JOptionPane.showConfirmDialog(null, "Success!Do you want to login directly?","Congratulation!",JOptionPane.YES_NO_OPTION);
				if(i==0){  
					logFrame.frame.setVisible(true);//判断是否要直接登录
					logFrame.nameField.setText(userNameString);
					logFrame.passField.setText(passString);
					logFrame.button1.doClick();
					signframe.dispose();
					
				}
				else if(i==1){
					signframe.dispose();
					logFrame.frame.setVisible(true);
				}
			}
	
		}
		
	}
}