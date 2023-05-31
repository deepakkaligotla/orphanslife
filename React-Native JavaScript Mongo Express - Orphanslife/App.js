import React from 'react';
import { SafeAreaView, Dimensions, StyleSheet, StatusBar } from 'react-native';
import { NavigationContainer } from '@react-navigation/native';
import { createNativeStackNavigator } from '@react-navigation/native-stack';
import {navigationRef} from './screens/navigation/RootNavigation';
import DeviceInfo from 'react-native-device-info';
import Main from './screens/Auth/Main';
import AdminTab from './screens/home/Admin/tab_bar_navigation/AdminTab'
import VolunteerTab from './screens/home/Volunteer/tab_bar_navigation/VolunteerTab';
import GuardianTab from './screens/home/Guardian/tab_bar_navigation/GuardianTab';
import SponsorTab from './screens/home/Sponsor/tab_bar_navigation/SponsorTab';

export default function App() {

  const Stack = createNativeStackNavigator();
  DeviceInfo.getBatteryLevel().then((batteryLevel) => {
    console.log(batteryLevel)
  });

  return (
    <>
      <SafeAreaView style={styles.appSafeArea}>
      <StatusBar
        animated={true}
        barStyle='light-content'
        backgroundColor="#000000"
        hidden={false}
      />
        <NavigationContainer ref={navigationRef}>
          <Stack.Navigator initialRouteName="Orphanslife">
            <Stack.Screen name="Orphanslife" component={Main} />
            <Stack.Screen name="AdminTab" component={AdminTab} />
            <Stack.Screen name="VolunteerTab" component={VolunteerTab} />
            <Stack.Screen name="GuardianTab" component={GuardianTab} />
            <Stack.Screen name="SponsorTab" component={SponsorTab} />
          </Stack.Navigator>
        </NavigationContainer>
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
    width: Dimensions.get('window').width,
    borderColor: 'black',
    borderBottomWidth: 5,
    flex: 1
  }
})