import 'dart:convert';

List<OrphanageActivities> modelUserFromJson(String str) =>
    List<OrphanageActivities>.from(json.decode(str).map((x) => OrphanageActivities.fromJson(x)));

class OrphanageActivities {
  String _event_id, _orphanage_id, _details, _image_1, _image_2, _image_3, _image_4, _image_5;

  OrphanageActivities.all(this._event_id,this._orphanage_id,this._details,this._image_1,this._image_2,this._image_3,this._image_4,this._image_5);

  factory OrphanageActivities.fromJson(Map<String, dynamic> json) => OrphanageActivities.all(
      json["even_id"].toString(),
      json["orphanage_id"].toString(),
      json["details"].toString(),
      json["image_1"].toString(),
      json["image_2"].toString(),
      json["image_3"].toString(),
      json["image_4"].toString(),
      json["image_5"].toString()
  );

  Map<String, dynamic> toJson() => {
    "id": event_id,
    "amount": orphanage_id,
    "payment_status": details,
    "image_1" : image_1,
    "image_2" : image_2,
    "image_3" : image_3,
    "image_4" : image_4,
    "image_5" : image_5
  };

  get image_5 => _image_5;

  set image_5(value) {
    _image_5 = value;
  }

  get image_4 => _image_4;

  set image_4(value) {
    _image_4 = value;
  }

  get image_3 => _image_3;

  set image_3(value) {
    _image_3 = value;
  }

  get image_2 => _image_2;

  set image_2(value) {
    _image_2 = value;
  }

  get image_1 => _image_1;

  set image_1(value) {
    _image_1 = value;
  }

  get details => _details;

  set details(value) {
    _details = value;
  }

  get orphanage_id => _orphanage_id;

  set orphanage_id(value) {
    _orphanage_id = value;
  }

  String get event_id => _event_id;

  set event_id(String value) {
    _event_id = value;
  }

  @override
  String toString() {
    return 'OrphanageActivities{event_id: $_event_id, orphanage_id: $_orphanage_id, details: $_details, image_1: $_image_1,'
        ' image_2: $_image_2, image_3: $_image_3, image_4: $_image_4, image_5: $_image_5}';
  }
}