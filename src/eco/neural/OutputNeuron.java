package eco.neural;

/**
 * This class does something
 * 
 * @author will
 * 
 */

public class OutputNeuron {

	public int fireValue = 2;
	public int currentValue;
	public int fired;
	public int id;
	public int pairedAxon;
	public int action = 0;
	public boolean alreadyFired = false;

	public void addToCurrent(int x) {

		currentValue = x + currentValue;

	}

	public int fireCheck(int currentnetwork) {
		fired = 0;
		if (currentValue > fireValue) {
			fired = 1;
			// System.out.println("outputneuron " + id + " pairedaxon "+
			// pairedaxon + " current value "+ currentvalue);
			alreadyFired = true;
			OutputActions.actions(action, currentnetwork);
		}
		return fired;
	}

	public void reset() {
		currentValue = 0;
		fired = 0;
		alreadyFired = false;
	}

}
