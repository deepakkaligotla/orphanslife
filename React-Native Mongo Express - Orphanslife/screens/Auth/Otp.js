function getRandomNumber(min, max) {
    return Math.round(Math.random() * (max - min) + min);
}

function fakeFetch(date, { signal }) {
    return new Promise((resolve, reject) => {
        const timeout = setTimeout(() => {
            const daysInMonth = date.daysInMonth();
            const daysToHighlight = [1, 2, 3].map(() => getRandomNumber(1, daysInMonth));

            resolve({ daysToHighlight });
        }, 500);

        signal.onabort = () => {
            clearTimeout(timeout);
            reject(new DOMException('aborted', 'AbortError'));
        };
    });
}

function validateOTP() {
    userOTP = document.getElementById('validate-otp').value
    console.log('User Entered OTP - ' + userOTP)
    validateOTPTime = (new Date().getMinutes() * 60) + new Date().getSeconds();
    otpExpire = validateOTPTime - otpSentTime
    console.log(otpSentTime)
    console.log(validateOTPTime)
    console.log(otpExpire)
    if (otpExpire < 120) {
        if (otp == userOTP) {
            console.log('OTP Verified')
            setTimeout(() => {
                navigate('/home');
            }, 500);
        } else if (otp != userOTP) {
            console.log('Invalid OTP')
            alert("Invalid OTP please check again");
        }
    } else if (otpExpire > 120) {
        console.log('Invalid expired')
        alert("OTP Expired please try again!!!");
    }
}

function loginButton() {
    console.log(cred)
    axios.post(localIP, cred).then((response) => {
        const profile = response.data;
        console.log(profile)
        const token = profile.token;
        if (response.data != null) {
            otpTimer()
            otpSentTime = (new Date().getMinutes() * 60) + new Date().getSeconds();
            localStorage.clear();
            localStorage.setItem('user-token', token);
            otp = profile.otp;
            console.log('Sent OTP from DB')
            return;
        } if (response.ok === 'false') {
            alert("Something went wrong")
        }
    }).catch((error) => {
        return;
    });
}

function otpTimer() {
    let circularProgress = document.querySelector('.circular-progress'),
        progressValue = document.querySelector('.progress-value')
    let progressStartValue = 120, progressEndValue = 0, speed = 1000
    let progress = setInterval(() => {
        progressStartValue--
        progressValue.textContent = `${progressStartValue} sec left`
        circularProgress.style.background = `conic-gradient(#7d2ae8 ${progressStartValue * 3}deg, #ededed 0deg)`
        if (progressStartValue === progressEndValue) {
            clearInterval(progress);
        }
    }, speed);
}