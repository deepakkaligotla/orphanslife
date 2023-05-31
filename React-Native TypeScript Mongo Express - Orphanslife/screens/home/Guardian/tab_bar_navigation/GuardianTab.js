import React from "react";
import { createBottomTabNavigator } from "@react-navigation/bottom-tabs";
import { MaterialCommunityIcons } from "@expo/vector-icons";
import GuardianHome from "../GuardianHome";
import GuardianSettings from "../GuardianSettings"
import GuardianActions from "../GuardianActions";

const Tab = createBottomTabNavigator();

function MyTabs() {
  return (
      <Tab.Navigator
        initialRouteName="GuardianHome"
        screenOptions={{
          tabBarActiveTintColor: "#e91e63",
          tabBarActiveBackgroundColor: "#000000",
          tabBarInactiveBackgroundColor: "#ffffff"
        }}
      >
        <Tab.Screen
          name="GuardianHome"
          component={GuardianHome}
          options={{
            tabBarIcon: ({ color, size }) => (
              <MaterialCommunityIcons name="home" color={color} size={size} />
            ),
          }}
        />
        <Tab.Screen
          name="GuardianActions"
          component={GuardianActions}
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
          name="GuardianSettings"
          component={GuardianSettings}
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

class GuardianTab extends React.Component {

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

export default GuardianTab;
