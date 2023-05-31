import 'dart:convert';
import 'package:flutter/material.dart';
import 'package:shared_preferences/shared_preferences.dart';
import 'package:Orphanslife/signin.dart';
import 'package:Orphanslife/AdminHome/admin_home.dart';
import 'package:Orphanslife/Models/admin.dart';
import 'package:Orphanslife/Models/sponsor.dart';

class StorageService extends StatefulWidget {
  StorageService({Key key}) : super(key: key);
  Admin loggedInAdmin;
  Sponsor loggedInSponsor;

  set logged_in_user(String loggedinUser) {
    SharedPreferences.getInstance().then((prefs) => {
      prefs.setString("logged_in_user", loggedinUser),
    });
  }

  set remember_me(bool remember_me) {
    SharedPreferences.getInstance().then((prefs) => {
      prefs.setBool("remember_me", remember_me),
    });
  }

  @override
  _storageService createState() => _storageService();
}

class _storageService extends State<StorageService> {

  Admin loggedInAdmin;
  List<Admin> adminList = [];
  Sponsor loggedInSponsor;

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Orphanslife',
      home: checkUserLoggedIn()!=true
          ? Signin()
          : AdminHome(),
    );
  }

  Future<bool> checkUserLoggedIn() async {
    final SharedPreferences prefs = await SharedPreferences.getInstance();
    print(prefs.getBool("remember_me"));
    print(prefs.getString("logged_in_user"));
    return prefs.getBool("remember_me");
  }
}
