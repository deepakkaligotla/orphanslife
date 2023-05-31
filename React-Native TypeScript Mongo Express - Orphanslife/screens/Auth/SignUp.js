import {
  StyleSheet,
  Text,
  View,
  TextInput,
  ScrollView,
  Pressable,
  Dimensions,
  Modal,
  TouchableOpacity,
  FlatList,
} from 'react-native';
import React from 'react';
import Icon from 'react-native-vector-icons/AntDesign';
import MyHeader from '../Auth/header/MyHeader';
import MyFooter from '../Auth/footer/MyFooter';

export default class SignUp extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      whoAmI: ['Sponsor', 'Admin'],
      roles: ['Volunteer', 'Guardian'],
      genders: ['Male', 'Female', 'Others'],
      personModal: false,
      adminRoleModal: false,
      genderModal: false,
      selectedPerson: 'Select One',
      selectedAdminRole: 'Role',
      selectedGender: 'Gender',
      name: '',
      mobile: '',
      email: '',
      password: '',
      confirmPassword: '',
      dropdownTop: 0,
      adminRoleDropdownTop: 0,
      genderDropdownTop: 0,
    };
  }

  componentDidMount() {}

  shouldComponentUpdate(nextProp, nextState) {
    return true;
  }

  render() {
    return (
      <>
        <MyHeader />
        <ScrollView>
          <View style={styles.container}>
            <Text style={styles.signupText}>SignUp</Text>

            {/* Selecting person dropdown */}
            <View>
              <Modal
                visible={this.state.personModal}
                transparent
                animationType="slide"
                onRequestClose={() => this.setState({personModal: false})}>
                <TouchableOpacity style={styles.overlay}>
                  <View
                    style={[styles.dropdown, {top: this.state.dropdownTop}]}>
                    <FlatList
                      data={this.state.whoAmI}
                      renderItem={({item}) => (
                        <TouchableOpacity
                          style={styles.item}
                          onPress={() => {
                            this.setState({personModal: false});
                            this.setState({selectedPerson: item});
                          }}>
                          <Text style={{color: '#fff'}}>{item}</Text>
                        </TouchableOpacity>
                      )}
                      keyExtractor={index => index.toString()}
                    />
                  </View>
                </TouchableOpacity>
              </Modal>
            </View>
            <View style={styles.dropdownView}>
              <Pressable onPress={() => this.setState({personModal: true})}>
                <Icon size={30} color="white" name="circledown" />
              </Pressable>
              <Text style={{color: 'white', marginRight: 10, fontSize: 20}}>
                {this.state.selectedPerson}
              </Text>
            </View>

            {/* Selecting Admin_role dropdown */}
            <View>
              <Modal
                visible={this.state.adminRoleModal}
                transparent
                animationType="slide"
                onRequestClose={() => this.setState({adminRoleModal: false})}>
                <TouchableOpacity style={styles.adminRoleoverlay}>
                  <View
                    style={[
                      styles.adminRoleDropdown,
                      {top: this.state.adminRoleDropdownTop},
                    ]}>
                    <FlatList
                      data={this.state.roles}
                      renderItem={({item}) => (
                        <TouchableOpacity
                          style={styles.adminRoleitem}
                          onPress={() => {
                            this.setState({adminRoleModal: false});
                            this.setState({selectedAdminRole: item});
                          }}>
                          <Text style={{color: '#fff'}}>{item}</Text>
                        </TouchableOpacity>
                      )}
                      keyExtractor={index => index.toString()}
                    />
                  </View>
                </TouchableOpacity>
              </Modal>
            </View>
            <View style={styles.adminRoleDropdownView}>
              <Pressable onPress={() => this.setState({adminRoleModal: true})}>
                <Icon size={30} color="white" name="circledown" />
              </Pressable>
              <Text style={{color: 'white', marginRight: 10, fontSize: 20}}>
                {this.state.selectedAdminRole}
              </Text>
            </View>

            <TextInput
              style={styles.input}
              onChangeText={name => this.setState({name})}
              value={this.state.name}
              placeholder="Enter Full Name"
              secureTextEntry={false}
            />

            {/* Date of Birth */}

            {/* Selecting Gender dropdown */}
            <View>
              <Modal
                visible={this.state.genderModal}
                transparent
                animationType="slide"
                onRequestClose={() => this.setState({genderModal: false})}>
                <TouchableOpacity style={styles.genderoverlay}>
                  <View
                    style={[
                      styles.genderDropdown,
                      {top: this.state.genderDropdownTop},
                    ]}>
                    <FlatList
                      data={this.state.genders}
                      renderItem={({item}) => (
                        <TouchableOpacity
                          style={styles.genderItem}
                          onPress={() => {
                            this.setState({genderModal: false});
                            this.setState({selectedGender: item});
                          }}>
                          <Text style={{color: '#fff'}}>{item}</Text>
                        </TouchableOpacity>
                      )}
                      keyExtractor={index => index.toString()}
                    />
                  </View>
                </TouchableOpacity>
              </Modal>
            </View>
            <View style={styles.genderDropdownView}>
              <Pressable onPress={() => this.setState({genderModal: true})}>
                <Icon size={30} color="white" name="circledown" />
              </Pressable>
              <Text style={{color: 'white', marginRight: 10, fontSize: 20}}>
                {this.state.selectedGender}
              </Text>
            </View>

            <TextInput
              style={styles.input}
              onChangeText={mobile => this.setState({mobile})}
              value={this.state.mobile}
              placeholder="Enter Mobile Number"
              secureTextEntry={false}
            />
            <TextInput
              style={styles.input}
              onChangeText={email => this.setState({email})}
              value={this.state.email}
              placeholder="Enter Email"
            />
            <TextInput
              style={styles.input}
              onChangeText={password => this.setState({password})}
              value={this.state.password}
              placeholder="Enter Password"
              secureTextEntry={true}
              textContentType="password"
            />
            <TextInput
              style={styles.input}
              onChangeText={confirmPassword => this.setState({confirmPassword})}
              value={this.state.confirmPassword}
              placeholder="Confirm Password"
              secureTextEntry={true}
              textContentType="password"
            />
          </View>
        </ScrollView>
        <MyFooter />
      </>
    );
  }

  componentDidUpdate(prevProp, prevState) {}

  componentWillUnmount() {}

  componentDidCatch(error, info) {
    console.log('componentDidCatch Error: ' + error);
    console.log('componentDidCatch Info: ' + info);
  }
}

const styles = StyleSheet.create({
  container: {
    alignSelf: 'center',
    alignItems: 'center',
    backgroundColor: 'black',
    marginLeft: 10,
    marginRight: 10,
    width: Dimensions.get('window').width - 30,
    height: 600,
  },
  signupText: {
    fontSize: 30,
    textAlign: 'center',
    color: '#ffffff',
  },
  input: {
    textAlign: 'center',
    height: 40,
    width: 300,
    margin: 12,
    borderWidth: 1,
    padding: 10,
    backgroundColor: '#ffffff',
  },
  button: {
    flexDirection: 'row',
    alignItems: 'center',
    backgroundColor: '#efefef',
    height: 50,
    zIndex: 1,
  },
  buttonText: {
    flex: 1,
    textAlign: 'center',
  },
  icon: {
    marginRight: 10,
  },
  overlay: {
    width: '100%',
    height: '70%',
    justifyContent: 'center',
    alignItems: 'center',
  },
  dropdown: {
    backgroundColor: '#000000',
    width: 130,
    shadowRadius: 4,
    shadowOffset: {height: 4, width: 0},
    shadowOpacity: 0.5,
    borderColor: 'white',
    borderWidth: 1,
  },
  dropdownView: {
    flexDirection: 'row-reverse',
    borderColor: 'white',
    borderWidth: 1,
    width: 150,
    marginTop: 10,
  },
  item: {
    paddingHorizontal: 10,
    paddingVertical: 10,
    borderBottomWidth: 1,
    borderColor: 'white',
    borderWidth: 1,
  },
  adminRoleoverlay: {
    width: '100%',
    height: '80%',
    justifyContent: 'center',
    alignItems: 'center',
  },
  adminRoleDropdown: {
    backgroundColor: '#000000',
    width: 130,
    shadowRadius: 4,
    shadowOffset: {height: 4, width: 0},
    shadowOpacity: 0.5,
    borderColor: 'white',
    borderWidth: 1,
  },
  adminRoleDropdownView: {
    flexDirection: 'row-reverse',
    borderColor: 'white',
    borderWidth: 1,
    width: 150,
    marginTop: 10,
  },
  adminRoleitem: {
    paddingHorizontal: 10,
    paddingVertical: 10,
    borderBottomWidth: 1,
    borderColor: 'white',
    borderWidth: 1,
  },
  genderoverlay: {
    width: '100%',
    height: '80%',
    justifyContent: 'center',
    alignItems: 'center',
  },
  genderDropdown: {
    backgroundColor: '#000000',
    width: 130,
    shadowRadius: 4,
    shadowOffset: {height: 4, width: 0},
    shadowOpacity: 0.5,
    borderColor: 'white',
    borderWidth: 1,
  },
  genderDropdownView: {
    flexDirection: 'row-reverse',
    borderColor: 'white',
    borderWidth: 1,
    width: 150,
    marginTop: 10,
  },
  genderItem: {
    paddingHorizontal: 10,
    paddingVertical: 10,
    borderBottomWidth: 1,
    borderColor: 'white',
    borderWidth: 1,
  },
});
