import * as React from 'react-native';
import { View, Image, StyleSheet, Text, Dimensions } from 'react-native';

const MyHeader = () => {

  return (
    <>
      <View style={styles.insideHeaderContainer}>
        <Image
          style={styles.topImage}
          source={require("../../assets/images/donate.png")}
        />
      <Text style={{color:'white'}}>Orphanage Management System</Text>
      </View>
    </>
  );
}

const styles = StyleSheet.create({
  insideHeaderContainer: {
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
  },
  topImage: {
    width: 150,
    height: 50,
  },
})

export default MyHeader