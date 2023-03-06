import 'package:flutter/material.dart';
import 'package:google_fonts/google_fonts.dart';
import 'package:shared_preferences/shared_preferences.dart';

class VolunteerHome extends StatefulWidget {
  VolunteerHome({Key key}) : super(key: key);

  @override
  _VolunteerHomeState createState() => _VolunteerHomeState();
}

class _VolunteerHomeState extends State<VolunteerHome> {

  @override
  void initState() {
    super.initState();
    _loadCounter();
  }

  _loadCounter() async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    print(prefs.get('logged_in_user'));
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Colors.black,
      body: Center(
        child: Text("Volunteer Home",
            style: GoogleFonts.pacifico(
                fontWeight: FontWeight.bold, fontSize: 50, color: Colors.white)),
      ),
    );
  }
}