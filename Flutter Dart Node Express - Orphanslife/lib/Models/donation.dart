import 'dart:convert';

List<Donation> modelUserFromJson(String str) =>
    List<Donation>.from(json.decode(str).map((x) => Donation.fromJson(x)));

class Donation {
  String _id, _amount, _payment_status, _user_id, _created_at, _updated_at;

  Donation.all(this._id,this._amount,this._payment_status,this._user_id,this._created_at,this._updated_at);

  factory Donation.fromJson(Map<String, dynamic> json) => Donation.all(
    json["id"].toString(),
    json["amount"].toString(),
    json["payment_status"].toString(),
    json["user_id"].toString(),
    json["created_at"].toString(),
    json["updated_at"].toString()
  );

  Map<String, dynamic> toJson() => {
    "id": id,
    "amount": amount,
    "payment_status": payment_status,
    "user_id" : user_id,
    "created_at" : created_at,
    "updated_at" : updated_at
  };

  get updated_at => _updated_at;

  set updated_at(value) {
    _updated_at = value;
  }

  get created_at => _created_at;

  set created_at(value) {
    _created_at = value;
  }

  get user_id => _user_id;

  set user_id(value) {
    _user_id = value;
  }

  get payment_status => _payment_status;

  set payment_status(value) {
    _payment_status = value;
  }

  get amount => _amount;

  set amount(value) {
    _amount = value;
  }

  String get id => _id;

  set id(String value) {
    _id = value;
  }

  @override
  String toString() {
    return 'Donation{id: $_id, amount: $_amount, payment_status: $_payment_status, user_id: $_user_id, created_at: $_created_at,'
        ' updated_at: $_updated_at}';
  }
}