import LoginForm from "./LoginForm";
import Logo from '../assets/carxLogo.png'
import {  Link } from "react-router-dom";
import '../css/App.css'

const EnterPage = () => {
    return (
        <div className="enter-page-container">
            <header className="enter-page-header">
                    <img className="logo" src={Logo} alt="Logo"></img>
                    <h1 className="text-container">
                        CarX - For today's busy driver who wants to manage all matters related to their car in one place.
                    </h1>
            </header>
            <main>
                <div className="login-form"><LoginForm/></div>
                <div className="enter-page-signup-link">
                    <h1>Don't have an account yet?</h1>
                    <Link className="link" to="/registration">Sign Up</Link>
                </div>
            </main>
            <footer>

            </footer>

        </div>

    );
}

export default EnterPage