import 'dart:convert';
import 'package:Orphanslife/Models/sponsor.dart';
import 'package:Orphanslife/Models/admin.dart';
import 'package:Orphanslife/AdminHome/admin_home.dart';
import 'package:Orphanslife/GuardianHome/guardian_home.dart';
import 'package:Orphanslife/signup.dart';
import 'package:Orphanslife/SponsorHome/sponsor_home.dart';
import 'package:Orphanslife/user.dart';
import 'package:Orphanslife/VolunteerHome/volunteer_home.dart';
import 'package:flutter/material.dart';
import 'package:flutter_svg/svg.dart';
import 'package:google_fonts/google_fonts.dart';
import 'package:http/http.dart' as http;
import 'package:Orphanslife/storage_service.dart';

class Signin extends StatefulWidget {
  Signin({Key key}) : super(key: key);

  @override
  _SigninState createState() => _SigninState();
}

class _SigninState extends State<Signin> {
  final _formKey = GlobalKey<FormState>();
  bool isCheckedRememberMe = false;
  var loading = false;
  var otp;

  actionRemeberMe(bool value) {
    isCheckedRememberMe = value;
    if (value) {
      StorageService().remember_me = value;
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

  Future login() async {
    Future<Null> getData() async {
      setState(() {
        loading = true;
      });
    }

    var res = await http.post("http://192.168.0.14:4000/",
        headers: <String, String>{
          'Context-Type': 'application/json;charSet=UTF-8'
        },
        body: <String, String>{
          'email': user.email,
          'password': user.password
        });

    var jsonObj = jsonDecode(res.body);
    if (jsonObj['ok']==true) {
      if(jsonObj['data'][0]['loggedInUser']['admin_name']==null) {
        Sponsor sponsor;
        otp = jsonObj['otp'];
        setState(() {
          sponsor = Sponsor.fromJson(jsonObj['data'][0]['loggedInUser']);
          loading = false;
        });
        StorageService().logged_in_user = jsonObj['data'][0]['loggedInUser'].toString();
        Navigator.push(
            context, new MaterialPageRoute(builder: (context) => SponsorHome()));
      } else if(jsonObj['data'][0]['loggedInUser']['admin_name']!=null) {
        Admin admin;
        otp = jsonObj['otp'];
        setState(() {
          admin = Admin.fromJson(jsonObj['data'][0]['loggedInUser']);
          loading = false;
        });
        StorageService().logged_in_user = jsonObj['data'][0]['loggedInUser'].toString();

        switch (admin.role_id) {
          case 1:
            {
              Navigator.push(context,
                  new MaterialPageRoute(builder: (context) => VolunteerHome()));
            }
            break;

          case 2:
            {
              Navigator.push(context,
                  new MaterialPageRoute(builder: (context) => GuardianHome()));
            }
            break;

          case 3:
            {
              Navigator.push(context,
                  new MaterialPageRoute(builder: (context) => AdminHome()));
            }
            break;

          default:
            {
              return;
            }
            break;
        }
      } else {
        return;
      }
    } else {
      return;
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        backgroundColor: Colors.black,
        body: Stack(
          children: [
            Container(
              alignment: Alignment.center,
              child: Form(
                key: _formKey,
                child: Column(
                  mainAxisAlignment: MainAxisAlignment.center,
                  crossAxisAlignment: CrossAxisAlignment.center,
                  children: [
                    Image.asset(
                      'images/donate.png',
                      width: 400,
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
                        child: MaterialButton(
                            color: Colors.white,
                            shape: RoundedRectangleBorder(
                                borderRadius: BorderRadius.circular(16.0)),
                            onPressed: () {
                              if (_formKey.currentState.validate()) {
                                login();
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
                        )
                    ),
                    Image.asset(
                      'images/kids_jumping.gif',
                      width: 400,
                      height: 150,
                    ),
                  ],
                ),
              ),
            ),
          ],
        )
    );
  }

}
