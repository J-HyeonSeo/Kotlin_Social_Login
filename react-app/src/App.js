import './App.css';
import LoginProcessPage from "./pages/LoginProcessPage";
import {Route, Routes} from "react-router-dom";
import MainPage from "./pages/MainPage";

function App() {
  return (
      <Routes>
        <Route path="/" element={<MainPage/>}/>
        <Route path="/login/oauth2/code/kakao" element={<LoginProcessPage/>}/>
      </Routes>
  );
}

export default App;
