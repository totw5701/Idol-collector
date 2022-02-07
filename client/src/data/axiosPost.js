import ApiService from '../ApiService'

let axiosPost = []

ApiService.getHome().then(( result ) => {
  //console.log(result)
  axiosPost = [...axiosPost,...result.data.data.cards]

}).catch((err) => {
  console.log('api/home axios 실패! '+err )

})


export default axiosPost