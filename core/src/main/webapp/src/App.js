import React, { Component } from "react";
import { BrowserRouter as Router, Route, Switch } from "react-router-dom";
import Home from "./views/Home/Home";
import PatientDetails from "./views/Patient-Details/PatientDetails";
import PatientQuestions from "./views/Patient-Questions/PatientQuestions";
import PatientList from "./views/Patient-List/PatientList";
import Signin from "./views/Signin/Signin";
import AdminHome from "./views/Admin/AdminHome";

class App extends Component {
  render() {
    return (
      <Router>
        <Switch>
          <Route exact path="/" component={Signin} />
          <Route path="/patients" component={PatientList} />
          <Route path="/patient/:id" children={<PatientDetails />} />
          <Route path="/ask/:id" children={<PatientQuestions />} />
          <Route path="/home" component={Home} />
          <Route path="/admin" component={AdminHome} />
        </Switch>
      </Router>
    );
  }
}

export default App;
