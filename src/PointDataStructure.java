
public class PointDataStructure implements PDT {
	
	private MinHeap minHeap;
	private MaxHeap maxHeap;
	private BinarySearchTree t; 


	/**Constructor
	 * @param points
	 * @param initialYMedianPoint
	 */
	public PointDataStructure(Point[] points, Point initialYMedianPoint){
		int n = points.length;
		boolean found = false;
		maxHeap = new MaxHeap((int) (n/2 + 5*(Math.log(n)/Math.log(2))));
		minHeap = new MinHeap((int) (n/2 + 5*(Math.log(n)/Math.log(2))));
		for( int i = 0 ; i < points.length ; i++ ){
			if( (points[i]).getY() < (initialYMedianPoint).getY())
				maxHeap.insert(points[i]); 
			else if( (points[i]).getY() > (initialYMedianPoint).getY())
				minHeap.insert(points[i]);
			//if the Y value is equal
			else{ 
				if( (points[i]).getX() >= (initialYMedianPoint).getX() ) 
					minHeap.insert(points[i]);
				else maxHeap.insert(points[i]); 
			}
		}
		//checks if the "points" array is sorted
		for( int j = 0 ; (j < points.length - 1) && !found ; j++ ){
			if ( points[j].getX() > points[j+1].getX() )
				found = true;
		}
		if (found){
			Point[]sortedPoint = new Point[n];
			for( int i = 0 ; i < points.length ; i++ ){
				sortedPoint[(points[i]).getX()] = new Point(points[i].getX(),points[i].getY());
			}
			t = new BinarySearchTree(sortedPoint);
		}
		else t = new BinarySearchTree(points);
	
		
		
		/*int n = points.length;
		this.minHeap = new MinHeap((int) (n/2 + 5*(Math.log(n)/Math.log(2)))); //change to MaxSize of heap
		this.maxHeap = new MaxHeap((int) (n/2 + 5*(Math.log(n)/Math.log(2)))); //
		
		for(int i =0; i<points.length ; i++){
			if(points[i].getY()== initialYMedianPoint.getY()){ //if has the same Y value compare X value
				if(points[i].getX()>= initialYMedianPoint.getX())
					minHeap.insert(points[i]);
				else maxHeap.insert(points[i]);
			}
			if(points[i].getY()< initialYMedianPoint.getY()){ //if smaller than median
				maxHeap.insert(points[i]);
			}
			else minHeap.insert(points[i]);//if larger than median
		}
		
		if (!isSorted(points)){
			Point [] sortedPointArray = new Point [n]; 
			for (int j = 0; j < points.length ; j++){
				sortedPointArray[(points[j].getX())]= new Point (points [j].getX(), points [j].getY());	
			}
		this.t = new BinarySearchTree (sortedPointArray);	
		}
		this.t= new BinarySearchTree (points);*/	 
	}
		
	//checks if the array is sorted
	
	/*private boolean isSorted(Point[] points) {
		boolean ans= true;
		int i=0;
		while ((ans)&& (i<points.length-1)){
			if (points[i].getX() < points[i+1].getX())
				i++;
			else ans=false;
		}
		return ans;
	}*/

	@Override
	/**add point to data structure 
	 * @param Point point
	 */
	public void addPoint(Point point) {
		t.insert(point);
		if(point.getY()<minHeap.min().getY()) //if new point's Y value is smaller than median 
			maxHeap.insert(point);
		else minHeap.insert(point);	//if if new point's Y value is bigger or equal to median  
		if (maxHeap.heapsize()> minHeap.heapsize()){ //if #of elements in maxHeap is bigger than #of elements in minHeap
			Point temp = maxHeap.removeMax();
			minHeap.insert(temp);
		}
		if (minHeap.heapsize()-maxHeap.heapsize()>1){ //if the Subtraction between #of elements in minHeap and #of elements                                         
			Point temp = minHeap.removeMin();        //in maxHeap is larger than 1
			maxHeap.insert(temp);                
		}
		  
	}

	@Override
	/**this method returns all Points in a given range XLeft to XRight
	 * @param int XLeft, XRight
	 * @return array of all Points in range 
	 */

	public Point[] getPointsInRange(int XLeft, int XRight) {
		
		return t.pointsInRange(XLeft, XRight);
	}

	@Override
	/** this method returns the number of points in a given range XLeft to XRight 
	 * @param int XLeft, XRight
	 * @return number of Points  in range
	 */
	public int numOfPointsInRange(int XLeft, int XRight) {
		return t.countNodes(XLeft, XRight);
	}

	@Override
	/**this method returns the average height of Points' y value 
	 * @param int XLeft, XRight
	 * return double averageHeight
	 */
	public double averageHeightInRange(int XLeft, int XRight) {
		int div = numOfPointsInRange(XLeft,XRight);
		double sumOfY = t.sumInRange(XLeft, XRight);
		if (sumOfY!=0)
			return (sumOfY/div);
		else return 0;
	}

	@Override
	/**this method removes Point with median y value */
	public void removeMedianPoint() {
		Point removed = minHeap.removeMin();
		//if after removal #of elements in maxHeap is bigger than #of elements in minHeap
		if (maxHeap.heapsize()> minHeap.heapsize()){ 
			Point temp = maxHeap.removeMax();
			minHeap.insert(temp); //move an element from maxHeap to minHeap
		}
		t.delete(removed.getX());
		
	}

	@Override
	/**this method returns K closest to median Points
	 * @param int K
	 * @return array of Points 
	 */
	public Point[] getMedianPoints(int k) {
		int elementsFromMax=k/2;
		int elementsFromMin=(k+1)/2;
		Point [] medianPoints = new Point [k];
		
		MaxHeap helpMax= new MaxHeap(k);
		Point curr1 = maxHeap.max(); 
		int currIndex1;
		helpMax.insert(new Point(curr1.getX(),curr1.getY(),0+"")); //first insert the root of heap to the auxiliary heap
		for(int i=0;i<elementsFromMax;i++){
			Point temp = helpMax.removeMax(); //remove the max element from auxiliary heap
			medianPoints[i] = new Point (temp.getX(),temp.getY()); //insert it to array
			currIndex1= Integer.valueOf(temp.getName()); //index of point that was inserted to array in maxHeap's array
			if(maxHeap.hasLeftChild(currIndex1)){
			helpMax.insert(new Point(maxHeap.getArrayHeap()[maxHeap.leftchild(currIndex1)].getX(),
					maxHeap.getArrayHeap()[maxHeap.leftchild(currIndex1)].getY(),
					maxHeap.leftchild(currIndex1)+"")); //insert left son
			}
			if(maxHeap.hasRightChild(currIndex1)){
				helpMax.insert(new Point(maxHeap.getArrayHeap()[maxHeap.rightchild(currIndex1)].getX(),
					maxHeap.getArrayHeap()[maxHeap.rightchild(currIndex1)].getY(),
					maxHeap.rightchild(currIndex1)+"")); //insert right son
			}
		}
				
		MinHeap helpMin= new MinHeap(k);
		Point curr2= minHeap.min();
		int currIndex2;
		helpMin.insert(new Point(curr2.getX(),curr2.getY(),0+""));
		for(int j=0;j<elementsFromMin;j++){
			Point temp =helpMin.removeMin(); //remove the min element from auxiliary heap
			medianPoints[elementsFromMax+j]= new Point (temp.getX(), temp.getY()); //insert it to array
			currIndex2= Integer.valueOf((temp).getName()); //index of point that was inserted to array in minHeap's array
			if(minHeap.hasLeftChild(currIndex2)){
			helpMin.insert(new Point(minHeap.getArrayHeap()[minHeap.leftchild(currIndex2)].getX(),
					minHeap.getArrayHeap()[minHeap.leftchild(currIndex2)].getY(),
					minHeap.leftchild(currIndex2)+"")); //insert left son
			}
			if(minHeap.hasRightChild(currIndex2)){
			helpMin.insert(new Point(minHeap.getArrayHeap()[minHeap.rightchild(currIndex2)].getX(),
					minHeap.getArrayHeap()[minHeap.rightchild(currIndex2)].getY(),
					minHeap.rightchild(currIndex2)+"")); //insert right son
			}
		}
		
		return medianPoints;
	}

	@Override
	/**this method returns all Points 
	 * @return array of Points
	 */
	public Point[] getAllPoints() {
		Point [] allPoints = new Point [minHeap.heapsize()+ maxHeap.heapsize()];
		for(int i=0; i<minHeap.heapsize();i++){ //deep copy of points in heap from arrayHeap
			allPoints[i]= minHeap.getArrayHeap()[i];
		}
		for(int j=0; j<maxHeap.heapsize();j++){ //deep copy of points in heap from arrayHeap
			allPoints[minHeap.heapsize()+ j]= maxHeap.getArrayHeap()[j]; //starting from next index available
		}
		return allPoints;
	}
	
}

