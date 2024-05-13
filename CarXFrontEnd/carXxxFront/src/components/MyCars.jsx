import React, { useState, useEffect } from 'react';
import { getCarsByOwner, deleteCar } from '../services/CarService';
import { useNavigate, Link } from 'react-router-dom';
import Modal from 'react-modal';
import '../css/MyCars.css';
import MyCarsImg from '../assets/myCars.png'

Modal.setAppElement('#root');

const MyCars = () => {
  const [cars, setCars] = useState([]);
  const [error, setError] = useState('');
  const [modalIsOpen, setModalIsOpen] = useState(false);
  const [selectedCarId, setSelectedCarId] = useState(null);
  const navigate = useNavigate();

  useEffect(() => {
    const token = localStorage.getItem('accessToken');
    if (token) {
      loadCars(token);
    } else {
      setError('Nėra prieigos rakto. Būtina prisijungti.');
    }
  }, []);

  const loadCars = (token) => {
    getCarsByOwner(token)
      .then(response => {
        if (Array.isArray(response.data)) {
          setCars(response.data);
        } else {
          setError('Gauta netinkama duomenų struktūra iš serverio.');
        }
      })
      .catch(error => {
        console.error('Error loading cars:', error);
        setError('Nepavyko gauti automobilių informacijos.');
      });
  };

  const openModal = (carId) => {
    setSelectedCarId(carId);
    setModalIsOpen(true);
  };

  const closeModal = () => {
    setModalIsOpen(false);
    setSelectedCarId(null);
  };

  const handleDeleteCar = () => {
    const token = localStorage.getItem('accessToken');
    if (token && selectedCarId) {
      deleteCar(selectedCarId, token)
        .then(() => {
          setCars(cars.filter(car => car.id !== selectedCarId));
          closeModal();
        })
        .catch(error => {
          console.error('Error deleting car:', error);
          setError('Nepavyko ištrinti automobilio.');
        });
    }
  };

  if (error) {
    return <div className="error">{error}</div>;
  }

  return (
    <div className='main-container-home'>
      <img className="my-cars-img" src={MyCarsImg} alt="MyCars"></img>
      <div className='car-list'>
        {cars.map(car => (
          <div key={car.id} className="car-card">
            <div className="car-info">
              <p>{car.make}</p>
              <p>{car.model}</p>
              <p>{car.plateNumber}</p>
              <Modal
                isOpen={modalIsOpen}
                onRequestClose={closeModal}
                contentLabel="Delete Confirmation"
                className="modal"
                overlayClassName="overlay"
                >
                  <h2>Are you sure you want to delete this car?</h2>
                  <button onClick={handleDeleteCar}>Yes</button>
                  <button onClick={closeModal}>No</button>
              </Modal>
            </div>
            <div className="car-actions">
              <button onClick={() => navigate(`/update/${car.id}`)}>Update</button>
              <button onClick={() => openModal(car.id)}>Delete</button>
            </div>
            
          </div>
        ))}
      </div>
      <Link className='link' to="/home">Home</Link>
      
    </div>
  );
};

export default MyCars;
