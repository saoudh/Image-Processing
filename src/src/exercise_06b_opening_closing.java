
package src;

import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

import boofcv.io.image.ConvertBufferedImage;
import boofcv.io.image.UtilImageIO;
import boofcv.struct.image.GrayU8;

/*
 * opening(closing(I))
 */
public class exercise_06b_opening_closing {

	public static void main(String[] args) {

		if (args.length == 3) {
			int size = Integer.valueOf(args[0]);
			String filein1 = args[1];
			String fileout = args[2];

			try {
				exercise_06b_opening_closing.process(filein1, size, fileout);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void process(String filein1, int size, String fileout) throws IOException {
		GrayU8 img_closing = new exercise_04b_closing().process(filein1, size, fileout);
		GrayU8 img_opening = new exercise_04a_opening().process(fileout, size);

		UtilImageIO.saveImage(img_opening, fileout);

	}

}
