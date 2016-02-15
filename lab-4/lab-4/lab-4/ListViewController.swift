//
//  ListViewController.swift
//  lab-4
//
//  Created by tkbrocke on 2/11/16.
//  Copyright © 2016 edu.asu.bscs.tkbrocke. All rights reserved.
//

import UIKit

class ListViewController: UITableViewController {
    
    let library:MovieLibrary = MovieLibrary()
    
    @IBOutlet weak var movieTable: UITableView!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
        self.navigationItem.leftBarButtonItem = self.editButtonItem()
        let addButton = UIBarButtonItem(barButtonSystemItem: UIBarButtonSystemItem.Add, target: self, action: "insertMovie")
        self.navigationItem.rightBarButtonItem = addButton
        NSNotificationCenter.defaultCenter().addObserver(self, selector: "loadList:",name:"loadMovieList", object: nil)
    }
    
    func loadList(notification: NSNotification){
        //load data here
        NSLog("Loading")
        self.library.sort()
        self.tableView.reloadData()
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    // This is a data source method that will be called when table is loaded
    override func tableView(tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return library.getSize()
    }
    
    
    // This datasource method will create each cell of the table
    override func tableView(tableView: UITableView,cellForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCell {
        
        let cell = tableView.dequeueReusableCellWithIdentifier("tableCell", forIndexPath: indexPath)
        
        cell.textLabel?.text = library.get(indexPath.row).getTitle()
        cell.detailTextLabel?.textAlignment = .Right
        
        return cell;
    }
    
    func insertMovie(){
        performSegueWithIdentifier("newMovieSegue", sender: self)
    }
    
    // segue will be called as a row of the table is selected
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        
        if(segue.identifier == "newMovieSegue"){
            if let newMovieViewController: NewMovieViewController = segue.destinationViewController as? NewMovieViewController {
                newMovieViewController.library = library
            }
        }
        
        if(segue.identifier == "detailViewSegue"){
            let selectedIndex: NSIndexPath = self.movieTable.indexPathForCell(sender as! UITableViewCell)!
            if let detailviewController: DetailViewController = segue.destinationViewController as? DetailViewController {
                detailviewController.movie = library.get(selectedIndex.row)
            }
        }
    }
    
    // Override to support editing the table view
    override func tableView(tableView: UITableView, commitEditingStyle editingStyle: UITableViewCellEditingStyle, forRowAtIndexPath indexPath: NSIndexPath) {
        if editingStyle == .Delete {
            library.remove(indexPath.row)
            tableView.deleteRowsAtIndexPaths([indexPath], withRowAnimation: .Fade)
        }
    }
}

