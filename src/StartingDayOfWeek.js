import React, { Component } from "react";
import { Switch } from 'react-native';

import styles from './style/StartingDayOfWeek.style.js';

export default class StartingDayOfWeek extends Component {
  constructor(props) {
    super(props);
  }
  render() {
    return (
        <Switch
          style={styles.switch}
          onValueChange={(value) => this.props.updateStartingDayOfWeekSunday(value)}
          value={this.props.startingDayOfWeekSunday}
        />
    );
  }
}
