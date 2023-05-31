import React from "react";
import { View, StyleSheet, Dimensions, Text } from "react-native";
import AdoptReqDashboard from '../../../assets/html/google_charts/adopt_request_details'

export const options = {
    title: "My Daily Activities",
};

class AdminHome extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            new_req: 0,
            approved: 0,
            rejected: 0,
            data: [
                ["Requests Type", "Count"],
                ["New Requests", this.new_req],
                ["Approved", this.approved],
                ["Rejected", this.rejected],
            ],
        };
    }

    render() {
        return (
            <>
                <View style={styles.insideAdminHomeContainer}>
                    <Text style={styles.viewText}>Welcome to Admin Home Screen</Text>
                    <AdoptReqDashboard/>
                </View>
            </>
        );
    }
}

const styles = StyleSheet.create({
    insideAdminHomeContainer: {
        alignSelf: "center",
        alignItems: "center",
        backgroundColor: "black",
        marginLeft: 10,
        marginRight: 10,
        width: Dimensions.get("window").width - 30,
        height: 600,
    },
    input: {
        textAlign: "center",
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
    webView: {
        height: 300,
        width: Dimensions.get("window").width - 35,
    },
});

export default AdminHome;
