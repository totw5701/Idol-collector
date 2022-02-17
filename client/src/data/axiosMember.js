import ApiService from '../ApiService'


let axiosMember = {}


ApiService.getHome().then(( result ) => {
  //console.log(result.data.data.member)
  axiosMember = {...axiosMember,...result.data.data.member}
  //console.log(axiosMember)
}).catch((err) => {
  console.log('axiosMember 데이터 가져오기 에러! '+ err )
})

export default axiosMember