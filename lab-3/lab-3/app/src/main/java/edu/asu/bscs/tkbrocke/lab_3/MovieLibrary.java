/*
 * @author Tyler Brockett   mailto:tylerbrockett@gmail.com
 * @project CSE 494 Lab 3 - Android
 * @version February 3, 2016
 *
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 Tyler Brockett
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package edu.asu.bscs.tkbrocke.lab_3;

import java.util.ArrayList;
import java.util.Collections;

public class MovieLibrary {

    ArrayList<MovieDescription> library;

    public MovieLibrary(){
        library = new ArrayList<MovieDescription>();
    }

    public void add(MovieDescription movie){
        library.add(movie);
        Collections.sort(library);
    }

    public void remove(int index){
        library.remove(index);
    }

    public ArrayList<MovieDescription> getLibrary(){
        return library;
    }

    public MovieDescription get(int index){
        return library.get(index);
    }

    public String get(int parentIndex, int childIndex){
        switch(childIndex){
            case 0:
                return library.get(parentIndex).getTitle();
            case 1:
                return library.get(parentIndex).getYear();
            case 2:
                return library.get(parentIndex).getRated();
            case 3:
                return library.get(parentIndex).getReleased();
            case 4:
                return library.get(parentIndex).getRuntime();
            case 5:
                return library.get(parentIndex).getGenre();
            case 6:
                return library.get(parentIndex).getActors();
            case 7:
                return library.get(parentIndex).getPlot();
            default:
                return "";
        }
    }

    public int getSize(){
        return library.size();
    }

    public String getSizeFormatted(){
        if (getSize() == 1){
            return "1 Entry";
        }
        else{
            return getSize() + " Entries";
        }
    }
}
