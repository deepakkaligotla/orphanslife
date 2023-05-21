import {StyleSheet, Text, View, Image, TextInput, PlatformColor} from 'react-native'
import React from 'react'

const SignUp = () => {

    return (
      <View style={styles.container}>
        <View>
        <Image
          style={styles.top}
          source={require('../assets/images/donate.png')}
        />
        </View>
        <Text style={styles.viewText}>Login</Text>
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
    viewText: {
        fontSize: 30,
        textAlign : 'center',
        color: '#ffffff'
    },
    signInput: {
        borderBottomWidth : 0.5,
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
    }
})

export default SignUp