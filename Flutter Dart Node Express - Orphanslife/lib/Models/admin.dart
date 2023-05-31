import 'dart:convert';
import 'package:json_annotation/json_annotation.dart';

@JsonSerializable()
class Admin {
  int admin_id;
  String admin_name, admin_dob, admin_gender, admin_govt_id_type, admin_govt_id,
      admin_mobile, admin_email, admin_password, admin_address;
  int admin_location_id, role_id, admin_orphanage_id;
  String admin_image, created_at, updated_at;

  Admin.all(this.admin_id, this.admin_name, this.admin_dob, this.admin_gender, this.admin_govt_id_type, this.admin_govt_id,
      this.admin_mobile, this.admin_email, this.admin_password, this.admin_address, this.admin_location_id, this.role_id,
      this.admin_orphanage_id, this.admin_image, this.created_at, this.updated_at);

  Admin.signup(this.admin_name, this.admin_dob, this.admin_gender,this.admin_govt_id_type, this.admin_govt_id,
      this.admin_mobile, this.admin_email, this.admin_password);

  factory Admin.fromJson(Map<String, dynamic> json) {
    return Admin.all(json['admin_id'] as int, json['admin_name'] as String,json['admin_dob'] as String,json['admin_gender'] as String,
      json['admin_govt_id_type'] as String,json['admin_govt_id'] as String,json['admin_mobile'] as String,json['admin_email'] as String,
      json['admin_password'] as String,json['admin_address'] as String,json['admin_location_id'] as int,json['role_id'] as int,
      json['admin_orphanage_id'] as int,json['admin_image'] as String,json['created_at'] as String,json['updated_at'] as String);
  }

  Map<String, dynamic> toJson() => {
    "admin_id": admin_id,
    "admin_name": admin_name,
    "admin_dob": admin_dob,
    "admin_gender" : admin_gender,
    "admin_govt_id_type" : admin_govt_id_type,
    "admin_govt_id" : admin_govt_id,
    "admin_mobile" : admin_mobile,
    "admin_email": admin_email,
    "admin_password" : admin_password,
    "admin_address": admin_address,
    "admin_location_id": admin_location_id,
    "role_id" : role_id,
    "admin_orphanage_id" : admin_orphanage_id,
    "admin_image" : admin_image,
    "created_at" : created_at,
    "updated_at" : updated_at,
  };

  @override
  String toString() {
    return 'Admin{id: $admin_id, name: $admin_name, dob: $admin_dob, gender: $admin_gender, govt_id_type: $admin_govt_id_type, govt_id: $admin_govt_id, mobile: $admin_mobile, email: $admin_email, password: $admin_password, address: $admin_address, location_id: $admin_location_id, role_id: $role_id, orphanage_id: $admin_orphanage_id, image: $admin_image, created_at: $created_at, updated_at: $updated_at}';
  }
}