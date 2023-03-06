import 'dart:convert';

List<Location> modelUserFromJson(String str) =>
    List<Location>.from(json.decode(str).map((x) => Location.fromJson(x)));

class Location {
  String _id, _pincode, _area, _city, _district, _state;

  Location.all(this._id,this._pincode,this._area,this._city,this._district,this._state);

  factory Location.fromJson(Map<String, dynamic> json) => Location.all(
      json["id"].toString(),
      json["pincode"].toString(),
      json["area"].toString(),
      json["city"].toString(),
      json["district"].toString(),
      json["state"].toString()
  );

  Map<String, dynamic> toJson() => {
    "id": id,
    "pincode": pincode,
    "area": area,
    "city" : city,
    "district" : district,
    "state" : state
  };

  get state => _state;

  set state(value) {
    _state = value;
  }

  get district => _district;

  set district(value) {
    _district = value;
  }

  get city => _city;

  set city(value) {
    _city = value;
  }

  get area => _area;

  set area(value) {
    _area = value;
  }

  get pincode => _pincode;

  set pincode(value) {
    _pincode = value;
  }

  String get id => _id;

  set id(String value) {
    _id = value;
  }

  @override
  String toString() {
    return 'Location{_id: $_id, _pincode: $_pincode, _area: $_area, _city: $_city, _district: $_district, _state: $_state}';
  }
}