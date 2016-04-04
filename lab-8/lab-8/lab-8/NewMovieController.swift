//
//  ViewController.swift
//  lab-8
//
//  Created by Tyler Brockett on 4/3/16.
//  Copyright © 2016 Tyler Brockett. All rights reserved.
//

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
    
    @IBAction func searchBtn(sender: UIButton) {
        let task = NetworkAsyncTask()
        task.fetchMovie(searchTF.text!) { (res: String, err: String?) -> Void in
            if err != nil {
                NSLog(err!)
            } else {
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
                
                task.getPoster(movie.getPoster(), callback: { (res:NSData?, err:String?) -> Void in
                    if err != nil {
                        NSLog(err!)
                    } else {
                        if res != nil {
                            self.posterIV.image = UIImage(data: res!)
                        } else {
                            self.posterIV.image = nil
                        }
                    }
                })
            }
        }
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
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
}
