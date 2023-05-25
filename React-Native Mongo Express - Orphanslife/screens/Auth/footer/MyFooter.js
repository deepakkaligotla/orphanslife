import React from "react";
import { View, Image, StyleSheet, Dimensions } from 'react-native';


export default class MyFooter extends React.Component {

    render() {
        return (
            <>
                <View style={styles.insideFooterContainer}>
                    <Image style={styles.bottom}
                        source={require("../../../assets/images/kids_jumping.gif")}
                    />
                </View>
            </>
        );
    }
}

const styles = StyleSheet.create({
    insideFooterContainer: {
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
    bottom: {
        width: 150,
        height: 100,
        marginTop: 10
    }
})