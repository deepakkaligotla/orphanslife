import React from 'react';
import { SafeAreaView, Dimensions, StyleSheet } from 'react-native';
import Main from './screens/Auth/Main';

export default function App() {

  return (
    <>
      <SafeAreaView style={styles.appSafeArea}>
        <Main />
      </SafeAreaView>
    </>
  );
};

const styles = StyleSheet.create({
  appSafeArea: { 
    marginTop: 5,
    marginLeft: 5,
    marginRight: 5,
    marginBottom: 5,
    backgroundColor: '#ffffff',
    width:Dimensions.get('window').width,
    borderColor: 'black',
    borderBottomWidth: 5,
    flex: 1
  }
})