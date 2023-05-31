import React, { Component } from "react";
import Svg, { Circle, Text } from "react-native-svg";

class OTPTimer extends Component {

    constructor(props) {
        super(props);
        this.state = {
            minutes: 2,
            seconds: 0,
            message: `Expired`
        }
    }

    componentDidMount() {
        this.CounterInterval()
    }

    componentWillUnmount() {
        clearInterval(this.myInterval)
    }

    CounterInterval = () => {
        this.myInterval = setInterval(() => {
            const { seconds, minutes } = this.state

            if (seconds > 0) {
                this.setState(({ seconds }) => ({
                    seconds: seconds - 1
                }))
            }
            if (seconds === 0) {
                if (minutes === 0) {
                    clearInterval(this.myInterval)
                } else {
                    this.setState(({ minutes }) => ({
                        minutes: minutes - 1,
                        seconds: 59
                    }))
                }
            } 
        }, 1000)
    }

    render() {
        const width = 150;
        const height = 150;
        const size = width < height ? width - 32 : height - 16;
        const strokeWidth = 25;
        const radius = (size - strokeWidth) / 2;
        const circunference = radius * 2 * Math.PI;
        const { minutes, seconds } = this.state

        return (
            <Svg width={width} height={size}>
                <Circle
                    stroke="#2162cc"
                    strokeWidth={5}
                    fill="#ffffff"
                    cx={size / 2}
                    cy={size / 2}
                    r={radius}
                    strokeDasharray={`${circunference} ${circunference}`}
                />
                <Text
                    stroke="black"
                    fontSize="25"
                    x={size / 2}
                    y={size / 1.8}
                    textAnchor="middle"
                >
                    { minutes === 0 && seconds === 0
                    ? `${this.state.message}`
                    : `${minutes}:${seconds < 10 ? `0${seconds}` : seconds}`
                    }
                </Text>
            </Svg>
        );
    }
}

export default OTPTimer;