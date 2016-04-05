/*
 * @author              Tyler Brockett	mailto:tylerbrockett@gmail.com
 * @course              ASU CSE 494
 * @project             Lab 8
 * @version             April 5, 2016
 * @project-description Store data from http://www.omdbapi.com/ into Core Data.
 * @class-name          Movies.swift
 * @class-description   Auto-generated file for Core Data, provides extra functionality to the Movies entity.
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
import CoreData

class Movies: NSManagedObject {

    // Insert code here to add functionality to your managed object subclass
    func parseData(dict:NSDictionary) {
        self.title = (dict.valueForKey("Title") as! String)
        self.year = (dict.valueForKey("Year") as! String)
        self.rated = (dict.valueForKey("Rated") as! String)
        self.released = (dict.valueForKey("Released") as! String)
        self.runtime = (dict.valueForKey("Runtime") as! String)
        self.genre = (dict.valueForKey("Genre") as! String)
        self.actors = (dict.valueForKey("Actors") as! String)
        self.plot = (dict.valueForKey("Plot") as! String)
        self.poster = (dict.valueForKey("Poster") as! String)
    }
    
    func setData(input:String) {
        if let data: NSData = input.dataUsingEncoding(NSUTF8StringEncoding){
            do{
                let dict = try NSJSONSerialization.JSONObjectWithData(data,options:.MutableContainers) as! NSDictionary
                parseData(dict)
            } catch {
                NSLog("unable to convert to dictionary")
            }
        }
    }

}
