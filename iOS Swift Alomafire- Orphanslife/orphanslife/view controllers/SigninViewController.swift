//
//  SigninViewController.swift
//  orphanslife
//
//  Created by Deepak Kaligotla on 03/02/23.
//

import UIKit
import Alamofire

class SigninViewController: BaseViewController {

    @IBOutlet weak var loginEmail: UITextField!
    @IBOutlet weak var loginPassword: UITextField!
    @IBOutlet weak var loginRememberMe: UISwitch!
    
    override func viewDidLoad() {
        super.viewDidLoad()
    }
    
    override func viewDidAppear(_ animated: Bool) {
        super.viewDidAppear(animated)
        
        let logInStatus = UserDefaults.standard.bool(forKey: "login_status")
        let adminRole = UserDefaults.standard.string(forKey: "admin_role")
        if (logInStatus && adminRole=="Super_Admin"){
                let storyboard = UIStoryboard(name: "Main", bundle: nil)
                let adminHomeVC = storyboard.instantiateViewController(withIdentifier: "AdminTabBarViewController")
                self.view.window?.rootViewController = adminHomeVC
        }else if(logInStatus) {
            let storyboard = UIStoryboard(name: "Main", bundle: nil)
            let sponsorHomeVC = storyboard.instantiateViewController(withIdentifier: "SponsorTabBarViewController")
            self.view.window?.rootViewController = sponsorHomeVC
        }
    }
    
    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?) -> Bool {
        let storyboard = UIStoryboard(name: "Main", bundle: nil)
        if let loggedUsername = UserDefaults.standard.string(forKey: "admin_name") {
            let mainTabBarController = storyboard.instantiateViewController(identifier: "AdminTabBarViewController")
            self.view.window?.rootViewController = mainTabBarController
        } else {
            let loginNavController = storyboard.instantiateViewController(identifier: "SigninViewController")
            self.view.window?.rootViewController = loginNavController
        }
      
        return true
    }
    
    @IBAction func loginOnSignIn() {
        
        let providedEmail = loginEmail.text!
        let providedPassword = loginPassword.text!
        
        if providedEmail.count == 0 {
            self.showErrorAlert(message: "please enter registered email")
        } else if providedPassword.count == 0 {
            self.showErrorAlert(message: "please enter password")
        } else {
            
            let body = [
                "email": providedEmail,
                "password": providedPassword
            ]
            
            let url = Config.serverUrl+"/adminlogin"
            
            AF.request(url, method: .post, parameters: body, encoding: JSONEncoding.default)
                .responseJSON { [self] response in
                    switch (response.result) {
                        case .success:
                        let result = response.value as! [String: Any]
                        if result["status"] as! String == "success" {
                            let data = result["data"] as! [[String: Any]]
                            if data.isEmpty {
//                                self.showErrorAlert(message: "Cannot find your account details, please verify & re-enter registered email, password (or) SIGN UP")
                                sponsorLogin(providedEmail: providedEmail,providedPassword: providedPassword)
                            }
                            else {
                                let admin = data[0]
                                let id = admin["id"] as! Int
                                let name = admin["name"] as! String
                                let role = admin["role"] as! String
                                let email = admin["email"] as! String
                                let password = admin["password"] as! String
                                if email==providedEmail && password==providedPassword {
                                    let defaults = UserDefaults.standard
                                    if loginRememberMe.isOn {
                                        defaults.setValue(id, forKey: "admin_id")
                                        defaults.setValue(name, forKey: "admin_name")
                                        defaults.setValue(role, forKey: "admin_role")
                                        defaults.setValue(true, forKey: "login_status")
                                        defaults.synchronize()
                                        print("Inside remember me on")
                                    } else {
                                        defaults.setValue(id, forKey: "admin_id")
                                        defaults.setValue(name, forKey: "admin_name")
                                        defaults.setValue(role, forKey: "admin_role")
                                        defaults.setValue(false, forKey: "login_status")
                                        print("Inside remember me off")
                                    }
                                    
                                    if (defaults.bool(forKey: "login_status")==true||defaults.bool(forKey: "login_status")==false) {
                                        let storyboard = UIStoryboard(name: "Main", bundle: nil)
                                        let adminHomeVC = storyboard.instantiateViewController(withIdentifier: "AdminTabBarViewController")
                                        
                                        self.view.window?.rootViewController = adminHomeVC
                                    }
                                }
                            }
                        }
                            break
                    case .failure:
                        self.showErrorAlert(message: "Failed to call API")
                        
                    }
                    
                }
        }
    }
    
    @objc func sponsorLogin(providedEmail:String, providedPassword:String){
        
        if providedEmail.count == 0 {
            self.showErrorAlert(message: "please enter registered email")
        } else if providedPassword.count == 0 {
            self.showErrorAlert(message: "please enter password")
        } else {
            
            let body = [
                "email": providedEmail,
                "password": providedPassword
            ]
            
            let url = Config.serverUrl+"/sponsorlogin"
            
            AF.request(url, method: .post, parameters: body, encoding: JSONEncoding.default)
                .responseJSON { [self] response in
                    switch (response.result) {
                        case .success:
                        let result = response.value as! [String: Any]
                        if result["status"] as! String == "success" {
                            let data = result["data"] as! [[String: Any]]
                            if data.isEmpty {
                                self.showErrorAlert(message: "Cannot find your account details, please provide valid email & password or SIGN-UP")
                            }
                            else {
                                let sponsor = data[0]
                                let id = sponsor["id"] as! Int
                                let name = sponsor["name"] as! String
                                let email = sponsor["email"] as! String
                                let password = sponsor["password"] as! String
                                if email==email && password==password {
                                    let defaults = UserDefaults.standard
                                    if loginRememberMe.isOn {
                                        defaults.setValue(id, forKey: "sponsor_id")
                                        defaults.setValue(name, forKey: "sponsor_name")
                                        defaults.setValue(true, forKey: "login_status")
                                        defaults.synchronize()
                                        print("Inside remember me on")
                                    } else {
                                        defaults.setValue(id, forKey: "sponsor_id")
                                        defaults.setValue(name, forKey: "sponsor_name")
                                        defaults.setValue(false, forKey: "login_status")
                                        print("Inside remember me off")
                                    }
                                    
                                    if (defaults.bool(forKey: "login_status")==true||defaults.bool(forKey: "login_status")==false) {
                                        let storyboard = UIStoryboard(name: "Main", bundle: nil)
                                        let sponsorHomeVC = storyboard.instantiateViewController(withIdentifier: "SponsorTabBarViewController")
                                        
                                        self.view.window?.rootViewController = sponsorHomeVC
                                    }
                                }
                            }
                        }
                            break
                    case .failure:
                        self.showErrorAlert(message: "Failed to call API")
                        
                    }
                    
                }
        }
        
    }
    
    @IBAction func admimLoginForgotPassword() {
        
    }
}
