/*
 * @author              Tyler Brockett	mailto:tylerbrockett@gmail.com
 * @course              ASU CSE 494
 * @project             Lab 8
 * @version             April 5, 2016
 * @project-description Store data from http://www.omdbapi.com/ into Core Data.
 * @class-name          NewMovieController.swift
 * @class-description   Allows user to search web service for movie titles, or create new movies from scratch. Ultimately saves movie data to Core Data.
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

import UIKit
import CoreData

class NewMovieViewController: UIViewController {

    @IBOutlet weak var searchTF: UITextField!
    @IBOutlet weak var posterIV: UIImageView!
    @IBOutlet weak var titleTF: UITextField!
    @IBOutlet weak var yearTF: UITextField!
    @IBOutlet weak var ratedTF: UITextField!
    @IBOutlet weak var releasedTF: UITextField!
    @IBOutlet weak var runtimeTF: UITextField!
    @IBOutlet weak var genreTF: UITextField!
    @IBOutlet weak var actorsTF: UITextField!
    @IBOutlet weak var plotTF: UITextField!
    @IBOutlet weak var posterTF: UITextField!
    
    let context = (UIApplication.sharedApplication().delegate as! AppDelegate).managedObjectContext
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
        posterIV.image = UIImage(named:"default_poster")
    }
    
    @IBAction func searchBtn(sender: UIButton) {
        let task = NetworkAsyncTask()
        task.fetchMovie(searchTF.text!) { (res: String, err: String?) -> Void in
            if err != nil {
                NSLog(err!)
            } else {
                if let data: NSData = res.dataUsingEncoding(NSUTF8StringEncoding){
                    do{
                        let dict = try NSJSONSerialization.JSONObjectWithData(data, options: .MutableContainers) as? [String:AnyObject]
                        if dict != nil && ((dict!["Response"] as! String) == "True")  {
                            self.movieFound(res)
                        } else {
                            self.noResult()
                        }
                    } catch _ {
                        self.noResult()
                    }
                } else {
                    self.noResult()
                }
            }
        }
    }
    
    func movieFound(res:String) {
        let movie:Movie = Movie(json: res)
        self.titleTF.text! = movie.getTitle()
        self.yearTF.text! = movie.getYear()
        self.ratedTF.text! = movie.getRated()
        self.releasedTF.text! = movie.getReleased()
        self.runtimeTF.text! = movie.getRuntime()
        self.genreTF.text! = movie.getGenre()
        self.actorsTF.text! = movie.getActors()
        self.plotTF.text! = movie.getPlot()
        self.posterTF.text! = movie.getPoster()
        
        let task = NetworkAsyncTask()
        task.getPoster(movie.getPoster(), callback: { (res:NSData?, err:String?) -> Void in
            if err != nil {
                self.posterIV.image = UIImage(named: "default_poster")
                NSLog(err!)
            } else {
                if res != nil {
                    self.posterIV.image = UIImage(data: res!)
                } else {
                    self.posterIV.image = UIImage(named: "default_poster")
                }
            }
        })
    }
    
    func noResult() {
        self.titleTF.text! = "Movie not found!"
        self.yearTF.text! = ""
        self.ratedTF.text! = ""
        self.releasedTF.text! = ""
        self.runtimeTF.text! = ""
        self.genreTF.text! = ""
        self.actorsTF.text! = ""
        self.plotTF.text! = ""
        self.posterTF.text! = ""
        self.posterIV.image = UIImage(named: "default_poster")
    }
    
    @IBAction func saveBtn(sender: UIBarButtonItem) {
        let entity = NSEntityDescription.entityForName("Movies", inManagedObjectContext: self.context)
        let movie = Movies(entity: entity!, insertIntoManagedObjectContext: self.context)
        movie.title = titleTF.text!
        movie.year = yearTF.text!
        movie.rated = ratedTF.text!
        movie.released = releasedTF.text!
        movie.runtime = runtimeTF.text!
        movie.genre = genreTF.text!
        movie.actors = actorsTF.text!
        movie.plot = plotTF.text!
        movie.poster = posterTF.text!
        do {
            try self.context.save()
        } catch _ {
            NSLog("Error saving data.")
        }
        navigationController!.popViewControllerAnimated(true)
    }
    
}
