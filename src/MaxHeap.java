
public class MaxHeap {
	private Point [] arrayHeap; //a pointer to the heap array
	private int num; //number of objects in heap
	private int maxSize; //maximum size of the heap

	
	/**Constructor */
	public MaxHeap(int maxSize){
	num =0; 
	this.maxSize = maxSize;
	arrayHeap = new Point [maxSize];
	}

	/** @return Current size of the heap */
	public int heapsize() { 
		return num; 
	}
	public Point[] getArrayHeap(){
		return arrayHeap;
	}
	public boolean hasLeftChild(int i) {
		return leftchild(i) < num;
	}


	public boolean hasRightChild(int i) {
		return rightchild(i) < num;
	}

	/** @return Position for left child of currIndex */
	public int leftchild(int currIndex) {
	  return 2*currIndex + 1;
	}
	
	/** @return Position for right child of currIndex */
	public int rightchild(int currIndex) { 
	  return 2*currIndex + 2;
	}
	
	/** @return Position for parent */
	public int parent(int currIndex) { 
	  return (currIndex-1)/2;
	}
	
	/** Insert a new point into heap */
	public void insert(Point p) {
	  if (num < maxSize) {// Heap is not full
	  int curr = num++;
	  arrayHeap[curr] = p;             // put p Start end of arrayHeap
	  // Now sift up until curr's parent's key > curr's key
	//while not a maxHeap - son has bigger value than parent - swap      	
	  while  
		((arrayHeap[curr].getY() > (arrayHeap[parent(curr)]).getY() 
				|| ((arrayHeap[curr]).getY() == (arrayHeap[parent(curr)]).getY()
				&& ((arrayHeap[curr]).getX() > (arrayHeap[parent(curr)]).getX())))){ 
		  swap( curr, parent(curr));
		  curr = parent(curr);
			  	
	  	}
	  }
	}  
	  
	private void swap( int curr, int parent) {
		Point temp = arrayHeap [curr];
		arrayHeap[curr]= arrayHeap[parent];
		arrayHeap[parent]=temp ;	
	}


	/** Put element in its correct place */
	private void maxHeapify(int i) {
		int left = leftchild(i);
		int right = rightchild(i);
		int largest;
		if ((left < num )&&(arrayHeap[left].getY()>arrayHeap[i].getY())){ //if in range and left value is bigger than parent
			largest = left; 
		}
		else largest = i;
		if ((right<num)&&(arrayHeap[right].getY()>arrayHeap[largest].getY())){ //if in range and right value is bigger than parent
			largest = right;
		}
		if (largest != i){ //if the largest of the 3 is is not i - swap the largest with i
			swap( i , largest);
			maxHeapify(largest); //Recursively continue to the rest of the heap - direction: down
		}
		
			
	}
	/** @return maximum value , without deleting*/
	
	public Point max(){
		if (num>=1){
			return arrayHeap[0];
		}
		else throw new RuntimeException("heap is empty");
		}
	
	public Point removeMax() {
		if (num >= 1){
			Point max = arrayHeap [0];
			swap(0,num-1); //move last element to top of heap
			num=num-1; //update size of heap 
			maxHeapify(0);
			arrayHeap[num]=null;
			return max;
		}
		throw new RuntimeException("heap underflow"); 
	}


}	
