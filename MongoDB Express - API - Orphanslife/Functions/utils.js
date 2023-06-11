function createResult(error, data) {
    const result = {};
    if (error) {
        result['status'] = 'error'
        result['error'] = error
    }
    else {
        result['status'] = 'success'
        result['data'] = data
    }
    return result
}

function createErrorResult(error) {
    return { status: 'error', error }
  }
  
function createSuccessResult(data, otp) {
    console.log(otp)
    return { status: 'success', data, otp }
}

module.exports = { createResult,createErrorResult,
    createSuccessResult, }
