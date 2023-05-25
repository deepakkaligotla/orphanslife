import React from "react";
import { View, StyleSheet, Dimensions } from 'react-native';


export default class GuardianHome extends React.Component {

    render() {
        return (
            <>
                <View style={styles.insideGuardianHomeContainer}>
                    <Text>Guardian Home</Text>
                </View>
            </>
        );
    }
}

const styles = StyleSheet.create({
    insideGuardianHomeContainer: {
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