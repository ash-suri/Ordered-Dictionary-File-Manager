import java.util.*;

public class BinarySearchTree
{
	private Node root;
	private ArrayList<String> list; // List that will hold a list of node names for text interface list method, declared outside to be accessible to BST and, by extension, dict

	public BinarySearchTree()
	{
		root = null; // set root to null in constructor so that initial call for put can verify whether the tree is empty or not
		list = new ArrayList<String>(); // Instantiating list in constructor
	}

	public Node getRoot() // Method will be used for most methods that require an r input node
	{
		return root;
	}

	public Node get(Node r, Key k)
	{

		if (r.isLeaf())
		{
			return r;
		}

		else
		{
			if (k.compareTo(r.getData().getKey()) == 0) //Use compare function from key class to check whether node has been found
			{
				return r;
			}

			else if (k.compareTo(r.getData().getKey()) == -1) 
			{
				return get(r.getLeft(), k); //Key is less than current node position so we go left
			}

			else
			{
				return get(r.getRight(), k); //Otherwise, it must be greater than so we go right
			}

		}

	}

	public boolean put(Node r, DataItem d)
	{
		if (root == null) // Tree is not yet populated, thus this is the first call of put and the given dataItem will comprise the root node
		{
			root = new Node(d, null, null, null); //Root node has no parent
			Node left = new Node(null, null, null, root); //Instantiate left and right child nodes
			Node right = new Node(null, null, null, root);

			root.setLeft(left); // Two leaf nodes set as child of root
			root.setRight(right);

			return true;
		}

		Node p = get(r, d.getKey()); //Pointer node

		if (!p.isLeaf()) 
		{
			return false; //Data exists in current node, thus its already occupied
		}

		else
		{
			p.setData(d); // Set data

			Node left = new Node(null, null, null, p); //Make new leaf children with parent p
			Node right = new Node(null, null, null, p);

			p.setLeft(left); //Set as children of p
			p.setRight(right);

			return true;
		}

	}

	public boolean remove(Node r, Key k)
	{
		Node p = get(r, k);

		if (p.isLeaf())
			return false;

		else
		{

			if (p.getLeft().isLeaf()) // Case where node has left leaf child
			{

				Node c = p.getRight(); // Set new node to the right child of p
				Node pp = p.getParent(); //Set new node to the parent of p

				if (p == root)
				{
					root = c; // C must become new root if removed node is the current root
				}

				else
				{
					p.setData(null); // Make p a null node, i.e. a leaf, 
					pp.setRight(c); // and change structure so it's not connected to anything and thus, effectively doesn't exist
				}

			}

			else if (p.getRight().isLeaf()) // Opposite case where p has a right leaf child instead
			{

				Node c = p.getLeft(); // Set to other child of p 
				Node pp = p.getParent();

				if (p == root)
				{

					root = c;
				}

				else
				{
					p.setData(null);
					pp.setLeft(c);
				}
			}

			else // Case where node is internal and has no leaf children
			{
				Node s = smallest(p.getRight()); // Find smallest node on the right side (so that structure of BST is maintained. Smallest right side node will be greater than all left nodes and parent)
				p.getData().setContent(s.getData().getContent()); //Copy over data to new node
				p.getData().setKey(s.getData().getKey());
				
				remove(s, s.getData().getKey()); //Recursive call on smallest node on right side to complete structural shift from the bottom of the right subtree to the current position of p
			}

			return true;
		}

	}

	public Node successor(Node r, Key k)
	{
		if (r.isLeaf())
			return null;

		else
		{
			Node p = get(r, k); //Find node position

			if ((!p.isLeaf()) && (!p.getRight().isLeaf())) // Case where right child is not a leaf
			{
				return smallest(p.getRight()); // Direct successor will be smallest node on right subtree
			}

			else
			{
				Node pp = p.getParent(); 
				while (p != r && pp.getRight() == p) // In case where p is not root, and right child of p prime is p
				{
					p = pp; // Set child to parent
					pp = p.getParent(); // Effectively traversing upwards by setting p prime to new parent until successor is found
				}

				if (p == r) // Root has no successor
					return null;

				else
					return pp; 
			}
		}
	}

	public Node predecessor(Node r, Key k)
	{
		if (r.isLeaf())
			return null;

		else
		{
			Node p = get(r, k);

			if ((!p.isLeaf()) && (!p.getLeft().isLeaf())) // Opposite case of successor
			{
				return largest(p.getLeft()); // Opposite of successor, predecessor will be largest value on the left subtree
			}

			else
			{
				Node pp = p.getParent();
				while (p != r && pp.getLeft() == p)
				{
					p = pp;
					pp = p.getParent(); //Similar to successor but traversing downwards instead
				}

				if (p == r)
					return null;

				else
					return pp;
			}
		}
	}

	public Node smallest(Node r)
	{
		if (r.isLeaf())
			return null;

		else
		{
			Node p = r;

			while (!p.getLeft().isLeaf()) // As long as p's left child is not a leaf, the smallest node will be the leftmost node on the left main branch
			{
				p = p.getLeft();
			}
			return p;
		}
	}

	public Node largest(Node r)
	{
		if (r.isLeaf())
			return null;

		else
		{
			Node p = r;

			while (!p.getRight().isLeaf()) // Vice versa from smallest function, largest node will be the bottom right node on the right subtree, keep traversing right till we're one level from the leaf node
			{
				p = p.getRight();
			}

			return p;
		}
	}
	
	public void inOrder(Node r) //inOrder traversal of tree to generate a list of names in the tree for the list method in textInterface
	{
		if (r.getData() != null)
		{
			inOrder(r.getLeft());
			list.add(r.getData().getKey().getName()); //.add method used to add new entry of name to list for later searching
			inOrder(r.getRight());
		}
	}
	
	public ArrayList<String> getList()
	{
		return this.list;
	}
}
