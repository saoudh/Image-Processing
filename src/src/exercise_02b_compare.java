
package src;

import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

import boofcv.io.image.ConvertBufferedImage;
import boofcv.io.image.UtilImageIO;
import boofcv.struct.image.GrayU8;

public class exercise_02b_compare {

	public static void main(String[] args) {

		String fileout1="";
		if (args.length > 2) {
			System.out.println("2 > arguments");

			String filein1 = args[0];
			String filein2 = args[1];
			 fileout1 = args[2];
			System.out.println("1. arg="+filein1);
			System.out.println("2. arg="+filein2);
			System.out.println("3. arg="+fileout1);



			try {
				exercise_02b_compare.process(filein1, filein2,fileout1);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else
		{
			System.out.println("less than 3 arguments");
		}
	}

	public static void process(String filein1, String filein2,String fileout1) throws IOException {
		// load and convert the image into a usable format

		System.out.println("filein1: " + filein1);
		// BufferedImage image =
		// UtilImageIO.loadImage("particles01__rows480__cols638.jpg");
		BufferedImage image1 = UtilImageIO.loadImage(filein1);
		BufferedImage image2 = UtilImageIO.loadImage(filein2);
		//if filename for output file is specified by user then assign it, else standard file name
		String fileout=fileout1.length()>0?fileout1:"exercise_02b_output_01.txt";
		FileWriter fw = new FileWriter(fileout);
		BufferedWriter bw = new BufferedWriter(fw);

		
		GrayU8 img_filein1 = ConvertBufferedImage.convertFromSingle(image1, null, GrayU8.class);
		GrayU8 img_filein2 = ConvertBufferedImage.convertFromSingle(image2, null, GrayU8.class);

		/*
		 * If the sizes are false, then it means the pictures are not the same
		 * and the checking is over. Otherwise we can check whether the values
		 * of the pixels are the same in a seperate method.
		 */
		if (img_filein1.width != img_filein2.width && img_filein1.height != img_filein2.height) 
		{
			System.out.println("size not equal: !=");
			bw.write("!=");
		} else if (exercise_02b_compare.isSame(img_filein1, img_filein2)) {
			System.out.println("is same: =");
			bw.write("=");
		} else {
			System.out.println("else: !=");
			bw.write("!=");
		}
		bw.flush();
		bw.close();

	}

	private static boolean isSame(GrayU8 img_filein1, GrayU8 img_filein2) {
		boolean is_same = true;
		
		for (int i = 0; i < img_filein1.width; i++) {
			for (int j = 0; j < img_filein1.height; j++) {
				if (img_filein1.get(i, j) != img_filein2.get(i, j)) {
					return false;
				}

			}
		}
		return is_same;
	}

}
