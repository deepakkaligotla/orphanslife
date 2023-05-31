/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 */

import React from 'react';
import {
  SafeAreaView,
  ScrollView,
  StatusBar,
  useColorScheme,
} from 'react-native';
import {NavigationContainer} from '@react-navigation/native';
import {createNativeStackNavigator} from '@react-navigation/native-stack';
import {navigationRef} from './screens/navigation/RootNavigation';
import Main from './screens/Auth/Main';
import AdminTab from './screens/home/Admin/tab_bar_navigation/AdminTab';
import VolunteerTab from './screens/home/Volunteer/tab_bar_navigation/VolunteerTab';
import GuardianTab from './screens/home/Guardian/tab_bar_navigation/GuardianTab';
import SponsorTab from './screens/home/Sponsor/tab_bar_navigation/SponsorTab';

import {Colors} from 'react-native/Libraries/NewAppScreen';

function App(): JSX.Element {
  const isDarkMode = useColorScheme() === 'dark';
  const Stack = createNativeStackNavigator();

  const backgroundStyle = {
    backgroundColor: isDarkMode ? Colors.darker : Colors.lighter,
  };

  return (
    <SafeAreaView style={backgroundStyle}>
      <StatusBar
        barStyle={isDarkMode ? 'light-content' : 'dark-content'}
        backgroundColor={backgroundStyle.backgroundColor}
      />
      <ScrollView
        contentInsetAdjustmentBehavior="automatic"
        style={backgroundStyle}>
        <StatusBar
          animated={true}
          barStyle="light-content"
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
      </ScrollView>
    </SafeAreaView>
  );
}

export default App;
