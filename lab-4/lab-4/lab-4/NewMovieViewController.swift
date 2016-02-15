//
//  NewMovieViewController.swift
//  lab-4
//
//  Created by tkbrocke on 2/11/16.
//  Copyright © 2016 edu.asu.bscs.tkbrocke. All rights reserved.
//

import UIKit

class NewMovieViewController: UIViewController {
    
    var library:MovieLibrary = MovieLibrary()
    
    
    @IBOutlet weak var jsonTextField: UITextField!
    @IBOutlet weak var titleTextField: UITextField!
    @IBOutlet weak var yearTextField: UITextField!
    @IBOutlet weak var ratedTextField: UITextField!
    @IBOutlet weak var releasedTextField: UITextField!
    @IBOutlet weak var runtimeTextField: UITextField!
    @IBOutlet weak var genreTextField: UITextField!
    @IBOutlet weak var actorsTextField: UITextField!
    @IBOutlet weak var plotTextField: UITextField!
    
    @IBAction func saveButton(sender: UIButton) {
        
        var movie: MovieDescription
        if jsonTextField.text != "" {
            movie = MovieDescription(json:"")
        }
        else{
            movie = MovieDescription(title:titleTextField.text!, year:yearTextField.text!, rated:ratedTextField.text!, released:releasedTextField.text!,
                runtime:runtimeTextField.text!, genre:genreTextField.text!, actors:actorsTextField.text!, plot:plotTextField.text!)
        }
        library.add(movie)

        NSNotificationCenter.defaultCenter().postNotificationName("loadMovieList", object: nil)
        
        if let navController = self.navigationController {
            navController.popViewControllerAnimated(true)
        }
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

