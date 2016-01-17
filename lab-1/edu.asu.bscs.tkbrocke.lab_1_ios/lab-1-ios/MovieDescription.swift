//
//  MovieDescription.swift
//  lab-1-ios
//
//  Created by Tyler Brockett on 1/16/16.
//  Copyright © 2016 Tyler Brockett. All rights reserved.
//

import Foundation

class MovieDescription{
    var json: String
    var title: String
    var year: String
    var rated: String
    var released: String
    var runtime: String
    var genre: String
    var actors: String
    var plot: String
    
    init(json: String) {
        self.json = json;
        self.title = ""
        self.year = ""
        self.rated = ""
        self.released = ""
        self.runtime = ""
        self.genre = ""
        self.actors = ""
        self.plot = ""
        if let data: NSData = json.dataUsingEncoding(NSUTF8StringEncoding){
            do{
                let dict = try NSJSONSerialization.JSONObjectWithData(data, options: .MutableContainers) as? [String:AnyObject]
                self.title = (dict!["Title"] as? String)!
                self.year = (dict!["Year"] as? String)!
                self.rated = (dict!["Rated"] as? String)!
                self.released = (dict!["Released"] as? String)!
                self.runtime = (dict!["Runtime"] as? String)!
                self.genre = (dict!["Genre"] as? String)!
                self.actors = (dict!["Actors"] as? String)!
                self.plot = (dict!["Plot"] as? String)!
            } catch {
                self.title = "Error"
                self.year = "Error"
                self.rated = "Error"
                self.released = "Error"
                self.runtime = "Error"
                self.genre = "Error"
                self.actors = "Error"
                self.plot = "Error"
                print("Unable to convert to dictionary")
            }
        }
    }
    
    func getTitle() -> String {
        return self.title
    }
    
    func getYear() -> String {
        return self.year
    }
    
    func getRated() -> String {
        return self.rated
    }
    
    func getReleased() -> String {
        return self.released
    }
    
    func getRuntime() -> String {
        return self.runtime
    }
    
    func getGenre() -> String {
        return self.genre
    }
    
    func getActors() -> String {
        return self.actors
    }
    
    func getPlot() -> String {
        return self.plot
    }
    
    func toJsonString() -> String {
        var jsonStr = "";
        let dict = ["Title": self.title, "Year": self.year, "Rated": self.rated, "Released": self.released,
                    "Runtime": self.runtime, "Genre": self.genre, "Actors": self.actors, "Plot": self.plot]
        do {
            let jsonData = try NSJSONSerialization.dataWithJSONObject(dict, options: NSJSONWritingOptions.PrettyPrinted)
            jsonStr = NSString(data: jsonData, encoding: NSUTF8StringEncoding)! as String
        } catch let error as NSError {
            print(error)
        }
        return jsonStr
    }
}
