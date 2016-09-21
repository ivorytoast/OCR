public class Analyzer {
	
	public int[] letterA =  {152,85,85,182,153,160,146,138,146,138,153,138,168,131,175,153,175,168,168,168};
	public int[] letterB =  {152,85,85,175,175,175,175,175,175,168,175,153,175,146,175,117,175,73,0,65};
	public int[] letterC =  {152,85,85,206,206,206,199,206,192,206,184,206,177,206,170,199,156,184,135,177};
	public int[] letterD =  {152,85,85,204,204,197,204,197,204,190,204,190,204,190,204,175,204,168,197,153};
	public int[] letterE =  {152,85,85,175,175,175,175,175,175,175,175,175,175,175,175,175,0,175,0,175};
	public int[] letterF =  {152,85,85,197,197,197,197,197,197,197,197,197,197,197,0,197,0,197,0,197};
	public int[] letterG =  {152,85,85,213,199,192,206,192,192,184,192,177,184,184,170,177,170,163,156,170};
	public int[] letterH =  {152,85,85,226,226,226,226,226,226,226,226,226,226,226,226,226,219,226,0,226};
	//public int[] letterI =  {}; Acting wierd
	public int[] letterJ =  {52,85,85,241,234,241,226,234,219,234,175,226,182,212,190,0,212,7,190,14};
	public int[] letterK =  {152,85,85,160,197,160,212,153,219,153,219,153,219,153,219,153,212,153,204,168};
			
	public static void main(String[] args) {
		Analyzer main = new Analyzer();
		System.out.println("Small A: " + main.displayMean(main.letterA));
		System.out.println("Small B: " + main.displayMean(main.letterB));
		System.out.println("Small C: " + main.displayMean(main.letterC));
		System.out.println("Small D: " + main.displayMean(main.letterD));
		System.out.println("Small E: " + main.displayMean(main.letterE));
		System.out.println("Small F: " + main.displayMean(main.letterF));
		System.out.println("Small G: " + main.displayMean(main.letterG));
		System.out.println("Small H: " + main.displayMean(main.letterH));
		System.out.println("Small J: " + main.displayMean(main.letterJ));
		System.out.println("Small K: " + main.displayMean(main.letterK));
		
	}
	
	public int displayMean(int[] array) {
		int amount = 0;
		for (int i = 0; i < array.length; i++) {
			amount += array[i];
		}
		return amount/array.length;
	}
}
