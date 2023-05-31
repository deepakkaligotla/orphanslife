import 'package:flutter/material.dart';
import 'package:google_fonts/google_fonts.dart';
import 'package:shared_preferences/shared_preferences.dart';
import 'package:Orphanslife/signin.dart';
import 'package:Orphanslife/storage_service.dart';

class Choice {
  const Choice({ this.title, this.icon });
  final String title;
  final IconData icon;
}

const List<Choice> choices = <Choice>[
  Choice(title: 'Home', icon: Icons.home),
  Choice(title: 'Search', icon: Icons.search),
  Choice(title: 'Account Settings', icon: Icons.settings),
  Choice(title: 'Logout', icon: Icons.logout)
];

class AdminHome extends StatefulWidget {
  AdminHome({Key key}) : super(key: key);

  @override
  _AdminHomeState createState() => _AdminHomeState();
}

class _AdminHomeState extends State<AdminHome> {
  final _formKey = GlobalKey<FormState>();
  int _selectedIndex = 0;

  Choice _selectedChoice = choices[0]; // The app's "state".

  void _select(Choice choice) {
    setState(() { // Causes the app to rebuild with the new _selectedChoice.
      _selectedChoice = choice;
    });
  }

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
    print(prefs.getString('logged_in_user'));
  }

  void menu(String title) {
    if(title=='Account Settings') {
      print("Inside Account Settings");
    } else if(title=='Logout') {
      print("Inside Logout");
      StorageService().remember_me = false;
      StorageService().logged_in_user = "";
      Navigator.push(
          context, new MaterialPageRoute(builder: (context) => Signin()));
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        backgroundColor: Colors.black,
        appBar: AppBar(
          title: const Text('Admin Home'),
          actions: <Widget>[
            // action button
            IconButton(
              icon: Icon(choices[0].icon),
              onPressed: () {
                _select(choices[0]);
              },
            ),
            // action button
            IconButton(
              icon: Icon(choices[1].icon),
              onPressed: () {
                _select(choices[1]);
              },
            ),
            // overflow menu
            PopupMenuButton<Choice>(
              onSelected: _select,
              itemBuilder: (BuildContext context) {
                return choices.skip(2).map((Choice choice) {
                  return PopupMenuItem<Choice>(
                    value: choice,
                    child: Text(choice.title),
                    onTap: () {
                      menu(choice.title);
                    },
                  );
                }).toList();
              },
            ),
          ],
        ),
        body: Stack(
          children: [
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
                        "Admin Home",
                        style: GoogleFonts.pacifico(
                            fontWeight: FontWeight.bold,
                            fontSize: 30,
                            color: Colors.white),
                      ),
                      Center(child:
                      Row(
                        mainAxisAlignment: MainAxisAlignment.center,
                        crossAxisAlignment: CrossAxisAlignment.center,
                        children: [
                          Column(children: <Widget>[
                            Container(
                              margin: EdgeInsets.all(5),
                              color: Colors.white,
                              child: TextButton(
                                child: Text("Admin", style: TextStyle(fontSize: 15.0),textAlign: TextAlign.center),
                                onPressed: () {},
                              ),
                            ),
                            Container(
                              margin: EdgeInsets.all(5),
                              color: Colors.white,
                              child: TextButton(
                                child: Text("Adoptive\nStatus", style: TextStyle(fontSize: 15.0),textAlign: TextAlign.center),
                                onPressed: () {},
                              ),
                            ),
                            Container(
                              margin: EdgeInsets.all(5),
                              color: Colors.white,
                              child: TextButton(
                                child: Text("Adopt\nRequest", style: TextStyle(fontSize: 15.0),textAlign: TextAlign.center),
                                onPressed: () {},
                              ),
                            ),
                            Container(
                              margin: EdgeInsets.all(5),
                              color: Colors.white,
                              child: TextButton(
                                child: Text("Child", style: TextStyle(fontSize: 15.0),textAlign: TextAlign.center),
                                onPressed: () {},
                              ),
                            ),
                            Container(
                              margin: EdgeInsets.all(5),
                              color: Colors.white,
                              child: TextButton(
                                child: Text("Donation", style: TextStyle(fontSize: 15.0),textAlign: TextAlign.center),
                                onPressed: () {},
                              ),
                            ),
                          ],
                          ),
                          Row(
                            children: [
                              Column(children: <Widget>[
                                  Container(
                                    margin: EdgeInsets.all(5),
                                    color: Colors.white,
                                    child: TextButton(
                                      child: Text("Location", style: TextStyle(fontSize: 15.0),textAlign: TextAlign.center),
                                      onPressed: () {},
                                    ),
                                  ),
                                  Container(
                                    margin: EdgeInsets.all(5),
                                    color: Colors.white,
                                    child: TextButton(
                                      child: Text("Orphanage", style: TextStyle(fontSize: 15.0),textAlign: TextAlign.center),
                                      onPressed: () {},
                                    ),
                                  ),
                                  Container(
                                    margin: EdgeInsets.all(5),
                                    color: Colors.white,
                                    child: TextButton(
                                      child: Text("Orphanage\nActivities", style: TextStyle(fontSize: 15.0),textAlign: TextAlign.center),
                                      onPressed: () {},
                                    ),
                                  ),
                                  Container(
                                    margin: EdgeInsets.all(5),
                                    color: Colors.white,
                                    child: TextButton(
                                      child: Text("Role", style: TextStyle(fontSize: 15.0),textAlign: TextAlign.center),
                                      onPressed: () {},
                                    ),
                                  ),
                                  Container(
                                    margin: EdgeInsets.all(5),
                                    color: Colors.white,
                                    child: TextButton(
                                      child: Text("Sponsor", style: TextStyle(fontSize: 15.0),textAlign: TextAlign.center),
                                      onPressed: () {},
                                    ),
                                  ),
                                ],
                              )
                            ],
                          )
                        ],
                      ),
                      ),
                    ]),
              ),
            )
          ],
        ));
  }
}
