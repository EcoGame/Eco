package eco.game;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

/**
 * A class that contains various utilities and convenience methods
 * 
 * @author phil, nate, connor, will
 * 
 */

public class Util {

	private static Random random = new Random();

	public static void createSave() {
        
        if (Main.currentSave == 1) {
            if(Main.saveName1.contains(" ")) {
                Main.saveName1 = Main.saveName1.replace(" ", "@");
            }
        }
        if (Main.currentSave == 2) {
            if(Main.saveName2.contains(" ")) {
                Main.saveName2 = Main.saveName2.replace(" ", "@");
            }
        }
        if (Main.currentSave == 3) {
            if(Main.saveName3.contains(" ")) {
                Main.saveName3 = Main.saveName3.replace(" ", "@");
            }
        }
        if (Main.currentSave == 4) {
            if(Main.saveName4.contains(" ")) {
                Main.saveName4 = Main.saveName4.replace(" ", "@");
            }
        }
        if (Main.currentSave == 5) {
            if(Main.saveName5.contains(" ")) {
                Main.saveName5 = Main.saveName5.replace(" ", "@");
            }
        }

		String path = null;
		try {
			path = "saves/" + Main.currentSave + ".txt";
			if (!Main.isInEclipse) {
				path = "../" + path;
			}
			File fOut = new File(path);
			FileOutputStream FOS = new FileOutputStream(fOut);
			BufferedWriter BW = new BufferedWriter(new OutputStreamWriter(FOS));

			// Data Being Saved:
			if (Main.currentSave == 1) {
				BW.write(Main.saveName1);
				BW.newLine();
			}
			if (Main.currentSave == 2) {
				BW.write(Main.saveName2);
				BW.newLine();
			}
			if (Main.currentSave == 3) {
				BW.write(Main.saveName3);
				BW.newLine();
			}
			if (Main.currentSave == 4) {
				BW.write(Main.saveName4);
				BW.newLine();
			}
			if (Main.currentSave == 5) {
				BW.write(Main.saveName5);
				BW.newLine();
			}

			BW.write(Integer.toString(PlayerCountry.year));
			BW.newLine();
			BW.write(Integer.toString(PlayerCountry.wheat.gettWheat()));
			BW.newLine();
			BW.write(Integer.toString(PlayerCountry.farmer.getfPop()));
			BW.newLine();
			BW.write(Integer.toString(PlayerCountry.warrior.getwPop()));
			BW.newLine();
			BW.write(Integer.toString(PlayerCountry.land.getLand()));
			BW.newLine();
			BW.write(Integer.toString(PlayerCountry.land.getPop()));
			BW.newLine();
			for (int x = 0; x < World.mapsize; x++) {
				for (int y = 0; y < World.mapsize; y++) {
					BW.write(Short.toString(World.map[x][y]));
				}
				BW.newLine();
			}
			for (int x = 0; x < World.mapsize; x++) {
				for (int y = 0; y < World.mapsize; y++) {
					BW.write(Float.toString(World.noise[x][y]) + ",");
				}
				BW.newLine();
			}
			for (int x = 0; x < World.mapsize; x++) {
				for (int y = 0; y < World.mapsize; y++) {
					BW.write(Short.toString(World.structures[x][y]) + ",");
				}
				BW.newLine();
			}
			for (int x = 0; x < World.mapsize; x++) {
				for (int y = 0; y < World.mapsize; y++) {
					BW.write(Short.toString(World.popmap[x][y]) + ",");
				}
				BW.newLine();
			}
			for (int x = 0; x < World.mapsize; x++) {
				for (int y = 0; y < World.mapsize; y++) {
					BW.write(Short.toString(World.popdensity[x][y]) + ",");
				}
				BW.newLine();
			}
			for (int x = 0; x < World.mapsize; x++) {
				for (int y = 0; y < World.mapsize; y++) {
					BW.write(Short.toString(World.decorations[x][y]) + ",");
				}
				BW.newLine();
			}
			/*
			 * Use: BW.write(STUFF TO BE SAVED HERE); BW.newLine();
			 * 
			 * Unless it needs to use loops in which case see the loops above.
			 */

			BW.close();
		} catch (IOException ex) {
			System.out.println("IOException");
		}

	}

	public static void readSave() {
        
        if (Main.currentSave == 1) {
            if(Main.saveName1.contains("@")) {
                Main.saveName1 = Main.saveName1.replace("@", " ");
            }
        }
        if (Main.currentSave == 2) {
            if(Main.saveName2.contains("@")) {
                Main.saveName2 = Main.saveName1.replace("@", " ");
            }
        }
        if (Main.currentSave == 3) {
            if(Main.saveName3.contains("@")) {
                Main.saveName3 = Main.saveName3.replace("@", " ");
            }
        }
        if (Main.currentSave == 4) {
            if(Main.saveName4.contains("@")) {
                Main.saveName4 = Main.saveName4.replace("@", " ");
            }
        }
        if (Main.currentSave == 5) {
            if(Main.saveName5.contains("@")) {
                Main.saveName5 = Main.saveName1.replace("@", " ");
            }
        }
		String path = "";
		@SuppressWarnings("unused")
		File name = null;
		path = "saves/" + Main.currentSave + ".txt";
		name = new File(Main.saveName1 + ".txt");
		if (!Main.isInEclipse) {
			path = "../" + path;
		}
		Scanner s = null;
		try {
			s = new Scanner(new File(path));
		} catch (FileNotFoundException e) {
			System.out.println("File Not Found");
			return;
		}
		ArrayList<String> list = new ArrayList<String>();
		try {
			while (s.hasNext()) {
				list.add(s.nextLine());
			}
			s.close();
		} catch (Exception e) {
			readError();
			return;
		}
		try {
			for (String str : list) {
				str = str.replace(System.getProperty("line.separator"), "");
			}

			// Information being loaded:
			if (Main.currentSave == 1) {
				Main.saveName1 = list.get(0);
			}
			if (Main.currentSave == 2) {
				Main.saveName2 = list.get(0);
			}
			if (Main.currentSave == 3) {
				Main.saveName3 = list.get(0);
			}
			if (Main.currentSave == 4) {
				Main.saveName4 = list.get(0);
			}
			if (Main.currentSave == 5) {
				Main.saveName5 = list.get(0);
			}
			PlayerCountry.year = Integer.valueOf(list.get(1));
			PlayerCountry.wheat.settWheat(Integer.valueOf(list.get(2)));
			PlayerCountry.farmer.setfPop(Integer.valueOf(list.get(3)));
			PlayerCountry.warrior.setwPop(Integer.valueOf(list.get(4)));
			PlayerCountry.land.setLand(Integer.valueOf(list.get(5)));
			PlayerCountry.land.setPop(Integer.valueOf(list.get(6)));
			int line = 7;
			for (int x = 0; x < World.mapsize; x++) {
				String values = list.get(line);
				for (int y = 0; y < World.mapsize; y++) {
					World.map[x][y] = Short.valueOf(values.substring(y, y + 1));
				}
				line++;
			}
			for (int x = 0; x < World.mapsize; x++) {
				String values = list.get(line);
				String[] parts = values.split(",");
				for (int y = 0; y < World.mapsize; y++) {
					World.noise[x][y] = Float.valueOf((parts[y]));
				}
				line++;
			}
			for (int x = 0; x < World.mapsize; x++) {
				String values = list.get(line);
				String[] parts = values.split(",");
				for (int y = 0; y < World.mapsize; y++) {
					World.structures[x][y] = Short.valueOf((parts[y]));
				}
				line++;
			}
			for (int x = 0; x < World.mapsize; x++) {
				String values = list.get(line);
				String[] parts = values.split(",");
				for (int y = 0; y < World.mapsize; y++) {
					World.popmap[x][y] = Short.valueOf((parts[y]));
				}
				line++;
			}
			for (int x = 0; x < World.mapsize; x++) {
				String values = list.get(line);
				String[] parts = values.split(",");
				for (int y = 0; y < World.mapsize; y++) {
					World.popdensity[x][y] = Short.valueOf((parts[y]));
				}
				line++;
			}
			for (int x = 0; x < World.mapsize; x++) {
				String values = list.get(line);
				String[] parts = values.split(",");
				for (int y = 0; y < World.mapsize; y++) {
					World.decorations[x][y] = Short.valueOf((parts[y]));
				}
				line++;
			}
			// Set the variable that the information will become
			// To the end here.

			//readSuccess();
		} catch (Exception e) {
			e.printStackTrace();
			readError();
		}
		return;
	}

	public static void readError() {
		Message.addMessage(new Message(
				"------------------------------------------", 100, 100, 300));
		Message.addMessage(new Message("Failed to load save!", 100, 130, 300));
		Message.addMessage(new Message(
				"The file either disappeared or is corrupt!", 100, 160, 300));
		Message.addMessage(new Message(
				"------------------------------------------", 100, 190, 300));
	}

	public static void readSuccess() {
		Message.addMessage(new Message("----------------------------------",
				100, 100, 300));
		Message.addMessage(new Message("Loaded game state from save file!",
				100, 130, 300));
		Message.addMessage(new Message("----------------------------------",
				100, 160, 300));
	}

	public static int randInt(int max) { // Returns a random number below max.

		return random.nextInt(max);

	}

	public static int randInt(int min, int max) { // Returns a random number
													// between min and max.

		return min + random.nextInt((max + 1) - min);

	}
    
    
    public static float randFloat(float min, float max){
        return random.nextFloat() * (max - min) + min;
    }
    
	    
	public static void takeScreenshot() {
		GL11.glReadBuffer(GL11.GL_FRONT);
		int width = Display.getDisplayMode().getWidth();
		int height = Display.getDisplayMode().getHeight();
		int bpp = 4;
		ByteBuffer buffer = BufferUtils.createByteBuffer(width * height * bpp);
		GL11.glReadPixels(0, 0, width, height, GL11.GL_RGBA,
				GL11.GL_UNSIGNED_BYTE, buffer);

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd H:mm:ss");
		Date date = new Date(System.currentTimeMillis());
		File file = new File("../screenshots/" + dateFormat.format(date));
		file.mkdirs();
		String format = "PNG";
		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);

		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				int i = (x + (width * y)) * bpp;
				int r = buffer.get(i) & 0xFF;
				int g = buffer.get(i + 1) & 0xFF;
				int b = buffer.get(i + 2) & 0xFF;
				image.setRGB(x, height - (y + 1), (0xFF << 24) | (r << 16)
						| (g << 8) | b);
			}
		}

		try {
			ImageIO.write(image, format, file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static int computeTotalHunger() {
		return PlayerCountry.farmer.getTotalHunger()
				+ PlayerCountry.warrior.getTotalHunger()
				+ ((int) (PlayerCountry.farmer.getfHunger() * World.displacedPeople / 2f));
	}

	public static String getWheatRateForDisplay() {
		int hunger = computeTotalHunger();
		int input = PlayerCountry.farmer.getWheatProductionRate()
				* PlayerCountry.farmer.getfPop();
		input += PlayerCountry.land.getWheatRate();
		int total = input - hunger;
		if (total < 0) {
			return "dW/dT: " + String.valueOf(total) + " Bushels";
		} else if (total == 0) {
			return "dW/dT: " + "0 Bushels";
		} else {
			return "dW/dT: +" + String.valueOf(total) + " Bushels";
		}
	}

	public static int getWheatRate() {
		int hunger = computeTotalHunger();
		int input = PlayerCountry.farmer.getWheatProductionRate()
				* PlayerCountry.farmer.getfPop();
		input += PlayerCountry.land.getWheatRate();
		int total = input - hunger;
		return total;
	}

	public static float getTotalPopf() {
		return PlayerCountry.warrior.getFloatWPop() + PlayerCountry.farmer.getFloatFPop();
	}

	public static int getTotalPop() {
		return PlayerCountry.warrior.getwPop() + PlayerCountry.farmer.getfPop();
	}

	public static boolean doesSaveExist(int currentSave) {
		String path = "";
		File name = null;
		path = "saves/" + currentSave + ".txt";
		name = new File(currentSave + ".txt");
		if (!Main.isInEclipse) {
			path = "../" + path;
		}
		name = new File(path);
		if (name.exists()) {
			return true;
		}
		return false;
	}

	public static Treble<Float, Float, Float> convertColor(
			Treble<Float, Float, Float> base) {
		return new Treble<Float, Float, Float>(base.x / 255f, base.y / 255f,
				base.z / 255f);
	}

	public static String loadSaveName(int currentSave) {
		String path = "";
		@SuppressWarnings("unused")
		File name = null;
		path = "saves/" + currentSave + ".txt";
		if (!Main.isInEclipse) {
			path = "../" + path;
		}
		Scanner s = null;
		try {
			s = new Scanner(new File(path));
		} catch (FileNotFoundException e) {
			System.out.println("File Not Found");
			return "";
		}
		ArrayList<String> list = new ArrayList<String>();
		try {
			while (s.hasNext()) {
				list.add(s.next());
			}
			s.close();
		} catch (Exception e) {
			readError();
			return "";
		}
		try {
			for (String str : list) {
				str = str.replace(System.getProperty("line.separator"), "");
			}

			// Information being loaded:
			/*
			 * if (currentSave == 1){ Main.saveName1 = list.get(0); } if
			 * (currentSave == 2){ Main.saveName2 = list.get(0); } if
			 * (currentSave == 3){ Main.saveName3 = list.get(0); } if
			 * (currentSave == 4){ Main.saveName4 = list.get(0); } if
			 * (currentSave == 5){ Main.saveName5 = list.get(0); }
			 */
			return list.get(0);
		} catch (Exception e) {
			e.printStackTrace();
			readError();
		}
		return "";
	}

	public static void deleteSave(int save){
		String path = "";
		File name = null;
		path = "saves/" + save + ".txt";
		if (!Main.isInEclipse) {
			path = "../" + path;
		}
		name = new File(path);
		if (name.exists()){
			name.delete();
			Menu.initMenu();
		}
	}
    
    public static Country[] getCountries(){
        return PlayerCountry.countries.toArray(new Country[PlayerCountry.countries.size()]);
    }
    
    public static void putCountries(Country[] toPut){
        PlayerCountry.countries.clear();
        for (Country c : toPut){
            PlayerCountry.countries.add(c);
        }
    }
	
}
