/*
 * @author              Tyler Brockett	mailto:tylerbrockett@gmail.com
 * @course              ASU CSE 494
 * @project             Lab 9 - iOS
 * @version             April 25, 2016
 * @project-description Get movie data from two sources and play movie if file exists.
 * @class-name          NetworkAsyncTask.swift
 * @class-description   Handles network calls and calls the callback methods.
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
import Foundation

class NetworkAsyncTask {
    
    let jsonUrl:String = "http://localhost:8080"
    
    let GET:String = "GET"
    let POST:String = "POST"
    
    init(){
    }
    
    func asyncHttpGet(url: String, data:NSData?, requestMethod:String,
        callback: (NSData?, NSError?) -> Void) {
            let request = NSMutableURLRequest(URL: NSURL(string: url)!)
            request.HTTPMethod = requestMethod
            request.addValue("application/json",forHTTPHeaderField: "Content-Type")
            request.addValue("application/json",forHTTPHeaderField: "Accept")
            if data != nil {
                print("Data added to request")
                request.HTTPBody = data!
            }
            sendHttpRequest(request, callback: callback)
    }
    
    func sendHttpRequest(request: NSMutableURLRequest,
                         callback: (NSData?, NSError?) -> Void) {
            let task = NSURLSession.sharedSession().dataTaskWithRequest(request) {
                (data, response, error) -> Void in
                if (error != nil) {
                    callback(nil, error!)
                } else {
                    dispatch_async(dispatch_get_main_queue(),
                        {callback(data!, nil)})
                }
            }
            task.resume()
    }
    
    
    func fetchMovieOmdb(title:String, callback: (MovieParser?, NSError?) -> Void) {
        let t = title.stringByReplacingOccurrencesOfString(" ", withString: "+", options: NSStringCompareOptions.LiteralSearch, range: nil)
        let url = "http://www.omdbapi.com/?t=\(t)&plot=short&r=json"
        print("URL: \(url)")
        self.asyncHttpGet(url, data: nil, requestMethod: self.GET, callback: { (result: NSData?, error: NSError?) -> Void in
            if error != nil {
                callback(nil, error!)
            } else {
                do{
                    let response = try NSJSONSerialization.JSONObjectWithData(result! ,options:.MutableContainers) as! NSDictionary
                    print("Response: \(response)")
                    if let valid:String = response.valueForKey("Response") as? String {
                        if valid == "True" {
                            let movie:MovieParser = MovieParser(dictionary: response)
                            callback(movie, nil)
                        } else {
                            callback(nil, NSError(domain: "NetworkAsyncTask", code: 987, userInfo: nil))
                        }
                        
                    } else {
                        callback(nil, NSError(domain: "NetworkAsyncTask", code: 999, userInfo: nil))
                    }
                } catch let error as NSError {
                    NSLog("unable to convert to dictionary")
                    callback(nil, error)
                }
            }
        })
    }
    
    func fetchMovieJsonRPC(title:String, callback: (MovieParser?, NSError?) -> Void) {
        let dict:[String:AnyObject] = ["jsonrpc":"2.0", "method":"get", "params":[title], "id":3]
        var reqData:NSData = NSData()
        do {
            reqData = try NSJSONSerialization.dataWithJSONObject(dict, options: NSJSONWritingOptions(rawValue: 0))
        } catch let error as NSError {
            print(error)
            callback(nil, error)
        }
        self.asyncHttpGet(self.jsonUrl, data: reqData, requestMethod: self.POST, callback: { (result: NSData?, error: NSError?) -> Void in
            if error != nil {
                callback(nil, error!)
            } else {
                do{
                    let response = try NSJSONSerialization.JSONObjectWithData(result! ,options:.MutableContainers) as! NSDictionary
                    let movie:NSDictionary = response.valueForKey("result") as! NSDictionary
                    let m:MovieParser = MovieParser(dictionary: movie)
                    if m.getTitle() != "Unknown" {
                        callback(m, nil)
                    } else {
                        callback(nil, NSError(domain: "NetworkAsyncTask", code: 123, userInfo: nil))
                    }
                    
                } catch let error as NSError {
                    NSLog("unable to convert to dictionary")
                    callback(nil, error)
                }
            }
        })
    }
    
    
    func getPoster(url:String, callback: (UIImage, String?) -> Void) {
        dispatch_async(dispatch_get_main_queue()) {
            if let
                url = NSURL(string: url),
                data = NSData(contentsOfURL: url),
                image = UIImage(data: data)
            {
                callback(image, nil)
            }
            else {
                let image:UIImage = UIImage(named:"reel")!
                callback(image, nil)
            }
        }
    }
    
}
