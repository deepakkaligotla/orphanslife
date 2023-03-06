//
//  SponsorHome.swift
//  orphanslife
//
//  Created by Deepak Kaligotla on 20/02/23.
//

import UIKit
import Alamofire

class SponsorHome: BaseViewController, UITableViewDelegate, UITableViewDataSource {
    
    var orphanageActivities: [OrphanageActivityDetails] = []
    
    @IBOutlet weak var sponsorHomeTable: UITableView!
    @IBOutlet weak var welcomeUserMessage: UILabel!
    
    let sponsorLoggedInId = UserDefaults.standard.string(forKey: "sponsor_id")
    let sponsorLoggedInName = UserDefaults.standard.string(forKey: "sponsor_name")!
    let logInStatus = UserDefaults.standard.string(forKey: "login_status")
    
    override func viewWillAppear(_ animated: Bool) {
        getAllOrphanageActivities()
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        let Logout = UIImage(named: "Logout")
        self.navigationItem.rightBarButtonItem = UIBarButtonItem(image: Logout, style: .done, target: self,action: #selector(adminLogout))
        welcomeUserMessage.text = sponsorLoggedInName
        self.sponsorHomeTable.register(OrphanageActivityCell.self, forCellReuseIdentifier: "orphanageActivityCell")
        sponsorHomeTable.delegate = self
        sponsorHomeTable.dataSource = self
    }
    
    func getAllOrphanageActivities() {
        let url = Config.serverUrl+"/activities"
        
        AF.request(url, method: .get, encoding: JSONEncoding.default)
            .responseJSON { [self] response in
                switch (response.result) {
                case .success:
                    let result = response.value as! [String: Any]
                    if (result["status"] as! String == "success") {
                        let data = result["data"] as! [[String: Any]]
                        self.orphanageActivities.removeAll()
                        for event in data {
                            var orphanageActivity = OrphanageActivityDetails()
                            orphanageActivity.event_id = (event["event_id"] as? Int)!
                            orphanageActivity.orphanage_id = (event["orphanage_id"] as? Int)!
                            orphanageActivity.details = (event["details"] as? String)!
                            orphanageActivity.image_1 = (event["image_1"] as? String)!
                            orphanageActivity.image_2 = (event["image_2"] as? String)!
                            orphanageActivity.image_3 = (event["image_3"] as? String)!
                            orphanageActivity.image_4 = (event["image_4"] as? String)!
                            orphanageActivity.image_5 = (event["image_5"] as? String)!
                            self.orphanageActivities.append(orphanageActivity)
                        }
                    }
                    self.sponsorHomeTable.reloadData()
                case .failure(_):
                    return
                }
            }
    }
    
    @objc func adminLogout() {
        let defaults = UserDefaults.standard
        defaults.setValue(0, forKey: "sponsor_id")
        defaults.setValue("", forKey: "sponsor_name")
        defaults.setValue(nil, forKey: "login_status")
        let storyboard = UIStoryboard(name: "Main", bundle: nil)
        let signInVC = storyboard.instantiateViewController(withIdentifier: "MainTabBarViewController")
        print(defaults.bool(forKey: "login_status") as Any)
        self.view.window!.rootViewController = signInVC
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return self.orphanageActivities.count
            }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        
        let cell = sponsorHomeTable.dequeueReusableCell(withIdentifier: "orphanageActivityCell") as! OrphanageActivityCell
        let orphanageActivity = orphanageActivities[indexPath.row]
        cell.orphanageId?.text = "\(String(describing: orphanageActivity.orphanage_id))"
        cell.orphanageActivityDetails?.text = orphanageActivity.details
        cell.eventImage?.image = orphanageActivity.getImage()
            return cell
        }

        func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
                
            sponsorHomeTable.deselectRow(at: indexPath, animated: true)
            let event = self.orphanageActivities[indexPath.row].details!
            let orphanage_id = self.orphanageActivities[indexPath.row].orphanage_id!
            let alert = UIAlertController(title: "selection", message: "\(event) Orphanage: \(orphanage_id) Index: \(indexPath.row)", preferredStyle: .alert)
                alert.addAction(UIAlertAction(title: "OK", style: .default, handler: {action in
                    print("Selected \(event)")
                }))
                present(alert, animated: true)
            }

}
