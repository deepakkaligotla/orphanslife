//
//  OrphanageActivityDetails.swift
//  orphanslife
//
//  Created by Deepak Kaligotla on 20/02/23.
//

import Foundation
import UIKit
import CoreData

class OrphanageActivityDetails {
    var event_id: Int?
    var orphanage_id: Int?
    var details: String?
    var image_1: String?
    var image_2: String?
    var image_3: String?
    var image_4: String?
    var image_5: String?
    var image_slider: UIImage?
    var managedObject: NSManagedObject?
    
    init(event_id: Int? = nil, orphanage_id: Int? = nil, details: String? = nil, image_1: String? = nil, image_2: String? = nil, image_3: String? = nil, image_4: String? = nil, image_5: String? = nil) {
        self.event_id = event_id
        self.orphanage_id = orphanage_id
        self.details = details
        self.image_1 = image_1
        self.image_2 = image_2
        self.image_3 = image_3
        self.image_4 = image_4
        self.image_5 = image_5
    }
 
    func getAll() -> String {
        return ("Event ID: \(self.event_id!) Orphanage_ID: \(self.orphanage_id!) Event_details: \(self.details!) \n\(self.image_1!)\n\(self.image_2!)\n\(self.image_3!)\n\(self.image_4!)\n\(self.image_5!)")
    }
    
    func getImage() -> UIImage {
            
            if let image_slider = image_slider {
                return image_slider
            }
            let url = self.image_1!
            let imageData = try! Data(contentsOf: URL(string: url)!)
            self.image_slider = UIImage(data: imageData)
            
            return self.image_slider!
        }
}
