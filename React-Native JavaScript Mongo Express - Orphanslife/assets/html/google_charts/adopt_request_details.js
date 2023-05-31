import React from "react";
import { View, StyleSheet, Dimensions, Text } from "react-native";
import { WebView } from "react-native-webview";

class AdoptRequestDashboard extends React.Component {
  constructor(props) {
    super(props);
    this.state = {

      adopt_req_html: 
      `<html>
  <head>
    <script type="text/javascript" src="https://www.google.com/jsapi"></script>
    <script type="text/javascript">
      google.load("visualization", "1", {packages:["corechart"]});
      google.setOnLoadCallback(drawChart);
      function drawChart() {
        var data = google.visualization.arrayToDataTable([
          ['Items',		'Value'],
          ['New Requests',	Android.getNewReq()],
          ['Approved',	Android.getApproved()],
          ['Rejected',	Android.getRejected()]
        ]);

        var options = {
          title: 'Adopt Requests Dashboard',
          is3D: true,
        };

        var chart = new google.visualization.PieChart(document.getElementById('piechart_3d'));
        chart.draw(data, options);
      }
    </script>
  </head>
  <body style="background-color:#B3FF37;">
    <div id="piechart_3d" style="width: 100%; height: 100%;"></div>
  </body>
</html>`
    }
  }

    render() {
      return (
        <View style={styles.container}>
          <WebView source={{ html: (this.state.adopt_req_html) }} style={styles.webStyle} />
        </View>
      );
    }
  }

  const styles = StyleSheet.create({
    container: {
      alignSelf: "center",
      alignItems: "center",
      backgroundColor: "black",
      marginLeft: 10,
      marginRight: 10,
      width: Dimensions.get("window").width - 35,
      height: 300,
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

export default AdoptRequestDashboard;
