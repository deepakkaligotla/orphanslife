import 'dart:convert';
import 'package:json_annotation/json_annotation.dart';

@JsonSerializable()
class Sponsor {
  int sponsor_id;
  String sponsor_name, sponsor_dob, sponsor_gender, sponsor_govt_id_type,
      sponsor_govt_id, sponsor_mobile, sponsor_email, sponsor_password, marital_status,
      sponsor_image, sponsor_address;
  int sponsor_location_id;
  String spouce_name, spouce_dob, spouce_govt_id_type, spouce_govt_id, spouce_mobile,
      spouce_image;
  int donation_id;
  String created_at, updated_at;

  Sponsor.all({this.sponsor_id,this.sponsor_name,this.sponsor_dob,this.sponsor_gender,
  this.sponsor_govt_id_type,this.sponsor_govt_id,this.sponsor_mobile,this.sponsor_email,this.sponsor_password,
  this.marital_status,this.sponsor_image,this.sponsor_address,this.sponsor_location_id,
  this.spouce_name,this.spouce_dob,this.spouce_govt_id_type,this.spouce_govt_id,
  this.spouce_mobile,this.spouce_image,this.donation_id,this.created_at,this.updated_at});

  factory Sponsor.fromJson(Map<String, dynamic> json) => Sponsor.all();

  Map<String, dynamic> toJson() => {
    "sponsor_id": sponsor_id,
    "sponsor_name": sponsor_name,
    "sponsor_dob": sponsor_dob,
    "sponsor_gender" : sponsor_gender,
    "sponsor_govt_id_type" : sponsor_govt_id_type,
    "sponsor_govt_id" : sponsor_govt_id,
    "sponsor_mobile" : sponsor_mobile,
    "sponsor_email": sponsor_email,
    "sponsor_password" : sponsor_password,
    "marital_status" : marital_status,
    "sponsor_image" : sponsor_image,
    "sponsor_address": sponsor_address,
    "sponsor_location_id": sponsor_location_id,
    "spouce_name" : spouce_name,
    "spouce_dob" : spouce_dob,
    "spouce_govt_id_type" : spouce_govt_id_type,
    "spouce_govt_id" : spouce_govt_id,
    "spouce_mobile" : spouce_mobile,
    "spouce_image" : spouce_image,
    "donation_id" : donation_id,
    "created_at" : created_at,
    "updated_at" : updated_at,
  };

}