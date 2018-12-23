
package src;

import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

import boofcv.io.image.ConvertBufferedImage;
import boofcv.io.image.UtilImageIO;
import boofcv.struct.image.GrayU8;

public class exercise_04b_closing {

	public static void main(String[] args) {

		if (args.length == 3) {
			int size = Integer.valueOf(args[0]);
			String filein1 = args[1];
			String fileout = args[2];

			try {
				exercise_04b_closing.process(filein1, size, fileout);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static GrayU8 process(String filein1, int size) throws IOException {

		BufferedImage image1 = UtilImageIO.loadImage(filein1);

		GrayU8 img_filein1 = ConvertBufferedImage.convertFromSingle(image1, null, GrayU8.class);

		/*
		 * Closing means: first dilation followed by a erosion
		 */
		GrayU8 img_out = exercise_03b_dilation.dilation(size, img_filein1);
		GrayU8 img_out2 = exercise_03a_erosion.erosion(size, img_out);

		return img_out2;
	}

	public static GrayU8 process(String filein1, int size, String fileout) throws IOException {

		BufferedImage image1 = UtilImageIO.loadImage(filein1);
		if (image1 == null)
			System.out.println("exercise_04b-process-image1 is null!");

		GrayU8 img_filein1 = ConvertBufferedImage.convertFromSingle(image1, null, GrayU8.class);

		/*
		 * Opening means: first dilation followed by a erosion
		 */
		GrayU8 img_out = exercise_03b_dilation.dilation(size, img_filein1);
		GrayU8 img_out2 = exercise_03a_erosion.erosion(size, img_out);

		UtilImageIO.saveImage(img_out2, fileout);
		return img_out2;
	}

}
