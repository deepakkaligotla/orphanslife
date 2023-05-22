import * as React from 'react';
import { createBottomTabNavigator } from '@react-navigation/bottom-tabs';
import { MaterialCommunityIcons } from '@expo/vector-icons';
import Login from '../Login';
import SignUp from '../SignUp';
import { NavigationContainer } from '@react-navigation/native';

const Tab = createBottomTabNavigator();

function MyTabs() {
  return (
    <>
      <NavigationContainer>
        <Tab.Navigator
          initialRouteName="Login"
          screenOptions={{
            tabBarActiveTintColor: '#e91e63',
          }}
        >
          <Tab.Screen
            name="Login"
            component={Login}
            options={{
              tabBarLabel: 'Login',
              tabBarIcon: ({ color, size }) => (
                <MaterialCommunityIcons name="login" color={color} size={size} />
              ),
            }}
          />
          <Tab.Screen
            name="SignUp"
            component={SignUp}
            options={{
              tabBarLabel: 'SignUp',
              tabBarIcon: ({ color, size }) => (
                <MaterialCommunityIcons name="moon-new" color={color} size={size} />
              ),
            }}
          />
        </Tab.Navigator>
      </NavigationContainer>
    </>
  );
}

export default MyTabs
