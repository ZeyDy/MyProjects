
import './css/App.css'
import { BrowserRouter, Routes, Route  } from 'react-router-dom'
import HomePage from './components/HomePage'
import SignupForm from './components/SignupForm'
import LoginForm from './components/LoginForm'
import EnterPage from './components/EnterPage'
import MyCars from './components/MyCars'
import UpdateCarForm from './components/UpdateCarForm'
import CreateCarForm from './components/CreateCarForm'

function App() {


  return (
    <>
      <BrowserRouter>
        
        <Routes>
          <Route path='/enterpage' element={<EnterPage/>}></Route>
          <Route path='/home' element={<HomePage />}></Route>
          <Route path='/login' element={<LoginForm />}></Route>
          <Route path='/registration' element={<SignupForm />}></Route>
          <Route path='/mycars' element={<MyCars />}></Route>
          <Route path='/update/:carId' element={<UpdateCarForm/>}></Route>
          <Route path='/createcar' element={<CreateCarForm/>}></Route>
        </Routes>
      </BrowserRouter>
    </>
  )
}

export default App
