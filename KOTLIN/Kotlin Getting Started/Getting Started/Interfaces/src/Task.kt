interface Drawable {
    fun draw()
}

open class Smiley: Drawable {
    override fun draw() = print(":-)")
}
class HairySmiley: Smiley() {
    override fun draw() {
        print("?")
        super.draw()
    }
}
