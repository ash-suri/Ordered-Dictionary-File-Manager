public class DataItem
{
	private Key theKey;
	private String content;

	public DataItem(Key k, String data)
	{
		theKey = k;
		content = data;
	}

	public Key getKey()
	{
		return theKey;
	}

	public String getContent()
	{
		return content;
	}
	
	public void setKey(Key k)
	{
		theKey = k;
	}
	
	public void setContent(String c)
	{
		content = c;
	}
}
