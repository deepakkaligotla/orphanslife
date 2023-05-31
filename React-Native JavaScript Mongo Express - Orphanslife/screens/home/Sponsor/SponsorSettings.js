import React from "react";
import { View, StyleSheet, Text, Dimensions } from 'react-native';

export default class SponsorSettings extends React.Component {

  render() {
    return (
      <>
        <View style={styles.insideSponsorSettingsContainer}>
        <Text style={{color:'white'}}>Sponsor Settings Page</Text>
        </View>
      </>
    );
  }
}

const styles = StyleSheet.create({
  insideSponsorSettingsContainer: {
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