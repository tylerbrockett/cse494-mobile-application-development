/*
 * @author                  Tyler Brockett	mailto:tylerbrockett@gmail.com
 * @course                  ASU CSE 494
 * @project                 Lab 4
 * @version                 Feb 18, 2016
 * @project description     Stores information about movies in a data structure and presents it to the user. Specifically, this project was to learn about TableView and Picker
 * @class description       Shows all Movie entries to the user in the form of a TableView. Allows user to add or delete entries, and click on rows to view or edit.
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

class TableViewController: UITableViewController {
    
    let library:MovieLibrary = MovieLibrary()
    
    @IBOutlet weak var movieTable: UITableView!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
        self.navigationItem.leftBarButtonItem = self.editButtonItem()
        let addButton = UIBarButtonItem(barButtonSystemItem: UIBarButtonSystemItem.Add, target: self, action: "newMovie")
        self.navigationItem.rightBarButtonItem = addButton
        NSNotificationCenter.defaultCenter().addObserver(self, selector: "loadList:",name:"loadMovieList", object: nil)
    }
    
    func loadList(notification: NSNotification){
        self.library.sort()
        self.tableView.reloadData()
    }
    
    // TableView functions
    override func tableView(tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return library.getSize()
    }
    
    override func tableView(tableView: UITableView,cellForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCellWithIdentifier("tableCell", forIndexPath: indexPath)
        cell.textLabel?.text = library.get(indexPath.row).getTitle()
        cell.detailTextLabel?.textAlignment = .Right
        return cell;
    }
    
    override func tableView(tableView: UITableView, commitEditingStyle editingStyle: UITableViewCellEditingStyle, forRowAtIndexPath indexPath: NSIndexPath) {
        if editingStyle == .Delete {
        library.remove(indexPath.row)
        tableView.deleteRowsAtIndexPaths([indexPath], withRowAnimation: .Fade)
        }
    }

    // Segue functions
    func newMovie(){
        performSegueWithIdentifier("newMovieSegue", sender: self)
    }
    
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
}
