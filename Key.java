public class Key
{
	private String name;
	private String kind;

	public Key(String word, String type)
	{
		name = word.toLowerCase(); // Name is set to lowercase to prevent issues of case-sensitivity
		kind = type;
	}

	public String getName()
	{
		return name;
	}

	public String getKind()
	{
		return kind;
	}

	public int compareTo(Key k)
	{
		if (this.name.equals(k.getName()) && this.kind.equals(k.getKind())) //Case where keys are lexicographically the same
		{
			return 0;
		}

		else if (this.name.compareTo(k.getName()) < 0
				|| ((this.name.compareTo(k.getName()) == 0) && this.kind.compareTo(k.getKind()) < 0)) //Two cases where one key is smaller than the other
		{
			return -1;
		}

		else
		{
			return 1; //Otherwise the key must be greater than
		}
	}

}
