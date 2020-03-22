import { StyleSheet } from 'react-native';

export default StyleSheet.create({
  container: {
    backgroundColor: '#303F9F'
  },
  content: {
    backgroundColor: 'white',
    marginTop: 3,
    paddingTop: 20,
  },
  row: {
    paddingLeft: 25,
    paddingRight: 25,
  },
  rowUpper: {
    flexWrap: 'wrap',
    alignItems: 'flex-start',
    height: 50,
    justifyContent: 'center',
    flexDirection: 'column',
  },
  rowLower: {
    marginLeft: 20,
  },
  left: {
    alignSelf: 'flex-start',
    width: 240,
    fontSize: 18,
  },
  infoText: {
    fontSize: 12,
    color: 'gray'
  },
});
