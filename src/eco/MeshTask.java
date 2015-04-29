package eco;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

/**
 * This class is an implementation of <i>Runnable</i> and is used to
 * asynchronously mesh the world's geometry. The only synchronized part is
 * actually buffering the data, because openGL is single threaded we must take
 * the rendering context.
 * 
 * @author phil
 * 
 */

public class MeshTask implements Runnable {

	private static float tilesize = Render.tilesize;
	private static float heightConstant = Render.heightConstant;
	
	private static final float offset = Render.tilesize / 2f;
	
	private static float[] vertex = new float[0x2000000];
	private static float[] texture = new float[0x2000000];
	
	private static int index = 0;

	@Override
	public void run() {
		index = 0;

		for (int x = 0; x < World.mapsize; x++) {
			for (int y = 0; y < World.mapsize; y++) {
				float height = World.noise[x][y] * heightConstant;
				if (World.map[x][y] == 0) {
					height = 48 * heightConstant;
					drawTile(x, y, height, 0, 0);
					if (World.getHeight(x + 1, y) < World.getHeight(x, y)) {
						float diff = World.getHeight(x, y)
								- World.getHeight(x + 1, y);
						diff *= Render.heightConstant;
						drawTileN(x, y, height, diff, 0, 0);
					}
					if (World.getHeight(x - 1, y) < World.getHeight(x, y)) {
						float diff = World.getHeight(x, y)
								- World.getHeight(x - 1, y);
						diff *= Render.heightConstant;
						drawTileS(x, y, height, diff, 0, 0);
					}
					if (World.getHeight(x, y + 1) < World.getHeight(x, y)) {
						float diff = World.getHeight(x, y)
								- World.getHeight(x, y + 1);
						diff *= Render.heightConstant;
						drawTileW(x, y, height, diff, 0, 0);
					}
					if (World.getHeight(x, y - 1) < World.getHeight(x, y)) {
						float diff = World.getHeight(x, y)
								- World.getHeight(x, y - 1);
						diff *= Render.heightConstant;
						drawTileE(x, y, height, diff, 0, 0);
					}
				}
				if (World.map[x][y] == 1) {
					drawTile(x, y, height, 1, 0);
					if (World.getHeight(x + 1, y) < World.getHeight(x, y)) {
						float diff = World.getHeight(x, y)
								- World.getHeight(x + 1, y);
						diff *= Render.heightConstant;
						drawTileN(x, y, height, diff, 1, 0);
					}
					if (World.getHeight(x - 1, y) < World.getHeight(x, y)) {
						float diff = World.getHeight(x, y)
								- World.getHeight(x - 1, y);
						diff *= Render.heightConstant;
						drawTileS(x, y, height, diff, 1, 0);
					}
					if (World.getHeight(x, y + 1) < World.getHeight(x, y)) {
						float diff = World.getHeight(x, y)
								- World.getHeight(x, y + 1);
						diff *= Render.heightConstant;
						drawTileW(x, y, height, diff, 1, 0);
					}
					if (World.getHeight(x, y - 1) < World.getHeight(x, y)) {
						float diff = World.getHeight(x, y)
								- World.getHeight(x, y - 1);
						diff *= Render.heightConstant;
						drawTileE(x, y, height, diff, 1, 0);
					}
				}
				if (World.map[x][y] == 2) {
					drawTile(x, y, height, 3, 0);
					if (World.getHeight(x + 1, y) < World.getHeight(x, y)) {
						float diff = World.getHeight(x, y)
								- World.getHeight(x + 1, y);
						diff *= Render.heightConstant;
						drawTileN(x, y, height, diff, 3, 0);
					}
					if (World.getHeight(x - 1, y) < World.getHeight(x, y)) {
						float diff = World.getHeight(x, y)
								- World.getHeight(x - 1, y);
						diff *= Render.heightConstant;
						drawTileS(x, y, height, diff, 3, 0);
					}
					if (World.getHeight(x, y + 1) < World.getHeight(x, y)) {
						float diff = World.getHeight(x, y)
								- World.getHeight(x, y + 1);
						diff *= Render.heightConstant;
						drawTileW(x, y, height, diff, 3, 0);
					}
					if (World.getHeight(x, y - 1) < World.getHeight(x, y)) {
						float diff = World.getHeight(x, y)
								- World.getHeight(x, y - 1);
						diff *= Render.heightConstant;
						drawTileE(x, y, height, diff, 3, 0);
					}
				}
				if (World.map[x][y] == 3) {
					drawTile(x, y, height, 2, 0);
					if (World.getHeight(x + 1, y) < World.getHeight(x, y)) {
						float diff = World.getHeight(x, y)
								- World.getHeight(x + 1, y);
						diff *= Render.heightConstant;
						drawTileN(x, y, height, diff, 2, 0);
					}
					if (World.getHeight(x - 1, y) < World.getHeight(x, y)) {
						float diff = World.getHeight(x, y)
								- World.getHeight(x - 1, y);
						diff *= Render.heightConstant;
						drawTileS(x, y, height, diff, 2, 0);
					}
					if (World.getHeight(x, y + 1) < World.getHeight(x, y)) {
						float diff = World.getHeight(x, y)
								- World.getHeight(x, y + 1);
						diff *= Render.heightConstant;
						drawTileW(x, y, height, diff, 2, 0);
					}
					if (World.getHeight(x, y - 1) < World.getHeight(x, y)) {
						float diff = World.getHeight(x, y)
								- World.getHeight(x, y - 1);
						diff *= Render.heightConstant;
						drawTileE(x, y, height, diff, 2, 0);
					}
				}
			}
		}

		int buffersize = index;

		FloatBuffer vertexData = BufferUtils.createFloatBuffer(buffersize);
		FloatBuffer textureData = BufferUtils.createFloatBuffer(buffersize * 2 / 3);

		vertexData.put(vertex, 0, index);
		textureData.put(texture, 0, index * 2 / 3);
		
		vertexData.flip();
		textureData.flip();


		synchronized(Render.lock){
			Render.texture = textureData;
			Render.vertex = vertexData;
			Render.buffersize = index;
		}

	}



	
	private static void drawTile(float x, float y, float height, int tex,
			int tey) {
		
		int texindex = index / 3 * 2;
		
		texture[texindex] = Render.atlas.getCoord(tex, false);
		texture[texindex + 1] = Render.atlas.getCoord(tey, false);
		texture[texindex + 2] = Render.atlas.getCoord(tex, true);
		texture[texindex + 3] = Render.atlas.getCoord(tey, false);
		texture[texindex + 4] = Render.atlas.getCoord(tex, true);
		texture[texindex + 5] = Render.atlas.getCoord(tey, true);
		texture[texindex + 6] = Render.atlas.getCoord(tex, false);
		texture[texindex + 7] = Render.atlas.getCoord(tey, true);
		
		vertex[index] = -x * tilesize - offset;
		vertex[index + 1] = height;
		vertex[index + 2] = -y * tilesize - offset;
		
		vertex[index + 3] = -x * tilesize + offset;
		vertex[index + 4] = height;
		vertex[index + 5] = -y * tilesize - offset;
		
		vertex[index + 6] = -x * tilesize + offset;
		vertex[index + 7] = height;
		vertex[index + 8] = -y * tilesize + offset;
		
		vertex[index + 9] = -x * tilesize - offset;
		vertex[index + 10] = height;
		vertex[index + 11] = -y * tilesize + offset;
		
		index += 12;
	}

	private static void drawTileN(float x, float y, float height, float length,
			int tex, int tey) {
		int texindex = index / 3 * 2;
		
		texture[texindex] = Render.atlas.getCoord(tex, false);
		texture[texindex + 1] = Render.atlas.getCoord(tey, false);
		texture[texindex + 2] = Render.atlas.getCoord(tex, true);
		texture[texindex + 3] = Render.atlas.getCoord(tey, false);
		texture[texindex + 4] = Render.atlas.getCoord(tex, true);
		texture[texindex + 5] = Render.atlas.getCoord(tey, true);
		texture[texindex + 6] = Render.atlas.getCoord(tex, false);
		texture[texindex + 7] = Render.atlas.getCoord(tey, true);
		
		vertex[index] = -x * tilesize - offset;
		vertex[index + 1] = height;
		vertex[index + 2] = -y * tilesize - offset;
		
		vertex[index + 3] = -x * tilesize - offset;
		vertex[index + 4] = height - length;
		vertex[index + 5] = -y * tilesize - offset;
		
		vertex[index + 6] = -x * tilesize - offset;
		vertex[index + 7] = height - length;
		vertex[index + 8] = -y * tilesize + offset;
		
		vertex[index + 9] = -x * tilesize - offset;
		vertex[index + 10] = height;
		vertex[index + 11] = -y * tilesize + offset;
		
		index += 12;
	}

	private static void drawTileW(float x, float y, float height, float length,
			int tex, int tey) {
		
		int texindex = index / 3 * 2;
		
		texture[texindex] = Render.atlas.getCoord(tex, false);
		texture[texindex + 1] = Render.atlas.getCoord(tey, false);
		texture[texindex + 2] = Render.atlas.getCoord(tex, true);
		texture[texindex + 3] = Render.atlas.getCoord(tey, false);
		texture[texindex + 4] = Render.atlas.getCoord(tex, true);
		texture[texindex + 5] = Render.atlas.getCoord(tey, true);
		texture[texindex + 6] = Render.atlas.getCoord(tex, false);
		texture[texindex + 7] = Render.atlas.getCoord(tey, true);
		vertex[index] = -x * tilesize - offset;
		vertex[index + 1] = height;
		vertex[index + 2] = -y * tilesize - offset;
		
		vertex[index + 3] = -x * tilesize + offset;
		vertex[index + 4] = height;
		vertex[index + 5] = -y * tilesize - offset;
		
		vertex[index + 6] = -x * tilesize + offset;
		vertex[index + 7] = height - length;
		vertex[index + 8] = -y * tilesize - offset;
		
		vertex[index + 9] = -x * tilesize - offset;
		vertex[index + 10] = height - length;
		vertex[index + 11] = -y * tilesize - offset;
		
		index += 12;
	}

	private static void drawTileS(float x, float y, float height, float length,
			int tex, int tey) {
		
		int texindex = index / 3 * 2;
		
		texture[texindex] = Render.atlas.getCoord(tex, false);
		texture[texindex + 1] = Render.atlas.getCoord(tey, false);
		texture[texindex + 2] = Render.atlas.getCoord(tex, true);
		texture[texindex + 3] = Render.atlas.getCoord(tey, false);
		texture[texindex + 4] = Render.atlas.getCoord(tex, true);
		texture[texindex + 5] = Render.atlas.getCoord(tey, true);
		texture[texindex + 6] = Render.atlas.getCoord(tex, false);
		texture[texindex + 7] = Render.atlas.getCoord(tey, true);
		
		vertex[index] = -x * tilesize + offset;
		vertex[index + 1] = height;
		vertex[index + 2] = -y * tilesize - offset;
		
		vertex[index + 3] = -x * tilesize + offset;
		vertex[index + 4] = height - length;
		vertex[index + 5] = -y * tilesize - offset;
		
		vertex[index + 6] = -x * tilesize + offset;
		vertex[index + 7] = height - length;
		vertex[index + 8] = -y * tilesize + offset;
		
		vertex[index + 9] = -x * tilesize + offset;
		vertex[index + 10] = height;
		vertex[index + 11] = -y * tilesize + offset;
		
		index += 12;
	}

	private static void drawTileE(float x, float y, float height, float length,
			int tex, int tey) {
		int texindex = index / 3 * 2;
		
		texture[texindex] = Render.atlas.getCoord(tex, false);
		texture[texindex + 1] = Render.atlas.getCoord(tey, false);
		texture[texindex + 2] = Render.atlas.getCoord(tex, true);
		texture[texindex + 3] = Render.atlas.getCoord(tey, false);
		texture[texindex + 4] = Render.atlas.getCoord(tex, true);
		texture[texindex + 5] = Render.atlas.getCoord(tey, true);
		texture[texindex + 6] = Render.atlas.getCoord(tex, false);
		texture[texindex + 7] = Render.atlas.getCoord(tey, true);
		vertex[index] = -x * tilesize - offset;
		vertex[index + 1] = height;
		vertex[index + 2] = -y * tilesize + offset;
		
		vertex[index + 3] = -x * tilesize + offset;
		vertex[index + 4] = height;
		vertex[index + 5] = -y * tilesize + offset;
		
		vertex[index + 6] = -x * tilesize + offset;
		vertex[index + 7] = height - length;
		vertex[index + 8] = -y * tilesize + offset;
		
		vertex[index + 9] = -x * tilesize - offset;
		vertex[index + 10] = height - length;
		vertex[index + 11] = -y * tilesize + offset;
		
		index += 12;
	}

}
