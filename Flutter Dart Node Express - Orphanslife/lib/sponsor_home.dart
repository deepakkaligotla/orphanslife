import 'package:flutter/material.dart';
import 'package:google_fonts/google_fonts.dart';
import 'package:shared_preferences/shared_preferences.dart';

class SponsorHome extends StatefulWidget {
  SponsorHome({Key key}) : super(key: key);

  @override
  _SponsorHomeState createState() => _SponsorHomeState();
}

class _SponsorHomeState extends State<SponsorHome> {

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
        child: Text("Sponsor Home",
            style: GoogleFonts.pacifico(
                fontWeight: FontWeight.bold, fontSize: 50, color: Colors.white)),
      ),
    );
  }
}