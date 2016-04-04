//
//  Movies+CoreDataProperties.swift
//  lab-8
//
//  Created by Tyler Brockett on 4/3/16.
//  Copyright © 2016 Tyler Brockett. All rights reserved.
//
//  Choose "Create NSManagedObject Subclass…" from the Core Data editor menu
//  to delete and recreate this implementation file for your updated model.
//

import Foundation
import CoreData

extension Movies {

    @NSManaged var title: String?
    @NSManaged var year: String?
    @NSManaged var rated: String?
    @NSManaged var released: String?
    @NSManaged var runtime: String?
    @NSManaged var genre: String?
    @NSManaged var actors: String?
    @NSManaged var plot: String?
    @NSManaged var poster: String?

}
