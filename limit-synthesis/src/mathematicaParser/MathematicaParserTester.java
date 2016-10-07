package mathematicaParser;

public class MathematicaParserTester {
	static String[] inputs = {
			  "x < -1 || Inequality[-1, Less, x, Less, 0] || Inequality[0, Less, x, Less, Root[-3 - #1 + #1^3 & , 1, 0]] || x > Root[-3 - #1 + #1^3 & , 1, 0]", 
			  "x < -1 || Inequality[-1, Less, x, LessEqual, -(1/Sqrt[2])] || Inequality[0, LessEqual, x, LessEqual, 1/Sqrt[2]] || x > 1",
			  "x > 0",
			  "NotElement[1/2 + x/Pi, Integers] && Surd[-x + 2*Tan[x], 3] >= 0",
			  "x < -1 || Inequality[-1, Less, x, Less, 0] || x > 0"};
	
	public static void main(String[] args) {
		for (String str : inputs) {
			System.out.println("Original:\n" + str);
			System.out.println("Processed:\n" + MathematicaParser.parseToOneLine(str) + "\n");
		}
	}
}
