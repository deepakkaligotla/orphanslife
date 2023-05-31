import 'package:flutter/material.dart';
import 'package:flutter_svg/svg.dart';
import 'package:google_fonts/google_fonts.dart';
import 'package:shared_preferences/shared_preferences.dart';

class GuardianHome extends StatefulWidget {
  GuardianHome({Key key}) : super(key: key);

  @override
  _GuardianHomeState createState() => _GuardianHomeState();
}

class _GuardianHomeState extends State<GuardianHome> {
  final _formKey = GlobalKey<FormState>();
  int _selectedIndex = 0;
  static const List<Widget> _widgetOptions = <Widget>[
    Text('Home Page',
        style: TextStyle(fontSize: 35, fontWeight: FontWeight.bold)),
    Text('Actions Page',
        style: TextStyle(fontSize: 35, fontWeight: FontWeight.bold)),
    Text('Profile Page',
        style: TextStyle(fontSize: 35, fontWeight: FontWeight.bold)),
  ];

  void _onItemTapped(int index) {
    setState(() {
      _selectedIndex = index;
    });
  }

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
                        "Guardian Home",
                        style: GoogleFonts.pacifico(
                            fontWeight: FontWeight.bold,
                            fontSize: 30,
                            color: Colors.white),
                      ),
                      Center(
                        child: _widgetOptions.elementAt(_selectedIndex),
                      ),
                      Positioned(
                          child: BottomNavigationBar(
                              items: const <BottomNavigationBarItem>[
                                BottomNavigationBarItem(
                                    icon: Icon(Icons.home),
                                    label: 'Home',
                                    backgroundColor: Colors.black),
                                BottomNavigationBarItem(
                                    icon: Icon(Icons.edit),
                                    label: 'Actions',
                                    backgroundColor: Colors.black),
                                BottomNavigationBarItem(
                                  icon: Icon(Icons.person),
                                  label: 'Profile',
                                  backgroundColor: Colors.black,
                                ),
                              ],
                              type: BottomNavigationBarType.shifting,
                              currentIndex: _selectedIndex,
                              selectedItemColor: Colors.white,
                              iconSize: 40,
                              onTap: _onItemTapped,
                              elevation: 5))
                    ]),
              ),
            )
          ],
        ));
  }
}
