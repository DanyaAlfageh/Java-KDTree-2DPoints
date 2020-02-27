package kdreetest;

import kdtree.KDTree;
import kdtree.Point;
import kdtree.QueryBox;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import static org.junit.Assert.assertEquals;

public class KDTreeTest {
    private static ArrayList < Point > outputPointList;
    private static KDTree tree;

    /*
     * The complexity achieved for counting the number of points inside bounding box is O(sqrt(n))
     * Assuming no point is repeated twice while constructing KDTree
     * */

    @BeforeClass
    public static void BuildKDTree ( ) {
        /*
        Input Points
        -5 -5
        1 -1
        2.5 1
        0 0
        3 2
        2 3
        -2 2
        -1 1
        -2 4
        2 4

        Bounding Box - KDtree will retrieve all the points inside this region
        -3 -3 3 3
        */

        /*
            Input Points
         */
        ArrayList < Point > inputPointList=new ArrayList <> ( );
        inputPointList.add ( new Point ( -5 , -5 ) );
        inputPointList.add ( new Point ( 1 , -1 ) );
        inputPointList.add ( new Point ( 2.5 , 1 ) );
        inputPointList.add ( new Point ( 0 , 0 ) );
        inputPointList.add ( new Point ( 3 , 2 ) );
        inputPointList.add ( new Point ( 2 , 3 ) );
        inputPointList.add ( new Point ( -2 , 2 ) );
        inputPointList.add ( new Point ( -1 , 1 ) );
        inputPointList.add ( new Point ( -2 , 4 ) );
        inputPointList.add ( new Point ( 2 , 4 ) );


          /*
        Building KDTree
         */
        tree=new KDTree ( );
        tree.build ( inputPointList );
    }

    @Test
    public void TestKDTreePrint_1 ( ) {

        /*
         * searching all the points and reporting the points complexity is O(sqrt(n) + k)
         * where k is number of points reported
         * */

        /* ================================================================================================
         * Defining the expected output
         * Query box to get all the points that exist within the region
         */
        QueryBox bb=new QueryBox ( new Point ( -3 , -3 ) , new Point ( 3 , 3 ) );

        /*
        Expected output of ArrayList for above Query Box
          1 -1
          0 0
          -1 1
          2.5 1
          -2 2
          3 2
          2 3
        */

        outputPointList=new ArrayList <> ( );
        outputPointList.add ( new Point ( 1 , -1 ) );
        outputPointList.add ( new Point ( 0 , 0 ) );
        outputPointList.add ( new Point ( -1 , 1 ) );
        outputPointList.add ( new Point ( 2.5 , 1 ) );
        outputPointList.add ( new Point ( -2 , 2 ) );
        outputPointList.add ( new Point ( 3 , 2 ) );
        outputPointList.add ( new Point ( 2 , 3 ) );




        /*
        Searching all the points in KDTree within the Bounding Box: bb
         */
        List < Point > final_points=tree.search ( bb );

        /* ================================================================================================
         * Computing the result
         * Searching all the points in KDTree within the Bounding Box: bb
         */
        TreeSet < Point > sorted_point=new TreeSet <> ( final_points );

        /*
         * converting to sorted_point to ArrayList for comparison
         * final_points list contains all the points which are inside the given bounding box
         * final_points are sorted lexicographically
         *
         * */
        final_points=new ArrayList <> ( sorted_point );

        assertEquals ( outputPointList.size ( ) , final_points.size ( ) );
        assertEquals ( outputPointList , final_points );

    }

    @Test
    public void TestKDTreePrint_2 ( ) {

        /*
         * searching all the points and reporting the points complexity is O(sqrt(n) + k)
         * where k is number of points reported
         * */

        /* ================================================================================================
         * Defining the expected output
         * Query box to get all the points that exist within the region
         */
        QueryBox bb=new QueryBox ( new Point ( 2 , -1 ) , new Point ( 2 , 1 ) );

        /*
        Expected output of ArrayList for above Query Box
          No Points are inside Query Box
        */

        /*Empty ArrayList as no points are inside the Query Box*/
        outputPointList=new ArrayList <> ( );
        /* ================================================================================================
         * Computing the result
         * Searching all the points in KDTree within the Bounding Box: bb
         */
        List < Point > final_points=tree.search ( bb );

        /*
        Sorting the output points lexicographically
        */
        TreeSet < Point > sorted_point=new TreeSet <> ( final_points );

        /*
         * converting to sorted_point to ArrayList for comparison
         * final_points list contains all the points which are inside the given bounding box
         * final_points are sorted lexicographically
         *
         * */
        final_points=new ArrayList <> ( sorted_point );

        assertEquals ( outputPointList.size ( ) , final_points.size ( ) );
        assertEquals ( outputPointList , final_points );

    }

    @Test
    public void TestKDTreeCount_1 ( ) {
        /*
         * tree.count() is with the complexity O(sqrt(n))
         * */
        QueryBox bb=new QueryBox ( new Point ( -3 , -3 ) , new Point ( 3 , 3 ) );
        int expectedCount=7;

        assertEquals ( expectedCount , tree.count ( bb ) );
    }

    @Test
    public void TestKDTreeCount_2 ( ) {
        /*
         * tree.count() is with the complexity O(sqrt(n))
         * */
        QueryBox bb=new QueryBox ( new Point ( 2 , -1 ) , new Point ( 2 , 1 ) );
        int expectedCount=0;

        assertEquals ( expectedCount , tree.count ( bb ) );
    }
}
