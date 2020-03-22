import React, { Component } from 'react';
import { Container, Header, Left, Body, Right, Button, Icon, Title, Text } from 'native-base';
import { Ionicons } from '@expo/vector-icons';
import styles from './style/MyHeader.style.js';
export default class MyHeader extends Component {
  constructor(props) {
    super(props);
  }

  render() {

    return (
        <Header style={styles.header}>
          <Left>
            <Button transparent onPress={() => this.props.handleBackPress() }>
              <Icon name='arrow-back' />
            </Button>
          </Left>
          <Body>
            <Title>Settings</Title>
          </Body>
          <Right/>
        </Header>
    );
  }
}
