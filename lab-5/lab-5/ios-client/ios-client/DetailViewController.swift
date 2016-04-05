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

class DetailViewController: UIViewController, UITextFieldDelegate, UIPickerViewDelegate {
    let genres:[String] = ["Action", "Adventure", "Animation", "Biography", "Comedy", "Crime",
        "Documentary", "Drama", "Family", "Fantasy", "History", "Horror", "Music", "Musical",
        "Mystery", "Romance", "Sci-Fi", "Sports", "Thriller", "War", "Western"]
    
    var movie: Movie? = nil
        
    var defaultHeight:CGFloat = -1
    
    
    @IBOutlet weak var jsonTF: UITextField!
    @IBOutlet weak var titleTF: UITextField!
    @IBOutlet weak var yearTF: UITextField!
    @IBOutlet weak var ratedTF: UITextField!
    @IBOutlet weak var releasedTF: UITextField!
    @IBOutlet weak var runtimeTF: UITextField!
    @IBOutlet weak var genreTF: UITextField!
    @IBOutlet weak var actorsTF: UITextField!
    @IBOutlet weak var plotTF: UITextField!
    @IBOutlet weak var genrePicker: UIPickerView!
    
    @IBAction func save(sender: UIBarButtonItem) {
        if movie == nil {
            if jsonTF.text!.isEmpty {
                movie = Movie(title:titleTF.text!, id:"", year:yearTF.text!, rated:ratedTF.text!, released:releasedTF.text!,
                    runtime:runtimeTF.text!, genre:genreTF.text!, actors:actorsTF.text!, plot:plotTF.text!)
            }
            else {
                movie = Movie(json:jsonTF.text!)
            }
            save()
        }
        else {
            if jsonTF.text! == movie!.getJSON() {
                movie!.setTitle(titleTF.text!)
                // movie!.setID(movie!.getID())
                movie!.setYear(yearTF.text!)
                movie!.setRated(ratedTF.text!)
                movie!.setReleased(releasedTF.text!)
                movie!.setRuntime(runtimeTF.text!)
                movie!.setGenre(genreTF.text!)
                movie!.setActors(actorsTF.text!)
                movie!.setPlot(plotTF.text!)
                movie!.generateJSON()
            }
            else {
                movie!.setJSON(jsonTF.text!)
            }
            edit()
        }
        // Finish this "activity"
        if let navController = self.navigationController {
            navController.popViewControllerAnimated(true)
        }
    }
    
    func save() {
        let aConnect:NetworkAsyncTask = NetworkAsyncTask(urlString: Constants.ipAddress)
        let _:Bool = aConnect.add(movie!, callback: { (res: String, err: String?) -> Void in
            if err != nil {
                NSLog(err!)
            }else{
                NSLog(res)
                if let data: NSData = res.dataUsingEncoding(NSUTF8StringEncoding){
                    do{
                        let response = try NSJSONSerialization.JSONObjectWithData(data,options:.MutableContainers) as! NSDictionary
                        let result:Bool = response.valueForKey("result") as! Bool
                        // Reload TableView from ListViewController
                        NSNotificationCenter.defaultCenter().postNotificationName("loadMovieList", object: nil)
                    } catch {
                        NSLog("unable to convert to dictionary")
                    }
                }
            }
        })
    }
    
    func edit() {
        let aConnect:NetworkAsyncTask = NetworkAsyncTask(urlString: Constants.ipAddress)
        let _:Bool = aConnect.edit(movie!, callback: { (res: String, err: String?) -> Void in
            if err != nil {
                NSLog(err!)
            }else{
                NSLog(res)
                if let data: NSData = res.dataUsingEncoding(NSUTF8StringEncoding){
                    do{
                        let response = try NSJSONSerialization.JSONObjectWithData(data,options:.MutableContainers) as! NSDictionary
                        let result:Bool = response.valueForKey("result") as! Bool
                        // Reload TableView from ListViewController
                        NSNotificationCenter.defaultCenter().postNotificationName("loadMovieList", object: nil)
                    } catch {
                        NSLog("unable to convert to dictionary")
                    }
                }
            }
        })
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
        self.defaultHeight = self.view.frame.origin.y
        NSNotificationCenter.defaultCenter().addObserver(self, selector: Selector("keyboardWillShow:"), name:UIKeyboardWillShowNotification, object: nil)
        NSNotificationCenter.defaultCenter().addObserver(self, selector: Selector("keyboardWillHide:"), name:UIKeyboardWillHideNotification, object: nil)
        
        self.genreTF.delegate = self
        self.genrePicker.delegate = self
        genrePicker.removeFromSuperview()
        self.genreTF.inputView = genrePicker
        
        if movie == nil {
            
        }
        else {
            self.genrePicker.selectRow(genres.indexOf(movie!.getGenre())!, inComponent: 0, animated: false)
            jsonTF.text = movie!.getJSON()
            titleTF.text = movie!.getTitle()
            yearTF.text = movie!.getYear()
            ratedTF.text = movie!.getRated()
            releasedTF.text = movie!.getReleased()
            runtimeTF.text = movie!.getRuntime()
            genreTF.text = movie!.getGenre()
            actorsTF.text = movie!.getActors()
            plotTF.text = movie!.getPlot()
        }
    }
    
    // PickerView functions
    func pickerView(pickerView: UIPickerView, didSelectRow row: Int, inComponent component: Int) {
        self.genreTF.text = genres[row]
        self.genreTF.resignFirstResponder()
    }
    
    func numberOfComponentsInPickerView(pickerView: UIPickerView) -> Int {
        return 1
    }
    
    func pickerView(pickerView: UIPickerView, numberOfRowsInComponent component: Int) -> Int {
        return genres.count
    }
    
    func pickerView (pickerView: UIPickerView, titleForRow row: Int, forComponent component: Int) -> String? {
        return genres[row]
    }

    // Keyboard Show functions
    func keyboardWillShow(sender: NSNotification) {
        self.view.frame.origin.y = self.defaultHeight - 100
    }
    func keyboardWillHide(sender: NSNotification) {
        self.view.frame.origin.y = self.defaultHeight
    }
    
    override func touchesBegan(touches: Set<UITouch>, withEvent event: UIEvent?) {
        super.touchesBegan(touches, withEvent:event)
        view.endEditing(true)
        self.jsonTF.resignFirstResponder()
        self.titleTF.resignFirstResponder()
        self.yearTF.resignFirstResponder()
        self.ratedTF.resignFirstResponder()
        self.releasedTF.resignFirstResponder()
        self.runtimeTF.resignFirstResponder()
        self.genreTF.resignFirstResponder()
        self.actorsTF.resignFirstResponder()
        self.plotTF.resignFirstResponder()
    }
    
    func textFieldShouldReturn(textField: UITextField) -> Bool {
        self.jsonTF.resignFirstResponder()
        self.titleTF.resignFirstResponder()
        self.yearTF.resignFirstResponder()
        self.ratedTF.resignFirstResponder()
        self.releasedTF.resignFirstResponder()
        self.runtimeTF.resignFirstResponder()
        self.genreTF.resignFirstResponder()
        self.actorsTF.resignFirstResponder()
        self.plotTF.resignFirstResponder()
        return true
    }
}

