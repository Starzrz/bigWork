package serviceImpl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.rmi.RemoteException;

import service.IOService;

public class IOServiceImpl implements IOService{
	public static void main(String[] args) {
		IOServiceImpl impl =  new IOServiceImpl();
		
		System.out.println(impl.readFile("admin", "code"));
	}
	
	@Override
	public boolean writeFile(String file, String userId, String fileName) {
		File f = new File(userId + "_" + fileName);
		try {
			FileWriter fw = new FileWriter(f, false);
			fw.write(file);
			fw.flush();
			fw.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public String readFile(String userId, String fileName) {
		File file = new File(userId + "_" + fileName);
		String resultString="";
		try {
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			//while((bufferedReader.readLine())!=null){
			resultString = resultString+bufferedReader.readLine();
			//}
		} catch (Exception e) {
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
		// TODO Auto-generated method stub
		return "OK";
	}
	
}
