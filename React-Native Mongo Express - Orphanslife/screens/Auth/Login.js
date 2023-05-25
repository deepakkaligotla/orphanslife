import { Dimensions, StyleSheet, Text, ScrollView, Alert, Modal, Pressable, View, TextInput, TouchableOpacity } from "react-native";
import React from "react";
import { Checkbox } from 'react-native-paper';
import MyHeader from "../Auth/header/MyHeader";
import MyFooter from "../Auth/footer/MyFooter";
import axios from "axios";
import OTPTimer from '../extras/OTPTimer'

export default class Login extends React.Component {

  constructor(props) {
    super(props);
    this.state = {
      loggedInUser: [],
      email: "",
      password: "",
      confirmPassword: "",
      isSelected: true,
      otpWindow: false,
      forgotPasswordWindow: false,
      sentOtp: 0,
      otpSentTime: new Date(),
      validateOTPTime: new Date(),
      otpExpire: new Date(),
      enteredOtp: 0,
      apiToken: "",
      url: "http://192.168.0.14:4000/",
    };
    console.log('Constructor Called.');
  }

  componentDidMount() {
    console.log('componentDidMount called.');
  }

  shouldComponentUpdate(nextProp, nextState) {
    console.log('shouldComponentUpdate nextProp: '+nextProp);
    console.log('shouldComponentUpdate nextState: '+nextState);
    return true;
  }

  render() {
    console.log('render called.');
    return (<>
      <MyHeader />
      <ScrollView>
        <View style={styles.container}>

          {/* --------- Login Screen ----------- */}
          <Text style={styles.viewText}>Login</Text>
          <TextInput
            style={styles.input}
            onChangeText={(email) => this.setState({ email })}
            value={this.state.email}
            placeholder="Enter Registered Email"
          ></TextInput>
          <TextInput
            style={styles.input}
            onChangeText={(password) => this.setState({ password })}
            value={this.state.password}
            placeholder="Enter Registered Password"
            secureTextEntry={true}
            textContentType="password"
          ></TextInput>
          <TouchableOpacity style={{ flexDirection: 'row' }}>
            <Checkbox
              status={this.state.isSelected ? 'checked' : 'unchecked'}
              onPress={() => this.setState({ isSelected: !this.state.isSelected })}
              value={this.state.isSelected}
              color={'green'}
              uncheckedColor={'red'}

            />
            <Text style={styles.rememberMe}>Remember Me</Text>
          </TouchableOpacity>

          {/* --------- Forgot Password Window ----------- */}
          <View>
            <Modal
              animationType="slide"
              transparent={true}
              visible={this.state.forgotPasswordWindow}
              onRequestClose={() => {
                Alert.alert('Forgot Password Window closed.');
                this.setState({ forgotPasswordWindow: !forgotPasswordWindow })
              }}>
              <View style={styles.centeredView}>
                <View style={styles.forgotPasswordModalView}>
                  <Text style={{ color: 'white', alignSelf: 'center', backgroundColor: 'black', fontSize: 25 }}>Forgot Password</Text>
                  <TextInput
                    style={styles.input}
                    onChangeText={(email) => this.setState({ email })}
                    value={this.state.email}
                    placeholder="Enter Registered Email"
                  ></TextInput>
                  <Pressable
                    style={[styles.button, styles.buttonClose]}
                    onPress={() => setForgotPasswordWindow(!forgotPasswordWindow)}>
                    <Text style={styles.textStyle}>Get OTP</Text>
                  </Pressable>
                  <TextInput
                    style={styles.input}
                    placeholder="Enter OTP - X-X-X-X-X-X"
                    onChangeText={(enteredOtp) => this.setState({ enteredOtp })}
                  ></TextInput>
                  <OTPTimer/>
                  <TextInput
                    style={styles.input}
                    onChangeText={(password) => this.setState({ password })}
                    value={this.state.password}
                    placeholder="Enter New Passsword"
                    pattern={[
                      '^.{8,}$', // min 8 chars
                      '(?=.*\\d)', // number required
                      '(?=.*[A-Z])', // uppercase letter
                    ]}
                    onValidation={isValid => this.setState({ isValid })}
                    secureTextEntry={true}
                    textContentType="password"
                  ></TextInput>
                  <TextInput
                    style={styles.input}
                    onChangeText={(confirmPassword) => this.setState({ confirmPassword })}
                    value={this.state.confirmPassword}
                    placeholder="Confirm New Password"
                    secureTextEntry={true}
                    textContentType="password"
                  ></TextInput>
                  <TouchableOpacity onPress={() => this.setState({ forgotPasswordWindow: false })} style={[styles.changePasswordButton, styles.changePasswordButtonclose]}>
                    <Text style={styles.changePasswordButtonText}>Change Password</Text>
                  </TouchableOpacity>
                </View>
              </View>
            </Modal>
            <Pressable
              style={[styles.forgotPasswordButton, styles.forgotPasswordButtonOpen]}
              onPress={() => this.setState({ forgotPasswordWindow: true })}>
              <Text style={styles.forgotPasswordButtonText}>Forgot Password</Text>
            </Pressable>
          </View>

          {/* --------- OTP Window ----------- */}
          <View>
            <Modal
              animationType="slide"
              transparent={true}
              visible={this.state.otpWindow}
              onRequestClose={() => {
                Alert.alert('OTP Window closed.');
                this.setState({ otpWindow: !otpWindow })
              }}>
              <View style={styles.centeredView}>
                <View style={styles.modalView}>
                  <Text style={{ color: 'white', alignSelf: 'center', backgroundColor: 'black', fontSize: 25 }}>Enter OTP to Login</Text>
                  <View style={[styles.otpFlex, { flexDirection: 'row' }]}>
                    <TextInput
                      style={styles.otpInput}
                      placeholder="Enter OTP - X-X-X-X-X-X"
                      onChangeText={(enteredOtp) => this.setState({ enteredOtp })}
                    ></TextInput>
                  </View>
                  <OTPTimer/>

                  <TouchableOpacity onPress={this.handleValidateOtp.bind(this)} style={[styles.validateOtpButton, styles.validateOtpButtonClose]}>
                    <Text style={styles.validateOtpButtonText}>Validate OTP</Text>
                  </TouchableOpacity>
                </View>
              </View>
            </Modal>
            <TouchableOpacity onPress={this.handleLogin.bind(this)} style={[styles.loginButton, styles.loginButtonOpen]}>
              <Text style={styles.loginButtonText}>Login</Text>
            </TouchableOpacity>
          </View>
        </View>
      </ScrollView>
      <MyFooter />
    </>
    );
  }

  componentDidUpdate(prevProp, prevState) {
    console.log('componentDidUpdate prevProp: '+prevProp);
    console.log('componentDidUpdate prevState: '+prevState);
  }

  componentWillUnmount() {
    console.log('componentWillUnmount called.');
  }

  componentDidCatch(error, info) {
    console.log('componentDidCatch Error: '+ error);
    console.log('componentDidCatch Info: '+ info);
  }

  async loginAPI() {
    await axios.post(this.state.url,
      {
        email: this.state.email,
        password: this.state.password
      },
      {
        headers: {
          'x-auth-token': ''
        }
      })
      .then(response => {
        if (response.status === 200) {
          this.setState({ otpWindow: true })
          Alert.alert("Login OTP sent to Email");
          this.setState({ sentOtp: response.data.otp });
          this.setState({ apiToken: response.data.token });
          this.setState({ loggedInUser: response.data.data[0].loggedInUser });
          this.state.otpSentTime = (new Date().getMinutes() * 60) + new Date().getSeconds();
          console.log("Sent OTP : " + response.data.otp)
          return;
        } else if (response.status === 400 || response.status === 500) {
          throw new Error("Failed to fetch users");
        }
      }).catch(function (error) {
        if (axios.isCancel(error)) {
          Alert.alert("Something went wrong");
          return null;
        } else {
          Alert.alert(error);
          return null;
        }
      });
  }

  handleLogin() {
    if (this.state.email === '' || this.state.password === '') {
      Alert.alert("Enter Email & Password");
      return;
    } else {
      this.loginAPI()
    }
  };

  handleValidateOtp() {
    this.state.validateOTPTime = (new Date().getMinutes() * 60) + new Date().getSeconds();
    this.state.otpExpire = this.state.validateOTPTime - this.state.otpSentTime
    if (this.state.otpExpire < 120) {
      if (this.state.enteredOtp === 0) {
        Alert.alert("Need OTP");
        return;
      } else if (this.state.enteredOtp.toString().length > 6 || this.state.enteredOtp.toString().length < 6) {
        Alert.alert("OTP should be only 6 digits");
        return;
      } else if (this.state.sentOtp != this.state.enteredOtp) {
        Alert.alert("INVALID OTP");
      } else if (this.state.sentOtp == this.state.enteredOtp) {
        this.setState({ otpWindow: false })
        this.routeHomePage()
      }
    } else Alert.alert("OTP EXPIRED");
  }

  routeHomePage() {
    if(this.state.loggedInUser.role_id!=undefined) {
      if(this.state.loggedInUser.role_id==1) {
        Alert.alert(this.state.loggedInUser.admin_name+" Volunteer Home Page");
      }
      if(this.state.loggedInUser.role_id==2) {
        Alert.alert(this.state.loggedInUser.admin_name+" Guardian Home Page");
      }
      if(this.state.loggedInUser.role_id==3) {
        Alert.alert(this.state.loggedInUser.admin_name+" Super Admin Home Page");
      }
    } else if(this.state.loggedInUser.sponsor_name!=undefined) {
      Alert.alert(this.state.loggedInUser.sponsor_name+" Sponsor Home Page");
    }
  }
};

const styles = StyleSheet.create({
  container: {
    alignSelf: "center",
    alignItems: 'center',
    backgroundColor: 'black',
    marginLeft: 10,
    marginRight: 10,
    width: Dimensions.get("window").width - 30,
    height: 600
  },
  input: {
    textAlign: 'center',
    height: 40,
    width: 300,
    margin: 12,
    borderWidth: 1,
    padding: 10,
    backgroundColor: "#ffffff",
  },
  otpFlex: {
    alignSelf: 'center',
    marginTop: 10,
    marginBottom: 10
  },
  otpInput: {
    width: 200,
    height: 40,
    margin: 3,
    borderWidth: 1,
    textAlign: 'center',
    backgroundColor: "#ffffff",
    flex: 1
  },
  viewText: {
    fontSize: 30,
    textAlign: "center",
    color: "#ffffff",
  },
  signInput: {
    borderBottomWidth: 0.5,
    height: 48,
    marginBottom: 30,
  },
  rememberMe: {
    color: '#ffffff'
  },
  centeredView: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    marginTop: 2,
  },
  forgotPasswordModalView: {
    margin: 2,
    backgroundColor: '#545254',
    opacity: 0.9,
    borderRadius: 20,
    padding: 3,
    width: 350,
    height: 550,
    alignItems: 'center',
    justifyContent: 'center',
    alignSelf: 'center',
    shadowColor: '#000',
    shadowOffset: {
      width: 0,
      height: 2,
    },
    shadowOpacity: 0.25,
    shadowRadius: 4,
    elevation: 5,
  },
  modalView: {
    margin: 2,
    backgroundColor: '#545254',
    opacity: 0.9,
    borderRadius: 20,
    padding: 3,
    width: 350,
    height: 300,
    alignItems: 'center',
    justifyContent: 'center',
    alignSelf: 'center',
    shadowColor: '#000',
    shadowOffset: {
      width: 0,
      height: 2,
    },
    shadowOpacity: 0.25,
    shadowRadius: 4,
    elevation: 5,
  },
  forgotPasswordButton: {
    borderRadius: 20,
    padding: 3,
    elevation: 2,
    marginBottom: 5,
    alignSelf: 'flex-end'
  },
  forgotPasswordButtonOpen: {
    backgroundColor: '#545254',
    width: 150,
  },
  loginButton: {
    borderRadius: 20,
    padding: 10,
    elevation: 2,
  },
  loginButtonOpen: {
    backgroundColor: '#d1fad4',
    width: 300
  },
  loginButtonText: {
    color: 'black',
    fontWeight: 'bold',
    textAlign: 'center',
  },
  signUpButton: {
    marginTop: 80,
    borderRadius: 20,
    padding: 10,
    elevation: 2,
  },
  signUpButtonOpen: {
    backgroundColor: '#eeccff',
    width: 300
  },
  signupButtonText: {
    color: 'black',
    fontWeight: 'bold',
    textAlign: 'center',
  },
  validateOtpButton: {
    marginTop: 10,
    borderRadius: 20,
    padding: 10,
    elevation: 2,
  },
  validateOtpButtonClose: {
    alignSelf: 'center',
    backgroundColor: '#2196F3',
    width: 200
  },
  validateOtpButtonText: {
    color: 'white',
    fontWeight: 'bold',
    textAlign: 'center',
  },
  changePasswordButton: {
    marginTop: 20,
    borderRadius: 20,
    padding: 10,
    elevation: 2,
    alignSelf: 'center'
  },
  changePasswordButtonclose: {
    backgroundColor: '#2196F3',
    width: 300
  },
  changePasswordButtonText: {
    color: 'white',
    fontWeight: 'bold',
    textAlign: 'center',
  },
  forgotPasswordButtonText: {
    color: 'red',
    fontWeight: 'bold',
    textAlign: 'center',
  },
  textStyle: {
    color: 'white',
    fontWeight: 'bold',
    textAlign: 'center',
  },
});
