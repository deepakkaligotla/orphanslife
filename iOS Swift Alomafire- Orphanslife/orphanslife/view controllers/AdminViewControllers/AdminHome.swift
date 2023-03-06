//
//  AdminHome.swift
//  orphanslife
//
//  Created by Deepak Kaligotla on 04/02/23.
//

import UIKit

class AdminHome: BaseViewController, UITableViewDelegate, UITableViewDataSource {
    
    let adminHomeTableMenu: [String] = ["Sponsors","Childs","Orphanages","Orphanage Activities","Adopt Requests","Adopt Status","Roles","Locations","Admins","Donations"]
    
    @IBOutlet weak var adminHomeTable: UITableView!
    @IBOutlet weak var welcomeUserMessage: UILabel!
    

    let adminLoggedInId = UserDefaults.standard.string(forKey: "admin_id")
    let adminLoggedInName = UserDefaults.standard.string(forKey: "admin_name")!
    let adminLoggedInRole = UserDefaults.standard.string(forKey: "admin_role")!
    let logInStatus = UserDefaults.standard.string(forKey: "login_status")
    
    override func viewDidLoad() {
        super.viewDidLoad()
        let Logout = UIImage(named: "Logout")
        self.navigationItem.rightBarButtonItem = UIBarButtonItem(image: Logout, style: .done, target: self,action: #selector(sponsorLogout))
                
        welcomeUserMessage.text = adminLoggedInName+" || Role: "+adminLoggedInRole
        adminHomeTable.delegate = self
        adminHomeTable.dataSource = self
    }
    
    @objc func sponsorLogout() {
        let defaults = UserDefaults.standard
        defaults.setValue(0, forKey: "admin_id")
        defaults.setValue("", forKey: "admin_name")
        defaults.setValue("", forKey: "admin_role")
        defaults.setValue(nil, forKey: "login_status")
        let storyboard = UIStoryboard(name: "Main", bundle: nil)
        let signInVC = storyboard.instantiateViewController(withIdentifier: "MainTabBarViewController")
        self.view.window!.rootViewController = signInVC
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
                return adminHomeTableMenu.count
            }
        func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
                let cell = UITableViewCell(style: .default, reuseIdentifier: nil)
                cell.textLabel?.text = adminHomeTableMenu[indexPath.row]
                return cell
            }

        func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
                
                adminHomeTable.deselectRow(at: indexPath, animated: true)
                let menu = adminHomeTableMenu[indexPath.row]
                print("Selected Menu: \(menu)")
            
            switch menu {
            case "Sponsors":
                let alert = UIAlertController(title: "selection", message: "Inside: \(menu)", preferredStyle: .alert)
                alert.addAction(UIAlertAction(title: "OK", style: .default, handler: {action in
                    print("ok tapped")
                }))
                present(alert, animated: true)
            case "Childs":
                let alert = UIAlertController(title: "selection", message: "Inside: \(menu)", preferredStyle: .alert)
                alert.addAction(UIAlertAction(title: "OK", style: .default, handler: {action in
                    print("ok tapped")
                }))
                present(alert, animated: true)
            case "Donations":
                let alert = UIAlertController(title: "selection", message: "Inside: \(menu)", preferredStyle: .alert)
                alert.addAction(UIAlertAction(title: "OK", style: .default, handler: {action in
                    print("ok tapped")
                }))
                present(alert, animated: true)
            case "Orphanages":
                let alert = UIAlertController(title: "selection", message: "Inside: \(menu)", preferredStyle: .alert)
                alert.addAction(UIAlertAction(title: "OK", style: .default, handler: {action in
                    print("ok tapped")
                }))
                present(alert, animated: true)
            case "Orphanage Activities":
                let alert = UIAlertController(title: "selection", message: "Inside: \(menu)", preferredStyle: .alert)
                alert.addAction(UIAlertAction(title: "OK", style: .default, handler: {action in
                    print("ok tapped")
                }))
                present(alert, animated: true)
            case "Adopt Requests":
                let alert = UIAlertController(title: "selection", message: "Inside: \(menu)", preferredStyle: .alert)
                alert.addAction(UIAlertAction(title: "OK", style: .default, handler: {action in
                    print("ok tapped")
                }))
                present(alert, animated: true)
            case "Adopt Status":
                let alert = UIAlertController(title: "selection", message: "Inside: \(menu)", preferredStyle: .alert)
                alert.addAction(UIAlertAction(title: "OK", style: .default, handler: {action in
                    print("ok tapped")
                }))
                present(alert, animated: true)
            case "Roles":
                let alert = UIAlertController(title: "selection", message: "Inside: \(menu)", preferredStyle: .alert)
                alert.addAction(UIAlertAction(title: "OK", style: .default, handler: {action in
                    print("ok tapped")
                }))
                present(alert, animated: true)
            case "Locations":
                let alert = UIAlertController(title: "selection", message: "Inside: \(menu)", preferredStyle: .alert)
                alert.addAction(UIAlertAction(title: "OK", style: .default, handler: {action in
                    print("ok tapped")
                }))
                present(alert, animated: true)
            case "Admins":
                let alert = UIAlertController(title: "selection", message: "Inside: \(menu)", preferredStyle: .alert)
                alert.addAction(UIAlertAction(title: "OK", style: .default, handler: {action in
                    print("ok tapped")
                }))
                present(alert, animated: true)
            default:
                self.navigationController?.popViewController(animated: true)
            }
            }

}
