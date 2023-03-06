//
//  OrphanageActiviityCell.swift
//  orphanslife
//
//  Created by Deepak Kaligotla on 20/02/23.
//
import UIKit
import CoreData

class OrphanageActivityCell: UITableViewCell {
    
    @IBOutlet weak var orphanageId: UILabel!
    @IBOutlet weak var orphanageActivityDetails: UILabel!
    @IBOutlet weak var eventImage: UIImageView!
    @IBOutlet weak var eventsImagePageControl: UIPageControl!
    
    var managedObject: NSManagedObject!
    
    override func awakeFromNib() {
        super.awakeFromNib()
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)
    }
}
