package ui;

public class changeCheck implements Runnable{    //撤销线程，监视code和input的变化
	MainFrame frame;
	public changeCheck(MainFrame mframe) {
		
		frame = mframe;
		State firstState = new State(frame.getcode(),frame.getInput());
		frame.saveList.add(firstState);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true){
			try {
				Thread.sleep(50);  //每50ms监听一次
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		State newState = new State(frame.getcode(),frame.getInput());
		State oldState = frame.saveList.get(frame.ptr);
		//如果代码或输入有不同，列表增加
		if(!(newState.codeString.equals(oldState.codeString)) || !(newState.inputString.equals(oldState.inputString))){
			frame.saveList.add(newState);
			
			frame.ptr++;
			System.out.println("add"+frame.ptr);
			
		}
	}
	
}
}