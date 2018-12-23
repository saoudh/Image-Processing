
package src;

import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

import boofcv.io.image.ConvertBufferedImage;
import boofcv.io.image.UtilImageIO;
import boofcv.struct.image.GrayU8;

public class exercise_03a_erosion {

	public static void main(String[] args) {

		if (args.length == 3) {

			int size = Integer.valueOf(args[0]);
			String filein1 = args[1];
			String fileout = args[2];
			System.out.println("1.arg=" + size);
			System.out.println("2.arg=" + filein1);
			System.out.println("3.arg=" + fileout);
			try {
				exercise_03a_erosion.process(filein1, size, fileout);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			System.out.println("less than 3 arguments");

		}
	}

	public static void process(String filein1, int size, String fileout) throws IOException {
		// load and convert the image into a usable format

		System.out.println("filein1: " + filein1);
		// BufferedImage image =
		// UtilImageIO.loadImage("particles01__rows480__cols638.jpg");
		BufferedImage image1 = UtilImageIO.loadImage(filein1);
		FileWriter fw = new FileWriter("exercise_02b_output_01.txt");
		BufferedWriter bw = new BufferedWriter(fw);

		GrayU8 img_filein1 = ConvertBufferedImage.convertFromSingle(image1, null, GrayU8.class);

		/*
		 * If the sizes are false, then it means the pictures are not the same
		 * and the checking is over. Otherwise we can check whether the values
		 * of the pixels are the same in a seperate method.
		 */
		// GrayU8 img_out = getErosionPic(size, img_filein1);
		GrayU8 img_out = erosion(size, img_filein1);

		UtilImageIO.saveImage(img_out, fileout);

	}

	public static GrayU8 erosion(int size, GrayU8 img_filein1) {
		// Adapt size
		size = 2 * size + 1;
		// dimension of the image
		int width = img_filein1.getWidth();
		int height = img_filein1.getHeight();
		GrayU8 img_out = new GrayU8(img_filein1.width, img_filein1.height);

		// buffer for saving the values under the pattern mask
		int buff[];

		// array of the values for output image
		int output[] = new int[width * height];

		// process erosion
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				buff = new int[size * size];
				int i = 0;
				// Check values under the mask
				for (int ty = y - size / 2, mr = 0; ty <= y + size / 2; ty++, mr++) {
					for (int tx = x - size / 2, mc = 0; tx <= x + size / 2; tx++, mc++) {
						//
						if (ty >= 0 && ty < height && tx >= 0 && tx < width) {
							// save values of the pixels under the mask
							buff[i] = img_filein1.get(tx, ty);
							i++;
						}
					}
				}

				// sort values of the pattern mask
				java.util.Arrays.sort(buff);

				// save lowest value
				output[x + y * height] = buff[(size * size) - i];
			}
		}

		// Save the values into the output image
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				int v = output[x + y * height];
				img_out.set(x, y, v);
			}
		}
		return img_out;
	}

}
