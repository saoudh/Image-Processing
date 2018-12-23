
package src;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import boofcv.io.image.ConvertBufferedImage;
import boofcv.io.image.UtilImageIO;
import boofcv.struct.image.GrayU8;

public class exercise_11_flat_zone {

	private static int LABEL_NO_FZ;

	public static void main(String[] args) throws IOException {
		if (args.length == 3) {
			System.out.println("3 arguments");

			String textFile = args[0];
			String img_input = args[1];
			String img_output = args[2];

			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(textFile)));

			List<String> myData = new ArrayList<String>();
			String myLine = null;
			while ((myLine = br.readLine()) != null) {
				myData.add(myLine);
				System.out.println("Line of Textfile: " + myLine);

			}
			int x = -1, y = -1, connectivity = -1, LABEL_FZ = -1;

			try {
				x = Integer.valueOf(myData.get(0));
				y = Integer.valueOf(myData.get(1));
				connectivity = Integer.valueOf(myData.get(2));
				LABEL_FZ = Integer.valueOf(myData.get(3));
				System.out.println("x=" + x);
				System.out.println("y=" + y);
				System.out.println("connectivity=" + connectivity);
				System.out.println("LABEL_FZ=" + LABEL_FZ);

			} catch (NullPointerException e) {

				System.out.println("values of list is null");
			}

			System.out.println("1.arg=" + textFile);
			System.out.println("2.arg=" + img_input);
			System.out.println("3.arg=" + img_output);
			// LABEL_NO_FZ must have contrary value than LABEL_FZ to distinguish
			// them
			GrayU8 gray8_img_out = exercise_11_flat_zone.reconstructFlatZOne(img_input, img_output,
					new Integer[] { x, y }, LABEL_FZ);
			// gray8_img_out=exercise_11_flat_zone.initImageOutput(gray8_img_out,LABEL_NO_FZ);
			UtilImageIO.saveImage(gray8_img_out, img_output);

		} else {
			System.out.println("less than 3 arguments");

		}

	}

	public static GrayU8 reconstructFlatZOne(String img_input, String img_output, Integer[] pixel, int LABEL_FZ) {

		// BufferedImage image =
		// UtilImageIO.loadImage("particles01__rows480__cols638.jpg");
		BufferedImage buf_img_input = UtilImageIO.loadImage(img_input);
		BufferedImage buf_img_output = UtilImageIO.loadImage(img_output);

		// Converting from BufferedImage input image to BoofCV imgray8
		GrayU8 imgray8_input = ConvertBufferedImage.convertFromSingle(buf_img_input, null, GrayU8.class);
		GrayU8 imgray8_output = new GrayU8(imgray8_input.width, imgray8_input.height);
		System.out.println("width x length=" + imgray8_input.width + "x" + imgray8_input.height);
		// set the value of LABEL_FZ to the value of the desired pixel
		LABEL_FZ = imgray8_input.get(pixel[0], pixel[1]);

		LABEL_NO_FZ = LABEL_FZ > (255 / 2) ? 0 : 255;
		System.out.println("LabelNo=" + LABEL_NO_FZ + " - LabelFZ=" + LABEL_FZ);
		initImageOutput(imgray8_output, LABEL_NO_FZ);

		System.out.println("value of FZ_Label=" + LABEL_FZ);
		imgray8_output.set(pixel[0], pixel[1], LABEL_FZ);
		Queue<Integer[]> queue = new LinkedList<Integer[]>();
		queue.offer(pixel);
		Integer[] pixel2 = new Integer[2];
		while (!queue.isEmpty()) {
			pixel2 = queue.poll();
			// System.out.println("queue.poll()="+pixel2[0]+" x "+pixel2[1]+" -
			// value="+LABEL_FZ);
			for (int x = pixel2[0] - 1; x <= pixel2[0] + 1; x++) {
				for (int y = pixel2[1] - 1; y <= pixel2[1] + 1; y++) {
					// skip testing the flat zone pixel with itself
					if ((x == pixel2[0] && y == pixel2[1])
							|| (x > imgray8_input.width - 1 || y > imgray8_input.height - 1) || (x < 0 || y < 0))
						continue;
					if (imgray8_input.get(x, y) > 0)
						System.out.println("no 0 =" + x + " x " + y + " - value=" + imgray8_input.get(x, y));

					/*
					 * if the current pixel belongs to the flat zone and was not
					 * treated yet, then set it properly in the ouput image and
					 * add it to the queue
					 */
					/*
					 * System.out.println("get(x,y)="+imgray8_input.get(x, y)
					 * +" - get(pixel2[0], pixel2[1]"+imgray8_input.get(pixel2[0
					 * ], pixel2[1]) +"exist: "+(imgray8_output.get(x, y) !=
					 * LABEL_FZ));
					 */
					if (imgray8_input.get(x, y) == imgray8_input.get(pixel2[0], pixel2[1])
							&& imgray8_output.get(x, y) != LABEL_FZ) {
						// System.out.println("queue.offer()="+x+" x "+y + " -
						// value="+imgray8_input.get(x, y));

						imgray8_output.set(x, y, LABEL_FZ);
						queue.offer(new Integer[] { x, y });
					}
				}
			}
		}
		return imgray8_output;
	}

	private static GrayU8 initImageOutput(GrayU8 imgray8_output, int LABEL_NO_FZ) {
		System.out.println("initImage is called and setting everything to: " + LABEL_NO_FZ);
		for (int x = 0; x < imgray8_output.width; x++) {
			for (int y = 0; y < imgray8_output.height; y++) {
				// System.out.println("before="+imgray8_output.get(x, y));

				imgray8_output.set(x, y, LABEL_NO_FZ);
				// System.out.println("after="+imgray8_output.get(x, y));

			}
		}
		return imgray8_output;
	}

}
