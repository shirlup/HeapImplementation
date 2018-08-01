
public class BinarySearchNode {
	private Point point;
	private BinarySearchNode left;
	private BinarySearchNode right;
	private BinarySearchNode parent; 
	private int sumLeft;
	private int sumRight;
	
	//Constructor
	public BinarySearchNode(Point point, BinarySearchNode parent){
		if( point == null )
			throw new RuntimeException("No null object!");
		this.point = new Point(point.getX(),point.getY());
		left = null; 
		right = null;
		this.parent=parent;
		this.sumLeft = 0;
		this.sumRight = 0;
	}
	
	/**Inserting a point to it place according the X value*/
	public void insert(Point toAdd,BinarySearchNode node ){
		
		if(toAdd.getX() < point.getX()){
			this.setSumLeft(toAdd.getY());
			if( left == null ){
				left = new BinarySearchNode(toAdd,node);
			}
			else left.insert(toAdd,node.left);
		}
		else{
			this.setSumRight(toAdd.getY());
			if( right == null ){
				right = new BinarySearchNode(toAdd,node);
				
			}	
			else right.insert(toAdd, node.right);
		}
	}

	
	public void setLeft(BinarySearchNode node){
		
		left = node;
	}
	
	public void setRight(BinarySearchNode node){
		
		right = node;
	}
	
	public BinarySearchNode getLeft(){
		return left;
	}
	
	public BinarySearchNode getRight(){
		return right;
	}
	public Point getPoint(){
		return point;
	}
	public Point setPoint(Point p){
		return this.point = new Point(p.getX(),p.getY());
	}
	
	public BinarySearchNode getParent() {
		return parent;
	}
	public int getSumLeft() {
		return sumLeft;
	}


	public int getSumRight() {
		return sumRight;
	}
	public int setSumLeft(int y) {
		return sumLeft = y;
	}


	public int setSumRight(int y) {
		return sumRight = y;
	}

	public int size(){
		int res = 1;
		if (left != null ) res = res + left.size();
		if( right != null) res = res + right.size();
		return res;
	}
	
	public void print(){
		System.out.println(point.toString());
		
	}

	public BinarySearchNode searchLeft( BinarySearchNode node, int x  , BinarySearchNode max) {
		if(node.getPoint().getX() > x ){
			max = node;
			if(node.getLeft() != null )
				return searchLeft(node.getLeft(),x,max);
		}
		else if( node.getPoint().getX() < x ) {

			if(node.getRight() != null )
				return searchLeft(node.getRight(),x,max);
		}

		else{
			max = node;
		}
		return max;
	}
	
	public BinarySearchNode searchRight( BinarySearchNode node, int x  , BinarySearchNode min) {
		if(node.getPoint().getX() > x ){
			if(node.getLeft() != null )
				return searchRight(node.getLeft(),x,min);
		}
		else if( node.getPoint().getX() < x ) {
			min = node;
			if(node.getRight() != null )
				return searchRight(node.getRight(),x,min);
		}

		else{
			min = node;
		}
		return min;
	}
	
	/**@return number of points in a given range*/
	public int countNodes(int XLeft,int XRight,BinarySearchNode node){

	
		 if (node==null) return 0;
		    
	       if (node.getPoint().getX() == XRight && node.getPoint().getX() == XLeft)
	           return 1;
	    
	       if (node.getPoint().getX() <= XRight && node.getPoint().getX() >= XLeft)
	            return 1 + countNodes(XLeft, XRight,node.left) +
	                       countNodes(XLeft, XRight,node.right);
	    
	       else if (node.getPoint().getX() < XLeft)
	            return countNodes( XLeft, XRight,node.right);
	    
	   
	       else return countNodes( XLeft, XRight,node.left);
		
	}
		
	/** @returns an array of points from a starting node */
	public Point[] inOrder(Point[] points,BinarySearchNode node,int index){
		if(index < points.length  ){
			BinarySearchNode successor;
			if( node.getRight() != null ){
				successor = mostLeftChild(node.getRight());
				points[index] = (successor).getPoint();
			}
			else {
				successor = firstRightParent(node);
				points[index] = successor.getPoint();
			}
			return inOrder(points,successor,index + 1 );	
		}		
		return points;
	}
	/**finds the most left child of a given node*/
	private BinarySearchNode mostLeftChild (BinarySearchNode node){
		while( node.getLeft() != null )
			node = node.getLeft();
		return node;
	}
	/**find the first right parent of a given node*/
	private BinarySearchNode firstRightParent(BinarySearchNode node){
		BinarySearchNode parent = node.getParent();
		while(node != null && parent.right == node ){
			node = parent;
			parent = parent.getParent();
		}
		return parent;
	}
	/**@param XLeft
	 * @param XRight
	 * @param node
	 * @returns sum of points' Y's value in a given range
	 */
	public int sumInRange (int XLeft,int XRight, BinarySearchNode node){
		
		 if (node==null) return 0;
		    
	       if (node.getPoint().getX() == XRight && node.getPoint().getX() == XLeft)
	    	   return node.getPoint().getY() ; //if the point is the only one in range
	    
	       if (node.getPoint().getX() <= XRight && node.getPoint().getX() >= XLeft) //if point is in range
	            return node.getPoint().getY() + sumInRange(XLeft, XRight,node.left) + //go left&right
	            		sumInRange(XLeft, XRight,node.right);
	    
	       else if (node.getPoint().getX() < XLeft) //if not in range curr'x < min x in range
	            return sumInRange( XLeft, XRight,node.right); //go right
	    
	     //if not in range curr'x > max x in range
	       else return sumInRange( XLeft, XRight,node.left); //go left
	}
	
	public boolean delete(int x ,BinarySearchNode parent){
		if ( x < point.getX()){
			if ( left != null )
				return left.delete(x,this);
			else
				return false; // in case we didn't find our x
		}
		else if ( x > point.getX() ){
			if ( right != null )
				return right.delete(x,this); 
			else
				return false; // in case we didn't find our x
		}
		//means x = point.getX();
		else{
			if (( left != null ) && ( right != null )) { // two children
				point = right.findMin();
				right.delete(this.getPoint().getX(),this);
			}else if ( parent.getLeft() == this ){ // we are left son
				if(left != null)
					parent.left = left; 
				else
					parent.left = right;
			}
			else if ( parent.getRight() == this ){ // we are right son
				if(left != null)
					parent.right = left; 
				else
					parent.right = right;
			}
		}
		return true;
	}

	private Point findMin(){
		BinarySearchNode currNode = this;
		while (currNode.getLeft() != null){
			currNode = currNode.getLeft();
		}
		return currNode.getPoint();
	}
}

	

	


