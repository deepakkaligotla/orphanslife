class User {
  String _email, _password;

  User(email, password){
    this._email = email;
    this._password = password;
  }

  get password => _password;

  set password(value) {
    _password = value;
  }

  String get email => _email;

  set email(String value) {
    _email = value;
  }

  @override
  String toString() {
    return 'User{_email: $_email, _password: $_password}';
  }
}