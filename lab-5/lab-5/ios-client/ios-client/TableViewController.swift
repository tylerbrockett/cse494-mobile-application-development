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


struct Library {
    static var movies:NSArray? = nil
}

class TableViewController: UITableViewController {
    
    @IBOutlet weak var movieTable: UITableView!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
        self.navigationItem.leftBarButtonItem = self.editButtonItem()
        let addButton = UIBarButtonItem(barButtonSystemItem: UIBarButtonSystemItem.Add, target: self, action: "newMovie")
        self.navigationItem.rightBarButtonItem = addButton
        NSNotificationCenter.defaultCenter().addObserver(self, selector: "loadList:",name:"loadMovieList", object: nil)
        loadList()
    }
    
    func loadList(notification: NSNotification){
        self.loadList()
    }
    
    func loadList() {
        Library.movies = nil
        let aConnect:NetworkAsyncTask = NetworkAsyncTask(urlString: Constants.ipAddress)
        let _:Bool = aConnect.getAll( { (res: String, err: String?) -> Void in
            if err != nil {
                NSLog(err!)
            }else{
                NSLog(res)
                if let data: NSData = res.dataUsingEncoding(NSUTF8StringEncoding){
                    do{
                        let response = try NSJSONSerialization.JSONObjectWithData(data,options:.MutableContainers) as! NSDictionary
                        let result:NSDictionary = response.valueForKey("result") as! NSDictionary
                        Library.movies = (result.valueForKey("Movies") as! NSArray)
                        self.tableView.reloadData()
                    } catch {
                        NSLog("unable to convert to dictionary")
                    }
                }
            }
        })
    }
    
    // TableView functions
    override func tableView(tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        if Library.movies != nil {
            return Library.movies!.count
        }
        return 0
    }
    
    override func tableView(tableView: UITableView,cellForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCellWithIdentifier("tableCell", forIndexPath: indexPath)
        cell.textLabel?.text = ((Library.movies!.objectAtIndex(indexPath.row) as! NSDictionary).valueForKey("Title") as! String)
        // cell.detailTextLabel?.textAlignment = .Right
        return cell;
    }
    
    override func tableView(tableView: UITableView, commitEditingStyle editingStyle: UITableViewCellEditingStyle, forRowAtIndexPath indexPath: NSIndexPath) {
        if editingStyle == .Delete {
            let movie:Movie = Movie(dict: Library.movies![indexPath.row] as! NSDictionary)
            let aConnect:NetworkAsyncTask = NetworkAsyncTask(urlString: Constants.ipAddress)
            let _ :Bool = aConnect.remove(movie.getID(), callback: {(res:String, err:String?) -> Void in
                if err != nil {
                    NSLog(err!)
                } else {
                    NSLog(res)
                    self.loadList()
                }
            })
        }
    }
    
    // Segue functions
    func newMovie(){
        performSegueWithIdentifier("add", sender: self)
    }
    
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        if(segue.identifier == "add"){
            if let viewController: DetailViewController = segue.destinationViewController as? DetailViewController {
                viewController.movie = nil
            }
        }
        if(segue.identifier == "details"){
            let selectedIndex: NSIndexPath = self.movieTable.indexPathForCell(sender as! UITableViewCell)!
            if let viewController: DetailViewController = segue.destinationViewController as? DetailViewController {
                viewController.movie = Movie(dict: Library.movies![selectedIndex.row] as! NSDictionary)
            }
        }
    }
}
