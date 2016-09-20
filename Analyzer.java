
public class Analyzer {
	
	public int[] smallA =  {152,85,85,154,151,148,144,141,138,141};
	public int[] mediumA = {152,85,85,157,161,152,157,148,150,144};
	public int[] largeA =  {152,85,85,160,167,158,164,156,161,153};
	public int[] testA =   {152,85,85,186,176,180,173,175,168,170};
	public int[] test2A =  {152,85,85,189,182,186,180,184,177,181};
	public int[] test3A =  {152,85,85,181,161,169,148,156,144,144};
	
	public static void main(String[] args) {
		Analyzer main = new Analyzer();
		System.out.println("Small A: " + main.smallA());
		System.out.println("Medium A: " + main.mediumA());
		System.out.println("Large A: " + main.largeA());
		System.out.println("Test A: " + main.testA());
		System.out.println("Test 2A: " + main.test2A());
		System.out.println("Test 3A: " + main.test3A());
	}
	
	public int smallA() {
		int amount = 0;
		for (int i = 0; i < smallA.length; i++) {
			amount += smallA[i];
		}
		return amount/smallA.length;
	}
	
	public int mediumA() {
		int amount = 0;
		for (int i = 0; i < mediumA.length; i++) {
			amount += mediumA[i];
		}
		return amount/mediumA.length;
	}
	
	public int largeA() {
		int amount = 0;
		for (int i = 0; i < largeA.length; i++) {
			amount += largeA[i];
		}
		return amount/largeA.length;
	}
	
	public int testA() {
		int amount = 0;
		for (int i = 0; i < testA.length; i++) {
			amount += testA[i];
		}
		return amount/testA.length;
	}
	
	public int test2A() {
		int amount = 0;
		for (int i = 0; i < test2A.length; i++) {
			amount += test2A[i];
		}
		return amount/test2A.length;
	}
	
	public int test3A() {
		int amount = 0;
		for (int i = 0; i < test3A.length; i++) {
			amount += test3A[i];
		}
		return amount/test3A.length;
	}

}
