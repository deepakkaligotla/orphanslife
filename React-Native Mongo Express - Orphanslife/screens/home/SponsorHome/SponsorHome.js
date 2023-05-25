import React from "react";
import { View, StyleSheet, Dimensions } from 'react-native';


export default class SponsorHome extends React.Component {

    render() {
        return (
            <>
                <View style={styles.insideSponsorHomeContainer}>
                    <Text>Sponsor Home</Text>
                </View>
            </>
        );
    }
}

const styles = StyleSheet.create({
    insideSponsorHomeContainer: {
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