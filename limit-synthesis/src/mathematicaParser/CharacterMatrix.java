package mathematicaParser;

public class CharacterMatrix {
	char[][] charMatrix;
	int height;
	int width;
	char nullChar = '@';
	
	public CharacterMatrix(int height, int width) {
		charMatrix = new char[height][width];
		this.height = height;
		this.width = width;
	}
	
	/**
	 * 
	 * @param stringArr Mathematica Domain Output split into separate lines by the newline character.
	 */
	public void insertCharacters(String[] stringArr) {
		if (!isValidSize(stringArr)) 
			return;
		
		for (int row = 0; row < height; row++) {
			for (int column = 0; column < width; column++) {
				if (column < stringArr[row].length()) {
					charMatrix[row][column] = stringArr[row].charAt(column);
				}
				else {
					charMatrix[row][column] = nullChar;
				}
			}
		}
	}
	
	private boolean isValidSize(String[] stringArr) {
		int height = stringArr.length;
		int width = 0;
		for (int i = 0; i < height; i++) {
			if (stringArr[i].length() > width) 
				width = stringArr[i].length();
		}
		
		if (height > this.height) {
			System.out.println("String inserted into CharacterMatrix is invalid size. CharacterMatrix height " + this.height + " < StringArray Height " + height);
			return false;
		} else if (width > this.width) {
			System.out.println("String inserted into CharacterMatrix is invalid size. CharacterMatrix width " + this.width + " < StringArray Width " + width);
			return false;
		}
		
		return true;
	}
}