import 'dart:convert';

List<Admin> modelUserFromJson(String str) =>
    List<Admin>.from(json.decode(str).map((x) => Admin.fromJson(x)));

class Admin {
  String _id, _name, _dob, _gender, _govt_id_type, _govt_id, _mobile,
      _email, _password, _address, _location_id, _role_id, _orphanage_id,
      _image, _created_at, _updated_at;

  Admin.all(this._id,
      this._name,
      this._dob,
      this._gender,
      this._govt_id_type,
      this._govt_id,
      this._mobile,
      this._email,
      this._password,
      this._address,
      this._location_id,
      this._role_id,
      this._orphanage_id,
      this._image,
      this._created_at,
      this._updated_at);

  factory Admin.fromJson(Map<String, dynamic> json) => Admin.all(
    json["id"].toString(),json["name"].toString(),json["dob"].toString(),json["gender"].toString(),json["govt_id_type"].toString(),json["govt_id"].toString(),
      json["mobile"].toString(),json["email"].toString(),json["password"].toString(),json["address"].toString(),json["location_id"].toString(),
      json["role_id"].toString(),json["orphanage_id"].toString(),json["image"].toString(),json["created_at"].toString(),
      json["updated_at"].toString()
  );

  Map<String, dynamic> toJson() => {
    "id": id,
    "name": name,
    "dob": dob,
    "gender" : gender,
    "govt_id_type" : govt_id_type,
    "govt_id" : govt_id,
    "mobile" : mobile,
    "email": email,
    "password" : password,
    "address": address,
    "location_id": location_id,
    "role_id" : role_id,
    "orphanage_id" : orphanage_id,
    "image" : image,
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

  get image => _image;

  set image(value) {
    _image = value;
  }

  get orphanage_id => _orphanage_id;

  set orphanage_id(value) {
    _orphanage_id = value;
  }

  get role_id => _role_id;

  set role_id(value) {
    _role_id = value;
  }

  get location_id => _location_id;

  set location_id(value) {
    _location_id = value;
  }

  get address => _address;

  set address(value) {
    _address = value;
  }

  get password => _password;

  set password(value) {
    _password = value;
  }

  get email => _email;

  set email(value) {
    _email = value;
  }

  get mobile => _mobile;

  set mobile(value) {
    _mobile = value;
  }

  get govt_id => _govt_id;

  set govt_id(value) {
    _govt_id = value;
  }

  get govt_id_type => _govt_id_type;

  set govt_id_type(value) {
    _govt_id_type = value;
  }

  get gender => _gender;

  set gender(value) {
    _gender = value;
  }

  get dob => _dob;

  set dob(value) {
    _dob = value;
  }

  String get name => _name;

  set name(String value) {
    _name = value;
  }

  String get id => _id;

  set id(String value) {
    _id = value;
  }

  @override
  String toString() {
    return 'Admin{_id: $_id, _name: $_name, _dob: $_dob, _gender: $_gender, _govt_id_type: $_govt_id_type, _govt_id: $_govt_id, _mobile: $_mobile, _email: $_email, _password: $_password, _address: $_address, _location_id: $_location_id, _role_id: $_role_id, _orphanage_id: $_orphanage_id, _image: $_image, _created_at: $_created_at, _updated_at: $_updated_at}';
  }
}