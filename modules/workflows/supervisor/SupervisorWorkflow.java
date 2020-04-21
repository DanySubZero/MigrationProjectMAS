package supervisor;

import com.tilab.wade.performer.layout.TransitionLayout;
import com.tilab.wade.performer.Transition;
import com.tilab.wade.performer.layout.ActivityLayout;
import com.tilab.wade.performer.layout.WorkflowLayout;

import SupervisorPackage.Supervisor;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.io.Serializable;
import java.util.ArrayList;

import com.tilab.wade.performer.CodeExecutionBehaviour;
import com.tilab.wade.performer.WorkflowBehaviour;
import com.tilab.wade.performer.FormalParameter;

@WorkflowLayout(transitions = { @TransitionLayout(routingpoints = "(790,294);(885,294);(885,249)", to = "MoveMigrant", from = "MoveMigrant") }, activities = { @ActivityLayout(label = "MoveMigrant", position = "(735,219)", name = "MoveMigrant") })
public class SupervisorWorkflow extends WorkflowBehaviour {

	public static final String MOVEMIGRANT_ACTIVITY = "MoveMigrant";
	private final int s = Supervisor.N;


	public SupervisorWorkflow() {
	}

	public SupervisorWorkflow(String activityName) {
		super(activityName);
	}

	public SupervisorWorkflow(String activityName, boolean component) {
		super(activityName);
	}

	public boolean isLongRunning() {
		return false;
	}

	private void defineActivities() {
		CodeExecutionBehaviour moveMigrantActivity = new CodeExecutionBehaviour(MOVEMIGRANT_ACTIVITY, this);
		registerActivity(moveMigrantActivity, INITIAL);
	}

	/**
	 */
	protected void executeMoveMigrant() throws Exception {
		
		MessageTemplate sMT = MessageTemplate.MatchPerformative(ACLMessage.REQUEST);
		ACLMessage requestMig = myAgent.blockingReceive(sMT);
		if (requestMig != null) {
			ACLMessage responseMig = requestMig.createReply();
			responseMig.setPerformative(ACLMessage.INFORM);
			int x = Integer.parseInt(requestMig.getContent().split(",",4)[0]); //prende posizione attuale
			int y = Integer.parseInt(requestMig.getContent().split(",",4)[1]);
			int xdes = Integer.parseInt(requestMig.getContent().split(",",4)[2]);//prende posizione desiderata
			int ydes = Integer.parseInt(requestMig.getContent().split(",",4)[3]);
			ArrayList<Integer> dest = searchAround(xdes,ydes,0);
			xdes = dest.get(0);
			ydes = dest.get(1);
			changePosition(x,y,xdes,ydes);
			ArrayList<Integer> firm_pos = searchAround(xdes,ydes,2);
			String auxStr = firm_pos.get(0)+","+firm_pos.get(1);
			double alpha = Supervisor.firm.get(auxStr);
			responseMig.setContent(xdes + "," + ydes + "," + alpha);
			myAgent.send(responseMig);
			dest = new ArrayList<Integer>();
		}
	
	}

	private void defineTransitions() {
		registerTransition(new Transition(), MOVEMIGRANT_ACTIVITY, MOVEMIGRANT_ACTIVITY);
	}
	
	//
	//utility
	//
	
	//guarda tutte le patch nel quadrato di lato D il cui centro è (x, y) in cerca di celle di tipo target, fino a trovarne una
    public ArrayList<Integer> searchAround (int x, int y, int target){//x e y sono la posizione dell'agente a cui fa riferimento
        boolean value = false;
        ArrayList<Integer> pos = new ArrayList<Integer>();
        int D = 0;
        int i = x - D;
        int j = y - D;
        while (value == false){
	        if (y < Supervisor.N/2) {
	        	while(j < y + D && value == false){
	        		try {
	        			if (Supervisor.map[i][j] == target && j < Supervisor.N/2) {
	        				pos.add(i);
	        				pos.add(j);
	        				value = true;
	        			}
	        			j++;
	        		} catch(ArrayIndexOutOfBoundsException e1) {
	        			j++;
	        		}
	        	}
	        	while(i < x + D && value == false){
	        		try {
	        			if (Supervisor.map[i][j] == target && j < Supervisor.N/2) {
	        				pos.add(i);
	        				pos.add(j);
	        				value = true;
	        			}
	        			i++;
	        		} catch(ArrayIndexOutOfBoundsException e2) {
	        			i++;
	        		}
	        	}
	        	while(j > y - D && value == false){
	        		try {
	        			if (Supervisor.map[i][j] == target && j < Supervisor.N/2) {
	        				pos.add(i);
	        				pos.add(j);
	        				value = true;
	        			}
	        			j--;
	        		} catch(ArrayIndexOutOfBoundsException e3) {
	        			j--;
	        		}
	        	}
	        	while(i > x - D && value == false){
	        		try {
	        			if (Supervisor.map[i][j] == target && j < Supervisor.N/2) {
	        				pos.add(i);
	        				pos.add(j);
	        				value = true;
	        			}
	        			i--;
	        		} catch(ArrayIndexOutOfBoundsException e4) {
	        			i--;
	        		}
	        	}
	        	D++;
	        	i = x - D;
	        	j = y - D;
	        }
	    if (y > Supervisor.N/2) {
        	while(j < y + D && value == false){
        		try {
        			if (Supervisor.map[i][j] == target && j > Supervisor.N/2) {
        				pos.add(i);
        				pos.add(j);
        				value = true;
        			}
        			j++;
        		} catch(ArrayIndexOutOfBoundsException e1) {
        			j++;
        		}
        	}
        	while(i < x + D && value == false){
        		try {
        			if (Supervisor.map[i][j] == target && j > Supervisor.N/2) {
        				pos.add(i);
        				pos.add(j);
        				value = true;
        			}
        			i++;
        		} catch(ArrayIndexOutOfBoundsException e2) {
        			i++;
        		}
        	}
        	while(j > y - D && value == false){
        		try {
        			if (Supervisor.map[i][j] == target && j > Supervisor.N/2) {
        				pos.add(i);
        				pos.add(j);
        				value = true;
        			}
        			j--;
        		} catch(ArrayIndexOutOfBoundsException e3) {
        			j--;
        		}
        	}
        	while(i > x - D && value == false){
        		try {
        			if (Supervisor.map[i][j] == target && j > Supervisor.N/2) {
        				pos.add(i);
        				pos.add(j);
        				value = true;
        			}
        			i--;
        		} catch(ArrayIndexOutOfBoundsException e4) {
        			i--;
        		}
        	}
        	D++;
        	i = x - D;
        	j = y - D;
        }
        }
        return pos;
    }
    
    //scambia la posizione di due pach
    public void changePosition(int x1, int y1, int x2, int y2) {
        
    	int aux = Supervisor.map[x1][y1];
        Supervisor.map[x1][y1] = Supervisor.map[x2][y2];
        Supervisor.map[x2][y2] = aux;
         
    }


}
