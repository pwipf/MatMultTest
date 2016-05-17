// Philip Wipf
// Caleb Bryson
// CS 432 Hwk 5 Group proj
// 11 Nov 2015
//
// Compares and plots the runtimes of two different matrix multiplication
// algorithms at different matrix sizes, n.
//
// Simple implementation only uses square matrices

package matmult;

import java.util.*;
import java.awt.*;
import static matmult.StdDraw.*;


public class Matmult {

	static Color alg1Col=BOOK_BLUE;
	static Color alg2Col=BOOK_RED;

	static double maxTime=15000;

	public static void main(String[] args) {
		Random gen = new Random();

		int maxn=1200;
        int minn=16;

		setupGraphic(maxn,minn,(int)maxTime);
		double xstart=.05; // x values to plot are from .05 to 1
		double ystart=.05;


		for(int n=minn;n<maxn;n+=4){
			//initialize and fill 2 matrices A and B with random integers
			int[][] A=new int[n][n];
			int[][] B=new int[n][n];
			int[][] C,D;
			for(int i=0;i<n;i++){
				for(int j=0;j<n;j++){
					A[i][j]=gen.nextInt(100)+1;
					B[i][j]=gen.nextInt(100)+1;
				}
			}

			//time the multiplications
			long startTime=System.currentTimeMillis();

			C=naive(A,B);

			long t1=System.currentTimeMillis()-startTime;

			D=strassen(A,B);

			long t2=(System.currentTimeMillis()-startTime)-t1;


//			System.out.println("\n            N: "+n);
//			System.out.println("   naive time: "+t1);
//			System.out.println("strassen time: "+t2);

			// just curious...
//			boolean correct=true;
//			for(int i=0;i<n;i++)
//				for(int j=0;j<n;j++)
//					if(C[i][j]!=D[i][j])
//						correct=false;
			//System.out.println("correct? "+(correct? "true": "false"));

			// show result on graph
			setPenColor(alg1Col);
			point(map(n,minn,maxn,xstart,1),map(t1,0,maxTime,ystart,1));
			setPenColor(alg2Col);
			point(map(n,minn,maxn,xstart,1),map(t2,0,maxTime,ystart,1));


		}

	}



	static int[][] naive(int[][] a, int[][] b){
		int m=a.length;
		int n=a[0].length;
		int p=b[0].length;
		int[][] c=new int[m][p];
		for(int i=0;i<m;i++)
			for(int j=0;j<p;j++)
				for(int k=0;k<n;k++)
					c[i][j]+=a[i][k]*b[k][j];

		return c;
	}

	// strassen's recursive alg
	// assumes matrix is square and a power of 2 size
	static int[][] strassen(int[][] A, int[][] B){
		int n=A.length;

		if(n<=16){
			return naive(A,B);
		}

		// java fills with zeros
		int[][] C=new int[n][n];

		int halfsize=n/2;

		//init new quarters
		int[][] a11=new int[halfsize][halfsize];
		int[][] a12=new int[halfsize][halfsize];
		int[][] a21=new int[halfsize][halfsize];
		int[][] a22=new int[halfsize][halfsize];
		int[][] b11=new int[halfsize][halfsize];
		int[][] b12=new int[halfsize][halfsize];
		int[][] b21=new int[halfsize][halfsize];
		int[][] b22=new int[halfsize][halfsize];
		int[][] c11;
		int[][] c21;
		int[][] c12;
		int[][] c22;

		//fill quarters with corresponding parts of A and B
		for(int i=0;i<halfsize;i++){
			for(int j=0;j<halfsize;j++){
				a11[i][j]=A[i][j];
				a21[i][j]=A[i+halfsize][j];
				a12[i][j]=A[i][j+halfsize];
				a22[i][j]=A[i+halfsize][j+halfsize];

				b11[i][j]=B[i][j];
				b21[i][j]=B[i+halfsize][j];
				b12[i][j]=B[i][j+halfsize];
				b22[i][j]=B[i+halfsize][j+halfsize];
			}
		}

		// a
		// m1=a11+a22
		// m2=a21+a22
		// m3=a11
		// m4=a22
		// m5=a11+a12
		// m6=a21-a11
		// m7=a12-a22

		// b
		// m1=b11+b22
		// m2=b11
		// m3=b12-b22
		// m4=b21-b11
		// m5=22
		// m6=b11+b12
		// m7=b21+b22

		int[][] m1=strassen(matAdd(a11,a22),matAdd(b11,b22));
		int[][] m2=strassen(matAdd(a21,a22),b11);
		int[][] m3=strassen(a11,matSub(b12,b22));
		int[][] m4=strassen(a22,matSub(b21,b11));
		int[][] m5=strassen(matAdd(a11,a12),b22);
		int[][] m6=strassen(matSub(a21,a11),matAdd(b11,b12));
		int[][] m7=strassen(matSub(a12,a22),matAdd(b21,b22));


		c11=matAdd(matSub(matAdd(m1,m4),m5),m7);
		c21=matAdd(m2,m4);
		c12=matAdd(m3,m5);
		c22=matAdd(matAdd(matSub(m1,m2),m3),m6);

		//put C quarters together
		for(int i=0;i<halfsize;i++){
			for(int j=0;j<halfsize;j++){
				C[i][j]                  =c11[i][j];
				C[i+halfsize][j]         =c21[i][j];
				C[i][j+halfsize]         =c12[i][j];
				C[i+halfsize][j+halfsize]=c22[i][j];
			}
		}
		return C;
	}

	static int[][] matAdd(int[][]a,int[][]b){
		int m=a.length;
		int n=a[0].length;
		int[][]c=new int[m][n];
		for(int i=0;i<m;i++)
			for(int j=0;j<n;j++)
				c[i][j]=a[i][j]+b[i][j];
		return c;
	}

	static int[][] matSub(int[][]a,int[][]b){
		int m=a.length;
		int n=a[0].length;
		int[][]c=new int[m][n];
		for(int i=0;i<m;i++)
			for(int j=0;j<n;j++)
				c[i][j]=a[i][j]-b[i][j];
		return c;
	}

	// utility functions ///////////////////////////////////////////////////////
  // stuff for graphical output
	static void setupGraphic(int maxx, int minx, int maxy){
		setCanvasSize(800,600);
		setPenRadius(.001);
		line(0,.05,1,.05);
		line(.05,0,.05,1);
		text(0,.5,"Time");
		text(0,.45,"(sec)");
		text(.5,0,"n (matrix size n x n)");
		text(.97,.02,String.format("%d",maxx));
		text(.06,.02,String.format("%d",minx));
		text(.022,.97,String.format("%d",maxy/1000));
		text(.042,.06,String.format("%d",0));
		setPenColor(alg1Col);
		text(.3,1.02,String.format("Naive Alg"));
		setPenColor(alg2Col);
		text(.7,1.02,String.format("Strassen"));
		setPenRadius(.005);
	}

	// helpful map function
	// returns x maped from in_min-in_max TO out_min-out_max
	static double map(double x, double in_min, double in_max, double out_min, double out_max)
	{
		return (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
	}

}
