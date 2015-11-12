

package matmult;

import java.io.*;
import java.util.*;
import java.awt.*;
import static matmult.StdDraw.*;


public class Matmult {

	static Color alg1Col=BOOK_BLUE;
	static Color alg2Col=BOOK_RED;

	static double maxTime=100;

	public static void main(String[] args) {
		Random gen = new Random();

		int maxn=600; //getInt("Enter the max number n: ");
		//System.out.println("maxn: "+maxn);

		setupGraphic(maxn);
		double xstart=.05; // x values to plot are from .05 to 1
		double ystart=.05;


		for(int n=2;n<maxn;n++){
			//initialize and fill 2 matrices A and B with random integers
			int[][] A=new int[n][n];
			int[][] B=new int[n][n];
			int[][] C,D;
			for(int i=0;i<n;i++){
				for(int j=0;j<n;j++){
					A[i][j]=gen.nextInt(100);
					B[i][j]=gen.nextInt(100);
				}
			}

			//time the multiplications
			long startTime=System.currentTimeMillis();

			C=mult1(A,B);

			long t1=System.currentTimeMillis()-startTime;

			D=mult2(A,B);
			long t2=(System.currentTimeMillis()-startTime)-t1;


			System.out.println(t1);
			System.out.println(t2);

			//System.out.println(C[0][0]);
			//System.out.println(D[0][0]);

			// show result on graph
			setPenColor(alg1Col);
			point(map(n,2,maxn,xstart,1),map(t1,0,maxTime,ystart,1));
			setPenColor(alg2Col);
			point(map(n,2,maxn,xstart,1),map(maxTime/2,0,maxTime,ystart,1));


		}

	}



	static int[][] mult1(int[][] a, int[][] b){
		int m=a.length;
		int n=a[0].length;
		int p=b[0].length;
		int sum;
		int[][] c=new int[m][p];
		for(int i=0;i<m;i++)
			for(int j=0;j<p;j++){
				sum=0;
				for(int k=0;k<n;k++)
					sum+=a[i][k]*b[k][j];
				c[i][j]=sum;
			}
		return c;
	}
	
	static int[][] mult2(int[][] A, int[][] B){
		int n=A.length;

		int[][] C=new int[n][n]; // java fills with zeros

		return C;
	}

	// utility functions ///////////////////////////////////////////////////////
	// just return the string entered, (or returns " " if user entered nothing)
	static String getString(){
    String s="";
    try{
    	s=new BufferedReader(new InputStreamReader(System.in)).readLine();
    }catch(IOException e){
      System.err.println("IOException");
      System.exit(1);
    }
    if(s.equals(""))
      return " ";
    return s;
	}

	static int getInt(String prompt){
		while(true){
			System.out.print(prompt);
			String s=getString();
			Scanner scan=new Scanner(s);
			if(scan.hasNextInt())
				return scan.nextInt();
		}
	}

  // stuff for graphical output
	static void setupGraphic(int maxn){
		setCanvasSize(800,600);
		setPenRadius(.001);
		line(0,.05,1,.05);
		line(.05,0,.05,1);
		text(0,.5,"Time");
		text(.5,0,"n (matrix size n x n)");
		setPenColor(alg1Col);
		text(.3,1.02,String.format("Alg 1"));
		setPenColor(alg2Col);
		text(.7,1.02,String.format("Alg 2"));

		setPenRadius(.005);
	}

	// helpful map function (from arduino library)
	// returns x maped from in_min-in_max TO out_min-out_max
	static double map(double x, double in_min, double in_max, double out_min, double out_max)
	{
		return (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
	}

}
