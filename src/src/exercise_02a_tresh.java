
package src;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;

import boofcv.io.image.ConvertBufferedImage;
import boofcv.io.image.UtilImageIO;
import boofcv.struct.image.GrayU8;

public class exercise_02a_tresh {

	public static void main(String[] args) throws IOException {
		if (args.length == 3) {
			System.out.println("3 arguments");
			String filein1 = args[0];
			String value = args[1];
			String fileout = args[2];

			exercise_02a_tresh.process(filein1, Integer.valueOf(value), fileout);
		} else {
			System.out.println("less than 3 arguments");

		}

	}

	public static void process(String filein1, int value, String fileout) {
		// load and convert the image into a usable format
		// String filein1 = "exercise_02a_input_01.pgm";
		String fileout1 = fileout;
		String fileout2 = "/tmp/out2.png";
		System.out.println("filein1: " + filein1);
		// BufferedImage image =
		// UtilImageIO.loadImage("particles01__rows480__cols638.jpg");
		BufferedImage image = UtilImageIO.loadImage(filein1);

		// Converting from BufferedImage input image to BoofCV imgray8
		GrayU8 imgray8 = ConvertBufferedImage.convertFromSingle(image, null, GrayU8.class);
		System.out.println("imgray8.width: " + imgray8.width);
		System.out.println("imgray8.height: " + imgray8.height);
		for (int i = 0; i < imgray8.width; i++) {
			for (int j = 0; j < imgray8.height; j++) {
				if (imgray8.get(i, j) > value) {
					imgray8.set(i, j, 255);
				} else {
					imgray8.set(i, j, 0);

				}

			}
		}

		// Copying the data array from imgray8 to a new im2gray8.
		GrayU8 im2gray8 = new GrayU8(imgray8.width, imgray8.height); // se crea
																		// una
																		// imagen
		im2gray8.setData(Arrays.copyOf(imgray8.getData(), imgray8.width * imgray8.height));

		// The imgray8 is going to be modified: its 4 corner pixels will have
		// values 201, 202, 203, 204.
		//
		//
		// Using accessors (.set y .get)
		imgray8.set(0, 0, 201); // setting the pixel at col 0, row 0 to value
								// 201
		imgray8.set((imgray8.width - 1), 0, 202);
		imgray8.set(0, (imgray8.height - 1), 203);
		imgray8.set((imgray8.width - 1), (imgray8.height - 1), 204);

		UtilImageIO.saveImage(imgray8, fileout1);
		System.out.println("fileout1: " + fileout1);
		UtilImageIO.saveImage(im2gray8, fileout2);
		System.out.println("fileout2: " + fileout2);
	}

}
