import React, { Component } from 'react';
import './NotFound.css';
import { Link } from 'react-router-dom';

class NotFound extends Component {
  render() {
    return (
      <div className="page-not-found">
        <img src="https://www.nicepng.com/png/full/225-2255762_error404-error-404-icono-png.png" alt="404" width="350px" />
        <div className="desc">
          The Page you're looking for was not found.
                </div>
        <Link to="/home">
          <button className="go-back-btn" type="primary" size="large">Go Back</button>
        </Link>
      </div>
    );
  }
}

export default NotFound;