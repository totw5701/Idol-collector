class Validator {

  constructor (cardDB,tags) {//cardDB로 여기에 안만들고 따로 받는이유: 유효성 검사 요소 다달라서
    this.regObj =
    { //유효성 검사 객체
         title: { rule: /^[ㄱ-ㅎ가-힣a-zA-Z0-9 ]{1,10}$/, msg: '카드 title은 10자 이내로 입력해주세요!' },
         description: { rule: /^[ㄱ-ㅎ가-힣a-zA-Z0-9 ]{1,30}$/, msg: '카드 설명은 30자 내로 입력해주세요'},
         alt: { rule: /^[ㄱ-ㅎ가-힣a-zA-Z0-9 ]/, msg: '카드 이미지 alt값을 입력해주세요!' },
         tags: { rule: /^[a-zA-Zㄱ-ㅎ가-힣]/, msg: '태그는 띄어쓰기 없이 한글 영어만 가능' },
         photo: { rule: /[ㄱ-ㅎ가-힣a-zA-Z0-9 ]/, msg: '사진을 등록해주세요' }
      }

    this.cardDB = cardDB
    this.tags = tags
  }

  isNull = (el) => { // nullcheck
    return (el.value == null) //undefined까지 걸러낼거라서 === 대신 == 사용
  }

  regTest() { // handleCreateCard 데이터 cardDB 유효성 검사

    let failed = [] //실패하면 push
    console.log(this.cardDB)
    this.cardDB.map((el) => {
    //태그 유효성 검사
      if(el.id === 'tags'){
         //태그 배열 크기 검사
         if( this.tags.length === 0 || this.tags.length >5 ) {
           failed.push(this.regObj[el.id].msg)
         }else{ //태그 1~5개 입력시 각각의 태그 유효성 검사
           this.tags.map((tag) => {
             if(!this.regObj['tags'].rule.test(tag) ){
               failed.push(this.regObj[el.id].msg)
             }
           })
         }
      }else{ // 태그 외 나머지 유효성 검사
        if( this.isNull(el) || !this.regObj[el.id].rule.test(el.value) ){
          failed.push(this.regObj[el.id].msg)
        }
      }
    })// cardDB 반복문 끝

    console.log(failed)

    if( failed.length === 0 ){ //유효성 검사 전부 통과하면
      return true
    }else{
      alert(failed[0])
      return false
    }

 }// regTest 끝

}

export default Validator