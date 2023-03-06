//
//  BaseViewController.swift
//  orphanslife
//
//  Created by Deepak Kaligotla on 03/02/23.
//

import UIKit
import Foundation

class BaseViewController: UIViewController {

    override func viewDidLoad() {
        super.viewDidLoad()
    }
    
    func showSuccessAlert(message: String) {
        showAlert(title: "Success", message: message)
    }

    func showErrorAlert(message: String) {
        showAlert(title: "Error", message: message)
    }

    func showWarningAlert(message: String) {
        showAlert(title: "Warning", message: message)
    }

    func showAlert(title: String, message: String) {
        let alert = UIAlertController(title: title, message: message, preferredStyle: .alert)
        let action = UIAlertAction(title: "OK", style: .default)
        alert.addAction(action)
        present(alert, animated: true)
    }

}
