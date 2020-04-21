package ClockPackage;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import clock.ClockWorkflow;

import com.tilab.wade.commons.AgentInitializationException;
import com.tilab.wade.dispatcher.DispatchingCapabilities;
import com.tilab.wade.performer.WorkflowEngineAgent;
import com.tilab.wade.performer.WorkflowException;
import com.tilab.wade.performer.descriptors.WorkflowDescriptor;

import SupervisorPackage.Supervisor;
import jade.core.AID;

public class Clock extends WorkflowEngineAgent{
	
	public static Map<String, Integer> eventContMap = new ConcurrentHashMap<String,Integer>();
	public static ArrayList<AID> migrantAID = new ArrayList<AID>();
	private DispatchingCapabilities dcC = new DispatchingCapabilities();
	
	protected void agentSpecificSetup() throws AgentInitializationException{
		
		super.agentSpecificSetup();
		dcC.init(this);
		
		Clock.eventContMap.put("eventCont", 0);
		Supervisor.linkComm.put("Clock", this.getAID());
		
		WorkflowDescriptor wd = new WorkflowDescriptor("clock.ClockWorkflow");
		try {
			dcC.launchWorkflow(getAID(),wd,null,null);
		} catch (WorkflowException e) {
			e.printStackTrace();
		}
		
		/*BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader("C:\\Users\\SARTIRANO\\eclipse-workspace\\MigrationProject\\lib\\skewnormal.txt"));
			String line = reader.readLine();
			while(line != null) {
				Supervisor.skewNormal.add(Double.parseDouble(line));
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		
	}

}
