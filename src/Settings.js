import React, { Component } from "react";

import { Text, View, BackHandler, ActivityIndicator } from 'react-native';
import { Container, Content, Form } from "native-base";

import MyHeader from './MyHeader.js'
import StartingDayOfWeek from './StartingDayOfWeek.js'
import SettingsPageModule from './SettingsPageModule.js'

import styles from './style/Settings.style.js';

import { Ionicons } from '@expo/vector-icons';


export default class Settings extends Component {
  constructor(props) {
    super(props);
    this.state = {
      startingDayOfWeekSunday: this.props.startingDayOfWeekSunday,
      isLoading: true
    };
  }

  componentDidMount() {
    this.backHandler = BackHandler.addEventListener('hardwareBackPress', this.handleBackPress);
    this.setState({isLoading: false})
  }

  componentWillUnmount() {
    this.backHandler.remove();
  }

  handleBackPress = () => {
    SettingsPageModule.exitSettingsPage();
  }

  updateStartingDayOfWeekSunday = (value) => {
    this.setState({
      startingDayOfWeekSunday: value
    })
    SettingsPageModule.setStartingDayOfWeekSunday(value);
  }

  settingsPageBody = () => {
    if (this.state.isLoading)
      return (
        <View style={styles.spinner}>
          <ActivityIndicator size="large" color="#3F51B5" />
        </View>
      )
    else
      return (
        <Content style={styles.content}>
          <View style={styles.row}>
            <Form style={styles.rowUpper}>
              <Text style={styles.left}>Start week on Sunday</Text>
              <StartingDayOfWeek
                startingDayOfWeekSunday={this.state.startingDayOfWeekSunday}
                updateStartingDayOfWeekSunday={this.updateStartingDayOfWeekSunday}
              />
            </Form>
            <View  style={styles.rowLower}>
              <Text style={styles.infoText}>Turn this on to make Sunday the first day of week</Text>
            </View>
          </View>
        </Content>
      )
  }

  render() {
    return (
      <Container style={styles.container}>
        <MyHeader
          handleBackPress={this.handleBackPress}
        />
        { this.settingsPageBody() }
      </Container>
    );
  }
}
