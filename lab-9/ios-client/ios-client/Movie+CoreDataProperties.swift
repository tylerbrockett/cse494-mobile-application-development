//
//  Movie+CoreDataProperties.swift
//  ios-client
//
//  Created by Tyler Brockett on 4/24/16.
//  Copyright © 2016 Tyler Brockett. All rights reserved.
//
//  Choose "Create NSManagedObject Subclass…" from the Core Data editor menu
//  to delete and recreate this implementation file for your updated model.
//

import Foundation
import CoreData

extension Movie {

    @NSManaged var actors: String?
    @NSManaged var genre: String?
    @NSManaged var plot: String?
    @NSManaged var poster: String?
    @NSManaged var rated: String?
    @NSManaged var released: String?
    @NSManaged var runtime: String?
    @NSManaged var title: String?
    @NSManaged var year: String?
    @NSManaged var filename: String?

}
