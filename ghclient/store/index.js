export const state = () => ({
  username: undefined,
  displayName: undefined,
  gameBind: undefined,
  gamePlay: undefined
})

export const mutations = {
  setUsername(state, _username) {
    state.username = _username;
  },
  setDisplayName(state, _displayName) {
    state.displayName = _displayName;
  },
  setGameBind(state, _gameBind) {
    state.gameBind = _gameBind;
  },
  setGamePlay(state, _gamePlay) {
    state.gamePlay = _gamePlay;
  }
}
