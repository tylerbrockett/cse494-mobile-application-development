//
//  DetailViewController.swift
//  lab-4
//
//  Created by tkbrocke on 2/11/16.
//  Copyright © 2016 edu.asu.bscs.tkbrocke. All rights reserved.
//

import UIKit

class DetailViewController: UIViewController {
    
    var movie: MovieDescription = MovieDescription(json: "")
    

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
        
        if jsonTextField.text != movie.getJSON() {
            movie.setJSON(jsonTextField.text!)
        }
        else {
            movie.setTitle(titleTextField.text!)
            movie.setYear(yearTextField.text!)
            movie.setRated(ratedTextField.text!)
            movie.setReleased(releasedTextField.text!)
            movie.setRuntime(runtimeTextField.text!)
            movie.setGenre(genreTextField.text!)
            movie.setActors(actorsTextField.text!)
            movie.setPlot(plotTextField.text!)
        }
        
        NSNotificationCenter.defaultCenter().postNotificationName("loadMovieList", object: nil)
        
        if let navController = self.navigationController {
            navController.popViewControllerAnimated(true)
        }
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
        
        jsonTextField.text = movie.getJSON()
        titleTextField.text = movie.getTitle()
        yearTextField.text = movie.getYear()
        ratedTextField.text = movie.getRated()
        releasedTextField.text = movie.getReleased()
        runtimeTextField.text = movie.getRuntime()
        genreTextField.text = movie.getGenre()
        actorsTextField.text = movie.getActors()
        plotTextField.text = movie.getPlot()
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }


}

