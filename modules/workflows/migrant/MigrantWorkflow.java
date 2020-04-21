package migrant;

import com.tilab.wade.performer.layout.TransitionLayout;
import com.tilab.wade.performer.Transition;
import com.tilab.wade.performer.RouteActivityBehaviour;
import com.tilab.wade.performer.layout.ActivityLayout;
import com.tilab.wade.performer.layout.WorkflowLayout;

import ClockPackage.Clock;
import MigrantPackage.Migrant;
import SupervisorPackage.Supervisor;

import com.tilab.wade.performer.CodeExecutionBehaviour;
import com.tilab.wade.performer.WorkflowBehaviour;

import jade.core.AID;
import jade.domain.FIPANames;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import supervisor.SupervisorWorkflow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.math3.analysis.function.Gaussian;

import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import com.tilab.wade.performer.FormalParameter;

import MigrantPackage.CustomWriteFile;;

@WorkflowLayout(transitions = {@TransitionLayout(to = "Init", from = "Move"), @TransitionLayout(to = "Init", from = "MigrantWorkflowRouteActivity6"), @TransitionLayout(to = "Init", from = "MigrantWorkflowRouteActivity4"), @TransitionLayout(to = "Move", from = "ChooseWall"), @TransitionLayout(to = "ChooseWall", from = "MigrantWorkflowRouteActivity7"), @TransitionLayout(to = "Move", from = "ChooseWhereMig"), @TransitionLayout(labelPosition = "START", labelVisible = true, label = "TRUE", to = "ChooseWhereMig", from = "MigrantWorkflowRouteActivity7"), @TransitionLayout(labelPosition = "START", labelVisible = true, label = "TRUE", to = "MigrantWorkflowRouteActivity7", from = "MigrantWorkflowRouteActivity6"), @TransitionLayout(labelPosition = "START", labelVisible = true, label = "TRUE", to = "MigrantWorkflowRouteActivity6", from = "MigrantWorkflowRouteActivity4"), @TransitionLayout(to = "MigrantWorkflowRouteActivity4", from = "ReceiveWage"), @TransitionLayout(to = "ReceiveWage", from = "LinkNonMig"), @TransitionLayout(to = "LinkNonMig", from = "MigrantWorkflowRouteActivity1"), @TransitionLayout(to = "Init", from = "MigrantWorkflowRouteActivity5"), @TransitionLayout(to = "Init", from = "MigrantWorkflowRouteActivity3"), @TransitionLayout(to = "Move", from = "AskRet"), @TransitionLayout(labelPosition = "START", labelVisible = true, label = "TRUE", to = "AskRet", from = "MigrantWorkflowRouteActivity5"), @TransitionLayout(labelPosition = "START", labelVisible = true, label = "TRUE", to = "MigrantWorkflowRouteActivity5", from = "MigrantWorkflowRouteActivity3"), @TransitionLayout(to = "MigrantWorkflowRouteActivity3", from = "SendWage"), @TransitionLayout(to = "SendWage", from = "LinkMig"), @TransitionLayout(labelPosition = "START", labelVisible = true, label = "TRUE", to = "LinkMig", from = "MigrantWorkflowRouteActivity1"), @TransitionLayout(to = "MigrantWorkflowRouteActivity1", from = "Init") }, activities = { @ActivityLayout(label = "AskRet", position = "(693,645)", name = "AskRet"), @ActivityLayout(label = "ChooseWall", position = "(952,705)", name = "ChooseWall"), @ActivityLayout(size = "(110,50)", label = "ChooseWhereMig", position = "(802,698)", name = "ChooseWhereMig"), @ActivityLayout(label = "Move", position = "(792,821)", name = "Move"), @ActivityLayout(size = "(75,60)", conditionName = "isPassed", label = "isPassed", position = "(879,630)", name = "MigrantWorkflowRouteActivity7"), @ActivityLayout(size = "(77,60)", conditionName = "wantMig", label = "wantMig", position = "(876,558)", name = "MigrantWorkflowRouteActivity6"), @ActivityLayout(size = "(82,60)", conditionName = "wantReturn", label = "wantReturn", position = "(705,551)", name = "MigrantWorkflowRouteActivity5"), @ActivityLayout(size = "(79,60)", conditionName = "hasMigCost", label = "hasMigCost", position = "(873,477)", name = "MigrantWorkflowRouteActivity4"), @ActivityLayout(size = "(84,60)", conditionName = "hasRetCost", label = "hasRetCost", position = "(710,471)", name = "MigrantWorkflowRouteActivity3"), @ActivityLayout(label = "SendWage", position = "(672,376)", name = "SendWage"), @ActivityLayout(label = "ReceiveWage", position = "(895,381)", name = "ReceiveWage"), @ActivityLayout(size = "(90,88)", conditionName = "isMigrated", label = "isMigrated", position = "(789,198)", name = "MigrantWorkflowRouteActivity1"), @ActivityLayout(label = "LinkNonMig", position = "(896,298)", name = "LinkNonMig"), @ActivityLayout(label = "LinkMig", position = "(670,296)", name = "LinkMig"), @ActivityLayout(label = "Init", position = "(781,116)", name = "Init") })
public class MigrantWorkflow extends WorkflowBehaviour {

	public static final String ASKRET_ACTIVITY = "AskRet";
	public static final String CHOOSEWALL_ACTIVITY = "ChooseWall";
	public static final String CHOOSEWHEREMIG_ACTIVITY = "ChooseWhereMig";
	public static final String MOVE_ACTIVITY = "Move";
	public static final String MIGRANTWORKFLOWROUTEACTIVITY7_ACTIVITY = "MigrantWorkflowRouteActivity7";
	public static final String ISPASSED_CONDITION = "isPassed";
	public static final String MIGRANTWORKFLOWROUTEACTIVITY6_ACTIVITY = "MigrantWorkflowRouteActivity6";
	public static final String WANTMIG_CONDITION = "wantMig";
	public static final String MIGRANTWORKFLOWROUTEACTIVITY5_ACTIVITY = "MigrantWorkflowRouteActivity5";
	public static final String WANTRETURN_CONDITION = "wantReturn";
	public static final String MIGRANTWORKFLOWROUTEACTIVITY4_ACTIVITY = "MigrantWorkflowRouteActivity4";
	public static final String HASMIGCOST_CONDITION = "hasMigCost";
	public static final String MIGRANTWORKFLOWROUTEACTIVITY3_ACTIVITY = "MigrantWorkflowRouteActivity3";
	public static final String HASRETCOST_CONDITION = "hasRetCost";
	public static final String SENDWAGE_ACTIVITY = "SendWage";
	public static final String RECEIVEWAGE_ACTIVITY = "ReceiveWage";
	public static final String MIGRANTWORKFLOWROUTEACTIVITY1_ACTIVITY = "MigrantWorkflowRouteActivity1";
	public static final String ISMIGRATED_CONDITION = "isMigrated";
	public static final String LINKNONMIG_ACTIVITY = "LinkNonMig";
	public static final String LINKMIG_ACTIVITY = "LinkMig";
	public static final String INIT_ACTIVITY = "Init";
	@FormalParameter(mode=FormalParameter.INPUT)
	private int homeI;
	@FormalParameter(mode=FormalParameter.INPUT)
	private int homeJ;
	@FormalParameter(mode=FormalParameter.INPUT)
	private int selfI;
	@FormalParameter(mode=FormalParameter.INPUT)
	private int selfJ;
	private int age = 14;
	private double money = 0.;
	private double wage = 0.;
	private double wageMean = 0.;
	private boolean migrated = false;
	private boolean flagInit = true;
	private final int s = 6;
	private Map<AID,Double> wageLink = new HashMap<AID,Double>();
	public final double migrationCost = 1110.26;
	public final double retMigrationCost = 1715.65;
	private double homepref;
	public final ArrayList<Double> wallStrength = new ArrayList(Arrays.asList(0.108401759,0.107356859,0.110494519,0.09248026,0.08946709,0.09047517,0.08700569,0.096942843,0.105424107,0.104214582,0.114808081,0.092256237,0.122182407,0.145807116,0.140905532,0.138483687,0.139585925,0.146038574,0.224493752,0.226660342,0.3,0.352036397,0.414949549,0.480596866,0.490053616,0.525677089,0.599120696,0.652344811,0.68256128,0.673323083,0.702589213,0.746248011,0.746248011)); //parameter for percentage of migrants not allowed
	private String migDest; //desired destination for migration
	private Map<AID,ArrayList<Double>> linkAID = new ConcurrentHashMap<AID,ArrayList<Double>>();
	public static Map<AID,Map<AID,Integer>> randomLink = new HashMap<AID,Map<AID,Integer>>();
	private int cont = 0;
	@FormalParameter(mode=FormalParameter.INPUT)
	private double alpha;
	private CustomWriteFile writeFile = new CustomWriteFile();
	private boolean flagMig = false;
	private boolean flagWall = false;
	private boolean flagRet = false;
	private String country;
	private int contViaggi = 0;
	public static int contWages = 0;
	public final static ArrayList<Double> MEXpriceindex = new ArrayList(Arrays.asList(0.000421342,0.00048787,0.000629364,0.000739258,0.000873607,0.001103929,0.001412235,0.002244386,0.004528258,0.007496071,0.01182493,0.022022026,0.05105293,0.10933582,0.131211732,0.166183129,0.203842738,0.23545578,0.258416322,0.27641703,0.373160987,0.501444995,0.604875469,0.701220003,0.817521584,0.895145415,0.952099045,1.,1.045481379,1.094496504,1.138140336,1.179447738,1.226234525));
	public final static ArrayList<Double> USApriceindex = new ArrayList(Arrays.asList(0.297943302,0.315730962,0.337409672,0.362423569,0.401889939,0.459699833,0.503613118,0.539188438,0.553085047,0.576431351,0.598110061,0.608671484,0.630906059,0.655919956,0.689827682,0.722067815,0.755975542,0.779321845,0.802668149,0.822679266,0.847693163,0.871039466,0.891050584,0.906058922,0.923846581,0.958310172,0.989438577,1.,1.021122846,1.054474708,1.081156198,1.127848805,1.15815453));
	
	public MigrantWorkflow() {
	}

	public MigrantWorkflow(String activityName) {
		super(activityName);
	}

	public MigrantWorkflow(String activityName, boolean component) {
		super(activityName);
	}

	public boolean isLongRunning() {
		return false;
	}

	private void defineActivities() {
		CodeExecutionBehaviour initActivity = new CodeExecutionBehaviour(INIT_ACTIVITY, this);
		registerActivity(initActivity, INITIAL);
		CodeExecutionBehaviour linkMigActivity = new CodeExecutionBehaviour(LINKMIG_ACTIVITY, this);
		registerActivity(linkMigActivity);
		CodeExecutionBehaviour linkNonMigActivity = new CodeExecutionBehaviour(LINKNONMIG_ACTIVITY, this);
		registerActivity(linkNonMigActivity);
		RouteActivityBehaviour migrantWorkflowRouteActivity1Activity = new RouteActivityBehaviour(
				MIGRANTWORKFLOWROUTEACTIVITY1_ACTIVITY, this);
		registerActivity(migrantWorkflowRouteActivity1Activity);
		CodeExecutionBehaviour receiveWageActivity = new CodeExecutionBehaviour(RECEIVEWAGE_ACTIVITY, this);
		registerActivity(receiveWageActivity);
		CodeExecutionBehaviour sendWageActivity = new CodeExecutionBehaviour(SENDWAGE_ACTIVITY, this);
		registerActivity(sendWageActivity);
		RouteActivityBehaviour migrantWorkflowRouteActivity3Activity = new RouteActivityBehaviour(
				MIGRANTWORKFLOWROUTEACTIVITY3_ACTIVITY, this);
		registerActivity(migrantWorkflowRouteActivity3Activity);
		RouteActivityBehaviour migrantWorkflowRouteActivity4Activity = new RouteActivityBehaviour(
				MIGRANTWORKFLOWROUTEACTIVITY4_ACTIVITY, this);
		registerActivity(migrantWorkflowRouteActivity4Activity);
		RouteActivityBehaviour migrantWorkflowRouteActivity5Activity = new RouteActivityBehaviour(
				MIGRANTWORKFLOWROUTEACTIVITY5_ACTIVITY, this);
		registerActivity(migrantWorkflowRouteActivity5Activity);
		RouteActivityBehaviour migrantWorkflowRouteActivity6Activity = new RouteActivityBehaviour(
				MIGRANTWORKFLOWROUTEACTIVITY6_ACTIVITY, this);
		registerActivity(migrantWorkflowRouteActivity6Activity);
		RouteActivityBehaviour migrantWorkflowRouteActivity7Activity = new RouteActivityBehaviour(
				MIGRANTWORKFLOWROUTEACTIVITY7_ACTIVITY, this);
		registerActivity(migrantWorkflowRouteActivity7Activity);
		CodeExecutionBehaviour moveActivity = new CodeExecutionBehaviour(MOVE_ACTIVITY, this);
		registerActivity(moveActivity);
		CodeExecutionBehaviour chooseWhereMigActivity = new CodeExecutionBehaviour(CHOOSEWHEREMIG_ACTIVITY, this);
		registerActivity(chooseWhereMigActivity);
		CodeExecutionBehaviour chooseWallActivity = new CodeExecutionBehaviour(CHOOSEWALL_ACTIVITY, this);
		registerActivity(chooseWallActivity);
		CodeExecutionBehaviour askRetActivity = new CodeExecutionBehaviour(ASKRET_ACTIVITY, this);
		registerActivity(askRetActivity);
	}

	/**
	 */
	protected void executeInit() throws Exception {
		//System.out.println("INIT " + cont);
		if(cont == 1) {
			flagInit = false;
		}
		
		if(age == 15+32) {
			myAgent.doDelete();
		}
		this.age++;
		
		if(flagInit) {
			for(int i = 0; i < 13; i++) {
				if(this.selfJ < Supervisor.N/2) {
					this.wage = Supervisor.usaWage.get(cont);
				} else {
					this.wage = Supervisor.mexWage.get(cont);
				}
				this.money = this.money + Migrant.skewnormal.get(new Random().nextInt(Migrant.skewnormal.size()))*(wage + alpha*wage);
			}
		}
		
		if(this.selfJ < Supervisor.N/2) {
			this.wage = Supervisor.usaWage.get(cont);
		} else {
			this.wage = Supervisor.mexWage.get(cont);
		}
		
		this.money = money + Migrant.skewnormal.get(new Random().nextInt(Migrant.skewnormal.size()))*(wage + alpha*wage);
		//System.out.println("CONT " + cont + " FLAGINIT " + flagInit + " CONTROL J,N/2 " + selfJ + "," + Supervisor.N/2);
		if(selfJ < Supervisor.N/2) {
			migrated = true;
			if(flagInit) {
				Random r = new Random();
				ArrayList<AID> keyNotMig = new ArrayList<AID>(Migrant.notMigrated.keySet());
				AID auxAID = keyNotMig.get(r.nextInt(keyNotMig.size()));
				if(!MigrantWorkflow.randomLink.containsKey(auxAID)) {
					MigrantWorkflow.randomLink.put(auxAID,new HashMap<AID,Integer>());
				}
				MigrantWorkflow.randomLink.get(auxAID).put(myAgent.getAID(),selfJ);
				String homeKey = getKey(Supervisor.linkComm,auxAID);
				homeI = Integer.parseInt(homeKey.split(",")[0]);
				homeJ = Integer.parseInt(homeKey.split(",")[1]);
				ArrayList<Double> aux_link_list = new ArrayList<Double>();
				aux_link_list.add(Double.valueOf(homeJ));
				aux_link_list.add(1.);
				this.linkAID.put(auxAID,aux_link_list);
			}

		}
		
		if(flagInit) {
			double aux = new Random().nextDouble();
			if(aux < 57.85) {
				homepref = 0;
			}
			if(57.85 < aux && aux < 57.85 + 29.96) {
				homepref = 1;
			}
			if(57.85 + 29.96 < aux && aux < 57.85 + 29.96 + 7.83) {
				homepref = 2;
			}
			if(57.85 + 29.96 + 7.83 < aux && aux < 57.85 + 29.96 + 7.83 + 2.57) {
				homepref = 3;
			}
			if(57.85 + 29.96 + 7.83 + 2.57 < aux && aux <= 1) {
				homepref = 4;
			}
		}
		
		if(selfJ < Supervisor.N/2) {
			country = "USA";
		} else {
			country = "MEXICO";
		}
		
		writeFile.write("STEP " + cont + '\t' + "MIGRATED " + flagMig + '\t' + "ISRETURN " + flagRet + '\t' + "VIAGGI " + contViaggi + '\t' + "COUNTRY " + country + '\t' + "FIRM " + alpha + '\n');
		flagMig = false;
		flagRet = false;
		

		//Clock.eventContMap.computeIfPresent("eventCont", (key,val) -> val + 1);
		//if (Clock.eventContMap.get("eventCont") == Supervisor.n_migrant) {
		//	System.out.println(myAgent.getAID());
		//	System.out.println(Clock.eventContMap.get("eventCont") + " NMIGRANT " + Supervisor.n_migrant);
		ACLMessage infoClock = new ACLMessage();
		infoClock.setPerformative(ACLMessage.INFORM);
		infoClock.setContent("EDDAJE FRA");
		infoClock.addReceiver(Supervisor.linkComm.get("Clock"));
		myAgent.send(infoClock);
		//}
		
		System.out.println(myAgent.getAID() + " GIRO " + cont);
		cont++;
		MessageTemplate cmMT = MessageTemplate.MatchContent("DAJE TU");
		myAgent.blockingReceive(cmMT);
		//System.out.println(a);

		
		if(!flagInit) {
			//System.out.println("IN !FLAGINIT " + cont);
			int auxPrint = linkAID.keySet().size();
			AID[] auxKey = linkAID.keySet().toArray(new AID[linkAID.keySet().size()]);
			for(AID entry : auxKey) {
				String auxStr = getKey(Supervisor.linkComm,entry);
				//System.out.println("IN !FLAGINIT " + getKey(Supervisor.linkComm,entry) + " AID " + entry + " SIZE " + linkAID.keySet().size());
				int auxInt = Integer.parseInt(auxStr.split(",")[1]);
				ArrayList<Double> aux_link_list = new ArrayList<Double>();
				aux_link_list.add(Double.valueOf(auxInt));
				aux_link_list.add(linkAID.get(entry).get(1));
				if((selfJ < Supervisor.N/2 && linkAID.get(entry).get(0) > Supervisor.N/2) || (selfJ > Supervisor.N/2 && linkAID.get(entry).get(0) < Supervisor.N/2)) {
					double auxK = aux_link_list.get(1);
					aux_link_list.remove(1);
					aux_link_list.add(1,auxK - auxK*0.02);
					//System.out.println(auxK);
				} else {
					double auxK = aux_link_list.get(1);
					aux_link_list.remove(1);
					aux_link_list.add(1,auxK + 1);
					//System.out.println(auxK);
				}
				this.linkAID.computeIfPresent(entry, (key,val) -> aux_link_list);
			}
			//System.out.println("EXIT !FLAGINIT " + cont);
			/*if (auxPrint != linkAID.keySet().size()) {
				System.out.println("DIFFERENT SIZE: BEFORE " + auxPrint + '\t' + "AFTER " + linkAID.keySet().size());
			}*/
		}
	}

	
	/**
	 */
	protected boolean checkisMigrated() throws Exception {
		//System.out.println("CHECKISMIGRATED " + cont);
		boolean flagisMig = false;
		if (selfJ < Supervisor.N/2) {
			flagisMig = true;
		}
		//System.out.println("CHECKISMIGRATED OUT" + cont);
		return flagisMig;
	}
	
	/**
	 */
	protected void executeLinkMig() throws Exception {
		//System.out.println("EXECUTELINKMIG " + cont);
		MooreSearch(selfI,selfJ,s);//usa s
		ACLMessage infoClock = new ACLMessage();
		infoClock.setPerformative(ACLMessage.INFORM);
		infoClock.setContent("MOORE SYNC");
		infoClock.addReceiver(Supervisor.linkComm.get("Clock"));
		myAgent.send(infoClock);

		MessageTemplate cmMT = MessageTemplate.MatchContent("VAI MOORE");
		myAgent.blockingReceive(cmMT);
		//System.out.println("EXECUTELINKMIG OUT " + cont);
	}

	/**
	 */
	protected void executeLinkNonMig() throws Exception {
		//System.out.println("EXECUTELINK NON MIG " + cont);
		MooreSearch(selfI,selfJ,1); //usa 1
		//System.out.println(flagInit + " cont " + cont + "has key " + MigrantWorkflow.randomLink.containsKey(myAgent.getAID()) + " key size " + MigrantWorkflow.randomLink.keySet().size());
		if (flagInit && MigrantWorkflow.randomLink.containsKey(myAgent.getAID())) {
			//System.out.println(this.linkAID.keySet().size() + " BY " + myAgent.getAID() + " cont " + cont);
			for (AID entry : MigrantWorkflow.randomLink.get(myAgent.getAID()).keySet()) {
				//System.out.println(entry.getKey() + "," + entry.getValue());
				ArrayList<Double> aux_link_list = new ArrayList<Double>();
				aux_link_list.add(Double.valueOf(MigrantWorkflow.randomLink.get(myAgent.getAID()).get(entry)));
				aux_link_list.add(1.);
				this.linkAID.put(entry,aux_link_list);
			}
			//System.out.println(this.linkAID.keySet().size() + " BY " + myAgent.getAID() + " cont " + cont);
		}
		ACLMessage infoClock = new ACLMessage();
		infoClock.setPerformative(ACLMessage.INFORM);
		infoClock.setContent("MOORE SYNC");
		infoClock.addReceiver(Supervisor.linkComm.get("Clock"));
		myAgent.send(infoClock);

		MessageTemplate cmMT = MessageTemplate.MatchContent("VAI MOORE");
		myAgent.blockingReceive(cmMT);
		//System.out.println("EXECUTELINK NON MIG OUT" + cont);
	}

	private void defineTransitions() {
		registerTransition(new Transition(), INIT_ACTIVITY, MIGRANTWORKFLOWROUTEACTIVITY1_ACTIVITY);
		registerTransition(new Transition(ISMIGRATED_CONDITION, this), MIGRANTWORKFLOWROUTEACTIVITY1_ACTIVITY,
				LINKMIG_ACTIVITY);
		registerTransition(new Transition(), LINKMIG_ACTIVITY, SENDWAGE_ACTIVITY);
		registerTransition(new Transition(), SENDWAGE_ACTIVITY, MIGRANTWORKFLOWROUTEACTIVITY3_ACTIVITY);
		registerTransition(new Transition(HASRETCOST_CONDITION, this), MIGRANTWORKFLOWROUTEACTIVITY3_ACTIVITY,
				MIGRANTWORKFLOWROUTEACTIVITY5_ACTIVITY);
		registerTransition(new Transition(WANTRETURN_CONDITION, this), MIGRANTWORKFLOWROUTEACTIVITY5_ACTIVITY,
				ASKRET_ACTIVITY);
		registerTransition(new Transition(), ASKRET_ACTIVITY, MOVE_ACTIVITY);
		registerTransition(new Transition(), MIGRANTWORKFLOWROUTEACTIVITY3_ACTIVITY, INIT_ACTIVITY);
		registerTransition(new Transition(), MIGRANTWORKFLOWROUTEACTIVITY5_ACTIVITY, INIT_ACTIVITY);
		registerTransition(new Transition(), MIGRANTWORKFLOWROUTEACTIVITY1_ACTIVITY, LINKNONMIG_ACTIVITY);
		registerTransition(new Transition(), LINKNONMIG_ACTIVITY, RECEIVEWAGE_ACTIVITY);
		registerTransition(new Transition(), RECEIVEWAGE_ACTIVITY, MIGRANTWORKFLOWROUTEACTIVITY4_ACTIVITY);
		registerTransition(new Transition(HASMIGCOST_CONDITION, this), MIGRANTWORKFLOWROUTEACTIVITY4_ACTIVITY,
				MIGRANTWORKFLOWROUTEACTIVITY6_ACTIVITY);
		registerTransition(new Transition(WANTMIG_CONDITION, this), MIGRANTWORKFLOWROUTEACTIVITY6_ACTIVITY,
				MIGRANTWORKFLOWROUTEACTIVITY7_ACTIVITY);
		registerTransition(new Transition(ISPASSED_CONDITION, this), MIGRANTWORKFLOWROUTEACTIVITY7_ACTIVITY,
				CHOOSEWHEREMIG_ACTIVITY);
		registerTransition(new Transition(), CHOOSEWHEREMIG_ACTIVITY, MOVE_ACTIVITY);
		registerTransition(new Transition(), MIGRANTWORKFLOWROUTEACTIVITY7_ACTIVITY, CHOOSEWALL_ACTIVITY);
		registerTransition(new Transition(), CHOOSEWALL_ACTIVITY, MOVE_ACTIVITY);
		registerTransition(new Transition(), MIGRANTWORKFLOWROUTEACTIVITY4_ACTIVITY, INIT_ACTIVITY);
		registerTransition(new Transition(), MIGRANTWORKFLOWROUTEACTIVITY6_ACTIVITY, INIT_ACTIVITY);
		registerTransition(new Transition(), MOVE_ACTIVITY, INIT_ACTIVITY);
	}

	/**
	 */
	protected void executeSendWage() throws Exception {
		//System.out.println("SEND WAGE " + cont);
		ACLMessage sendWage = new ACLMessage(ACLMessage.INFORM);
		sendWage.setContent(Double.toString(wage));
		for (AID entry : this.linkAID.keySet()) {
			if(this.linkAID.get(entry).get(0) > Supervisor.N/2) {
				sendWage.addReceiver(entry);
			}
		}
		myAgent.send(sendWage);
		//System.out.println("SEND WAGE EXIT " + cont);
	}
	
	/**
	 */
	protected void executeReceiveWage() throws Exception {
		//System.out.println("ENTER WAGE RECEIVE " + cont + '\t' + myAgent.getAID());
		MessageTemplate rwmt = MessageTemplate.MatchPerformative(ACLMessage.INFORM);
		int contRec = 0;
		for (AID entry : this.linkAID.keySet()) {
			if (this.linkAID.get(entry).get(0) < Supervisor.N/2) {
				contRec++;
			}
		}
		while (contRec > 0) {
			ACLMessage recWage = myAgent.blockingReceive(rwmt);
			wageLink.put(recWage.getSender(),Double.parseDouble(recWage.getContent()));
			contRec--;
		}
		//System.out.println("EXIT WAGE RECEIVE " + cont + '\t' + myAgent.getAID());
	}
	
	/**
	 */
	protected boolean checkhasRetCost() throws Exception {
		//System.out.println("CHEKC HAS RET COST " + cont);
		boolean flagHasRetCost = false;
		double cost = retMigrationCost * USApriceindex.get(cont);
		if (money > cost) {
			flagHasRetCost = true;
		}
		//System.out.println("CHEKC HAS RET COST EXIT " + cont);
		return flagHasRetCost;
	}

	/**
	 */
	protected boolean checkhasMigCost() throws Exception {
		//System.out.println("CHEKC HAS MIG COST " + cont);
		boolean flagHasMigCost = false;
		double cost = retMigrationCost * MEXpriceindex.get(cont);
		if (money > cost) {
			flagHasMigCost = true;
		}
		//System.out.println("CHEKC HAS MIG COST EXIT " + cont);
		return flagHasMigCost;
	}

	/**
	 */
	protected boolean checkwantReturn() throws Exception {
		//System.out.println("checkWantReturn " + cont);
		double retProb = 0;
		double q0,q1,q2;
		q0 = 0.38;
		q1 = 0.12;
		q2 = 0.;
		double a = 0.;
        for (AID entry : linkAID.keySet()) {
        	if(linkAID.get(entry).get(0) < Supervisor.N/2) {
        		a += q1/linkAID.get(entry).get(1);
        	}
        }
        
        if(this.age > 18 && this.age < 30) {
        	q2 = 0.064;
        }
        if(this.age > 30) {
        	q2 = 0.048;
        }
        retProb = q0 + a + q2;
        //System.out.println("RET " + retProb);
        boolean flagRet = false;
        double auxRand = new Random().nextDouble();
        if (auxRand > retProb) {
        	flagRet = true;
        }
        //System.out.println("checkWantReturn EXIT " + cont);
		return flagRet;
	}

	/**
	 */
	protected boolean checkwantMig() throws Exception {
		//System.out.println("checkWantMig");
		double migProb = 0;
        double p0,p1,p2,p4,pMig;
        p0 = 0.1;
        p1 = 0.011;
        p2 = 0.2;
        p4 = 0.;
        if(this.age > 18 && this.age < 30) {
        	p4 = 0.008;
        }
        if(this.age > 30 && this.age < 45) {
        	p4 = -0.003;
        }
        if(this.age > 45) {
        	p4 = -0.011;
        }
        int Moore = 0; //numero vicini di Moore
        for (AID entry : linkAID.keySet()) {
        	if(linkAID.get(entry).get(0) < Supervisor.N/2) {
        		Moore++;
        	}
        }
        for (AID entry : wageLink.keySet()) {
        	wageMean += wageLink.get(entry)/wageLink.keySet().size();
        }
        if(migrated) {
        	pMig = 0.0000011;
        } else {
        	pMig = 0.0000035;
        }
        
        migProb = p0 + pMig*p1*(this.wageMean - this.wage) + p2*(this.linkAID.size() - Moore) - this.homepref + p4;
        wageMean = 0;
        //System.out.println("MIG " + migProb);
        boolean flagMig = false;
        double auxRand = new Random().nextDouble();
        if (auxRand > migProb) {
        	flagMig = true;
        }
		//System.out.println("checkWantMig OUT");
		return flagMig;
	}
	
	/**
	 */
	protected boolean checkisPassed() throws Exception {
		//System.out.println("CHECKIS PASSED " + cont);
        boolean flagWall = false;
        double auxRand = new Random().nextDouble();
        if (auxRand > wallStrength.get(cont)) {
        	flagWall = true;
        }
        //System.out.println("CHECKIS PASSED OUT " + cont);
		return flagWall;
	}

	/**
	 */
	protected void executeChooseWhereMig() throws Exception {
		//System.out.println("executeChooseWhereMig");
		AID AID_dest = new AID();
		double auxWage = 0.;
		for (AID entry : wageLink.keySet()) {
			if(wageLink.get(entry) > auxWage) {
				auxWage = wageLink.get(entry);
				AID_dest = entry;
			}
		}
		
		if(auxWage > 0.) {
			this.migDest = getKey(Supervisor.linkComm,AID_dest);
		} else {
			int rndrow = new Random().nextInt(Supervisor.N);
			int rndcol = new Random().nextInt(Supervisor.N/2);
			this.migDest = rndrow+","+rndcol;
		}
		
		//System.out.println("WAGEMAX " + auxWage + " DEST " + this.migDest);
		ACLMessage askMig = new ACLMessage(ACLMessage.REQUEST);
		askMig.setProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST);
		askMig.addReceiver(Supervisor.linkComm.get("Supervisor"));
		//System.out.println("DEST " + this.migDest + "AID " + AID_dest + "AUXWAGE " + auxWage + "KEYSET " + wageLink.keySet());
		askMig.setContent(this.selfI + "," + this.selfJ + "," + this.migDest);
		//System.out.println("WHEREMIG " + myAgent.getAID() + " MIGDEST " + this.migDest);
		myAgent.send(askMig);
		//System.out.println("ASKED SUPERVISOR " + myAgent.getAID());
	}

	/**
	 */
	protected void executeChooseWall() throws Exception {
		//System.out.println("executeChooseWall");
		flagWall = true;
		String auxString = Integer.toString(Supervisor.N/2+1);
		this.migDest = selfI+","+auxString;
		ACLMessage askMig = new ACLMessage(ACLMessage.REQUEST);
		askMig.setProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST);
		askMig.addReceiver(Supervisor.linkComm.get("Supervisor"));
		askMig.setContent(this.selfI + "," + this.selfJ + "," + this.migDest);
		//System.out.println("WALLMIG " + myAgent.getAID() + " MIGDEST " + this.migDest);
		myAgent.send(askMig);
		//System.out.println("ASKED SUPERVISOR " + myAgent.getAID());
	}
	
	/**
	 */
	protected void executeAskRet() throws Exception {
		//System.out.println("executeAskRet");
		flagRet = true;
		this.migDest = homeI+","+homeJ;
		ACLMessage askMig = new ACLMessage(ACLMessage.REQUEST);
		askMig.setProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST);
		askMig.addReceiver(Supervisor.linkComm.get("Supervisor"));
		askMig.setContent(this.selfI + "," + this.selfJ + "," + this.migDest);
		myAgent.send(askMig);
		//System.out.println("ASKED SUPERVISOR " + myAgent.getAID());
	}
	
	/**
	 */
	protected void executeMove() throws Exception {
		//System.out.println("ENTERED MOVE " + cont);
		if (selfJ < Supervisor.N/2) {
			money = money - USApriceindex.get(cont)*retMigrationCost;
		} else {
			money = money - MEXpriceindex.get(cont)*migrationCost;
		}
		MessageTemplate pMMT = MessageTemplate.MatchSender(Supervisor.linkComm.get("Supervisor"));
		ACLMessage posMig = myAgent.blockingReceive(pMMT);
		Supervisor.linkComm.remove(selfI+","+selfJ);
		//System.out.println("R E M O V E D " + selfI+","+selfJ);
		if(posMig != null) {
			this.selfI = Integer.parseInt(posMig.getContent().split(",",3)[0]);
			this.selfJ = Integer.parseInt(posMig.getContent().split(",",3)[1]);
			this.alpha = Double.parseDouble(posMig.getContent().split(",",3)[2]);
		}
		String auxStr = selfI+","+selfJ;
		Supervisor.linkComm.put(auxStr,myAgent.getAID());
		//System.out.println("PUT IN MAP " + auxStr + " AID " + myAgent.getAID());
		if(flagWall == false) {
			this.flagMig = true;
			contViaggi++;
		}
		flagWall = false;
		migrated = true;
		//System.out.println("EXITED MOVE " + cont);
		//System.out.println("MESSAGE RECEIVED BY " + myAgent.getAID() + " FROM SUPERVISOR: " + posMig);
	}
	
	//
	//UTILITY
	//
	
	//vicini di Moore
	public void MooreSearch(int x, int y, int s) {
		if(y > Supervisor.N/2) {
			for (int i = x - s; i < x + s + 1; i++) {
				for (int j = y - s; j < y + s + 1; j++) {
					if ((i != x || j != y) && j > Supervisor.N/2) {
						try {
							if(Supervisor.map[i][j] == 3) {
								ArrayList<Double> aux_link_list = new ArrayList<Double>();
								aux_link_list.add(Double.valueOf(j));
								aux_link_list.add(1.);
								String auxStr = i + "," + j;
								this.linkAID.put(Supervisor.linkComm.get(auxStr),aux_link_list);
							}
						} catch(ArrayIndexOutOfBoundsException e5) {
							//bad practice but hey *shrug*
						}
					}
				}
			}
		} else if(y < Supervisor.N/2) {
			for (int i = x - s; i < x + s + 1; i++) {
				for (int j = y - s; j < y + s + 1; j++) {
					if ((i != x || j != y) && j < Supervisor.N/2) {
						try {
							if(Supervisor.map[i][j] == 3) {
								ArrayList<Double> aux_link_list = new ArrayList<Double>();
								aux_link_list.add(Double.valueOf(j));
								aux_link_list.add(1.);
								String auxStr = i + "," + j;
								this.linkAID.put(Supervisor.linkComm.get(auxStr),aux_link_list);
							}
						} catch(ArrayIndexOutOfBoundsException e5) {
							//bad practice but hey *shrug*
						}
					}
				}
			}
		}
	}
	
	//get key from value
	public <K, V> K getKey(Map<K, V> map, V value) {
	    for (Entry<K, V> entry : map.entrySet()) {
	        if (entry.getValue().equals(value)) {
	            return entry.getKey();
	        }
	    }
	    return null;
	}



}
