
import React, { useState } from 'react';
import { signUp } from '../services/CarService';
import { useNavigate, Link } from 'react-router-dom';


const SignupForm = () => {
  const [formData, setFormData] = useState({
    username: '',
    email: '',
    password: ''
  });

  const navigate = useNavigate();

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData(prevState => ({
      ...prevState,
      [name]: value
    }));
  };


  const handleSubmit = (e) => {
    e.preventDefault();
    signUp(formData)
      .then(response => {
        console.log(response.data);
        alert('Registarcija sėkminga');
        navigate('/enterpage');
      })
      .catch(error => {
        console.error('Registration error:', error);
      });
  };

  return ( 
  <div className='enter-page-container'>
    <form className='form-row' onSubmit={handleSubmit}>
    <label htmlFor="Username">Username</label>
      <input
        type="text"
        name="username"
        value={formData.username}
        onChange={handleInputChange}
        placeholder="Username"
        required
      />
      <label htmlFor="Email">Email</label>
      <input
        type="email"
        name="email"
        value={formData.email}
        onChange={handleInputChange}
        placeholder="Email"
        required
      />
      <label htmlFor="Password">Password</label>
      <input
        type="password"
        name="password"
        value={formData.password}
        onChange={handleInputChange}
        placeholder="Password"
        required
      />
      <button type="submit">Register</button>
      <Link className='link' to="/enterpage">Login</Link>
    </form>
  </div>
  );
};

export default SignupForm;
