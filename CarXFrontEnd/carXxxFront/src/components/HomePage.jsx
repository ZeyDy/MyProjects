import React from "react";
import Logo from '../assets/carxLogo.png'
import DigitalClock from "./DigitalClock";
import OruStotelesKomponentas from "./WeatherStation";
import { useNavigate, Link } from "react-router-dom";
import CarSelection from "./CarSelection";


const HomePage = () => {

    const navigate = useNavigate();

  const handleLogout = () => {
    localStorage.removeItem('accessToken'); 
    navigate('/enterpage'); 
  };

    return (
        <div className="main-container-home">
            <header className="container">
                <img className="logo" src={Logo} alt="Logo"></img>
                <DigitalClock />
                <nav className="navigate">
                    <Link className="link" to="/mycars">My Cars</Link>
                    <Link className="link" to="/createcar">Add Car</Link>
                    <button className="logout-button" onClick={handleLogout}>Log Out</button>
                </nav>
            </header>
            <main className="main-container-home">
                <div className="stoteles"><OruStotelesKomponentas /></div>
                <div><CarSelection/></div>
            </main>
            <footer></footer>
        </div>
    );
}

export default HomePage