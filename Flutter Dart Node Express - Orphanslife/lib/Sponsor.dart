class Sponsor {
  String _id, _name, _dob, _gender, _govt_id_type, _govt_id, _mobile,
      _email, _password, _marital_status, _user_image, _user_address,
      _location_id, _spouce_name, _spouce_dob, _spouce_govt_id_type,
      _spouce_govt_id, _spouce_mobile, _spouce_image, _donation_id,
      _created_at, _updated_at;

  Sponsor.all(
      this._id,
      this._name,
      this._dob,
      this._gender,
      this._govt_id_type,
      this._govt_id,
      this._mobile,
      this._email,
      this._password,
      this._marital_status,
      this._user_image,
      this._user_address,
      this._location_id,
      this._spouce_name,
      this._spouce_dob,
      this._spouce_govt_id_type,
      this._spouce_govt_id,
      this._spouce_mobile,
      this._spouce_image,
      this._donation_id,
      this._created_at,
      this._updated_at);

  factory Sponsor.fromJson(Map<String, dynamic> json) => Sponsor.all(
      json["id"].toString(),json["name"].toString(),json["dob"].toString(),json["gender"].toString(),json["govt_id_type"].toString(),
      json["govt_id"].toString(), json["mobile"].toString(),json["email"].toString(),json["password"].toString(),
      json["marital_status"].toString(),json["user_image"].toString(),json["user_address"].toString(),json["location_id"].toString(),
      json["spouce_name"].toString(),json["spouce_dob"].toString(), json["spouce_govt_id_type"].toString(),json["spouce_govt_id"].toString(),
      json["spouce_mobile"].toString(),json["spouce_image"].toString(),json["donation_id"].toString(),json["created_at"].toString(),
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
    "marital_status" : marital_status,
    "user_image" : user_image,
    "user_address": user_address,
    "location_id": location_id,
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

  get updated_at => _updated_at;

  set updated_at(value) {
    _updated_at = value;
  }

  get created_at => _created_at;

  set created_at(value) {
    _created_at = value;
  }

  get donation_id => _donation_id;

  set donation_id(value) {
    _donation_id = value;
  }

  get spouce_image => _spouce_image;

  set spouce_image(value) {
    _spouce_image = value;
  }

  get spouce_mobile => _spouce_mobile;

  set spouce_mobile(value) {
    _spouce_mobile = value;
  }

  get spouce_govt_id => _spouce_govt_id;

  set spouce_govt_id(value) {
    _spouce_govt_id = value;
  }

  get spouce_govt_id_type => _spouce_govt_id_type;

  set spouce_govt_id_type(value) {
    _spouce_govt_id_type = value;
  }

  get spouce_dob => _spouce_dob;

  set spouce_dob(value) {
    _spouce_dob = value;
  }

  get spouce_name => _spouce_name;

  set spouce_name(value) {
    _spouce_name = value;
  }

  get location_id => _location_id;

  set location_id(value) {
    _location_id = value;
  }

  get user_address => _user_address;

  set user_address(value) {
    _user_address = value;
  }

  get user_image => _user_image;

  set user_image(value) {
    _user_image = value;
  }

  get marital_status => _marital_status;

  set marital_status(value) {
    _marital_status = value;
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
    return 'Sponsor{_id: $_id, _name: $_name, _dob: $_dob, _gender: $_gender, _govt_id_type: $_govt_id_type, _govt_id: $_govt_id, _mobile: $_mobile, _email: $_email, _password: $_password, _marital_status: $_marital_status, _user_image: $_user_image, _user_address: $_user_address, _location_id: $_location_id, _spouce_name: $_spouce_name, _spouce_dob: $_spouce_dob, _spouce_govt_id_type: $_spouce_govt_id_type, _spouce_govt_id: $_spouce_govt_id, _spouce_mobile: $_spouce_mobile, _spouce_image: $_spouce_image, _donation_id: $_donation_id, _created_at: $_created_at, _updated_at: $_updated_at}';
  }
}