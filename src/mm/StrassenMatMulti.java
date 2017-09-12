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
		int[][] matC = bruteForce(matA, matB);
		int[][] matD = strassen(matA, matB);
		
		System.out.println("Brute Force:");
		for(int r = 0; r <matC.length; r++){
			for(int c = 0; c < matC[r].length; c++){
				System.out.print(matC[r][c] + " ");
			}
			System.out.println();
		}
		
		System.out.println("Strassen:");
		for(int r = 0; r <matD.length; r++){
			for(int c = 0; c < matD[r].length; c++){
				System.out.print(matD[r][c] + " ");
			}
			System.out.println();
		}

	}

	public static int[][] bruteForce(int[][] matA, int[][] matB){
		int size = matA.length;
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

	public static int[][] strassen(int[][] matA, int[][] matB){
		int size = matA.length;
		//initializing matrices
		int[][] A11 = new int [size/2][size/2];
		int[][] A12 = new int [size/2][size/2];
		int[][] A21 = new int [size/2][size/2];
		int[][] A22 = new int [size/2][size/2];
		int[][] B11 = new int [size/2][size/2];
		int[][] B12 = new int [size/2][size/2];
		int[][] B21 = new int [size/2][size/2];
		int[][] B22 = new int [size/2][size/2];
		
		int[][] S1 = new int[size/2][size/2];
		int[][] S2 = new int[size/2][size/2];
		int[][] S3 = new int[size/2][size/2];
		int[][] S4 = new int[size/2][size/2];
		int[][] S5 = new int[size/2][size/2];
		int[][] S6 = new int[size/2][size/2];
		int[][] S7 = new int[size/2][size/2];
		int[][] S8 = new int[size/2][size/2];
		int[][] S9 = new int[size/2][size/2];
		int[][] S10 = new int[size/2][size/2];
		
		int[][] P1 = new int[size/2][size/2];
		int[][] P2 = new int[size/2][size/2];
		int[][] P3 = new int[size/2][size/2];
		int[][] P4 = new int[size/2][size/2];
		int[][] P5 = new int[size/2][size/2];
		int[][] P6 = new int[size/2][size/2];
		int[][] P7 = new int[size/2][size/2];
		
		int[][] C11 = new int[size/2][size/2];
		int[][] C12 = new int[size/2][size/2];
		int[][] C21 = new int[size/2][size/2];
		int[][] C22 = new int[size/2][size/2];
		
		//cutting each matrix into 4
		A11 = seperate(matA, A11, 0, 0);
		A12 = seperate(matA, A12, 0, size / 2);
		A21 = seperate(matA, A21, size / 2, 0);
		A22 = seperate(matA, A22, size / 2, size / 2);
		B11 = seperate(matB, B11, 0, 0);
		B12 = seperate(matB, B12, 0, size / 2);
		B21 = seperate(matB, B21, size / 2, 0);
		B22 = seperate(matB, B22, size / 2, size / 2);
		
		//10 S matrices
		S1 = matSub(B12, B22);
		S2 = matAdd(A11, A12);
		S3 = matAdd(A21, A22);
		S4 = matSub(B21, B11);
		S5 = matAdd(A11, A22);
		S6 = matAdd(B11, B22);
		S7 = matSub(A12, A22);
		S8 = matAdd(B21, B22);
		S9 = matSub(A11, A21);
		S10 = matAdd(B11, B12);
			
		//7 P matrices
		P1 = matMulti(A11, S1);
		P2 = matMulti(S2, B22);
		P3 = matMulti(S3, B11);
		P4 = matMulti(A22, S4);
		P5 = matMulti(S5, S6);
		P6 = matMulti(S7, S8);
		P7 = matMulti(S9, S10);
		
		//C11
		C11 = matAdd(P5, P4);
		C11 = matSub(C11, P2);
		C11 = matAdd(C11, P6);
		//C12
		C12 = matAdd(P1, P2);
		//C21
		C21 = matAdd(P3, P4);
		//C22
		C22 = matAdd(P5, P1);
		C22 = matSub(C22, P3);
		C22 = matSub(C22, P7);
		
		return combine(C11, C12, C21, C22, size);
	}
	
	public static int[][] matMulti(int[][] matA, int[][] matB){
		int size = matA.length;
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

	public static int[][] seperate(int[][] mainMat, int[][] subMat, int row, int col){
		for(int r1 = 0, r2 = row; r1 < subMat.length; r1++, r2++){
			for(int c1 = 0, c2 = col; c1 < subMat.length; c1++, c2++){
				subMat[r1][c1] = mainMat [r2][c2];
			}
		}
		return subMat;
	}
	
	public static int[][] combine(int[][]C1, int[][]C2, int[][]C3, int[][]C4, int size){
		int[][] matC = new int[size][size];
		int cut = size / 2;
		
		for(int r = 0; r < matC.length; r++){
			for(int c = 0; c < matC[r].length; c++){
				//C11
				if(r < cut && c < cut){
					matC[r][c] = C1[r][c];
				}
				//C12
				else if(r < cut && c >= cut){
					matC[r][c] = C2[r][c - cut];
				}
				//C21
				else if(r >= cut && c < cut){
					matC[r][c] = C3[r - cut][c];
				}
				//C22
				else if(r >= cut && c >= cut){
					matC[r][c] = C4[r - cut][c - cut];
				}
			}
		}
		
		return matC;
	}
	
	public static int[][] matSub(int[][] matA, int[][] matB){
		int size = matA.length;
		int[][] matC = new int[size][size];
		
		for(int r = 0; r < size; r++){
			for(int c = 0; c< size; c++){
				matC[r][c] = matA[r][c] - matB[r][c];
			}
		}
		
		return matC;
	}
	
	public static int[][] matAdd(int[][] matA, int[][] matB){
		int size = matA.length;
		int[][] matC = new int[size][size];
		
		for(int r = 0; r < size; r++){
			for(int c = 0; c< size; c++){
				matC[r][c] = matA[r][c] + matB[r][c];
			}
		}
		
		return matC;
	}
}
