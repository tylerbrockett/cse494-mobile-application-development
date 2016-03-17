/*
 * @author                  Tyler Brockett	mailto:tylerbrockett@gmail.com
 * @course                  ASU CSE 494
 * @project                 Lab 4
 * @version                 Feb 18, 2016
 * @project description     Stores information about movies in a data structure and presents it to the user. Specifically, this project was to learn about TableView and Picker
 * @class description       Stores information about a specific movie.
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

class Movie{
    
    var json: String = ""
    var title: String = ""
    var id:String = ""
    var year: String = ""
    var rated: String = ""
    var released: String = ""
    var runtime: String = ""
    var genre: String = ""
    var actors: String = ""
    var plot: String = ""
    
    init(json: String) {
        self.json = json
        parseJSON(json)
    }
    
    init(title:String, id:String, year:String, rated:String, released:String, runtime:String, genre:String, actors:String, plot:String){
        self.title = title
        self.id = id
        self.year = year
        self.rated = rated
        self.released = released
        self.runtime = runtime
        self.genre = genre
        self.actors = actors
        self.plot = plot
        generateJSON()
    }
    
    init(dict:NSDictionary) {
        self.title = (dict.valueForKey("Title") as! String)
        self.id = (dict.valueForKey("ID") as! String)
        self.year = (dict.valueForKey("Year") as! String)
        self.rated = (dict.valueForKey("Rated") as! String)
        self.released = (dict.valueForKey("Released") as! String)
        self.runtime = (dict.valueForKey("Runtime") as! String)
        self.genre = (dict.valueForKey("Genre") as! String)
        self.actors = (dict.valueForKey("Actors") as! String)
        self.plot = (dict.valueForKey("Plot") as! String)
        generateJSON()
    }
    
    func generateJSON() -> String {
        self.json =
            "{" +
                "\"Title\":\"" + self.title + "\"," +
                "\"ID\":\"" + self.id + "\"," +
                "\"Year\":\"" + self.year + "\"," +
                "\"Rated\":\"" + self.rated + "\"," +
                "\"Released\":\"" + self.released + "\"," +
                "\"Runtime\":\"" + self.runtime + "\"," +
                "\"Genre\":\"" + self.genre + "\"," +
                "\"Actors\":\"" + self.actors + "\"," +
                "\"Plot\":\"" + self.plot + "\"" +
            "}";
        return self.json
    }
    
    func parseJSON(json:String) {
        if let data: NSData = json.dataUsingEncoding(NSUTF8StringEncoding){
            do{
                let dict = try NSJSONSerialization.JSONObjectWithData(data, options: .MutableContainers) as? [String:AnyObject]
                self.title = (dict!["Title"] as? String)!
                self.id = (dict!["ID"] as? String)!
                self.year = (dict!["Year"] as? String)!
                self.rated = (dict!["Rated"] as? String)!
                self.released = (dict!["Released"] as? String)!
                self.runtime = (dict!["Runtime"] as? String)!
                self.genre = (dict!["Genre"] as? String)!
                self.actors = (dict!["Actors"] as? String)!
                self.plot = (dict!["Plot"] as? String)!
            } catch {
                self.title = "Error"
                self.id = "Error"
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
        generateJSON()
    }
    
    func getJSON() -> String {
        return self.json
    }
    
    func getTitle() -> String {
        return self.title
    }
    
    func getID() -> String {
        return self.id
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
    
    func setJSON(json:String) {
        self.json = json
        parseJSON(json)
    }
    
    func setTitle(title:String) {
        self.title = title
    }
    
    func setID(id:String) {
        self.id = id
    }
    
    func setYear(year:String) {
        self.year = year
    }
    
    func setRated(rated:String) {
        self.rated = rated
    }
    
    func setReleased(released:String) {
        self.released = released
    }
    
    func setRuntime(runtime:String){
        self.runtime = runtime
    }
    
    func setGenre(genre:String) {
        self.genre = genre
    }
    
    func setActors(actors:String) {
        self.actors = actors
    }
    
    func setPlot(plot:String) {
        self.plot = plot
    }
    
    func compareTo(this:Movie, that:Movie) -> Int {
        if this.getTitle() > that.getTitle() {
            return 1
        }
        else if this.getTitle() < that.getTitle() {
            return -1
        }
        return 0
    }
}
