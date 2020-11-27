public class Node
{
	private DataItem data;
	private Node left;
	private Node right;
	private Node parent;

	public Node(DataItem data, Node left, Node right, Node parent)
	{
		this.data = data;
		this.left = left;
		this.right = right;
		this.parent = parent;
	}

	public Node getLeft()
	{
		return this.left;
	}

	public Node getRight()
	{
		return this.right;
	}

	public Node getParent()
	{
		return this.parent;
	}

	public DataItem getData()
	{
		return this.data;
	}

	public void setLeft(Node left)
	{
		this.left = left;
	}

	public void setRight(Node right)
	{
		this.right = right;
	}

	public void setParent(Node parent)
	{
		this.parent = parent;
	}

	public void setData(DataItem data)
	{
		this.data = data;
	}

	public boolean isLeaf()
	{
		if (this.data == null) //Definition of a leaf
		{
			return true;
		} 
		
		else
		{
			return false;
		}
	}
}
