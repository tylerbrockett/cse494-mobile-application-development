/*
 * @author Tyler Brockett			mailto:tylerbrockett@gmail.com
 * @project CSE 494 Lab 4 - iOS
 * @version February 13, 2016
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

import Foundation

class MovieLibrary {

    var library:[MovieDescription]
    
    init() {
        library = [MovieDescription]()
    }
    
    func add(movie:MovieDescription) {
        library.append(movie)
        sort()
    }
    
    func sort() {
        library.sortInPlace{(movie1, movie2) -> Bool in
            return movie1.title.lowercaseString < movie2.title.lowercaseString
        }
    }
    
    func remove(index:Int) {
        library.removeAtIndex(index)
    }
    
    func search(title:String) -> MovieDescription {
        for movie in library {
            if movie.getTitle() == title {
                return movie
            }
        }
        return MovieDescription(json:"")
    }
    
    func getLibrary() -> [MovieDescription] {
        return library
    }
    
    func get(index:Int) -> MovieDescription {
        return library[index]
    }
    
    func clearAll() {
        library = []
    }
    
    func getSize() -> Int {
        return library.count
    }
    
    func getSizeFormatted() -> String {
        if getSize() == 1 {
            return "1 Entry"
        }
        return "\(getSize()) Entries"
    }
    
}
