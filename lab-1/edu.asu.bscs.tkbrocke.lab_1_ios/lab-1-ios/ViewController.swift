//
//  ViewController.swift
//  lab-1-ios
//
//  Created by Tyler Brockett on 1/15/16.
//  Copyright © 2016 Tyler Brockett. All rights reserved.
//

import UIKit

class ViewController: UIViewController {
    
    @IBOutlet weak var inputBox: UITextView!
    @IBOutlet weak var titleLabel: UILabel!
    @IBOutlet weak var yearLabel: UILabel!
    @IBOutlet weak var ratedLabel: UILabel!
    @IBOutlet weak var releasedLabel: UILabel!
    @IBOutlet weak var runtimeLabel: UILabel!
    @IBOutlet weak var genreLabel: UILabel!
    @IBOutlet weak var actorsLabel: UILabel!
    @IBOutlet weak var plotLabel: UILabel!

    
    @IBAction func submit() {
        let jsonInput: String = inputBox.text
        let movie: MovieDescription = MovieDescription(json: jsonInput)
        
        titleLabel.text = movie.getTitle()
        yearLabel.text = movie.getYear()
        ratedLabel.text = movie.getRated()
        releasedLabel.text = movie.getReleased()
        runtimeLabel.text = movie.getRuntime()
        genreLabel.text = movie.getGenre()
        actorsLabel.text = movie.getActors()
        plotLabel.text = movie.getPlot()
    }
}
