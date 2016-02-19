/*
 * @author                  Tyler Brockett	mailto:tylerbrockett@gmail.com
 * @course                  ASU CSE 494
 * @project                 Lab 4
 * @version                 Feb 18, 2016
 * @project description     Stores information about movies in a data structure and presents it to the user. Specifically, this project was to learn about TableView and Picker
 * @class description       Allows user to enter details about a new movie and save it to the list.
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

class NewMovieViewController: UIViewController, UITextFieldDelegate, UIPickerViewDelegate {
    
    var library:MovieLibrary = MovieLibrary()
    
    var defaultHeight:CGFloat = -1
    
    @IBOutlet weak var jsonTextField: UITextField!
    @IBOutlet weak var titleTextField: UITextField!
    @IBOutlet weak var yearTextField: UITextField!
    @IBOutlet weak var ratedTextField: UITextField!
    @IBOutlet weak var releasedTextField: UITextField!
    @IBOutlet weak var runtimeTextField: UITextField!
    @IBOutlet weak var genreTextField: UITextField!
    @IBOutlet weak var actorsTextField: UITextField!
    @IBOutlet weak var plotTextField: UITextField!
    @IBOutlet weak var genrePicker: UIPickerView!
    
    @IBAction func saveButton(sender: UIButton) {
        var movie: Movie
        if jsonTextField.text != "" {
            movie = Movie(json:"")
        }
        else{
            movie = Movie(title:titleTextField.text!, year:yearTextField.text!, rated:ratedTextField.text!, released:releasedTextField.text!,
                runtime:runtimeTextField.text!, genre:genreTextField.text!, actors:actorsTextField.text!, plot:plotTextField.text!)
        }
        library.add(movie)
        // Reload TableView from ListViewController
        NSNotificationCenter.defaultCenter().postNotificationName("loadMovieList", object: nil)
        // Finish this "activity"
        if let navController = self.navigationController {
            navController.popViewControllerAnimated(true)
        }
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
        self.defaultHeight = self.view.frame.origin.y
        NSNotificationCenter.defaultCenter().addObserver(self, selector: Selector("keyboardWillShow:"), name:UIKeyboardWillShowNotification, object: nil)
        NSNotificationCenter.defaultCenter().addObserver(self, selector: Selector("keyboardWillHide:"), name:UIKeyboardWillHideNotification, object: nil)
        
        self.genreTextField.delegate = self
        self.genrePicker.delegate = self
        genrePicker.removeFromSuperview()
        self.genreTextField.inputView = genrePicker
    }
    
    // PickerView Functions
    func pickerView(pickerView: UIPickerView, didSelectRow row: Int, inComponent component: Int) {
        self.genreTextField.text = MovieLibrary.genres[row]
        self.genreTextField.resignFirstResponder()
    }
    
    func numberOfComponentsInPickerView(pickerView: UIPickerView) -> Int {
        return 1
    }
    
    func pickerView(pickerView: UIPickerView, numberOfRowsInComponent component: Int) -> Int {
        return MovieLibrary.genres.count
    }
    
    func pickerView (pickerView: UIPickerView, titleForRow row: Int, forComponent component: Int) -> String? {
        return MovieLibrary.genres[row]
    }
    
    // Keyboard Show Functions
    func keyboardWillShow(sender: NSNotification) {
        self.view.frame.origin.y = self.defaultHeight - 100
    }
    func keyboardWillHide(sender: NSNotification) {
        self.view.frame.origin.y = self.defaultHeight
    }
    
    override func touchesBegan(touches: Set<UITouch>, withEvent event: UIEvent?) {
        super.touchesBegan(touches, withEvent:event)
        view.endEditing(true)
        self.jsonTextField.resignFirstResponder()
        self.titleTextField.resignFirstResponder()
        self.yearTextField.resignFirstResponder()
        self.ratedTextField.resignFirstResponder()
        self.releasedTextField.resignFirstResponder()
        self.runtimeTextField.resignFirstResponder()
        self.genreTextField.resignFirstResponder()
        self.actorsTextField.resignFirstResponder()
        self.plotTextField.resignFirstResponder()
    }
    
    func textFieldShouldReturn(textField: UITextField) -> Bool {
        self.jsonTextField.resignFirstResponder()
        self.titleTextField.resignFirstResponder()
        self.yearTextField.resignFirstResponder()
        self.ratedTextField.resignFirstResponder()
        self.releasedTextField.resignFirstResponder()
        self.runtimeTextField.resignFirstResponder()
        self.genreTextField.resignFirstResponder()
        self.actorsTextField.resignFirstResponder()
        self.plotTextField.resignFirstResponder()
        return true
    }
}

