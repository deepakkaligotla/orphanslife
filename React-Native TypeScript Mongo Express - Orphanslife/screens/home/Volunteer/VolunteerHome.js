import React from "react";
import { View, StyleSheet, Dimensions, Text } from 'react-native';


class VolunteerHome extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
        }
    }

    render() {
        return (
            <>
                <View style={styles.insideVolunteerHomeContainer}>
                    <Text style={styles.viewText}>Welcome to Volunteer Home Screen</Text>
                </View>
            </>
        );
    }
}

const styles = StyleSheet.create({
    insideVolunteerHomeContainer: {
        alignSelf: "center",
        alignItems: 'center',
        backgroundColor: 'black',
        marginLeft: 10,
        marginRight: 10,
        width: Dimensions.get("window").width - 30,
        height: 600
    },
    input: {
        textAlign: 'center',
        height: 40,
        width: 300,
        margin: 12,
        borderWidth: 1,
        padding: 10,
        backgroundColor: "#ffffff",
    },
    viewText: {
        fontSize: 15,
        textAlign: "center",
        color: "#ffffff",
    },
})

export default VolunteerHome