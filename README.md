# Matrix Multiplication

This is a simple java (netbeans) project which runs a comparison of two matrix multiplication algorithms and displays
a graph of the result.

For simplification the test matrices are all square n x n matrices.

The test matrices have random elements from 1 to 100;

The "naive algorithm" is a very simple 3 nested loops, with time complexity of O(n<sup>3</sup>).  
The Strassen algorithm is Strassen's recursive algorithm, with time complexity of O(n<sup>2.807</sup>).

This program is very basic and just an attempt to visualize the difference, showing the increasing time as the size of the
matrices get larger.  In fact, the strassen algorithm is not even accurate except on power-of-two size matrices (but the difference
in time to get the right result is not significant).

Output (this took about an hour to run):
![image](/output.jpg?raw=true "output")
