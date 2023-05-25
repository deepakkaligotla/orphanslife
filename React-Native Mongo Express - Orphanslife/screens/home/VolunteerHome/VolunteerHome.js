import React from "react";
import { View, StyleSheet, Dimensions } from 'react-native';


export default class VolunteerHome extends React.Component {

    render() {
        return (
            <>
                <View style={styles.insideVolunteerHomeContainer}>
                    <Text>Volunteer Home</Text>
                </View>
            </>
        );
    }
}

const styles = StyleSheet.create({
    insideVolunteerHomeContainer: {
        backgroundColor: "#fff",
        alignItems: "center",
        alignSelf: "center",
        backgroundColor: 'black',
        borderWidth: 1,
        borderColor: 'white',
        marginTop: 5,
        marginLeft: 5,
        marginRight: 5,
        marginBottom: 5,
        width: Dimensions.get('window').width - 30,
    },
})