

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
		DisplayUtilities.display(segment);
		processor.performOCR(segment, processor.grayScalePixels);
		//clone.drawShape(new Rectangle(left_most, top_most, width, height), RGBColour.CYAN);
		 
		//Step 5
		 //Display the image
		DisplayUtilities.display(clone);

	}
	
	//-----------------------------------------Methods----------------------------------------------------------//
	
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
	
	/*
	public void reduceNoise(MBFImage clone, int tileWidth, int tileHeight) {
		int x = 0;
		int y = 0;
		int width = tileWidth;
		int height = tileHeight;
		int counter = 0;
		for (int yPos = 0; yPos <= (clone.getHeight() / height); yPos++) {
			width = tileWidth;
			height = tileHeight;
			for (int xPos = 0; xPos <= (clone.getWidth() / width); xPos++) {
				Rectangle bounds = new Rectangle(x, y, getWidth(width, clone, x), getHeight(height, clone, y));
				MBFImage segment = clone.extractROI(bounds);
				//clone.drawShape(bounds, 1, RGBColour.ORANGE);
				segments[counter] = segment;
				if (analyzeWhiteSegments(segments[counter]) > 1) {
					comparators[counter] = true;
				}
				counter++;
				x += width;
			}
			y += height;
			x = 0;
		}
	}
	
	public void performSegmentation(MBFImage clone, int tileWidth, int tileHeight, int[] values) {
		values[0] = 0;
		values[1] = 0;
		int personCounter = 0;
		int x = 0;
		int y = 0;
		int width = tileWidth;
		int height = tileHeight;
		int counter = 0;
		//double doubleWidth = (double) tileWidth;
		//double totalTiles = (double) (clone.getWidth() / doubleWidth);
		//int totalTilesDone = ((int) Math.ceil(totalTiles));
		for (int yPos = 0; yPos <= (clone.getHeight() / height); yPos++) {
			width = tileWidth;
			height = tileHeight;
			for (int xPos = 0; xPos <= (clone.getWidth() / width); xPos++) {
				Rectangle bounds = new Rectangle(x, y, getWidth(width, clone, x), getHeight(height, clone, y));
				MBFImage segment = clone.extractROI(bounds);
				segments[counter] = segment;
				if (analyzeWhiteSegments(segments[counter]) > 100 && analyzeBlackSegments(segments[counter]) > 20) {
					if (counter > 0) {
						if (determineIfSamePerson(values[0], values[1], xPos, yPos) == false) {
							personCounter++;
						} else {
							//continue -- same person
							System.out.println("Same Person");
						}
					} else {
						//values[0] = xPos;
						//values[1] = yPos;
						//determineIfSamePerson(values[0], values[1], xPos, yPos);
						//personCounter++;
					}
					if (personCounter == 1) {
						clone.drawShape(bounds, 1, RGBColour.RED);
					} else if (personCounter == 2) {
						clone.drawShape(bounds, 1, RGBColour.GREEN);
					} else if (personCounter == 3) {
						clone.drawShape(bounds, 1, RGBColour.BLUE);
					} else if (personCounter == 4) {
						clone.drawShape(bounds, 1, RGBColour.ORANGE);
					} else if (personCounter == 5) {
						clone.drawShape(bounds, 1, RGBColour.PINK);
					} else if (personCounter == 6) {
						clone.drawShape(bounds, 1, RGBColour.GRAY);
					} else if (personCounter == 7) {
						clone.drawShape(bounds, 1, RGBColour.CYAN);
					} else {
						clone.drawShape(bounds, 1, RGBColour.MAGENTA);
					}
					System.out.println("Person " + personCounter + " positioned at (" + xPos + ", " + yPos + ")");
					values[0] = xPos;
					values[1] = yPos;
					/*
					//if (comparators[counter + 1] == false //Next
							//&& comparators[counter - 1] == false) //Before
								//&& comparators[counter - totalTilesDone] == false //Above
									//&& comparators[counter + totalTilesDone] == false) //Below
					{
						//continue
					} else {
						clone.drawShape(bounds, 1, RGBColour.RED);
					}
					*/
	/*
				}
				counter++;
				x += width;
			}
			y += height;
			x = 0;
		}
	}
	
	public boolean determineIfSamePerson(int x1, int y1, int x2, int y2) {
		boolean isSamePerson = true;
		int xDifference = Math.abs(x2 - x1);
		int yDifference = Math.abs(y2 - y1);
		int totalDifference = xDifference - yDifference;
		//System.out.println(x1);
		//System.out.println(y1);
		//System.out.println("X DIFF: " + xDifference + " || Y DIFF: " + yDifference);
		//System.out.println("XDifference: " + xDifference);
		//System.out.println("YDifference: " + yDifference);
		//System.out.println("TOTALDifference: " + totalDifference);
		if (xDifference > 1 || yDifference > 1 || totalDifference > 1) {
			isSamePerson = false;
		} else {
			isSamePerson = true;
		}
		return isSamePerson;
	}
	
	public void performWhiteOut(MBFImage image, float[][] whiteOut) {
		for (int y=1; y<image.getHeight() - 1; y++) {
		    for(int x=1; x<image.getWidth() - 1; x++) {
		    	whiteOut[y][x] = 1;
		    } 
		}
	}

	public int analyzeWhiteSegments(MBFImage image) {
		int counter = 0;
		int y = 1;
		int x = 1;
		for (y=1; y<image.getHeight() - 1; y++) {
		    for(x=1; x<image.getWidth() - 1; x++) {
		    	float sumFloat = 0;
		        float redColor = image.getBand(0).pixels[y][x];
		        float greenColor = image.getBand(1).pixels[y][x];
		        float blueColor = image.getBand(2).pixels[y][x];
		        sumFloat = (((redColor*256) + (greenColor*256) + (blueColor*256)) / 3);
		        if (sumFloat/256 < 0.98) {
		        	sumFloat = 1;
		        } else {
		        	sumFloat = sumFloat / 256;
		        	counter++;
		        }
		        grayScalePixels[y][x] = sumFloat;
		    } 
		}
		
		if (counter > 10) {
			colorInImage(image, grayScalePixels);
		} else {
			colorInImage(image, whiteOut);
		}
		
		//if (counter != 0)
			//System.out.println("White Pixels Found: " + counter);
		return counter;
	}
	
	public int analyzeBlackSegments(MBFImage image) {
		int counter = 0;
		int y = 1;
		int x = 1;
		for (y=1; y<image.getHeight() - 1; y++) {
		    for(x=1; x<image.getWidth() - 1; x++) {
		    	float sumFloat = 0;
		        float redColor = image.getBand(0).pixels[y][x];
		        float greenColor = image.getBand(1).pixels[y][x];
		        float blueColor = image.getBand(2).pixels[y][x];
		        sumFloat = (((redColor*256) + (greenColor*256) + (blueColor*256)) / 3);
		        if (sumFloat/256 > 0.99) {
		        	sumFloat = 1;
		        } else {
		        	sumFloat = sumFloat / 256;
		        	counter++;
		        }
		        grayScalePixels[y][x] = sumFloat;
		    } 
		}
		
		if (counter > 10) {
			colorInImage(image, grayScalePixels);
		} else {
			colorInImage(image, whiteOut);
		}
		
		//System.out.println("Black Pixels Found: " + counter);
		return counter;
	}*/
	
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
