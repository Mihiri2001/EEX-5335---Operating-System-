import java.util.Scanner;

class MatrixMultiplier implements Runnable {
    private int row;
    private int[][] A;
    private int[][] B;
    private int[][] C;
    private int size;

    public MatrixMultiplier(int row, int[][] A, int[][] B, int[][] C, int size) {
        this.row = row;
        this.A = A;
        this.B = B;
        this.C = C;
        this.size = size;
    }

    @Override
    public void run() {
        for (int j = 0; j < size; j++) {
            C[row][j] = 0;
            for (int k = 0; k < size; k++) {
                C[row][j] += A[row][k] * B[k][j];
            }
        }
    }
}

public class MultiThreadedMatrixMultiplication {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        final int SIZE = 3; 

        int[][] A = new int[SIZE][SIZE];
        int[][] B = new int[SIZE][SIZE];
        int[][] C = new int[SIZE][SIZE];

        // Input matrix A
        System.out.println("Enter elements for Matrix A (3x3):");
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                System.out.printf("A[%d][%d]: ", i+1, j+1);
                A[i][j] = input.nextInt();
            }
        }

        // Input matrix B
        System.out.println("\nEnter elements for Matrix B (3x3):");
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                System.out.printf("B[%d][%d]: ", i+1, j+1);
                B[i][j] = input.nextInt();
            }
        }

        
        Thread[] threads = new Thread[SIZE];

        for (int i = 0; i < SIZE; i++) {
            threads[i] = new Thread(new MatrixMultiplier(i, A, B, C, SIZE));
            threads[i].start();
        }

        
        for (int i = 0; i < SIZE; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Print result matrix C
        System.out.println("\nResult Matrix C (A x B):");
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                System.out.print(C[i][j] + " ");
            }
            System.out.println();
        }

        input.close();
    }
}
