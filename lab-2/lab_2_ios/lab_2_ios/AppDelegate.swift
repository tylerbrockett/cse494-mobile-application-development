/*
 * @author Tyler Brockett	mailto:tylerbrockett@gmail.com
 * @project Lab 2 - iOS
 * @version January 27, 2016
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

@UIApplicationMain
class AppDelegate: UIResponder, UIApplicationDelegate {

    var window: UIWindow?

    func application(application: UIApplication, didFinishLaunchingWithOptions launchOptions: [NSObject: AnyObject]?) -> Bool {
        /* 
        This is called when the application is launched when it is not already in memory/running.
        */
        NSLog("App Delegate - didFinishLaunchingWithOptions")
        return true
    }

    func applicationWillResignActive(application: UIApplication) {
        /* 
        This is called when you press "Home" while in the app. It occurs BEFORE it actually goes into the background.
        */
        NSLog("App Delegate - applicationWillResignActive")
    }

    func applicationDidEnterBackground(application: UIApplication) {
        /*
        1) There is a small period of time between the call to applicationWillResignActive and the call to this method. This is called when the application ACTUALLY goes into the background.
        2) This is also called before the call to applicationWillTerminate when swiping the app away to "kill" it.
        */
        NSLog("App Delegate - applicationDidEnterBackground")
    }

    func applicationWillEnterForeground(application: UIApplication) {
        /*
        This is called when the app is resumed after going away from it for whatever reason (e.g. pressing "Home"). This is called BEFORE the call to applicationDidBecomeActive.
        */
        NSLog("App Delegate - applicationWillEnterForeground")
    }

    func applicationDidBecomeActive(application: UIApplication) {
        /*
        1) This is called after the application is first launched, when the view is first shown. Again, this will show when you just launch the app. 
        2) This is also called when you resume the app after going away from it for whatever reason (e.g. pressing "Home"). In that case, it is called AFTER the applicationWillEnterForeground.
        */
        NSLog("App Delegate - applicationDidBecomeActive")
    }

    func applicationWillTerminate(application: UIApplication) {
        /*
        This is called when you swipe the app away to "kill" it. 
        */
        NSLog("App Delegate - applicationWillTerminate")
    }
}

