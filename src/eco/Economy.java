package eco;

/**
* This class describes the international market for wheat,
* it manages wheat reserve goals and sells or buys to
* meet it.
*
* @Author phil
*/

public class Economy {

	private static int treasury = 0;

	private static int wheatPrice = 10;

	public static int buyWheat(int ammount){
		int neededMoney = wheatPrice * ammount;
		if (neededMoney <= treasury){
			treasury -= neededMoney;
			return ammount;
		}
		else{
			int canBuy = treasury / wheatPrice;
			treasury = 0;
			return canBuy;		
		}
	}

	public static int sellWheat(int ammount){
		treasury += wheatPrice * ammount;
		return ammount;
	}

	public static void updateMarket(int time){
		wheatPrice += Util.randInt(-5, 7); // Biased to increase over time
	}

	public static int getTreasury(){
		return treasury;	
	}

	public static int getPrice(){
		return wheatPrice;
	}	

}
