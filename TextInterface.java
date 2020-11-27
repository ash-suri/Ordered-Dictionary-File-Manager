import java.io.*;

public class TextInterface
{
	public static void main(String[] args)
	{
		OrderedDictionary dict = new OrderedDictionary(); // Creating new dictionary to hold input file values

		StringReader keyboard = new StringReader(); // Instantiating new readers for execution methods outside of loop
													// to avoid re-instancing
		SoundPlayer player = new SoundPlayer();
		PictureViewer viewer = new PictureViewer();
		RunCommand processor = new RunCommand();
		ShowHTML browser = new ShowHTML();

		try
		{
			BufferedReader in = new BufferedReader(new FileReader(args[0])); // Read input file
			String name = ""; // Strings to hold different parameters declared outside loop for inner access
			String content = "";
			String kind;

			try
			{
				name = in.readLine().toLowerCase(); // Loading first parameters prior to do while loop
				content = in.readLine().toLowerCase();

			} catch (IOException e1)
			{
				e1.printStackTrace();
			}

			do
			{
				name.toLowerCase(); // Setting to lower at beginning to avoid null pointer exception when next line
									// being loaded is null
				content.toLowerCase();

				if (content.endsWith(".wav") || content.endsWith(".mid")) // Checking for various instances of kind
					kind = "sound";

				else if (content.endsWith(".gif") || content.endsWith(".jpg"))
					kind = "picture";

				else if (content.endsWith(".exe"))
					kind = "program";

				else if (content.endsWith(".html"))
					kind = "url";

				else // If it doesn't belong to other instances, must be a text line
					kind = "definition";

				try
				{
					dict.put(new DataItem(new Key(name, kind), content)); // Place current loaded item into dict
				} catch (DictionaryException e)
				{
					e.printStackTrace();
				}

				try
				{
					name = in.readLine(); // Load next lines at end of loop
					content = in.readLine();
				} catch (IOException e)
				{
					e.printStackTrace();
				}
			} while (name != null); // Continue until end of file is reached

			try
			{
				in.close(); // Close input reader to prevent leak
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}

		catch (FileNotFoundException e1)
		{
			e1.printStackTrace();
		}

		boolean end = false; // Flag for loop exit condition

		do
		{
			String line = keyboard.read("Enter next command: ");

			line.toLowerCase(); // Avoid case-sensitive errors

			if (line.startsWith("end")) // Starts with method will find command which is always at the beginning of the
										// input line
			{
				end = true; // Set end flag to true which will cause loop to exit
			}

			else if (line.startsWith("get"))
			{
				String[] str = line.split(" "); // Split string into an array of strings using whitespace as breakpoints
												// for each element
				String name = str[1]; // Get command only inputs 1 word other than "get" itself, which is the name of
										// the data item
				DataItem d; // Data item will hold different instances of name and kind combinations
				boolean f = false; // Flag to indicate if item was found with given name and kind combination
				String pred = ""; // Initialize empty predecessor and successor strings for preceding and
									// following word printing as empty
				String succ = "";

				d = dict.get(new Key(name, "sound")); // Set d to one combination of name and kind to check if it
														// returns null or not

				if (d != null) // If null, this is not the correct combination, otherwise, it is
				{
					try
					{
						player.play(d.getContent());
						f = true;
					} catch (MultimediaException e)
					{
						e.printStackTrace();
					}
				}

				d = dict.get(new Key(name, "picture")); // Make new instance of d with a different value for kind

				if (d != null) // Using all if statements instead of else if in order to force program to check
								// all possible cases before proceeding
				{
					try
					{
						viewer.show(d.getContent());
						f = true;
					} catch (MultimediaException e)
					{
						e.printStackTrace();
					}
				}

				d = dict.get(new Key(name, "program"));

				if (d != null)
				{
					processor.run(d.getContent());
					f = true;
				}

				d = dict.get(new Key(name, "url"));

				if (d != null)
				{
					browser.show(d.getContent());
					f = true;
				}

				d = dict.get(new Key(name, "definition"));

				if (d != null)
				{
					System.out.println(d.getContent());
					f = true;
				}

				if (f == false) // In the case that none of the name and kind combinations are valid, the item
								// is not in the dictionary
				{
					try
					{
						dict.put(new DataItem(new Key(name, "definition"), "sample")); // Place non-existent item in
																						// dictionary with a
																						// hypothetical kind and empty
																						// content because this was not
																						// specified in the assignment
																						// outline
					} catch (DictionaryException e)
					{
						e.printStackTrace();
					}

					if (dict.predecessor((new Key(name, "definition"))) == null) // Cannot invoke getKey and thus,
																					// getName on null returning values.
																					// If hypothetical/non-existent
																					// string has no predecessor, set
																					// pred string to empty space in
																					// accordance with assignment specs
						pred = " ";
					else
						pred = dict.predecessor(new Key(name, "definition")).getKey().getName(); // If predecessor does
																									// exist, we set
																									// pred string to
																									// that name

					if (dict.successor((new Key(name, "definition"))) == null) // Same logic applied for successor value
																				// in the case that the non-existent key
																				// would has none
						succ = " ";
					else
						succ = dict.successor(new Key(name, "definition")).getKey().getName();

					try
					{
						dict.remove(new Key(name, "definition")); // Remove item after storing predecessor and successor
																	// values
					} catch (DictionaryException e)
					{
						e.printStackTrace();
					}

					System.out.println("The word " + name + " is not in the dictionary."); // Print results
					System.out.println("Preceding Word: " + pred);
					System.out.println("Following Word: " + succ);
				}
			}

			else if (line.startsWith("remove"))
			{
				String[] str = line.split(" ");
				try
				{
					dict.remove(new Key(str[1], str[2])); // Use given parameters for name and kind to specified item
				} catch (DictionaryException e) // Throws exception if item is not in dict
				{
					e.printStackTrace();
				}
			}

			else if (line.startsWith("add"))
			{
				String[] str = line.split(" ");
				if (!str[2].equals("definition") && !str[2].equals("sound") && !str[2].equals("program")
						&& !str[2].equals("url") && !str[2].equals("picture")) // Check if the user-input for kind is of a valid type to avoid clogging up dictionary with inaccessible files
				{
					System.out.println(
							"Invalid item type. Please ensure the added file is a definition, program, sound, picture, or url. "); // Reset loop after informing user of error
				} else
				{
					try
					{
						dict.put(new DataItem(new Key(str[1], str[2]), str[3])); // Adds with given parameters
					} catch (DictionaryException e) // Throws exception if item is already inserted
					{
						e.printStackTrace();
					}
				}
			}

			else if (line.startsWith("first"))
			{
				System.out.println(dict.smallest().getKey().getName() + "," + dict.smallest().getKey().getKind() + ","
						+ dict.smallest().getContent()); // Prints the attributes of the first object in dictionary
															// which is the one with the smallest key
			}

			else if (line.startsWith("last"))
			{
				System.out.println(dict.largest().getKey().getName() + "," + dict.largest().getKey().getKind() + ","
						+ dict.largest().getContent()); // Prints last object in dict which is the one with the largest
														// key
			}

			else if (line.startsWith("list"))
			{
				String[] str = line.split(" ");
				String prefix = str[1]; // Store prefix

				dict.BST.getList().clear(); // Clear array list during each new call of list to prevent duplicate
											// entries based on subsequent calls

				dict.BST.inOrder(dict.BST.getRoot()); // Call inOrder function to assess compile list of items in
														// dictionary
				String s = ""; // Holder string for list of matches

				for (String name : dict.BST.getList()) // Iterate through each entry in list of names stored in BST
				{
					if (name.startsWith(prefix))
						s += name + ", "; // If current entry starts with prefix, it's a match and is stored into holder
											// string for subsequent printing
				}

				if (s.isBlank()) // If list is blank after loop iterates entirely, there are no matches in the
									// tree with the given prefix
					System.out.println("No name attributes in the ordered dictionary start with prefix " + prefix);

				else // If matches are present and loop is done iterating
				{
					s = s.substring(0, (s.length() - 2)); // Remove dangling comma from last entry in compiled list of
															// matches by taking substring with ending index - 2 for
															// readability
					System.out.println(s);
				}
			}
		} while (end == false); // Loop exit condition
	}
}
