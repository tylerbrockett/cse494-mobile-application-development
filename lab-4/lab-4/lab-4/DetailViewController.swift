//
//  DetailViewController.swift
//  lab-4
//
//  Created by tkbrocke on 2/11/16.
//  Copyright © 2016 edu.asu.bscs.tkbrocke. All rights reserved.
//

import UIKit

class DetailViewController: UIViewController {
    
    var selectedMovie: MovieDescription = MovieDescription(json: "")
    
    @IBOutlet weak var mTitle: UILabel!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
        
        mTitle.text = selectedMovie.getTitle()
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }


}

