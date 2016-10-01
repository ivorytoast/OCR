import java.text.DecimalFormat;

public class Analyzer {
	
	public double[] C1 =  {0.21,0.14,0.29,0.07,0.29,0.21,0.43,0.21,0.21,0.21};
	public double[] C2 =  {0.17,0.17,0.17,0.17,0.17,0.17,0.24,0.17,0.24,0.17,0.28,0.21,0.31,0.28,0.38,0.31,0.62,0.34,0.66,0.45,0.52,0.41,0.28,0.28};
	public double[] C3 =  {0.22,0.19,0.22,0.19,0.22,0.2,0.22,0.19,0.24,0.2,0.24,0.22,0.24,0.22,0.25,0.22,0.25,0.24,0.27,0.22,0.27,0.25,0.31,0.24,0.31,0.25,0.36,0.27,0.39,0.31,0.46,0.31,0.56,0.32,0.73,0.39,0.69,0.46,0.64,0.46,0.58,0.42,0.51,0.37,0.41,0.32,0.24,0.25};
	public double[] C4 =  {0.22,0.22,0.22,0.22,0.23,0.22,0.23,0.22,0.23,0.22,0.23,0.22,0.23,0.22,0.23,0.22,0.23,0.22,0.23,0.22,0.23,0.23,0.23,0.23,0.25,0.23,0.25,0.22,0.25,0.23,0.25,0.23,0.27,0.23,0.25,0.23,0.27,0.23,0.28,0.24,0.28,0.24,0.3,0.25,0.3,0.25,0.32,0.25,0.32,0.27,0.32,0.27,0.35,0.28,0.35,0.28,0.37,0.3,0.38,0.31,0.41,0.33,0.45,0.34,0.48,0.35,0.56,0.38,0.73,0.4,0.73,0.43,0.72,0.47,0.69,0.47,0.68,0.45,0.65,0.43,0.62,0.41,0.59,0.39,0.56,0.38,0.53,0.34,0.49,0.32,0.44,0.28,0.39,0.25,0.33,0.22,0.23,0.17,0.02,0.12};
	public double[] C5 =  {0.22,0.22,0.22,0.22,0.22,0.22,0.22,0.22,0.22,0.22,0.22,0.22,0.22,0.22,0.22,0.22,0.23,0.22,0.23,0.22,0.23,0.22,0.23,0.22,0.23,0.22,0.23,0.22,0.23,0.22,0.23,0.22,0.23,0.22,0.23,0.22,0.24,0.22,0.24,0.23,0.23,0.23,0.24,0.23,0.24,0.23,0.24,0.23,0.24,0.23,0.24,0.23,0.25,0.23,0.25,0.23,0.25,0.23,0.25,0.23,0.26,0.23,0.25,0.23,0.26,0.23,0.26,0.23,0.26,0.23,0.27,0.24,0.27,0.24,0.27,0.24,0.28,0.24,0.28,0.25,0.28,0.25,0.28,0.25,0.29,0.25,0.29,0.25,0.29,0.25,0.3,0.26,0.3,0.26,0.31,0.26,0.31,0.27,0.32,0.28,0.32,0.28,0.33,0.28,0.33,0.28,0.34,0.29,0.35,0.29,0.35,0.3,0.36,0.3,0.38,0.31,0.38,0.31,0.39,0.33,0.4,0.33,0.41,0.33,0.43,0.35,0.45,0.36,0.47,0.36,0.49,0.38,0.52,0.39,0.55,0.4,0.6,0.42,0.69,0.44,0.75,0.46,0.74,0.47,0.73,0.48,0.72,0.47,0.71,0.47,0.7,0.46,0.69,0.45,0.68,0.44,0.67,0.43,0.66,0.42,0.65,0.41,0.63,0.4,0.62,0.39,0.61,0.38,0.59,0.37,0.57,0.36,0.56,0.35,0.55,0.33,0.52,0.32,0.5,0.3,0.49,0.29,0.46,0.28,0.44,0.26,0.42,0.24,0.39,0.23,0.36,0.2,0.33,0.17,0.28,0.15,0.23,0.12,0.17,0.08};
	public double[] CTest =  {0.17,0.17,0.17,0.17,0.17,0.17,0.24,0.17,0.24,0.17,0.28,0.21,0.31,0.28,0.38,0.31,0.62,0.34,0.66,0.45,0.52,0.41,0.28,0.28};
	public int[] letterG =  {152,85,85,213,199,192,206,192,192,184,192,177,184,184,170,177,170,163,156,170};
	public int[] letterH =  {152,85,85,226,226,226,226,226,226,226,226,226,226,226,226,226,219,226,0,226};
	//public int[] letterI =  {}; Acting wierd
	public int[] letterJ =  {52,85,85,241,234,241,226,234,219,234,175,226,182,212,190,0,212,7,190,14};
	public int[] letterK =  {152,85,85,160,197,160,212,153,219,153,219,153,219,153,219,153,212,153,204,168};
			
	public static void main(String[] args) {
		Analyzer main = new Analyzer();
		System.out.println("Average array size: " + main.findAverageSize());
		System.out.println("C1: " + main.displayMean(main.C1) + " with a length of: " + main.C1.length);
		System.out.println("C2: " + main.displayMean(main.C2) + " with a length of: " + main.C2.length);
		System.out.println("C3: " + main.displayMean(main.C3) + " with a length of: " + main.C3.length);
		System.out.println("C4: " + main.displayMean(main.C4) + " with a length of: " + main.C4.length);
		System.out.println("C5: " + main.displayMean(main.C5) + " with a length of: " + main.C5.length);
		System.out.println("CTest: " + main.displayMean(main.CTest) + " with a length of: " + main.CTest.length);
		System.out.println("Difference: " + main.findDifference(main.findAverageSize(), main.CTest.length));
		//System.out.println("Percent Error of Size: " + main.findPercentErrorOfSize(main.findAverageSize(), main.CTest.length));
//		System.out.println("Small D: " + main.displayMean(main.letterD));
//		System.out.println("Small E: " + main.displayMean(main.letterE));
//		System.out.println("Small F: " + main.displayMean(main.letterF));
//		System.out.println("Small G: " + main.displayMean(main.letterG));
//		System.out.println("Small H: " + main.displayMean(main.letterH));
//		System.out.println("Small J: " + main.displayMean(main.letterJ));
//		System.out.println("Small K: " + main.displayMean(main.letterK));
		
	}
	
	public double displayMean(double[] array) {
		double amount = 0.0;
		for (int i = 0; i < array.length; i++) {
			amount += array[i];
		}
		return amount/array.length;
	}
	
	public String findDifference(int averageSize, int testSize) {
		DecimalFormat df = new DecimalFormat("#.##");
		double average = (displayMean(C1) + displayMean(C2) + displayMean(C3) + displayMean(C4) + displayMean(C5)) / 5;
		double difference = average - (displayMean(CTest) * findPercentErrorOfSize(averageSize, testSize));
		System.out.println("New Average: " + (displayMean(CTest) * findPercentErrorOfSize(averageSize, testSize)));
		System.out.println("Average of all 5: " + average);
		double output = difference / average * 100;
		//double outputAdjusted = output * findPercentErrorOfSize(averageSize, testSize);
		return df.format(Math.abs(output)) + "%";
	}
	
	public int findAverageSize() {
		return (C1.length + C2.length + C3.length + C4.length + C5.length) / 5;
	}
	
	public double findPercentErrorOfSize(int averageSize, int testSize) {
		double averageSizeOfArray = (double) averageSize;
		double testSizeOfInput = (double) testSize;
		double difference = averageSizeOfArray - testSizeOfInput;
		double output = (100 - Math.abs(((difference / averageSizeOfArray * 10.0)))) / 100;
		return output;
	}
}
