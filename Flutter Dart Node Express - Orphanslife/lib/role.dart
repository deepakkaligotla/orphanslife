import 'dart:convert';

List<Role> modelUserFromJson(String str) =>
    List<Role>.from(json.decode(str).map((x) => Role.fromJson(x)));

class Role {
  String _id, _role;

  Role.all(this._id,this._role);

  factory Role.fromJson(Map<String, dynamic> json) => Role.all(
      json["id"].toString(),
      json["role"].toString()
  );

  Map<String, dynamic> toJson() => {
    "id": id,
    "role": role
  };

  get role => _role;

  set role(value) {
    _role = value;
  }

  String get id => _id;

  set id(String value) {
    _id = value;
  }

  @override
  String toString() {
    return 'Role{_id: $_id, _role: $_role}';
  }
}