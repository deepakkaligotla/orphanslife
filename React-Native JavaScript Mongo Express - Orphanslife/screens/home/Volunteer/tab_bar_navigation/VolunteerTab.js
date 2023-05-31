import React from "react";
import { createBottomTabNavigator } from "@react-navigation/bottom-tabs";
import { MaterialCommunityIcons } from "@expo/vector-icons";
import VolunteerHome from "../VolunteerHome";
import VolunteerSettings from "../VolunteerSettings"
import VolunteerActions from "../VolunteerActions";

const Tab = createBottomTabNavigator();

function MyTabs() {
  return (
      <Tab.Navigator
        initialRouteName="VolunteerHome"
        screenOptions={{
          tabBarActiveTintColor: "#e91e63",
          tabBarActiveBackgroundColor: "#000000",
          tabBarInactiveBackgroundColor: "#ffffff"
        }}
      >
        <Tab.Screen
          name="VolunteerHome"
          component={VolunteerHome}
          options={{
            tabBarIcon: ({ color, size }) => (
              <MaterialCommunityIcons name="home" color={color} size={size} />
            ),
          }}
        />
        <Tab.Screen
          name="VolunteerActions"
          component={VolunteerActions}
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
          name="VolunteerSettings"
          component={VolunteerSettings}
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

class VolunteerTab extends React.Component {

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

export default VolunteerTab;
