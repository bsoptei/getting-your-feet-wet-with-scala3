package cardgame

trait UpdateGameState[A]:
  type UpdateInput
  
  extension (a: A) def updateGameState(updateInput: UpdateInput): A
