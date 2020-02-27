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

public class QueryBox {
    /*
    * Axis parallel rectangular box.
    * This class defines the range of point set stored in internal nodes as well as used during range search.
    * */
    double xmin;
    double xmax;
    double ymin;
    double ymax;

    public QueryBox (Point p1, Point p2) {

        // computing xmin, xmax, ymin, ymax and assigning accordingly from p1 and p2
        if (p1.x < p2.x) {
            this.xmin = p1.x;
            this.xmax = p2.x;
        } else {
            this.xmin = p2.x;
            this.xmax = p1.x;
        }

        if (p1.y < p2.y) {
            this.ymin = p1.y;
            this.ymax = p2.y;
        } else {
            this.ymin = p2.y;
            this.ymax = p1.y;
        }
    }

    public boolean contains(Point p) {

        return p.x >= this.xmin && p.x <= this.xmax && p.y >= this.ymin && p.y <= this.ymax;
    }

    public boolean inside(QueryBox bbox) {

        boolean checkx = this.xmin >= bbox.xmin && this.xmin <= bbox.xmax && this.xmax >= bbox.xmin
                && this.xmax <= bbox.xmax;

        boolean checky = this.ymin >= bbox.ymin && this.ymin <= bbox.ymax && this.ymax >= bbox.ymin
                && this.ymax <= bbox.ymax;

        return checkx && checky;
    }

    public boolean intersect(QueryBox bbox) {
        // checking all the conditions like if query box are next to each other. or above and below to each other.
        boolean condition1 = this.xmin > bbox.xmax;
        boolean condition2 = this.xmax < bbox.xmin;
        boolean condition3 = this.ymax < bbox.ymin;
        boolean condition4 = this.ymin > bbox.ymax;


        return !(condition1 || condition2 || condition3 || condition4);

    }

    @Override
    public String toString() {
        return "QueryBox [xmin=" + xmin + ", xmax=" + xmax + ", ymin=" + ymin + ", ymax=" + ymax + "]";
    }

}