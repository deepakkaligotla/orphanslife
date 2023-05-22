import { StyleSheet, Text, View, Image, Dimensions } from "react-native";
import React from "react";
import MyHeader from "../header/MyHeader";
import MyFooter from "../footer/MyFooter";
import { ScrollView } from "react-native-gesture-handler";

function SignUp() {
  return (
    <>
      <MyHeader />
      <ScrollView>
        <View style={styles.container}>
          <Text style={styles.signupText}>SignUp</Text>
        </View>
      </ScrollView>
      <MyFooter />
    </>
  );
}

const styles = StyleSheet.create({
  container: {
    alignSelf: "center",
    alignItems: "center",
    backgroundColor: "black",
    marginLeft: 10,
    marginRight: 10,
    width: Dimensions.get('window').width - 30,
    height: 600,
  },
  signupText: {
    fontSize: 30,
    textAlign: "center",
    color: "#ffffff",
  },
  signInput: {
    borderBottomWidth: 0.5,
    height: 48,
    marginBottom: 30,
  },
  top: {
    width: 350,
    height: 100,
  },
  bottom: {
    width: 350,
    height: 150,
  },
});

export default SignUp;
