import React from "react";
import { View, StyleSheet, Dimensions } from 'react-native';


export default class AdminHome extends React.Component {

    render() {
        return (
            <>
                <View style={styles.insideAdminHomeContainer}>
                    <Text>Admin Home</Text>
                </View>
            </>
        );
    }
}

const styles = StyleSheet.create({
    insideAdminHomeContainer: {
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