//
//  Movies.swift
//  lab-8
//
//  Created by Tyler Brockett on 4/3/16.
//  Copyright © 2016 Tyler Brockett. All rights reserved.
//

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
