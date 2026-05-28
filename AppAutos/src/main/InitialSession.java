package main;

import framework_controllers.BaseController;
import interfaces.ICommunicator;

class InitialSession implements Runnable {
	
	private BaseController controller;
	private ICommunicator communicator;
	
	public InitialSession(BaseController controller, ICommunicator communicator) {
		super();
		this.controller = controller;
		this.communicator = communicator;
	}

	public BaseController getController() {
		return controller;
	}

	public ICommunicator getCommunicator() {
		return communicator;
	}

	@Override
	public void run() {
		while(true) {
			String sMessage = communicator.receive();
			
		}
		
	}

}
