export default [

 {
  authorId: 1,
  authorNickName: "회원 별명",
  createDate: "2022-02-06T15:19:49.146Z",
  comments: [
    {
      authorId: 1,
      authorNickName: "별명0",
      content: "댓글내용 여기 써요!",
      createDate: "2022-02-06T15:19:49.146Z",
      didLike: true,
      id: 200,
      likes: 0,
      modifyDate: "2022-02-06T15:19:49.146Z",
      nestedComments: [
        {
          authorId: 3,
          authorNickName: "별명1",
          content: "대댓글",
          createDate: "2022-02-06T15:19:49.146Z",
          didLike: true,
          id: 201,
          likes: 0,
          modifyDate: "2022-02-06T15:19:49.146Z"
        },
        {
          authorId: 5,
          authorNickName: "별명1",
          content: "대댓글",
          createDate: "2022-02-06T15:19:49.146Z",
          didLike: true,
          id: 202,
          likes: 0,
          modifyDate: "2022-02-06T15:19:49.146Z"
        }
      ]
    },
    {
      authorId: 7,
      authorNickName: "별명1",
      content: "댓글내용 여기 써요1!",
      createDate: "2022-02-06T15:19:49.146Z",
      didLike: true,
      id: 203,
      likes: 0,
      modifyDate: "2022-02-06T15:19:49.146Z",
      nestedComments: [
        {
          authorId: 9,
          authorNickName: "별명1",
          content: "대댓글",
          createDate: "2022-02-06T15:19:49.146Z",
          didLike: true,
          id: 204,
          likes: 0,
          modifyDate: "2022-02-06T15:19:49.146Z"
        }
      ]
    },
    {
      authorId: 11,
      authorNickName: "별명2",
      content: "댓글내용 여기 써요2!",
      createDate: "2022-02-06T15:19:49.146Z",
      didLike: true,
      id: 205,
      likes: 0,
      modifyDate: "2022-02-06T15:19:49.146Z",
      nestedComments: [
        {
          authorId: 13,
          authorNickName: "별명1",
          content: "대댓글",
          createDate: "2022-02-06T15:19:49.146Z",
          didLike: true,
          id: 206,
          likes: 0,
          modifyDate: "2022-02-06T15:19:49.146Z"
        }
      ]
    },
    {
      authorId: 15,
      authorNickName: "별명3",
      content: "댓글내용 여기 써요3!",
      createDate: "2022-02-06T15:19:49.146Z",
      didLike: true,
      id: 207,
      likes: 0,
      modifyDate: "2022-02-06T15:19:49.146Z",
      nestedComments: [
        {
          authorId: 17,
          authorNickName: "별명1",
          content: "대댓글",
          createDate: "2022-02-06T15:19:49.146Z",
          didLike: true,
          id: 208,
          likes: 0,
          modifyDate: "2022-02-06T15:19:49.146Z"
        }
      ]
    },

  ],
  content: "설명은 파란배경 아이돌 사진",
  didLike: true,
  didScrap: true,
  id: 0,
  likes: 10000,
  oriFileName: "string",
  storeFileName:
  'https://pds.joongang.co.kr/news/component/htmlphoto_mmdata/202012/09/4b713b30-3499-4cf7-a95d-1e877f0367d4.jpg',
  tags: [{name:'아무나'},{name:'아이돌'}
    ],
  title: "카드제목은 로운입니다.",
  views: 0
  },

  {
    id: 1,
    authorNickName: 'nickname1',
    authorId: 'id1',
    title: '로제',
    content: '로제 개인 사진',
    createDate: '2021-12-21',
    views: 100,
    likes: 10,
    storeFileName:
      'https://post-phinf.pstatic.net/MjAyMDA4MDRfMTM1/MDAxNTk2NTMzMDAxNjU4.IWaWaf1YDVPgPgxX0Td0nge2UClOiPaA99r8HpLsaGQg.PnAM3SqL6YeB3ZbRFnLAQyAE-YgBloIbmb_SQ0YL0_Ag.JPEG/%EB%A1%9C%EC%A0%9C01.jpg?type=w1200',
  },
  {
    id: 2,
    authorNickName: 'nickname2',
    authorId: 'id2',
    title: '진',
    content: '진 멋있는 척',
    createDate: '2021-12-21',
    views: 200,
    likes: 20,
    storeFileName:
      'https://pbs.twimg.com/profile_images/1417337660683612166/k969fqda_400x400.jpg',
  },
  {
   authorId: 3,
   authorNickName: "회원 별명",
   createDate: "2022-02-06T15:19:49.146Z",
   comments: [
     {
       authorId: 15,
       authorNickName: "별명3",
       content: "댓글내용 여기 써요3!",
       createDate: "2022-02-06T15:19:49.146Z",
       didLike: true,
       id: 207,
       likes: 0,
       modifyDate: "2022-02-06T15:19:49.146Z",
       nestedComments: []
     },
   ],
   content: "설명은 파란배경 아이돌 사진",
   didLike: true,
   didScrap: true,
   id: 3,
   likes: 10000,
   oriFileName: "string",
   storeFileName:
'https://post-phinf.pstatic.net/MjAyMDA4MDRfMTM1/MDAxNTk2NTMzMDAxNjU4.IWaWaf1YDVPgPgxX0Td0nge2UClOiPaA99r8HpLsaGQg.PnAM3SqL6YeB3ZbRFnLAQyAE-YgBloIbmb_SQ0YL0_Ag.JPEG/%EB%A1%9C%EC%A0%9C01.jpg?type=w1200',   tags: [{name:'아무나'},{name:'아이돌'}
     ],
   title: "카드제목은 로운입니다.",
   views: 0
   },
  {
   authorId: 3,
   authorNickName: "회원 별명",
   createDate: "2022-02-06T15:19:49.146Z",
   comments: [

   ],
   content: "설명은 파란배경 아이돌 사진",
   didLike: true,
   didScrap: true,
   id: 4,
   likes: 10000,
   oriFileName: "string",
   storeFileName:
  'https://img.koreatimes.co.kr/upload/newsV2/images/202108/c6758c3ec6454152bf0d29e969caac1c.jpg/dims/resize/740/optimize',
  tags: [{name:'아무나'},{name:'아이돌'}
     ],
   title: "방탄 버터.",
   views: 12
   },
  {
    id: 5,
    authorNickName: 'nickname5',
    authorId: 'id5',
    title: '블랙핑크',
    content: '블랙핑크 검은색 드레스',
    createDate: '2021-12-21',
    views: 500,
    likes: 50,
    storeFileName:
      'https://images.squarespace-cdn.com/content/v1/5c5867627a1fbd515af1bd68/1631914145181-KW1T90WGEYVO61TAAAIM/UN+-+BLACKPINK+Photograph.jpg?format=1500w',
  },
  {
    id: 6,
    authorNickName: 'nickname6',
    authorId: 'id6',
    title: '아이유',
    content: '아이유 나무위키 사진',
    createDate: '2021-12-21',
    views: 600,
    likes: 60,
    storeFileName:
      'https://w.namu.la/s/40de86374ddd74756b31d4694a7434ee9398baa51fa5ae72d28f2eeeafdadf0c475c55c58e29a684920e0d6a42602b339f8aaf6d19764b04405a0f8bee7f598d2922db9475579419aac4635d0a71fdb8a4b2343cb550e6ed93e13c1a05cede75',
  },
  {
    id: 7,
    authorNickName: 'nickname7',
    authorId: 'id7',
    title: '뷔',
    content: '뷔 개인 사진',
    createDate: '2021-12-21',
    views: 700,
    likes: 70,
    storeFileName:
      'https://www.allkpop.com/upload/2021/12/content/191625/1639949115-incollage-20210813-231704613.jpg',
  },
  {
    id: 8,
    authorNickName: 'nickname8',
    authorId: 'id8',
    title: 'bts 지민과 누구',
    content: '지민과 다른 사람',
    createDate: '2021-12-21',
    views: 800,
    likes: 80,
    storeFileName:
      'https://www.pinkvilla.com/files/bts_be_v_hilarious_last_thought_plum_jimin_song_most_himself_filter.jpg',
  },
  {
    id: 9,
    authorNickName: 'nickname9',
    authorId: 'id9',
    title: '청하',
    content: '청하 청하 청하 청하 청하 청하 청하 청하 청하',
    createDate: '2021-12-21',
    views: 900,
    likes: 90,
    storeFileName:
      'https://pds.joongang.co.kr/news/component/htmlphoto_mmdata/202012/09/4b713b30-3499-4cf7-a95d-1e877f0367d4.jpg',
  },
  {
    id: 10,
    authorNickName: 'nickname10',
    authorId: 'id10',
    title: '쯔위',
    content: '쯔위 걷는 중',
    createDate: '2021-12-21',
    views: 1000,
    likes: 100,
    storeFileName:
      'https://pds.joongang.co.kr/news/component/htmlphoto_mmdata/202003/12/c709f3ac-5f0c-4a75-9185-573d61f5aca2.jpg',
  },
  {
    id: 11,
    authorNickName: 'nickname11',
    authorId: 'id11',
    title: '김유정',
    content: '김유정 김유정 김유정 김유정 김유정 김유정 김유정 김유정 김유정',
    createDate: '2021-12-21',
    views: 1100,
    likes: 110,
    storeFileName: 'https://img.hankyung.com/photo/202011/BF.24518659.1.png',
  },
  {
    id: 12,
    authorNickName: 'nickname12',
    authorId: 'id12',
    title: '누구죠',
    content: '죄송해요 누군지 모르겠어요',
    createDate: '2021-12-21',
    views: 1200,
    likes: 120,
    storeFileName: 'https://t1.daumcdn.net/cfile/tistory/99FEE8465C4D9DFA1A',
  },
  {
    id: 13,
    authorNickName: 'nickname13',
    authorId: 'id13',
    title: '누굴까',
    content: '또 모르는 아이돌이에요',
    createDate: '2021-12-21',
    views: 1300,
    likes: 130,
    storeFileName:
      'https://img1.daumcdn.net/thumb/R1280x0.fjpg/?fname=http://t1.daumcdn.net/brunch/service/user/9g2Q/image/_SznIDRiJJdKID82JMB-x02W4FM.jpg',
  },
  {
    id: 14,
    authorNickName: 'nickname14',
    authorId: 'id14',
    title: '전소미',
    content: '소미 구글에서 가장 먼저 뜨는 사진',
    createDate: '2021-12-21',
    views: 1400,
    likes: 140,
    storeFileName: 'https://img.hankyung.com/photo/201906/BF.19897553.1.jpg',
  },
  {
    id: 15,
    authorNickName: 'nickname15',
    authorId: 'id15',
    title: '킹덤',
    content: '새로운 보이그룹 킹덤',
    createDate: '2021-12-21',
    views: 1500,
    likes: 150,
    storeFileName:
      'https://img.hankyung.com/photo/201906/BF.19897553.1.jpg',
  },
  {
    id: 16,
    authorNickName: 'nickname16',
    authorId: 'id16',
    title: '송민호',
    content: '나무위키 송민호 사진',
    createDate: '2021-12-21',
    views: 1600,
    likes: 160,
    storeFileName:
      'https://w.namu.la/s/064badd7320fcb672be77971556a749c241c3f1a0c180e8c8e7cfce4cc795d4037b33bf5e45a28949fd4daf8021685ea2a7c42217140f4029b33babee5375a8e63308ee771c2d99ffbc90795a1182341c3aafa7c4ab70c169dabbb148cd19dfd',
  },
  {
    id: 17,
    authorNickName: 'nickname17',
    authorId: 'id17',
    title: '유나',
    content: 'ITZY의 유나',
    createDate: '2021-12-21',
    views: 1700,
    likes: 170,
    storeFileName:
      'https://cdn.mhns.co.kr/news/photo/202006/411019_530277_3711.jpg',
  },
  {
    id: 18,
    authorNickName: 'nickname18',
    authorId: 'id18',
    title: '아이즈원',
    content: '올리브영 광고 속 아이즈원',
    createDate: '2021-12-21',
    views: 1800,
    likes: 180,
    storeFileName:
      'https://www.cosinkorea.com/data/photos/20190416/art_15553758253287_393796.jpg',
  },
  {
    id: 19,
    authorNickName: 'nickname19',
    authorId: 'id19',
    title: '차은우',
    content: '97년생 차은우',
    createDate: '2021-12-21',
    views: 1900,
    likes: 190,
    storeFileName:
      'http://file2.instiz.net/data/cached_img/upload/2017/12/10/22/5be4aa5167d050f7d70384f2620f1ff3.jpg',
  },
  {
    id: 20,
    authorNickName: 'nickname20',
    authorId: 'id20',
    title: '버논',
    content: '세븐틴 버논',
    createDate: '2021-12-21',
    views: 2000,
    likes: 200,
    storeFileName:
      'http://file2.instiz.net/data/cached_img/upload/2017/12/10/22/ea157bc4974420076209cce9df096bd9.jpg',
  },
  {
    id: 21,
    authorNickName: 'nickname20',
    authorId: 'id20',
    title: '버논',
    content: '세븐틴 버논',
    createDate: '2021-12-21',
    views: 2000,
    likes: 200,
    storeFileName:
      'http://file2.instiz.net/data/cached_img/upload/2017/12/10/22/ea157bc4974420076209cce9df096bd9.jpg',
  },
  {
    id: 22,
    authorNickName: 'nickname20',
    authorId: 'id20',
    title: '버논',
    content: '세븐틴 버논',
    createDate: '2021-12-21',
    views: 2000,
    likes: 200,
    storeFileName:
      'http://file2.instiz.net/data/cached_img/upload/2017/12/10/22/ea157bc4974420076209cce9df096bd9.jpg',
  },
  {
    id: 23,
    authorNickName: 'nickname20',
    authorId: 'id20',
    title: '버논',
    content: '세븐틴 버논',
    createDate: '2021-12-21',
    views: 2000,
    likes: 200,
    storeFileName:
      'http://file2.instiz.net/data/cached_img/upload/2017/12/10/22/ea157bc4974420076209cce9df096bd9.jpg',
  },
  {
    id: 24,
    authorNickName: 'nickname20',
    authorId: 'id20',
    title: '버논',
    content: '세븐틴 버논',
    createDate: '2021-12-21',
    views: 2000,
    likes: 200,
    storeFileName:
      'http://file2.instiz.net/data/cached_img/upload/2017/12/10/22/ea157bc4974420076209cce9df096bd9.jpg',
  },
  {
    id: 25,
    authorNickName: 'nickname20',
    authorId: 'id20',
    title: '버논',
    content: '세븐틴 버논',
    createDate: '2021-12-21',
    views: 2000,
    likes: 200,
    storeFileName:
      'http://file2.instiz.net/data/cached_img/upload/2017/12/10/22/ea157bc4974420076209cce9df096bd9.jpg',
  },
  {
    id: 26,
    authorNickName: 'nickname20',
    authorId: 'id20',
    title: '버논',
    content: '세븐틴 버논',
    createDate: '2021-12-21',
    views: 2000,
    likes: 200,
    storeFileName:
      'http://file2.instiz.net/data/cached_img/upload/2017/12/10/22/ea157bc4974420076209cce9df096bd9.jpg',
  },
  {
    id: 27,
    authorNickName: 'nickname20',
    authorId: 'id20',
    title: '버논',
    content: '세븐틴 버논',
    createDate: '2021-12-21',
    views: 2000,
    likes: 200,
    storeFileName:
      'http://file2.instiz.net/data/cached_img/upload/2017/12/10/22/ea157bc4974420076209cce9df096bd9.jpg',
  },
  {
    id: 28,
    authorNickName: 'nickname20',
    authorId: 'id20',
    title: '버논',
    content: '세븐틴 버논',
    createDate: '2021-12-21',
    views: 2000,
    likes: 200,
    storeFileName:
      'http://file2.instiz.net/data/cached_img/upload/2017/12/10/22/ea157bc4974420076209cce9df096bd9.jpg',
  },
  {
    id: 29,
    authorNickName: 'nickname20',
    authorId: 'id20',
    title: '버논',
    content: '세븐틴 버논',
    createDate: '2021-12-21',
    views: 2000,
    likes: 200,
    storeFileName:
      'http://file2.instiz.net/data/cached_img/upload/2017/12/10/22/ea157bc4974420076209cce9df096bd9.jpg',
  },
  {
    id: 30,
    authorNickName: 'nickname20',
    authorId: 'id20',
    title: '버논',
    content: '세븐틴 버논',
    createDate: '2021-12-21',
    views: 2000,
    likes: 200,
    storeFileName:
      'http://file2.instiz.net/data/cached_img/upload/2017/12/10/22/ea157bc4974420076209cce9df096bd9.jpg',
  },
  {
    id: 31,
    authorNickName: 'nickname20',
    authorId: 'id20',
    title: '버논',
    content: '세븐틴 버논',
    createDate: '2021-12-21',
    views: 2000,
    likes: 200,
    storeFileName:
      'http://file2.instiz.net/data/cached_img/upload/2017/12/10/22/ea157bc4974420076209cce9df096bd9.jpg',
  },
  {
    id: 32,
    authorNickName: 'nickname9',
    authorId: 'id9',
    title: '청하',
    content: '청하 청하 청하 청하 청하 청하 청하 청하 청하',
    createDate: '2021-12-21',
    views: 900,
    likes: 90,
    storeFileName:
      'https://pds.joongang.co.kr/news/component/htmlphoto_mmdata/202012/09/4b713b30-3499-4cf7-a95d-1e877f0367d4.jpg',
  },
  {
    id: 33,
    authorNickName: 'nickname9',
    authorId: 'id9',
    title: '청하',
    content: '청하 청하 청하 청하 청하 청하 청하 청하 청하',
    createDate: '2021-12-21',
    views: 900,
    likes: 90,
    storeFileName:
      'https://pds.joongang.co.kr/news/component/htmlphoto_mmdata/202012/09/4b713b30-3499-4cf7-a95d-1e877f0367d4.jpg',
  },

  {
    id: 34,
    authorNickName: 'nickname9',
    authorId: 'id9',
    title: '청하',
    content: '청하 청하 청하 청하 청하 청하 청하 청하 청하',
    createDate: '2021-12-21',
    views: 900,
    likes: 90,
    storeFileName:
      'https://pds.joongang.co.kr/news/component/htmlphoto_mmdata/202012/09/4b713b30-3499-4cf7-a95d-1e877f0367d4.jpg',
  },

  {
    id: 35,
    authorNickName: 'nickname9',
    authorId: 'id9',
    title: '청하',
    content: '청하 청하 청하 청하 청하 청하 청하 청하 청하',
    createDate: '2021-12-21',
    views: 900,
    likes: 90,
    storeFileName:
      'https://pds.joongang.co.kr/news/component/htmlphoto_mmdata/202012/09/4b713b30-3499-4cf7-a95d-1e877f0367d4.jpg',
  },

  {
    id: 36,
    authorNickName: 'nickname9',
    authorId: 'id9',
    title: '청하',
    content: '청하 청하 청하 청하 청하 청하 청하 청하 청하',
    createDate: '2021-12-21',
    views: 900,
    likes: 90,
    storeFileName:
      'https://pds.joongang.co.kr/news/component/htmlphoto_mmdata/202012/09/4b713b30-3499-4cf7-a95d-1e877f0367d4.jpg',
  },
  {
    id: 37,
    authorNickName: 'nickname9',
    authorId: 'id9',
    title: '청하',
    content: '청하 청하 청하 청하 청하 청하 청하 청하 청하',
    createDate: '2021-12-21',
    views: 900,
    likes: 90,
    storeFileName:
      'https://pds.joongang.co.kr/news/component/htmlphoto_mmdata/202012/09/4b713b30-3499-4cf7-a95d-1e877f0367d4.jpg',
  },
  {
    id: 38,
    authorNickName: 'nickname9',
    authorId: 'id9',
    title: '청하',
    content: '청하 청하 청하 청하 청하 청하 청하 청하 청하',
    createDate: '2021-12-21',
    views: 900,
    likes: 90,
    storeFileName:
      'https://pds.joongang.co.kr/news/component/htmlphoto_mmdata/202012/09/4b713b30-3499-4cf7-a95d-1e877f0367d4.jpg',
  },
  {
    id: 39,
    authorNickName: 'nickname9',
    authorId: 'id9',
    title: '청하',
    content: '청하 청하 청하 청하 청하 청하 청하 청하 청하',
    createDate: '2021-12-21',
    views: 900,
    likes: 90,
    storeFileName:
      'https://pds.joongang.co.kr/news/component/htmlphoto_mmdata/202012/09/4b713b30-3499-4cf7-a95d-1e877f0367d4.jpg',
  },
  {
    id: 40,
    authorNickName: 'nickname9',
    authorId: 'id9',
    title: '청하',
    content: '청하 청하 청하 청하 청하 청하 청하 청하 청하',
    createDate: '2021-12-21',
    views: 900,
    likes: 90,
    storeFileName:
      'https://pds.joongang.co.kr/news/component/htmlphoto_mmdata/202012/09/4b713b30-3499-4cf7-a95d-1e877f0367d4.jpg',
  },
  {
    id: 41,
    authorNickName: 'nickname9',
    authorId: 'id9',
    title: '청하',
    content: '청하 청하 청하 청하 청하 청하 청하 청하 청하',
    createDate: '2021-12-21',
    views: 900,
    likes: 90,
    storeFileName:
      'https://pds.joongang.co.kr/news/component/htmlphoto_mmdata/202012/09/4b713b30-3499-4cf7-a95d-1e877f0367d4.jpg',
  },
  {
    id: 42,
    authorNickName: 'nickname9',
    authorId: 'id9',
    title: '청하',
    content: '청하 청하 청하 청하 청하 청하 청하 청하 청하',
    createDate: '2021-12-21',
    views: 900,
    likes: 90,
    storeFileName:
      'https://pds.joongang.co.kr/news/component/htmlphoto_mmdata/202012/09/4b713b30-3499-4cf7-a95d-1e877f0367d4.jpg',
  }
];

