//请不要修改本文件名
package serviceImpl;

import java.rmi.RemoteException;

import service.ExecuteService;
import service.UserService;

public class ExecuteServiceImpl implements ExecuteService {
	char[]stack = new char[10000];
	String resultString="";
	int ptr = 5000;
	/**
	 * 请实现该方法
	 */
	@Override
	public String execute(String code, String param) throws RemoteException {
		// TODO Auto-generated method stub
		int i=0;
		param = param+" ";
		char []codeChar =code.toCharArray();
		int firstnum=i;
		while(i<codeChar.length){
			//System.out.print(codeChar[i]);
			switch (codeChar[i]) {
			case ',':
				String input = param;
				/*try{
					int a = Integer.parseInt(input);
					stack[ptr] = (char)a;
					System.out.println(stack[ptr]);
				}
				//catch (Exception e) {
					// TODO: handle exception
				*/
				try{
				
				stack[ptr] = input.charAt(0);
				}catch (Exception ex) {
					System.out.print("error");
					stack[ptr] = 10;// TODO: handle exception
				}
				param = param.substring(1);
				//}
				break;
			case '.':
				resultString =resultString+stack[ptr];
				//System.out.print(stack[ptr]);
				break;
			case '<':
				ptr--;
				break;
			case '>':
				ptr++;
				break;
			case '+':
				
				stack[ptr]++;
				break;
			case '-':
				stack[ptr]--;
				break;
			case '[':
				int tempnum=0;
				int num=0;
				for(int j=i;j<codeChar.length;j++){
					if(code.charAt(j)=='['){
						num++;
					}
					if(code.charAt(j)==']'){
						num--;
						
					}
					if(num==0){
						tempnum = j;
						break;
					}
				}
				String tempcodeString = code.substring(i+1,tempnum);
				int temp = i;
				while(stack[ptr] != 0){
					
				//while(codeChar[i] != ']'){
					resultString =resultString+execute(tempcodeString, param);
					/*switch (codeChar[i]) {
					case ',':
						String input2 = param;
						/*try{
							int a = Integer.parseInt(input2);
							stack[ptr] = (char)a;
							System.out.println(stack[ptr]);
						}
						catch (Exception e) {
							// TODO: handle exception
						*/
						/*try{
						
						stack[ptr] = input2.charAt(0);
						}catch (Exception ex) {
							System.out.print("error");
							stack[ptr] = 10;// TODO: handle exception
						}
						//}
						break;
					case '.':
						resultString =resultString+stack[ptr];
						//System.out.print(stack[ptr]);
						break;
					case '<':
						ptr--;
						break;
					case '>':
						ptr++;
						break;
					case '+':
						stack[ptr]++;
						break;
					case '-':
						stack[ptr]--;
						break;
					}*/
					//i++;
				//}
				
				}
				i=i+tempcodeString.length()+1;
			//case ']':
			//	i=firstnum-1;
			default:
				break;
			}
			i++;
		}
		
	
	return resultString;
}
	}


