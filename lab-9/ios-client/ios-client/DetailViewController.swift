/*
 * @author              Tyler Brockett	mailto:tylerbrockett@gmail.com
 * @course              ASU CSE 494
 * @project             Lab 9 - iOS
 * @version             April 25, 2016
 * @project-description Get movie data from two sources and play movie if file exists.
 * @class-name          DetailViewController.swift
 * @class-description   Gets the movie details from Core Data and displays it to the user. Also, fetches the poster image from the web service using it's URL stored in Core Data.
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

import AVFoundation
import AVKit
import UIKit

class DetailViewController: UIViewController, UITextFieldDelegate {
    
    let context = (UIApplication.sharedApplication().delegate as! AppDelegate).managedObjectContext
    var movie: Movie?
    var filename:String = ""
    
    @IBOutlet weak var posterIV: UIImageView!
    @IBOutlet weak var titleTF: UITextField!
    @IBOutlet weak var yearTF: UITextField!
    @IBOutlet weak var ratedTF: UITextField!
    @IBOutlet weak var releasedTF: UITextField!
    @IBOutlet weak var runtimeTF: UITextField!
    @IBOutlet weak var genreTF: UITextField!
    @IBOutlet weak var actorsTF: UITextField!
    @IBOutlet weak var plotTF: UITextField!
    @IBOutlet weak var watchBtn: UIButton!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
        if movie == nil {
            NSLog("Movie is nil")
        }
        else {
            let task = NetworkAsyncTask()
            task.getPoster(movie!.poster!, callback: { (res:UIImage, err:String?) -> Void in
                self.posterIV.image = res
            })
            
            titleTF.text = movie!.title
            yearTF.text = movie!.year
            ratedTF.text = movie!.rated
            releasedTF.text = movie!.released
            runtimeTF.text = movie!.runtime
            genreTF.text = movie!.genre
            actorsTF.text = movie!.actors
            plotTF.text = movie!.plot
            if movie!.filename != "" {
                watchBtn.hidden = false
            } else {
                watchBtn.hidden = true
            }
        }
    }
    
    @IBAction func save(sender: UIBarButtonItem) {
        if movie != nil {
            movie!.title! = titleTF.text!
            movie!.year! = yearTF.text!
            movie!.rated! = ratedTF.text!
            movie!.released! = releasedTF.text!
            movie!.runtime! = runtimeTF.text!
            movie!.genre! = genreTF.text!
            movie!.actors! = actorsTF.text!
            movie!.plot! = plotTF.text!
            do {
                try self.context.save()
            } catch _ {
                NSLog("Error saving data.")
            }
            navigationController!.popViewControllerAnimated(true)
        }
    }

    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        if(segue.identifier == "watch"){
            if let viewController: WatchMovieViewController = segue.destinationViewController as? WatchMovieViewController {
                if let file:String = movie?.filename {
                    viewController.file = file
                }
            }
        }
    }
    
}
