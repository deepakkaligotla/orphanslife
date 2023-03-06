import 'dart:convert';

List<AdoptReq> modelUserFromJson(String str) =>
    List<AdoptReq>.from(json.decode(str).map((x) => AdoptReq.fromJson(x)));

class AdoptReq {
  String _req_no, _user_id, _admin_id, _child_id, _reason, _req_stage, _date_of_req, _last_checked, _req_comment, _next_check, _adopted;

  AdoptReq.all(this._req_no,this._user_id,this._admin_id,this._child_id,this._reason,this._req_stage,this._date_of_req,this._last_checked,this._req_comment,this._next_check,this._adopted);

  factory AdoptReq.fromJson(Map<String, dynamic> json) => AdoptReq.all(
      json["req_no"].toString(),
    json["user_id"].toString(),
    json["admin_id"].toString(),
    json["child_id"].toString(),
    json["reason"].toString(),
    json["req_stage"].toString(),
    json["date_of_req"].toString(),
    json["last_checked"].toString(),
    json["req_comment"].toString(),
    json["next_check"].toString(),
    json["adopted"].toString(),
  );

  Map<String, dynamic> toJson() => {
    "req_no": _req_no,
    "user_id": _user_id,
    "admin_id": _admin_id,
    "child_id" : _child_id,
    "reason" : _reason,
    "req_stage" : _req_stage,
    "date_of_req" : _date_of_req,
    "last_checked": _last_checked,
    "req_comment" : _req_comment,
    "next_check": _next_check,
    "adopted": _adopted,
  };

  get adopted => _adopted;

  set adopted(value) {
    _adopted = value;
  }

  get next_check => _next_check;

  set next_check(value) {
    _next_check = value;
  }

  get req_comment => _req_comment;

  set req_comment(value) {
    _req_comment = value;
  }

  get last_checked => _last_checked;

  set last_checked(value) {
    _last_checked = value;
  }

  get date_of_req => _date_of_req;

  set date_of_req(value) {
    _date_of_req = value;
  }

  get req_stage => _req_stage;

  set req_stage(value) {
    _req_stage = value;
  }

  get reason => _reason;

  set reason(value) {
    _reason = value;
  }

  get child_id => _child_id;

  set child_id(value) {
    _child_id = value;
  }

  get admin_id => _admin_id;

  set admin_id(value) {
    _admin_id = value;
  }

  get user_id => _user_id;

  set user_id(value) {
    _user_id = value;
  }

  String get req_no => _req_no;

  set req_no(String value) {
    _req_no = value;
  }

  @override
  String toString() {
    return 'AdoptReq{_req_no: $_req_no, _user_id: $_user_id, _admin_id: $_admin_id, _child_id: $_child_id, _reason: $_reason, _req_stage: $_req_stage, _date_of_req: $_date_of_req, _last_checked: $_last_checked, _req_comment: $_req_comment, _next_check: $_next_check, _adopted: $_adopted}';
  }
}