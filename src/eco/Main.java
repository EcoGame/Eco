package eco;

import java.util.Random;

import eco.Eco;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

//http://www222.pair.com/sjohn/blueroom/demog.htm

public class Main {

    public static Random random = new Random();
    public static final int fov = 90;
	public static final int height = 620;
	public static final int width = 854;
	public static volatile int year = 1;
	public static volatile int wheatPrice = 20;
    public static volatile int oldtWheat = 0;
	public volatile static int tAcres = 1000;
    public static volatile float fBirthRate = 0.03f;
    public static volatile float fDeathRate = 0.02f;
    public static volatile float wBirthRate = 0.008f;
    public static volatile float wDeathRate = 0.002f;
    public static int unusedpops = 0;
    public static int[] unfilledpops = new int[10000];
    public static Pops[] popArray = new Pops[10000];
    public static int newpops = 0;
    public static int uneatenwheat = 0;
    public static int unusedarray = 2;
    public static int unusedacres = 0;
    public static int aggDemand;

	public static void main(String[] args) {

		System.out.println("Welcome to EcoLand!");
        init();
        tick();

	}

    public static void tick(){

        //  System.out.println("TOCK");
        for(int i = 1; i < 2000; i++) {
            year = i; //One tick is 1 year
            popManger.popController();
            int farmPacks = Wheat.farmPacks(tAcres);
            int tPop = popManger.fPopulation + popManger.wPopulation;
            int unemployedFarmers = Wheat.unemployedFarmers(farmPacks, popManger.fPopulation);
            int employedFarmers = Wheat.employedFarmers(popManger.fPopulation, unemployedFarmers);
            Warrior.wHunger(popManger.wPopulation);
            Wheat.tWheat(employedFarmers);
            aggDemand = ((Farmer.fHunger * popManger.fPopulation) + (Warrior.wHunger * popManger.wPopulation));
            wheatPrice = Market.wheatPrice(wheatPrice);
            int tMoney = Money.tMoney(uneatenwheat, wheatPrice);
            System.out.println("Year: " + year);
            System.out.println("Wheat Produced this year: " + uneatenwheat);
            System.out.println("Price of wheat: " + wheatPrice);
            System.out.println("Total number of Warriors: " + popManger.wPopulation);
            System.out.println("Total number of Farmers: " + popManger.fPopulation);
            System.out.println("Avaible Acres" + unusedacres);
            System.out.println(Main.unusedpops);
            System.out.println("        Unemployed Farmers: " + unemployedFarmers);
            System.out.println("        Employed Farmers: " + employedFarmers);
            System.out.println("Total Population: " + tPop);
            System.out.println("Money in the Treasury: " + tMoney);
            System.out.println("\n");
            oldtWheat = Wheat.tWheat;

            //Render.drawString("yourmessage", 10, 10);
            popManger.tPop = Eco.tryToUpdatePop();
            tAcres= Eco.tick(year);

        }
        Eco.simDone();
    }


    public static void init() {

        Eco.init();
        Util.readSave();
        popManger.initpops();

	}

	public static int randInt(int max) { //Returns a random number below max.

        return random.nextInt(max);

	}

    public static int randInt(int min, int max) { //Returns a random number between min and max.

        return min + random.nextInt((max + 1)- min);

	}

    public static void ioUpdate(){

        if (Keyboard.isKeyDown(Keyboard.KEY_R)) {
			Render.rot -= 0.5f;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_F)) {
			Render.rot += 0.5f;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
			Render.z += 0.1f;
			Render.camera.moveForward(0.1f);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
			Render.x -= 0.1f;
			Render.camera.moveRight(0.1f);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
			Render.z -= 0.1f;
			Render.camera.moveBack(0.1f);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
			Render.x += 0.1f;
			Render.camera.moveLeft(0.1f);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
			//Render.y += 0.01f;
			Render.camera.moveDown(0.1f);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
			//Render.y -= 0.01f;
			Render.camera.moveUp(0.1f);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_O)) {
			World.messages.add(new Message("messages", 10, 10, 300));
		}

	}

}
