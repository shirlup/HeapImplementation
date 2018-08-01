
public class BinarySearchTree {
	private BinarySearchNode root;
	/**Constructor*/
	public BinarySearchTree(Point[] arr){
		root = BuildingTree(arr,0,arr.length - 1,null);
		
	}
	/**build tree from given array of points by dividing to medians */
	public BinarySearchNode BuildingTree(Point[] arr, int start, int end,BinarySearchNode parent){
		
		if (start > end) 
			return null;
		
		int mid = start + (end - start) / 2; //the median of array
		BinarySearchNode node = new BinarySearchNode(arr[mid],parent); //create a node according to median index
		
		node.setLeft(BuildingTree(arr, start, mid-1,node));
		if(node.getLeft()!= null)
			node.setSumLeft(node.getLeft().getSumLeft() + node.getLeft().getSumRight() + node.getLeft().getPoint().getY());
		node.setRight(BuildingTree(arr, mid+1, end,node));
		if(node.getRight()!= null)
			node.setSumRight(node.getRight().getSumRight() + node.getRight().getSumLeft() + node.getRight().getPoint().getY());
		return node;
	}
	//checks if the tree is empty
	public boolean isEmpty(){
		return (root == null);
	}
	//inserts point to tree
	public void insert(Point toAdd){
		if(isEmpty()) //if the tree is empty point is now root
			root = new BinarySearchNode(toAdd,null);
		else root.insert(toAdd,root); //else insert to the right place in tree
	}
	/**@return root*/
	public BinarySearchNode getRoot(){
		return root;
	}
	/**@return number of elements in tree*/
	public int size(){
		if(root == null) return 0;
		else return root.size();
	}
	/**@return an array of points in a given range*/ 
	public Point[] pointsInRange(int xLeft, int xRight) {
		if (root==null)
			throw new RuntimeException("tree is empty");
		Point[] pointsInRange = new Point [countNodes(xLeft,xRight)]; //let pointsOnRange be in size needed
		BinarySearchNode start = search(xLeft); //find starting node
		if(pointsInRange.length>0){
		pointsInRange[0]= start.getPoint();
		}
		return start.inOrder(pointsInRange,start,1); //start inOrder scan from start node

	}
	/**@return number of nodes in a given range*/
	public int countNodes(int XLeft,int XRight){
		 return root.countNodes(XLeft, XRight, root);
		   }
		
	/**@return sum of Y's value of points in a given range*/
	public int sumInRange (int XLeft,int XRight){
		if (root != null)
			return root.sumInRange(XLeft,XRight,root);
		else return 0;
	}
	
	
	/**search a point with x value in tree*/
	private BinarySearchNode search(int x) {
		if (root==null)throw new RuntimeException("tree is empty");
		return root.searchLeft(root, x,root);
	}
	/**deletes a point with x value from tree*/
	public void delete(int x) {
		root.delete(x, root);		
	}
}

