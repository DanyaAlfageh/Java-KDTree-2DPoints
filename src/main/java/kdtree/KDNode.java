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

import java.util.List;

public class KDNode {
    /* Internal Node or Leaf Node of KDTree */
    boolean isLeaf;
    KDNode left;
    KDNode right;
    double position;
    Point value;
    String splitDimen;
    QueryBox bbox;
    int childCount;

    public void reportSubtree(List<Point> p) {
        // recursive function to return the leaf node point
        if (!this.isLeaf) {

            if (this.left != null)
                this.left.reportSubtree ( p );
            if (this.right != null)
                this.right.reportSubtree ( p );
        } else {

            p.add ( value );
        }
    }
}
