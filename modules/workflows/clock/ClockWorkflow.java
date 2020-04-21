package clock;

import com.tilab.wade.performer.layout.TransitionLayout;
import com.tilab.wade.performer.Transition;
import com.tilab.wade.performer.layout.ActivityLayout;
import com.tilab.wade.performer.layout.WorkflowLayout;

import ClockPackage.Clock;
import SupervisorPackage.Supervisor;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import com.tilab.wade.performer.CodeExecutionBehaviour;
import com.tilab.wade.performer.WorkflowBehaviour;

@WorkflowLayout(transitions = {
		@TransitionLayout(routingpoints = "(800,398);(895,398);(895,353)", to = "Synchronize", from = "Synchronize") }, activities = { @ActivityLayout(label = "Synchronize", position = "(745,323)", name = "Synchronize") })
public class ClockWorkflow extends WorkflowBehaviour{

	public static final String SYNCHRONIZE_ACTIVITY = "Synchronize";

	public ClockWorkflow() {
	}

	public ClockWorkflow(String activityName) {
		super(activityName);
	}

	public ClockWorkflow(String activityName, boolean component) {
		super(activityName);
	}

	public boolean isLongRunning() {
		return false;
	}

	private void defineActivities() {
		CodeExecutionBehaviour synchronizeActivity = new CodeExecutionBehaviour(SYNCHRONIZE_ACTIVITY, this);
		registerActivity(synchronizeActivity, INITIAL);
	}

	/**
	 */
	protected void executeSynchronize() throws Exception {
		
		//SYNCHRO INIT
		int contmes = Supervisor.n_migrant;
		while(contmes > 0) {
			MessageTemplate cMT = MessageTemplate.MatchContent("EDDAJE FRA");
			ACLMessage migSinc = myAgent.blockingReceive(cMT);
			if (migSinc != null) {
				contmes--;
			}
		}
		Thread.sleep(100);

		ACLMessage synchro = new ACLMessage();
		for (int i = 0; i < Clock.migrantAID.size(); i++) {
			synchro.addReceiver(Clock.migrantAID.get(i));
		}
		synchro.setContent("DAJE TU");
		myAgent.send(synchro);
		
		//SYNCHRO MOORE
		contmes = Supervisor.n_migrant;
		while(contmes > 0) {
			MessageTemplate cMT = MessageTemplate.MatchContent("MOORE SYNC");
			ACLMessage migSincMoore = myAgent.blockingReceive(cMT);
			if (migSincMoore != null) {
				contmes--;
			}
		}
		Thread.sleep(100);

		ACLMessage synchroMoore = new ACLMessage();
		for (int i = 0; i < Clock.migrantAID.size(); i++) {
			synchroMoore.addReceiver(Clock.migrantAID.get(i));
		}
		synchroMoore.setContent("VAI MOORE");
		myAgent.send(synchroMoore);
	}

	private void defineTransitions() {
		registerTransition(new Transition(), SYNCHRONIZE_ACTIVITY, SYNCHRONIZE_ACTIVITY);
	}

}
