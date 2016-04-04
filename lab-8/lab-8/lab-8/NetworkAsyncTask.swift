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
    
    init(){
    }
    
    func asyncHttpGet(url: String,
        callback: (String, String?) -> Void) {
            let request = NSMutableURLRequest(URL: NSURL(string: url)!)
            request.HTTPMethod = "GET"
            request.addValue("application/json",forHTTPHeaderField: "Content-Type")
            request.addValue("application/json",forHTTPHeaderField: "Accept")
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
    
    func fetchMovie(title:String, callback: (String, String?) -> Void) -> Bool {
        let t = title.stringByReplacingOccurrencesOfString(" ", withString: "+", options: NSStringCompareOptions.LiteralSearch, range: nil)
        let url = "http://www.omdbapi.com/?t=\(t)&plot=short&r=json"
        self.asyncHttpGet(url, callback: callback)
        return true
    }
    
    func getPoster(url:String, callback: (NSData?, String?) -> Void) {
        let url = NSURL(string: url)
        dispatch_async(dispatch_get_main_queue()) {
            if let data = NSData(contentsOfURL: url!){
                dispatch_async(dispatch_get_main_queue(),
                    {callback(data, nil)})
            } else {
                callback(nil, "Failed to get poster")
            }
        }
    }
}

