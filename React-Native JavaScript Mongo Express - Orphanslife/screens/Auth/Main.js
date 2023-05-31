import React from "react";
import Login from "./Login";
import SignUp from "./SignUp";
import { createBottomTabNavigator } from "@react-navigation/bottom-tabs";
import { MaterialCommunityIcons } from "@expo/vector-icons";

const Tab = createBottomTabNavigator();

function MyTabs() {
  return (
      <Tab.Navigator
        initialRouteName="Login"
        screenOptions={{
          tabBarActiveTintColor: "#e91e63",
          tabBarActiveBackgroundColor: "#000000",
          tabBarInactiveBackgroundColor: "#ffffff"
        }}
      >
        <Tab.Screen
          name="Login"
          component={Login}
          options={{
            tabBarIcon: ({ color, size }) => (
              <MaterialCommunityIcons name="login" color={color} size={size} />
            ),
          }}
        />
        <Tab.Screen
          name="SignUp"
          component={SignUp}
          options={{
            tabBarIcon: ({ color, size }) => (
              <MaterialCommunityIcons
                name="cash-register"
                color={color}
                size={size}
              />
            ),
          }}
        />
      </Tab.Navigator>
  );
}

const Main = () => {

  return (
    <>
      <MyTabs/>
    </>
  );
};

export default Main;
