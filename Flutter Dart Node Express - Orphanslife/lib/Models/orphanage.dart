import 'dart:convert';

List<Orphanage> modelUserFromJson(String str) =>
    List<Orphanage>.from(json.decode(str).map((x) => Orphanage.fromJson(x)));

class Orphanage {
  String _id, _type, _address, _location_id, _contact_person, _mobile, _phone, _email, _in_home,
      _adoptable, _boys, _girls, _orphanage_image, _created_at, _updated_at;

  Orphanage.all(this._id,
      this._type,
      this._address,
      this._location_id,
      this._contact_person,
      this._mobile,
      this._phone,
      this._email,
      this._in_home,
      this._adoptable,
      this._boys,
      this._girls,
      this._orphanage_image,
      this._created_at,
      this._updated_at);

  factory Orphanage.fromJson(Map<String, dynamic> json) => Orphanage.all(
      json["id"].toString(),json["type"].toString(),json["address"].toString(),json["location_id"].toString(),json["contact_person"].toString(),json["mobile"].toString(),
      json["phone"].toString(),json["email"].toString(),json["in_home"].toString(),json["adoptable"].toString(),json["boys"].toString(),
      json["girls"].toString(),json["orphanage_image"].toString(),json["created_at"].toString(),
      json["updated_at"].toString()
  );

  Map<String, dynamic> toJson() => {
    "id": id,
    "type": type,
    "address": address,
    "location_id" : location_id,
    "contact_person" : contact_person,
    "mobile" : mobile,
    "phone" : phone,
    "email": email,
    "in_home" : in_home,
    "adoptable": adoptable,
    "boys": boys,
    "girls" : girls,
    "orphanage_image" : orphanage_image,
    "created_at" : created_at,
    "updated_at" : updated_at,
  };

  get updated_at => _updated_at;

  set updated_at(value) {
    _updated_at = value;
  }

  get created_at => _created_at;

  set created_at(value) {
    _created_at = value;
  }

  get orphanage_image => _orphanage_image;

  set orphanage_image(value) {
    _orphanage_image = value;
  }

  get girls => _girls;

  set girls(value) {
    _girls = value;
  }

  get boys => _boys;

  set boys(value) {
    _boys = value;
  }

  get adoptable => _adoptable;

  set adoptable(value) {
    _adoptable = value;
  }

  get in_home => _in_home;

  set in_home(value) {
    _in_home = value;
  }

  get email => _email;

  set email(value) {
    _email = value;
  }

  get phone => _phone;

  set phone(value) {
    _phone = value;
  }

  get mobile => _mobile;

  set mobile(value) {
    _mobile = value;
  }

  get contact_person => _contact_person;

  set contact_person(value) {
    _contact_person = value;
  }

  get location_id => _location_id;

  set location_id(value) {
    _location_id = value;
  }

  get address => _address;

  set address(value) {
    _address = value;
  }

  get type => _type;

  set type(value) {
    _type = value;
  }

  String get id => _id;

  set id(String value) {
    _id = value;
  }

  @override
  String toString() {
    return 'Orphanage{id: $_id, _type: $_type, address: $_address, location_id: $_location_id, contact_person: $_contact_person,'
        ' mobile: $_mobile, phone: $_phone, email: $_email, in_home: $_in_home, adoptable: $_adoptable, boys: $_boys,'
        ' girls: $_girls, orphanage_image: $_orphanage_image, created_at: $_created_at, updated_at: $_updated_at}';
  }
}