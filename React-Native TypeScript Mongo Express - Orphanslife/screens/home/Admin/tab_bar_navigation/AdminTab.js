import React from "react";
import { createBottomTabNavigator } from "@react-navigation/bottom-tabs";
import { MaterialCommunityIcons } from "@expo/vector-icons";
import AdminHome from "../AdminHome";
import AdminSettings from "../AdminSettings"
import AdminActions from "../AdminActions";

const Tab = createBottomTabNavigator();

function MyTabs() {
  return (
      <Tab.Navigator
        initialRouteName="AdminHome"
        screenOptions={{
          tabBarActiveTintColor: "#e91e63",
          tabBarActiveBackgroundColor: "#000000",
          tabBarInactiveBackgroundColor: "#ffffff"
        }}
      >
        <Tab.Screen
          name="AdminHome"
          component={AdminHome}
          options={{
            tabBarIcon: ({ color, size }) => (
              <MaterialCommunityIcons name="home" color={color} size={size} />
            ),
          }}
        />
        <Tab.Screen
          name="AdminActions"
          component={AdminActions}
          options={{
            tabBarIcon: ({ color, size }) => (
              <MaterialCommunityIcons
                name="table"
                color={color}
                size={size}
              />
            ),
          }}
        />
        <Tab.Screen
          name="AdminSettings"
          component={AdminSettings}
          options={{
            tabBarIcon: ({ color, size }) => (
              <MaterialCommunityIcons
                name="account-settings"
                color={color}
                size={size}
              />
            ),
          }}
        />
      </Tab.Navigator>
  );
}

class AdminTab extends React.Component {

  constructor(props) {
    super(props);
    this.state = {
    }
  }

  render() {
    return (
      <>
        <MyTabs/>
      </>
    );
  }
};

export default AdminTab;
