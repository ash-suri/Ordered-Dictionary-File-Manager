# Ordered-Dictionary-File-Manager
A small-scale file managing system utilizing a binary search tree implementation of an ordered dictionary.

Originally created as an academic project for a Data Structures and Algorithms class. Only non-original files are the Reader.java files which allow the system to display content of various file extensions. InputFile.txt is a placeholder input file that the program will take in as it constructs the initial ordered dictionary. To use your own files, rewrite inputFile in the following format:

File Name1 in Dictionary

File Name1.extension

File Name2

File Name2.extension

The 2nd entry does not have to be the same name as the file name in the dictionary but it must directly proceed the name entry. Program is compatible with .txt, .html, .exe, .jpg, .gif, .wav, and .mid files. After storing, files will be classified into various types (sound, picture, program etc) based on their respective extensions. Files of type "definition" can also be stored, where the line following the file name can simply be a string stored within double quotes. Make sure files are placed outside the src and bin folders along with the inputFile itself.

The following commands can be used to interact with and edit the file system by running textInterface.java:

get w: Returns a file with name w
remove w k: Remove file with name w of kind k (where kind can be any one of: picture, sound, program, url, definition)
add w k c: Adds a file with name w and of type k and content c (follow inputFile formatting)
list prefix: Returns a list of files whose names contain prefix
first: Returns the first item stored in the dictionary
last: Returns the last item stored in the dictionary
end: Terminates the program
