import 'dart:convert';

List<Child> modelUserFromJson(String str) =>
    List<Child>.from(json.decode(str).map((x) => Child.fromJson(x)));

class Child {
  String _id, _name, _dob, _gender, _admitted_date, _leave_date, _mother_name, _father_name, _mobile, _child_image, _status_id, _admin_id, _created_at, _updated_at;

  Child.all(this._id,
      this._name,
      this._dob,
      this._gender,
      this._admitted_date,
      this._leave_date,
      this._mother_name,
      this._father_name,
      this._mobile,
      this._child_image,
      this._status_id,
      this._admin_id,
      this._created_at,
      this._updated_at);

  factory Child.fromJson(Map<String, dynamic> json) => Child.all(
      json["id"].toString(),json["name"].toString(),json["dob"].toString(),json["gender"].toString(),json["admitted_date"].toString(),json["leave_date"].toString(),
      json["mother_name"].toString(),json["father_name"].toString(),json["mobile"].toString(),json["child_image"].toString(),json["status_id"].toString(),
      json["admin_id"].toString(),json["created_at"].toString(),
      json["updated_at"].toString()
  );

  Map<String, dynamic> toJson() => {
    "id": id,
    "name": name,
    "dob": dob,
    "gender" : gender,
    "admitted_date" : admitted_date,
    "leave_date" : leave_date,
    "mother_name" : mother_name,
    "father_name": father_name,
    "mobile" : mobile,
    "child_image": child_image,
    "status_id": status_id,
    "admin_id" : admin_id,
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

  get admin_id => _admin_id;

  set admin_id(value) {
    _admin_id = value;
  }

  get status_id => _status_id;

  set status_id(value) {
    _status_id = value;
  }

  get child_image => _child_image;

  set child_image(value) {
    _child_image = value;
  }

  get mobile => _mobile;

  set mobile(value) {
    _mobile = value;
  }

  get father_name => _father_name;

  set father_name(value) {
    _father_name = value;
  }

  get mother_name => _mother_name;

  set mother_name(value) {
    _mother_name = value;
  }

  get leave_date => _leave_date;

  set leave_date(value) {
    _leave_date = value;
  }

  get admitted_date => _admitted_date;

  set admitted_date(value) {
    _admitted_date = value;
  }

  get gender => _gender;

  set gender(value) {
    _gender = value;
  }

  get dob => _dob;

  set dob(value) {
    _dob = value;
  }

  get name => _name;

  set name(value) {
    _name = value;
  }

  String get id => _id;

  set id(String value) {
    _id = value;
  }

  @override
  String toString() {
    return 'Child{_id: $_id, _name: $_name, _dob: $_dob, _gender: $_gender, _admitted_date: $_admitted_date, _leave_date: $_leave_date, _mother_name: $_mother_name, _father_name: $_father_name, _mobile: $_mobile, _child_image: $_child_image, _status_id: $_status_id, _admin_id: $_admin_id, _created_at: $_created_at, _updated_at: $_updated_at}';
  }
}