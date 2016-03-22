/*
 * @author				Tyler Brockett	mailto:tylerbrockett@gmail.com
 * @course				ASU CSE 494
 * @project				Lab 6 - iOS
 * @version				March 16, 2016
 * @project-description	Use iOS client to get/post data from/to JSON-RPC Server
 * @class-name          NetworkAsyncTask.swift
 * @class-description   Performs network activity on separate thread to prevent UI from locking up.
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

import Foundation

class NetworkAsyncTask {
    
    var url:String
    
    init(urlString: String){
        self.url = urlString
    }
    
    // asyncHttpPostJson creates and posts a URLRequest that attaches a JSONRPC request as an NSData object
    func asyncHttpPostJSON(url: String,  data: NSData,
        callback: (String, String?) -> Void) {
            let request = NSMutableURLRequest(URL: NSURL(string: url)!)
            request.HTTPMethod = "POST"
            request.addValue("application/json",forHTTPHeaderField: "Content-Type")
            request.addValue("application/json",forHTTPHeaderField: "Accept")
            request.HTTPBody = data
            sendHttpRequest(request, callback: callback)
    }
    
    // sendHttpRequest
    func sendHttpRequest(request: NSMutableURLRequest,
        callback: (String, String?) -> Void) {
            // task.resume causes the shared session http request to be posted in the background (non-UI Thread)
            // the use of the dispatch_async on the main queue causes the callback to be performed on the UI Thread
            // after the result of the post is received.
            let task = NSURLSession.sharedSession().dataTaskWithRequest(request) {
                (data, response, error) -> Void in
                if (error != nil) {
                    callback("", error!.localizedDescription)
                } else {
                    dispatch_async(dispatch_get_main_queue(),
                        {callback(NSString(data: data!,
                            encoding: NSUTF8StringEncoding)! as String, nil)})
                }
            }
            task.resume()
    }
    
    func add(movie:Movie, callback: (String, String?) -> Void) -> Bool {
        var ret:Bool = false
        do {
            let dict:[String:AnyObject] = ["jsonrpc":"2.0", "method":"add", "params":[movie.getJSON()], "id":3 ]
            let reqData:NSData = try NSJSONSerialization.dataWithJSONObject(dict, options: NSJSONWritingOptions(rawValue: 0))
            self.asyncHttpPostJSON(self.url, data: reqData, callback: callback)
            ret = true
        } catch let error as NSError {
            print(error)
        }
        return ret
    }
    
    func remove(id:String, callback: (String, String?) -> Void) -> Bool {
        var ret:Bool = false
        do {
            let dict:[String:AnyObject] = ["jsonrpc":"2.0", "method":"remove", "params":[id], "id":3 ]
            let reqData:NSData = try NSJSONSerialization.dataWithJSONObject(dict, options: NSJSONWritingOptions(rawValue: 0))
            self.asyncHttpPostJSON(self.url, data: reqData, callback: callback)
            ret = true
        } catch let error as NSError {
            print(error)
        }
        return ret
    }
    
    func edit(movie:Movie, callback: (String, String?) -> Void) -> Bool {
        var ret:Bool = false
        do {
            let dict:[String:AnyObject] = ["jsonrpc":"2.0", "method":"edit", "params":[movie.getJSON()], "id":3 ]
            let reqData:NSData = try NSJSONSerialization.dataWithJSONObject(dict, options: NSJSONWritingOptions(rawValue: 0))
            self.asyncHttpPostJSON(self.url, data: reqData, callback: callback)
            ret = true
        } catch let error as NSError {
            print(error)
        }
        return ret
    }
    
    func getAll(callback: (String, String?) -> Void) -> Bool {
        var ret:Bool = false
        do {
            print("IN THE DO")
            let dict:[String:AnyObject] = ["jsonrpc":"2.0", "method":"getAll", "params":[], "id":3 ]
            let reqData:NSData = try NSJSONSerialization.dataWithJSONObject(dict, options: NSJSONWritingOptions(rawValue: 0))
            print("BEFORE ASYNC HTTP POST JSON")
            self.asyncHttpPostJSON(self.url, data: reqData, callback: callback)
            print("AFTER THE ASYNC HTTP POST JSON")
            ret = true
        } catch let error as NSError {
            print(error)
        }
        print("BEFORE RETURN")
        return ret
    }
    
    func get(id: String, callback: (String, String?) -> Void) -> Bool{
        var ret:Bool = false
        do {
            let dict:[String:AnyObject] = ["jsonrpc":"2.0", "method":"get", "params":[id], "id":3]
            let reqData:NSData = try NSJSONSerialization.dataWithJSONObject(dict, options: NSJSONWritingOptions(rawValue: 0))
            self.asyncHttpPostJSON(self.url, data: reqData, callback: callback)
            ret = true
        } catch let error as NSError {
            print(error)
        }
        return ret
    }
    
    func getIDs(callback: (String, String?) -> Void) -> Bool{
        var ret:Bool = false
        do {
            let dict:[String:AnyObject] = ["jsonrpc":"2.0", "method":"getIDs", "params":[ ], "id":3]
            let reqData:NSData = try NSJSONSerialization.dataWithJSONObject(dict, options: NSJSONWritingOptions(rawValue: 0))
            self.asyncHttpPostJSON(self.url, data: reqData, callback: callback)
            ret = true
        } catch let error as NSError {
            print(error)
        }
        return ret
    }
}

