import 'package:flutter/material.dart';
import 'package:Orphanslife/storage_service.dart';

void main() {
  WidgetsFlutterBinding.ensureInitialized();
  runApp(MaterialApp(
      debugShowCheckedModeBanner: false,
      home: StorageService()
  ));
}
