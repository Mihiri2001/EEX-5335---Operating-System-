#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>

#define SIZE 3

int A[SIZE][SIZE];
int B[SIZE][SIZE];
int C[SIZE][SIZE];


void* multiply_row(void* arg) {
    int row = *(int*)arg;

    for (int j = 0; j < SIZE; j++) {
        C[row][j] = 0;  
        for (int k = 0; k < SIZE; k++) {
            C[row][j] += A[row][k] * B[k][j];
        }
    }

    pthread_exit(NULL);
}

int main() {
    pthread_t threads[SIZE];
    int row_indices[SIZE];

    // Input matrix A
    printf("Enter elements for Matrix A (%dx%d):\n", SIZE, SIZE);
    for (int i = 0; i < SIZE; i++) {
        for (int j = 0; j < SIZE; j++) {
            printf("A[%d][%d]: ", i+1, j+1);
            scanf("%d", &A[i][j]);
        }
    }

    // Input matrix B
    printf("\nEnter elements for Matrix B (%dx%d):\n", SIZE, SIZE);
    for (int i = 0; i < SIZE; i++) {
        for (int j = 0; j < SIZE; j++) {
            printf("B[%d][%d]: ", i+1, j+1);
            scanf("%d", &B[i][j]);
        }
    }

    
    for (int i = 0; i < SIZE; i++) {
        row_indices[i] = i;
        pthread_create(&threads[i], NULL, multiply_row, (void*)&row_indices[i]);
    }

    
    for (int i = 0; i < SIZE; i++) {
        pthread_join(threads[i], NULL);
    }

    // Print the result matrix C
    printf("\nResult Matrix C (A x B):\n");
    for (int i = 0; i < SIZE; i++) {
        for (int j = 0; j < SIZE; j++) {
            printf("%4d ", C[i][j]);
        }
        printf("\n");
    }

    return 0;
}
