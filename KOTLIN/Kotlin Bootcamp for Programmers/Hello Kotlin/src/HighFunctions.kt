fun main(){
    val game = Game()
    println(game.path)
    game.north()
    game.south()
    game.east()
    game.west()
    game.end()
    println(game.path)

}
enum class Direction {NORTH,SOUTH,EAST,WEST,START,END}
class Game {
    var path   = mutableListOf<Direction>(Direction.START)
    val north  = { path.add(Direction.NORTH)}
    val south  = { path.add(Direction.SOUTH)}
    val east   = { path.add(Direction.EAST)}
    val west   = { path.add(Direction.WEST)}
    val end    = { path.add(Direction.END); println("Game Over: $path")}

}