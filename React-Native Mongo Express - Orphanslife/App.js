import React from 'react';
import { SafeAreaView, StyleSheet, TouchableOpacity, ScrollView, View, StatusBar, Text } from 'react-native';
import * as ScreenOrientation from "expo-screen-orientation";
import { useEffect, useState } from "react";
import { Colors } from 'react-native/Libraries/NewAppScreen';
import SignUp from './screens/SignUp';

export default function App() {

  const [orientation, setOrientation] = useState(null);

  useEffect(() => {
    checkOrientation();
    const subscription = ScreenOrientation.addOrientationChangeListener(
      handleOrientationChange
    );
    return () => {
      ScreenOrientation.removeOrientationChangeListeners(subscription);
    };
  }, []);

  const checkOrientation = async () => {
    const orientation = await ScreenOrientation.getOrientationAsync();
    setOrientation(orientation);
  };

  const handleOrientationChange = (o) => {
    setOrientation(o.orientationInfo.orientation);
  };
  console.log(orientation);

  return (
    <>
      <StatusBar barStyle="dark-content" />
      <SafeAreaView>
        <ScrollView
          contentInsetAdjustmentBehavior="automatic"
          style={styles.scrollView}>
          <View style={styles.container}>
            <View style={styles.body}>
              <View style={styles.sectionContainer}>
                <SignUp/>
              </View>
            </View>
          </View>
        </ScrollView>
      </SafeAreaView>
    </>
  );
};

const styles = StyleSheet.create({
  scrollView: {
    backgroundColor: Colors.lighter,
  },
  engine: {
    position: 'absolute',
    right: 0,
  },
  container: {
    flex: 1,
    backgroundColor: "#fff",
    alignItems: "center",
    justifyContent: "center",
  },
  body: {
    backgroundColor: Colors.black,
  },
  sectionContainer: {
    marginTop: 32,
    paddingHorizontal: 24,
  },
  sectionTitle: {
    fontSize: 24,
    fontWeight: '600',
    color: Colors.black,
  },
  sectionDescription: {
    marginTop: 8,
    fontSize: 18,
    fontWeight: '400',
    color: Colors.dark,
  },
  highlight: {
    fontWeight: '700',
  },
  footer: {
    color: Colors.dark,
    fontSize: 12,
    fontWeight: '600',
    padding: 4,
    paddingRight: 12,
    textAlign: 'right',
  },
  btn: {
    padding: 10,
  },
  txt: {
    fontSize: 16,
    color: "blue",
  },
});