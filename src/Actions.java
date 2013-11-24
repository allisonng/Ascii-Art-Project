import java.io.IOException;





public class Actions {
	public static void main(String[] args ) throws IOException {
		// Font myFont = new Font("Courier", Font.PLAIN, 12);
		
		CharacterProcess cp = new CharacterProcess();
		float[][] test = cp.convertToBlocks(cp.getImage());
		
		
/*		for(int i = 0; i<test.length; i++){
			for(int j = 0; j<test[0].length; j++){
				System.out.print(test[i][j] + "\t");
			}
			System.out.print("\n");
		}
*/

		cp.convertImageToAscii(test);
		System.out.print("done conversion? check for file");
		
	}
}
