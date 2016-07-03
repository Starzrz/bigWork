package serviceImpl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.rmi.RemoteException;

import service.IOService;

public class IOServiceImpl implements IOService{
	public static void main(String[] args) {
		IOServiceImpl impl =  new IOServiceImpl();
		
		//impl.writeFile("333", "Admin", "userlist");
		//impl.writeFile("444", "Admin", "userlist");
		System.out.println(impl.readFile("Admin", "userlist"));
	}
	
	@Override
	public boolean writeFile(String file, String userId, String fileName,boolean w) {
		File f = new File(userId + "_" + fileName+".txt");
		boolean b = true;
		if(!f.exists())
			b= false;
		try {
			FileWriter fw = new FileWriter(f, w);
			BufferedWriter writer = new BufferedWriter(fw);
			writer.newLine();
		
			writer.write(file);
			
			writer.flush();
			writer.close();
			
		} catch (IOException e) {
			e.printStackTrace();
			b=false;
		}
		return b;
	}

	@Override
	public String readFile(String userId, String fileName) {
		File file = new File(userId + "_" + fileName+".txt");
		
		String resultString="";
		if(!file.exists()){
			return resultString;
		}
		try {
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String line="";
			while((line=bufferedReader.readLine())!=null){
			resultString = resultString+" "+line;
			
			}
			fileReader.close();
			bufferedReader.close();
		}
			//}
		 catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		
		// TODO Auto-generated method stub
		return resultString;
	}
	public String runExecutor (String code,String param) throws RemoteException{
		ExecuteServiceImpl executeServiceImpl = new ExecuteServiceImpl();
		String resultString ="";
		
			resultString=executeServiceImpl.execute(code, param);
		
		return resultString;
	}

	@Override
	public String readFileList(String userId) {
		File file = new File(userId+"_"+"fileList"+".txt");
		String resultString="";
		if(!file.exists()){
			
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return resultString;
		}
		try {
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String line="";
			while((line=bufferedReader.readLine())!=null){
			resultString = resultString+" "+line;
			
			}
			fileReader.close();
			bufferedReader.close();
		}
			//}
		 catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		// TODO Auto-generated method stub
		return resultString;
	}
	
}
