import java.io.IOException;





public class TestMain {
	public static void main(String[] args ) throws IOException {
		

		/*
		for(int i=32; i<127; i++){
			System.out.print(Character.toString ((char) i));
		}
		String str = "` 1234567890-=qwertyuiop[]\\asdfghjkl;'zxcvbnm,./~!@#$%^&*()_+QWERTYUIOPPPP{}ASDFGHJKL:\"ZXCVBNM<>?";
		System.out.println(str.length());
		*/
//		for(int y=0; (y+4)<=8; y+=4){
//			for(int x=0; (x+4)<=512; x+=4){
//				System.out.println("row " + y + "\tcol" + x);
//			}
//		}	
		
		
		
		// Font myFont = new Font("Courier", Font.PLAIN, 12);
		
/*		BlockBrightness bb = new BlockBrightness();
		int[][] dmatrix = bb.convertImageToBrightnessMatrix(bb.getImage());
		CharacterProcess cp = new CharacterProcess();
		cp.convertImageToAscii(dmatrix, CharacterProcess.BLACKWHITE);
*/
//		double[][] test = cp.convertImageToBrightnessMatrix(cp.getImage());
		
		
/*		for(int i = 0; i<test.length; i++){
			for(int j = 0; j<test[0].length; j++){
				System.out.print(test[i][j] + "\t");
			}
			System.out.print("\n");
		}
*/

//		cp.convertImageToAscii(test);
//		System.out.print("done conversion? check for file");
		
		String test = "abc" + 'd';
		System.out.println(test);
	}
}
