import React from "react";
import { View, StyleSheet, Text, Dimensions } from 'react-native';

export default class AdminSettings extends React.Component {

  render() {
    return (
      <>
        <View style={styles.insideAdminSettingsContainer}>
        <Text style={{color:'white'}}>Admin Settings Page</Text>
        </View>
      </>
    );
  }
}

const styles = StyleSheet.create({
  insideAdminSettingsContainer: {
    backgroundColor: "#fff",
    alignItems: "center",
    alignSelf: "center",
    backgroundColor: 'black',
    borderWidth: 1,
    borderColor: 'white',
    marginTop: 5,
    marginLeft: 5,
    marginRight: 5,
    marginBottom: 5,
    width: Dimensions.get('window').width - 30,
  }
})