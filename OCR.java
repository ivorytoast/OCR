

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
		MBFImage image = ImageUtilities.readMBF(new File("/Users/Anthony/Desktop/Letters/small.png"));
		
		 //Step 2
		 //Create a clone
		MBFImage clone = image.clone();
		
		 //Step 3
		OCR processor = new OCR(clone);

		 //Step 4
		 //Main logic of the OCR
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
		DisplayUtilities.display(segment);
		processor.performOCR(segment, processor.grayScalePixels);
		 
		//Step 5
		 //Display the image
		DisplayUtilities.display(clone);

	}
	
	//-----------------------------------------Methods----------------------------------------------------------//
	
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
		int difference = 0;
		for(int i = 0; i < inputPixels.length; i++) {
			
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
