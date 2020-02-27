/*
* MIT License

Copyright (c) 2020 Sanket Gupta

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
* */

package kdtree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class KDTree {

    private KDNode root;

    public void build (List < Point > p) {

        double maxX = -Double.MAX_VALUE;
        double minX = Double.MAX_VALUE;
        double maxY = -Double.MAX_VALUE;
        double minY = Double.MAX_VALUE;

        // computing the minX, maxX, minY, MaxY for the QueryBox.
        for (Point point : p) {
            if (point.x >= maxX)
                maxX = point.x;
            if (point.x <= minX)
                minX = point.x;
            if (point.y >= maxY)
                maxY = point.y;
            if (point.y <= minY)
                minY = point.y;
        }
        this.root = build ( p , 0 , new QueryBox ( new Point ( maxX , maxY ) ,
                new Point ( minX , minY ) ) );
    }

    private ArrayList < Point > reportSubtree (KDNode n) {
        /*
         * return all the points inside the subtree
         */
        ArrayList < Point > point = new ArrayList <> ( );
        n.reportSubtree ( point );
        return point;
    }

    public List < Point > search (QueryBox range) {
			/*
			 * search for all the points that are inside QueryBox.
			 * It is a public method and will call private method written below.
			 */
        return search ( root , range );
    }

    public int count (QueryBox range) {
			/*
			 * count all the points that are inside QueryBox.
			 * It is a public method and will call private method written below.
			 */
        return count ( root , range );
    }

    private int count (KDNode node , QueryBox range) {
        // count all the points that are inside QueryBox.
        // Complexity is O(sqrt(n))
        int pointCount = 0;
        if (node != null && node.isLeaf) {

            if (range.contains ( node.value )) {
                pointCount = pointCount + node.childCount;
            }
        } else if (node != null && node.bbox.inside ( range )) {
            pointCount += node.childCount;

        } else if (node != null && range.intersect ( node.bbox )) {
            pointCount += count ( node.left , range );
            pointCount += count ( node.right , range );
        }

        return pointCount;
    }


    private ArrayList < Point > search (KDNode node , QueryBox range) {
        // search for all the points that are inside query box
        ArrayList < Point > pointList = new ArrayList <> ( );
        if (node != null && node.isLeaf) {
            if (range.contains ( node.value )) {
                // if leaf node is inside range then add to the list
                pointList.add ( node.value );
            }
        } else if (node != null && node.bbox.inside ( range )) {
            // if QueryBox of node is completely inside the range then add all the points
            pointList.addAll ( this.reportSubtree ( node ) );
        } else if (node != null && range.intersect ( node.bbox )) {
            // if QueryBox of node intersect with the range
            pointList.addAll ( search ( node.left , range ) );
            pointList.addAll ( search ( node.right , range ) );
        }
        return pointList;
    }


    private KDNode build (List < Point > p , int depth , QueryBox bbox) {

        KDNode node = new KDNode ( );

        node.childCount = p.size ( );
        if (p.size ( ) <= 1) {
            node.isLeaf = true;
            node.value = p.get ( 0 );
            node.bbox = bbox;

        } else {
            node.isLeaf = false;
            if (depth % 2 == 0) {
                // if depth is even we do Vertical Line Split
                node.splitDimen = Style.VERTICAL.toString ( );
            } else {
                // if depth is odd we do Horizontal Line Split
                node.splitDimen = Style.HORIZONTAL.toString ( );
            }

            node.bbox = bbox;
            double median = findMedian ( p , depth );
            ArrayList < ArrayList < Point > > split = pointList ( p , node.splitDimen , median );
            depth++;
            node.position = median;
            if (!split.get(0).isEmpty())
                node.left = build ( split.get ( 0 ) , depth ,
                        new QueryBox ( new Point ( bbox.xmin , bbox.ymin ) , new Point ( median , bbox.ymax ) ) );
            if (!split.get(1).isEmpty())
                node.right = build ( split.get ( 1 ) , depth ,
                        new QueryBox ( new Point ( median , bbox.ymin ) , new Point ( bbox.xmax , bbox.ymax ) ) );
        }
        return node;
    }


    private double findMedian (List < Point > p , int depth) {
        // find the median for horizontal and vertical split

        // Quick select should be used here for O(n) complexity

        ArrayList < Double > set = new ArrayList <> ( );
        if (depth % 2 == 0) {
            for (Point point : p) {
                set.add ( point.x );
            }
        } else {
            for (Point point : p) {
                set.add ( point.y );
            }
        }
        Collections.sort ( set );
        Iterator < Double > k = set.iterator ( );
        double splitValue = Double.MIN_VALUE;
        int inc = 0;
        while (inc != set.size ( ) / 2 && k.hasNext ( )) {
            splitValue = k.next ( );
            inc++;
        }
        return splitValue;
    }


    private ArrayList < ArrayList < Point > > pointList (List < Point > points , String dimension , double value) {

        /*split the array into two arrays depending on the dimension. if dimension is H then splits the array by x-coordinates.
         * otherwise by y-coordinates*/
        ArrayList < ArrayList < Point > > pointList = new ArrayList <> ( );

        ArrayList < Point > p1 = new ArrayList <> ( );
        ArrayList < Point > p2 = new ArrayList <> ( );

        if (dimension.equalsIgnoreCase ( Style.VERTICAL.toString ( ) )) {
            for (Point t : points) {
                if (t.x <= value) {
                    p1.add ( t );
                } else {
                    p2.add ( t );
                }
            }
        } else if (dimension.equalsIgnoreCase ( Style.HORIZONTAL.toString ( ) )) {
            for (Point t : points) {
                if (t.y > value) {
                    p1.add ( t );
                } else {
                    p2.add ( t );
                }
            }
        }
        pointList.add ( p1 );
        pointList.add ( p2 );
        return pointList;
    }
}