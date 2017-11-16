Good day Mr Chew. (ill make this short)

A. How-to:
	1. You can use 7th option as Quick Insert (input followed as sample you provided)
	2. 6th option is the RBT (task requested)
	3. option 1 stays 1by1 input as in original

B. I only test with pre-oder
C. I ddnt do try-catch if user input string when program expect int, vice-versa since not required by the coursework
D. Amendment [My comment start/end with //** **//]:
	(RedBlackTreeTest.java)
	-only change in option
	LINE 47 = added Case 6, with parse() function
	LINE 51 = added Case 7, for quick-testing purposes

	(RedBlackNode.java)
	LINE 17/18/22/27 = added posi in the constructor
	LINE 88 = posi usage in new node creation

	LINE 180-198 = copied+altered function search() to create searchN(), that return NODE /used later in parse() function

	LINE 222 = initialising root posi
	LINE 227-233 = adding posi for child, using the i=i*2 and i=(i*2)+1

	LINE 258-318 = function parse() for testing the input



////////////DISCLAIMER: I DO NOT COPY ANYONE in my class, and i dont even have a glimpse on theirs.
