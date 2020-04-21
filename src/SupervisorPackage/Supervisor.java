package SupervisorPackage;

import supervisor.SupervisorWorkflow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import com.tilab.wade.commons.AgentInitializationException;
import com.tilab.wade.dispatcher.DispatchingCapabilities;
import com.tilab.wade.performer.WorkflowEngineAgent;
import com.tilab.wade.performer.WorkflowException;
import com.tilab.wade.performer.descriptors.WorkflowDescriptor;
import jade.core.AID;

public class Supervisor extends WorkflowEngineAgent {
	
	public static int N;
	public static int[][] map;
	private int n_firm;
	public static int n_migrant;
	private DispatchingCapabilities dcS = new DispatchingCapabilities();
	public static ArrayList<Integer> migI,migJ;
	public static Map<String,Integer> contHomeMap = new ConcurrentHashMap<String,Integer>();
	public static Map<String,AID> linkComm = new ConcurrentHashMap<String,AID>();
	public static Map<String,Double> firm = new HashMap<String,Double>();
	public final static ArrayList<Double> usaWage = new ArrayList(Arrays.asList(8173.44,8768.16,9374.4,10104.,10833.6,11573.76,12570.72,13108.32,13736.64,14304.,14641.44,14873.76,15223.68,15677.76,16228.8,16788.,17208.48,17676.,18187.68,18778.56,19203.36,19837.44,20729.28,21530.88,22231.2,23088.48,23701.92,24324.,24866.88,25396.32,26127.84,27257.76,28321.92));
	public final static ArrayList<Double> mexWage = new ArrayList(Arrays.asList(2648.266423,2840.960932,3037.388023,3273.784837,3510.181652,3750.,4330.,4320.,4260.,4510.,4720.,4510.,4680.,5170.,5490.,5900.,6280.,6550.,6690.,7000.,6490.,6860.,7410.,7740.,8070.,8780.,8890.,9140.,10480.,11140.,11980.,13060.,13890.));
	
	protected void agentSpecificSetup() throws AgentInitializationException{
		
		super.agentSpecificSetup();
		dcS.init(this);
		
		Supervisor.contHomeMap.put("contHome", 0);
		
		Supervisor.N = 33;
		this.n_firm = 58+199;
		this.n_migrant = 200;
		
		this.map = new int[N][N+1];
		
		//fill with void pathces
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N+1; j++) {
				this.map[i][j] = 0;
			}
		}
		
		//wall positioning
		for (int i = 0; i < N; i++) {
			this.map[i][N/2] = 1;
		}
		
		//firms positioning
		/*int j = this.n_firm;
		while (j>0) {
			int rndrow = new Random().nextInt(N);
			int rndcol = new Random().nextInt(N+1);
			if (this.map[rndrow][rndcol] == 0) {
				this.map[rndrow][rndcol] = 2;
				j--;
				double alpha = new Random().nextGaussian()*0.28*0.28;
				firm.put(rndrow+","+rndcol, alpha);
			}
			
		}*/
		
		for (int i = 0; i < 58; i++) {
			int rndrow = new Random().nextInt(N);
			int rndcol = new Random().nextInt(N/2);
			if (map[rndrow][rndcol] == 0) {
				map[rndrow][rndcol] = 2;
				double alpha = new Random().nextGaussian()*0.28*0.28;
				firm.put(rndrow+","+rndcol, alpha);
			}
		}
		
		for (int i = 0; i < 199; i++) {
			int rndrow = new Random().nextInt(N);
			int rndcol = new Random().nextInt(N/2) + N/2 + 1;
			if (map[rndrow][rndcol] == 0) {
				map[rndrow][rndcol] = 2;
				double alpha = new Random().nextGaussian()*0.28*0.28;
				firm.put(rndrow+","+rndcol, alpha);
			}
		}
		
		Supervisor.migI = new ArrayList<Integer>();
		Supervisor.migJ = new ArrayList<Integer>();
		//migrants positioning
		int k = (int) (this.n_migrant*0.944);
		int aux = n_migrant - k;
		while (k > 0) {
			int rndrow = new Random().nextInt(N);
			int rndcol = new Random().nextInt(N/2) + N/2 +1;
			if (this.map[rndrow][rndcol] == 0) {
				this.map[rndrow][rndcol] = 3;
				Supervisor.migI.add(rndrow);
				Supervisor.migJ.add(rndcol);
				k--;
			}
		}
		
		while (aux>0) {
			int rndrow = new Random().nextInt(N);
			int rndcol = new Random().nextInt(N/2);
			if (this.map[rndrow][rndcol] == 0) {
				this.map[rndrow][rndcol] = 3;
				Supervisor.migI.add(rndrow);
				Supervisor.migJ.add(rndcol);
				aux--;
			}
		}

		Supervisor.linkComm.put("Supervisor", this.getAID());
		
		WorkflowDescriptor wd = new WorkflowDescriptor("supervisor.SupervisorWorkflow");
		try {
			dcS.launchWorkflow(getAID(), wd, null, null);
		} catch (WorkflowException e) {
			e.printStackTrace();
		}
		
	}
	
}