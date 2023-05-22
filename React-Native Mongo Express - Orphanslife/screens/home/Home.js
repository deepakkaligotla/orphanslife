import * as React from 'react-native';
import { View, Text, StyleSheet } from 'react-native';
import { NavigationContainer } from '@react-navigation/native';
import { createNativeStackNavigator } from '@react-navigation/native-stack';

const Home = ({ navigation }) => {

  return (
    <>
      <View>
        <Text style={{color:'white'}}>Home</Text>
      </View>
    </>
  );
}

const styles = StyleSheet.create({
  top: {
    width: 350,
    height: 100,
  },
})

export default Home