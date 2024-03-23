import {Fragment} from "react";
import {useNavigate} from "react-router-dom";

const LoginProcessPage = () => {

  let navigate = useNavigate();

  console.log(window.location.pathname + window.location.search);

  fetch("http://localhost:8080" + window.location.pathname + window.location.search, {
    method: "POST",
    headers: { 'Content-Type': 'application/json' },
    credentials: 'include' 
  })
  .then(res => {
    res.json().then(json => {
      console.log(json);
      window.localStorage.setItem("token", json.token);
      navigate("/");
    })
  }).catch(e => {
    console.log(e);
  });

  return (
      <Fragment></Fragment>
  )
}

export default LoginProcessPage;