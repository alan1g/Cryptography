import java.math.BigInteger;
/**
 * Alice’s public key is (83187685431865799, 231541186, 58121575766172118).
 * Bob sends her the cipher (15714167638989179,1416052582726447).
 * This programme calculates Alice's private key and extracts the message Bob is sending.
 * It uses the Baby Steps Giant Steps algorithm to reduce the complexity to O(sqrt(n)).
 * 
 * @author alan1g
 */


public class PublicKeyCrypt 
{
	public static BigInteger x;//variable to store key
	public static void main(String args[])
	{
		final long startTime = System.currentTimeMillis();//start timer
		
		BigInteger p = new BigInteger("83187685431865799");
		BigInteger sqrt = SqRtN(p.subtract(BigInteger.ONE));//newton's method
		int m = sqrt.intValue();//convert sqrt to int to initialize size of array
		m= m/2;//keep reducing m to avoid memory and runtime errors.
		m= m/2;
		m= m/2;
		m= m/2;
		m= m/2;
		m= m/2;
		BigInteger y = new BigInteger("58121575766172118");
		BigInteger g = new BigInteger("231541186");
		BigInteger j = BigInteger.ZERO;//counter to get BigInteger value of i		
		
		Array ar = new Array(m);
		for(int i=0;i<m;i++)//fill array
		{
			Node object = new Node();
			object.BigIntj=j;
			object.c = g.modPow(j, p);
			ar.insert(object);
			j=j.add(BigInteger.ONE);//increase j++
		}
		
		ar.quickSort();
		
		BigInteger power = p.subtract(BigInteger.ONE).subtract(sqrt);
		BigInteger c = g.modPow(power, p);//initialize c
		BigInteger t = y;//let t=y
		
		//Compute t and search for in Array ar
		for(BigInteger i = BigInteger.ZERO;i.compareTo(sqrt)<=0;i=i.add(BigInteger.ONE))
		{
			Node temp = ar.find(t);
			if(temp==null)//did not find, assign new value to t
			{
				t = t.multiply(c).mod(p);
			}
			else//found it, calculate x
			{
				x = (sqrt.multiply(i)).add(temp.BigIntj);
				System.out.println("p: "+p);
				System.out.println("sqrt(p-1)): "+sqrt);
				System.out.println("j: "+temp.BigIntj);
				System.out.println("temp.c: "+temp.c);
				System.out.println("i: "+i);
				System.out.println("t: "+t);
				break;
			}
			
		}
		System.out.println("x: "+x);
		BigInteger c1 = new BigInteger("15714167638989179"); 
		BigInteger c2 = new BigInteger("1416052582726447");
		BigInteger pow = p.subtract(BigInteger.ONE).subtract(x);
		BigInteger c1x = c1.modPow(pow, p);
		BigInteger message = c1x.multiply(c2).mod(p);
		System.out.println("Message: "+message);
		
		final long elapsedTimeMillis = System.currentTimeMillis() - startTime;
		System.out.println("Time taken: "+elapsedTimeMillis+" m/s");
		
	
	}
	/**
	 * Newton's Method to find the square root of a BigInteger 
	 * @param N
	 * @return 
	 */
	public static BigInteger SqRtN(BigInteger N)
    {
      BigInteger G = new BigInteger((N.shiftRight((N.bitLength() + 1) / 2)).toString());
      BigInteger LastG = null;
      BigInteger One = new BigInteger("1");
      while (true)
      {
        LastG = G;
        G = N.divide(G).add(G).shiftRight(1);
        int i = G.compareTo(LastG);
        if (i == 0) 
        	return G;
        if (i < 0)
        {
          if (LastG.subtract(G).compareTo(One) == 0)
            if (G.multiply(G).compareTo(N) < 0 && LastG.multiply(LastG).compareTo(N) > 0) return G;
        }
        else
        {
          if (G.subtract(LastG).compareTo(One) == 0)
            if (LastG.multiply(LastG).compareTo(N) < 0 && G.multiply(G).compareTo(N) > 0) return LastG;
        }
      }
    }
}
/**
 * This class describes an object of type Node 
 */
class Node
{
	BigInteger BigIntj;
	BigInteger c;
}
/**
 * Quick sort Algorithm
 */
class Array
{
	public Node theArray[];
	private int nElems;
	
	public Array(int max)          // constructor
    {
    theArray = new Node[max];      // create the array
    nElems = 0;                    // no items yet
    }
	
	public void insert(Node value)    // put element into array
    {
    theArray[nElems] = value;      // insert it
    nElems++;                      // increment size
    }
//--------------------------------------------------------------
	//Binary Search
	public Node find(BigInteger key)
	{
		int lowerBound =0;
		int upperBound = nElems-1;
		
		int curIn;
		while(true)
		{
			curIn = (lowerBound + upperBound)/2;
			if(theArray[curIn].c.compareTo(key)==0)
			{
				return theArray[curIn];
			}
			else if(lowerBound > upperBound)
			{
				return null;
			}
			else
			{
				if(theArray[curIn].c.compareTo(key)<0)
				{
					lowerBound = curIn+1;
				}
				else
				{
					upperBound = curIn-1;
				}
			}
		}
	}
	public void display()             // displays array contents
    {
    
    for(int j=0; j<nElems; j++)
    {
    	System.out.print("("+theArray[j].BigIntj+","+theArray[j].c+"), ");// for each element,
    }
       
    System.out.println();
    }
//--------------------------------------------------------------
	public void quickSort()
    {
    recQuickSort(0, nElems-1);
    }
//--------------------------------------------------------------
	public void recQuickSort(int left, int right)
    {
    if(right-left <= 0)              // if size <= 1,
        return;                      //    already sorted
    else                             // size is 2 or larger
       {
       Node pivot = theArray[right];      // rightmost item
                                          // partition range
       int partition = partitionIt(left, right, pivot);
       recQuickSort(left, partition-1);   // sort left side
       recQuickSort(partition+1, right);  // sort right side
       }
    }  // end recQuickSort()
//--------------------------------------------------------------
	public int partitionIt(int left, int right, Node pivot)
     {
     int leftPtr = left-1;           // left    (after ++)
     int rightPtr = right;           // right-1 (after --)
     while(true)
        {                            // find bigger item
        while( theArray[++leftPtr].c.compareTo(pivot.c)<0)
           ;  // (nop)
                                     // find smaller item
        while(rightPtr > 0 && theArray[--rightPtr].c.compareTo(pivot.c) >0)
           ;  // (nop)

        if(leftPtr >= rightPtr)      // if pointers cross,
           break;                    //    partition done
        else                         // not crossed, so
           swap(leftPtr, rightPtr);  //    swap elements
        }  // end while(true)
     swap(leftPtr, right);           // restore pivot
     return leftPtr;                 // return pivot location
     }  // end partitionIt()
//--------------------------------------------------------------
	public void swap(int dex1, int dex2)  // swap two elements
    {
    Node temp = theArray[dex1];        // A into temp
    theArray[dex1] = theArray[dex2];   // B into A
    theArray[dex2] = temp;             // temp into B
    }  // end swap(
//-------

}
