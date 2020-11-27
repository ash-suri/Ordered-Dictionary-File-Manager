public class OrderedDictionary implements OrderedDictionaryADT 
{
	BinarySearchTree BST; //This is left as a public variable due to the assignment's specification that no other public methods can be added to OrderedDictionary, otherwise a getTree method would have been implemented instead
	
	public OrderedDictionary()
	{
		BST = new BinarySearchTree(); // Instantiating new BST in constructor so methods can always be accessed with dictionary
	}

	public DataItem get(Key k)
	{
		Node n = BST.get(BST.getRoot(), k); // Simply pass in values to our BST methods so structure is preserved
		
		return n.getData();
	}

	public void put(DataItem d) throws DictionaryException
	{
		boolean f = BST.put(BST.getRoot(), d); // Flag to check if data item has been inserted also calls method to minimize number of operations
		if(f==false) // In case where item is already in tree
			throw new DictionaryException("The Data Item has already been inserted into the tree. ");			
	}

	public void remove(Key k) throws DictionaryException
	{
		boolean f = BST.remove(BST.getRoot(), k); // Same flag logic as previous method
		
		if(f==false)
			throw new DictionaryException("The Data Item is not in the tree. ");
	}

	public DataItem successor(Key k)
	{
		Node n = BST.successor(BST.getRoot(), k); 
		
		if(n==null)
			return null;
		
		else
			return n.getData();
	}

	public DataItem predecessor(Key k)
	{
		Node n = BST.predecessor(BST.getRoot(), k);
		
		if(n==null)
			return null;
		
		else
			return n.getData();
	}

	public DataItem smallest()
	{
		Node n = BST.smallest(BST.getRoot());
		
		if(n==null)
			return null;
		
		else
			return n.getData();
	}

	public DataItem largest()
	{
		Node n = BST.largest(BST.getRoot());
		
		if(n==null)
			return null;
		
		else
			return n.getData();
	}	
}
