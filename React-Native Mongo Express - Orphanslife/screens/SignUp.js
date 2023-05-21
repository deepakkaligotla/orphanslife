import {StyleSheet, Text, View, Image, TextInput} from 'react-native'
import React from 'react'
import {
    Colors
  } from 'react-native/Libraries/NewAppScreen';

const SignUp = () => {

    return (
      <View style={styles.container}>
        <View>
        <Image
          style={styles.top}
          source={require('../assets/images/donate.png')}
        />
        </View>
        <Text style={styles.signupText}>SignUp</Text>
        <View>
        <Image
          style={styles.bottom}
          source={require('../assets/images/kids_jumping.gif')}
        />
        </View>
      </View>
    );
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        justifyContent: 'center'
    },
    signupText: {
        fontSize: 30,
        textAlign : 'center',
        color: Colors.white
    },
    signInput: {
        borderBottomWidth : 0.5,
        height: 48,
        borderBottomColor : '#8e93a1',
        marginBottom: 30,
    },
    top: {
        width: 350,
        height: 100,
    },
    bottom: {
        width: 350,
        height: 150,
    }
})

export default SignUp