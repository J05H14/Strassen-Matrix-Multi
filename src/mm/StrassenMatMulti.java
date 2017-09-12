package mm;

public class StrassenMatMulti {

	public static void main(String[] args) {
		int[][] matA = {
				{4, 5},
				{6, 9}
		};
		int[][] matB = {
				{2, 4},
				{3, 7}
		};
		int[][] matC = bruteForce(matA, matB, 2);

		for(int r =0; r <matC.length; r++){
			for(int c = 0; c < matC[r].length; c++){
				System.out.print(matC[r][c] + " ");
			}
			System.out.println();
		}

	}

	public static int[][] bruteForce(int[][] matA, int[][] matB, int size){
		int[][] matC = new int[size][size];

		for(int row = 0; row < size; row++){
			for(int col = 0; col < size; col++){
				matC[row][col] = 0;
				for(int down = 0; down < size; down++){
					matC[row][col] += matA[row][down] * matB[down][col];
				}
			}
		}

		return matC;
	}

	public static int[][] strassen(int[][] matA, int[][] matB, int size){
		int[][] A11 = new int [size/2][size/2];
		int[][] A12 = new int [size/2][size/2];
		int[][] A21 = new int [size/2][size/2];
		int[][] A22 = new int [size/2][size/2];
		int[][] B11 = new int [size/2][size/2];
		int[][] B12 = new int [size/2][size/2];
		int[][] B21 = new int [size/2][size/2];
		int[][] B22 = new int [size/2][size/2];
		
		seperate(matA, A11, 0, 0);
		seperate(matA, A12, 0, size / 2);
		seperate(matA, A21, size / 2, 0);
		seperate(matA, A22, size / 2, 0);
	}

	public static void seperate(int[][] mainMat, int[][] subMat, int row, int col){
		for(int r1 = 0, r2 = row; r1 < subMat.length; r1++, r2++){
			for(int c1 = 0, c2 = col; c1 < subMat.length; c1++, c2++){
				subMat[r1][c1] = mainMat [r2][c2];
			}
		}
	}
}
