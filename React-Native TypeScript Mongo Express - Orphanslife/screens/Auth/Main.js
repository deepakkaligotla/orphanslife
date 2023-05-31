import React from 'react';
import Login from './Login';
import SignUp from './SignUp';
import {createBottomTabNavigator} from '@react-navigation/bottom-tabs';

const Tab = createBottomTabNavigator();

function MyTabs() {
  return (
    <Tab.Navigator
      initialRouteName="Login"
      screenOptions={{
        tabBarActiveTintColor: '#e91e63',
        tabBarActiveBackgroundColor: '#000000',
        tabBarInactiveBackgroundColor: '#ffffff',
      }}>
      <Tab.Screen name="Login" component={Login} />
      <Tab.Screen name="SignUp" component={SignUp} />
    </Tab.Navigator>
  );
}

const Main = () => {
  return (
    <>
      <MyTabs />
    </>
  );
};

export default Main;
