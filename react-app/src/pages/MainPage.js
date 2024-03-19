import logo from "../logo.svg";
import kakaoImg from "../images/kakao-login.png";

const MainPage = () => {

  const getMemberHandler = () => {
    fetch("http://localhost:8080/social/info", {
      method: "GET",
      headers: {
        'Content-Type': 'application/json',
        'Authorization': "Bearer " + window.localStorage.getItem("token")
      }
    }).then(res => {
      res.json().then(json => {
        console.log(json);
      }).catch(e => {
        console.log(e);
      })
    }).catch(e => {
      console.log(e);
    })
  }

  return (
      <div className="App">
        <header className="App-header">
          <img src={logo} className="App-logo" alt="logo" />
          <p>
            THIS IS REACT - SPRING KAKAO LOGIN!!
          </p>
          <button onClick={getMemberHandler}>정보가져오기</button>
          <a href={"http://localhost:8080/oauth2/authorization/kakao"}>
            <img src={kakaoImg} alt="kakao-login"/>
          </a>
        </header>
      </div>
  )
}

export default MainPage;