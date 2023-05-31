import 'dart:convert';

List<AdoptiveStatus> modelUserFromJson(String str) =>
    List<AdoptiveStatus>.from(json.decode(str).map((x) => AdoptiveStatus.fromJson(x)));

class AdoptiveStatus {
  String _id, _status;

  AdoptiveStatus.all(this._id,this._status);

  factory AdoptiveStatus.fromJson(Map<String, dynamic> json) => AdoptiveStatus.all(
    json["id"].toString(),
    json["status"].toString()
  );

  Map<String, dynamic> toJson() => {
    "id": _id,
    "status": _status
  };

  get status => _status;

  set status(value) {
    _status = value;
  }

  String get id => _id;

  set id(String value) {
    _id = value;
  }

  @override
  String toString() {
    return 'AdoptiveStatus{id: $_id, status: $_status}';
  }
}