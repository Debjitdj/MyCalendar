import React from 'react';
import { AppRegistry } from 'react-native';

import Settings from './src/Settings.js';

class App extends React.Component {
  render() {
    return (
      <Settings
        startingDayOfWeekSunday={this.props.startingDayOfWeekSunday}
      />
    );
  }
}

AppRegistry.registerComponent('SettingsPage', () => App);
