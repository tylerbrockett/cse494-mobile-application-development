/*
 * @author				Tyler Brockett	mailto:tylerbrockett@gmail.com
 * @course				ASU CSE 494
 * @project				Lab 6 - iOS
 * @version				March 16, 2016
 * @project-description	Use iOS client to get/post data from/to JSON-RPC Server
 * @class-name          DetailViewController.swift
 * @class-description   Shows details of a specific entry in the movie library. Allows the user to edit and save the changes.
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

class DetailViewController: UIViewController, UITextFieldDelegate {
    
    var movie: Movies?
    
    @IBOutlet weak var posterIV: UIImageView!
    @IBOutlet weak var titleTF: UITextField!
    @IBOutlet weak var yearTF: UITextField!
    @IBOutlet weak var ratedTF: UITextField!
    @IBOutlet weak var releasedTF: UITextField!
    @IBOutlet weak var runtimeTF: UITextField!
    @IBOutlet weak var genreTF: UITextField!
    @IBOutlet weak var actorsTF: UITextField!
    @IBOutlet weak var plotTF: UITextField!
    
    let context = (UIApplication.sharedApplication().delegate as! AppDelegate).managedObjectContext
    
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

    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
        if movie == nil {
            NSLog("Movie is nil")
        }
        else {
            let task = NetworkAsyncTask()
            task.getPoster(movie!.poster!, callback: { (res:NSData?, err:String?) -> Void in
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
            
            titleTF.text = movie!.title
            yearTF.text = movie!.year
            ratedTF.text = movie!.rated
            releasedTF.text = movie!.released
            runtimeTF.text = movie!.runtime
            genreTF.text = movie!.genre
            actorsTF.text = movie!.actors
            plotTF.text = movie!.plot
        }
    }

}
