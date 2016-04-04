/*
 * @author				Tyler Brockett	mailto:tylerbrockett@gmail.com
 * @course				ASU CSE 494
 * @project				Lab 6 - iOS
 * @version				March 16, 2016
 * @project-description	Use iOS client to get/post data from/to JSON-RPC Server
 * @class-name          TableViewController.swift
 * @class-description   Displays list of movies from the server.
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
import CoreData


class TableViewController: UITableViewController, NSFetchedResultsControllerDelegate {
    
    @IBOutlet weak var movieTable: UITableView!
    
    @IBAction func newMovie(sender: UIBarButtonItem) {
        NSLog("Before add segue")
        performSegueWithIdentifier("add", sender: sender)
    }
    
    var context: NSManagedObjectContext = (UIApplication.sharedApplication().delegate as! AppDelegate).managedObjectContext
    var dataController: NSFetchedResultsController = NSFetchedResultsController()
    
    func getFetchResultsController() -> NSFetchedResultsController {
        dataController = NSFetchedResultsController(fetchRequest: listFetchRequest(), managedObjectContext: context, sectionNameKeyPath: nil, cacheName: nil)
        return dataController
    }
    
    func listFetchRequest() -> NSFetchRequest {
        let fetchRequest = NSFetchRequest(entityName: "Movies")
        let sortDescripter = NSSortDescriptor(key: "title", ascending: true)
        fetchRequest.sortDescriptors = [sortDescripter]
        return fetchRequest
    }
    
    func controllerDidChangeContent(controller: NSFetchedResultsController) {
        self.movieTable.reloadData()
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        dataController = getFetchResultsController()
        dataController.delegate = self
        do {
            try dataController.performFetch()
        } catch _ {
        }

        
        self.navigationItem.leftBarButtonItem = self.editButtonItem()
        // let addButton = UIBarButtonItem(barButtonSystemItem: UIBarButtonSystemItem.Add, target: self, action: "newMovie")
        // self.navigationItem.rightBarButtonItem = addButton
        
        NSNotificationCenter.defaultCenter().addObserver(self, selector: "loadList:",name:"loadMovieList", object: nil)
    }
    
    func loadList(notification: NSNotification){
        self.movieTable.reloadData()
    }
    
    override func numberOfSectionsInTableView(tableView: UITableView) -> Int {
        let numberOfSections  = dataController.sections?.count
        return numberOfSections!
    }
    
    override func tableView(tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        let numberOfRowsInSection = dataController.sections?[section].numberOfObjects
        return numberOfRowsInSection!
    }
    
    override func tableView(tableView: UITableView, cellForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCell {
        let cell = movieTable.dequeueReusableCellWithIdentifier("tableCell", forIndexPath: indexPath) as! CustomTableViewCell
        let movie = dataController.objectAtIndexPath(indexPath) as! Movies
        

        cell.titleTF.text = movie.title!
        cell.yearTF.text = movie.year!
        
        let nat:NetworkAsyncTask = NetworkAsyncTask()
        nat.getPoster(movie.poster!, callback: { (res: NSData?, error:String?) -> Void in
            if error != nil {
                NSLog(error!)
            } else {
                if res != nil {
                    let image = UIImage(data: res!)
                    cell.posterIV.image = image
                }
            }
        })
        return cell
    }
    
    override func tableView(tableView: UITableView, commitEditingStyle editingStyle:   UITableViewCellEditingStyle, forRowAtIndexPath indexPath: NSIndexPath) {
        if (editingStyle == UITableViewCellEditingStyle.Delete) {
            let movie = dataController.objectAtIndexPath(indexPath) as! Movies
            context.deleteObject(movie)
            do {
                try context.save()
            } catch _ {
            }
        }
    }
    
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        if(segue.identifier == "details"){
            let selectedIndex: NSIndexPath = self.movieTable.indexPathForCell(sender as! CustomTableViewCell)!
            if let viewController: DetailViewController = segue.destinationViewController as? DetailViewController {
                let movie = dataController.objectAtIndexPath(selectedIndex) as! Movies
                viewController.movie = movie
            }
        }
        if(segue.identifier == "add"){
            // Do something here
            NSLog("add segue initiated")
        }
    }
}
