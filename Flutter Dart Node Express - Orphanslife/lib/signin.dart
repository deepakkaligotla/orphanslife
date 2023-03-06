import 'dart:convert';
import 'package:Orphanslife/admin.dart';
import 'package:Orphanslife/admin_home.dart';
import 'package:Orphanslife/guardian_home.dart';
import 'package:Orphanslife/signup.dart';
import 'package:Orphanslife/sponsor.dart';
import 'package:Orphanslife/sponsor_home.dart';
import 'package:Orphanslife/user.dart';
import 'package:Orphanslife/volunteer_home.dart';
import 'package:flutter/material.dart';
import 'package:flutter_svg/svg.dart';
import 'package:google_fonts/google_fonts.dart';
import 'package:http/http.dart' as http;
import 'package:shared_preferences/shared_preferences.dart';

class Signin extends StatefulWidget {
  Signin({Key key}) : super(key: key);

  @override
  _SigninState createState() => _SigninState();
}

class _SigninState extends State<Signin> {
  final _formKey = GlobalKey<FormState>();
  bool isCheckedRememberMe = false;
  List<Admin> adminList = [];
  List<Sponsor> sponsorList = [];
  var loading = false;

  actionRemeberMe(bool value) {
    isCheckedRememberMe = value;
    if (value) {
      SharedPreferences.getInstance().then(
        (prefs) {
          prefs.setBool("remember_me", value);
        },
      );
      setState(() {
        isCheckedRememberMe = value;
      });
    }
  }

  @override
  void initState() {
    super.initState();
  }

  User user = User("", "");

  Future adminLogin() async {
    Future<Null> getData() async {
      setState(() {
        loading = true;
      });
    }

    Admin admin;
    var res = await http.post("http://orphanslife.in:4000/adminlogin",
        headers: <String, String>{
          'Context-Type': 'application/json;charSet=UTF-8'
        },
        body: <String, String>{
          'email': user.email,
          'password': user.password
        });
    var jsonObj = jsonDecode(res.body);
    var jsonData = new List();
    jsonData = jsonObj['data'];
    if (!jsonData.isEmpty) {
      setState(() {
        adminList.clear();
        adminList.add(Admin.fromJson(jsonData[0]));
        admin = adminList.first;
        loading = false;
      });
      SharedPreferences.getInstance().then(
        (prefs) {
          prefs.setString('logged_in_user', admin.toString());
        },
      );
      switch (admin.role_id) {
        case "1":
          {
            Navigator.push(context,
                new MaterialPageRoute(builder: (context) => VolunteerHome()));
          }
          break;

        case "2":
          {
            Navigator.push(context,
                new MaterialPageRoute(builder: (context) => GuardianHome()));
          }
          break;

        case "3":
          {
            Navigator.push(context,
                new MaterialPageRoute(builder: (context) => AdminHome()));
          }
          break;

        default:
          {
            sponsorLogin();
          }
          break;
      }
    } else if (jsonData.isEmpty) {
      sponsorLogin();
    }
  }

  Future sponsorLogin() async {
    Future<Null> getData() async {
      setState(() {
        loading = true;
      });
    }

    Sponsor sponsor;
    var res = await http.post("http://orphanslife.in:4000/sponsorlogin",
        headers: <String, String>{
          'Context-Type': 'application/json;charSet=UTF-8'
        },
        body: <String, String>{
          'email': user.email,
          'password': user.password
        });
    var jsonObj = jsonDecode(res.body);
    var jsonData = new List();
    jsonData = jsonObj['data'];
    if (!jsonData.isEmpty) {
      setState(() {
        sponsorList.clear();
        sponsorList.add(Sponsor.fromJson(jsonData[0]));
        sponsor = sponsorList.first;
        loading = false;
      });
      SharedPreferences.getInstance().then(
        (prefs) {
          prefs.setString('logged_in_user', sponsor.toString());
        },
      );
      Navigator.push(
          context, new MaterialPageRoute(builder: (context) => SponsorHome()));
    } else if (jsonData[0] = null) {
      //toast
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        backgroundColor: Colors.black,
        body: Stack(
          children: [
            Positioned(
                top: 0,
                child: SvgPicture.asset(
                  'images/top.svg',
                  width: 400,
                  height: 150,
                )),
            Container(
              alignment: Alignment.center,
              child: Form(
                key: _formKey,
                child: Column(
                  mainAxisAlignment: MainAxisAlignment.start,
                  crossAxisAlignment: CrossAxisAlignment.center,
                  children: [
                    SizedBox(
                      height: 150,
                    ),
                    Text(
                      "Orphanslife",
                      style: GoogleFonts.pacifico(
                          fontWeight: FontWeight.bold,
                          fontSize: 30,
                          color: Colors.white),
                    ),
                    SizedBox(
                      height: 25,
                    ),
                    Padding(
                      padding: const EdgeInsets.all(16.0),
                      child: TextFormField(
                        style: TextStyle(color: Colors.white),
                        controller: TextEditingController(text: user.email),
                        onChanged: (value) {
                          user.email = value;
                        },
                        validator: (value) {
                          if (value.isEmpty) {
                            return 'Enter something';
                          } else if (RegExp(
                                  r"^[a-zA-Z0-9.a-zA-Z0-9.!#$%&'*+-/=?^_`{|}~]+@[a-zA-Z0-9]+\.[a-zA-Z]+")
                              .hasMatch(value)) {
                            return null;
                          } else {
                            return 'Enter valid email';
                          }
                        },
                        decoration: InputDecoration(
                            icon: Icon(
                              Icons.email,
                              color: Colors.white,
                            ),
                            hintText: 'Enter Email',
                            hintStyle: TextStyle(color: Colors.white),
                            enabledBorder: OutlineInputBorder(
                                borderRadius: BorderRadius.circular(16),
                                borderSide: BorderSide(color: Colors.white)),
                            focusedBorder: OutlineInputBorder(
                                borderRadius: BorderRadius.circular(16),
                                borderSide: BorderSide(color: Colors.white)),
                            errorBorder: OutlineInputBorder(
                                borderRadius: BorderRadius.circular(16),
                                borderSide: BorderSide(color: Colors.red)),
                            focusedErrorBorder: OutlineInputBorder(
                                borderRadius: BorderRadius.circular(16),
                                borderSide: BorderSide(color: Colors.red))),
                      ),
                    ),
                    Padding(
                      padding: const EdgeInsets.all(16.0),
                      child: TextFormField(
                        style: TextStyle(color: Colors.white),
                        controller: TextEditingController(text: user.password),
                        onChanged: (value) {
                          user.password = value;
                        },
                        validator: (value) {
                          if (value.isEmpty) {
                            return 'Enter something';
                          }
                          return null;
                        },
                        obscureText: true,
                        decoration: InputDecoration(
                            icon: Icon(
                              Icons.vpn_key,
                              color: Colors.white,
                            ),
                            hintText: 'Enter Password',
                            hintStyle: TextStyle(color: Colors.white),
                            enabledBorder: OutlineInputBorder(
                                borderRadius: BorderRadius.circular(16),
                                borderSide: BorderSide(color: Colors.white)),
                            focusedBorder: OutlineInputBorder(
                                borderRadius: BorderRadius.circular(16),
                                borderSide: BorderSide(color: Colors.white)),
                            errorBorder: OutlineInputBorder(
                                borderRadius: BorderRadius.circular(16),
                                borderSide: BorderSide(color: Colors.red)),
                            focusedErrorBorder: OutlineInputBorder(
                                borderRadius: BorderRadius.circular(16),
                                borderSide: BorderSide(color: Colors.red))),
                      ),
                    ),
                    SizedBox(
                      height: 10,
                    ),
                    Row(mainAxisAlignment: MainAxisAlignment.start, children: [
                      Checkbox(
                        checkColor: Colors.black,
                        activeColor: Colors.white,
                        value: this.isCheckedRememberMe,
                        onChanged: (bool value) {
                          setState(() {
                            this.isCheckedRememberMe = value;
                            actionRemeberMe(value);
                          });
                        },
                      ),
                      SizedBox(width: 10.0),
                      Text("Remember Me",
                          style: TextStyle(
                            color: Colors.white,
                            fontSize: 12,
                          ))
                    ]),
                    Padding(
                      padding: EdgeInsets.fromLTRB(55, 16, 16, 0),
                      child: Container(
                        height: 50,
                        width: 400,
                        child: FlatButton(
                            color: Colors.white,
                            shape: RoundedRectangleBorder(
                                borderRadius: BorderRadius.circular(16.0)),
                            onPressed: () {
                              if (_formKey.currentState.validate()) {
                                adminLogin();
                              } else {
                                print("not ok");
                              }
                            },
                            child: Text(
                              "Signin",
                              style:
                                  TextStyle(color: Colors.black, fontSize: 20),
                            )),
                      ),
                    ),
                    Padding(
                        padding: const EdgeInsets.fromLTRB(95, 20, 0, 0),
                        child: Row(
                          children: [
                            Text(
                              "Not have Account ? ",
                              style: TextStyle(
                                  color: Colors.white,
                                  fontWeight: FontWeight.bold,
                                  fontSize: 20),
                            ),
                            InkWell(
                              onTap: () {
                                Navigator.push(
                                    context,
                                    new MaterialPageRoute(
                                        builder: (context) => Signup()));
                              },
                              child: Text(
                                "Signup",
                                style: TextStyle(
                                    color: Colors.purple,
                                    fontWeight: FontWeight.bold,
                                    fontSize: 20),
                              ),
                            ),
                          ],
                        ))
                  ],
                ),
              ),
            )
          ],
        ));
  }
}
