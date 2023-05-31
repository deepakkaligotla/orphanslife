import React from "react";
import { createBottomTabNavigator } from "@react-navigation/bottom-tabs";
import { MaterialCommunityIcons } from "@expo/vector-icons";
import SponsorHome from "../SponsorHome";
import SponsorSettings from "../SponsorSettings"
import SponsorActions from "../SponsorActions";

const Tab = createBottomTabNavigator();

function MyTabs() {
  return (
      <Tab.Navigator
        initialRouteName="SponsorHome"
        screenOptions={{
          tabBarActiveTintColor: "#e91e63",
          tabBarActiveBackgroundColor: "#000000",
          tabBarInactiveBackgroundColor: "#ffffff"
        }}
      >
        <Tab.Screen
          name="SponsorHome"
          component={SponsorHome}
          options={{
            tabBarIcon: ({ color, size }) => (
              <MaterialCommunityIcons name="home" color={color} size={size} />
            ),
          }}
        />
        <Tab.Screen
          name="SponsorActions"
          component={SponsorActions}
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
          name="SponsorSettings"
          component={SponsorSettings}
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

class SponsorTab extends React.Component {

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

export default SponsorTab;
