import React from "react";
import { View, StyleSheet, Text, Dimensions } from 'react-native';

export default class GuardianActions extends React.Component {

  render() {
    return (
      <>
        <View style={styles.insideGuardianActionsContainer}>
        <Text style={{color:'white'}}>Guardian Actions Page</Text>
        </View>
      </>
    );
  }
}

const styles = StyleSheet.create({
  insideGuardianActionsContainer: {
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