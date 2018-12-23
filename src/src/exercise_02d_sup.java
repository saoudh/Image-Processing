
package src;

import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

import boofcv.io.image.ConvertBufferedImage;
import boofcv.io.image.UtilImageIO;
import boofcv.struct.image.GrayU8;

public class exercise_02d_sup {

	public static void main(String[] args) {

		if (args.length == 3) {
			String filein1 = args[0];
			String filein2 = args[1];
			String fileout = args[2];
			System.out.println("1.arg=" + filein1);
			System.out.println("2.arg=" + filein2);
			System.out.println("3.arg=" + fileout);

			try {
				exercise_02d_sup.process(filein1, filein2, fileout);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			System.out.println("less than 3 arguments");

		}
	}

	public static void process(String filein1, String filein2, String fileout) throws IOException {
		// load and convert the image into a usable format

		System.out.println("filein1: " + filein1);
		// BufferedImage image =
		// UtilImageIO.loadImage("particles01__rows480__cols638.jpg");
		BufferedImage image1 = UtilImageIO.loadImage(filein1);
		BufferedImage image2 = UtilImageIO.loadImage(filein2);
		FileWriter fw = new FileWriter("exercise_02b_output_01.txt");
		BufferedWriter bw = new BufferedWriter(fw);

		GrayU8 img_filein1 = ConvertBufferedImage.convertFromSingle(image1, null, GrayU8.class);
		GrayU8 img_filein2 = ConvertBufferedImage.convertFromSingle(image2, null, GrayU8.class);

		/*
		 * If the sizes are false, then it means the pictures are not the same
		 * and the checking is over. Otherwise we can check whether the values
		 * of the pixels are the same in a seperate method.
		 */
		if (img_filein1.width == img_filein2.width && img_filein1.height == img_filein2.height) {
			System.out.println("same size, getSub()");
			GrayU8 img_out = getSup(img_filein1, img_filein2);
			UtilImageIO.saveImage(img_out, fileout);
		} else {
			System.out.println("not same size, doing nothing");

		}
	}

	// The Sup (=supremum) of a and b is the least element of L that is greater
	// than both a and b
	private static GrayU8 getSup(GrayU8 img_filein1, GrayU8 img_filein2) {
		GrayU8 img_out = new GrayU8(img_filein1.width, img_filein1.height);

		for (int i = 0; i < img_filein1.width; i++) {
			for (int j = 0; j < img_filein1.height; j++) {
				int value = img_filein1.get(i, j) > img_filein2.get(i, j) ? img_filein1.get(i, j)
						: img_filein2.get(i, j);
				img_out.set(i, j, value);
			}
		}
		return img_out;
	}

}
