

import java.io.File;
import java.io.IOException;

import org.openimaj.image.DisplayUtilities;
import org.openimaj.image.ImageUtilities;
import org.openimaj.image.MBFImage;
import org.openimaj.image.colour.RGBColour;
import org.openimaj.math.geometry.shape.Rectangle;

public class OCR {

	//---------------------------------------Variables--------------------------------------------------------//

	private float[][] grayScalePixels = null;

	private int[] xCords;
	private int[] yCords;
	
	private int[] letterA;
	
	//---------------------------------------Constructor--------------------------------------------------------//
	
	public OCR(MBFImage image) {
		grayScalePixels = new float[image.getHeight()][image.getWidth()];
		xCords = new int[4];
		yCords = new int[4];
		letterA = new int[1000];
	}
	
	//---------------------------------------Main Method--------------------------------------------------------//

	public static void main(String[] args) throws IOException {
		 //Step 1
		 //Read in the image using 'OpenImaj'
		MBFImage image = ImageUtilities.readMBF(new File("/Users/Anthony/Desktop/Main/Sort/Letters/small.png"));
		
		 //Step 2
		 //Create a clone
		MBFImage clone = image.clone();
		
		 //Step 3
		 //Fill in all arrays - you are now able to process clone
		OCR processor = new OCR(clone);

		 //Step 4
		 //Main logic of the processor
		
		//processor.analyzeWhiteSegments(clone);
		//DisplayUtilities.display(clone);
		//processor.reduceNoise(clone, 100, 110); //Makes the boxes
		//processor.performSegmentation(clone, 100, 110, processor.values); //Gets rid of unnecessary boxes
		processor.convertToGrayScale(clone);
		processor.findTopCorner(processor.grayScalePixels);
		processor.findBottomCorner(processor.grayScalePixels);
		processor.findLeftCorner(processor.grayScalePixels);
		processor.findRightCorner(processor.grayScalePixels);
		int left_most = processor.determineLeftMostPoint(processor.xCords);
		int right_most = processor.determineRightMostPoint(processor.xCords);
		int top_most = processor.determineTopMostPoint(processor.yCords);
		int bottom_most = processor.determineBottomMostPoint(processor.yCords);
		int height = bottom_most - top_most;
		int width = right_most - left_most;
		Rectangle bounds = new Rectangle(left_most, top_most, width, height);
		MBFImage segment = clone.extractROI(bounds);
		segment.drawShape(new Rectangle(segment.getWidth()/2-2,0,0,segment.getHeight()), RGBColour.BLUE);
		segment.drawShape(new Rectangle(segment.getWidth()/2-1,0,0,segment.getHeight()), RGBColour.ORANGE);
		segment.drawShape(new Rectangle(segment.getWidth()/2,0,0,segment.getHeight()), RGBColour.RED);
		System.out.println("Left: " + processor.determineAverage(segment, segment.getWidth()/2-2));
		System.out.println("Middle: " + processor.determineAverage(segment, segment.getWidth()/2-1));
		System.out.println("Right: " + processor.determineAverage(segment, segment.getWidth()/2));
		DisplayUtilities.display(segment);
		//processor.performOCR(segment, processor.grayScalePixels);
		clone.drawShape(new Rectangle(left_most, top_most, width, height), 1, RGBColour.RED);
		System.out.println("Width: " + segment.getWidth());
		System.out.println("YOSKI: " + segment.getWidth()/2);
		System.out.println("A Averages: " + processor.findAverages(segment));
		System.out.println("A Averages: " + processor.findAveragesFromMiddle(segment));
		System.out.println("Height: " +segment.getHeight());
		//Step 5
		 //Display the image
		DisplayUtilities.display(clone);

	}
	
	//-----------------------------------------Methods----------------------------------------------------------//
	
	public StringBuilder findAverages(MBFImage image) {
		StringBuilder string = new StringBuilder();
		int startLoop = image.getWidth() / 4;
		int endLoop = image.getWidth() - startLoop;
		for (int x = startLoop; x <= endLoop; x++) {
			float counter = 0;
			float average = 0;
			if (x != startLoop)
				string.append(",");
			for (int y = 0; y < image.getHeight(); y++) {
					float redColor = image.getBand(0).pixels[y][x];
			        float greenColor = image.getBand(1).pixels[y][x];
			        float blueColor = image.getBand(2).pixels[y][x];
			        float sumFloat = (((redColor*256) + (greenColor*256) + (blueColor*256)) / 3);
			        average += sumFloat;
			        counter++;
			}
			string.append(Integer.toString((int)(average/counter)));
		}
		return string;
	}
	
	public StringBuilder findAveragesFromMiddle(MBFImage image) {
		StringBuilder string = new StringBuilder();
		int counter = 0;
		int denominator = 0;
		double average = 0;
		boolean add = false;
		//int zeroCounter = 0;
		//int zeroDenominator = 0;
		//int zeroDifference = 0;
		while (counter < 10) {
			int x = image.getWidth() / 2 - 1;
			if (add) {
				counter++;
				x = Math.abs(counter + x);
			} else {
				x = Math.abs(counter - x);
			}
			if (counter != 0)
				string.append(",");
			denominator = 0;
			average = 0;
			//zeroCounter = 0;
			//zeroDenominator = 0;
			for (int y = 0; y < image.getHeight(); y++) {
				int weightedY = (int) ((y)*(image.getHeight()-y))/(image.getHeight());
				float redColor = image.getBand(0).pixels[weightedY][x];
		        float greenColor = image.getBand(1).pixels[weightedY][x];
		        float blueColor = image.getBand(2).pixels[weightedY][x];
		        float sumFloat = (((redColor*256) + (greenColor*256) + (blueColor*256)) / 3);
		        //if (sumFloat == 0)
		        	//zeroCounter++;
		        average += sumFloat;
		        denominator++;
		        //if (zeroCounter > 10)
		        	//zeroDifference = zeroCounter - 50;
		        //zeroDenominator = denominator - zeroDifference;
			}
			if (add) {
				add = false;
			} else {
				add = true;
			}
			//System.out.println(zeroCounter);
			string.append(Integer.toString((int)(average/denominator)));
		}
		return string;
		
	}
	
	public float determineAverage(MBFImage image, int x) {
		float counter = 0;
		float average = 0;
		for (int y=0; y<image.getHeight(); y++) {
			float redColor = image.getBand(0).pixels[y][x];
	        float greenColor = image.getBand(1).pixels[y][x];
	        float blueColor = image.getBand(2).pixels[y][x];
	        float sumFloat = (((redColor*256) + (greenColor*256) + (blueColor*256)) / 3);
	        average += sumFloat;
	        counter++;
		}
		return average / counter;
	}
	
	public void convertToGrayScale(MBFImage image) {
		for (int y=0; y<image.getHeight(); y++) {
		    for(int x=0; x<image.getWidth(); x++) {
		        float redColor = image.getBand(0).pixels[y][x];
		        float greenColor = image.getBand(1).pixels[y][x];
		        float blueColor = image.getBand(2).pixels[y][x];
		        float sumFloat = (((redColor*256) + (greenColor*256) + (blueColor*256)) / 3);
		        if (sumFloat/256 > 0.05) {
		        	sumFloat = 1;
		        } else {
		        	sumFloat = sumFloat / 256;
		        }
		        grayScalePixels[y][x] = sumFloat;
		    } 
		}
		colorInImage(image, grayScalePixels);
	}
	
	public void findBottomCorner(float[][] pixels) {
		for(int y = pixels.length - 1; y >= 0; y--){
            for (int x = 0; x < pixels[y].length; x++){
                if (pixels[y][x] < 0.1) {
                	xCords[0] = x;
                	yCords[0] = y;
                	System.out.println("Position of bottom pixel: (" + x + "," + y + ")");
                	return;
                }
            }
        }
	}
	
	public void findTopCorner(float[][] pixels) {
		for(int y = 0; y < pixels.length - 1; y++){
            for (int x = pixels[y].length - 1; x >= 0; x--){
                if (pixels[y][x] < 0.1) {
                	xCords[1] = x;
                	yCords[1] = y;
                	System.out.println("Position of top pixel: (" + x + "," + y + ")");
                	return;
                }
            }
        }
	}
	
	public void findLeftCorner(float[][] pixels) {
		for(int x = 0; x < pixels[0].length - 1; x++){
            for (int y = pixels.length - 1; y >= 0; y--){
                if (pixels[y][x] < 0.1) {
                	xCords[2] = x;
                	yCords[2] = y;
                	System.out.println("Position of left pixel: (" + x + "," + y + ")");
                	return;
                }
            }
        }
	}
	
	public void findRightCorner(float[][] pixels) {
		for(int x = pixels[0].length - 1; x >= 0; x--){
            for (int y = 0; y < pixels.length - 1; y++){
                if (pixels[y][x] < 0.1) {
                	xCords[3] = x;
                	yCords[3] = y;
                	System.out.println("Position of right pixel: (" + x + "," + y + ")");
                	return;
                }
            }
        }
	}
	
	public int determineLeftMostPoint(int[] cords) {
		int output = cords[0];
		for (int i = 1; i < cords.length; i++) {
			if (cords[i] < output) {
				output = cords[i];
			}
		}
		return output;
	}
	
	public int determineRightMostPoint(int[] cords) {
		int output = cords[0];
		for (int i = 1; i < cords.length; i++) {
			if (cords[i] > output) {
				output = cords[i];
			}
		}
		return output;
	}
	
	public int determineTopMostPoint(int[] cords) {
		int output = cords[0];
		for (int i = 1; i < cords.length; i++) {
			if (cords[i] < output) {
				output = cords[i];
			}
		}
		return output;
	}
	
	public int determineBottomMostPoint(int[] cords) {
		int output = cords[0];
		for (int i = 1; i < cords.length; i++) {
			if (cords[i] > output) {
				output = cords[i];
			}
		}
		return output;
	}
	
	public void performOCR(MBFImage image, float[][] pixels) {
		int pixelCounter;
		int counter = 0;
		for(int x = 0; x < pixels[0].length - 1; x++) {
			pixelCounter = 0;
            for (int y = 0; y < pixels.length - 1; y++) {
                if (pixels[y][x] < 0.1) {
                	pixelCounter++;
                }
            }
            counter++;
            letterA[counter] = pixelCounter;
        }
		returnLetter(image, letterA, letterA);
	}
	
	public void returnLetter(MBFImage image, int[] inputPixels, int[] letterPixels) {
		for (int y = 0; y<image.getHeight(); y++) {
		}
	}
	
	public static void colorInImage(MBFImage image, float[][] pixels) {
		for (int y=1; y<image.getHeight() - 1; y++) {
		    for(int x=1; x<image.getWidth() - 1; x++) {
		        image.getBand(0).setPixel(x, y, pixels[y][x]);
		        image.getBand(1).setPixel(x, y, pixels[y][x]);
		        image.getBand(2).setPixel(x, y, pixels[y][x]);
		    } 
		}
	}
	


}
