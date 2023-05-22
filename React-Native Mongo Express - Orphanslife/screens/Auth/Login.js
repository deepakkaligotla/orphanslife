import { Dimensions, StyleSheet, Text, ScrollView, Alert, Modal, Pressable, View, TextInput, TouchableOpacity } from "react-native";
import React, { useState } from "react";
import { Checkbox } from 'react-native-paper';
import MyHeader from "../header/MyHeader";
import MyFooter from "../footer/MyFooter";
import axios from "axios";

function Login() {
  const [email, setEmail] = React.useState("");
  const [password, setPassword] = React.useState("");
  const [confirmPassword, setConfirmPassword] = React.useState("");
  const [isSelected, setSelection] = React.useState(true);
  const [otpWindow, setOtpWindow] = React.useState(false);
  const [forgotPasswordWindow, setForgotPasswordWindow] = React.useState(false);
  const [OTP, setOTP] = React.useState(0);
  const url = "http://192.168.0.14:4000/"
  const [loggedInUser, setLoggedInUser] = React.useState();
  const [otp, setOtp] = React.useState();
  const [apiToken, setApiToken] = React.useState();

  const handleLogin = async () => {
    if (email === '' || password === '') {
      alert("All fields are required");
      return;
    }
    try {
      const response = await axios.post(url, { email, password });
      if (response.status === 200) {
        setOtpWindow(true)
        alert("Login OTP sent to Email");
        setOTP(response.data.otp)
        setApiToken(response.data.token)
        console.log(response.data.data[0].loggedInUser)
        return;
      } else {
        throw new Error("Failed to fetch users");
      }
    } catch (error) {
      if (axios.isCancel(error)) {
        alert("Something went wrong");
      } else {
        alert(error);
      }
    }
  };

  const handleValidateOtp = async () => {
    if (otp === '' || otp < 6) {
      alert("Invalid OTP");
      return;
    } setOtpWindow(!otpWindow)
  }

  return (<>
    <MyHeader />
    <ScrollView>
      <View style={styles.container}>

        {/* --------- Login Screen ----------- */}
        <Text style={styles.viewText}>Login</Text>
        <TextInput
          style={styles.input}
          onChangeText={setEmail}
          value={email}
          placeholder="Enter Registered Email"
        ></TextInput>
        <TextInput
          style={styles.input}
          onChangeText={setPassword}
          value={password}
          placeholder="Enter Registered Password"
          secureTextEntry={true}
          textContentType="password"
        ></TextInput>
        <TouchableOpacity style={{ flexDirection: 'row' }}>
          <Checkbox
            status={isSelected ? 'checked' : 'unchecked'}
            onPress={() => {
              setSelection(!isSelected);
            }}
            value={isSelected}
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
            visible={forgotPasswordWindow}
            onRequestClose={() => {
              Alert.alert('Forgot Password Window closed.');
              setForgotPasswordWindow(!forgotPasswordWindow);
            }}>
            <View style={styles.centeredView}>
              <View style={styles.forgotPasswordModalView}>
                <Text style={{ color: 'white', alignSelf: 'center', backgroundColor: 'black', fontSize: 25 }}>Forgot Password</Text>
                <TextInput
                  style={styles.input}
                  onChangeText={setEmail}
                  value={email}
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
                  onChangeText={setOTP}
                ></TextInput>
                <TextInput
                  style={styles.input}
                  onChangeText={setPassword}
                  value={password}
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
                  onChangeText={setConfirmPassword}
                  value={confirmPassword}
                  placeholder="Confirm New Password"
                  secureTextEntry={true}
                  textContentType="password"
                ></TextInput>
                <TouchableOpacity onPress={() => { setForgotPasswordWindow(!forgotPasswordWindow) }} style={[styles.changePasswordButton, styles.changePasswordButtonclose]}>
                  <Text style={styles.changePasswordButtonText}>Change Password</Text>
                </TouchableOpacity>
              </View>
            </View>
          </Modal>
          <Pressable
            style={[styles.forgotPasswordButton, styles.forgotPasswordButtonOpen]}
            onPress={() => setForgotPasswordWindow(true)}>
            <Text style={styles.forgotPasswordButtonText}>Forgot Password</Text>
          </Pressable>
        </View>

        {/* --------- OTP Window ----------- */}
        <View>
          <Modal
            animationType="slide"
            transparent={true}
            visible={otpWindow}
            onRequestClose={() => {
              Alert.alert('OTP Window closed.');
              setOtpWindow(!otpWindow);
            }}>
            <View style={styles.centeredView}>
              <View style={styles.modalView}>
                <Text style={{ color: 'white', alignSelf: 'center', backgroundColor: 'black', fontSize: 25 }}>Enter OTP to Login</Text>
                <View style={[styles.otpFlex, { flexDirection: 'row' }]}>
                  <TextInput
                    style={styles.otpInput}
                    placeholder="Enter OTP - X-X-X-X-X-X"
                    onChangeText={setOTP}
                  ></TextInput>
                </View>
                <TouchableOpacity onPress={() => { handleValidateOtp() }} style={[styles.validateOtpButton, styles.validateOtpButtonClose]}>
                  <Text style={styles.validateOtpButtonText}>Validate OTP</Text>
                </TouchableOpacity>
              </View>
            </View>
          </Modal>
          <TouchableOpacity onPress={() => { handleLogin() }} style={[styles.loginButton, styles.loginButtonOpen]}>
            <Text style={styles.loginButtonText}>Login</Text>
          </TouchableOpacity>
        </View>
      </View>
    </ScrollView>
    <MyFooter />
  </>
  );
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
    height: 400,
    justifyContent: 'center',
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
    height: 250,
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
  modalText: {
    marginBottom: 3,
    textAlign: 'center',
  }
});

export default Login;
