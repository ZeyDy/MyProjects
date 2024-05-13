import React, { useState } from 'react';
import { createCar } from '../services/CarService';
import { useNavigate, Link } from 'react-router-dom';

const CreateCarForm = () => {
  const [car, setCar] = useState({
    make: '',
    model: '',
    plateNumber: ''
  });
  const navigate = useNavigate();
  const [error, setError] = useState('');
  const [success, setSuccess] = useState('');

  const handleInputChange = (event) => {
    const { name, value } = event.target;
    setCar(prev => ({ ...prev, [name]: value }));
  };

  const handleSubmit = (event) => {
    event.preventDefault();
    const token = localStorage.getItem('accessToken');

    if (!token) {
      setError('Trūksta autentifikacijos rakto.');
      return;
    }

    createCar(car, token)
      .then(response => {
        alert('Automobilis sėkmingai sukurtas.');
        navigate('/mycars');
      })
      .catch(error => {
        const errorMessage = error.response ? error.response.data.message : error.message;
        setError(`Nepavyko sukurti automobilio. Klaida: ${errorMessage}`);
        setSuccess('');
      });
  };

  return (
    <div className='main-container-home'>
    <div className='form-row-home'>
      <h2>Add new car</h2>
      {error && <div style={{ color: 'red' }}>{error}</div>}
      {success && <div style={{ color: 'green' }}>{success}</div>}
      <form className='form-row-home' onSubmit={handleSubmit}>
        <div>
          <label>MAKE:</label>
          <input
            name="make"
            value={car.make}
            placeholder="Dodge"
            required
            onChange={handleInputChange}
          />
        </div>
        <div>
          <label>MODEL:</label>
          <input
            name="model"
            value={car.model}
            placeholder="Viper"
            required
            onChange={handleInputChange}
          />
        </div>
        <div>
          <label>PLATENUMBER:</label>
          <input
            name="plateNumber"
            value={car.plateNumber}
            placeholder="ABC123"
            required
            onChange={handleInputChange}
          />
        </div>
        <button type="submit">Add Car</button>
        <Link className='link' to="/home">Home</Link>
      </form>
    </div>
    </div>
  );
};

export default CreateCarForm;
