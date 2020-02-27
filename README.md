# Java-KDTree for 2D Points
KDTree Implementation for Range Query. Given set of 2D Points. Find all the points inside the axis parallel query rectangle.


#### KD-Tree for 2D Points
KD-Tree is a binary tree which is constructed by splitting the point set recursively by alternating between x-splits and y-splits.

Splitting the point set by x-coordinate: The point set is split by vertical line which has half the points left or on the line, and half on right.

Splitting the point set by y-coordinate: The point set is split by horizintal line which has half the points below or on the line and half above.

#### Complexity
Query Time is O(&radic;n) for reporting the number of points in the query rectangle.

Query Time is O(&radic;n+k) for reporting all the points in the query rectangle.

#### Usage
Refer [KDTreeTest.java](src/test/java/kdreetest/KDTreeTest.java) for Usage