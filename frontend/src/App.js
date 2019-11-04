import React, { Component } from 'react';
import logo from './logo.svg';
import './App.css';

class App extends Component {
  constructor(props) {
    super(props);
    this.state = {
      avocados: [],
    };
  }
  componentDidMount() {
    fetch(window._env_.backend_root + "/avocados")
      .then(response => response.json())
      .then(data => {console.log(data); this.setState({ avocados: data }) });
  }

  render() {
    const { avocados } = this.state;
    return (
      <ul>
        {avocados.map(avocado =>
          <li key={avocado.id}>
            A <em>{avocado.breed}</em> &#129361; of ripeness {avocado.ripeness}/10 that was bought {avocado.dateOfPurchase}
          </li>
        )}
      </ul>
    );
  }
}

export default App;
